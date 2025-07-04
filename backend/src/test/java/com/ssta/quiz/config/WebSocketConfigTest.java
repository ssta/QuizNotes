/*
 * Copyright (c) 2025. Stephen Stafford <clothcat@gmail.com>
 *
 * This code is licensed under the MIT license.  Please see LICENSE.md for details.
 */
package com.ssta.quiz.config;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.config.SimpleBrokerRegistration;
import org.springframework.web.socket.config.annotation.SockJsServiceRegistration;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;

import static org.mockito.Mockito.*;

class WebSocketConfigTest {

  @Test
  void configureMessageBroker_ShouldConfigureTopicDestinationsAndAppPrefix() {
    // Arrange
    WebSocketConfig config = new WebSocketConfig();
    MessageBrokerRegistry registry = mock(MessageBrokerRegistry.class);
    SimpleBrokerRegistration simpleBrokerRegistration = mock(SimpleBrokerRegistration.class);
    when(registry.enableSimpleBroker("/topic")).thenReturn(simpleBrokerRegistration);
    when(registry.setApplicationDestinationPrefixes("/app")).thenReturn(registry);

    // Act
    config.configureMessageBroker(registry);

    // Assert
    verify(registry).enableSimpleBroker("/topic");
    verify(registry).setApplicationDestinationPrefixes("/app");
  }

  @Test
  void registerStompEndpoints_ShouldRegisterWsEndpointWithCorrectOrigin() {
    // Arrange
    WebSocketConfig config = new WebSocketConfig();
    StompEndpointRegistry registry = mock(StompEndpointRegistry.class);
    StompWebSocketEndpointRegistration registration = mock(StompWebSocketEndpointRegistration.class);
    SockJsServiceRegistration sockJsRegistration = mock(SockJsServiceRegistration.class);

    when(registry.addEndpoint("/ws")).thenReturn(registration);
    when(registration.setAllowedOrigins("http://localhost:5173")).thenReturn(registration);
    when(registration.withSockJS()).thenReturn(sockJsRegistration);

    // Act
    config.registerStompEndpoints(registry);

    // Assert
    verify(registry).addEndpoint("/ws");
    verify(registration).setAllowedOrigins("http://localhost:5173");
    verify(registration).withSockJS();
  }
}
