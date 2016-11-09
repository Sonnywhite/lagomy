/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.selling.impl;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.lagomy.selling.api.User;
import org.pcollections.PSequence;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;

import akka.Done;

/**
 * This interface defines all the commands that the HelloWorld entity supports.
 * 
 * By convention, the commands should be inner classes of the interface, which
 * makes it simple to get a complete picture of what commands an entity
 * supports.
 */
public interface SellingCommand extends Jsonable {

  
//-----------------------------------------------------------------------------------------------------------------------------
//    ShowInterest (Command)
//-----------------------------------------------------------------------------------------------------------------------------
  /**
   * A command to showInterest
   * <p>
   * It has a reply type of {@link akka.Done}, which is sent back to the caller
   * when all the events emitted by this command are successfully persisted.
   */
  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class ShowInterest implements SellingCommand, CompressedJsonable, PersistentEntity.ReplyType<Done> {
    
    public final String userName;

    @JsonCreator
    public ShowInterest(String userName) {
      this.userName = Preconditions.checkNotNull(userName, "userName");
    }

    @Override
    public boolean equals(@Nullable Object another) {
      if (this == another)
        return true;
      return another instanceof ShowInterest && equalTo((ShowInterest) another);
    }

    private boolean equalTo(ShowInterest another) {
      return userName.equals(another.userName);
    }

    @Override
    public int hashCode() {
      int h = 31;
      h = h * 17 + userName.hashCode();
      return h;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper("ShowInterestCommand").add("userName", userName).toString();
    }
  }
  
  
//-----------------------------------------------------------------------------------------------------------------------------
//    GetIntersted (Command)
//-----------------------------------------------------------------------------------------------------------------------------
  /**
   * A command to say retrieve the list of intersted people
   * <p>
   * The reply type is String, and will contain the message to say to that
   * person.
   */
  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class GetInterested implements SellingCommand, PersistentEntity.ReplyType<PSequence<User>> {
    
    public final String productId;

    @JsonCreator
    public GetInterested(String productId) {
      this.productId = Preconditions.checkNotNull(productId, "productId");
    }

    @Override
    public boolean equals(@Nullable Object another) {
      if (this == another)
        return true;
      return another instanceof GetInterested && equalTo((GetInterested) another);
    }

    private boolean equalTo(GetInterested another) {
      return productId.equals(another.productId);
    }

    @Override
    public int hashCode() {
      int h = 31;
      h = h * 17 + productId.hashCode();
      return h;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper("GetIntersted").add("productId", productId).toString();
    }
  }

}
