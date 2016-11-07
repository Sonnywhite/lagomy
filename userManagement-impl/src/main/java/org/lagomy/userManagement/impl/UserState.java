package org.lagomy.userManagement.impl;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.pcollections.PSequence;

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

  public final Optional<User> user;

  @JsonCreator
  public UserState(Optional<User> user) {
    this.user = Preconditions.checkNotNull(user, "user");
  }

//  public UserState addFriend(String friendUserId) {
//    if (!user.isPresent())
//      throw new IllegalStateException("friend can't be added before user is created");
//    //PSequence<String> newFriends = user.get().friends.plus(friendUserId);
//    return new UserState(Optional.of(new User(user.get().userId, user.get().name, Optional.of(newFriends))));
//  }

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

