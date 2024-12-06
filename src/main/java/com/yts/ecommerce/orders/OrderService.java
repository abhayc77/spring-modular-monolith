package com.yts.ecommerce.orders;

import com.yts.ecommerce.orders.domain.models.CreateOrderRequest;
import com.yts.ecommerce.orders.domain.models.CreateOrderResponse;
import com.yts.ecommerce.orders.domain.models.OrderDTO;
import com.yts.ecommerce.orders.domain.models.OrderView;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest request);

    Optional<OrderDTO> findOrder(String orderNumber);

    List<OrderView> findOrders();
}
