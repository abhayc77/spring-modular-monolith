package com.yts.ecommerce.orders.domain;

import com.yts.ecommerce.catalog.ProductService;
import com.yts.ecommerce.orders.InvalidOrderException;
import com.yts.ecommerce.orders.OrderService;
import com.yts.ecommerce.orders.domain.events.OrderCreatedEvent;
import com.yts.ecommerce.orders.domain.models.CreateOrderRequest;
import com.yts.ecommerce.orders.domain.models.CreateOrderResponse;
import com.yts.ecommerce.orders.domain.models.OrderDTO;
import com.yts.ecommerce.orders.domain.models.OrderView;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final ApplicationEventPublisher eventPublisher;

    OrderServiceImpl(
            OrderRepository orderRepository, ProductService productService, ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        validate(request);
        OrderEntity newOrder = OrderMapper.convertToEntity(request);
        OrderEntity savedOrder = this.orderRepository.save(newOrder);
        log.info("Created Order with orderNumber={}", savedOrder.getOrderNumber());
        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getOrderNumber(),
                savedOrder.getOrderItem().code(),
                savedOrder.getOrderItem().quantity(),
                savedOrder.getCustomer());
        eventPublisher.publishEvent(event);
        return new CreateOrderResponse(savedOrder.getOrderNumber());
    }

    private void validate(CreateOrderRequest request) {
        String code = request.item().code();
        var product = productService
                .getByCode(code)
                .orElseThrow(() -> new InvalidOrderException("Product not found with code: " + code));
        if (product.price().compareTo(request.item().price()) != 0) {
            throw new InvalidOrderException("Product price mismatch");
        }
    }

    @Override
    public Optional<OrderDTO> findOrder(String orderNumber) {
        Optional<OrderEntity> byOrderNumber = orderRepository.findByOrderNumber(orderNumber);
        if (byOrderNumber.isEmpty()) {
            return Optional.empty();
        }
        OrderEntity orderEntity = byOrderNumber.get();
        var orderDTO = OrderMapper.convertToDTO(orderEntity);
        return Optional.of(orderDTO);
    }

    @Override
    public List<OrderView> findOrders() {
        Sort sort = Sort.by("id").descending();
        var orders = orderRepository.findAllBy(sort);
        return buildOrderViews(orders);
    }

    private List<OrderView> buildOrderViews(List<OrderEntity> orders) {
        List<OrderView> orderViews = new ArrayList<>();
        for (OrderEntity order : orders) {
            var orderView = new OrderView(order.getOrderNumber(), order.getStatus(), order.getCustomer());
            orderViews.add(orderView);
        }
        return orderViews;
    }
}
