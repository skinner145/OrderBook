/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Jane
 */
public class Trade {
    private String ID;
    private Double executionTime;
    private Integer quantityFilled;
    private BigDecimal executedPrice;
    private Order buyOrder;
    private Order sellOrder;

    public Trade(Order buyOrder, Order sellOrder, BigDecimal executedPrice) {
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

    public String getID() {
        return ID;
    }

    public Double getExecutionTime() {
        return executionTime;
    }

    public Integer getQuantityFilled() {
        return quantityFilled;
    }

    public BigDecimal getExecutedPrice() {
        return executedPrice;
    }

    public Order getBuyOrder() {
        return buyOrder;
    }

    public Order getSellOrder() {
        return sellOrder;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.ID);
        hash = 11 * hash + Objects.hashCode(this.executionTime);
        hash = 11 * hash + Objects.hashCode(this.quantityFilled);
        hash = 11 * hash + Objects.hashCode(this.executedPrice);
        hash = 11 * hash + Objects.hashCode(this.buyOrder);
        hash = 11 * hash + Objects.hashCode(this.sellOrder);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Trade other = (Trade) obj;
        if (!Objects.equals(this.ID, other.ID)) {
            return false;
        }
        if (!Objects.equals(this.executionTime, other.executionTime)) {
            return false;
        }
        if (!Objects.equals(this.quantityFilled, other.quantityFilled)) {
            return false;
        }
        if (!Objects.equals(this.executedPrice, other.executedPrice)) {
            return false;
        }
        if (!Objects.equals(this.buyOrder, other.buyOrder)) {
            return false;
        }
        return Objects.equals(this.sellOrder, other.sellOrder);
    }
}
