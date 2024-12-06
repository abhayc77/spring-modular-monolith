package com.yts.ecommerce.orders.domain;

import com.yts.ecommerce.orders.domain.models.CreateOrderRequest;
import com.yts.ecommerce.orders.domain.models.OrderDTO;
import com.yts.ecommerce.orders.domain.models.OrderStatus;

import java.util.UUID;

final class OrderMapper {
    private OrderMapper() {}

    static OrderEntity convertToEntity(CreateOrderRequest request) {
        OrderEntity entity = new OrderEntity();
        entity.setOrderNumber(UUID.randomUUID().toString());
        entity.setStatus(OrderStatus.NEW);
        entity.setCustomer(request.customer());
        entity.setDeliveryAddress(request.deliveryAddress());
        entity.setOrderItem(request.item());
        return entity;
    }

    static OrderDTO convertToDTO(OrderEntity order) {
        return new OrderDTO(
                order.getOrderNumber(),
                order.getOrderItem(),
                order.getCustomer(),
                order.getDeliveryAddress(),
                order.getStatus(),
                order.getCreatedAt());
    }
}
