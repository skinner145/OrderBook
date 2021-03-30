/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.dao;

/**
 *
 * @author Skininho
 */
public interface OrderBookOrderDao {
    Order addOrder(String orderId, Order order);
    Order getOrder(String orderId);
    List<Order> getAllOrders();
    Order removeOrder(String orderId);
    Order editOrder(String orderId, Order editedOrder);
}
