package com.order.controller;

import com.order.entity.Order;
import com.order.model.CommonResponse;
import com.order.model.OrderRequest;
import com.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CommonResponse<Order>> createOrder(@Valid @RequestBody OrderRequest request) {
        try {
            Order order = orderService.createOrder(request);

            CommonResponse<Order> response = new CommonResponse<>(
                    true,
                    HttpStatus.CREATED.value(),
                    "Order created successfully",
                    order,
                    null
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            CommonResponse<Order> response = new CommonResponse<>(
                    false,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to create order",
                    null,
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
    @GetMapping
    public ResponseEntity<CommonResponse<List<Order>>> getAllOrders() {

        List<Order> orders = (List<Order>) orderService.getAllOrders();

        CommonResponse<List<Order>> response = new CommonResponse<>(
                true,
                HttpStatus.OK.value(),
                "Orders fetched successfully",
                orders,
                null
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<Order>> getOrderById(@PathVariable Long id) {
        try {
            Order order = orderService.getOrderById(id);

            CommonResponse<Order> response = new CommonResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    "Order fetched successfully",
                    order,
                    null
            );

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            CommonResponse<Order> response = new CommonResponse<>(
                    false,
                    HttpStatus.NOT_FOUND.value(),
                    "Order not found",
                    null,
                    e.getMessage()
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
