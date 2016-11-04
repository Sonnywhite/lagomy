/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.message.impl;

import com.google.inject.AbstractModule;
import org.lagomy.message.api.MessageService;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class MessageModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindServices(serviceBinding(MessageService.class, MessageServiceImpl.class));
    }
}
