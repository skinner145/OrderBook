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
    //member fields
    private UserIO io;
    
    //min and max value for price of order
    BigDecimal min = new BigDecimal("190");
    BigDecimal max = new BigDecimal("191");
    
    //constructor
    public OrderBookView(UserIO io){
        this.io = io;
    }
    
    //passes menu array and min and max values to getMenuSelection
    //returns user choice
    public Integer printMenuAndGetSelection() {
        io.print("<== OrderBook App ==>");
        String[] options = {"1. Order Menu", "2. Trade Menu", "3. Exit"};
        return io.getMenuSelection(options, 1, options.length);
    }
    
    //passes menu array and min and max values to getMenuSelection
    //returns user choice
    public Integer orderMenu(){
        String[] options = {"1. View OrderBook", "2. Add Buy Order", "3. Add Sell Order", "4. Edit Order",
        "5. Display OrderBook Stats", "6. Return to Main Menu"};
        return io.getMenuSelection(options, 1, options.length);
    }
    
    //passes menu array and min and max values to getMenuSelection
    //returns user choice
    public Integer tradeMenu(){
        String[] options = {"1. View Trade", "2. View All Trades", "3. Match Order", "4. Match All Orders",
        "5. Back to Main Menu"};
        return io.getMenuSelection(options, 1, options.length);
    }
    
    //passes menu array and min and max values to getMenuSelection
    //returns user choice
    public Integer manageOrders() {
        String[] options = {"1. Match One Order", "2. Match All Orders", "3. View A Trade", "4. View All Trades (Sorted by exection time)", "5. Return to Main Menu"};
        return io.getMenuSelection(options, 1, options.length);
    }
    
    //prints string
    public void printString(String s){
        io.print(s);
    }
    
    //prints orders
    public void displayOrders(List<List<Order>> allOrders){
        //separates allOrders into two lists
        List<Order> orders1 = allOrders.get(0);
        List<Order> orders2 = allOrders.get(1);
        //passes them to printOrderList
        io.printOrderList(orders1, orders2);
    }
    
    //create new buy order
    public Order getNewBuyOrderInfo(){
        //prompt the user for price of order with min and max values
        BigDecimal price = io.readBigDecimal(
                "Please input the price of the buy order", min, max);
        //prompts user for quantity of order - min value is 1
        int quantity = io.readInt("Please input the quantity", 1);
        return new BuyOrder(price, quantity);
    }
    
    //similar to above method but will create sellOrder
    public Order getNewSellOrderInfo(){
        BigDecimal price = io.readBigDecimal(
                "Please input the price of the sell order", min, max);
        int quantity = io.readInt("Please input the quantity", 1);
        return new SellOrder(price, quantity);
    }
    
    //edit order method
    public Order editOrderInfo(Order order){
        //prompts the user for a new price
        BigDecimal newPrice = io.readBigDecimal(
                "Please enter the new price for the order", min, max);
        //prompts the user for new quantity
        int newQuantity = io.readInt(
                "Please input a new quantity for the order", 1);
        //asks the user if they want to save it
        int choice = io.readInt(
                "Would you like to save the edited order? 1. Yes  -  2. No", 
                1, 2);
        //if the user enters one order values are updated
        if(choice == 1){
            order.setPrice(newPrice);
            order.setQuantity(newQuantity);
        }
        //returns order
        return order;
    }
    
    //prints each trade object
    public void displayAllTrades(List<Trade> trades){
        trades.stream().forEach((t) -> io.print(t.toString()));
    }
    
    //prints single trade object
    public void displayTrade(Trade trade){
        io.print(trade.toString());
    }
    
    //prompts the user for an ID
    public String getId(String msg) throws OrderBookIDException{
        String id = io.readString(msg);
        //if ID is blank throw error
        if(id.isBlank()){
            throw new OrderBookIDException("ID cannot be blank");
        }
        //return ID
        return id;
    }
    
    //display error
    public void displayError(String s){
        io.print("<== Error ==>");
        io.print(s);
    }
    
    //display pagination options
    public int selectPage(){
        String[] options = {"1. Previous Page", "2. Next Page", 
            "3. Back to Main Menu"};
        return io.getMenuSelection(options, 1, options.length);
    }
    
    //unknown command
    public void unknownCommand(){
        io.print("Unknown Command");
    }
    
    //exit message
    public void exitMessage(){
        io.print("Exiting...");
    }
    
    //all orders matched success banner
    public void allOrdersMatched(){
        io.print("<== All Orders Matched Successfully ==>");
    }
    
    //page number banner
    public void pageNumber(int page){
        io.print("<== Page Number " + page + " ==>");
    }
}
