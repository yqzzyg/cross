package com.qfedu.cross.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CrossFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println(((HttpServletRequest)servletRequest).getRequestURI()+"CrossFilter");
        System.out.println("crossFilter");
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        //  * 任意ip的路径都可以跨域访问
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        //httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,accept,content-type,token,uname");
        // * 任意的请求头可以跨域
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        // 允许跨域的提交方式
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "OPTIONS,GET,POST,DELETE,PUT");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
