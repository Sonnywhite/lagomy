/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.message.impl;

import akka.Done;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;
import org.lagomy.message.api.Message;
import org.pcollections.PSequence;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Optional;

public interface MessageCommand extends Jsonable {

    @SuppressWarnings("serial")
    @Immutable
    @JsonDeserialize
    public final class SendMessage implements MessageCommand, PersistentEntity.ReplyType<Done> {

        //    @Value.Parameter
//    Message getMessage();
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
            return MoreObjects.toStringHelper("CreateMessage").add("message", message).toString();
        }
    }

//
//  @SuppressWarnings("serial")
//  @Immutable
//  @JsonDeserialize
//  final class GetMessage implements MessageCommand,PersistentEntity.ReplyType<GetMessageReply> {
//
//    @Override
//    public boolean equals(@Nullable Object another) {
//      return this instanceof GetMessage;
//    }
//
//    @Override
//    public int hashCode() {
//      return 2053226012;
//    }
//
//    @Override
//    public String toString() {
//      return "GetMessage{}";
//    }
//  }
//
//  @SuppressWarnings("serial")
//  @Immutable
//  @JsonDeserialize
//  public final class GetMessageReply implements Jsonable {
//    public final Optional<Message> message;
//
//    @JsonCreator
//    public GetMessageReply(Optional<Message> message) {
//      this.message = Preconditions.checkNotNull(message, "message");
//    }
//
//    @Override
//    public boolean equals(@Nullable Object another) {
//      if (this == another)
//        return true;
//      return another instanceof GetMessageReply && equalTo((GetMessageReply) another);
//    }
//
//    private boolean equalTo(GetMessageReply another) {
//      return message.equals(another.message);
//    }
//
//    @Override
//    public int hashCode() {
//      int h = 31;
//      h = h * 17 + message.hashCode();
//      return h;
//    }
//
//    @Override
//    public String toString() {
//      return MoreObjects.toStringHelper("GetMessageReply").add("message", message).toString();
//    }
//  }



}
