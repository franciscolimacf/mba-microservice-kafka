package com.mba.app.adapters.output.infra.persistence;

import com.mba.app.application.domain.models.Order;
import com.mba.app.application.ports.output.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Adaptador de SAÍDA que implementa {@link OrderRepository} em memória.
 *
 * <p>Suficiente para a POC e mantém o foco no Kafka. Por estar atrás da
 * porta, pode ser substituído por uma implementação JPA/Postgres sem
 * tocar no domínio nem na camada de aplicação — demonstração prática do
 * Princípio Aberto/Fechado e da Inversão de Dependência.</p>
 */
@Repository
public class InMemoryOrderRepository implements OrderRepository {

    private final Map<UUID, Order> store = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
        store.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }
}
