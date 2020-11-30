package com.github.airatgaliev.restfulwebservices.controller;

import com.github.airatgaliev.restfulwebservices.versioning.Name;
import com.github.airatgaliev.restfulwebservices.versioning.PersonV1;
import com.github.airatgaliev.restfulwebservices.versioning.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

  @GetMapping("v1/person")
  public PersonV1 personV1() {
    return new PersonV1("Airat Galiev");
  }

  @GetMapping("v2/person")
  public PersonV2 personV2() {
    return new PersonV2(new Name("Airat", "Galiev"));
  }
}
