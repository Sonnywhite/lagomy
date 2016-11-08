/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.userManagement.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.namedCall;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;
import org.pcollections.PSequence;

/**
 * The friend service.
 */
public interface UserService extends Service {

  /**
   * Service call for getting a user.
   *
   * The ID of this service call is the user name, and the response message is the User object.
   */
  ServiceCall<NotUsed, User> getUser(String username, String password);

  /**
   * Service call for creating a user.
   *
   * The request message is the User to create.
   */
  ServiceCall<User, NotUsed> createUser();


  @Override
  default Descriptor descriptor() {
    // @formatter:off
    return named("friendservice").withCalls(
        pathCall("/api/users/:username/:password", this::getUser),
        namedCall("/api/users", this::createUser)
      ).withAutoAcl(true);
    // @formatter:on
  }
}
