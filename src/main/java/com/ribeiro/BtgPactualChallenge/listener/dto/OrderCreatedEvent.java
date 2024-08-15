package com.ribeiro.BtgPactualChallenge.listener.dto;

import java.util.List;

public record OrderCreatedEvent(
        Long codigoPedido,
        Long codigoCliente,
        List<OrderItemEvent> items
) {
}
