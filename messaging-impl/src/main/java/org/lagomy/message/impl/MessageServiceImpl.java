/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.message.impl;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import org.lagomy.message.api.Message;
import org.lagomy.message.api.MessageService;
import org.lagomy.message.impl.MessageCommand.SendMessage;
import org.pcollections.PSequence;
import org.pcollections.TreePVector;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class MessageServiceImpl implements MessageService {

    private final PersistentEntityRegistry persistentEntityRegistry;
    private final CassandraSession db;

    @Inject
    public MessageServiceImpl(PersistentEntityRegistry persistentEntityRegistry, CassandraReadSide readSide,
                              CassandraSession db) {
        this.persistentEntityRegistry = persistentEntityRegistry;
//        this.topics = topics;
        this.db = db;
        persistentEntityRegistry.register(MessageEntity.class);
        readSide.register(MessageEventProcessor.class);
    }



    @Override
    public ServiceCall<Message, Done> sendMessage(String receiver) {
        return request -> {

            PersistentEntityRef<MessageCommand> ref = persistentEntityRegistry.refFor(MessageEntity.class, request.messageId);
            return ref.ask(new SendMessage(request));
            //return messageEntityRef(request.messageId).ask((new SendMessage(request)));

        };
    }

    @Override
    public ServiceCall<NotUsed, PSequence<Message>> getAllMessages(String owner) {

        return (req) -> {
            CompletionStage<PSequence<Message>>
                    result = db.selectAll("SELECT * FROM newmessage")
                    .thenApply(rows -> {
                        List<Message> messageList =
                                rows.stream().map(row ->
                                        new Message(row.getString("messageId"),
                                                row.getString("sender"),
                                                row.getString("message"),
                                                row.getString("receiver")))
                                        .collect(Collectors.toList());
                        return TreePVector.from(messageList);
                    });
            return result;
        };
    }

//    @Override
//    public ServiceCall<NotUsed, Message> getMessage(String sender) {
//        return null;
//    }

//    @Override
//    public ServiceCall<NotUsed, Message> getMessage(String sender) {
//        return request -> {
//          return messageEntityRef(sender).ask(new GetMessage()).thenApply(reply -> {
//            if (reply.message.isPresent())
//              return reply.message.get();
//            else
//              throw new NotFound("message sent by " + sender + " not found");
//          });
//        };
//    }

    /*private PersistentEntityRef<MessageCommand> messageEntityRef(String messageId) {
        PersistentEntityRef<MessageCommand> ref = persistentEntityRegistry.refFor(MessageEntity.class, messageId);
        return ref;
    }*/
}
