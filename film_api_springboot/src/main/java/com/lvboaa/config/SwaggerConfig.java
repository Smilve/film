package com.lvboaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;


/**
 * swagger配置类
 * @author lvbo
 * @date 2021年 02月18日 13:50:00
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket getDocket(Environment environment){

        //判断是否是开发环境，提升效率
        Profiles of = Profiles.of("dev", "test");
        boolean flag = environment.acceptsProfiles(of);

        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .groupName("lvbo")
                .select()
                //RequestHandlerSelectors 配置要扫描接口的方式
                //.basePackage：指定要扫描的包
                //.any():扫描全部
                //.none():不扫描
                //.withClassAnnotation(Controller.class)扫描类上的注解
                //.withMethodAnnotation(RequestMapping.class)扫描类上的注解
                .apis(RequestHandlerSelectors.basePackage("com.lvboaa.controller"))
                //过滤路径,只扫描下面路径的请求
                //.paths(PathSelectors.ant("/lvbo/**"))
                .build()
                //配置Swagger是否启动
                .enable(flag);
    }

    //配置Swagger信息 apiInfo() Swagger的页面信息
    private ApiInfo apiInfo(){
        Contact contact = new Contact("Lvboaa", "https://www.baidu.com", "2484420621@qq.com");//作者信息
        return new ApiInfo(
                "橘子电影购票系统[Api接口文档]",
                "深呼吸两次，让心情平静。",
                "1.0",
                "https://www.baidu.com",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());

    }
}
