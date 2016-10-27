package org.lagomy.productManagement.api;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

@Immutable
@JsonDeserialize
public final class Product {

  public final String itemId;
  public final String itemName;
  public final String itemDescription;

  @JsonCreator
  public Product(String itemId, String itemName, String itemDescription) {
      this.itemId = Preconditions.checkNotNull(itemId, "itemId is null, du Depp");
      this.itemName = Preconditions.checkNotNull(itemName, "itemName is null, du Depp");
      this.itemDescription = Preconditions.checkNotNull(itemDescription, "itemDescription is null, du Depp");
  }

  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another)
      return true;
    return another instanceof Product && equalTo((Product) another);
  }

  private boolean equalTo(Product another) {
    return itemName.equals(another.itemName);
  }

  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + itemName.hashCode();
    return h;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper("Product").add("itemName", itemName).toString();
  }
  
  public static Product getDummy(){
    return new Product("1230", "Dummy-Kuchen", "The cake is a lie");
  }
}
