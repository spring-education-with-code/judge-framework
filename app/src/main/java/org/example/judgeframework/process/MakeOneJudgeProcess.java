package org.example.judgeframework.process;

import org.example.judgeframework.filters_configurer.CheckDuplicateFilterConfigurer;
import org.example.judgeframework.filters_configurer.FilterConfigurer;
import org.example.judgeframework.filters.Filter;
import org.example.judgeframework.filters_configurer.SendRabbitMQFilterConfigurer;
import org.example.judgeframework.filters_configurer.SpringJudgeFilterConfigurer;

import java.util.LinkedHashMap;
import java.util.List;

//spring security의 HttpSecurity 포지션
//한 개의 채점 process를 "만드는" 담당이다.
public class MakeOneJudgeProcess {

    // 시큐리티에서 configurers는 다음과 같이 저장되어 있음.
    // private final LinkedHashMap<Class<? extends SecurityConfigurer<O, B>>, List<SecurityConfigurer<O, B>>> configurers = new LinkedHashMap<>();

    // 1) configurers 에 설정 정보를 저장하고
    // 2) 그 다음 filters에 이 설정 정보 기반해서 필터 저장 한다.
    // 3) 그리고 마침내 하나의 OneJudgeProcess 로 생성.
    LinkedHashMap<Class<? extends FilterConfigurer>, FilterConfigurer> configurers = new LinkedHashMap<>();

    private List<Filter> filters;

    public MakeOneJudgeProcess checkDuplicate(CheckDuplicateFilterConfigurer checkDuplicateFilterConfigurer){
        addConfigurer(checkDuplicateFilterConfigurer);
        return MakeOneJudgeProcess.this;
    }

    public MakeOneJudgeProcess springJudge(SpringJudgeFilterConfigurer springJudgeFilterConfigurer){
        addConfigurer(springJudgeFilterConfigurer);
        return MakeOneJudgeProcess.this;
    }

    public MakeOneJudgeProcess sendRabbitMQMessage(SendRabbitMQFilterConfigurer sendRabbitMQFilterConfigurer){
        addConfigurer(sendRabbitMQFilterConfigurer);
        return MakeOneJudgeProcess.this;
    }
    /*
    //Spring Security 예시 ... (cors)

    public HttpSecurity cors(Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer) throws Exception {
        corsCustomizer.customize(getOrApply(new CorsConfigurer<>()));
        return HttpSecurity.this;
    }
     */

    public void addConfigurer(FilterConfigurer filterConfigurer){
        Class<? extends FilterConfigurer> clazz = (Class<? extends FilterConfigurer>) filterConfigurer.getClass();
        this.configurers.put(clazz, filterConfigurer);
    }

    public OneJudgeProcess build(){
        return null;
    }

}
