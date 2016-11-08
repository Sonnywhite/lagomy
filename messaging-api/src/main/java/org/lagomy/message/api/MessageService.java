/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.message.api;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;

import static com.lightbend.lagom.javadsl.api.Service.*;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import org.pcollections.PSequence;

import static com.lightbend.lagom.javadsl.api.Service.named;

/**
 * The friend service.
 */
public interface MessageService extends Service {


    ServiceCall<Message, Done> sendMessage(String receiver);
    ServiceCall<NotUsed, PSequence<Message>> getAllMessages(String owner);
//    ServiceCall<NotUsed, Message> getMessage(String sender);
    //ServiceCall<RateMessage, Done> invokeRating(String receiver);

    @Override
    default Descriptor descriptor() {
        return named("messages").withCalls(
//                pathCall("/messages/:sender", this::getMessage),
                pathCall("/messages/:receiver", this::sendMessage),
                pathCall("/messages/:owner", this::getAllMessages)
                //pathCall( "/messages/:username", this::invokeRating)
        ).withAutoAcl(true);

    }
}
