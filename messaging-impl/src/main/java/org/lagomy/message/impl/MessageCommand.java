package org.lagomy.message.impl;

import akka.Done;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;
import org.lagomy.message.api.Message;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * Created by Seva Meyer on 2016-10-18.
 */
public interface MessageCommand extends Jsonable{

    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    public final class SendMessage implements MessageCommand, PersistentEntity.ReplyType<Done> {

        public final Message message;


        @JsonCreator
        public SendMessage(Message message) {
            this.message = Preconditions.checkNotNull(message, "message");
        }

        @Override
        public boolean equals(@Nullable Object another) {
            if (this == another)
                return true;
            return another instanceof SendMessage && equalTo((SendMessage) another);
        }

        private boolean equalTo(SendMessage another) {
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
            return MoreObjects.toStringHelper("SendMessage").add("message", message).toString();
        }
    }
}
