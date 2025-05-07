package org.example.judgeframework.filters_configurer;

import org.example.judgeframework.filters.UpdateMySqlFilter;
import org.example.judgeframework.filters.UpdateRedisFilter;
import org.example.judgeframework.process.MakeOneJudgeProcess;

public class UpdateMySqlFilterConfigurer implements FilterConfigurer {
    @Override
    public void configure(MakeOneJudgeProcess makeOneJudgeProcess) {
        UpdateMySqlFilter updateMySqlFilter = new UpdateMySqlFilter();
        makeOneJudgeProcess.addFilter(updateMySqlFilter);
    }
}
