package org.lagomy.message.impl;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import org.lagomy.message.api.Message;
import org.lagomy.message.api.MessageService;
import org.lagomy.message.api.Messages;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * Created by spyrosmartel on 2016-10-12.
 */

public class MessageServiceImpl implements MessageService {


    private final CassandraSession db;


    @Inject
    public MessageServiceImpl( CassandraSession db ){

        this.db = db;
        createTable();
    }

    private void createTable(){
        CompletionStage<Done> result = db.executeCreateTable(

           "CREATE TABLE IF NOT EXISTS messages (source text, message text, receiver text)"
        );
        result.whenComplete((ok, err) -> {
            if (err != null) {
                System.out.println("Failed to create chirp table, due to: " + err.getMessage());
            }
        });
    }

    @Override
    public ServiceCall<Message, Done> sendMessage(String receiver) {
        return message -> {
            //@todo check if the receiver is the same in json object
            //PubSubRef<Message> topic = topics.refFor(TopicId.of(Message.class, topicQualifier(userId)));
            //topic.publish(message);
            CompletionStage<Done> result =
                    db.executeWrite("INSERT INTO messages (source, message, receiver) VALUES (?, ?, ?, ?)",
                            message.source, message.message, message.receiver).thenApply(done -> Done.getInstance());
            return result;
        };
    }

    @Override
    public ServiceCall<NotUsed, Messages> getAllMessages(String owner) {
        return null;
    }

    @Override
    public ServiceCall<NotUsed, Messages> getMessagesByUsername(String owner, String sender) {
        return null;
    }
}
