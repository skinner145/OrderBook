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
import com.as.orderbook.enums.PageChoices;
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
    int page = 1;
    int itemsPerPage = 10;
    int lastPage = 0;
    
    public OrderBookController(OrderBookView view, OrderBookServiceLayer service) {
        this.view = view;
        this.service = service;
    }
    
    public void run() throws OrderBookOrderException, OrderBookTradeException{
        int input = 0;
        Boolean keepRunning = true;
        createOrders(20);
        
        while (keepRunning) {
            boolean keepShowingOrders = true;
            input = view.printMenuAndGetSelection();
            switch (input) {
                case 1:
                    while(keepShowingOrders){
                        viewOrders();
                        System.out.println(page);
                        input = view.selectPage();
                        switch(input){
                            case 1:
                                changePage(PageChoices.PREVIOUS);
                                break;
                            case 2:
                                changePage(PageChoices.NEXT);
                                break;
                            case 3:
                                keepShowingOrders = false;
                                break;
                            default:
                                view.unknownCommand();
                        }
                    }
                    break;
                case 2:
                    displayStats();
                    break;
                case 3:
                    input = view.manageOrders();
                    switch(input) {
                        case 1:
                            matchOrder();
                            break;
                        case 2:
                            matchAllOrders();
                            break;
                        case 3:
                            viewTrade();
                            break;
                        case 4:
                            viewAllTrades();
                            break;
                        case 5:
                            break;
                        default:
                            view.unknownCommand();
                            break;
                    }
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    keepRunning = false;
                    break;
                default:
                    view.unknownCommand();
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
    public void viewOrders()throws OrderBookOrderException{
        try{
            List<List<Order>> allOrders = service.getAllOrders();
            allOrders = getCurrentOrders(allOrders);
            view.displayOrders(allOrders);
        }catch(OrderBookOrderException e){
            view.displayError(e.getMessage());
        }
        
    }
    
    public void displayStats(){
        String stats = service.displayStats();
        view.printString(stats);
    }
    
    public void matchOrder()throws OrderBookTradeException, OrderBookOrderException{
        try{
            List<List<Order>> allOrders = service.getAllOrders();
            Trade matchedOrder = service.matchOrder(allOrders.get(0), allOrders.get(1));
            System.out.println(matchedOrder.getExecutionTime());
            view.displayTrade(matchedOrder);
        }catch(OrderBookTradeException | OrderBookOrderException e){
            view.displayError(e.getMessage());
        }
    }
    public void matchAllOrders() throws OrderBookTradeException, OrderBookOrderException{
        try{
            service.matchAllOrders();
        }catch(OrderBookTradeException | OrderBookOrderException e){
            view.displayError(e.getMessage());
        }catch(IndexOutOfBoundsException e){
            view.displayError(e.getMessage());
        }
    }
    
    public void viewTrade() throws OrderBookTradeException{
        try{
            String tradeID = view.getId("Please input a Trade ID");
            Trade trade = service.getTrade(tradeID);
            view.displayTrade(trade);
        }catch(OrderBookTradeException e){
            view.displayError(e.getMessage());
        }
        
    }
    
    public void viewAllTrades(){
        List<Trade> trades = service.getAllTrades();
        view.displayAllTrades(trades);
    }
    
    public List<List<Order>> getCurrentOrders(List<List<Order>> allOrders){
        int lastItem = itemsPerPage * page;
        int firstItem = lastItem - itemsPerPage;
        getLastPage(allOrders);
        List currentBuyOrders = allOrders.get(0).subList(firstItem, lastItem);
        List currentSellOrders = allOrders.get(1).subList(firstItem, lastItem);
        allOrders.clear();
        allOrders.add(currentBuyOrders);
        allOrders.add(currentSellOrders);
        return allOrders;
    }
    
    public void changePage(PageChoices choice){
        if(choice == PageChoices.PREVIOUS && page > 1){
            page -= 1;
        }else if(choice == PageChoices.NEXT && page < lastPage){
            page += 1;
        }
    }
    
    public void getLastPage(List<List<Order>> orders){
        int length = Math.max(orders.get(0).size(), orders.get(1).size());
        lastPage = (int) Math.ceil(length/itemsPerPage);
    }
}
