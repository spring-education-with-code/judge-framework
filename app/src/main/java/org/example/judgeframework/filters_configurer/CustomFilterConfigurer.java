package org.example.judgeframework.filters_configurer;

import org.example.judgeframework.filters.Filter;
import org.example.judgeframework.process.MakeOneJudgeProcess;

public class CustomFilterConfigurer implements FilterConfigurer{
    Filter filter;

    public CustomFilterConfigurer(Filter filter){
        this.filter = filter;
    }

    @Override
    public void configure(MakeOneJudgeProcess makeOneJudgeProcess) {
        makeOneJudgeProcess.addFilter(filter);
    }
}
