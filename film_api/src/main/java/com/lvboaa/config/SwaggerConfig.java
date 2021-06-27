package com.lvboaa.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * swagger配置类
 * @author lvbo
 * @date 2021年 02月18日 13:50:00
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket myDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("电影院购票系统[Api接口文档]") // 标题
                .description("") // 描述
                .contact(new Contact("lb", "", "2484420621@qq.com")) // 联系方式
                .version("1.0") // 版本号
                .build();
        docket.apiInfo(apiInfo);
        //设置只生成被Api这个注解注解过的Ctrl类中有ApiOperation注解的api接口的文档
        docket.select().apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)).build();
        return docket;
    }
}
