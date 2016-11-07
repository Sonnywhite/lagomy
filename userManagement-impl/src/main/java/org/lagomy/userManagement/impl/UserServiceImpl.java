package org.lagomy.userManagement.impl;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.pcollections.PSequence;
import org.pcollections.TreePVector;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.NotFound;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;

import akka.NotUsed;
import org.lagomy.userManagement.api.UserService;
import org.lagomy.userManagement.impl.UserCommand.CreateUser;
import org.lagomy.userManagement.impl.UserCommand.GetUser;
import org.lagomy.userManagement.api.User;

public class UserServiceImpl implements UserService {

  private final PersistentEntityRegistry persistentEntities;
  private final CassandraSession db;

  @Inject
  public UserServiceImpl(PersistentEntityRegistry persistentEntities, CassandraReadSide readSide,
      CassandraSession db) {
    this.persistentEntities = persistentEntities;
    this.db = db;

    persistentEntities.register(UserEntity.class);
    readSide.register(UserEventProcessor.class);
  }
// only in this part just check the being equivalent password?
  @Override
  public ServiceCall<NotUsed, User> getUser(String username, String password) {
    return request -> {
      return friendEntityRef(username,password).ask(new GetUser()).thenApply(reply -> {
        if (reply.user.isPresent() && (reply.user.get().password).equals(password))
          return reply.user.get();
        else
          throw new NotFound("user " + username + " not found");
      });
    };
  }

  @Override
  public ServiceCall<User, NotUsed> createUser() {
    return request -> {
      return friendEntityRef(request.username, request.password).ask(new CreateUser(request))
          .thenApply(ack -> NotUsed.getInstance());
    };
  }


  private PersistentEntityRef<UserCommand> friendEntityRef(String username , String password) {
    PersistentEntityRef<UserCommand> ref = persistentEntities.refFor(UserEntity.class, username);
    return ref;
  }

}

