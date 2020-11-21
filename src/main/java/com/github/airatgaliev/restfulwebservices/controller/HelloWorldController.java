package com.github.airatgaliev.restfulwebservices.controller;

import com.github.airatgaliev.restfulwebservices.model.HelloWorld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

  @Autowired
  private MessageSource messageSource;

  @GetMapping(path = "/hello-world")
  public String helloWorld() {
    return "Hello World";
  }

  @GetMapping(path = "/hello-world-bean")
  public HelloWorld helloWorldBean() {
    return new HelloWorld("Hello World");
  }

  @GetMapping(path = "/hello-world-bean/{name}")
  public HelloWorld helloWorldBeanPathVariable(@PathVariable String name) {
    return new HelloWorld("Hello World, " + name);
  }

  @GetMapping(path = "/hello-world-internalized")
  public String helloWorldInternalized() {
    return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
  }
}