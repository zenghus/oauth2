package com.zenghus.gitee.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author 曾虎
 * @Date 2021/4/20
 */
public class CookieUtil {

    private static Logger logger = LoggerFactory.getLogger(CookieUtil.class);

    //从请求中读取cookie
    public static String readLoginToken(HttpServletRequest request,String key){
        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck : cks){
                logger.info("read cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                if(key.equals(ck.getName())){
                    logger.info("return cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    //往响应中写cookie
    //这里的cookie的名字就是rmall_login_token，而值就是token,这里的token就是sessionID
    public static void writeLoginToken(HttpServletResponse response,String key,String value){
        Cookie cookie = new Cookie(key, value);
        //将cookie设置在根目录下面
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        //设置cookie的有效期，单位是秒(一年)
        //如果这个maxage不设置的话，cookie就不会写入硬盘，而是写在内存。只在当前页面有效。
        cookie.setMaxAge(60*60*24*365);
        logger.info("write cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
        response.addCookie(cookie);
    }

    //删除cookie(从请求中读，往响应中写,已经删除完了的)
    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response,String key){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(key)){
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    logger.info("del cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
                    response.addCookie(cookie);
                    return;
                }
            }
        }
    }

}
