package org.lagomy.message.api;

/**
 * Created by spyrosmartel on 2016-10-12.
 */

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

@Immutable
@JsonDeserialize
public final class Message{

    public final String source;
    public final String message;
    public final String receiver;


    @JsonCreator
    public Message(String source, String message, String receiver){
        this.source = Preconditions.checkNotNull(source, "source");
        this.message = Preconditions.checkNotNull(message, "message");
        this.receiver = Preconditions.checkNotNull(receiver, "receiver");
    }

    @Override
    public boolean equals(@Nullable Object another) {
        if(this==another){
            return true;

        }return false;
    }


    private boolean equalTo(Message another) {
        return source.equals(another.source) && message.equals(another.message);
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
    public String toString(){
        return MoreObjects.toStringHelper("Message")
                .add("source", source)
                .add("message", message)
                .add("receiver", receiver)
                .toString();
    }
}
