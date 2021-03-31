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
public class OrderBookTradeDaoFileImpl implements OrderBookTradeDao{
    //map for trades
    Map<String, Trade>trades = new HashMap<>();
    
    //adds trade to map
    @Override
    public Trade addTrade(String tradeId, Trade trade){
        return trades.put(tradeId, trade);
    }
    
    //gets trade from map by ID
    @Override
    public Trade getTrade(String tradeId){
        return trades.get(tradeId);
    }
    
    //returns list of all trade objects from trades map
    @Override
    public List<Trade> getAllTrades(){
        return new ArrayList<>(trades.values());
    }
    
    //remove Trade from map with matching Trade ID
    @Override
    public Trade removeTrade(String tradeId){
        return trades.remove(tradeId);
    }
    
    //replace trade object with ediedTrade object at matching trade ID
    @Override
    public Trade editTrade(String tradeId, Trade editedTrade){
        return trades.replace(tradeId, editedTrade);
    }

    //clears map
    @Override
    public void clearDao() {
        trades.clear();
    }
}
