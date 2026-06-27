package com.mba.app.adapters.input.controller;

import com.mba.app.adapters.input.dto.CreateOrderRequest;
import com.mba.app.adapters.input.dto.OrderResponse;
import com.mba.app.application.domain.models.Order;
import com.mba.app.application.domain.models.OrderItem;
import com.mba.app.application.ports.input.CreateOrderUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Adaptador de ENTRADA (driving adapter) HTTP.
 *
 * <p>Responsabilidade única: traduzir a requisição HTTP para o
 * {@link CreateOrderCommand} do caso de uso e o resultado de volta para
 * JSON. Não contém regra de negócio — apenas tradução de fronteira.</p>
 *
 * <p>Depende da PORTA {@link CreateOrderUseCase}, nunca da implementação
 * concreta. O Spring injeta o {@code CreateOrderService} em tempo de
 * execução.</p>
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody CreateOrderRequest request) {
        var items = request.items().stream()
                .map(i -> new OrderItem(i.sku(), i.quantity(), i.unitPrice()))
                .toList();

        Order order = createOrderUseCase.create(
                new CreateOrderUseCase.CreateOrderCommand(request.customerId(), items));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OrderResponse.from(order));
    }
}
