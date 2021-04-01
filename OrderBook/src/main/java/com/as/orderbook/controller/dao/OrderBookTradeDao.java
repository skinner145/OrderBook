/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.dao;

import com.as.orderbook.dto.Trade;
import java.util.*;

/**
 *
 * @author Skininho
 */
public interface OrderBookTradeDao {
    /**
     * Adds a trade object into the DAO
     * @param tradeId the ID of the trade object to be added
     * @param trade the trade object to be added
     * @return the added trade
     */
    Trade addTrade(String tradeId, Trade trade);
    /**
     * Gets an individual trade based on the ID
     * @param tradeId the ID of the trade being fetched
     * @return the trade
     */
    Trade getTrade(String tradeId);
    /**
     * Gets a list of all the trades in the DAO
     * @return a list of all the trades in the DAO
     */
    List<Trade> getAllTrades();
    /**
     * Removes an individual trade based on the ID
     * @param tradeId the ID of the trade being removed
     * @return the trade being removed
     */
    Trade removeTrade(String tradeId);
    /**
     * Edits a trade based on the ID
     * @param tradeId the ID of the trade being edited
     * @param editedTrade the trade the existing trade is being updated to
     * @return the updated trade
     */
    Trade editTrade(String tradeId, Trade editedTrade);
    /**
     * Removes all trades in the DAO
     */
    void clearDao();
}
