package org.example.judgeframework.filters;

import org.example.judgeframework.dto.RequestDTO;
import org.example.judgeframework.process.RunOneJudgeProcess;

import java.util.Map;


public class SampleFilter extends IntraProcessFilter {

    int temp;

    public SampleFilter(int temp){
        this.temp = temp;
    }


    @Override
    public void doFilterInternal(Map<String, String> requestDTO, RunOneJudgeProcess filterChain) {
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
