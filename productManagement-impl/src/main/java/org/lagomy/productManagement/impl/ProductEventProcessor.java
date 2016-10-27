package org.lagomy.productManagement.impl;

import akka.Done;
import com.datastax.driver.core.BoundStatement;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSideProcessor;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;

import com.datastax.driver.core.PreparedStatement;

import org.lagomy.productManagement.impl.ProductEvent.ProductAdded;
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


    private PreparedStatement writeProduct = null; // initialized in prepare
    private PreparedStatement writeOffset = null; // initialized in prepare

    private void setWriteProduct(PreparedStatement writeProduct) {
        this.writeProduct = writeProduct;
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
                        prepareWriteProduct(session).thenCompose(b ->
                                prepareWriteOffset(session).thenCompose(c ->
                                        selectOffset(session))));
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
                        + "productId text, name text, description text,"
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
    private CompletionStage<Done> prepareWriteProduct(CassandraSession session) {
        return session.prepare(
            "INSERT INTO product (productId, name, description)"
            + " VALUES (?,?,?)").thenApply(ps -> {
            setWriteProduct(ps);
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
        return session.prepare("INSERT INTO product_offset (partition, offset) VALUES (1, ?)").thenApply(ps -> {
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
     * Bind the read side persistence to the ProductRegistered event
     *
     * @param builder
     * @return
     */
    @Override
    public EventHandlers defineEventHandlers(EventHandlersBuilder builder) {
        builder.setEventHandler(ProductAdded.class, this::processProductRegistered);
        return builder.build();
    }

    /** --------------------------- processProductRegistered ---------------------------
     * Write a persistent event into the read-side optimized database.
     *
     * @param @link{ProductRegistered}
     * @param offset
     * @return
     */
    private CompletionStage<List<BoundStatement>> processProductRegistered(ProductAdded event, UUID offset) {
        BoundStatement bindWriteProduct = writeProduct.bind();
        bindWriteProduct.setString("productId", event.product.itemId);
        bindWriteProduct.setString("name", event.product.itemName);
        bindWriteProduct.setString("description", event.product.itemDescription);
        BoundStatement bindWriteOffset = writeOffset.bind(offset);
        log.info("Persisted {}", event.product.itemId);
        return completedStatements(Arrays.asList(bindWriteProduct, bindWriteOffset));
    }

}
