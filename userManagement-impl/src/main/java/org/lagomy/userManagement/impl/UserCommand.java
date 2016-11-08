package org.lagomy.userManagement.impl;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;

import akka.Done;

import org.lagomy.userManagement.api.User;


public interface UserCommand extends Jsonable {
  
  
//-----------------------------------------------------------------------------------------------------------------------------
//    CreateUser (Command)
//-----------------------------------------------------------------------------------------------------------------------------
  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class CreateUser implements UserCommand, PersistentEntity.ReplyType<Done> {
    
    public final User user;

    @JsonCreator
    public CreateUser(User user) {
      this.user = Preconditions.checkNotNull(user, "user");
    }

    @Override
    public boolean equals(@Nullable Object another) {
      if (this == another)
        return true;
      return another instanceof CreateUser && equalTo((CreateUser) another);
    }

    private boolean equalTo(CreateUser another) {
      return user == another.user;
    }

    @Override
    public int hashCode() {
      int h = 31;
      h = h * 17 + user.hashCode();
      return h;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper("CreateUser").add("user", user).toString();
    }
  }

  
//-----------------------------------------------------------------------------------------------------------------------------
//   CheckLogin(Command)
//-----------------------------------------------------------------------------------------------------------------------------
  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class CheckLogin implements UserCommand,PersistentEntity.ReplyType<Boolean> {
    
    public final User user;

    @JsonCreator
    public CheckLogin(User user) {
      this.user = Preconditions.checkNotNull(user, "user");
    }

    @Override
    public boolean equals(@Nullable Object another) {
      if (this == another)
        return true;
      return another instanceof CheckLogin && equalTo((CheckLogin) another);
    }

    private boolean equalTo(CheckLogin another) {
      return user == another.user;
    }

    @Override
    public int hashCode() {
      int h = 31;
      h = h * 17 + user.hashCode();
      return h;
    }

    @Override
    public String toString() {
      return "GetUser{}";
    }
  }

  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class GetUserReply implements Jsonable {
    public final Optional<User> user;

    @JsonCreator
    public GetUserReply(Optional<User> user) {
      this.user = Preconditions.checkNotNull(user, "user");
    }

    @Override
    public boolean equals(@Nullable Object another) {
      if (this == another)
        return true;
      return another instanceof GetUserReply && equalTo((GetUserReply) another);
    }

    private boolean equalTo(GetUserReply another) {
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
      return MoreObjects.toStringHelper("GetUserReply").add("user", user).toString();
    }
  }


}

