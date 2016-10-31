package org.lagomy.productManagement.api;

import java.time.LocalDateTime;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

@Immutable
@JsonDeserialize
public final class Product {

  public final String productId;
  public final String productName;
  public final String sellerName;
  public final String description;
  public final String photoPath;
  public final int price;
  public final boolean sold;

  @JsonCreator
  public Product(String productId, String productName, String sellerName, String description, String photoPath, int price, boolean sold) {
      this.productId = Preconditions.checkNotNull(productId, "productId is null, du Depp");
      this.productName = Preconditions.checkNotNull(productName, "productName is null, du Depp");
      this.sellerName = Preconditions.checkNotNull(sellerName, "sellerName is null, du Depp");
      this.description = Preconditions.checkNotNull(description, "itemDescription is null, du Depp");
      this.photoPath = Preconditions.checkNotNull(photoPath, "photoPath is null, du Depp");
      this.price = Preconditions.checkNotNull(price, "price is null, du Depp");
      this.sold = Preconditions.checkNotNull(sold, "sold is null, du Depp");
  }
  
  public Product asSold(){
    return new Product(productId, productName, sellerName, description, photoPath, price, true);
  }

  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another)
      return true;
    return another instanceof Product && equalTo((Product) another);
  }

  private boolean equalTo(Product another) {
    return productId.equals(another.productId)
        && productName.equals(another.productName)
        && sellerName.equals(another.sellerName)
        && description.equals(another.description)
        && photoPath.equals(another.photoPath)
        && price == another.price
        && (sold == another.sold);
  }

  @Override
  public int hashCode() {
    int h = 31;
    h = h * 17 + productId.hashCode();
    h = h * 17 + productName.hashCode();
    return h;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper("Product").add("itemName", productName).toString();
  }
  
  public static Product getDummy(){
    return new Product("Dummy" + LocalDateTime.now().toString(), 
        "Dummy-Kuchen", "Mr.Dummy", "The cake is a lie", "dummypath", 0, false);
  }
  
  public static Product getDeletedProductDummy(){
    return new Product("deleted" + LocalDateTime.now().toString(), 
        "Deleted Product", "Mr.Nobody", "Placeholder for deleted product", "noPath", 0, false);
  }
  
}
