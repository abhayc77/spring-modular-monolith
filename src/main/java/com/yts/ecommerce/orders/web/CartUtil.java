package com.yts.ecommerce.orders.web;

import jakarta.servlet.http.HttpSession;

public class CartUtil {
    public static Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        return cart;
    }
}
