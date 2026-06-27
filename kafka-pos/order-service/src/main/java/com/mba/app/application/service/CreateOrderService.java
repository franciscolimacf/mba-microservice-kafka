package com.mba.app.application.service;

import com.mba.app.application.domain.events.OrderCreatedEvent;
import com.mba.app.application.domain.models.Order;
import com.mba.app.application.ports.input.CreateOrderUseCase;
import com.mba.app.application.ports.output.OrderEventPublisher;
import com.mba.app.application.ports.output.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do caso de uso de criação de pedido.
 *
 * <p>Orquestra o fluxo da regra de negócio:
 * <ol>
 *   <li>cria o agregado {@link Order} (que valida a si mesmo);</li>
 *   <li>persiste através da porta {@link OrderRepository};</li>
 *   <li>publica o evento via porta {@link OrderEventPublisher}.</li>
 * </ol>
 *
 * <p>Note que o serviço depende apenas de <b>abstrações</b> (as portas),
 * recebidas por injeção de construtor. Ele não conhece HTTP nem Kafka —
 * essa é a separação de responsabilidades (SRP) que torna o caso de uso
 * unitariamente testável com mocks simples.</p>
 */
@Service
public class CreateOrderService implements CreateOrderUseCase {

    private static final Logger log = LoggerFactory.getLogger(CreateOrderService.class);

    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;

    public CreateOrderService(OrderRepository orderRepository,
                              OrderEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Order create(CreateOrderCommand command) {
        // 1. Criação do agregado: as invariantes de negócio são garantidas aqui.
        Order order = Order.create(command.customerId(), command.items());

        // 2. Persistência através da porta de saída.
        Order saved = orderRepository.save(order);
        log.info("Pedido persistido: id={} total={}", saved.getId(), saved.getTotalAmount());

        // 3. Publicação do evento de domínio.
        eventPublisher.publishOrderCreated(toEvent(saved));
        log.info("Evento OrderCreated publicado: orderId={}", saved.getId());

        return saved;
    }

    /** Traduz o agregado de domínio para o contrato de evento. */
    private OrderCreatedEvent toEvent(Order order) {
        var items = order.getItems().stream()
                .map(i -> new OrderCreatedEvent.Item(i.sku(), i.quantity(), i.unitPrice()))
                .toList();

        return new OrderCreatedEvent(
                UUID.randomUUID(),
                order.getId(),
                order.getCustomerId(),
                items,
                order.getTotalAmount(),
                order.getCreatedAt()
        );
    }
}
