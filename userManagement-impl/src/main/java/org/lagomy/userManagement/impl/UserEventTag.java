package org.lagomy.userManagement.impl;

import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;

public class UserEventTag {

  public static final AggregateEventTag<UserEvent> INSTANCE = 
    AggregateEventTag.of(UserEvent.class);

}
