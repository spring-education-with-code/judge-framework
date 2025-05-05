package org.example.judgeframework.filters;

import org.example.judgeframework.dto.RequestDTO;
import org.example.judgeframework.process.RunOneJudgeProcess;

public interface Filter {
    void doFilter(RequestDTO requestDTO, RunOneJudgeProcess filterChain);

}
