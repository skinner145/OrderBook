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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Skininho
 */

public class OrderBookServiceLayerImpl implements OrderBookServiceLayer{
    //member fields for dao
    private OrderBookOrderDao orderDao;
    private OrderBookTradeDao tradeDao;
    
    //constructor

    public OrderBookServiceLayerImpl(OrderBookOrderDao orderDao, OrderBookTradeDao tradeDao) {
        this.orderDao = orderDao;
        this.tradeDao = tradeDao;
    }
    
    //list for orders
    List<Order> orders = new ArrayList<>();
    List<Order> buyOrders = new ArrayList<>();
    List<Order> sellOrders = new ArrayList<>();
    
    //random
    Random rand = new Random();
    
    
    //method that creates 1000 buy and sell orders
    @Override
    public void createOrders(){
        for(int i = 0; i < 10; i++){
            
            Order buyOrder = new BuyOrder(new BigDecimal(getRandomNum(190)),
                    getRandomNum(20, 50));
            Order sellOrder = new SellOrder(new BigDecimal(getRandomNum(190)),
                    getRandomNum(20, 50));
            orderDao.addOrder(buyOrder.getID(), buyOrder);
            orderDao.addOrder(sellOrder.getID(), sellOrder);
        }
        orders = orderDao.getAllOrders();
    }
    
    //add's order to map in dao
    @Override
    public Order addOrder(String orderId, Order newOrder){
        return orderDao.addOrder(orderId, newOrder);
    }

    //add's buy order to map in dao
    @Override
    public Order addBuyOrder(String orderId, Order order){
        return orderDao.addOrder(orderId, order);
    }
    
    //add's sell order to map in dao
    @Override
    public Order addSellOrder(String orderId, Order order){
        return orderDao.addOrder(orderId, order);
    }
    
    //get order by ID
    @Override
    public Order getOrder(String orderId){
        return orderDao.getOrder(orderId);
    }
    
    //returns a list of lists - one for buy orders - one for sell orders
    @Override
    public List<List<Order>> getAllOrders(){
        orders.forEach(order -> {
            //if order is a buy order add to buyOrders list
            if(order instanceof BuyOrder){
                buyOrders.add(order);
            }
            //else add it to sellOrders list
            else{
                sellOrders.add(order);
            }
        });
        //list that will be returned
        List<List<Order>> allOrders = new ArrayList<List<Order>>();
        //sort lists
        buyOrders.sort((Order o1, Order o2) -> o1.getPrice().compareTo(o2.getPrice()));
        sellOrders.sort((Order o1, Order o2) -> o1.getPrice().compareTo(o2.getPrice()));
        //add each list to allOrders list
        allOrders.add(buyOrders);
        allOrders.add(sellOrders);
        
        //return list
        return allOrders;
    }
    
    //remove Order by ID
    @Override
    public Order removeOrder(String orderId){
        return orderDao.removeOrder(orderId);
    }
    
    //edit Order by ID
    @Override
    public Order editOrder(String orderId, Order editedOrder){
        return orderDao.editOrder(orderId, editedOrder);
    }
    
    //add Trade to map in dao
    @Override
    public Trade addTrade(String tradeId, Trade trade){
        return tradeDao.addTrade(tradeId, trade);
    }
    
    //get Trade by ID
    @Override
    public Trade getTrade(String tradeId){
        return tradeDao.getTrade(tradeId);
    }
    
    //returns list of all Trades
    @Override
    public List<Trade> getAllTrades(){
        return tradeDao.getAllTrades();
    }
    
    //remove Trade by ID
    @Override
    public Trade removeTrade(String tradeId){
        return tradeDao.removeTrade(tradeId);
    }
    
    //edit Trade by ID
    @Override
    public Trade editTrade(String tradeId, Trade editedTrade){
        return tradeDao.editTrade(tradeId, editedTrade);
    }
    //gets int within range (inclusive)
    public int getRandomNum(int min, int max){
        return rand.nextInt((max+1) - min ) + min;
    }
    
    //gets double between min - (min + 1)
    public double getRandomNum(int min){
        return rand.nextDouble() + min;
    }

    //returns number of sell orders
    @Override
    public int getNumOfSellOrders() {
        return sellOrders.size();
    }

    //returns number of buy orders
    @Override
    public int getNumOfBuyOrders() {
        return buyOrders.size();
    }

    //adds quantity of all sell orders together and returns them
    @Override
    public int getSellQuantity() {
        int count = 0;
        for(Order order : sellOrders){
            count += order.getQuantity();
        }
        return count;
    }

    //adds quantity of all buy orders together and returns them
    @Override
    public int getBuyQuantity() {
        int count = 0;
        for(Order order : buyOrders){
            count += order.getQuantity();
        }
        return count;
    }

    //adds all sell prices together then divide by number of sell orders
    @Override
    public BigDecimal getAverageSellPrice() {
        BigDecimal price = new BigDecimal("0");
        for(Order order : sellOrders){
            price.add(order.getPrice());
        }
        return price.divide(new BigDecimal(getNumOfSellOrders()));
    }

    //adds all buy prices together then divide by number of buy orders
    @Override
    public BigDecimal getAverageBuyPrice() {
        BigDecimal price = new BigDecimal("0");
        for(Order order : buyOrders){
            price.add(order.getPrice());
        }
        return price.divide(new BigDecimal(getNumOfBuyOrders()));
    }
    
    //returns String of previous 6 method returns
    @Override
    public String displayStats(){
        return "Number of Sell Orders: " + getNumOfSellOrders() + 
                " - Number of Buy Orders: " + getNumOfBuyOrders() +
                " - Overall Sell Quantity: " + getSellQuantity() + 
                " - Overall Buy Quantity: " + getBuyQuantity() + 
                " - Average Sale Price: " + getAverageSellPrice() +
                " - Average Buy Price: " + getAverageBuyPrice();
    }
    
    //clears lists in service layer
    @Override
    public void clearService(){
        orders.clear();
        buyOrders.clear();
        sellOrders.clear();
    }
    
    //if buy orders or sell orders = 0 or less - order book is empty
    @Override
    public boolean checkIfEmpty(){
        if(buyOrders.size() > 0 && sellOrders.size() > 0){
            return false;
        }
        else{
            return true;
        }
    }
    
    //matches sell order with buy order
    @Override
    public Trade matchOrder(){
        //gets up-to-date list
        getAllOrders();
        //gets the buy and sell order with the highest price
        Order buy = buyOrders.get(0);
        Order sell = sellOrders.get(0);
        //gets the smallest quantity of the two orders
        int quantity = getSmallest(buy.getQuantity(), sell.getQuantity());
        //execution price will be sell orders price
        BigDecimal price = sell.getPrice();
        
        //subtract quantity from sell and buy order
        buy.setQuantity(buy.getQuantity() - quantity);
        sell.setQuantity(sell.getQuantity() - quantity);
        
        //update map after trade
        updateAfterMatch(buy, sell);
        
        //create trade object
        Trade trade = new Trade(buy, sell, price);
        //add trade object in dao
        return tradeDao.addTrade(trade.getID(), trade);
    }
    
    //method to match all orders
    @Override
    public void matchAllOrders(){
        //while buy and sell orders list is not empty
        while(!checkIfEmpty()){
            //match order method
            matchOrder();
        }
    }
    
    //method to update orders in dao
    public void updateAfterMatch(Order buy, Order sell){
        //if buy order quaantity = 0
        if(buy.getQuantity() == 0){
            //remove buy order from dao list
            orderDao.removeOrder(buy.getID());
            //update sell order in list
            orderDao.editOrder(sell.getID(), sell);
        }
        else{
            //remove sell order from dao list
            orderDao.removeOrder(sell.getID());
            //update buy order in list
            orderDao.editOrder(buy.getID(), buy);
        }
    }
    
    //method to return the smallest of two ints
    public int getSmallest(int a, int b){
        if(a > b){
            return b;
        }else{
            return a;
        }
    }
}
