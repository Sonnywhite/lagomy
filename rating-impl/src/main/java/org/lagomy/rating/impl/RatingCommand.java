/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.rating.impl;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

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
public interface RatingCommand extends Jsonable {

  
//-----------------------------------------------------------------------------------------------------------------------------
//    RateSeller (Command)
//-----------------------------------------------------------------------------------------------------------------------------
  /**
   * A command to rate a Seller
   * <p>
   * It has a reply type of {@link akka.Done}, which is sent back to the caller
   * when all the events emitted by this command are successfully persisted.
   */
  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class RateSeller implements RatingCommand, CompressedJsonable, PersistentEntity.ReplyType<Done> {
    
    public final int newRating;

    @JsonCreator
    public RateSeller(int newRating) {
      this.newRating = Preconditions.checkNotNull(newRating, "newRating");
    }

    @Override
    public boolean equals(@Nullable Object another) {
      if (this == another)
        return true;
      return another instanceof RateSeller && equalTo((RateSeller) another);
    }

    private boolean equalTo(RateSeller another) {
      return newRating == another.newRating;
    }

    @Override
    public int hashCode() {
      int h = 31;
      h = h * 17 + newRating;
      return h;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper("RateSellerCommand").add("newRating", newRating).toString();
    }
  }
  
  
//-----------------------------------------------------------------------------------------------------------------------------
//    GetRating (Command)
//-----------------------------------------------------------------------------------------------------------------------------
  /**
   * A command to say hello to someone using the current greeting message.
   * <p>
   * The reply type is String, and will contain the message to say to that
   * person.
   */
  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class GetRating implements RatingCommand, PersistentEntity.ReplyType<String> {
    public final String sellerName;

    @JsonCreator
    public GetRating(String sellerName) {
      this.sellerName = Preconditions.checkNotNull(sellerName, "sellerName");
    }

    @Override
    public boolean equals(@Nullable Object another) {
      if (this == another)
        return true;
      return another instanceof GetRating && equalTo((GetRating) another);
    }

    private boolean equalTo(GetRating another) {
      return sellerName.equals(another.sellerName);
    }

    @Override
    public int hashCode() {
      int h = 31;
      h = h * 17 + sellerName.hashCode();
      return h;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper("Hello").add("name", sellerName).toString();
    }
  }

}
