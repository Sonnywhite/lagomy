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
import com.lightbend.lagom.serialization.CompressedJsonable;

/**
 * The state for the {@link ProductWorld} entity.
 */
@SuppressWarnings("serial")
@Immutable
@JsonDeserialize
public final class WorldState implements CompressedJsonable {

  public final Product product;
  public final String timestamp;

  @JsonCreator
  public WorldState(Product product, String timestamp) {
    this.product = Preconditions.checkNotNull(product, "product");
    this.timestamp = Preconditions.checkNotNull(timestamp, "timestamp");
  }

  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another)
      return true;
    return another instanceof WorldState && equalTo((WorldState) another);
  }

  private boolean equalTo(WorldState another) {
    return product.equals(another.product) && timestamp.equals(another.timestamp);
  }

  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + product.hashCode();
    h = h * 17 + timestamp.hashCode();
    return h;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper("WorldState").add("product", product).add("timestamp", timestamp).toString();
  }
}
