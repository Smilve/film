package com.lvboaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class FilmApiSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmApiSpringbootApplication.class, args);
    }

}
