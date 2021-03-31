/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.service;

import com.as.orderbook.controller.dao.OrderBookOrderDao;
import com.as.orderbook.controller.dao.OrderBookOrderDaoFileImpl;
import com.as.orderbook.controller.dao.OrderBookTradeDao;
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
    OrderBookOrderDao orderDao = new OrderBookOrderDaoFileImpl();
    OrderBookTradeDao tradeDao = new OrderBookTradeDaoFileImpl();
    OrderBookServiceLayer service = new OrderBookServiceLayerImpl(orderDao, tradeDao);
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
    public void testIsSorted() { //Also tests getAllOrders
        service.clearService();
        service.createOrders();
        Boolean isSorted = true;
        int i = 0;
        List<Order> buyOrderList = service.getAllOrders().get(0);
        List<Order> sellOrderList = service.getAllOrders().get(1);
        
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
    
    @Test
    public void testStats() {
        service.clearService();
        service.createOrders();
        Boolean isValid = false;
        assertEquals(1000, service.getNumOfBuyOrders(), "Didn't create 1000 buy orders");
        assertEquals(1000, service.getNumOfSellOrders(), "Didn't create 1000 sell orders");
        if (service.getSellQuantity() >= 20000 && service.getSellQuantity() <= 50000) { //Checking if the sell quantity is within a valid range
            isValid = true;
        }
        assertTrue(isValid, "Sell quantity is not valid");
        isValid = false;
        if (service.getBuyQuantity() >= 20000 && service.getBuyQuantity() <= 50000) { //Checking if the sell quantity is within a valid range
            isValid = true;
        }
        assertTrue(isValid, "Buy quantity is not valid");
        isValid = false;
        if (service.getAverageSellPrice().compareTo(new BigDecimal(20)) > -1 && service.getAverageSellPrice().compareTo(new BigDecimal(50)) < 1) { //Checking if the average sell price is within a valid range
            isValid = true;
        }
        assertTrue(isValid, "Average sell quantity is not valid");
        isValid = false;
        if (service.getAverageBuyPrice().compareTo(new BigDecimal(20)) > -1 && service.getAverageBuyPrice().compareTo(new BigDecimal(50)) < 1) { //Checking if the average buy price is within a valid range
            isValid = true;
        }
        assertTrue(isValid, "Average buy quantity is not valid");
    }
    
    @Test
    public void testMatchOrder() {
        service.clearService();
        service.createOrders();
        Trade tradeMatch = service.matchOrder();
        System.out.println("trade id: " + tradeMatch.getID());
        assertTrue(tradeMatch.getID().matches("TRADE\\d+"), "Trade id doesn't match format");
    }
    
    @Test
    public void testMatchAllOrders() {
        service.clearService();
        service.createOrders();
        service.matchAllOrders();
        assertEquals(1000, service.getAllTrades().size(), "Not all trades were matched");
    }
}
