/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author Jane
 */
public class Trade {
    private String ID;
    private Double executionTime;
    private Integer quantityFilled;
    private BigDecimal executedPrice;
    private BuyOrder buyOrder;
    private SellOrder sellOrder;

    public Trade(BuyOrder buyOrder, SellOrder sellOrder, BigDecimal executedPrice) {
        Double startTime = (double)System.nanoTime();
        this.buyOrder = buyOrder;
        this.sellOrder = sellOrder;
        this.executedPrice = executedPrice;
        if (buyOrder.getQuantity() <= sellOrder.getQuantity()) {
            this.quantityFilled = buyOrder.getQuantity();
        } else {
            this.quantityFilled = sellOrder.getQuantity();
        }
        this.ID = "TRADE" + this.quantityFilled + LocalDateTime.now().toString();
        Double endTime = (double)System.nanoTime();
        this.executionTime = (endTime - startTime) / 1000000; //Getting the execution time of the constructor
    }
}
