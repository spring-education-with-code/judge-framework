package org.example.judgeframework.proxy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DeliverCallback;
import org.example.judgeframework.dto.RequestDTO;
import org.example.judgeframework.process.OneJudgeProcess;
import org.example.judgeframework.process.RunOneJudgeProcess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//최초 진입점.
//RabbitMQ 메세지를 받아서 어떤 채점 process 한테 넘길 지를 결정한다.
public class ProcessChainProxy {

    ObjectMapper objectMapper = new ObjectMapper();
    public List<RunOneJudgeProcess> judgeProcesses = new ArrayList<>();

    public DeliverCallback createDeliverCallback(){
        return (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            Map<String, String> requestDTO = objectMapper.readValue(message, Map.class);

            //processChain 고르고 doFilter 한다
            //* 지금은 무조건 한 개의 process 로 고정되게 함.
            RunOneJudgeProcess nowProcess = judgeProcesses.get(0);

            nowProcess.doFilter(requestDTO);
        };
    }

    //이것도 한 개의 필터....
    public void updateContent(Map<String, String> dtoMap){
        try {
            //dtoMap 에서 controller 받은 코드 파일에 덮어쓰기
            String basicFilePath = "../src/main/java/com/spring_education/template";
            String filePath = basicFilePath + "/controller/TestController.java";
            Files.write(Paths.get(filePath), dtoMap.get("controller").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            //dtoMap 에서 service 받은 코드 파일에 덮어쓰기
            filePath = basicFilePath + "/service/TestService.java";
            Files.write(Paths.get(filePath), dtoMap.get("service").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
