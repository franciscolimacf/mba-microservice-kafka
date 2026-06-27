package com.mba.app.adapters.output.infra.message;


import com.mba.app.application.domain.events.OrderCreatedEvent;
import com.mba.app.application.domain.exception.EventPublishingException;
import com.mba.app.application.ports.output.OrderEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Adaptador de SAÍDA que implementa {@link OrderEventPublisher} usando Kafka.
 *
 * <p>É o ÚNICO ponto do producer que conhece o {@link KafkaTemplate}.
 * Toda a complexidade de mensageria fica isolada aqui, na borda da
 * aplicação (princípio de "infraestrutura nas extremidades").</p>
 *
 * <p>Usamos o {@code orderId} como CHAVE da mensagem. A chave determina
 * a partição de destino, garantindo que todos os eventos de um mesmo
 * pedido caiam na mesma partição e, portanto, sejam consumidos em ORDEM.</p>
 */
@Component
public class KafkaOrderEventPublisher implements OrderEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaOrderEventPublisher.class);

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;
    private final String topic;

    public KafkaOrderEventPublisher(
            KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate,
            @Value("${app.kafka.topics.orders-created}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void publishOrderCreated(OrderCreatedEvent event) {
        String key = event.orderId().toString();
        try {
            kafkaTemplate.send(topic, key, event)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Falha ao publicar evento orderId={}", key, ex);
                        } else {
                            var meta = result.getRecordMetadata();
                            log.info("Evento publicado em {}-{} offset={}",
                                    meta.topic(), meta.partition(), meta.offset());
                        }
                    });
        } catch (Exception ex) {
            throw new EventPublishingException(
                    "Erro ao publicar OrderCreatedEvent para orderId=" + key, ex);
        }
    }
}
