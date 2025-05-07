package org.example.judgeframework.filters;

import io.lettuce.core.api.sync.RedisCommands;
import org.example.App;
import org.example.judgeframework.JudgeApplication;
import org.example.judgeframework.process.RunOneJudgeProcess;
import org.example.judgeframework.utility.Sha256;

import java.util.Map;

public class UpdateRedisFilter extends IntraProcessFilter {

    Sha256 sha256 = new Sha256();
    //redis 에 채점 결과 업데이트.
    @Override
    public void doFilterInternal(Map<String, String> requestDTO, RunOneJudgeProcess filterChain) {
        insertRedis(requestDTO, Integer.valueOf(requestDTO.get("isCorrect")));
    }

    public void insertRedis(Map<String, String> dtoMap, int isCorrect){
        String userId = dtoMap.get("user_id");
        String problemId = dtoMap.get("problem_id");
        String controller = dtoMap.get("controller");
        String service = dtoMap.get("service");
        String codeHash = sha256.encrypt(controller + service);

        String key = "submit_cache:" + userId + ":" + problemId;
        RedisCommands<String, String> redisCommands = JudgeApplication.redisConnection.sync();

        redisCommands.hset(key, codeHash, String.valueOf(isCorrect));
    }
}
