/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.message.impl;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import org.lagomy.message.api.Message;

import java.time.LocalDateTime;
import java.util.Optional;

public class MessageEntity extends PersistentEntity<MessageCommand, MessageEvent, MessageState> {

    @Override
    public Behavior initialBehavior(Optional<MessageState> snapshotState) {

        BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(
                new MessageState(new Message("","","",""), LocalDateTime.now().toString())
        ));
      /*MessageState.builder().message(Message.builder()
            .id("")
            .sender("")
            .message("")
            .receiver("").build())
            .timestamp(LocalDateTime.now()).build()));*/

        b.setCommandHandler(MessageCommand.SendMessage.class, (cmd, ctx) -> {
     /* if (state().message.isPresent()) {
        ctx.invalidCommand("User " + entityId() + " is already created");
        return ctx.done();
      } else {*/

            final MessageEvent.MessageCreated messageCreated =
                    new MessageEvent.MessageCreated(cmd.message.messageId, cmd.message);
            return ctx.thenPersist(messageCreated, ect -> ctx.reply(Done.getInstance()));

//        Message message = cmd.message;
//        List<MessageEvent> events = new ArrayList<MessageEvent>();
//        events.add(new MessageEvent.MessageCreated(message.sender, message.message, message.receiver) );
//        return ctx.thenPersistAll(events, () -> ctx.reply(Done.getInstance()));
            //  }
        });

        b.setEventHandler(MessageEvent.MessageCreated.class,
                evt -> new MessageState(evt.message, LocalDateTime.now().toString()));
      /*state()
              .withMessage(evt.getMessage())
              .withTimestamp(LocalDateTime.now()))*/;//
//    b.setCommandHandler(AddFriend.class, (cmd, ctx) -> {
//      if (!state().user.isPresent()) {
//        ctx.invalidCommand("User " + entityId() + " is not  created");
//        return ctx.done();
//      } else if (state().user.get().friends.contains(cmd.friendUserId)) {
//        ctx.reply(Done.getInstance());
//        return ctx.done();
//      } else {
//        return ctx.thenPersist(new FriendAdded(getUserId(), cmd.friendUserId), evt ->
//          ctx.reply(Done.getInstance()));
//      }
//    });

//    b.setEventHandler(FriendAdded.class, evt -> state().addFriend(evt.friendId));

//    b.setReadOnlyCommandHandler(MessageCommand.GetMessage.class, (cmd, ctx) -> {
//      ctx.reply(new MessageCommand.GetMessageReply(state().message));
//    });

      /*b.setReadOnlyCommandHandler(MessageCommand.GetAllMessages.class, (cmd, ctx) -> {
          ctx.reply(new MessageCommand.GetMessageList(state().message));
      });*/
        return b.build();
    }

 /* private String getUserId() {
    return state().message.get().sender;
  }*/
}
