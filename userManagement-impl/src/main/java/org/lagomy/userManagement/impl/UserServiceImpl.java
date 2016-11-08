package org.lagomy.userManagement.impl;


import javax.inject.Inject;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;

import akka.Done;
import org.lagomy.userManagement.api.UserService;
import org.lagomy.userManagement.impl.UserCommand.CheckLogin;
import org.lagomy.userManagement.impl.UserCommand.CreateUser;
import org.lagomy.userManagement.api.User;

public class UserServiceImpl implements UserService {

  private final PersistentEntityRegistry persistentEntities;

  @Inject
  public UserServiceImpl(PersistentEntityRegistry persistentEntities) {
    this.persistentEntities = persistentEntities;

    persistentEntities.register(UserEntity.class);
  }
  
  
  @Override
  public ServiceCall<User, Done> createUser() {
    return request -> {
      // Look up the hello world entity for the given ID.
      PersistentEntityRef<UserCommand> ref = persistentEntities.refFor(UserEntity.class, request.username);
      // Ask the entity the Hello command.
      return ref.ask(new CreateUser(request));
    };
  }


  
  
  //only in this part just check the being equivalent password?
   @Override
   public ServiceCall<User, Boolean> checkLogin() {
     return request -> {
       // Look up the hello world entity for the given ID.
       PersistentEntityRef<UserCommand> ref = persistentEntities.refFor(UserEntity.class, request.username);
       // Ask the entity the Hello command.
       return ref.ask(new CheckLogin(request));
     };
   }

}

