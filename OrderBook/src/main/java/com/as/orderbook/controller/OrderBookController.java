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
import com.as.orderbook.controller.view.OrderBookIDException;
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
    int orderPage = 1;
    int itemsPerPage = 10;
    int orderLastPage = 0;
    int tradePage = 1;
    int tradeLastPage = 0;
    
    public OrderBookController(OrderBookView view, OrderBookServiceLayer service) {
        this.view = view;
        this.service = service;
    }
    
    public void run() throws OrderBookOrderException, OrderBookTradeException, OrderBookIDException{
        int input = 0;
        Boolean keepRunning = true;
        createOrders(25);
        
        while (keepRunning) {
            boolean keepShowingOrderMenu = true;
            
            
            input = view.printMenuAndGetSelection();
            switch (input) {
                case 1:
                    while(keepShowingOrderMenu){
                        input = view.orderMenu();
                        switch(input){
                            case 1:
                                boolean keepShowingOrders = true;
                                while(keepShowingOrders){
                                    viewOrders();
                                    input = view.selectPage();
                                    switch(input){
                                    case 1:
                                        changeOrderPage(PageChoices.PREVIOUS);
                                        break;
                                    case 2:
                                        changeOrderPage(PageChoices.NEXT);
                                        break;
                                    case 3:
                                        keepShowingOrders = false;
                                        orderPage = 1;
                                        break;
                                    default:
                                        view.unknownCommand();
                                    }
                                }
                                break;
                                case 2:
                                    addBuyOrder();
                                    break;
                                case 3:
                                    addSellOrder();
                                    break;
                                case 4:
                                    editOrder();
                                    break;
                                case 5:
                                    displayStats();
                                    break;
                                case 6:
                                    keepShowingOrderMenu = false;
                                    break;
                                
                        }
                    }
                    break;
                case 2:
                    boolean keepShowingTradeMenu = true;
                    while(keepShowingTradeMenu){
                        input = view.tradeMenu();
                        switch(input){
                            case 1:
                                viewTrade();
                                break;
                            case 2:
                                boolean keepShowingTrades = true;
                                while(keepShowingTrades){
                                    viewAllTrades();
                                    input = view.selectPage();
                                    switch(input){
                                    case 1:
                                        changeTradePage(PageChoices.PREVIOUS);
                                        break;
                                    case 2:
                                        changeTradePage(PageChoices.NEXT);
                                        break;
                                    case 3:
                                        keepShowingTrades = false;
                                        tradePage = 1;
                                        break;
                                    default:
                                        view.unknownCommand();
                                    }
                                }
                                break;
                            case 3:
                                matchOrder();
                                break;
                            case 4:
                                matchAllOrders();
                                break;
                            case 5:
                                keepShowingTradeMenu = false;
                                break;
                        }
                    }
                    break;
                case 3:
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
            List<List<Order>> allOrders = service.getAllOrdersByPrice();
            allOrders = getCurrentOrders(allOrders);
            view.displayOrders(allOrders);
        }catch(OrderBookOrderException e){
            view.displayError(e.getMessage());
        }
        
    }
    
    public void displayStats() throws OrderBookOrderException{
        try{
            String stats = service.displayStats();
            view.printString(stats);
        }catch(OrderBookOrderException e){
            view.displayError(e.getMessage());
        }
    }
    
    public void addBuyOrder() throws OrderBookOrderException{
        try{
            Order order = view.getNewBuyOrderInfo();
            System.out.println(order.getID());
            service.addOrder(order.getID(), order);
        }catch(OrderBookOrderException e){
            view.displayError(e.getMessage());
        }
    }
    
    public void addSellOrder() throws OrderBookOrderException{
        try{
            Order order = view.getNewSellOrderInfo();
            System.out.println(order.getID());
            service.addOrder(order.getID(), order);
        }catch(OrderBookOrderException e){
            view.displayError(e.getMessage());
        }
    }
    
    public void editOrder() throws OrderBookIDException, OrderBookOrderException{
        try{
            String orderID = view.getId("Please input the ID of the order you wish to edit");
            Order order = service.getOrder(orderID);
            System.out.println(order.toString());
            Order editedOrder = view.editOrderInfo(order);
            System.out.println(editedOrder.toString());
            service.editOrder(editedOrder.getID(), editedOrder);
        }catch(OrderBookIDException | OrderBookOrderException e){
            view.displayError(e.getMessage());
        }
    }
    
    public void matchOrder()throws OrderBookTradeException, OrderBookOrderException{
        try{
            List<List<Order>> allOrders = service.getAllOrdersByPrice();
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
    
    public void viewTrade() throws OrderBookIDException, OrderBookTradeException{
        try{
            String tradeID = view.getId("Please input a Trade ID");
            Trade trade = service.getTrade(tradeID);
            view.displayTrade(trade);
        }catch(OrderBookIDException | OrderBookTradeException e){
            view.displayError(e.getMessage());
        }
        
    }
    
    public void viewAllTrades()throws OrderBookTradeException {
        try{
            List<Trade> trades = service.getAllTrades();
            trades = getCurrentTrades(trades);
            view.displayAllTrades(trades);
        }catch(OrderBookTradeException e){
            view.displayError(e.getMessage());
        }
    }
    
    public List<List<Order>> getCurrentOrders(List<List<Order>> allOrders){
        int lastOrder1 = itemsPerPage * orderPage;
        int lastOrder2 = itemsPerPage * orderPage;
        int firstOrder = lastOrder1 - itemsPerPage;
        getOrderLastPage(allOrders);
        
        if(orderPage == orderLastPage){
            lastOrder1 = firstOrder + (service.getNumOfBuyOrders() % itemsPerPage);
            lastOrder2 = firstOrder + (service.getNumOfSellOrders()% itemsPerPage);
        }
        List currentBuyOrders = allOrders.get(0).subList(firstOrder, lastOrder1);
        List currentSellOrders = allOrders.get(1).subList(firstOrder, lastOrder2);
        allOrders.clear();
        allOrders.add(currentBuyOrders);
        allOrders.add(currentSellOrders);
        return allOrders;
    }
    
    public List<Trade> getCurrentTrades(List<Trade> trades){
        int lastTrade = itemsPerPage * tradePage;
        int firstTrade = lastTrade - itemsPerPage;
        getTradeLastPage(trades);
        if(tradePage == tradeLastPage){
            lastTrade = firstTrade + (trades.size() % itemsPerPage);
        }
        return trades.subList(firstTrade, lastTrade);
    }
    
    public void changeOrderPage(PageChoices choice){
        if(choice == PageChoices.PREVIOUS && orderPage > 1){
            orderPage -= 1;
        }else if(choice == PageChoices.NEXT && orderPage < orderLastPage){
            orderPage += 1;
        }
    }
    
    public void changeTradePage(PageChoices choice){
        if(choice == PageChoices.PREVIOUS && tradePage > 1){
            tradePage -= 1;
        }else if(choice == PageChoices.NEXT && tradePage < tradeLastPage){
            tradePage += 1;
        }
    }
    
    public void getOrderLastPage(List<List<Order>> orders){
        int length = Math.max(orders.get(0).size(), orders.get(1).size());
        orderLastPage = (int) Math.ceil((double)length/(double)itemsPerPage);
    }
    public void getTradeLastPage(List<Trade> trades){
        tradeLastPage = (int) Math.ceil((double)trades.size()/(double)itemsPerPage);
    }
}
