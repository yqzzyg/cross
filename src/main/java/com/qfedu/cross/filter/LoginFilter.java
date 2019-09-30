package com.qfedu.cross.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfedu.cross.common.JsonResult;
import com.qfedu.cross.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/*")
public class LoginFilter implements Filter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("loginFilter");
        HttpServletRequest req =  (HttpServletRequest)servletRequest;

        HttpServletResponse res =  (HttpServletResponse)servletResponse;

        String method = req.getMethod();
        // 向请求头中写入数据后，会自动先发一个提交方式是OPTIONS的请求
        // 我们不用处理该请求，直接返回
        if(method.equalsIgnoreCase("OPTIONS")){
            return;
        }

        String uri = req.getRequestURI();
        if (uri.contains("login")){
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
//            String token = req.getParameter("token");
            //从请求头中取东西
            String token = req.getHeader("token");
            String ajax = req.getHeader("x-requested-with");
            if (token==null||token.equals("")){
                if (ajax!=null && ajax.equals("XMLHttpRequest")){
                    JsonResult result = new JsonResult(1, "权限不够");
                    ObjectMapper objectMapper = new ObjectMapper();
                    String jsonStr = objectMapper.writeValueAsString(result);
                    res.getWriter().write(jsonStr);
                    return;
                }else {
                    //跳转登录
                    res.sendRedirect(req.getContextPath()+"/login.html");
                }

            }else {
                String name = stringRedisTemplate.opsForValue().get(token);
                if (name==null){
                    // 判断是否ajax请求
                    if(ajax != null && ajax.equals("XMLHttpRequest") ){
                        JsonResult result = new JsonResult(1, "权限不够");
                        ObjectMapper objectMapper = new ObjectMapper();
                        String jsonStr = objectMapper.writeValueAsString(result);
                        res.getWriter().write(jsonStr);
                        return;
                    }else {
                        // 跳到登转陆页面
                        res.sendRedirect(req.getContextPath() + "/login.html");
                    }

                }else {
                    String token2 = MD5Utils.md5(name + "haha");
                    if (token.equals(token2)){
                        //计算结果一致，放行
                        filterChain.doFilter(servletRequest,servletResponse);
                    }else {
                        // 判断是否ajax请求
                        if(ajax != null && ajax.equals("XMLHttpRequest") ){
                            JsonResult result = new JsonResult(1, "权限不够");
                            ObjectMapper objectMapper = new ObjectMapper();
                            String jsonStr = objectMapper.writeValueAsString(result);
                            res.getWriter().write(jsonStr);
                            return;
                        }else {
                            // 跳到登转陆页面
                            res.sendRedirect(req.getContextPath() + "/login.html");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
