package com.jqcx.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 测试 过滤器（Filter）
 */
public class TestPrintUrlFilter implements Filter  {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //站点图标/favicon.ico  filter会执行2次
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        System.out.println(request.getRequestURI());
        System.out.println("MyFilter dofilter...");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
