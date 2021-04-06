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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author Skininho
 */

public class OrderBookServiceLayerImpl implements OrderBookServiceLayer{
    //member fields for dao
    private OrderBookOrderDao orderDao;
    private OrderBookTradeDao tradeDao;
    
    int count; //for use in functions
    BigDecimal price;
    
    //constructor

    public OrderBookServiceLayerImpl(OrderBookOrderDao orderDao, OrderBookTradeDao tradeDao) {
        this.orderDao = orderDao;
        this.tradeDao = tradeDao;
    }
    
    //random
    Random rand = new Random();

    //method that creates 1000 buy and sell orders
    @Override
    public void createOrders(int orderNum) throws OrderBookOrderException{
        clearService();
        for(int i = 0; i < orderNum; i++){
            Order buyOrder = new BuyOrder(new BigDecimal(getRandomNum(190)),
                    getRandomNum(20, 50));
            Order sellOrder = new SellOrder(new BigDecimal(getRandomNum(190)),
                    getRandomNum(20, 50));
            validateObject(buyOrder);
            validateObject(sellOrder);
            orderDao.addOrder(buyOrder.getID(), buyOrder);
            orderDao.addOrder(sellOrder.getID(), sellOrder);
        }
    }
    
    //add's order to map in dao
    @Override
    public Order addOrder(String orderId, Order newOrder) throws OrderBookOrderException{
        validateObject(newOrder);
        return orderDao.addOrder(orderId, newOrder);
    }
    
    //get order by ID
    @Override
    public Order getOrder(String orderId) throws OrderBookOrderException{
        if(orderDao.getOrder(orderId) == null){
            throw new OrderBookOrderException("No matching order");
        }
        return orderDao.getOrder(orderId);
    }
    
    //returns a list of lists - one for buy orders - one for sell orders
    @Override
    public List<List<Order>> getAllOrdersByPrice() throws OrderBookOrderException{
        List <Order> orders = orderDao.getAllOrders();
        if(orders.isEmpty()){
            throw new OrderBookOrderException("There are no orders");
        }
        List <Order> buyOrders = new ArrayList<>();
        List <Order> sellOrders = new ArrayList<>();
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
        List<List<Order>> allOrders = new ArrayList<>();
        //sort lists
        buyOrders.sort((Order o1, Order o2) -> o2.getPrice().compareTo(o1.getPrice()));
        sellOrders.sort((Order o1, Order o2) -> o2.getPrice().compareTo(o1.getPrice()));
        //add each list to allOrders list
        allOrders.add(buyOrders);
        allOrders.add(sellOrders);
        
        //return list
        return allOrders;
    }
    
    @Override
    public List<List<Order>> getAllOrdersByQuantity() throws OrderBookOrderException{ //Copypaste of getAllOrders but sorted by quantity instead of price
        List <Order> orders = orderDao.getAllOrders();
        if(orders.isEmpty()){
            throw new OrderBookOrderException("There are no orders");
        }
        List <Order> buyOrders = new ArrayList<>();
        List <Order> sellOrders = new ArrayList<>();
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
        List<List<Order>> allOrders = new ArrayList<>();
        //sort lists
        buyOrders.sort((Order o1, Order o2) -> o2.getQuantity().compareTo(o1.getQuantity()));
        sellOrders.sort((Order o1, Order o2) -> o2.getQuantity().compareTo(o1.getQuantity()));
        //add each list to allOrders list
        allOrders.add(buyOrders);
        allOrders.add(sellOrders);
        
        //return list
        return allOrders;
    }
    
    @Override
    public List<List<Order>> getOrdersByQuantity(Integer quantity) throws OrderBookOrderException{
        List <Order> orders = orderDao.getAllOrders().stream().filter(i -> Objects.equals(i.getQuantity(), quantity)).collect(Collectors.toList());
        if(orders.isEmpty()){
            throw new OrderBookOrderException("There are no orders");
        }
        List <Order> buyOrders = new ArrayList<>();
        List <Order> sellOrders = new ArrayList<>();
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
        List<List<Order>> allOrders = new ArrayList<>();
        //sort lists
        buyOrders.sort((Order o1, Order o2) -> o2.getPrice().compareTo(o1.getPrice()));
        sellOrders.sort((Order o1, Order o2) -> o2.getPrice().compareTo(o1.getPrice()));
        //add each list to allOrders list
        allOrders.add(buyOrders);
        allOrders.add(sellOrders);
        
        //return list
        return allOrders;
    }
    
    //remove Order by ID
    @Override
    public Order removeOrder(String orderId) throws OrderBookOrderException{
        validateObject(orderDao.getOrder(orderId));
        return orderDao.removeOrder(orderId);
    }
    
    //edit Order by ID
    @Override
    public Order editOrder(String orderId, Order editedOrder) throws OrderBookOrderException{
        validateObject(editedOrder);
        return orderDao.editOrder(orderId, editedOrder);
    }
    
    //add Trade to map in dao
    @Override
    public Trade addTrade(String tradeId, Trade trade) throws OrderBookTradeException{
        validateObject(trade);
        return tradeDao.addTrade(tradeId, trade);
    }
    
    //get Trade by ID
    @Override
    public Trade getTrade(String tradeId) throws OrderBookTradeException{
        Trade trade = tradeDao.getTrade(tradeId);
        if(trade == null){
            throw new OrderBookTradeException("There's is no matching Trade for ID :" + tradeId);
        }
        return trade;
    }
    
    @Override
    public Trade getTrade(LocalDateTime dateTime) throws OrderBookTradeException {
        Trade trade = tradeDao.getTrade(dateTime);
        if(trade == null){
            throw new OrderBookTradeException("There's is no matching Trade for date :" + dateTime);
        }
        return trade;
    }
    
    //Get all Trades
    @Override
    public List<Trade> getAllTrades() {
        List<Trade> trades = tradeDao.getAllTrades();
        trades.sort((Trade t1, Trade t2) -> t2.getExecutionTime().compareTo(t1.getExecutionTime()));
        return trades;
    }
    
    @Override
    public List<Trade> getTradesByQuantity(Integer quantity) {
        List<Trade> trades = tradeDao.getAllTrades().stream().filter(i -> Objects.equals(i.getQuantityFilled(), quantity)).collect(Collectors.toList());
        trades.sort((Trade t1, Trade t2) -> t2.getExecutionTime().compareTo(t1.getExecutionTime()));
        return trades;
    }
    
    //remove Trade by ID
    @Override
    public Trade removeTrade(String tradeId) throws OrderBookTradeException{
        validateObject(tradeDao.getTrade(tradeId));
        return tradeDao.removeTrade(tradeId);
    }
    
    //edit Trade by ID
    @Override
    public Trade editTrade(String tradeId, Trade editedTrade) throws OrderBookTradeException{
        validateObject(editedTrade);
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
        count = 0;
        orderDao.getAllOrders().forEach(order -> {
            if(order instanceof SellOrder){
                count++;
            }
        });
        return count;
    }

    //returns number of buy orders
    @Override
    public int getNumOfBuyOrders() {
        count = 0;
        orderDao.getAllOrders().forEach(order -> {
            if(order instanceof BuyOrder){
                count++;
            }
        });
        return count;
    }

    //adds quantity of all sell orders together and returns them
    @Override
    public int getSellQuantity() {
        count = 0;
        orderDao.getAllOrders().forEach(order -> {
            if(order instanceof SellOrder){
                count += order.getQuantity();
            }
        });
        return count;
    }

    //adds quantity of all buy orders together and returns them
    @Override
    public int getBuyQuantity() {
        count = 0;
        orderDao.getAllOrders().forEach(order -> {
            if(order instanceof BuyOrder){
                count += order.getQuantity();
            }
        });
        return count;
    }

    //adds all sell prices together then divide by number of sell orders
    @Override
    public BigDecimal getAverageSellPrice() {
        price = new BigDecimal("0");
        orderDao.getAllOrders().forEach(order -> {
            if(order instanceof SellOrder){
                price = price.add(order.getPrice());
            }
        });
        return price.divide(new BigDecimal(getNumOfSellOrders()));
    }

    //adds all buy prices together then divide by number of buy orders
    @Override
    public BigDecimal getAverageBuyPrice() {
        price = new BigDecimal("0");
        orderDao.getAllOrders().forEach(order -> {
            if(order instanceof BuyOrder){
                price = price.add(order.getPrice());
            }
        });
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
        orderDao.clearDao();
        tradeDao.clearDao();
    }
    
    //if buy orders or sell orders = 0 or less - order book is empty
    @Override
    public boolean checkIfEmpty(){
        System.out.println("buy orders left: " + getNumOfBuyOrders());
        System.out.println("sell orders left: " + getNumOfSellOrders());
        return (getNumOfBuyOrders() <= 0 || getNumOfSellOrders() <= 0);
    }
    
    //matches sell order with buy order
    @Override
    public Trade matchOrder(List<Order> buyList, List<Order> sellList) throws OrderBookOrderException, OrderBookTradeException{
        //gets the buy and sell order with the highest price
        Order buy = buyList.get(0);
        Order sell = sellList.get(0);
        //gets the smallest quantity of the two orders
        int quantity = getSmallest(buy.getQuantity(), sell.getQuantity());
        System.out.println("quantity: " + quantity);
        //execution price will be sell orders price
        BigDecimal price = sell.getPrice();
        
        //subtract quantity from sell and buy order
        buy.setQuantity(buy.getQuantity() - quantity);
        sell.setQuantity(sell.getQuantity() - quantity);
        //update map after trade
        updateAfterMatch(buy, sell);
        //create trade object
        Trade trade = new Trade(buy, sell, quantity, price);
        validateObject(trade);
        //add trade object in dao
        tradeDao.addTrade(trade.getID(), trade);

        System.out.println(trade.getExecutionTime() + " TIME ");
        return trade;
    }
    
    //method to match all orders
    @Override
    public void matchAllOrders() throws OrderBookTradeException, OrderBookOrderException{
        while(!checkIfEmpty()){
            List<List<Order>> orderList = getAllOrdersByPrice();
            //match order method
            matchOrder(orderList.get(0), orderList.get(1));
        }
    }
    
    //method to update orders in dao
    public void updateAfterMatch(Order buy, Order sell) throws OrderBookOrderException{
        //if buy order quantity = 0
        if(buy.getQuantity() == 0){
            //remove buy order from dao list
            removeOrder(buy.getID());
            //update sell order in list
            editOrder(sell.getID(), sell);
        }
        else{
            //remove sell order from dao list
            removeOrder(sell.getID());
            //update buy order in list
            editOrder(buy.getID(), buy);
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
    
    public void validateObject(Order order) throws OrderBookOrderException{
        if(order.getID().isBlank()){
            throw new OrderBookOrderException("Order ID cannot be blank");
        }
        if(order.getPrice().compareTo(BigDecimal.ZERO) != 1){
            throw new OrderBookOrderException("Order price must be greater than zero");
        }
        if(order.getQuantity() <= 0){
            throw new OrderBookOrderException("Order quantity must be greater than zero");
        }
    }
    
    public void validateObject(Trade trade) throws OrderBookTradeException{
        if(trade.getID().isBlank()){
            throw new OrderBookTradeException("Trade ID cannot be blank");
        }
        if(trade.getExecutedPrice().compareTo(BigDecimal.ZERO) != 1){
            throw new OrderBookTradeException("Execution price must be greater than 0");
        }
        if(trade.getQuantityFilled() <=0 ){
            throw new OrderBookTradeException("Quantity filled must be greater than 0");
        }
        if(trade.getExecutionTime() == 0.0d){
            throw new OrderBookTradeException("Execution Time cannot be null");
        }
    }
}
