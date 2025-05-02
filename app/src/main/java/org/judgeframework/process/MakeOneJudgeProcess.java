package org.judgeframework.process;

import org.judgeframework.configurer.FilterConfigurer;
import org.judgeframework.filters.Filter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

//spring security의 HttpSecurity 포지션
//한 개의 채점 process를 "만드는" 담당이다.
public class MakeOneJudgeProcess {

    // 시큐리티에서 configurers는 다음과 같이 저장되어 있음.
    // private final LinkedHashMap<Class<? extends SecurityConfigurer<O, B>>, List<SecurityConfigurer<O, B>>> configurers = new LinkedHashMap<>();
    LinkedHashMap<Class<? extends FilterConfigurer>, FilterConfigurer> configurers = new LinkedHashMap<>();

    private List<Filter> filters;

    public MakeOneJudgeProcess checkDuplicate(){
        return MakeOneJudgeProcess.this;
    }

    public MakeOneJudgeProcess springJudge(){
        return MakeOneJudgeProcess.this;
    }
    /*
    //Spring Security 예시 ... (cors)

    public HttpSecurity cors(Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer) throws Exception {
        corsCustomizer.customize(getOrApply(new CorsConfigurer<>()));
        return HttpSecurity.this;
    }
     */
    public OneJudgeProcess build(){
        return null;
    }

}
