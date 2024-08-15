package com.ribeiro.BtgPactualChallenge.controller.dto;

import com.ribeiro.BtgPactualChallenge.entity.OrderEntity;

import java.math.BigDecimal;

public record OrderResponse(
        Long orderId,
        Long costumerId,
        BigDecimal total
) {

    public static OrderResponse fromEntity(OrderEntity entity) {
        return new OrderResponse(entity.getOrderId(), entity.getCustomerId(), entity.getTotal());
    }
}
