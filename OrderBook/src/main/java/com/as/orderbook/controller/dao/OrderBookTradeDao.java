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
    //adds trade to hashmap
    Trade addTrade(String tradeId, Trade trade);
    //get trade by ID
    Trade getTrade(String tradeId);
    //returns list of all trades
    List<Trade> getAllTrades();
    //remove trade by ID
    Trade removeTrade(String tradeId);
    //edit Trade by ID
    Trade editTrade(String tradeId, Trade editedTrade);
    //clears map/list in dao
    void clearDao();
}
