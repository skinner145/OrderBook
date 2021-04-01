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
    /**
     * Creates buy and sell orders
     * @param orderNum the number of orders of each type to create
     * @throws OrderBookOrderException if the added order is invalid
     */
    void createOrders(int orderNum) throws OrderBookOrderException;
    /**
     * Wraps the addOrder method for the DAO
     * @param orderId the ID of the order object to be added
     * @param order the order object to be added
     * @return the added order
     */
    Order addOrder(String orderId, Order order);
    /**
     * Wraps the getOrder method for the DAO
     * @param orderId the ID of the order being fetched
     * @return the order
     * @throws OrderBookOrderException if the order is invalid
     */
    Order getOrder(String orderId) throws OrderBookOrderException;
    /**
     * Returns lists of orders sorted by price
     * @return a list consisting of 2 sorted lists of orders (buy orders, sell orders)
     * @throws OrderBookOrderException if one of the orders is invalid
     */
    List<List<Order>> getAllOrders() throws OrderBookOrderException;
    /**
     * Wraps the removeOrder method for the DAO
     * @param orderId the ID of the order being removed
     * @return the order being removed
     */
    Order removeOrder(String orderId);
    /**
     * Wraps the editOrder method of the DAO
     * @param orderId the ID of the order being edited
     * @param editedOrder the order the existing order is being updated to
     * @return the updated order
     */
    Order editOrder(String orderId, Order editedOrder);
    /**
     * Wraps the addTrade method of the DAO
     * @param tradeId the ID of the trade object to be added
     * @param trade the trade object to be added
     * @return the added trade
     */
    Trade addTrade(String tradeId, Trade trade);
    /**
     * Wraps the getTrade method of the DAO
     * @param tradeId the ID of the trade being fetched
     * @return the trade
     * @throws OrderBookTradeException if the trade is invalid
     */
    Trade getTrade(String tradeId) throws OrderBookTradeException;
    /**
     * Returns a list of trades sorted by execution time
     * @return a sorted list of trades
     */
    List<Trade> getAllTrades();
    /**
     * Wraps the removeTrade method of the DAO
     * @param tradeId the ID of the trade being removed
     * @return the trade being removed
     */
    Trade removeTrade(String tradeId);
    /**
     * Wraps the editTrade method of the DAO
     * @param tradeId the ID of the trade being edited
     * @param editedTrade the trade the existing trade is being updated to
     * @return the updated trade
     */
    Trade editTrade(String tradeId, Trade editedTrade);
    /**
     * Counts the number of sell orders in the DAO
     * @return the number of sell orders
     */
    int getNumOfSellOrders();
    /**
     * Counts the number of buy orders in the DAO
     * @return the number of buy orders
     */
    int getNumOfBuyOrders();
    /**
     * Sums the order quantity for each sell order in the DAO
     * @return the sell order quantity
     */
    int getSellQuantity();
    /**
     * Sums the order quantity for each buy order in the DAO
     * @return the buy order quantity
     */
    int getBuyQuantity();
    /**
     * Sums the price for each sell order in the DAO, and divides by the number of orders
     * @return the average sell price
     */
    BigDecimal getAverageSellPrice();
    /**
     * Sums the price for each buy order in the DAO, and divides by the number of orders
     * @return the average buy price
     */
    BigDecimal getAverageBuyPrice();
    /**
     * Converts getNumOfSellOrders, getNumOfBuyOrders, getSellQuantity, getBuyQuantity, getAverageSellPrice, getAverageBuyPrice into one string
     * @return string showing various stats about the DAO
     */
    String displayStats();
    /**
     * Wraps the clearDao methods
     */
    void clearService();
    /**
     * Checks if the orderDao is empty
     * @return true if the orderDao is empty, false otherwise
     */
    boolean checkIfEmpty();
    /**
     * Matches a buy order with a sell order and adds it to the list of trades
     * @param buyList a sorted list of the buy orders to use
     * @param sellList a sorted list of the sell orders to use
     * @return the resulting trade
     * @throws OrderBookTradeException if the created trade is invalid
     */
    Trade matchOrder(List<Order> buyList, List<Order> sellList) throws OrderBookTradeException;
    /**
     * Runs the matchOrder method until there are no possible trades left
     * @throws OrderBookTradeException if one of the created trades is invalid
     * @throws OrderBookOrderException if one of the orders is invalid
     */
    void matchAllOrders()throws OrderBookTradeException, OrderBookOrderException;
}
