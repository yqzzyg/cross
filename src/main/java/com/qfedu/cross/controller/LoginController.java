package com.qfedu.cross.controller;

import com.qfedu.cross.common.JsonResult;
import com.qfedu.cross.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

//该注解支持跨域
@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/login.do")
    public JsonResult login(HttpServletRequest request,String name, String password){
        //haha盐值
        //生成token
        String token = MD5Utils.md5(name + "haha");
        //将token放到redis中
        stringRedisTemplate.opsForValue().set(token,name);
        stringRedisTemplate.expire(token,30, TimeUnit.SECONDS);
        //将token发送给前端
        System.out.println(password+name+token);
        return new JsonResult(0,token);
    }
    /*  public JsonResult login(String name, HttpSession session, HttpServletResponse response){

        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setPath("/");
        response.addCookie(cookie);
        return new JsonResult(0,null);
    }*/

    @RequestMapping("/cookie.do")
    public JsonResult cookietest(@CookieValue(value = "JSESSIONID",required = false) String sessionId){
        return new JsonResult(0,sessionId);
    }
}