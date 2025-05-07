package org.example.judgeframework.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.example.judgeframework.dto.ResultDTO;
import org.example.judgeframework.process.RunOneJudgeProcess;
import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.gradle.tooling.events.ProgressEvent;
import org.gradle.tooling.events.ProgressListener;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Slf4j
public class SpringJudgeFilter extends IntraProcessFilter{

    @Override
    public void doFilterInternal(Map<String, String> requestDTO, RunOneJudgeProcess filterChain) {
        String isCorrect;

        int exitCode = build(requestDTO, "1", Long.valueOf(requestDTO.get("submit_id")));

        if(exitCode == 0){
            isCorrect = "1";
        }else{
            isCorrect = "0";
        }

        // 정답 여부를 requestDTO 에 삽입
        requestDTO.put("isCorrect", isCorrect);
    }

    // 빌드 한다 (result 0 이 정답인거)
    public int build(Map<String, String> dtoMap, String userId, long submitId){

        final AtomicInteger result = new AtomicInteger(0);
        final AtomicInteger passedTestCount = new AtomicInteger(0);
        int totalTestCount = countTests();

        File projectDir = new File("..");

        // Gradle 프로젝트에 연결
        try (ProjectConnection connection = GradleConnector.newConnector()
                .forProjectDirectory(projectDir)
                .connect()) {

            // "test" 태스크를 실행할 BuildLauncher 생성
            BuildLauncher buildLauncher = connection.newBuild();
            buildLauncher.forTasks("clean", "test");

            // 진행 상황 리스너 등록
            buildLauncher.addProgressListener(new ProgressListener() {
                @Override
                public void statusChanged(ProgressEvent event) {
                    // <System.out.println("Progress event: " + event.getDisplayName()); 의 출력형태>
                    // 테스트 성공 시
                    // Progress event: Test 더미_테스트_1()(com.spring_education.template.Test1) started
                    // Progress event: Test 더미_테스트_1()(com.spring_education.template.Test1) succeeded
                    // 테스트 실패 시
                    // Progress event: Test class com.spring_education.template.Test1 failed
                    String nowEvent = event.getDisplayName();

                    if (nowEvent.contains("case") && nowEvent.contains("succeeded")) {
                        sendResults(makeResultsMessage(1,1, submitId, passedTestCount.incrementAndGet(), totalTestCount));
                        log.info("test succeed 로그" + passedTestCount.get() + "번째 테스트가 통과되었습니다" + "원본 메세지: " + nowEvent);
                    }

                    if (nowEvent.contains("case") && nowEvent.contains("failed")) {
                        sendResults(makeResultsMessage(1,1, submitId,-1, totalTestCount));
                        result.set(1);
                        log.info("test failed 로그");
                    }
                }
            });

            // 태스크 실행 (실행 중 진행 이벤트가 리스너에 의해 출력됨)
            buildLauncher.run();
            log.info("테스트 실행 완료");
        } catch (Exception e) {
            // 예외 메시지만 로깅하거나 별도로 처리
            log.error("Build failed: " + e.getMessage());
        }

        return result.get();
    }

    public int countTests(){

        int count = 0;

        Path filePath = Paths.get("../src/test/java/com/spring_education/template");

        try (Stream<Path> paths = Files.walk(filePath)) {
            count = paths.filter(Files::isRegularFile)
                    // 파일 이름이 "Test"로 시작하고 ".java"로 끝나는지 필터링
                    .filter(path -> {
                        String fileName = path.getFileName().toString();
                        return fileName.startsWith("Test") && fileName.endsWith(".java");
                    })
                    .mapToInt(path -> {
                        try{
                            String content = new String(Files.readAllBytes(path));
                            int temp_count = 0;
                            int index = content.indexOf("@Test");

                            while (index != -1) {
                                temp_count ++;
                                index = content.indexOf("@Test", index + 1);
                            }

                            return temp_count;
                        }catch(IOException e){
                            log.error(e.getMessage());
                            return 0;
                        }
                    })
                    .sum();
        }catch(IOException e){
            log.error(e.getMessage());
        }

        return count;
    }

    public String makeResultsMessage(int userId, int problemId, long submitId, int solvedTestNum, int totalTestNum){
        //problemId 는 필요 없을 지도..
        ResultDTO resultDTO = new ResultDTO(userId, problemId, submitId, solvedTestNum, totalTestNum);
        String message = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            message = objectMapper.writeValueAsString(resultDTO);
        }catch(JsonProcessingException e){
            log.info(e.getMessage());
        }

        return message;
    }

    //rabbitmq 에 (중간) 결과를 보내기
    public void sendResults(String message){

        String QUEUE_NAME = "spring.education.result";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 메시지 발행 (exchange는 기본값인 빈 문자열을 사용)
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            log.info(" [x] Sent '" + message + "'");
        }catch(IOException e){
            log.error(e.getMessage());
        }catch(TimeoutException e){
            log.error(e.getMessage());
        }
    }
}
