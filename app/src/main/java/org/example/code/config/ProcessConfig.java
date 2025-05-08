package org.example.code.config;

import org.example.annotation.OneProcess;
import org.example.annotation.Process;
import org.example.code.customfilters.CustomFilter;
import org.example.judgeframework.filters_configurer.*;
import org.example.judgeframework.process.MakeOneJudgeProcess;
import org.example.judgeframework.process.OneJudgeProcess;

import javax.swing.*;

@Process
public class ProcessConfig {

    @OneProcess
    public OneJudgeProcess springJudgeProcess(){
        //TODO 이거 나중에 매개변수로 올려야 함. 그런데 이는 빈 등록 순서나, 의존 관계를 정리 해야 하기 때문에, 나중에 고도화 해보자!
        MakeOneJudgeProcess makeOneJudgeProcess = new MakeOneJudgeProcess();

        makeOneJudgeProcess
                .updateSpringContent(new UpdateSpringContentFilterConfigurer())
                .springJudge(new SpringJudgeFilterConfigurer())
                .updateMySqlFilter(new UpdateMySqlFilterConfigurer())
                .updateRedisFilter(new UpdateRedisFilterConfigurer())
                .addCustomFilter(new CustomFilter());

        return makeOneJudgeProcess.build();
    }

    /*
        //실험용
        makeOneJudgeProcess
                .sample(makeSampleConfigure1());
    */

    public SampleConfigurer makeSampleConfigure1(){
        SampleConfigurer sampleConfigurer = new SampleConfigurer();
        sampleConfigurer.setTemp(1);

        return sampleConfigurer;
    }
}
