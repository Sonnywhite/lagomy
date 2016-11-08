package org.lagomy.userManagement.impl;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.serialization.Jsonable;


import org.lagomy.userManagement.api.User;

@SuppressWarnings("serial")
@Immutable
@JsonDeserialize
public final class UserState implements Jsonable {

  public final User user;

  @JsonCreator
  public UserState(User user) {
    this.user = Preconditions.checkNotNull(user, "user");
  }


  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another)
      return true;
    return another instanceof UserState && equalTo((UserState) another);
  }

  private boolean equalTo(UserState another) {
    return user.equals(another.user);
  }

  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + user.hashCode();
    return h;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper("UserState").add("user", user).toString();
  }
}

