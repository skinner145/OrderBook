/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.dto;

import java.math.BigDecimal;

/**
 *
 * @author Jane
 */
public class SellOrder extends Order{
    public SellOrder(BigDecimal price, Integer quantity) {
        super("SELL" + System.nanoTime(), price, quantity);
    }
}
