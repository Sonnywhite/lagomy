package org.lagomy.message.api;/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
@JsonDeserialize
public final class Message {
    public final String sender;
    public final String message;
    public final String receiver;

    @JsonCreator
    public Message(String sender, String message, String receiver) {
        this.sender = Preconditions.checkNotNull(sender, "sender");
        this.message = Preconditions.checkNotNull(message, "message");
        this.receiver = Preconditions.checkNotNull(receiver, "receiver");
    }




    @Override
    public boolean equals(@Nullable Object another) {
        if (this == another)
            return true;
        return another instanceof Message && equalTo((Message) another);
    }

    private boolean equalTo(Message another) {
        return sender.equals(another.sender) && message.equals(another.message) && receiver.equals(another.receiver);
    }

    @Override
    public int hashCode() {
        int h = 31;
        h = h * 17 + sender.hashCode();
        h = h * 17 + message.hashCode();
        h = h * 17 + receiver.hashCode();
        return h;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("Message").add("sender", sender).add("message", message).add("receiver", receiver)
                .toString();
    }
}
