package org.example.judgeframework.filters;

import org.example.judgeframework.dto.RequestDTO;
import org.example.judgeframework.process.RunOneJudgeProcess;

public abstract class IntraProcessFilter implements Filter{
    @Override
    public void doFilter(RequestDTO requestDTO, RunOneJudgeProcess filterChain) {
        doFilterInternal(requestDTO, filterChain);
        filterChain.doFilter(requestDTO);
    }

    public abstract void doFilterInternal(RequestDTO requestDTO, RunOneJudgeProcess filterChain);
}
