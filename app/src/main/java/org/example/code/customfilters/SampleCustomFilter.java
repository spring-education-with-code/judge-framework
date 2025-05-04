package org.example.code.customfilters;

import org.example.judgeframework.process.MakeOneJudgeProcess;


public class SampleCustomFilter {

    public void sample(){
        MakeOneJudgeProcess makeOneJudgeProcess = new MakeOneJudgeProcess();

        //makeOneJudgeProcess.checkDuplicate();
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
