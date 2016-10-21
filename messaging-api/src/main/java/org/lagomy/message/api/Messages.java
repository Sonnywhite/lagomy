package org.lagomy.message.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.Preconditions;

import javax.annotation.concurrent.Immutable;
import java.util.List;

/**
 * Created by Seva Meyer on 2016-10-13.
 */

@SuppressWarnings("serial")
@Immutable
public class Messages {

    public final List<Message> messages;

    @JsonCreator
    public Messages(List<Message> messages){
        this.messages = Preconditions.checkNotNull(messages, "messages");

    }


}
