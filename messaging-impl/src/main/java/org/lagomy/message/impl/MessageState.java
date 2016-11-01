/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.message.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.serialization.Jsonable;
import org.lagomy.message.api.Message;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Optional;

@SuppressWarnings("serial")
@Immutable
@JsonDeserialize
public final class MessageState implements Jsonable {

    public final Optional<Message> message;

    @JsonCreator
    public MessageState(Optional<Message> message) {
        this.message = Preconditions.checkNotNull(message, "message");
    }

//  public FriendState addFriend(String friendUserId) {
//    if (!user.isPresent())
//      throw new IllegalStateException("friend can't be added before user is created");
//    PSequence<String> newFriends = user.get().friends.plus(friendUserId);
//    return new FriendState(Optional.of(new User(user.get().userId, user.get().name, Optional.of(newFriends))));
//  }

    @Override
    public boolean equals(@Nullable Object another) {
        if (this == another)
            return true;
        return another instanceof MessageState && equalTo((MessageState) another);
    }

    private boolean equalTo(MessageState another) {
        return message.equals(another.message);
    }

    @Override
    public int hashCode() {
        int h = 31;
        h = h * 17 + message.hashCode();
        return h;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("MessageState").add("message", message).toString();
    }
}
