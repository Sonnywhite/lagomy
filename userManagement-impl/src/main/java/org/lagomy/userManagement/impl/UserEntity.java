package org.lagomy.userManagement.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import akka.Done;
import org.lagomy.userManagement.impl.UserCommand.CreateUser;
import org.lagomy.userManagement.impl.UserCommand.GetUser;
import org.lagomy.userManagement.impl.UserCommand.GetUserReply;
import org.lagomy.userManagement.impl.UserEvent.UserCreated;
import org.lagomy.userManagement.api.User;

public class UserEntity extends PersistentEntity<UserCommand, UserEvent, UserState> {

  @Override
  public Behavior initialBehavior(Optional<UserState> snapshotState) {

    BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(
      new UserState(Optional.empty())));

    b.setCommandHandler(CreateUser.class, (cmd, ctx) -> {
      if (state().user.isPresent()) {
        ctx.invalidCommand("User " + entityId() + " is already created");
        return ctx.done();
      } else {
        User user = cmd.user;
        List<UserEvent> events = new ArrayList<UserEvent>();
        events.add(new UserCreated(user.username, user.password));
        //for (String friendId : user.friends) {
          //events.add(new FriendAdded(user.userId, friendId));
      //  }
        return ctx.thenPersistAll(events, () -> ctx.reply(Done.getInstance()));
      }
    });

    b.setEventHandler(UserCreated.class,
        evt -> new UserState(Optional.of(new User(evt.username, evt.password))));

    b.setReadOnlyCommandHandler(GetUser.class, (cmd, ctx) -> {
      ctx.reply(new GetUserReply(state().user));
    });

    return b.build();
  }

  private String getUserId() {
    return state().user.get().username;
  }
}

