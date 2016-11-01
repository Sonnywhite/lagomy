/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.productManagement.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.lagomy.productManagement.api.Product;
import org.lagomy.productManagement.impl.ProductCommand.AddProduct;
import org.lagomy.productManagement.impl.ProductCommand.DeleteProduct;
import org.lagomy.productManagement.impl.ProductCommand.Hello;
import org.lagomy.productManagement.impl.ProductCommand.MarkProduct;
import org.lagomy.productManagement.impl.ProductEvent.ProductAdded;
import org.lagomy.productManagement.impl.ProductEvent.ProductDeleted;
import org.lagomy.productManagement.impl.ProductEvent.ProductMarked;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import akka.Done;

/**
 * This is an event sourced entity.
 */
public class ProductWorld extends PersistentEntity<ProductCommand, ProductEvent, WorldState> {

  /**
   * An entity can define different behaviours for different states, but it will
   * always start with an initial behaviour.
   */
  @Override
  public Behavior initialBehavior(Optional<WorldState> snapshotState) {

    /*
     * Behaviour is defined using a behaviour builder.
     */
    BehaviorBuilder b = newBehaviorBuilder(
        snapshotState.orElse(new WorldState(Product.getDummy(), LocalDateTime.now().toString())));

    
    //--------------------------------------------------------------------------
    //    Hello (Command)
    //--------------------------------------------------------------------------
    /*
     * Command handler for the Hello command.
     */
    b.setReadOnlyCommandHandler(Hello.class,
        // Get the greeting from the current state, and prepend it to the name
        // that we're sending
        // a greeting to, and reply with that message.
        (cmd, ctx) -> ctx.reply(state().product.productName + ", " + cmd.name));
    
    //--------------------------------------------------------------------------
    //    AddProduct (Command)
    //--------------------------------------------------------------------------
    b.setCommandHandler(AddProduct.class, (cmd, ctx) ->
    // In response to this command, we want to first persist it as a
    // PassPhraseChangedEvent
    ctx.thenPersist(new ProductAdded(cmd.product.productId, cmd.product),
        // Then once the event is successfully persisted, we respond with done.
        evt -> ctx.reply(Done.getInstance())));

    //--------------------------------------------------------------------------
    //    ProductAdded (Event)
    //--------------------------------------------------------------------------
    b.setEventHandler(ProductAdded.class,
        // We simply update the current state to use the greeting message from
        // the event.
        evt -> new WorldState(evt.product, LocalDateTime.now().toString()));
    
    //--------------------------------------------------------------------------
    //    DeleteProduct (Command)
    //--------------------------------------------------------------------------
    b.setCommandHandler(DeleteProduct.class, (cmd, ctx) ->
    // In response to this command, we want to first persist it as a
    // PassPhraseChangedEvent
    ctx.thenPersist(new ProductDeleted(cmd.productId),
        // Then once the event is successfully persisted, we respond with done.
        evt -> ctx.reply(Done.getInstance())));

    //--------------------------------------------------------------------------
    //    ProductDeleted (Event)
    //--------------------------------------------------------------------------
    b.setEventHandler(ProductDeleted.class,
        // TODO do we really want to create a delete dummy here?
        // Well, I don't have a better idea yet, as setting product on null might cause some problems
        evt -> new WorldState(Product.getDeletedProductDummy(), LocalDateTime.now().toString()));
    
    //--------------------------------------------------------------------------
    //    MarkProduct (Command)
    //--------------------------------------------------------------------------
    b.setCommandHandler(MarkProduct.class, (cmd, ctx) ->
    // In response to this command, we want to first persist it as a
    // PassPhraseChangedEvent
    ctx.thenPersist(new ProductMarked(cmd.productId),
        // Then once the event is successfully persisted, we respond with done.
        evt -> ctx.reply(Done.getInstance())));

    //--------------------------------------------------------------------------
    //    ProductMarekd (Event)
    //--------------------------------------------------------------------------
    b.setEventHandler(ProductMarked.class,
        evt -> new WorldState(state().product.asSold(), LocalDateTime.now().toString()));

    
    //--------------------------------------------------------------------------
    /*
     * We've defined all our behaviour, so build and return it.
     */
    //--------------------------------------------------------------------------
    return b.build();
  }

}
