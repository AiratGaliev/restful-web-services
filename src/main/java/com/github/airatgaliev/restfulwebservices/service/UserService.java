package com.github.airatgaliev.restfulwebservices.service;

import com.github.airatgaliev.restfulwebservices.model.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static List<User> users = new ArrayList<>();

  static {
    users.add(new User(1, "Airat", new Date()));
    users.add(new User(2, "Endzhe", new Date()));
    users.add(new User(3, "Ainur", new Date()));
  }

  private int usersCount = users.size();

  public List<User> findAll() {
    return users;
  }

  public User save(User user) {
    if (user.getId() == null) {
      user.setId(++usersCount);
    }
    users.add(user);
    return user;
  }

  public User findById(Integer id) {
    for (User user : users) {
      if (user.getId().equals(id)) {
        return user;
      }
    }
    return null;
  }

  public User deleteById(Integer id) {
    Iterator<User> userIterator = users.iterator();
    while (userIterator.hasNext()) {
      User user = userIterator.next();
      if (user.getId().equals(id)) {
        userIterator.remove();
        return user;
      }
    }
    return null;
  }
}
