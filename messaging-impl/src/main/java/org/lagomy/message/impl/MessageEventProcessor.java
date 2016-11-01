/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.message.impl;

import akka.Done;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSideProcessor;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

public class MessageEventProcessor extends CassandraReadSideProcessor<MessageEvent> {

    private PreparedStatement writeMessages = null; // initialized in prepare
    private PreparedStatement writeOffset = null; // initialized in prepare

    private void setWriteMessages(PreparedStatement writeMessages) {
        this.writeMessages = writeMessages;
    }

    private void setWriteOffset(PreparedStatement writeOffset) {
        this.writeOffset = writeOffset;
    }

    @Override
    public AggregateEventTag<MessageEvent> aggregateTag() {
        return MessageEventTag.INSTANCE;
    }

    @Override
    public CompletionStage<Optional<UUID>> prepare(CassandraSession session) {
        // @formatter:off
        return
                prepareCreateTables(session).thenCompose(a ->
                        prepareWriteMessages(session).thenCompose(b ->
                                prepareWriteOffset(session).thenCompose(c ->
                                        selectOffset(session))));
        // @formatter:on
    }


    private CompletionStage<Done> prepareCreateTables(CassandraSession session) {
        // @formatter:off
        return session.executeCreateTable(
                "CREATE TABLE IF NOT EXISTS messages ("
                        + "messageId text, sender text, message text, receiver text, "
                        + "PRIMARY KEY (messageId))")
                .thenCompose(a -> session.executeCreateTable(
                        "CREATE TABLE IF NOT EXISTS message_offset ("
                                + "partition int, offset timeuuid, "
                                + "PRIMARY KEY (partition))"));
        // @formatter:on
    }

    private CompletionStage<Done> prepareWriteMessages(CassandraSession session) {
        return session.prepare("INSERT INTO messages (messageId, sender, message, receiver) VALUES (?, ?, ?, ?)").thenApply(ps -> {
            setWriteMessages(ps);
            return Done.getInstance();
        });
    }

    private CompletionStage<Done> prepareWriteOffset(CassandraSession session) {
        return session.prepare("INSERT INTO message_offset (partition, offset) VALUES (1, ?)").thenApply(ps -> {
            setWriteOffset(ps);
            return Done.getInstance();
        });
    }

    private CompletionStage<Optional<UUID>> selectOffset(CassandraSession session) {
        return session.selectOne("SELECT offset FROM message_offset WHERE partition=1")
                .thenApply(
                        optionalRow -> optionalRow.map(r -> r.getUUID("offset")));
    }

    @Override
    public EventHandlers defineEventHandlers(EventHandlersBuilder builder) {
        builder.setEventHandler(MessageEvent.MessageCreated.class, this::processMessageChanged);
        return builder.build();
    }

    private CompletionStage<List<BoundStatement>> processMessageChanged(MessageEvent.MessageCreated event, UUID offset) {
        BoundStatement bindWriteMessages = writeMessages.bind();
        bindWriteMessages.setString("sender", event.sender);
        bindWriteMessages.setString("message", event.message);
        bindWriteMessages.setString("receiver", event.receiver);
        BoundStatement bindWriteOffset = writeOffset.bind(offset);
        return completedStatements(Arrays.asList(bindWriteMessages, bindWriteOffset));
    }



}
