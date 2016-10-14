/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.productManagement.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import org.lagomy.productManagement.api.ProductService;

/**
 * The module that binds the HelloService so that it can be served.
 */
public class ProductServiceModule extends AbstractModule implements ServiceGuiceSupport {
  @Override
  protected void configure() {
    bindServices(serviceBinding(ProductService.class, ProductServiceImpl.class));
  }
}
