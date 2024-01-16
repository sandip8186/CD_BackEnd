package com.suma.consumer.config.activitylog;

import com.suma.consumer.services.activitylog.ActivityLogService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;

@Component
@Slf4j
public class RequestResponseStoringFilter implements Filter {

    @Autowired
    ActivityLogService activityLogService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.info("--------------- RequestResponseStoringFilter --------------");
        try {
            filterChain.doFilter(servletRequest, servletResponse);
            activityLogService.save();
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
//        Filter.super.destroy();
    }
}
