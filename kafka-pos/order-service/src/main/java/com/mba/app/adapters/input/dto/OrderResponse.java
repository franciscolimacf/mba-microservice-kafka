package com.mba.app.adapters.input.dto;


import com.mba.app.application.domain.models.Order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO de saída da API REST. Expõe apenas o necessário ao cliente HTTP,
 * desacoplando o contrato externo da estrutura interna do agregado.
 */
public record OrderResponse(
        UUID id,
        String customerId,
        BigDecimal totalAmount,
        String status,
        Instant createdAt
) {
    /** Converte o agregado de domínio na resposta de API. */
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getCreatedAt()
        );
    }
}
