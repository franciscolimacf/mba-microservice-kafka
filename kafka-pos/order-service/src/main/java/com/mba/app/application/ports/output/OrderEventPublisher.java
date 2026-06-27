package com.mba.app.application.ports.output;


import com.mba.app.application.domain.events.OrderCreatedEvent;

/**
 * Porta de SAÍDA para publicação de eventos de domínio.
 *
 * <p>O caso de uso depende desta abstração, e não do Kafka diretamente.
 * O adaptador {@code KafkaOrderEventPublisher} implementa esta interface.
 * Assim, poderíamos trocar Kafka por RabbitMQ, SNS ou um simples log de
 * testes sem alterar uma linha da camada de aplicação.</p>
 */
public interface OrderEventPublisher {

    void publishOrderCreated(OrderCreatedEvent event);
}
