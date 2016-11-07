/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.userManagement.api;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.pcollections.PSequence;
import org.pcollections.TreePVector;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

@Immutable
@JsonDeserialize
public final class User {
  public final String username;
  public final String password;
  
//  public User(String username, String password) {
//    this(username,password);
//  }

  @JsonCreator
  public User(String username, String password) {
    this.username = Preconditions.checkNotNull(username, "username");
    this.password = Preconditions.checkNotNull(password, "password");
    //this.friends = friends.orElse(TreePVector.empty());
  } // { "userId":"saeideh", "name":"saeideh", "friends":["f1","f2",...]}

  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another)
      return true;
    return another instanceof User && equalTo((User) another);
  }

  private boolean equalTo(User another) {
    return username.equals(another.username) && password.equals(another.password);
  }

  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + username.hashCode();
    h = h * 17 + password.hashCode();
    return h;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper("User").add("username", username).add("password", password)
        .toString();
  }
}
