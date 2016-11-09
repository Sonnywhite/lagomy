/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.selling.impl;

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
public interface SellingEvent extends Jsonable {

  /**
   * An event that represents a change in greeting message.
   */
  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class InterestShowed implements SellingEvent {
    public final String userName;

    @JsonCreator
    public InterestShowed(String userName) {
      this.userName = Preconditions.checkNotNull(userName, "userName");
    }

    @Override
    public boolean equals(@Nullable Object another) {
      if (this == another)
        return true;
      return another instanceof InterestShowed && equalTo((InterestShowed) another);
    }

    private boolean equalTo(InterestShowed another) {
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
      return MoreObjects.toStringHelper("InterestShowed").add("userName", userName).toString();
    }
  }
}
