package org.lagomy.message.impl;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import org.lagomy.message.api.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Seva Meyer on 2016-10-18.
 */
public class MessageEntity extends PersistentEntity<MessageCommand, MessageEvent, MessageState> {

    @Override
    public Behavior initialBehavior(Optional<MessageState> snapshotState) {

        BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(
                new MessageState()));

//        b.setCommandHandler(MessageCommand.SendMessage.class, (cmd, ctx) -> {
//            if (state().message.isPresent()) {
//                ctx.invalidCommand("Message " + entityId() + " is already created");
//                return ctx.done();
//            } else {
//                Message message = cmd.message;
//                List<MessageEvent> events = new ArrayList<MessageEvent>();
//                events.add(new MessageEvent.MessageSent(message.source, message.message,message.receiver));
////                for (String friendId : user.friends) {
////                    events.add(new FriendAdded(user.userId, friendId));
////                }
//                return ctx.thenPersistAll(events, () -> ctx.reply(Done.getInstance()));
//            }
//        });
//
//        b.setEventHandler(MessageEvent.MessageSent.class,
//                evt -> new MessageState(Optional.of(new Message(evt.source, evt.message, evt.receiver))));

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


        b.setReadOnlyCommandHandler(org.lagomy.message.impl.MessageCommand.GetMessage.class, (cmd, ctx) -> {
            ctx.reply(new org.lagomy.message.impl.MessageCommand.GetMessageReply(state().message));
        });

      /*b.setReadOnlyCommandHandler(MessageCommand.GetAllMessages.class, (cmd, ctx) -> {
          ctx.reply(new MessageCommand.GetMessageList(state().message));
      });*/
        return b.build();
    }
}
