/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.selling.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import org.lagomy.selling.api.SellingService;

/**
 * The module that binds the HelloService so that it can be served.
 */
public class SellingServiceModule extends AbstractModule implements ServiceGuiceSupport {
  @Override
  protected void configure() {
    bindServices(serviceBinding(SellingService.class, SellingServiceImpl.class));
  }
}
