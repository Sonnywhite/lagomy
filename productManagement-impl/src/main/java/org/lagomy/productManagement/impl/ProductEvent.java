/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.productManagement.impl;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.lagomy.productManagement.api.Product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.serialization.Jsonable;

/**
 * This interface defines all the events that the ProductWorld entity supports.
 * <p>
 * By convention, the events should be inner classes of the interface, which
 * makes it simple to get a complete picture of what events an entity has.
 */
public interface ProductEvent extends Jsonable, AggregateEvent<ProductEvent> {
  

  @Override
  default public AggregateEventTag<ProductEvent> aggregateTag() {
      return ProductEventTag.INSTANCE;
  }


//-----------------------------------------------------------------------------------------------------------------------------
//           ProductAdded (Event)
//-----------------------------------------------------------------------------------------------------------------------------
/**
 * An event that represents a change in the pass phrase.
 */
@SuppressWarnings("serial")
@Immutable
@JsonDeserialize
public final class ProductAdded implements ProductEvent {
  public final String productId;
  public final Product product;

  @JsonCreator
  public ProductAdded(String productId, Product product) {
    this.productId = Preconditions.checkNotNull(productId, "productId");
    this.product = Preconditions.checkNotNull(product, "product");
  }

  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another)
      return true;
    return another instanceof ProductAdded && equalTo((ProductAdded) another);
  }

  private boolean equalTo(ProductAdded another) {
      //TODO update for prhase
    return productId.equals(another.productId);
  }

  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + product.hashCode();
    return h;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper("ProductAddedEvent").add("product", product).toString();
  }
}


}
