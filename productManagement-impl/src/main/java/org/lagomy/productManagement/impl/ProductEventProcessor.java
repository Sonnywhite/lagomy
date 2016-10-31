package org.lagomy.productManagement.impl;

import akka.Done;
import com.datastax.driver.core.BoundStatement;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSideProcessor;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;

import com.datastax.driver.core.PreparedStatement;

import org.lagomy.productManagement.impl.ProductEvent.ProductAdded;
import org.lagomy.productManagement.impl.ProductEvent.ProductDeleted;
import org.lagomy.productManagement.impl.ProductEvent.ProductMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

/**
 * Transform the Persistent Entity events into Cassandra database
 * tables and records that can be queried from a service.
 */
public class ProductEventProcessor extends CassandraReadSideProcessor<ProductEvent> {

    private final Logger log = LoggerFactory.getLogger(ProductEventProcessor.class);

    @Override
    public AggregateEventTag<ProductEvent> aggregateTag() {
        return ProductEventTag.INSTANCE;
    }


    private PreparedStatement addProduct = null; // initialized in prepare
    private PreparedStatement markProduct = null; // initialized in prepare
    private PreparedStatement deleteProduct = null; // initialized in prepare
    private PreparedStatement writeOffset = null; // initialized in prepare

    
    private void setAddProduct(PreparedStatement addProduct) {
        this.addProduct = addProduct;
    }

    private void setMarkProduct(PreparedStatement markProduct) {
        this.markProduct = markProduct;
    }

    private void setDeleteProduct(PreparedStatement deleteProduct) {
        this.deleteProduct = deleteProduct;
    }

    private void setWriteOffset(PreparedStatement writeOffset) {
        this.writeOffset = writeOffset;
    }

    /**
     * Prepare read-side table and statements
     *
     * @param session
     * @return
     */
    @Override
    public CompletionStage<Optional<UUID>> prepare(CassandraSession session) {
        // @formatter:off
        return
          prepareCreateTables(session).thenCompose(a ->
          prepareAddProduct(session).thenCompose(b ->
          prepareMarkProduct(session).thenCompose(c ->
          prepareDeleteProduct(session).thenCompose(y ->
          prepareWriteOffset(session).thenCompose(z ->
                  selectOffset(session))))));
        // @formatter:on
    }

    /** --------------------------- prepareCreateTables ---------------------------
     * prepare read-side tables
     *
     * @param session
     * @return
     */
    private CompletionStage<Done> prepareCreateTables(CassandraSession session) {
        // @formatter:off
        return session.executeCreateTable(
                "CREATE TABLE IF NOT EXISTS product ("
                        + "productId text, productName text, sellerName text, "
                        + "description text, photoPath text, price int, sold boolean,"
                        + "PRIMARY KEY (productId))")
                .thenCompose(a -> session.executeCreateTable(
                  "CREATE TABLE IF NOT EXISTS product_offset ("
                          + "partition int, offset timeuuid, "
                          + "PRIMARY KEY (partition))"));
        // @formatter:on
    }

    /** --------------------------- prepareWriteProduct ---------------------------
     * prepared statement for writing a Product object
     *
     * @param session
     * @return
     */
    private CompletionStage<Done> prepareAddProduct(CassandraSession session) {
        return session.prepare(
            "INSERT INTO product (productId, productName, sellerName, description, photoPath, price, sold)"
            + " VALUES (?,?,?,?,?,?,?)").thenApply(ps -> {
            setAddProduct(ps);
            return Done.getInstance();
        });
    }

    /** --------------------------- prepareMarkProduct ---------------------------
     * prepared statement for marking a Product object as sold
     *
     * @param session
     * @return
     */
    private CompletionStage<Done> prepareMarkProduct(CassandraSession session) {
        return session.prepare(
            "UPDATE product SET sold=true"
            + " WHERE productId=?").thenApply(ps -> {
            setMarkProduct(ps);
            return Done.getInstance();
        });
    }

    /** --------------------------- prepareDeleteProduct ---------------------------
     * prepared statement for deleting a Product object
     *
     * @param session
     * @return
     */
    private CompletionStage<Done> prepareDeleteProduct(CassandraSession session) {
        return session.prepare(
            "DELETE FROM product WHERE"
            + " productId=?").thenApply(ps -> {
            setDeleteProduct(ps);
            return Done.getInstance();
        });
    }

    /** --------------------------- prepareWriteOffset ---------------------------
     * Prepared statement for the persistence offset
     *
     * @param session
     * @return
     */
    private CompletionStage<Done> prepareWriteOffset(CassandraSession session) {
        return session.prepare("INSERT INTO product_offset (partition, offset) VALUES (1, ?)")
            .thenApply(ps -> {
            setWriteOffset(ps);
            return Done.getInstance();
        });
    }

    /** --------------------------- selectOffset ---------------------------
     * Find persistence offset
     *
     * @param session
     * @return
     */
    private CompletionStage<Optional<UUID>> selectOffset(CassandraSession session) {
        return session.selectOne("SELECT offset FROM product_offset")
                .thenApply(
                        optionalRow -> optionalRow.map(r -> r.getUUID("offset")));
    }

    /** --------------------------- defineEventHandlers ---------------------------
     * Bind the read side persistence to the ProductAdded event
     *
     * @param builder
     * @return
     */
    @Override
    public EventHandlers defineEventHandlers(EventHandlersBuilder builder) {
        builder.setEventHandler(ProductAdded.class, this::processProductAdded);
        builder.setEventHandler(ProductMarked.class, this::processProductMarked);
        builder.setEventHandler(ProductDeleted.class, this::processProductDeleted);
        return builder.build();
    }

    /** --------------------------- processProductAdded ---------------------------
     * Write a persistent event into the read-side optimized database.
     *
     * @param @link{ProductRegistered}
     * @param offset
     * @return
     */
    private CompletionStage<List<BoundStatement>> processProductAdded(ProductAdded event, UUID offset) {
        BoundStatement bindAddProduct = addProduct.bind();
        bindAddProduct.setString("productId", event.product.productId);
        bindAddProduct.setString("productName", event.product.productName);
        bindAddProduct.setString("sellerName", event.product.sellerName);
        bindAddProduct.setString("description", event.product.description);
        bindAddProduct.setString("photoPath", event.product.photoPath);
        bindAddProduct.setInt("price", event.product.price);
        bindAddProduct.setBool("sold", event.product.sold);
        BoundStatement bindWriteOffset = writeOffset.bind(offset);
        log.info("Persisted {}", event.product.productId);
        return completedStatements(Arrays.asList(bindAddProduct, bindWriteOffset));
    }

    /** --------------------------- processProductDeleted ---------------------------
     * Write a persistent event into the read-side optimized database.
     *
     * @param @link{ProductRegistered}
     * @param offset
     * @return
     */
    private CompletionStage<List<BoundStatement>> processProductMarked(ProductMarked event, UUID offset) {
        BoundStatement bindMarkProduct = markProduct.bind();
        bindMarkProduct.setString("productId", event.productId);
        BoundStatement bindWriteOffset = writeOffset.bind(offset);
        log.info("Persisted {}", event.productId);
        return completedStatements(Arrays.asList(bindMarkProduct, bindWriteOffset));
    }

    /** --------------------------- processProductDeleted ---------------------------
     * Write a persistent event into the read-side optimized database.
     *
     * @param @link{ProductRegistered}
     * @param offset
     * @return
     */
    private CompletionStage<List<BoundStatement>> processProductDeleted(ProductDeleted event, UUID offset) {
        BoundStatement bindDeleteProduct = deleteProduct.bind();
        bindDeleteProduct.setString("productId", event.productId);
        BoundStatement bindWriteOffset = writeOffset.bind(offset);
        log.info("Persisted {}", event.productId);
        return completedStatements(Arrays.asList(bindDeleteProduct, bindWriteOffset));
    }

}
