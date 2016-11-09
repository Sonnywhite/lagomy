/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.selling.impl;

import javax.inject.Inject;

import org.lagomy.productManagement.api.ProductService;
import org.lagomy.rating.api.RatingService;
import org.lagomy.selling.api.SellingService;
import org.lagomy.selling.api.User;
import org.lagomy.selling.impl.SellingCommand.GetInterested;
import org.lagomy.selling.impl.SellingCommand.ShowInterest;
import org.pcollections.PSequence;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import akka.Done;
import akka.NotUsed;

/**
 * Implementation of the HelloService.
 */
public class SellingServiceImpl implements SellingService {

  private final PersistentEntityRegistry persistentEntityRegistry;
  private final ProductService productService;
  private final RatingService ratingService;

  @Inject
  public SellingServiceImpl(PersistentEntityRegistry persistentEntityRegistry, ProductService productService,
      RatingService ratingService) {
    this.persistentEntityRegistry = persistentEntityRegistry;
    this.productService = productService;
    this.ratingService = ratingService;
    persistentEntityRegistry.register(SellingWorld.class);
  }


  @Override
  public ServiceCall<User, Done> showInterest(String productId) {
    return request -> {
      // Look up the entity for the given ID.
      PersistentEntityRef<SellingCommand> ref = persistentEntityRegistry.refFor(SellingWorld.class, productId);
      // Tell the entity to save the user with interest
      return ref.ask(new ShowInterest(request.userName));
    };
  }

  @Override
  public ServiceCall<NotUsed, PSequence<User>> getAllInterests(String productId) {
    return request -> {
      // Look up the entity for the given ID.
      PersistentEntityRef<SellingCommand> ref = persistentEntityRegistry.refFor(SellingWorld.class, productId);
      // Ask the entity for list of interested user.
      return ref.ask(new GetInterested(productId));
    };
  }

  @Override
  public ServiceCall<User, Done> sellProduct(String productId, String buyerName) {
    return request -> productService.markAsSold(productId).invoke().thenCompose(
        a -> ratingService.orderRating(buyerName, request.userName).invoke());
  }

}
