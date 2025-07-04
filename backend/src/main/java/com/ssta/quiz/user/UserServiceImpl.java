package com.ssta.quiz.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public User createUser(User user) {
    if (user.getId() == null) {
      user.setId(UUID.randomUUID());
    }

    ZonedDateTime now = ZonedDateTime.now();
    if (user.getCreatedAt() == null) {
      user.setCreatedAt(now);
    }
    user.setUpdatedAt(now);

    return userRepository.save(user);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<User> findUserById(UUID id) {
    return userRepository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<User> findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<User> findUserByTwitchId(String twitchId) {
    return userRepository.findByTwitchId(twitchId);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<User> findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  @Transactional
  public User updateUser(User user) {
    user.setUpdatedAt(ZonedDateTime.now());
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public void deleteUser(UUID id) {
    userRepository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }
}
