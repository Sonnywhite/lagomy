/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.rating.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import org.lagomy.rating.api.RatingService;

/**
 * The module that binds the HelloService so that it can be served.
 */
public class RatingServiceModule extends AbstractModule implements ServiceGuiceSupport {
  @Override
  protected void configure() {
    bindServices(serviceBinding(RatingService.class, RatingServiceImpl.class));
  }
}
