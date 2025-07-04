package com.ssta.quiz.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  private UUID id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "twitch_id", unique = true)
  private String twitchId;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "created_at")
  private ZonedDateTime createdAt;

  @Column(name = "updated_at")
  private ZonedDateTime updatedAt;
}
