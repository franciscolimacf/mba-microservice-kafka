package com.mba.app.application.domain.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Evento de domínio que representa o fato "um pedido foi criado".
 *
 * <p>É o <b>contrato</b> publicado no tópico Kafka. Eventos descrevem
 * algo que <i>já aconteceu</i> (verbo no passado: {@code OrderCreated}),
 * são imutáveis e não carregam comportamento — por isso o uso de
 * {@code record}.</p>
 *
 * <p>O contrato é deliberadamente independente da entidade {@code Order}:
 * a representação interna do domínio pode evoluir sem quebrar quem consome
 * o evento, e vice-versa.</p>
 */
public record OrderCreatedEvent(
        UUID eventId,
        UUID orderId,
        String customerId,
        List<Item> items,
        BigDecimal totalAmount,
        Instant occurredAt
) {
    /** Representação do item dentro do evento (contrato de integração). */
    public record Item(String sku, int quantity, BigDecimal unitPrice) {
    }
}
