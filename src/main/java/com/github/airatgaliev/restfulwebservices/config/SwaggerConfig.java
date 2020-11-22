package com.github.airatgaliev.restfulwebservices.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(
      Arrays.asList("application/json", "application/xml"));

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).select().apis(
        RequestHandlerSelectors.basePackage("com.github.airatgaliev.restfulwebservices.controller"))
        .paths(
            PathSelectors.any()).build().apiInfo(apiInfo()).produces(DEFAULT_PRODUCES_AND_CONSUMES)
        .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
  }

  private ApiInfo apiInfo() {
    return new ApiInfo("Demo REST API", "Some custom description of API.",
        "0.1",
        "Terms of service",
        new Contact("Airat Galiev", "https://github.com/AiratGaliev", "airat.galiev@gmail.com"),
        "License of API", "API license URL", Collections.emptyList());
  }
}
