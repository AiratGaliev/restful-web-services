package com.github.airatgaliev.restfulwebservices.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.airatgaliev.restfulwebservices.exception.UserNotFoundException;
import com.github.airatgaliev.restfulwebservices.model.User;
import com.github.airatgaliev.restfulwebservices.service.UserService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/users")
  public List<User> retrieveAllUsers() {
    return userService.findAll();
  }

  @GetMapping("/users/{id}")
  public EntityModel<User> retrieveUser(@PathVariable Integer id) {
    User user = userService.findById(id);
    if (user == null) {
      throw new UserNotFoundException("Don't found user by id: " + id);
    }
    EntityModel<User> userEntityModel = new EntityModel<>(user);
    WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
    userEntityModel.add(link.withRel("all-users"));
    return userEntityModel;
  }

  @PostMapping("/users")
  public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
    User savedUser = userService.save(user);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedUser.getId())
        .toUri();
    return ResponseEntity.created(uri).build();
  }

  @DeleteMapping("/users/{id}")
  public void deleteUser(@PathVariable Integer id) {
    User user = userService.deleteById(id);
    if (user == null) {
      throw new UserNotFoundException("Don't found user by id: " + id);
    }
  }
}
