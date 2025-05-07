package org.example.judgeframework.filters_configurer;

import org.example.judgeframework.filters.SampleFilter;
import org.example.judgeframework.process.MakeOneJudgeProcess;

public class SampleConfigurer implements FilterConfigurer {

    int temp;

    @Override
    public void configure(MakeOneJudgeProcess makeOneJudgeProcess){
        SampleFilter sampleCustomFilter = new SampleFilter(temp);
        makeOneJudgeProcess.addFilter(sampleCustomFilter);
    }

    public void setTemp(int temp){
        this.temp = temp;
    }
}
