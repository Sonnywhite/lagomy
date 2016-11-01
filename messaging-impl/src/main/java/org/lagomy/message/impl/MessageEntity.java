/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.message.impl;;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import org.lagomy.message.api.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageEntity extends PersistentEntity<org.lagomy.message.impl.MessageCommand, MessageEvent, MessageState> {

    @Override
    public Behavior initialBehavior(Optional<MessageState> snapshotState) {

        BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(
                new org.lagomy.message.impl.MessageState(Optional.empty())));

        b.setCommandHandler(MessageCommand.SendMessage.class, (cmd, ctx) -> {
            if (state().message.isPresent()) {
                ctx.invalidCommand("User " + entityId() + " is already created");
                return ctx.done();
            } else {
                Message message = cmd.message;
                List<MessageEvent> events = new ArrayList<MessageEvent>();
                events.add(new MessageEvent.MessageCreated(message.sender, message.message, message.receiver) );
                return ctx.thenPersistAll(events, () -> ctx.reply(Done.getInstance()));
            }
        });

        b.setEventHandler(MessageEvent.MessageCreated.class,
                evt -> new MessageState(Optional.of(new Message(evt.sender, evt.message, evt.receiver))));

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

        b.setReadOnlyCommandHandler(org.lagomy.message.impl.MessageCommand.GetMessage.class, (cmd, ctx) -> {
            ctx.reply(new org.lagomy.message.impl.MessageCommand.GetMessageReply(state().message));
        });

      /*b.setReadOnlyCommandHandler(MessageCommand.GetAllMessages.class, (cmd, ctx) -> {
          ctx.reply(new MessageCommand.GetMessageList(state().message));
      });*/
        return b.build();
    }

 /* private String getUserId() {
    return state().message.get().sender;
  }*/
}
