/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.service;

import com.as.orderbook.dto.Order;
import com.as.orderbook.dto.Trade;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Skininho
 */
public interface OrderBookServiceLayer {
    void createOrders();
    Order addOrder(String orderId, Order order);
    Order getOrder(String orderId);
    List<List<Order>> getAllOrders();
    Order removeOrder(String orderId);
    Order editOrder(String orderId, Order editedOrder);
    Trade addTrade(String tradeId, Trade trade);
    Trade getTrade(String tradeId);
    List<Trade> getAllTrades();
    Trade removeTrade(String tradeId);
    Trade editTrade(String tradeId, Trade editedTrade);
    int getNumOfSellOrders();
    int getNumOfBuyOrders();
    int getSellQuantity();
    int getBuyQuantity();
    BigDecimal getAverageSellPrice();
    BigDecimal getAverageBuyPrice();
    String displayStats();
    void clearService();
    boolean checkIfEmpty();
    Trade matchOrder();
    void matchAllOrders();
}
