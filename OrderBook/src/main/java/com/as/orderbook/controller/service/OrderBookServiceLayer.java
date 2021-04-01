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
    //method for creating buy and sell orders
    void createOrders(int orderNum) throws OrderBookOrderException;
    //add order
    Order addOrder(String orderId, Order order);
    //add buy order
    Order getOrder(String orderId) throws OrderBookOrderException;
    //gets list of buy orders and list of sell orders
    List<List<Order>> getAllOrders() throws OrderBookOrderException;
    //remove order by ID
    Order removeOrder(String orderId);
    //edit order by ID
    Order editOrder(String orderId, Order editedOrder);
    //add trade
    Trade addTrade(String tradeId, Trade trade);
    //get trade by ID
    Trade getTrade(String tradeId);
    //get list of all trades
    List<Trade> getAllTrades();
    //remove Trade by ID
    Trade removeTrade(String tradeId);
    //edit Trade by ID
    Trade editTrade(String tradeId, Trade editedTrade);
    //get number of sell orders
    int getNumOfSellOrders();
    //get number of buy orders
    int getNumOfBuyOrders();
    //get total quantity of all sell orders
    int getSellQuantity();
    //get total quantity of all buy orders
    int getBuyQuantity();
    //get average price of sell orders
    BigDecimal getAverageSellPrice();
    //get average price of buy orders
    BigDecimal getAverageBuyPrice();
    //method to convert previous 6 methods to String
    String displayStats();
    //clear lists in service layer
    void clearService();
    //check if orderbook is empty
    boolean checkIfEmpty();
    //match order
    Trade matchOrder(List<Order> buyList, List<Order> sellList) throws OrderBookTradeException;
    //match all orders
    void matchAllOrders()throws OrderBookTradeException, OrderBookOrderException;
}
