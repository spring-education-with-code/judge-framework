package org.example.judgeframework.process;

import org.example.judgeframework.filters_configurer.SampleConfigurer;
import org.example.judgeframework.filters.Filter;
import org.example.judgeframework.filters_configurer.*;

import java.util.ArrayList;
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

    public MakeOneJudgeProcess sample(SampleConfigurer sampleConfigurer){
        addConfigurer(sampleConfigurer);
        return MakeOneJudgeProcess.this;
    }

    public MakeOneJudgeProcess updateSpringContent(UpdateSpringContentFilterConfigurer updateSpringContentFilterConfigurer){
        addConfigurer(updateSpringContentFilterConfigurer);
        return MakeOneJudgeProcess.this;
    }

    public MakeOneJudgeProcess springJudge(SpringJudgeFilterConfigurer springJudgeFilterConfigurer){
        addConfigurer(springJudgeFilterConfigurer);
        return MakeOneJudgeProcess.this;
    }
    /*
    //Spring Security 예시 ... (cors)

    public HttpSecurity cors(Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer) throws Exception {
        corsCustomizer.customize(getOrApply(new CorsConfigurer<>()));
        return HttpSecurity.this;
    }
     */

    public void addFilter(Filter filter){
        filters.add(filter);
    }

    public void addConfigurer(FilterConfigurer filterConfigurer){
        Class<? extends FilterConfigurer> clazz = (Class<? extends FilterConfigurer>) filterConfigurer.getClass();
        this.configurers.put(clazz, filterConfigurer);
    }

    public OneJudgeProcess build(){
        //map 을 순회하면서 순서대로 list filters에 추가
        this.filters = new ArrayList<>();

        // 2) 순서대로 Filter 생성
        for (FilterConfigurer configurer : configurers.values()) {
            // FilterConfigurer 인터페이스에 정의된 메서드를 호출
            configurer.configure(this);
        }

        return new OneJudgeProcess(filters);
    }

}
