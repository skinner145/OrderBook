/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.dao;

import com.as.orderbook.dto.Order;
import java.util.*;

/**
 *
 * @author Skininho
 */
public class OrderBookOrderDaoFileImpl implements OrderBookOrderDao{
    Map<String, Order>orders = new HashMap<>();
    @Override
    public Order addOrder(String orderId, Order newOrder){ //Temporarily modifying for testing purposes
        System.out.println("Adding " + orderId + ": " + newOrder);
        orders.put(orderId, newOrder);
        return newOrder;
    }

    @Override
    public Order getOrder(String orderId){
        return orders.get(orderId);
    }
    
    @Override
    public List<Order> getAllOrders(){
        return new ArrayList<>(orders.values());
    }
    
    @Override
    public Order removeOrder(String orderId){
        return orders.remove(orderId);
    }

    @Override
    public Order editOrder(String orderId, Order editedOrder){
        return orders.replace(orderId, editedOrder);
    }

    @Override
    public void clearDao() {
        orders.clear();
    }
}
