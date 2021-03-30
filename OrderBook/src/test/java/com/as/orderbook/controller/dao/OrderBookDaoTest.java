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
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 *
 * @author Jane
 */
public class OrderBookDaoTest {
    OrderBookOrderDao orderDao = new OrderBookOrderDaoFileImpl();
    BuyOrder testBuyOrder1 = new BuyOrder(BigDecimal.ONE, 123);
    BuyOrder testBuyOrder2 = new BuyOrder(BigDecimal.TEN, 456);
    List<BuyOrder> buyOrderList = new ArrayList<>();
    SellOrder testSellOrder1 = new SellOrder(new BigDecimal("1.1"), 120);
    SellOrder testSellOrder2 = new SellOrder(new BigDecimal("9.9"), 460);
    List<SellOrder> sellOrderList = new ArrayList<>();
    OrderBookTradeDao tradeDao = new OrderBookTradeDaoFileImpl();
    Trade testTrade1 = new Trade(testBuyOrder1, testSellOrder1, BigDecimal.ONE);
    Trade testTrade2 = new Trade(testBuyOrder2, testSellOrder2, new BigDecimal("9.9"));
    List<Trade> tradeList = new ArrayList<>();
    
    public OrderBookDaoTest() {
    }
    
    @BeforeEach
    public void setUp() { //returns the test dao to a blank state before each test
        orderDao.clearDao();
        tradeDao.clearDao();
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
    public void testAdd() {
        orderDao.addOrder(testBuyOrder1.getID(), testBuyOrder1);
        orderDao.addOrder(testSellOrder1.getID(), testSellOrder1);
        tradeDao.addTrade(testTrade1.getID(), testTrade1);
        assertEquals(testBuyOrder1, orderDao.getOrder(testBuyOrder1.getID()), "Added buy order was not equal");
        assertEquals(testSellOrder1, orderDao.getOrder(testSellOrder1.getID()), "Added sell order was not equal");
        assertEquals(testTrade1, tradeDao.getTrade(testTrade1.getID()), "Added trade was not equal");
    }
    
    @Test
    public void testGetAll() {
        orderDao.addOrder(testBuyOrder1.getID(), testBuyOrder1);
        orderDao.addOrder(testBuyOrder2.getID(), testBuyOrder2);
        assertEquals(buyOrderList, orderDao.getAllOrders(), "Added buy orders were either incorrect or in the wrong order");
        orderDao.clearDao();
        
        orderDao.addOrder(testSellOrder1.getID(), testSellOrder1);
        orderDao.addOrder(testSellOrder2.getID(), testSellOrder2);
        assertEquals(sellOrderList, orderDao.getAllOrders(), "Added sell orders were either incorrect or in the wrong order");
        
        tradeDao.addTrade(testTrade1.getID(), testTrade1);
        tradeDao.addTrade(testTrade2.getID(), testTrade2);
        assertEquals(tradeList, tradeDao.getAllTrades(), "Added trades were either incorrect or in the wrong order");
    }
    
    @Test
    public void testRemove() { //Will automatically pass if add test fails, keep this in mind
        orderDao.addOrder(testBuyOrder1.getID(), testBuyOrder1);
        orderDao.removeOrder(testBuyOrder1.getID());
        assertTrue(orderDao.getAllOrders().isEmpty(), "Buy order was not successfully removed");
        
        orderDao.addOrder(testSellOrder1.getID(), testSellOrder1);
        orderDao.removeOrder(testSellOrder1.getID());
        assertTrue(orderDao.getAllOrders().isEmpty(), "Sell order was not successfully removed");
        
        tradeDao.addTrade(testTrade1.getID(), testTrade1);
        tradeDao.removeTrade(testTrade1.getID());
        assertTrue(tradeDao.getAllTrades().isEmpty(), "Trade was not successfully removed");
    }
    
}
