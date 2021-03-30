/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.dao;

import java.util.*;

/**
 *
 * @author Skininho
 */
public interface OrderBookTradeDao {
    Trade addTrade(String tradeId, Trade trade);
    Trade getTrade(String tradeId);
    List<Trade> getAllTrades();
    Trade removeTrade(String tradeId);
    Trade editTrade(String tradeId, Trade editedTrade);
}
