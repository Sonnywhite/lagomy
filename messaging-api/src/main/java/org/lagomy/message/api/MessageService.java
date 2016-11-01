package org.lagomy.message.api;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import org.pcollections.PSequence;


import static com.lightbend.lagom.javadsl.api.Service.*;
/**
 * Created by Seva Meyer on 2016-10-12.
 */
public interface MessageService extends Service {

    ServiceCall<Message, Done> sendMessage(String receiver, String token);
    ServiceCall<NotUsed, PSequence<Message>> getAllMessages(String owner, String token);
    ServiceCall<NotUsed, Message> getMessage(String sender);
   // ServiceCall<NotUsed, Messages> getMessagesByUsername(String owner,String sender);
    //ServiceCall<RateMessage, Done> invokeRating(String receiver);

    @Override
    default Descriptor descriptor() {
        return named("messages").withCalls(
                pathCall("/messages/:username/:token", this::sendMessage),
                pathCall("/messages/:username/:token", this::getAllMessages),
                pathCall("/messages/:sender", this::getMessage)
                //pathCall("/messages/:username/:username", this::getMessagesByUsername)
                //pathCall( "/messages/:username", this::invokeRating)
        ).withAutoAcl(true);

    }
}
