/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.productManagement.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

import org.pcollections.PSequence;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;

/**
 * The hello service interface.
 * <p>
 * This describes everything that Lagom needs to know about how to serve and
 * consume the HelloService.
 */
public interface ProductService extends Service {

  /**
   * Example: http://localhost:9000/api/product/greet/Daniel
   */
  ServiceCall<NotUsed, String> hello(String id);

  /**
   * Example:
   * http://localhost:9000/api/product/add
   * Content-Type : application/json
   * {"itemId": "Daniel", "itemName" : "Cake", "itemDescription" : "Choko"}
   */
  ServiceCall<Product, Done> addProduct();

  /**
   * Example:
   * http://localhost:9000/api/product/get/all
   */
  ServiceCall<NotUsed, PSequence<Product>> getAllProducts();
  //ServiceCall<NotUsed, NotUsed, PSequence<Cargo>> getAllRegistrations();
  

  @Override
  default Descriptor descriptor() {
    // @formatter:off
    return named("productService").withCalls(
        pathCall("/api/product/greet/:id",  this::hello),
        pathCall("/api/product/add", this::addProduct),
        pathCall("/api/product/get/all", this::getAllProducts)
      ).withAutoAcl(true);
    // @formatter:on
  }
}
