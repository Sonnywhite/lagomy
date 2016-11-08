package org.lagomy.userManagement.impl;

import java.util.Optional;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;

import akka.Done;
import org.lagomy.userManagement.impl.UserCommand.CreateUser;
import org.lagomy.userManagement.impl.UserCommand.CheckLogin;
import org.lagomy.userManagement.impl.UserEvent.UserCreated;
import org.lagomy.userManagement.api.User;

public class UserEntity extends PersistentEntity<UserCommand, UserEvent, UserState> {

  @Override
  public Behavior initialBehavior(Optional<UserState> snapshotState) {

    BehaviorBuilder b = newBehaviorBuilder(snapshotState.orElse(
      new UserState(new User("",""))));

    //--------------------------------------------------------------------------
    //    CreateUser (Command)
    //--------------------------------------------------------------------------
    b.setCommandHandler(CreateUser.class, (cmd, ctx) ->
        // In response to this command, we want to first persist it 
        ctx.thenPersist(new UserCreated(cmd.user.username, cmd.user.password),
        // Then once the event is successfully persisted, we respond with done.
        evt -> ctx.reply(Done.getInstance())));

    //--------------------------------------------------------------------------
    //    UserCreated (Event)
    //--------------------------------------------------------------------------
    
    b.setEventHandler(UserCreated.class,
        evt -> new UserState(new User(evt.username, evt.password)));

    //--------------------------------------------------------------------------
    //    CheckLogin (Command)
    //--------------------------------------------------------------------------
    
    b.setReadOnlyCommandHandler(CheckLogin.class, 
        (cmd, ctx) -> ctx.reply(
            cmd.user.username.equals(state().user.username) 
            && cmd.user.password.equals(state().user.password) ));

    return b.build();
  }
  
}

