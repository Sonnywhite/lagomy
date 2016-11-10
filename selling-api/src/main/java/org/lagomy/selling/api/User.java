package org.lagomy.selling.api;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

@Immutable
@JsonDeserialize
public final class User {

  public final String userName;

  @JsonCreator
  public User(String userName) {
    this.userName = Preconditions.checkNotNull(userName, "userName");
  }

  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another)
      return true;
    return another instanceof User && equalTo((User) another);
  }

  private boolean equalTo(User another) {
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
    return MoreObjects.toStringHelper("User").add("userName", userName).toString();
  }
}
