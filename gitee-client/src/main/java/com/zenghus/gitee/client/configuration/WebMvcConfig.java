package com.zenghus.gitee.client.configuration;

import com.zenghus.gitee.client.filter.GiteeLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author 曾虎
 * @Date 2021/4/20
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    GiteeLoginFilter giteeLoginFilter;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(giteeLoginFilter).addPathPatterns("/**")
                .excludePathPatterns("/")
                .excludePathPatterns("/gitee/callback");
        super.addInterceptors(registry);
    }
}
