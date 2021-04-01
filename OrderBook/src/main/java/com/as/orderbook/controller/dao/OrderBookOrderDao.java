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
    /**
     * Adds an order object into the DAO
     * @param orderId the ID of the order object to be added
     * @param order the order object to be added
     * @return the added order
     */
    Order addOrder(String orderId, Order order);
    /**
     * Gets an individual order based on the ID
     * @param orderId the ID of the order being fetched
     * @return the order 
     */
    Order getOrder(String orderId);
    /**
     * Gets a list of all the orders in the DAO
     * @return a list of all the orders in the DAO
     */
    List<Order> getAllOrders();
    /**
     * Removes an individual order based on the ID
     * @param orderId the ID of the order being removed
     * @return the order being removed
     */
    Order removeOrder(String orderId);
    /**
     * Edits an order based on the ID
     * @param orderId the ID of the order being edited
     * @param editedOrder the order the existing order is being updated to
     * @return the updated order
     */
    Order editOrder(String orderId, Order editedOrder);
    /**
     * Removes all orders in the DAO
     */
    void clearDao();
}
