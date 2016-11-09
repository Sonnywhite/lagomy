package org.lagomy.userManagement.impl;

import java.time.Instant;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.serialization.Jsonable;

public interface UserEvent extends Jsonable {

  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public class UserCreated implements UserEvent {
    public final String username;
    public final String password;
    public final Instant timestamp;

    public UserCreated(String userId, String name) {
      this(userId, name, Optional.empty());
    }

    @JsonCreator
    private UserCreated(String userId, String name, Optional<Instant> timestamp) {
      this.username = Preconditions.checkNotNull(userId, "username");
      this.password = Preconditions.checkNotNull(name, "password");
      this.timestamp = timestamp.orElseGet(() -> Instant.now());
    }

    @Override
    public boolean equals(@Nullable Object another) {
      if (this == another)
        return true;
      return another instanceof UserCreated && equalTo((UserCreated) another);
    }

    private boolean equalTo(UserCreated another) {
      return username.equals(another.username) && password.equals(another.password) && timestamp.equals(another.timestamp);
    }

    @Override
    public int hashCode() {
      int h = 31;
      h = h * 17 + username.hashCode();
      h = h * 17 + password.hashCode();
      h = h * 17 + timestamp.hashCode();
      return h;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper("UserCreated").add("username", username).add("password", password)
          .add("timestamp", timestamp).toString();
    }
  }

}

