/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller;

import com.as.orderbook.controller.view.OrderBookView;

/**
 *
 * @author Skininho
 */
public class OrderBookController {
    private OrderBookView view;
    
    public void run() {
        int input = 0;
        Boolean keepRunning = true;
        while (keepRunning) {
            input = view.printMenuAndGetSelection();
            switch (input) {
                case 1:
                    System.out.println("VIEW ORDERBOOK NOT YET IMPLEMENTED");
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
    
    public OrderBookController(OrderBookView view) {
        this.view = view;
    }
}
