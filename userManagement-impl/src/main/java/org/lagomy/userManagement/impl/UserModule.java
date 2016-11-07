package org.lagomy.userManagement.impl;

import com.google.inject.AbstractModule;
import org.lagomy.userManagement.api.UserService;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class UserModule extends AbstractModule implements ServiceGuiceSupport {
  @Override
  protected void configure() {
    bindServices(serviceBinding(UserService.class, UserServiceImpl.class));
  }
}
