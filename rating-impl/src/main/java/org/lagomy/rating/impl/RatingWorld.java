/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.rating.impl;

import java.util.Optional;

import org.lagomy.rating.impl.RatingCommand.GetRating;
import org.lagomy.rating.impl.RatingCommand.GetRatingOrders;
import org.lagomy.rating.impl.RatingCommand.OrderRating;
import org.lagomy.rating.impl.RatingCommand.RateSeller;
import org.lagomy.rating.impl.RatingEvent.RatingOrdered;
import org.lagomy.rating.impl.RatingEvent.SellerRated;
import org.pcollections.TreePVector;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import akka.Done;


public class RatingWorld extends PersistentEntity<RatingCommand, RatingEvent, RatingState> {


  @Override
  public Behavior initialBehavior(Optional<RatingState> snapshotState) {

    
    BehaviorBuilder b = newBehaviorBuilder(
        snapshotState.orElse(new RatingState(0,0, TreePVector.empty())));

    //--------------------------------------------------------------------------
    //    RateSeller (Command)
    //--------------------------------------------------------------------------
    b.setCommandHandler(RateSeller.class, (cmd, ctx) ->
        // In response to this command, we want to first persist it as a
        // GreetingMessageChanged event
        ctx.thenPersist(new SellerRated(cmd.newRating),
        // Then once the event is successfully persisted, we respond with done.
        evt -> ctx.reply(Done.getInstance())));

    //--------------------------------------------------------------------------
    //    SellerRated (Event)
    //--------------------------------------------------------------------------
    b.setEventHandler(SellerRated.class,
        // We simply update the current state to use the greeting message from
        // the event.
        evt -> state().withAddedNewRating(evt.newRating));

    //--------------------------------------------------------------------------
    //    GetRating (Command)
    //--------------------------------------------------------------------------
    b.setReadOnlyCommandHandler(GetRating.class,
        (cmd, ctx) -> ctx.reply(cmd.sellerName + ": " + state().getRating()));
    

    //--------------------------------------------------------------------------
    //    OrderRating (Command)
    //--------------------------------------------------------------------------
    b.setCommandHandler(OrderRating.class, (cmd, ctx) ->
        // In response to this command, we want to first persist it
        ctx.thenPersist(new RatingOrdered(cmd.sellerName),
        // Then once the event is successfully persisted, we respond with done.
        evt -> ctx.reply(Done.getInstance())));

    //--------------------------------------------------------------------------
    //    RatingOrdered (Event)
    //--------------------------------------------------------------------------
    b.setEventHandler(RatingOrdered.class,
        // We simply update the current state 
        evt -> state().withRatingOrder(evt.sellerName));


    //--------------------------------------------------------------------------
    //    GetRatingOrders (Command)
    //--------------------------------------------------------------------------
    b.setReadOnlyCommandHandler(GetRatingOrders.class,
        (cmd, ctx) -> ctx.reply(state().getRateOrders()));

    /*
     * We've defined all our behaviour, so build and return it.
     */
    return b.build();
  }

}
