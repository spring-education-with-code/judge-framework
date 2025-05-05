package org.example.code.customfilters_configurer;

import org.example.code.customfilters.SampleCustomFilter;
import org.example.judgeframework.filters_configurer.FilterConfigurer;
import org.example.judgeframework.process.MakeOneJudgeProcess;

public class SampleConfigurer implements FilterConfigurer {

    int temp;

    @Override
    public void configure(MakeOneJudgeProcess makeOneJudgeProcess){
        SampleCustomFilter sampleCustomFilter = new SampleCustomFilter(temp);
        makeOneJudgeProcess.addFilter(sampleCustomFilter);
    }

    public void setTemp(int temp){
        this.temp = temp;
    }
}
