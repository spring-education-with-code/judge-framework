package org.judgeframework.filters;

import org.judgeframework.dto.RequestDTO;
import org.judgeframework.process.RunOneJudgeProcess;


// 하나의 필터 클래스(낱개)
public abstract class Filter {

    public void doFilter(RequestDTO requestDTO, RunOneJudgeProcess filterChain){

    }

    abstract void doFilterInternal();
}
