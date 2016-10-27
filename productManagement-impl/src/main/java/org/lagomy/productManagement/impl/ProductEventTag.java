package org.lagomy.productManagement.impl;

import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;

/**
 * Register a common event tag
 */
public class ProductEventTag {

    public static final AggregateEventTag<ProductEvent> INSTANCE =
            AggregateEventTag.of(ProductEvent.class);

}
