package com.yts.ecommerce.orders.domain.models;

public record OrderView(String orderNumber, OrderStatus status, Customer customer) {}
