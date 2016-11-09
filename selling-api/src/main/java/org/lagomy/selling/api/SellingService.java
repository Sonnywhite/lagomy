/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.selling.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

import org.pcollections.PSequence;

import akka.Done;
import akka.NotUsed;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;


public interface SellingService extends Service {

  /**
   * Example: 
   * http://localhost:9000/api/selling/interest/show/1  <- productId
   * "Content-Type: application/json" 
   * {"userName":"Daniel"}  <- person that is interested
   */
  ServiceCall<User, Done> showInterest(String productId);
  
  /**
   * Example:
   * http://localhost:9000/api/selling/interest/get/1  <- productId
   */
  ServiceCall<NotUsed, PSequence<User>> getAllInterests(String productId);
  
  /**
   * Example: 
   * http://localhost:9000/api/selling/sell/1/Matthias  <- productId / buyerName
   * "Content-Type: application/json" 
   * {"userName":"Daniel"}  <- ownerName
   */
  ServiceCall<User, Done> sellProduct(String productId, String buyerName);

  @Override
  default Descriptor descriptor() {
    // @formatter:off
    return named("sellingservice").withCalls(
        pathCall("/api/selling/interest/show/:productId", this::showInterest),
        pathCall("/api/selling/interest/get/:productId", this::getAllInterests),
        pathCall("/api/selling/sell/:productId/:buyerName",  this::sellProduct)
      ).withAutoAcl(true);
    // @formatter:on
  }
}
