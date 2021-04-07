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
    //member fields
    private OrderBookView view;
    private OrderBookServiceLayer service;
    
    //variables used for pagination for orders and trades
    int orderPage = 1;
    int itemsPerPage = 10;
    int orderLastPage = 0;
    int tradePage = 1;
    int tradeLastPage = 0;
    boolean keepShowingTrades = true;
    boolean keepShowingOrders = true;
    
    //constructor
    public OrderBookController(OrderBookView view,
            OrderBookServiceLayer service) {
        this.view = view;
        this.service = service;
    }
    
    //method that runs app
    public void run() throws OrderBookOrderException, 
            OrderBookTradeException, OrderBookIDException{
        //used for menu selection
        int input = 0;
        //app will keep running if keepRunning is true
        Boolean keepRunning = true;
        //create 1000 buy and sell orders
        createOrders(1000);
        
        //while loop to keep running app
        while (keepRunning) {
            //while true - order menu will be the menu shown
            boolean keepShowingOrderMenu = true;
            //shows main menu and prompts user for choice
            input = view.printMenuAndGetSelection();
            //switch case with user input
            switch (input) {
                //show order menu
                case 1:
                    //will keep showing order menu until 
                    //keepShowingOrderMenu is false
                    while(keepShowingOrderMenu){
                        //prints menu and prompts user for input
                        input = view.orderMenu();
                        //switch case for user input
                        switch(input){
                            case 1:
                                //declare boolean for while loop
                                boolean keepShowingOrders = true;
                                while(keepShowingOrders){
                                    //display orders
                                    viewOrders();
                                    //if keepShowingOrders has been set to 
                                    //false in viewOrders() - loop breaks
                                    if(!keepShowingOrders){
                                        break;
                                    }
                                    //prints menu and prompts user
                                    input = view.selectPage();
                                    //switch case
                                    switch(input){
                                        //go back a page
                                        case 1:
                                            changeOrderPage(PageChoices
                                                    .PREVIOUS);
                                            break;
                                        //go to next page
                                        case 2:
                                            changeOrderPage(PageChoices.NEXT);
                                            break;
                                        //sets keepShowingOrders to false
                                        //returns to orders menu
                                        case 3:
                                            keepShowingOrders = false;
                                            //resets pagination to page 1
                                            orderPage = 1;
                                            break;
                                        default:
                                            view.unknownCommand();
                                    }
                                }
                                break;
                                //add buy order
                                case 2:
                                    addBuyOrder();
                                    break;
                                //add sell order
                                case 3:
                                    addSellOrder();
                                    break;
                                //edit order
                                case 4:
                                    editOrder();
                                    break;
                                //display orderbook stats
                                case 5:
                                    displayStats();
                                    break;
                                //return to main menu
                                case 6:
                                    keepShowingOrderMenu = false;
                                    break;    
                        }
                    }
                    break;
                //show trade menu
                case 2:
                    //while true - keep showing trade menu
                    boolean keepShowingTradeMenu = true;
                    while(keepShowingTradeMenu){
                        //prints menu and prompts user for input
                        input = view.tradeMenu();
                        //switch case
                        switch(input){
                            //view single trade by ID
                            case 1:
                                viewTrade();
                                break;
                            //show all trades
                            case 2:
                                //while true - keep showing trades
                                keepShowingTrades = true;
                                while(keepShowingTrades){
                                    //view all trades
                                    viewAllTrades();
                                    //if keepShowingTrades is set to false in
                                    //viewAllTrades break loop
                                    if(!keepShowingTrades){
                                        break;
                                    }
                                    //print menu and prompt user for choice
                                    input = view.selectPage();
                                    //switch case
                                    switch(input){
                                    //go back a page
                                    case 1:
                                        changeTradePage(PageChoices.PREVIOUS);
                                        break;
                                    //go to next page
                                    case 2:
                                        changeTradePage(PageChoices.NEXT);
                                        break;
                                    //back to trade menu
                                    case 3:
                                        keepShowingTrades = false;
                                        //reset page to 1
                                        tradePage = 1;
                                        break;
                                    default:
                                        view.unknownCommand();
                                    }
                                }
                                break;
                            //match single order
                            case 3:
                                matchOrder();
                                break;
                            //match all orders
                            case 4:
                                matchAllOrders();
                                break;
                            //back to main menu
                            case 5:
                                keepShowingTradeMenu = false;
                                break;
                        }
                    }
                    break;
                //exit application
                case 3:
                    //show exit message
                    view.exitMessage();
                    keepRunning = false;
                    break;
                default:
                    view.unknownCommand();
                    break;
            }
        }
    }
    
    //method for creating orders - takes number of orders as paramater
    public void createOrders(int orderNum)throws OrderBookOrderException{
        //try call createOrderss method in service layer
        //catches and displays potential errors
        try{
            service.createOrders(orderNum);
        }catch(OrderBookOrderException e){
            view.displayError(e.getMessage());
        }
        
    }
    //method for viewing orders
    public void viewOrders() throws OrderBookOrderException{
        try{
            //get list buy and sell orders
            List<List<Order>> allOrders = service.getAllOrdersByPrice();
            //create sublist for pagination
            allOrders = getCurrentOrders(allOrders);
            //if both list are empty - keepShowingOrders is false;
            if(allOrders.get(0).size() < 1 && allOrders.get(1).size() < 1){
                keepShowingOrders = false;
            }
            //display page number
            view.pageNumber(orderPage);
            //display orders
            view.displayOrders(allOrders);
            
        //handle errors
        }catch(OrderBookOrderException e){
            view.displayError(e.getMessage());
        }
        
    }
    
    //method for displaying order book stats
    public void displayStats() throws OrderBookOrderException{
        try{
            //get string of stats
            String stats = service.displayStats();
            //pass to view to display
            view.printString(stats);
        //handle exceptions
        }catch(OrderBookOrderException e){
            view.displayError(e.getMessage());
        }
    }
    
    //add buy order
    public void addBuyOrder() throws OrderBookOrderException{
        try{
            //create new order in view
            Order order = view.getNewBuyOrderInfo();
            //pass to service layer
            service.addOrder(order.getID(), order);
        //error handled
        }catch(OrderBookOrderException e){
            view.displayError(e.getMessage());
        }
    }
    
    //add sell order
    public void addSellOrder() throws OrderBookOrderException{
        try{
            //create new order in view
            Order order = view.getNewSellOrderInfo();
            //pass to service layer
            service.addOrder(order.getID(), order);
        //handle error
        }catch(OrderBookOrderException e){
            view.displayError(e.getMessage());
        }
    }
    
    //edit order
    public void editOrder() throws OrderBookIDException, 
            OrderBookOrderException{
        try{
            //prompts the user for the ID of the order they wish to edit
            String orderID = view.getId(
                    "Please input the ID of the order you wish to edit");
            //gets orders using ID from service layer
            Order order = service.getOrder(orderID);
            //pass order to view and returns editedOrder
            Order editedOrder = view.editOrderInfo(order);
            //pass editedOrder to service layer
            service.editOrder(editedOrder.getID(), editedOrder);
        //errors handled
        }catch(OrderBookIDException | OrderBookOrderException e){
            view.displayError(e.getMessage());
        }
    }
    
    //method for matching one order
    public void matchOrder()throws OrderBookTradeException, 
            OrderBookOrderException{
        try{
            //get all orders
            List<List<Order>> allOrders = service.getAllOrdersByPrice();
            //pass the two list from all Orders to service layer
            //returns trade object
            Trade matchedOrder = service.matchOrder(allOrders.get(0),
                    allOrders.get(1));
            //pass trade to view
            view.displayTrade(matchedOrder);
        //errors handled
        }catch(OrderBookTradeException | OrderBookOrderException e){
            view.displayError(e.getMessage());
        }
    }
    
    //method for matching all orders
    public void matchAllOrders() throws OrderBookTradeException, 
            OrderBookOrderException{
        try{
            //call match all orders method
            service.matchAllOrders();
            //display success banner
            view.allOrdersMatched();
        //errors handled
        }catch(OrderBookTradeException | OrderBookOrderException | 
                IndexOutOfBoundsException e){
            view.displayError(e.getMessage());
        }
    }
    
    //method for displaying single trade
    public void viewTrade() throws OrderBookIDException, 
            OrderBookTradeException{
        try{
            //prompts the user for trade ID
            String tradeID = view.getId("Please input a Trade ID");
            //passes ID to service - returns with trade object
            Trade trade = service.getTrade(tradeID);
            //pass trade object to view
            view.displayTrade(trade);
        //errors handled
        }catch(OrderBookIDException | OrderBookTradeException e){
            view.displayError(e.getMessage());
        }
        
    }
    
    //view all trades
    public void viewAllTrades()throws OrderBookTradeException {
        try{
            //get all trades
            List<Trade> trades = service.getAllTrades();
            //create sublist for pagination
            trades = getCurrentTrades(trades);
            //print page number
            view.pageNumber(tradePage);
            //display sublist
            view.displayAllTrades(trades);
            
        //errors handled
        }catch(OrderBookTradeException e){
            view.displayError(e.getMessage());
            keepShowingTrades = false;
        }
    }
    
    //method for getting sublist for pagination
    public List<List<Order>> getCurrentOrders(List<List<Order>> allOrders){
        //last item of sublist index
        int lastOrder1 = itemsPerPage * orderPage;
        int lastOrder2 = itemsPerPage * orderPage;
        //first item index
        int firstOrder = lastOrder1 - itemsPerPage;
        
        //get number of last page
        getOrderLastPage(allOrders);
        
        //if on last page
        if(orderPage == orderLastPage){
            //last item index is set to first item index + remainder 
            //of items on page
            lastOrder1 = firstOrder + (service.getNumOfBuyOrders() 
                    % itemsPerPage);
            lastOrder2 = firstOrder + (service.getNumOfSellOrders()
                    % itemsPerPage);
        }
        //create sublists for buy and sell orders
        List currentBuyOrders = allOrders.get(0).subList(firstOrder, 
                lastOrder1);
        List currentSellOrders = allOrders.get(1).subList(firstOrder, 
                lastOrder2);
        //clear all orders list
        allOrders.clear();
        //add 2 sublists to it
        allOrders.add(currentBuyOrders);
        allOrders.add(currentSellOrders);
        
        //return all orders
        return allOrders;
    }
    
    //method for creating sublist of trades for pagination
    public List<Trade> getCurrentTrades(List<Trade> trades){
        //index of last item
        int lastTrade = itemsPerPage * tradePage;
        //index of first item
        int firstTrade = lastTrade - itemsPerPage;
        //get last page
        getTradeLastPage(trades);
        
        //if on last page
        if(tradePage == tradeLastPage){
            //index of last item is first item index + remainding trades
            lastTrade = firstTrade + (trades.size() % itemsPerPage);
        }
        //return sublist
        return trades.subList(firstTrade, lastTrade);
    }
    
    //method for changing page for orders
    public void changeOrderPage(PageChoices choice){
        //if enum PREVIOUS has been passed and not on the first page
        if(choice == PageChoices.PREVIOUS && orderPage > 1){
            //page is decremented 
            orderPage -= 1;
        //if enum NEXT has been passed and not on the last page
        }else if(choice == PageChoices.NEXT && orderPage < orderLastPage){
            //order page increments
            orderPage += 1;
        }
    }
    
    //method for changing page for trades
    public void changeTradePage(PageChoices choice){
        //if enum PREVIOUS has been passed and not on the first page
        if(choice == PageChoices.PREVIOUS && tradePage > 1){
            tradePage -= 1;
        //if enum NEXT has been passed and not on the last page
        }else if(choice == PageChoices.NEXT && tradePage < tradeLastPage){
            tradePage += 1;
        }
    }
    
    //get last page of orders and set it to orderLastPage
    public void getOrderLastPage(List<List<Order>> orders){
        int length = Math.max(orders.get(0).size(), orders.get(1).size());
        orderLastPage = (int) Math.ceil((double)length/(double)itemsPerPage);
    }
    
    //get last page of trades and set it to tradeLastPage
    public void getTradeLastPage(List<Trade> trades){
        tradeLastPage = (int) Math.ceil((double)trades.size()
                /(double)itemsPerPage);
    }
}
