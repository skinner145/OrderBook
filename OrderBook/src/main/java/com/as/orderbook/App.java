/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook;

import com.as.orderbook.controller.OrderBookController;
import com.as.orderbook.controller.dao.OrderBookOrderDao;
import com.as.orderbook.controller.dao.OrderBookOrderDaoFileImpl;
import com.as.orderbook.controller.dao.OrderBookTradeDao;
import com.as.orderbook.controller.dao.OrderBookTradeDaoFileImpl;
import com.as.orderbook.controller.service.OrderBookServiceLayer;
import com.as.orderbook.controller.service.OrderBookServiceLayerImpl;
import com.as.orderbook.controller.view.OrderBookView;
import com.as.orderbook.controller.view.UserIO;
import com.as.orderbook.controller.view.UserIOConsoleImpl;

/**
 *
 * @author Skininho
 */
public class App {
    public static void main(String[] args) {
        
         UserIO myIo = new UserIOConsoleImpl();
        
        //declare and initialize view passing myIo as an argument
        OrderBookView myView = new OrderBookView(myIo);
        
        //declare and inititalize Dao
        OrderBookOrderDao orderDao = new OrderBookOrderDaoFileImpl();
        OrderBookTradeDao tradeDao = new OrderBookTradeDaoFileImpl();
        
        
        //declare and initialize service layer - passing dao and auditDao
        OrderBookServiceLayer myService 
                = new OrderBookServiceLayerImpl(orderDao, tradeDao);
        
//        declare and initialize controller - passing service layer and view
        OrderBookController controller 
                = new OrderBookController(myView, myService);
        
        controller.run();
    }
}
