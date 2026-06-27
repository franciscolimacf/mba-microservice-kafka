package com.mba.app.adapters.input.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO de entrada da API REST. Vive na infraestrutura porque é um detalhe
 * de transporte (HTTP/JSON). As anotações de Bean Validation barram
 * requisições mal formatadas ANTES de chegarem ao domínio.
 */
public record CreateOrderRequest(

        @NotBlank(message = "customerId é obrigatório")
        String customerId,

        @NotEmpty(message = "o pedido deve conter ao menos um item")
        @Valid
        List<ItemRequest> items
) {
    public record ItemRequest(

            @NotBlank(message = "sku é obrigatório")
            String sku,

            @Min(value = 1, message = "quantity deve ser maior que zero")
            int quantity,

            @NotNull(message = "unitPrice é obrigatório")
            @DecimalMin(value = "0.0", message = "unitPrice não pode ser negativo")
            BigDecimal unitPrice
    ) {
    }
}
