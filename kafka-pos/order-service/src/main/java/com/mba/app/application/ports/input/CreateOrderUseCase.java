package com.mba.app.application.ports.input;



import com.mba.app.application.domain.models.Order;
import com.mba.app.application.domain.models.OrderItem;

import java.util.List;

/**
 * Porta de ENTRADA (driving port) da arquitetura hexagonal.
 *
 * <p>Define o que a aplicação sabe fazer — "criar um pedido" — sem expor
 * COMO. Os adaptadores de entrada (ex.: o controller REST) dependem desta
 * abstração, e não da implementação. Isso atende ao Princípio da Inversão
 * de Dependência (o "D" de SOLID).</p>
 */
public interface CreateOrderUseCase {

    /**
     * Cria um novo pedido a partir dos dados de entrada e publica o
     * evento de domínio correspondente.
     *
     * @param command dados necessários para criar o pedido
     * @return o pedido criado
     */
    Order create(CreateOrderCommand command);

    /**
     * Command object: agrupa os parâmetros de entrada do caso de uso,
     * isolando-o de DTOs de transporte (HTTP, mensageria, etc.).
     */
    record CreateOrderCommand(String customerId, List<OrderItem> items) {
    }
}
