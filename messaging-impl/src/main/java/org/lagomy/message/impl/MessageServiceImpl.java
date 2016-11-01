/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.message.impl;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.NotFound;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import org.lagomy.message.api.Message;
import org.lagomy.message.api.MessageService;
import org.pcollections.PSequence;

import javax.inject.Inject;

//import com.lightbend.lagom.javadsl.pubsub.PubSubRegistry;

public class MessageServiceImpl implements MessageService {

    private final PersistentEntityRegistry persistentEntities;
    private final CassandraSession db;
//  private final PubSubRegistry topics;

    @Inject
    public MessageServiceImpl( PersistentEntityRegistry persistentEntities, CassandraReadSide readSide,
                               CassandraSession db) {
        this.persistentEntities = persistentEntities;
        this.db = db;
//      this.topics = topics;
        persistentEntities.register(MessageEntity.class);
//    readSide.register(MessageEventProcessor.class);
    }



    @Override
    public ServiceCall<Message, Done> sendMessage(String receiver, String token) {
        return request -> {

            /*PubSubRef<Message> topic = topics.refFor(TopicId.of(Message.class, "topic"));
            topic.publish(request);*/
            return messageEntityRef(request.sender).ask(new MessageCommand.SendMessage(request))
                    .thenApply(ack -> Done.getInstance());
        };
    }

    @Override
    public ServiceCall<NotUsed, PSequence<Message>> getAllMessages(String owner, String token) {
        return null;/*( req) -> {
            CompletionStage<PSequence<Message>> result = db.selectAll("SELECT messageId, sender, message, receiver FROM messages")
                    .thenApply(rows -> {
                        List<Message> messages = rows.stream().map(row -> Message.of(row.getString("messageId"),
                                row.getString("sender"),
                                row.getString("message"),
                                row.getString("receiver"))).collect(Collectors.toList());
                        return TreePVector.from(messages);
                    });
            return result;
        };*/
    }

    @Override
    public ServiceCall<NotUsed, Message> getMessage(String sender) {
        return request -> {
            return messageEntityRef(sender).ask(new MessageCommand.GetMessage()).thenApply(reply -> {
                if (reply.message.isPresent())
                    return reply.message.get();
                else
                    throw new NotFound("message sent by " + sender + " not found");
            });
        };
    }

    private PersistentEntityRef<MessageCommand> messageEntityRef(String sender) {
        PersistentEntityRef<MessageCommand> ref = persistentEntities.refFor(MessageEntity.class, sender);
        return ref;
    }
}
