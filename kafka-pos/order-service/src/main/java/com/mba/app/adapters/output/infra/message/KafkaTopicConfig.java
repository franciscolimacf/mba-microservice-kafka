package com.mba.app.adapters.output.infra.message;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Cria automaticamente o tópico de pedidos na subida da aplicação
 * (via {@code KafkaAdmin}, presente quando há spring-kafka no classpath).
 *
 * <p>Definimos partições e fator de replicação explicitamente:
 * <ul>
 *   <li><b>partições</b> habilitam o paralelismo de consumo — cada
 *       partição é processada por no máximo um consumidor do grupo;</li>
 *   <li><b>replicação</b> dá tolerância a falhas (aqui 1, pois é um
 *       broker único de desenvolvimento; em produção use 3).</li>
 * </ul>
 */
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic ordersCreatedTopic(
            @Value("${app.kafka.topics.orders-created}") String topicName) {
        return TopicBuilder.name(topicName)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
