package com.zenghus.gitee.client.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zenghus.gitee.client.util.CookieUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Author 曾虎
 * @Date 2021/4/20
 */
@Controller
@RequestMapping("/gitee")
public class GiteeController {

    @Value("${gitee.client_secret}")
    private String client_secret;

    @Value("${gitee.client_id}")
    private String client_id;

    @Value("${gitee.redirect_url}")
    private String redirect_url;

    @RequestMapping("/callback")
    public void callBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/");
        String code=request.getParameter("code");
        if(code!=null && code.length()>0){
            mv.setViewName("/user/info");
            String user= CookieUtil.readLoginToken(request,"user");
            if(user!=null && user.length()>0) {
                JSONObject jsonObject = JSONUtil.parseObj(URLDecoder.decode(user, "utf-8"));
                if (jsonObject.isNull("error")) {
                    response.sendRedirect(mv.getViewName());
                    return;
                }
            }
            //https://gitee.com/oauth/token?grant_type=authorization_code&code=63efb53df9cacba4817586bce93faf971682c3bc0e7d1a5911521ed3a36e167b&client_id=7b444dc1a345179a1f5f42f8127212d9606f9b0a2194306c51dd025720737789&redirect_uri=http://localhost:8080/gitee/callback&client_secret=f86d67f54292a1198dddc4c0199f8b9c5410525d91dd4f5b9c4887aa8afdb232
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("grant_type", "authorization_code");
            paramMap.put("code", code);
            paramMap.put("client_id", client_id);
            paramMap.put("redirect_uri", redirect_url);
            paramMap.put("client_secret", client_secret);
            String result= HttpUtil.post("https://gitee.com/oauth/token", paramMap);
            CookieUtil.writeLoginToken(response,"user", URLEncoder.encode(result,"utf-8"));
            response.sendRedirect(mv.getViewName());
        }else{
            response.sendRedirect(mv.getViewName());
        }
    }

}
