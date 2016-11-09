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
import com.lightbend.lagom.serialization.CompressedJsonable;

/**
 * The state for the {@link SellingWorld} entity.
 */
@SuppressWarnings("serial")
@Immutable
@JsonDeserialize
public final class SellingState implements CompressedJsonable {

  private final PSequence<User> userWithInterest;

  public PSequence<User> getUserWithInterest() {
    return userWithInterest;
  }

  @JsonCreator
  public SellingState(PSequence<User> userWithInterest) {
    this.userWithInterest = Preconditions.checkNotNull(userWithInterest, "userWithInterest");
  }
  
  /**
   * creates a new SellingState, by adding the new user to the list
   * @param newRating the newly added rating
   * @return
   */
  public SellingState withNewInterested(User user){
    return new SellingState(userWithInterest.plus(user));
  }
  

  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another)
      return true;
    return another instanceof SellingState && equalTo((SellingState) another);
  }

  private boolean equalTo(SellingState another) {
    return userWithInterest.equals(another.userWithInterest);
  }

  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + userWithInterest.hashCode();
    return h;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper("SellingState").add("userWithInterest", userWithInterest).toString();
  }
}
