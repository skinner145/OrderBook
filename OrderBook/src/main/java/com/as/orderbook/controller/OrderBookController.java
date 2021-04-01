/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller;

import com.as.orderbook.controller.service.OrderBookOrderException;
import com.as.orderbook.controller.service.OrderBookOrderIDException;
import com.as.orderbook.controller.service.OrderBookServiceLayer;
import com.as.orderbook.controller.service.OrderBookTradeException;
import com.as.orderbook.controller.view.OrderBookView;
import com.as.orderbook.dto.Order;
import com.as.orderbook.dto.Trade;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Skininho
 */

public class OrderBookController {
    private OrderBookView view;
    private OrderBookServiceLayer service;
    
    
    public OrderBookController(OrderBookView view, OrderBookServiceLayer service) {
        this.view = view;
        this.service = service;
    }
    
    public void run() throws OrderBookOrderException, OrderBookTradeException{
        int input = 0;
        Boolean keepRunning = true;
        createOrders(1000);
        while (keepRunning) {
            input = view.printMenuAndGetSelection();
            switch (input) {
                case 1:
                    viewOrders();
                    break;
                case 2:
                    input = view.manageOrders();
                    switch(input) {
                        case 1:
                            matchOrder();
                            break;
                        case 2:
                            System.out.println("MATCH ALL ORDERS NOT YET IMPLEMENTED");
                            break;
                        case 3:
                            System.out.println("VIEW A TRADE NOT YET IMPLEMENTED");
                            break;
                        case 4:
                            viewAllTrades();
                            break;
                        case 5:
                            break;
                        default:
                            System.out.println("Invalid selection, returning to main menu");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid selection");
                    break;
            }
        }
    }
    public void createOrders(int orderNum)throws OrderBookOrderException{
        try{
            service.createOrders(orderNum);
        }catch(OrderBookOrderException e){
            view.displayError(e.getMessage());
        }
        
    }
    public void viewOrders(){
        List<List<Order>> allOrders = service.getAllOrders();
        view.displayOrders(allOrders);
    }
    
    public void matchOrder()throws OrderBookTradeException{
        try{
            Trade matchedOrder = service.matchOrder();
            System.out.println(matchedOrder.getExecutionTime());
            view.displayTrade(matchedOrder);
        }catch(OrderBookTradeException e){
            view.displayError(e.getMessage());
        }
    }
    
    public void viewAllTrades(){
        List<Trade> trades = service.getAllTrades();
        view.displayAllTrades(trades);
    }
}
