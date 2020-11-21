package com.github.airatgaliev.restfulwebservices.model;

public class HelloWorld {

  private String message;

  public HelloWorld(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
