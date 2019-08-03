package com.zjx.demo.filter;

import com.zjx.demo.utils.CustomMetric;
import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhengjiexiang on 2018/2/9
 */
@Component
@WebFilter(urlPatterns = "/*")
public class ControllerFilter implements Filter{

    static final Counter requests = Counter.build().name("dic_http_request_total").help("Total request.")
            .labelNames("url", "method", "code").register();

    static final Histogram requestLatencyHistogram = Histogram.build().labelNames("url", "method", "code")
            .name("dic_http_requests_response_time").help("Request latency in seconds.")
            .register();

    private Histogram.Timer histogramRequestTimer;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(request.getRequestURI().endsWith("prometheus")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        histogramRequestTimer = requestLatencyHistogram.labels(request.getRequestURI(), request.getMethod(), String.valueOf(response.getStatus())).startTimer();
        filterChain.doFilter(servletRequest, servletResponse);
        histogramRequestTimer.observeDuration();
        processRequest(request.getRequestURI(), request.getMethod(), String.valueOf(response.getStatus()));
    }

    @Override
    public void destroy() {

    }

    public void processRequest(String... method){
        requests.labels(method).inc();
    }

}
