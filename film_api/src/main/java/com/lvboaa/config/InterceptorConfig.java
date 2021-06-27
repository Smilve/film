package com.lvboaa.config;

import com.lvboaa.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lvbo
 * @date 2021年 02月19日 14:17:55
 */

//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    //@Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

    //配置拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/swagger-ui.html", "/webjars/**", "/swagger-resources/**","/v2/**");
    }
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
         //swagger增加url映射
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
