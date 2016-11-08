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
        return
                prepareCreateTables(session).thenCompose(a ->
                        prepareWriteMessage(session).thenCompose(b ->
                                prepareWriteOffset(session).thenCompose(c ->
                                        selectOffset(session))));
    }

    /**
     * Prepared statement for the persistence offset
     *
     * @param session
     * @return
     */
    private CompletionStage<Done> prepareWriteOffset(CassandraSession session) {
        return session.prepare("INSERT INTO message_offset (partition, offset) VALUES (1, ?)").thenApply(ps -> {
            setWriteOffset(ps);
            return Done.getInstance();
        });
    }

    /**
     * Find persistence offset
     *
     * @param session
     * @return
     */
    private CompletionStage<Optional<UUID>> selectOffset(CassandraSession session) {
        return session.selectOne("SELECT offset FROM message_offset")
                .thenApply(
                        optionalRow -> optionalRow.map(r -> r.getUUID("offset")));
    }



    private CompletionStage<Done> prepareCreateTables(CassandraSession session) {

        return session.executeCreateTable(
                "CREATE TABLE IF NOT EXISTS newmessage ("
                        + "messageId text, sender text, message text,"
                        + "receiver text, "
                        + "PRIMARY KEY (messageId))")

                .thenCompose(a -> session.executeCreateTable(
                        "CREATE TABLE IF NOT EXISTS message_offset ("
                                + "partition int, offset timeuuid, "
                                + "PRIMARY KEY (partition))"));
    }


    private CompletionStage<Done> prepareWriteMessage(CassandraSession session) {
        return session
                .prepare("INSERT INTO newmessage"
                        + "(messageId, sender, message, "
                        + "receiver ) VALUES (?, ?,?,?)")
                .thenApply(ps -> {
                    setWriteMessages(ps);
                    return Done.getInstance();
                });
    }

    @Override
    public EventHandlers defineEventHandlers(EventHandlersBuilder builder) {
        builder.setEventHandler(MessageEvent.MessageCreated.class,
                this::processMessageCreated);
        return builder.build();
    }

    private CompletionStage<List<BoundStatement>>  processMessageCreated(MessageEvent.MessageCreated event, UUID offset) {
// bind the prepared statement
        BoundStatement bindWriteMessage = writeMessages.bind();
// insert values into prepared statement
        bindWriteMessage.setString("messageId",
                event.message.messageId);
        bindWriteMessage.setString("sender",
                event.message.sender);
        bindWriteMessage.setString("message",
                event.message.message);
        bindWriteMessage.setString("receiver",
                event.message.receiver);

// bind the offset prepared statement
        BoundStatement bindWriteOffset =
                writeOffset.bind(offset);
        return completedStatements(
                Arrays.asList(bindWriteMessage,
                        bindWriteOffset));
    }




}
