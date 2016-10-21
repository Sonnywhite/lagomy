package org.lagomy.message.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import org.lagomy.message.api.MessageService;

/**
 * Created by Seva Meyer on 2016-10-13.
 */
public class MessageModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindServices(serviceBinding(MessageService.class, MessageServiceImpl.class));
    }
}
