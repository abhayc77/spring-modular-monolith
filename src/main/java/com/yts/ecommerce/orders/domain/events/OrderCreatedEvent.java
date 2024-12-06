package com.yts.ecommerce.orders.domain.events;

import com.yts.ecommerce.orders.domain.models.Customer;
import org.springframework.modulith.events.Externalized;

@Externalized("BookStoreExchange::orders.new")
public record OrderCreatedEvent(String orderNumber, String productCode, int quantity, Customer customer) {}
