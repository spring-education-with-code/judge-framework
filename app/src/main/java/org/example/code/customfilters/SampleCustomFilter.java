package org.example.code.customfilters;

import org.example.judgeframework.dto.RequestDTO;
import org.example.judgeframework.filters.IntraProcessFilter;
import org.example.judgeframework.process.MakeOneJudgeProcess;
import org.example.judgeframework.process.RunOneJudgeProcess;


public class SampleCustomFilter extends IntraProcessFilter {

    int temp;

    public SampleCustomFilter(int temp){
        this.temp = temp;
    }


    @Override
    public void doFilterInternal(RequestDTO requestDTO, RunOneJudgeProcess filterChain) {
        System.out.println("샘플 필터 입니다 ^^7 // temp 값은 " + temp);
    }
}


/*
@RequiredArgsConstructor
public class IPCheckFilter extends OncePerRequestFilter {

    private final List<String> adminIps;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String remoteAddr = getClientIpAddr(request);

        if (!adminIps.contains(remoteAddr)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "IP not allowed");
            return;
        }

        filterChain.doFilter(request, response);
    }

 */
