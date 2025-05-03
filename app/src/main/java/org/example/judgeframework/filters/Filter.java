package org.example.judgeframework.filters;

import org.example.judgeframework.dto.RequestDTO;
import org.example.judgeframework.process.RunOneJudgeProcess;


// 하나의 필터 클래스(낱개)
public abstract class Filter {

    public void doFilter(RequestDTO requestDTO, RunOneJudgeProcess filterChain){

    }

    abstract void doFilterInternal();
}
