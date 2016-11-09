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
   * {"productId":"nnn",
      "productName":"Cake", 
   "sellerName":"Antonidas", 
      "description":"Choko", 
      "photoPath":"path58", 
      "price":42, 
      "sold":false}
   */
  ServiceCall<Product, Done> addProduct();
  
  /**
   * Example: 
   * http://localhost:9000/api/product/delete/ + productId
   */
  ServiceCall<NotUsed, Done> deleteProduct(String productId);
  
  /**
   * Example: http://localhost:9000/api/product/sell/:productId
   */
  ServiceCall<NotUsed, Done> markAsSold(String productId);

  /**
   * Example:
   * http://localhost:9000/api/product/get/all
   */
  ServiceCall<NotUsed, PSequence<Product>> getAllProducts();
  

  @Override
  default Descriptor descriptor() {
    // @formatter:off
    return named("productService").withCalls(
        pathCall("/api/product/greet/:id",  this::hello),
        pathCall("/api/product/add", this::addProduct),
        pathCall("/api/product/delete/:productId",  this::deleteProduct),
        pathCall("/api/product/sell/:productId",  this::markAsSold),
        pathCall("/api/product/get/all", this::getAllProducts)
      ).withAutoAcl(true);
    // @formatter:on
  }
}
