/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.productManagement.impl;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.lagomy.productManagement.api.Product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;

import akka.Done;

/**
 * This interface defines all the commands that the ProductWorld entity supports.
 * 
 * By convention, the commands should be inner classes of the interface, which
 * makes it simple to get a complete picture of what commands an entity
 * supports.
 */
public interface ProductCommand extends Jsonable {

//-----------------------------------------------------------------------------------------------------------------------------
//            AddProduct (Command)
//-----------------------------------------------------------------------------------------------------------------------------
  /**
   * A command to add a product
   * <p>
   * It has a reply type of {@link akka.Done}, which is sent back to the caller
   * when all the events emitted by this command are successfully persisted.
   */
  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class AddProduct implements ProductCommand, CompressedJsonable, PersistentEntity.ReplyType<Done> {
        
//    public final String itemID;
//    public final String itemName;
//    public final String itemDescription;
    public final Product product;

    @JsonCreator
    public AddProduct(Product product) {
      this.product = Preconditions.checkNotNull(product, "product");
    }

    @Override
    public boolean equals(@Nullable Object another) {
      if (this == another)
        return true;
      return another instanceof AddProduct && equalTo((AddProduct) another);
    }

    private boolean equalTo(AddProduct another) {
        //TODO: update equal etc
      return product.equals(another.product);
    }

    @Override
    public int hashCode() {
      int h = 31;
      h = h * 17 + product.hashCode();
      return h;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper("AddProductCommand").add("product", product).toString();
    }
    
  }

  //-----------------------------------------------------------------------------------------------------------------------------
  //            DeleteProduct (Command)
  //-----------------------------------------------------------------------------------------------------------------------------
  /**
   * A command to delete a product
   * <p>
   * It has a reply type of {@link akka.Done}, which is sent back to the caller
   * when all the events emitted by this command are successfully persisted.
   */
  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class DeleteProduct implements ProductCommand, CompressedJsonable, PersistentEntity.ReplyType<Done> {
    
    public final String productId;

    @JsonCreator
    public DeleteProduct(String productId) {
      this.productId = Preconditions.checkNotNull(productId, "productId");
    }

    @Override
    public boolean equals(@Nullable Object another) {
      if (this == another)
        return true;
      return another instanceof DeleteProduct && equalTo((DeleteProduct) another);
    }

    private boolean equalTo(DeleteProduct another) {
      return productId.equals(another.productId);
    }

    @Override
    public int hashCode() {
      int h = 31;
      h = h * 17 + productId.hashCode();
      return h;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper("DeleteProductCommand").add("product", productId).toString();
    }
    
  }

  //-----------------------------------------------------------------------------------------------------------------------------
  //            MarkProduct (Command)
  //-----------------------------------------------------------------------------------------------------------------------------
  /**
   * A command to mark a product as sold
   * <p>
   * It has a reply type of {@link akka.Done}, which is sent back to the caller
   * when all the events emitted by this command are successfully persisted.
   */
  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class MarkProduct implements ProductCommand, CompressedJsonable, PersistentEntity.ReplyType<Done> {
    
    public final String productId;

    @JsonCreator
    public MarkProduct(String productId) {
      this.productId = Preconditions.checkNotNull(productId, "productId");
    }

    @Override
    public boolean equals(@Nullable Object another) {
      if (this == another)
        return true;
      return another instanceof MarkProduct && equalTo((MarkProduct) another);
    }

    private boolean equalTo(MarkProduct another) {
      return productId.equals(another.productId);
    }

    @Override
    public int hashCode() {
      int h = 31;
      h = h * 17 + productId.hashCode();
      return h;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper("MarkProductCommand").add("product", productId).toString();
    }
    
  }
 
 
  
  
//-----------------------------------------------------------------------------------------------------------------------------
//          Hello (Command)
//-----------------------------------------------------------------------------------------------------------------------------
  /**
   * A command to say hello to someone using the current greeting message.
   * <p>
   * The reply type is String, and will contain the message to say to that
   * person.
   */
  @SuppressWarnings("serial")
  @Immutable
  @JsonDeserialize
  public final class Hello implements ProductCommand, PersistentEntity.ReplyType<String> {
    public final String name;
    public final Optional<String> organization;

    @JsonCreator
    public Hello(String name, Optional<String> organization) {
      this.name = Preconditions.checkNotNull(name, "name");
      this.organization = Preconditions.checkNotNull(organization, "organization");
    }

    @Override
    public boolean equals(@Nullable Object another) {
      if (this == another)
        return true;
      return another instanceof Hello && equalTo((Hello) another);
    }

    private boolean equalTo(Hello another) {
      return name.equals(another.name) && organization.equals(another.organization);
    }

    @Override
    public int hashCode() {
      int h = 31;
      h = h * 17 + name.hashCode();
      h = h * 17 + organization.hashCode();
      return h;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper("Hello").add("name", name).add("organization", organization).toString();
    }
  }

}
