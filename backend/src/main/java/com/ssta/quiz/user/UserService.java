/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */

package com.ssta.quiz.user;

import java.util.Optional;

public interface UserService {

  User createUser(User user);

  Optional<User> findUserById(long id);

  Optional<User> findUserByUsername(String username);

  Optional<User> findUserByTwitchId(String twitchId);

  Optional<User> findUserByEmail(String email);

  User updateUser(User user);

  void deleteUser(long id);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
