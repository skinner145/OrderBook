/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.service;

import com.as.orderbook.dto.Order;
import com.as.orderbook.dto.Trade;
import java.util.List;

/**
 *
 * @author Skininho
 */
public interface OrderBookServiceLayer {
    Order addOrder(String orderId, Order order);
    Order getOrder(String orderId);
    List<Order> getAllOrders();
    Order removeOrder(String orderId);
    Order editOrder(String orderId, Order editedOrder);
    Trade addTrade(String tradeId, Trade trade);
    Trade getTrade(String tradeId);
    List<Trade> getAllTrades();
    Trade removeTrade(String tradeId);
    Trade editTrade(String tradeId, Trade editedTrade);
}
