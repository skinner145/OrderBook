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
public class SellOrder extends Order{
    public SellOrder(BigDecimal price, Integer quantity) {
        super("SELL" + LocalDateTime.now().toString(), price, quantity);
    }
}
