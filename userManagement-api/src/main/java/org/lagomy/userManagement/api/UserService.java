/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.userManagement.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

import akka.Done;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

/**
 * The friend service.
 */
public interface UserService extends Service {

  /**
   * Example: 
   * http://localhost:9000/api/users/create
   * "Content-Type: application/json" 
   * {"username":"Peter", "password":"123"}
   */
  ServiceCall<User, Done> createUser();

  /**
   * Example: 
   * http://localhost:9000/api/users/check
   * "Content-Type: application/json" 
   * {"username":"Peter", "password":"123"} 
   * -> returns true/false
   */
  ServiceCall<User, Boolean> checkLogin();


  @Override
  default Descriptor descriptor() {
    // @formatter:off
    return named("userservice").withCalls(
        pathCall("/api/users/create", this::createUser),
        pathCall("/api/users/check", this::checkLogin)
      ).withAutoAcl(true);
    // @formatter:on
  }
}
