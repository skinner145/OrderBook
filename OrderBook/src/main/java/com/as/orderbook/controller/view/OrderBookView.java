/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.view;

import com.as.orderbook.controller.service.OrderBookTradeException;
import com.as.orderbook.dto.BuyOrder;
import com.as.orderbook.dto.Order;
import com.as.orderbook.dto.SellOrder;
import com.as.orderbook.dto.Trade;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Skininho
 */

public class OrderBookView {
    private UserIO io;
    BigDecimal min = new BigDecimal("190");
    BigDecimal max = new BigDecimal("191");
    Scanner inputReader = new Scanner(System.in);
    public OrderBookView(UserIO io){
        this.io = io;
    }
    
    public Integer printMenuAndGetSelection() {
        io.print("<== OrderBook App ==>");
        String[] options = {"1. Order Menu", "2. Trade Menu", "3. Exit"};
        return io.getMenuSelection(options, 1, options.length);
    }
    
    public Integer orderMenu(){
        String[] options = {"1. View OrderBook", "2. Add Buy Order", "3. Add Sell Order", "4. Edit Order",
        "5. Display OrderBook Stats", "6. Return to Main Menu"};
        return io.getMenuSelection(options, 1, options.length);
    }
    
    
    public Integer tradeMenu(){
        String[] options = {"1. View Trade", "2. View All Trades", "3. Match Order", "4. Match All Orders",
        "5. Back to Main Menu"};
        return io.getMenuSelection(options, 1, options.length);
    }
    
    public Integer manageOrders() {
        String[] options = {"1. Match One Order", "2. Match All Orders", "3. View A Trade", "4. View All Trades (Sorted by exection time)", "5. Return to Main Menu"};
        return io.getMenuSelection(options, 1, options.length);
    }
    
    public void printString(String s){
        io.print(s);
    }
    
    public void displayOrders(List<List<Order>> allOrders){
        List<Order> orders1 = allOrders.get(0);
        List<Order> orders2 = allOrders.get(1);
        io.printOrderList(orders1, orders2);
    }
    
    public Order getNewBuyOrderInfo(){
        BigDecimal price = io.readBigDecimal("Please input the price of the buy order", min, max);
        int quantity = io.readInt("Please input the quantity", 1);
        return new BuyOrder(price, quantity);
    }
    
    public Order getNewSellOrderInfo(){
        BigDecimal price = io.readBigDecimal("Please input the price of the sell order", min, max);
        int quantity = io.readInt("Please input the quantity", 1);
        return new SellOrder(price, quantity);
    }
    
    public Order editOrderInfo(Order order){
        BigDecimal newPrice = io.readBigDecimal("Please enter the new price for the order", min, max);
        int newQuantity = io.readInt("Please input a new quantity for the order", 1);
        int choice = io.readInt("Would you like to save the edited order? 1. Yes  -  2. No", 1, 2);
        if(choice == 1){
            order.setPrice(newPrice);
            order.setQuantity(newQuantity);
        }
        return order;
    }
    
    public void displayAllTrades(List<Trade> trades){
        trades.stream().forEach((t) -> io.print(t.toString()));
    }
    
    public void displayTrade(Trade trade){
        io.print(trade.toString());
    }
    
    public String getId(String msg) throws OrderBookIDException{
        String id = io.readString(msg);
        if(id.isBlank()){
            throw new OrderBookIDException("ID cannot be blank");
        }
        return id;
    }
    
    public void displayError(String s){
        io.print("===Error===");
        io.print(s);
    }
    
    public int selectPage(){
        String[] options = {"1. Previous Page", "2. Next Page", "3 Back to Main Menu"};
        return io.getMenuSelection(options, 1, options.length);
    }
    
    public void unknownCommand(){
        io.print("Unknown Command");
    }
}
