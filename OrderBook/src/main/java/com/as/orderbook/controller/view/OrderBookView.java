/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.view;

import com.as.orderbook.dto.Order;
import com.as.orderbook.dto.Trade;
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
            System.out.println("2. Manage Orders");
            System.out.println("3. Exit Program");
            input = inputReader.nextInt();
            if (input > 0 && input < 4) {
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
            if (input > 0 && input < 6) {
                correctInput = true;
            }
        }
        return input;
    }
    
    public void displayOrders(List<List<Order>> allOrders){
        List<Order> orders1 = allOrders.get(0);
        List<Order> orders2 = allOrders.get(1);
        io.print(orders1.get(0).getID());
        io.printOrderList(orders1, orders2);
    }
    
    public void displayTrade(Trade trade){
        io.print(trade.getID());
    }
    
    public void displayError(String s){
        io.print("===Error===");
        io.print(s);
    }
}
