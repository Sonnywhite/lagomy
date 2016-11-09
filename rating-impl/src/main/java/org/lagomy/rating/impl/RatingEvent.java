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
import com.lightbend.lagom.serialization.Jsonable;

/**
 * This interface defines all the events that the HelloWorld entity supports.
 * <p>
 * By convention, the events should be inner classes of the interface, which
 * makes it simple to get a complete picture of what events an entity has.
 */
public interface RatingEvent extends Jsonable {

//-----------------------------------------------------------------------------------------------------------------------------
//  SellerRated (Event)
//-----------------------------------------------------------------------------------------------------------------------------
  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class SellerRated implements RatingEvent {
    public final int newRating;

    @JsonCreator
    public SellerRated(int newRating) {
      this.newRating = Preconditions.checkNotNull(newRating, "newRating");
    }

    @Override
    public boolean equals(@Nullable Object another) {
      if (this == another)
        return true;
      return another instanceof SellerRated && equalTo((SellerRated) another);
    }

    private boolean equalTo(SellerRated another) {
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
      return MoreObjects.toStringHelper("SellerRated").add("newRating", newRating).toString();
    }
  }

//-----------------------------------------------------------------------------------------------------------------------------
//  RatingOrdered (Event)
//-----------------------------------------------------------------------------------------------------------------------------
  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class RatingOrdered implements RatingEvent {
    
    public final String sellerName;

    @JsonCreator
    public RatingOrdered(String sellerName) {
      this.sellerName = Preconditions.checkNotNull(sellerName, "sellerName");
    }

    @Override
    public boolean equals(@Nullable Object another) {
      if (this == another)
        return true;
      return another instanceof RatingOrdered && equalTo((RatingOrdered) another);
    }

    private boolean equalTo(RatingOrdered another) {
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
      return MoreObjects.toStringHelper("RatingOrdered").add("userName", sellerName).toString();
    }
  }
}
