package com.taskflow.taskk.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        request.setAttribute("startTime", System.currentTimeMillis());

        MDC.put("method", request.getMethod());
        MDC.put("path", request.getRequestURI());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        long start = (Long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - start;

        log.info("⬅️ RESPONSE method={} path={} status={} duration={}ms",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                duration);

        MDC.clear();
    }
}