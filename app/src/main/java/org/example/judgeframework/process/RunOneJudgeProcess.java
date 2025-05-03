package org.example.judgeframework.process;

import org.example.judgeframework.dto.RequestDTO;
import org.example.judgeframework.filters.Filter;

import java.util.List;

//하나의 filter를 진행 시키는 클래스
//spring security 의 VirtualFilterChain 역할
public class RunOneJudgeProcess {

    private int index;

    private List<Filter> filters;

    public void doFilter(RequestDTO requestDTO){
        if(filters.size() <= index) return;

        Filter filter = filters.get(index++);
        filter.doFilter(requestDTO, this);
    }
}
