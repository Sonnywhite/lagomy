/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.rating.api;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.pathCall;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;


public interface RatingService extends Service {

  /**
   * Example:
   * http://localhost:9000/api/rating/get/Daniel
   */
  ServiceCall<NotUsed, String> getRating(String sellerName);

  /**
   * Example: 
   * http://localhost:9000/api/rating/rate/Daniel
   * "Content-Type: application/json" 
   * {"newRating":"5"}
   */
  ServiceCall<Rating, Done> rateSeller(String sellerName);

  @Override
  default Descriptor descriptor() {
    // @formatter:off
    return named("ratingservice").withCalls(
        pathCall("/api/rating/get/:sellerName",  this::getRating),
        pathCall("/api/rating/rate/:sellerName", this::rateSeller)
      ).withAutoAcl(true);
    // @formatter:on
  }
}
