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



        return b.build();
    }
}
