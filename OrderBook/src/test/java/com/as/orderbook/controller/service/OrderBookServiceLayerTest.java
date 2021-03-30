/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.service;

import com.as.orderbook.controller.dao.OrderBookOrderDaoFileImpl;
import com.as.orderbook.controller.dao.OrderBookTradeDaoFileImpl;
import com.as.orderbook.dto.BuyOrder;
import com.as.orderbook.dto.Order;
import com.as.orderbook.dto.SellOrder;
import com.as.orderbook.dto.Trade;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Jane
 */
public class OrderBookServiceLayerTest { //Fairly minimal, as majority of methods in the service layer are just wrapping methods from the DAO
    OrderBookServiceLayer service;
    BuyOrder testBuyOrder1 = new BuyOrder(BigDecimal.ONE, 123);
    BuyOrder testBuyOrder2 = new BuyOrder(BigDecimal.TEN, 456);
    List<BuyOrder> buyOrderList = new ArrayList<>();
    SellOrder testSellOrder1 = new SellOrder(new BigDecimal("1.1"), 120);
    SellOrder testSellOrder2 = new SellOrder(new BigDecimal("9.9"), 460);
    List<SellOrder> sellOrderList = new ArrayList<>();
    Trade testTrade1 = new Trade(testBuyOrder1, testSellOrder1, BigDecimal.ONE);
    Trade testTrade2 = new Trade(testBuyOrder2, testSellOrder2, new BigDecimal("9.9"));
    List<Trade> tradeList = new ArrayList<>();
    
    public OrderBookServiceLayerTest() {
    }
    
    @BeforeEach
    public void setUp() {
        service = new OrderBookServiceLayerImpl(new OrderBookOrderDaoFileImpl(), new OrderBookTradeDaoFileImpl());
        buyOrderList.clear();
        buyOrderList.add(testBuyOrder1);
        buyOrderList.add(testBuyOrder2);
        sellOrderList.clear();
        sellOrderList.add(testSellOrder1);
        sellOrderList.add(testSellOrder2);
        tradeList.clear();
        tradeList.add(testTrade1);
        tradeList.add(testTrade2);
    }

    @Test
    public void testCreateOrders() { //Also tests getAllOrders
        service.createOrders();
        Boolean isSorted = true;
        int i = 0;
        List<Order> buyOrderList = service.getAllOrders().get(0);
        List<Order> sellOrderList = service.getAllOrders().get(1);
        assertEquals(1000, buyOrderList.size(), "Didn't create 1000 buy orders");
        assertEquals(1000, sellOrderList.size(), "Didn't create 1000 sell orders");
        
        for (i = 1; i < 1000; i++) {
            if (buyOrderList.get(i).getPrice().compareTo(buyOrderList.get(i - 1).getPrice()) == -1) {
                isSorted = false;
                break;
            }
        }
        assertTrue(isSorted, "Buy orders aren't sorted");
        isSorted = true;
        
        for (i = 1; i < 1000; i++) {
            if (sellOrderList.get(i).getPrice().compareTo(sellOrderList.get(i - 1).getPrice()) == -1) {
                isSorted = false;
                break;
            }
        }
        assertTrue(isSorted, "Sell orders aren't sorted");
    }
    
    
    
}
