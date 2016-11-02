/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.rating.impl;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import javax.inject.Inject;

import org.lagomy.rating.impl.RatingCommand.*;

import org.lagomy.rating.api.Rating;
import org.lagomy.rating.api.RatingService;

/**
 * Implementation of the HelloService.
 */
public class RatingServiceImpl implements RatingService {

  private final PersistentEntityRegistry persistentEntityRegistry;

  @Inject
  public RatingServiceImpl(PersistentEntityRegistry persistentEntityRegistry) {
    this.persistentEntityRegistry = persistentEntityRegistry;
    persistentEntityRegistry.register(RatingWorld.class);
  }

  @Override
  public ServiceCall<NotUsed, String> getRating(String id) {
    return request -> {
      // Look up the hello world entity for the given ID.
      PersistentEntityRef<RatingCommand> ref = persistentEntityRegistry.refFor(RatingWorld.class, id);
      // Ask the entity the Hello command.
      return ref.ask(new GetRating(id));
    };
  }

  @Override
  public ServiceCall<Rating, Done> rateSeller(String sellerName) {
    return request -> {
      // Look up the hello world entity for the given ID.
      PersistentEntityRef<RatingCommand> ref = persistentEntityRegistry.refFor(RatingWorld.class, sellerName);
      // Tell the entity to use the greeting message specified.
      return ref.ask(new RateSeller(request.newRating));
    };

  }

}
