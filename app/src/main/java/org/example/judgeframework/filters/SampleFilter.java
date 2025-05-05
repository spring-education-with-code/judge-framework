package org.example.judgeframework.filters;

import org.example.judgeframework.dto.RequestDTO;
import org.example.judgeframework.process.RunOneJudgeProcess;

// 기본으로 주어지는 필터
public class SampleFilter extends IntraProcessFilter{

    @Override
    public void doFilterInternal(RequestDTO requestDTO, RunOneJudgeProcess filterChain) {

    }
}
