/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.selling.impl;

import java.util.Optional;

import org.lagomy.selling.api.User;
import org.lagomy.selling.impl.SellingCommand.GetInterested;
import org.lagomy.selling.impl.SellingCommand.ShowInterest;
import org.lagomy.selling.impl.SellingEvent.InterestShowed;
import org.pcollections.TreePVector;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import akka.Done;


public class SellingWorld extends PersistentEntity<SellingCommand, SellingEvent, SellingState> {


  @Override
  public Behavior initialBehavior(Optional<SellingState> snapshotState) {

    
    BehaviorBuilder b = newBehaviorBuilder(
        snapshotState.orElse(new SellingState(TreePVector.empty())));

    //--------------------------------------------------------------------------
    //    ShowInterest (Command)
    //--------------------------------------------------------------------------
    b.setCommandHandler(ShowInterest.class, (cmd, ctx) ->
        // In response to this command, we want to first persist it as a
        // GreetingMessageChanged event
        ctx.thenPersist(new InterestShowed(cmd.userName),
        // Then once the event is successfully persisted, we respond with done.
        evt -> ctx.reply(Done.getInstance())));

    //--------------------------------------------------------------------------
    //    InterestShowed (Event)
    //--------------------------------------------------------------------------
    b.setEventHandler(InterestShowed.class,
        // We simply update the current state to save also the new User
        evt -> state().withNewInterested(new User(evt.userName)));

    //--------------------------------------------------------------------------
    //    GetInterested (Command)
    //--------------------------------------------------------------------------
    b.setReadOnlyCommandHandler(GetInterested.class,
        (cmd, ctx) -> ctx.reply(state().getUserWithInterest()));

    /*
     * We've defined all our behaviour, so build and return it.
     */
    return b.build();
  }

}
