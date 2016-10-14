/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.productManagement.impl;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import java.util.Optional;

import javax.inject.Inject;

import org.lagomy.productManagement.impl.ProductCommand.*;

import org.lagomy.productManagement.api.TheMessage;
import org.lagomy.productManagement.api.ProductData;
import org.lagomy.productManagement.api.ProductService;

/**
 * Implementation of the HelloService.
 */
public class ProductServiceImpl implements ProductService {

  private final PersistentEntityRegistry persistentEntityRegistry;

  @Inject
  public ProductServiceImpl(PersistentEntityRegistry persistentEntityRegistry) {
    this.persistentEntityRegistry = persistentEntityRegistry;
    persistentEntityRegistry.register(ProductWorld.class);
  }

  @Override
  public ServiceCall<NotUsed, String> hello(String id) {
    return request -> {
      // Look up the hello world entity for the given ID.
      PersistentEntityRef<ProductCommand> ref = persistentEntityRegistry.refFor(ProductWorld.class, id);
      // Ask the entity the Hello command.
      return ref.ask(new Hello("<<" + id + ">>", Optional.empty()));
    };
  }

  @Override
  public ServiceCall<TheMessage, Done> useGreeting(String id) {
    return request -> {
      // Look up the hello world entity for the given ID.
      PersistentEntityRef<ProductCommand> ref = persistentEntityRegistry.refFor(ProductWorld.class, id);
      // Tell the entity to use the greeting message specified.
      return ref.ask(new UseGreetingMessage(request.message));
    };

  }

    @Override
    public ServiceCall<ProductData, Done> usePassPhrase(String id) {
    	return request -> {
          // Look up the hello world entity for the given ID.
          PersistentEntityRef<ProductCommand> ref = persistentEntityRegistry.refFor(ProductWorld.class, id);
          // Tell the entity to use the greeting message specified.
          return ref.ask(new ChangePassPhraseCommand(request.message, request.phrase));
        };
    }

}
