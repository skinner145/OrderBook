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
    Order testBuyOrder1 = new BuyOrder(BigDecimal.ONE, 123);
    Order testBuyOrder2 = new BuyOrder(BigDecimal.TEN, 456);
    List<Order> buyOrderList = new ArrayList<>();
    Order testSellOrder1 = new SellOrder(new BigDecimal("1.1"), 120);
    Order testSellOrder2 = new SellOrder(new BigDecimal("9.9"), 460);
    List<Order> sellOrderList = new ArrayList<>();
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
    }

    @Test
    public void testIsSorted() throws OrderBookOrderException{ //Also tests getAllOrders
        service.clearService();
        service.createOrders(10);
        Boolean isSorted = true;
        int i = 0;
        List<Order> buyOrderList = service.getAllOrders().get(0);
        List<Order> sellOrderList = service.getAllOrders().get(1);
        
        for (i = 1; i < buyOrderList.size(); i++) {
            if (buyOrderList.get(i).getPrice().compareTo(buyOrderList.get(i - 1).getPrice()) == 1) {
                isSorted = false;
                break;
            }
        }
        assertTrue(isSorted, "Buy orders aren't sorted");
        isSorted = true;
        
        for (i = 1; i < sellOrderList.size(); i++) {
            if (sellOrderList.get(i).getPrice().compareTo(sellOrderList.get(i - 1).getPrice()) == 1) {
                isSorted = false;
                break;
            }
        }
        assertTrue(isSorted, "Sell orders aren't sorted");
    }
    
    @Test
    public void testStats() throws OrderBookOrderException {
        service.clearService();
        service.createOrders(10);
        Boolean isValid = false;
        int buySize = service.getAllOrders().get(0).size();
        int sellSize = service.getAllOrders().get(1).size();
        assertEquals(buySize, service.getNumOfBuyOrders(), "Didn't create 10 buy orders");
        assertEquals(sellSize, service.getNumOfSellOrders(), "Didn't create 10 sell orders");
        if (service.getSellQuantity() >= sellSize * 20 && service.getSellQuantity() <= sellSize * 50) { //Checking if the sell quantity is within a valid range
            isValid = true;
        }
        assertTrue(isValid, "Sell quantity is not valid");
        isValid = false;
        if (service.getBuyQuantity() >= buySize * 20 && service.getBuyQuantity() <= buySize * 50) { //Checking if the buy quantity is within a valid range
            isValid = true;
        }
        assertTrue(isValid, "Buy quantity is not valid");
        isValid = false;
        if (service.getAverageSellPrice().compareTo(new BigDecimal(190)) > -1 && service.getAverageSellPrice().compareTo(new BigDecimal(191)) < 1) { //Checking if the average sell price is within a valid range
            isValid = true;
        }
        assertTrue(isValid, "Average sell price is not valid");
        isValid = false;
        if (service.getAverageBuyPrice().compareTo(new BigDecimal(190)) > -1 && service.getAverageBuyPrice().compareTo(new BigDecimal(191)) < 1) { //Checking if the average buy price is within a valid range
            isValid = true;
        }
        assertTrue(isValid, "Average buy price is not valid");
    }
    
    @Test
    public void testMatchOrder() throws OrderBookOrderException, OrderBookTradeException {
        service.clearService();
        service.createOrders(10);
        List<List<Order>> orderList = service.getAllOrders();
        Trade tradeMatch = service.matchOrder(orderList.get(0), orderList.get(1));
        System.out.println("trade id: " + tradeMatch.getID());
        assertTrue(tradeMatch.getID().matches("TRADE\\d+.*"), "Trade id doesn't match format");
    }
    
    @Test
    public void testMatchAllOrders() throws OrderBookOrderException, OrderBookTradeException {
        service.clearService();
        service.createOrders(10);
        try {
            service.matchAllOrders();
        } catch (OrderBookTradeException e) {
            System.out.println("Quantity filled must be greater than 0");
        }
        assertTrue(service.getAllTrades().size() >= 10, "Not all trades were matched");
    }
}
