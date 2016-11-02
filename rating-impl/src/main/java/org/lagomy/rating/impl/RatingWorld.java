/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.rating.impl;

import java.util.Optional;

import org.lagomy.rating.impl.RatingCommand.GetRating;
import org.lagomy.rating.impl.RatingCommand.RateSeller;
import org.lagomy.rating.impl.RatingEvent.SellerRated;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import akka.Done;


public class RatingWorld extends PersistentEntity<RatingCommand, RatingEvent, RatingState> {


  @Override
  public Behavior initialBehavior(Optional<RatingState> snapshotState) {

    
    BehaviorBuilder b = newBehaviorBuilder(
        snapshotState.orElse(new RatingState(0,0)));

    /*
     * Command handler for the UseGreetingMessage command.
     */
    b.setCommandHandler(RateSeller.class, (cmd, ctx) ->
    // In response to this command, we want to first persist it as a
    // GreetingMessageChanged event
    ctx.thenPersist(new SellerRated(cmd.newRating),
        // Then once the event is successfully persisted, we respond with done.
        evt -> ctx.reply(Done.getInstance())));

    /*
     * Event handler for the GreetingMessageChanged event.
     */
    b.setEventHandler(SellerRated.class,
        // We simply update the current state to use the greeting message from
        // the event.
        evt -> state().withAddedNewRating(evt.newRating));

    /*
     * Command handler for the Hello command.
     */
    b.setReadOnlyCommandHandler(GetRating.class,
        // Get the greeting from the current state, and prepend it to the name
        // that we're sending
        // a greeting to, and reply with that message.
        (cmd, ctx) -> ctx.reply(cmd.sellerName + ": " + state().getRating()));

    /*
     * We've defined all our behaviour, so build and return it.
     */
    return b.build();
  }

}
