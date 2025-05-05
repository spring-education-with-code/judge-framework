package org.example.code.config;

import org.example.annotation.OneProcess;
import org.example.annotation.Process;
import org.example.code.customfilters_configurer.SampleConfigurer;
import org.example.judgeframework.process.MakeOneJudgeProcess;
import org.example.judgeframework.process.OneJudgeProcess;

@Process
public class ProcessConfig {

    @OneProcess
    public OneJudgeProcess springJudgeProcess(){
        //TODO 이거 나중에 매개변수로 올려야 함. 그런데 이는 빈 등록 순서나, 의존 관계를 정리 해야 하기 때문에, 나중에 고도화 해보자!
        MakeOneJudgeProcess makeOneJudgeProcess = new MakeOneJudgeProcess();

        //실험용
        makeOneJudgeProcess
                .sample(makeSampleConfigure1());


        /*
        makeOneJudgeProcess
                .springJudge(new SpringJudgeFilterConfigurer())
                .sendRabbitMQMessage(new SendRabbitMQFilterConfigurer());
        */
        return makeOneJudgeProcess.build();
    }

    public SampleConfigurer makeSampleConfigure1(){
        SampleConfigurer sampleConfigurer = new SampleConfigurer();
        sampleConfigurer.setTemp(1);

        return sampleConfigurer;
    }
}
