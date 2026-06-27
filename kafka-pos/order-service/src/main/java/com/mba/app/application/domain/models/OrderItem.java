package com.mba.app.application.domain.models;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Item de um pedido. Modelado como {@code record} por ser um Value Object
 * imutável: não possui identidade própria, apenas valor.
 *
 * <p>As validações no construtor compacto garantem que o objeto nunca
 * exista em estado inválido — princípio "always valid domain model".</p>
 */
public record OrderItem(String sku, int quantity, BigDecimal unitPrice) {

    public OrderItem {
        Objects.requireNonNull(sku, "sku não pode ser nulo");
        Objects.requireNonNull(unitPrice, "unitPrice não pode ser nulo");
        if (sku.isBlank()) {
            throw new IllegalArgumentException("sku não pode ser vazio");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity deve ser maior que zero");
        }
        if (unitPrice.signum() < 0) {
            throw new IllegalArgumentException("unitPrice não pode ser negativo");
        }
    }

    /** Total da linha = preço unitário * quantidade. */
    public BigDecimal subtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
