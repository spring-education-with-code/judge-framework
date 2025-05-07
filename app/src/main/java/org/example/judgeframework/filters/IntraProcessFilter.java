package org.example.judgeframework.filters;

import org.example.judgeframework.dto.RequestDTO;
import org.example.judgeframework.process.RunOneJudgeProcess;

import java.util.Map;

public abstract class IntraProcessFilter implements Filter{
    @Override
    public void doFilter(Map<String, String> requestDTO, RunOneJudgeProcess filterChain) {
        doFilterInternal(requestDTO, filterChain);
        filterChain.doFilter(requestDTO);
    }

    public abstract void doFilterInternal(Map<String, String> requestDTO, RunOneJudgeProcess filterChain);
}
