/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook;

import com.as.orderbook.config.SpringConfig;
import com.as.orderbook.controller.OrderBookController;
import com.as.orderbook.controller.service.OrderBookOrderException;
import com.as.orderbook.controller.service.OrderBookTradeException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author Skininho
 */
public class App {
    public static void main(String[] args) throws OrderBookOrderException, OrderBookTradeException {
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        ((AnnotationConfigApplicationContext)appContext).scan("com.sg.flooringmastery.controller");
        
        OrderBookController controller = (OrderBookController) appContext.getBean("mainController");
        
        controller.run();
    }
}
