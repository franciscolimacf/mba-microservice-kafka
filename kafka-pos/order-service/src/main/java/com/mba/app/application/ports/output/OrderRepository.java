package com.mba.app.application.ports.output;


import com.mba.app.application.domain.models.Order;

import java.util.Optional;
import java.util.UUID;

/**
 * Porta de SAÍDA (driven port) para persistência de pedidos.
 *
 * <p>A camada de aplicação declara DE QUE precisa (salvar e buscar pedidos),
 * mas não conhece a tecnologia de persistência. O adaptador concreto
 * (em memória, JPA, MongoDB...) implementa esta interface na camada de
 * infraestrutura. Trocar a tecnologia não afeta o domínio.</p>
 */
public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(UUID id);
}
