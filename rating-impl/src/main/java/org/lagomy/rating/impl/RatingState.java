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
import com.lightbend.lagom.serialization.CompressedJsonable;

/**
 * The state for the {@link RatingWorld} entity.
 */
@SuppressWarnings("serial")
@Immutable
@JsonDeserialize
public final class RatingState implements CompressedJsonable {

  public final int ratingSum;
  public final int ratingCount;

  @JsonCreator
  public RatingState(int ratingSum, int ratingCount) {
    this.ratingSum = Preconditions.checkNotNull(ratingSum, "ratingSum");
    this.ratingCount = Preconditions.checkNotNull(ratingCount, "ratingCount");
  }
  
  /**
   * creates a new RatingState, by adding the new rating to the existing rating sum
   * and increasing the ratingCounter by one
   * @param newRating the newly added rating
   * @return
   */
  public RatingState withAddedNewRating(int newRating){
    return new RatingState(ratingSum + newRating, ratingCount + 1);
  }
  
  public double getRating(){
    return (ratingCount < 1) ? -1 : (ratingSum*1.0/ratingCount);
  }

  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another)
      return true;
    return another instanceof RatingState && equalTo((RatingState) another);
  }

  private boolean equalTo(RatingState another) {
    return ratingSum == another.ratingSum && ratingCount == another.ratingCount;
  }

  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + ratingSum;
    h = h * 17 + ratingCount;
    return h;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper("WorldState").add("ratingSum", ratingSum).add("ratingCount", ratingCount).toString();
  }
}
