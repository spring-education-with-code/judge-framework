package org.example.filters;

import java.io.IOException;
import java.util.List;

public class SampleCustomFilter {


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
