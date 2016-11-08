package org.lagomy.userManagement.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSideProcessor;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import akka.Done;


public class UserEventProcessor extends CassandraReadSideProcessor<UserEvent> {

  private PreparedStatement writeFollowers = null; // initialized in prepare
  private PreparedStatement writeOffset = null; // initialized in prepare

  private void setWriteFollowers(PreparedStatement writeFollowers) {
    this.writeFollowers = writeFollowers;
  }

  private void setWriteOffset(PreparedStatement writeOffset) {
    this.writeOffset = writeOffset;
  }

  @Override
  public AggregateEventTag<UserEvent> aggregateTag() {
    return UserEventTag.INSTANCE;
  }

  @Override
  public CompletionStage<Optional<UUID>> prepare(CassandraSession session) {
    // @formatter:off
    return
      prepareCreateTables(session).thenCompose(a ->
      prepareWriteFollowers(session).thenCompose(b ->
      prepareWriteOffset(session).thenCompose(c ->
      selectOffset(session))));
    // @formatter:on
  }

  private CompletionStage<Done> prepareCreateTables(CassandraSession session) {
    // @formatter:off
    return session.executeCreateTable(
        "CREATE TABLE IF NOT EXISTS follower ("
          + "userId text, followedBy text, "
          + "PRIMARY KEY (userId, followedBy))")
      .thenCompose(a -> session.executeCreateTable(
        "CREATE TABLE IF NOT EXISTS friend_offset ("
          + "partition int, offset timeuuid, "
          + "PRIMARY KEY (partition))"));
    // @formatter:on
  }

  private CompletionStage<Done> prepareWriteFollowers(CassandraSession session) {
    return session.prepare("INSERT INTO follower (userId, followedBy) VALUES (?, ?)").thenApply(ps -> {
      setWriteFollowers(ps);
      return Done.getInstance();
    });
  }

  private CompletionStage<Done> prepareWriteOffset(CassandraSession session) {
    return session.prepare("INSERT INTO friend_offset (partition, offset) VALUES (1, ?)").thenApply(ps -> {
      setWriteOffset(ps);
      return Done.getInstance();
    });
  }

  private CompletionStage<Optional<UUID>> selectOffset(CassandraSession session) {
    return session.selectOne("SELECT offset FROM friend_offset WHERE partition=1")
        .thenApply(
        optionalRow -> optionalRow.map(r -> r.getUUID("offset")));
  }

  @Override
  public EventHandlers defineEventHandlers(EventHandlersBuilder builder) {
  //  builder.setEventHandler(FriendAdded.class, this::processFriendChanged);
    return builder.build();
	 // return null;
  }

//  private CompletionStage<List<BoundStatement>> processFriendChanged(FriendAdded event, UUID offset) {
//    BoundStatement bindWriteFollowers = writeFollowers.bind();
//    bindWriteFollowers.setString("userId", event.friendId);
//    bindWriteFollowers.setString("followedBy", event.userId);
//    BoundStatement bindWriteOffset = writeOffset.bind(offset);
//    return completedStatements(Arrays.asList(bindWriteFollowers, bindWriteOffset));
//  }



}

