/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.dao;

import com.as.orderbook.dto.BuyOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.as.orderbook.dto.SellOrder;
import com.as.orderbook.dto.Trade;
import java.math.BigDecimal;
/**
 *
 * @author Jane
 */
public class OrderBookDaoTest {
    OrderBookOrderDao orderDao = new OrderBookOrderDaoFileImpl();
    BuyOrder testBuyOrder1 = new BuyOrder(BigDecimal.ONE, 123);
    BuyOrder testBuyOrder2 = new BuyOrder(BigDecimal.TEN, 456);
    SellOrder testSellOrder1 = new SellOrder(new BigDecimal("1.1"), 120);
    SellOrder testSellOrder2 = new SellOrder(new BigDecimal("9.9"), 460);
    OrderBookTradeDao tradeDao = new OrderBookTradeDaoFileImpl();
    Trade testTrade1 = new Trade(testBuyOrder1, testSellOrder1, BigDecimal.ONE);
    Trade testTrade2 = new Trade(testBuyOrder2, testSellOrder2, new BigDecimal("9.9"));
    
    public OrderBookDaoTest() {
    }
    
    @BeforeEach
    public void setUp() { //returns the test dao to a blank state before each test
        orderDao.clearDao();
        tradeDao.clearDao();
    }

    @Test
    public void testAdd() {
        
    }
    
}
