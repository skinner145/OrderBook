/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller;

import com.as.orderbook.controller.service.OrderBookServiceLayer;
import com.as.orderbook.controller.view.OrderBookView;
import com.as.orderbook.dto.Order;
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
    
    public void run() {
        int input = 0;
        Boolean keepRunning = true;
        createOrders();
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
                            System.out.println("MATCH ONE ORDER NOT YET IMPLEMENTED");
                            break;
                        case 2:
                            System.out.println("MATCH ALL ORDERS NOT YET IMPLEMENTED");
                            break;
                        case 3:
                            System.out.println("VIEW A TRADE NOT YET IMPLEMENTED");
                            break;
                        case 4:
                            System.out.println("VIEW ALL TRADES NOT YET IMPLEMENTED");
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
    public void createOrders(){
        service.createOrders();
    }
    public void viewOrders(){
        List<List<Order>> allOrders = service.getAllOrders();
        view.displayOrders(allOrders);
    }
}
