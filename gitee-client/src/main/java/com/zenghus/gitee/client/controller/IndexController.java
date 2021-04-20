package com.zenghus.gitee.client.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zenghus.gitee.client.util.CookieUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @Author 曾虎
 * @Date 2021/4/20
 */
@RestController
public class IndexController {

    @Value("${gitee.authorize.url}")
    private String giteeUrl;

    @Value("${gitee.client_id}")
    private String client_id;

    @Value("${gitee.redirect_url}")
    private String redirect_url;

    @GetMapping("/")
    public String index(){
        System.out.println(giteeUrl+client_id+redirect_url);
        return "index";
    }

    @GetMapping("/user/info")
    public String userInfo(HttpServletRequest request) throws UnsupportedEncodingException {
        String user= CookieUtil.readLoginToken(request,"user");
        JSONObject jsonObject = JSONUtil.parseObj(URLDecoder.decode(user,"utf-8"));
        String userinf= HttpUtil.get("https://gitee.com/api/v5/user?access_token="+jsonObject.get("access_token"));
        return userinf;
    }

}
