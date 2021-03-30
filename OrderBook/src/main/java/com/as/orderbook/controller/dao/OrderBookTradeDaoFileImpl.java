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
    Map<String, Trade>trades = new HashMap<>();
    
    @Override
    public Trade addTrade(String tradeId, Trade trade){
        return trades.put(tradeId, trade);
    }
    
    @Override
    public Trade getTrade(String tradeId){
        return trades.get(tradeId);
    }
    
    @Override
    public List<Trade> getAllTrades(){
        return new ArrayList<>(trades.values());
    }
    
    @Override
    public Trade removeTrade(String tradeId){
        return trades.remove(tradeId);
    }
    
    @Override
    public Trade editTrade(String tradeId, Trade editedTrade){
        return trades.replace(tradeId, editedTrade);
    }
    @Override
    public void clearDao() {
        trades.clear();
    }
}
