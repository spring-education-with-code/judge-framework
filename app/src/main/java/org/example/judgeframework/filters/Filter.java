package org.example.judgeframework.filters;

import org.example.judgeframework.dto.RequestDTO;
import org.example.judgeframework.process.RunOneJudgeProcess;

import java.util.Map;

public interface Filter {
    void doFilter(Map<String, String> requestDTO, RunOneJudgeProcess filterChain);

}
