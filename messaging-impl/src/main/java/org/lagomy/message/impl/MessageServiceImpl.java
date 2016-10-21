package org.lagomy.message.impl;

import akka.Done;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import org.lagomy.message.api.Message;
import org.lagomy.message.api.MessageService;
import org.lagomy.message.api.Messages;
import org.pcollections.PSequence;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * Created by Seva Meyer on 2016-10-12.
 */

public class MessageServiceImpl implements MessageService {

    private final PersistentEntityRegistry persistentEntityRegistry;
    private final CassandraSession db;


    @Inject
    public MessageServiceImpl(PersistentEntityRegistry persistentEntityRegistry,CassandraReadSide readSide, CassandraSession db ){
        this.persistentEntityRegistry = persistentEntityRegistry;
        persistentEntityRegistry.register(MessageEntity.class);
       // readSide.register(FriendEventProcessor.class);
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
    public ServiceCall<Message, Done> sendMessage(String receiver, String token) {
        return message -> {
            //@todo check if the receiver is the same in json object
            //@todo check token
            //PubSubRef<Message> topic = topics.refFor(TopicId.of(Message.class, topicQualifier(userId)));
            //topic.publish(message);
            CompletionStage<Done> result =
                    db.executeWrite("INSERT INTO messages (source, message, receiver) VALUES (?, ?, ?, ?)",
                            message.source, message.message, message.receiver).thenApply(done -> Done.getInstance());
            return result;
        };
    }

    @Override
    public ServiceCall<NotUsed, PSequence<Message>> getAllMessages(String owner, String token) {

//        return req -> {
//            return recentChirps(req.userIds).thenApply(recentChirps -> {
//                List<Source<Chirp, ?>> sources = new ArrayList<>();
//                for (String userId : req.userIds) {
//                    PubSubRef<Chirp> topic = topics.refFor(TopicId.of(Chirp.class, topicQualifier(userId)));
//                    sources.add(topic.subscriber());
//                }
//                HashSet<String> users = new HashSet<>(req.userIds);
//                Source<Chirp, ?> publishedChirps = Source.from(sources).flatMapMerge(sources.size(), s -> s)
//                        .filter(c -> users.contains(c.userId));
//
//                // We currently ignore the fact that it is possible to get duplicate chirps
//                // from the recent and the topic. That can be solved with a de-duplication stage.
//                return Source.from(recentChirps).concat(publishedChirps);
//            });
//        };
        return null;
    }


}
