package org.example.judgeframework.filters_configurer;

import org.example.judgeframework.filters.Filter;
import org.example.judgeframework.process.MakeOneJudgeProcess;

public interface FilterConfigurer {
    //configure 가 list filters 를 채우는 과정임.
    public void configure(MakeOneJudgeProcess makeOneJudgeProcess);
}
