package com.ssta.quiz.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable UUID id) {
    return userService.findUserById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
  }

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    if (userService.existsByUsername(user.getUsername())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
    }
    if (user.getEmail() != null && userService.existsByEmail(user.getEmail())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
    }

    User createdUser = userService.createUser(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
    if (!userService.findUserById(id).isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    user.setId(id); // Ensure the ID matches the path variable
    User updatedUser = userService.updateUser(user);
    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
    if (!userService.findUserById(id).isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}
