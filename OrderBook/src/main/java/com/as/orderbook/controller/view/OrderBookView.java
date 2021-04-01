/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.view;

import com.as.orderbook.controller.service.OrderBookTradeException;
import com.as.orderbook.dto.BuyOrder;
import com.as.orderbook.dto.Order;
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
    
    
    public OrderBookView(UserIO io){
        this.io = io;
    }
    
    public Integer printMenuAndGetSelection() {
        Scanner inputReader = new Scanner(System.in);
        Boolean correctInput = false;
        Integer input = 0;
        while (!correctInput) {
            System.out.println("<<Orderbook>>");
            System.out.println("1. View Orderbook");
            System.out.println("2. Add Buy Order");
            System.out.println("3. Add Sell Order");
            System.out.println("4. Display Order Book Stats");
            System.out.println("5. Manage Orders");
            System.out.println("6. Exit Program");
            input = inputReader.nextInt();
            if (checkInput(input, 1, 6)) {
                correctInput = true;
            }
        }
        return input;
    }
    
    public Integer manageOrders() {
        Scanner inputReader = new Scanner(System.in);
        Boolean correctInput = false;
        Integer input = 0;
        while (!correctInput) {
            System.out.println("1. Match One Order");
            System.out.println("2. Match All Orders");
            System.out.println("3. View A Trade");
            System.out.println("4. View All Trades (Sorted by exection time)");
            System.out.println("5. Return To Menu");
            input = inputReader.nextInt();
            if (checkInput(input, 1, 5)) {
                correctInput = true;
            }
        }
        return input;
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
        BigDecimal price = io.readBigDecimal("Please input the price of the buy order", BigDecimal.ZERO);
        int quantity = io.readInt("Please input the quantity");
        return new BuyOrder(price, quantity);
    }
    
    public void displayAllTrades(List<Trade> trades){
        io.print("" +trades.size());
        trades.stream().forEach((t) -> io.print(t.toString()));
    }
    
    public void displayTrade(Trade trade){
        io.print(trade.toString());
    }
    
    public String getId(String msg) throws OrderBookTradeException{
        String id = io.readString(msg);
        if(id.isBlank()){
            throw new OrderBookTradeException("ID cannot be blank");
        }
        return id;
    }
    
    public void displayError(String s){
        io.print("===Error===");
        io.print(s);
    }
    
    public int selectPage(){
        Boolean correctInput = false;
        Integer input = 0;
        while (!correctInput) {
            io.print("1. Previous Page");
            io.print("2. Next Page");
            io.print("3. Back to main menu");
            input = io.readInt("Please select from the above options");
            if (input > 0 && input < 4) {
                correctInput = true;
            }
        }
        return input;
    }
    
    public void unknownCommand(){
        io.print("Unknown Command");
    }
    
    public boolean checkInput(int input, int min, int max){
        if(input >= min && input <= max){
            return true;
        }else{
            return false;
        }
    }
}
