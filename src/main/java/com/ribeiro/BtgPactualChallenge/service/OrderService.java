package com.ribeiro.BtgPactualChallenge.service;

import com.ribeiro.BtgPactualChallenge.controller.dto.OrderResponse;
import com.ribeiro.BtgPactualChallenge.entity.OrderEntity;
import com.ribeiro.BtgPactualChallenge.entity.OrderItem;
import com.ribeiro.BtgPactualChallenge.listener.dto.OrderCreatedEvent;
import com.ribeiro.BtgPactualChallenge.repository.OrderRepository;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    public OrderService(
            OrderRepository orderRepository,
            MongoTemplate mongoTemplate
    ) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void save(OrderCreatedEvent event) {
        var entity = new OrderEntity();
        entity.setOrderId(event.codigoPedido());
        entity.setCustomerId(event.codigoCliente());
        entity.setItems(getOrderItems(event));
        entity.setTotal(getOrderTotal(event));
        orderRepository.save(entity);
    }

    public Page<OrderResponse> findAllByCostumerId(Long costumerId, PageRequest pageRequest) {
        var orders = orderRepository.findAllByCostumerId(costumerId, pageRequest);
        return orders.map(OrderResponse::fromEntity);
    }

    public BigDecimal findTotalOnOrdersByCustomerId(Long costumerId) {
        var aggregations = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("costumerId").is(costumerId)),
                Aggregation.group().sum("total").as("total")
        );

        var response = mongoTemplate.aggregate(aggregations, "tb_orders", Document.class);
        return new BigDecimal(response.getUniqueMappedResult().getOrDefault("total", BigDecimal.ZERO).toString());
    }

    private BigDecimal getOrderTotal(OrderCreatedEvent event) {
        return event.items().stream()
                .map(item -> item.preco().multiply(BigDecimal.valueOf(item.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private List<OrderItem> getOrderItems(OrderCreatedEvent event) {
        return event.items().stream()
                .map(item -> new OrderItem(item.produto(), item.quantidade(), item.preco()))
                .toList();
    }
}
