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

@SuppressWarnings("serial")
@Immutable
@JsonDeserialize
public final class MessageState implements Jsonable {

    public final Message message;
    public final String timestamp;

    @JsonCreator
    public MessageState(Message message, String timestamp) {
        this.message = Preconditions.checkNotNull(message, "message");
        this.timestamp = Preconditions.checkNotNull(timestamp, "timestamp");

    }

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
        h = h * 17 + timestamp.hashCode();
        return h;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("MessageState").add("message", message).add("timestamp", timestamp).toString();
    }
}
