package com.zenghus.gitee.client.filter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.zenghus.gitee.client.util.CookieUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;

/**
 * @Author 曾虎
 * @Date 2021/4/20
 */
@Component
public class GiteeLoginFilter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String user= CookieUtil.readLoginToken(request,"user");
        if(user!=null && user.length()>0){
            JSONObject jsonObject = JSONUtil.parseObj(URLDecoder.decode(user,"utf-8"));
            if(jsonObject.isNull("error")){
                return true;
            }else{
                CookieUtil.delLoginToken(request,response,"user");
            }
        }
        // 不存在重定向到错误页面，并返回 false
        response.sendRedirect("https://gitee.com/oauth/authorize?client_id=7b444dc1a345179a1f5f42f8127212d9606f9b0a2194306c51dd025720737789&redirect_uri=http://localhost:8080/gitee/callback&response_type=code");
        return false;
    }
}
