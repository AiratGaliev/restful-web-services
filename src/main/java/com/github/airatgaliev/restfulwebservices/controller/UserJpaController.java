package com.github.airatgaliev.restfulwebservices.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.github.airatgaliev.restfulwebservices.exception.UserNotFoundException;
import com.github.airatgaliev.restfulwebservices.model.Post;
import com.github.airatgaliev.restfulwebservices.model.User;
import com.github.airatgaliev.restfulwebservices.repository.PostRepository;
import com.github.airatgaliev.restfulwebservices.repository.UserRepository;
import java.net.URI;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PostRepository postRepository;

  @GetMapping("/users")
  public List<User> retrieveAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/users/{id}")
  public EntityModel<User> retrieveUser(@PathVariable Integer id) {
    Optional<User> user = userRepository.findById(id);
    if (!user.isPresent()) {
      throw new UserNotFoundException("Don't found user by id: " + id);
    }
    EntityModel<User> userEntityModel = new EntityModel<>(user.get());
    WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
    userEntityModel.add(link.withRel("all-users"));
    return userEntityModel;
  }

  @PostMapping("/users")
  public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
    User savedUser = userRepository.save(user);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedUser.getId())
        .toUri();
    return ResponseEntity.created(uri).build();
  }

  @DeleteMapping("/users/{id}")
  public void deleteUser(@PathVariable Integer id) {
    Optional<User> user = userRepository.findById(id);
    user.ifPresent(value -> userRepository.delete(value));
    if (!user.isPresent()) {
      throw new UserNotFoundException("Don't found user by id: " + id);
    }
  }

  @GetMapping("/users/{id}/posts")
  public List<Post> retrieveUserPosts(@PathVariable Integer id) {
    Optional<User> user = userRepository.findById(id);
    if (!user.isPresent()) {
      throw new UserNotFoundException("Don't found user by id: " + id);
    }
    return user.get().getPosts();
  }

  @PostMapping("/users/{id}/posts")
  public ResponseEntity<Object> createUser(@PathVariable Integer id, @RequestBody Post post) {
    Optional<User> userOptional = userRepository.findById(id);
    if (!userOptional.isPresent()) {
      throw new UserNotFoundException("Don't found user by id: " + id);
    }
    User user = userOptional.get();
    post.setUser(user);
    postRepository.save(post);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(post.getId())
        .toUri();
    return ResponseEntity.created(uri).build();
  }
}
