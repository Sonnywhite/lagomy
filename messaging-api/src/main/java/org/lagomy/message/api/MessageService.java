package org.lagomy.message.api;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;


import static com.lightbend.lagom.javadsl.api.Service.*;
/**
 * Created by spyrosmartel on 2016-10-12.
 */
public interface MessageService extends Service {

    ServiceCall<Message, Done> sendMessage(String receiver);
    ServiceCall<NotUsed, Messages> getAllMessages(String owner);
    ServiceCall<NotUsed, Messages> getMessagesByUsername(String owner,String sender);
    //ServiceCall<RateMessage, Done> invokeRating(String receiver);

    @Override
    default Descriptor descriptor() {
        return named("messages").withCalls(
                pathCall("/messages/:username", this::sendMessage),
                pathCall("/messages/:username", this::getAllMessages),
                pathCall("/messages/:username/:username", this::getMessagesByUsername)
                //pathCall( "/messages/:username", this::invokeRating)
        ).withAutoAcl(true);

    }
}
