/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.dao;

import com.as.orderbook.dto.BuyOrder;
import org.junit.jupiter.api.Test;
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
    Boolean isCorrect = true;
    
    public OrderBookDaoTest() {
    }

    @Test
    public void testAdd() {
        orderDao.clearDao();
        tradeDao.clearDao();
        orderDao.addOrder(testBuyOrder1.getID(), testBuyOrder1);
        orderDao.addOrder(testSellOrder1.getID(), testSellOrder1);
        tradeDao.addTrade(testTrade1.getID(), testTrade1);
        assertEquals(testBuyOrder1, orderDao.getOrder(testBuyOrder1.getID()), "Added buy order was not equal");
        assertEquals(testSellOrder1, orderDao.getOrder(testSellOrder1.getID()), "Added sell order was not equal");
        assertEquals(testTrade1, tradeDao.getTrade(testTrade1.getID()), "Added trade was not equal");
    }
    
    @Test
    public void testGetAll() {
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
        
        orderDao.addOrder(testBuyOrder1.getID(), testBuyOrder1);
        orderDao.addOrder(testBuyOrder2.getID(), testBuyOrder2);
        orderDao.getAllOrders().forEach((i) -> {
            if (!buyOrderList.contains(i)) {
                isCorrect = false;
            }
        });
        if (buyOrderList.size() != orderDao.getAllOrders().size()) {
            isCorrect = false;
        }
        assertTrue(isCorrect, "Added buy orders were incorrect");
        orderDao.clearDao();
        isCorrect = true;
        
        orderDao.addOrder(testSellOrder1.getID(), testSellOrder1);
        orderDao.addOrder(testSellOrder2.getID(), testSellOrder2);
        orderDao.getAllOrders().forEach((i) -> {
            if (!sellOrderList.contains(i)) {
                isCorrect = false;
                System.out.println("sellOrderList does not contain " + i);
            }
        });
        if (sellOrderList.size() != orderDao.getAllOrders().size()) {
            isCorrect = false;
            System.out.println("orderDao is wrong size(" + orderDao.getAllOrders().size() + ", should be " + sellOrderList.size() + ")");
        }
        assertTrue(isCorrect, "Added sell orders were incorrect");
        isCorrect = true;
        
        tradeDao.addTrade(testTrade1.getID(), testTrade1);
        tradeDao.addTrade(testTrade2.getID(), testTrade2);
        tradeDao.getAllTrades().forEach((i) -> {
            if (!tradeList.contains(i)) {
                isCorrect = false;
                System.out.println("tradeList does not contain " + i);
            }
        });
        if (tradeList.size() != tradeDao.getAllTrades().size()) {
            isCorrect = false;
            System.out.println("tradeDao is wrong size");
        }
        assertTrue(isCorrect, "Added trades were incorrect");
    }
    
    @Test
    public void testRemove() { //Will automatically pass if add test fails, keep this in mind
        orderDao.clearDao();
        tradeDao.clearDao();
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
    
    @Test
    public void testEdit() {
        orderDao.clearDao();
        tradeDao.clearDao();
        orderDao.addOrder(testBuyOrder1.getID(), testBuyOrder1);
        orderDao.editOrder(testBuyOrder1.getID(), testBuyOrder2);
        assertEquals(testBuyOrder2, orderDao.getOrder(testBuyOrder1.getID()), "Buy order was not successfully updated");
        
        orderDao.addOrder(testSellOrder1.getID(), testSellOrder1);
        orderDao.editOrder(testSellOrder1.getID(), testSellOrder2);
        assertEquals(testSellOrder2, orderDao.getOrder(testSellOrder1.getID()), "Sell order was not successfully updated");
        
        tradeDao.addTrade(testTrade1.getID(), testTrade1);
        tradeDao.editTrade(testTrade1.getID(), testTrade2);
        assertEquals(testTrade2, tradeDao.getTrade(testTrade1.getID()), "Trade was not successfully updated");
    }
    
    
}
