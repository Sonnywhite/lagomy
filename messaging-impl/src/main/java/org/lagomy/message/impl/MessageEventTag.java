/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.message.impl;

import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;

public class MessageEventTag {

    public static final AggregateEventTag<MessageEvent> INSTANCE =
            AggregateEventTag.of(MessageEvent.class);

}
