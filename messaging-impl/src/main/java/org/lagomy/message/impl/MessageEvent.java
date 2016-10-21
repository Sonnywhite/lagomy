package org.lagomy.message.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.serialization.Jsonable;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * Created by Seva Meyer on 2016-10-18.
 */
public interface MessageEvent extends Jsonable, AggregateEvent<MessageEvent>{


    @Override
    public default AggregateEventTag<MessageEvent> aggregateTag() {
        return null;
    }

    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    public class MessageSent implements MessageEvent {

        public final String source;
        public final String message;
        public final String receiver;

        @JsonCreator
        public MessageSent(String source, String message, String receiver){
            this.source = Preconditions.checkNotNull(source, "source");
            this.message = Preconditions.checkNotNull(message, "message");
            this.receiver = Preconditions.checkNotNull(receiver, "receiver");
        }

        @Override
        public boolean equals(@Nullable Object another) {
            if (this == another)
                return true;
            return another instanceof MessageSent && equalTo((MessageSent) another);
        }

        private boolean equalTo(MessageSent another) {
            return source.equals(another.source) && message.equals(another.message) && receiver.equals(another.receiver);
        }

        @Override
        public int hashCode() {
            int h = 31;
            h = h * 17 + source.hashCode();
            h = h * 17 + message.hashCode();
            h = h * 17 + receiver.hashCode();
            return h;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper("MessageSent").add("source", source).add("message", message)
                    .add("receiver", receiver).toString();
        }
    }
}
