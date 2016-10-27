/*
 * Copyright (C) 2016 Lightbend Inc. <http://www.lightbend.com>
 */
package org.lagomy.productManagement.impl;

import akka.Done;
import akka.NotUsed;

import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRef;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.lagomy.productManagement.impl.ProductCommand.*;
import org.pcollections.PSequence;
import org.pcollections.TreePVector;
import org.lagomy.productManagement.api.Product;
import org.lagomy.productManagement.api.ProductService;

/**
 * Implementation of the HelloService.
 */
public class ProductServiceImpl implements ProductService {

  private final PersistentEntityRegistry persistentEntityRegistry;
  private final CassandraSession db;

  @Inject
  public ProductServiceImpl(PersistentEntityRegistry persistentEntityRegistry, 
      CassandraReadSide readSide, CassandraSession db) {
    
    this.persistentEntityRegistry = persistentEntityRegistry;
    this.db = db;
    
    persistentEntityRegistry.register(ProductWorld.class);
    readSide.register(ProductEventProcessor.class);
    
  }
  

  @Override
  public ServiceCall<NotUsed, String> hello(String id) {
    return request -> {
      // Look up the hello world entity for the given ID.
      PersistentEntityRef<ProductCommand> ref = persistentEntityRegistry.refFor(ProductWorld.class, id);
      // Ask the entity the Hello command.
      return ref.ask(new Hello("<<" + id + ">>", Optional.empty()));
    };
  }


    @Override
    public ServiceCall<Product, Done> addProduct() {

        return product -> {
          
          // Look up the hello world entity for the given ID.
          PersistentEntityRef<ProductCommand> ref = persistentEntityRegistry.refFor(ProductWorld.class, product.itemId);
          
          // Tell the entity to use the greeting message specified.
          return ref.ask(new AddProduct(product));
          
        };
        
    }
    
    
    /**
     * Get all added Products
     *
     * @return
     */
    @Override
    public ServiceCall<NotUsed, PSequence<Product>> getAllProducts() {
        return (req) -> {
            CompletionStage<PSequence<Product>> result = 
                db.selectAll("SELECT productId, name, description FROM product")
                    .thenApply(rows -> {
                        List<Product> productList = rows.stream().map(row -> new Product(row.getString("productId"),
                                row.getString("name"),
                                row.getString("description"))).collect(Collectors.toList());
                        return TreePVector.from(productList);
                    });
            return result;
        };
    }

}
