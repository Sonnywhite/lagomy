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
import org.lagomy.message.api.Message;

public interface MessageEvent extends Jsonable, AggregateEvent<MessageEvent> {


    @Override
    default public AggregateEventTag<MessageEvent> aggregateTag() {
        return MessageEventTag.INSTANCE;
    }

    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    public final class MessageCreated implements MessageEvent {

        public final String messageId;
        public final Message message;



    /*  @Value.Parameter
      String getMessageId();

      @Value.Parameter
      Message getMessage();*/
//    public final String sender;
//    public final String message;

        @JsonCreator
        public MessageCreated(String messageId, Message message) {
            this.messageId = Preconditions.checkNotNull(messageId, "messageId");
            this.message = Preconditions.checkNotNull(message, "message");
        }

        @Override
        public boolean equals(@Nullable Object another) {
            if (this == another)
                return true;
            return another instanceof MessageCreated && equalTo((MessageCreated) another);
        }

        private boolean equalTo(MessageCreated another) {
            return message.equals(another);
        }

        @Override
        public int hashCode() {
            int h = 31;
            h = h * 17 + message.hashCode();
            return h;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper("MessageCreated").add("message", message).toString();
        }
    }
}
