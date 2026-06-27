package com.mba.app.application.domain.models;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Raiz do agregado Pedido. É uma entidade de domínio pura (POJO):
 * não depende de Spring, JPA ou Kafka. Toda regra de negócio referente
 * a um pedido vive aqui, o que mantém o domínio testável e estável.
 *
 * <p>A criação passa pela fábrica {@link #create(String, List)}, que
 * concentra as invariantes de construção. O total é calculado pelo
 * próprio agregado, nunca informado de fora.</p>
 */
public class Order {

    private final UUID id;
    private final String customerId;
    private final List<OrderItem> items;
    private final BigDecimal totalAmount;
    private final OrderStatus status;
    private final Instant createdAt;

    private Order(UUID id, String customerId, List<OrderItem> items,
                  BigDecimal totalAmount, OrderStatus status, Instant createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    /**
     * Fábrica de criação de um novo pedido. Garante as invariantes:
     * cliente informado, ao menos um item e total derivado dos itens.
     */
    public static Order create(String customerId, List<OrderItem> items) {
        Objects.requireNonNull(customerId, "customerId não pode ser nulo");
        Objects.requireNonNull(items, "items não pode ser nulo");
        if (customerId.isBlank()) {
            throw new IllegalArgumentException("customerId não pode ser vazio");
        }
        if (items.isEmpty()) {
            throw new IllegalArgumentException("um pedido precisa de ao menos um item");
        }
        BigDecimal total = items.stream()
                .map(OrderItem::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Order(
                UUID.randomUUID(),
                customerId,
                List.copyOf(items),
                total,
                OrderStatus.CREATED,
                Instant.now()
        );
    }

    public UUID getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
