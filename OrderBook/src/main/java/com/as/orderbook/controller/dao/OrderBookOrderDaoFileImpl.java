/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.dao;

import com.as.orderbook.dto.BuyOrder;
import com.as.orderbook.dto.Order;
import com.as.orderbook.dto.SellOrder;
import java.math.BigDecimal;
import java.util.*;
import org.springframework.stereotype.Component;

/**
 *
 * @author Skininho
 */

public class OrderBookOrderDaoFileImpl implements OrderBookOrderDao{
    //map to store all orders in 
    Map<String, Order>orders = new HashMap<>();
    Order buyOrder = new BuyOrder(new BigDecimal("190"),10);
    
    //method that adds order and ID to hashmap
    @Override
    public Order addOrder(String orderId, Order newOrder){
        orders.put(orderId, newOrder);
        return newOrder;
    }

    //gets order with matching ID from hashmap
    @Override
    public Order getOrder(String orderId){
        return orders.get(orderId);
    }
    
    //returns list of all orders from orders hashmap
    @Override
    public List<Order> getAllOrders(){
        return new ArrayList<>(orders.values());
    }
    
    //removes order using orderID
    @Override
    public Order removeOrder(String orderId){
        return orders.remove(orderId);
    }

    //replaces the order with matching orderID with editedOrder object
    @Override
    public Order editOrder(String orderId, Order editedOrder){
        System.out.println(orders.size());
        System.out.println("DAO: " + editedOrder.toString());
        System.out.println(orders.get(orderId));
        orders.replace(orderId, editedOrder);
        System.out.println(orders.get(orderId));
        return editedOrder;
    }

    //clears map
    @Override
    public void clearDao() {
        orders.clear();
    }
}
