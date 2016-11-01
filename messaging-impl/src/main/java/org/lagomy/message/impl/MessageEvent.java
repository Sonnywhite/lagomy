/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.message.impl;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.serialization.Jsonable;

public interface MessageEvent extends Jsonable, AggregateEvent<MessageEvent> {

    @Override
    default public AggregateEventTag<MessageEvent> aggregateTag() {
        return MessageEventTag.INSTANCE;
    }

    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    public class MessageCreated implements MessageEvent {
        public final String sender;
        public final String message;
        public final String receiver;

        @JsonCreator
        public MessageCreated(String sender, String message, String receiver) {
            this.sender = Preconditions.checkNotNull(sender, "sender");
            this.message = Preconditions.checkNotNull(message, "message");
            this.receiver = Preconditions.checkNotNull(receiver, "receiver");
        }

        @Override
        public boolean equals(@Nullable Object another) {
            if (this == another)
                return true;
            return another instanceof MessageCreated && equalTo((MessageCreated) another);
        }

        private boolean equalTo(MessageCreated another) {
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
            return MoreObjects.toStringHelper("MessageCreated").add("userId", sender)
                    .add("name", message)
                    .add("receiver", receiver).toString();
        }
    }
}
