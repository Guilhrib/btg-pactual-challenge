package com.ribeiro.BtgPactualChallenge.controller;

import com.ribeiro.BtgPactualChallenge.controller.dto.ApiResponse;
import com.ribeiro.BtgPactualChallenge.controller.dto.OrderResponse;
import com.ribeiro.BtgPactualChallenge.controller.dto.PaginationResponse;
import com.ribeiro.BtgPactualChallenge.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/costumer")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{costumerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrders(
            @PathVariable("costumerId") Long costumerId,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        var response = orderService.findAllByCostumerId(costumerId, PageRequest.of(page, pageSize));
        var totalOnOrders = orderService.findTotalOnOrdersByCustomerId(costumerId);

        return ResponseEntity.ok().body(new ApiResponse<>(
                Map.of("totalOnOrders", totalOnOrders),
                response.getContent(),
                PaginationResponse.fromPage(response)
        ));
    }
}
