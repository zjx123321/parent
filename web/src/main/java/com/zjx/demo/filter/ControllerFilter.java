package com.zjx.demo.filter;

import com.codahale.metrics.Timer;
import com.zjx.demo.config.MetricConfig;
import com.zjx.demo.controller.HelloController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by zhengjiexiang on 2018/2/9
 */
@Component
@WebFilter(urlPatterns = "/*")
public class ControllerFilter implements Filter{

    private Logger logger = LoggerFactory.getLogger(ControllerFilter.class);

    @Resource
    private MetricConfig.MeterFactory meterFactory;

    @Resource
    private MetricConfig.TimerFactory timerFactory;

    @Resource
    private MetricConfig.CounterFactory counterFactory;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        logger.info("请求url：{}", request.getRequestURI());
        meterFactory.getMeter("meter:" + request.getRequestURI()).mark();
        counterFactory.getCounter("counter:" + request.getRequestURI()).inc();
        final Timer.Context context = timerFactory.getTimer("times:" + request.getRequestURI()).time();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        }finally {
            context.stop();
        }
    }

    @Override
    public void destroy() {

    }
}
