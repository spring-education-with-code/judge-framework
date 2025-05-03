package org.example.judgeframework.filters_configurer;

public class SpringJudgeFilterConfigurer implements FilterConfigurer{

    //시간 제한
    public int timeLimit;

    //메모리 제한
    public int memoryLimit;

    //configure 가 list filters 를 채우는 과정임.
    @Override
    public void configure() {

    }
}
