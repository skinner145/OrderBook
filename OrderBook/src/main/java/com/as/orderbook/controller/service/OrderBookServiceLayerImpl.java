/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.service;

import com.as.orderbook.controller.dao.OrderBookOrderDao;
import com.as.orderbook.controller.dao.OrderBookTradeDao;
import com.as.orderbook.dto.BuyOrder;
import com.as.orderbook.dto.Order;
import com.as.orderbook.dto.SellOrder;
import com.as.orderbook.dto.Trade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Skininho
 */
public class OrderBookServiceLayerImpl implements OrderBookServiceLayer{
    private OrderBookOrderDao orderDao;
    private OrderBookTradeDao tradeDao;
    
    List<Order> orders = orderDao.getAllOrders();
    List<Order> buyOrders = new ArrayList<>();
    List<Order> sellOrders = new ArrayList<>();
    
    Random rand = new Random();
    //constructor
    public OrderBookServiceLayerImpl(OrderBookOrderDao orderDao, OrderBookTradeDao tradeDao){
        this.orderDao = orderDao;
        this.tradeDao = tradeDao;
    }
    
    @Override
    public void createOrders(){
        for(int i = 0; i < 1000; i++){
            Order buyOrder = new BuyOrder(new BigDecimal(getRandomNum(190)),
                    getRandomNum(20, 50));
            Order sellOrder = new SellOrder(new BigDecimal(getRandomNum(190)),
                    getRandomNum(20, 50));
            orderDao.addOrder(buyOrder.getID(), buyOrder);
            orderDao.addOrder(sellOrder.getID(), sellOrder);
        }
    }
    
    @Override
    public Order addOrder(String orderId, Order newOrder){
        return orderDao.addOrder(orderId, newOrder);
    }

    @Override
    public Order addBuyOrder(String orderId, Order order){
        return orderDao.addOrder(orderId, order);
    }
    
    @Override
    public Order addSellOrder(String orderId, Order order){
        return orderDao.addOrder(orderId, order);
    }
    
    @Override
    public Order getOrder(String orderId){
        return orderDao.getOrder(orderId);
    }
    
    @Override
    public List<List<Order>> getAllOrders(){
        clearService();
        
        orders.forEach(order -> {
            if(order instanceof BuyOrder){
                buyOrders.add(order);
            }
            else{
                sellOrders.add(order);
            }
        });
        List<List<Order>> allOrders = new ArrayList<List<Order>>();
        
        buyOrders.sort((Order o1, Order o2) -> o1.getPrice().compareTo(o2.getPrice()));
        sellOrders.sort((Order o1, Order o2) -> o1.getPrice().compareTo(o2.getPrice()));
        allOrders.add(buyOrders);
        allOrders.add(sellOrders);
        return allOrders;
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
    
    public int getRandomNum(int min, int max){
        return rand.nextInt((max+1) - min ) + min;
    }
    public double getRandomNum(int min){
        return rand.nextDouble() + min;
    }

    @Override
    public int getNumOfSellOrders() {
        return sellOrders.size();
    }

    @Override
    public int getNumOfBuyOrders() {
        return buyOrders.size();
    }

    @Override
    public int getSellQuantity() {
        int count = 0;
        for(Order order : sellOrders){
            count += order.getQuantity();
        }
        return count;
    }

    @Override
    public int getBuyQuantity() {
        int count = 0;
        for(Order order : buyOrders){
            count += order.getQuantity();
        }
        return count;
    }

    @Override
    public BigDecimal getAverageSellPrice() {
        BigDecimal price = new BigDecimal("0");
        for(Order order : sellOrders){
            price.add(order.getPrice());
        }
        return price.divide(new BigDecimal(getNumOfSellOrders()));
    }

    @Override
    public BigDecimal getAverageBuyPrice() {
        BigDecimal price = new BigDecimal("0");
        for(Order order : buyOrders){
            price.add(order.getPrice());
        }
        return price.divide(new BigDecimal(getNumOfBuyOrders()));
    }
    
    @Override
    public String displayStats(){
        return "Number of Sell Orders: " + getNumOfSellOrders() + 
                " - Number of Buy Orders: " + getNumOfBuyOrders() +
                " - Overall Sell Quantity: " + getSellQuantity() + 
                " - Overall Buy Quantity: " + getBuyQuantity() + 
                " - Average Sale Price: " + getAverageSellPrice() +
                " - Average Buy Price: " + getAverageBuyPrice();
    }
    
    @Override
    public void clearService(){
        orders.clear();
        buyOrders.clear();
        sellOrders.clear();
    }
    
    @Override
    public boolean checkIfEmpty(){
        if(buyOrders.size() > 0 && sellOrders.size() > 0){
            return false;
        }
        else{
            return true;
        }
    }
    
    @Override
    public Trade matchOrder(){
        getAllOrders();
        Order buy = buyOrders.get(0);
        Order sell = sellOrders.get(0);
        int quantity = getSmallest(buy.getQuantity(), sell.getQuantity());
        BigDecimal price = sell.getPrice();
        buy.setQuantity(buy.getQuantity() - quantity);
        sell.setQuantity(sell.getQuantity() - quantity);
        updateAfterMatch(buy, sell);
        Trade trade = new Trade(buy, sell, price);
        return tradeDao.addTrade(trade.getID(), trade);
    }
    
    @Override
    public void matchAllOrders(){
        while(!checkIfEmpty()){
            matchOrder();
        }
    }
    
    public void updateAfterMatch(Order buy, Order sell){
        if(buy.getQuantity() == 0){
            orderDao.removeOrder(buy.getID());
            orderDao.editOrder(sell.getID(), sell);
        }
        else{
            orderDao.removeOrder(sell.getID());
            orderDao.editOrder(buy.getID(), buy);
        }
    }
    
    public int getSmallest(int a, int b){
        if(a > b){
            return b;
        }else{
            return a;
        }
    }
}
