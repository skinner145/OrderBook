/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.config;

import com.as.orderbook.controller.OrderBookController;
import com.as.orderbook.controller.dao.OrderBookOrderDaoFileImpl;
import com.as.orderbook.controller.dao.OrderBookTradeDaoFileImpl;
import com.as.orderbook.controller.service.OrderBookServiceLayerImpl;
import com.as.orderbook.controller.view.OrderBookView;
import com.as.orderbook.controller.view.UserIO;
import com.as.orderbook.controller.view.UserIOConsoleImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Jane
 */
public class SpringConfig {
    @Bean
    @Scope("singleton")
    public OrderBookController mainController(){
        UserIO io = new UserIOConsoleImpl();
        OrderBookController controller = new OrderBookController(
                new OrderBookView(io), 
                new OrderBookServiceLayerImpl(new OrderBookOrderDaoFileImpl(), 
                        new OrderBookTradeDaoFileImpl()));
        return controller;
    }
}
