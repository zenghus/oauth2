package com.zenghus.gitee.client.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zenghus.gitee.client.util.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
public class UserController {

    @GetMapping("/user/info")
    public String userInfo(HttpServletRequest request) throws UnsupportedEncodingException {
        String user= CookieUtil.readLoginToken(request,"user");
        HttpSession session=request.getSession();
        if(session.getAttribute("userinfo")==null){
            JSONObject jsonObject = JSONUtil.parseObj(URLDecoder.decode(user,"utf-8"));
            String userinfo= HttpUtil.get("https://gitee.com/api/v5/user?access_token="+jsonObject.get("access_token"));
            session.setAttribute("userinfo",JSONUtil.parseObj(userinfo));
        }
        return "userinfo";
    }
}
