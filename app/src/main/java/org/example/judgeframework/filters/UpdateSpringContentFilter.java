package org.example.judgeframework.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.judgeframework.dto.RequestDTO;
import org.example.judgeframework.process.RunOneJudgeProcess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

public class UpdateSpringContentFilter extends IntraProcessFilter{
    @Override
    public void doFilterInternal(Map<String, String> requestDTO, RunOneJudgeProcess filterChain) {
        try {
            //dtoMap 에서 controller 받은 코드 파일에 덮어쓰기
            String basicFilePath = "../src/main/java/com/spring_education/template";
            String filePath = basicFilePath + "/controller/TestController.java";
            Files.write(Paths.get(filePath), requestDTO.get("controller").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            //dtoMap 에서 service 받은 코드 파일에 덮어쓰기
            filePath = basicFilePath + "/service/TestService.java";
            Files.write(Paths.get(filePath), requestDTO.get("service").getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
