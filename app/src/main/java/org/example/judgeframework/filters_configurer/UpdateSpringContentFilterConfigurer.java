package org.example.judgeframework.filters_configurer;

import org.example.judgeframework.filters.UpdateSpringContentFilter;
import org.example.judgeframework.process.MakeOneJudgeProcess;

public class UpdateSpringContentFilterConfigurer implements FilterConfigurer{
    @Override
    public void configure(MakeOneJudgeProcess makeOneJudgeProcess) {
        UpdateSpringContentFilter updateSpringContentFilter = new UpdateSpringContentFilter();
        makeOneJudgeProcess.addFilter(updateSpringContentFilter);
    }
}
