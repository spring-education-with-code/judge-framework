package org.example.judgeframework.filters_configurer;

import org.example.judgeframework.filters.UpdateRedisFilter;
import org.example.judgeframework.process.MakeOneJudgeProcess;

public class UpdateRedisFilterConfigurer implements FilterConfigurer{
    @Override
    public void configure(MakeOneJudgeProcess makeOneJudgeProcess) {
        UpdateRedisFilter updateRedisFilter = new UpdateRedisFilter();
        makeOneJudgeProcess.addFilter(updateRedisFilter);
    }
}
