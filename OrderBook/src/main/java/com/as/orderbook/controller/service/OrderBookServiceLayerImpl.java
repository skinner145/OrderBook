/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.service;

import com.as.orderbook.controller.dao.OrderBookOrderDao;
import com.as.orderbook.controller.dao.OrderBookTradeDao;
import com.as.orderbook.dto.Order;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Skininho
 */
public class OrderBookServiceLayerImpl implements OrderBookServiceLayer{
    private OrderBookOrderDao orderDao;
    private OrderBookTradeDao tradeDao;
    
    //constructor
    public OrderBookServiceLayerImpl(OrderBookOrderDao orderDao, OrderBookTradeDao tradeDao){
        this.orderDao = orderDao;
        this.tradeDao = tradeDao;
    }
    @Override
    public Order addOrder(String orderId, Order newOrder){
        return orderDao.addOrder(orderId, newOrder);
    }

    @Override
    public Order getOrder(String orderId){
        return orderDao.getOrder(orderId);
    }
    
    @Override
    public List<Order> getAllOrders(){
        return orderDao.getAllOrders();
    }
    
    @Override
    public Order removeOrder(String orderId){
        return orderDao.removeOrder(orderId);
    }

    @Override
    public Order editOrder(String orderId, Order editedOrder){
        return orderDao.editOrder(orderId, editedOrder);
    }
    
    @Override
    public Trade addTrade(String tradeId, Trade trade){
        return tradeDao.addTrade(tradeId, trade);
    }
    
    @Override
    public Trade getTrade(String tradeId){
        return tradeDao.getTrade(tradeId);
    }
    
    @Override
    public List<Trade> getAllTrades(){
        return tradeDao.getAllTrades();
    }
    
    @Override
    public Trade removeTrade(String tradeId){
        return tradeDao.removeTrade(tradeId);
    }
    
    @Override
    public Trade editTrade(String tradeId, Trade editedTrade){
        return tradeDao.editTrade(tradeId, editedTrade);
    }
}
