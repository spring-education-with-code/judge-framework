package org.example.judgeframework.filters;

import org.example.judgeframework.dto.RequestDTO;
import org.example.judgeframework.process.RunOneJudgeProcess;

import java.util.Map;

public class SpringJudgeFilter extends IntraProcessFilter{
    @Override
    public void doFilterInternal(Map<String, String> requestDTO, RunOneJudgeProcess filterChain) {
        //requestDTO 에
    }
}
