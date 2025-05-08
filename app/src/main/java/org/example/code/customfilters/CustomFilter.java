package org.example.code.customfilters;

import org.example.judgeframework.filters.IntraProcessFilter;
import org.example.judgeframework.process.RunOneJudgeProcess;

import java.util.Map;

public class CustomFilter extends IntraProcessFilter {
    @Override
    public void doFilterInternal(Map<String, String> requestDTO, RunOneJudgeProcess filterChain) {
        System.out.println("커스텀 필터 입니다 *^^*");
    }
}
