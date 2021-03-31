/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.dao;

import com.as.orderbook.dto.Order;
import java.util.List;

/**
 *
 * @author Skininho
 */
public interface OrderBookOrderDao {
    //add order
    Order addOrder(String orderId, Order order);
    //get order by ID
    Order getOrder(String orderId);
    //get list of all orders
    List<Order> getAllOrders();
    //remove order by ID
    Order removeOrder(String orderId);
    //edit oreder by ID
    Order editOrder(String orderId, Order editedOrder);
    //clear dao lists/maps
    void clearDao();
}
