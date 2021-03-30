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
public class BuyOrder extends Order{
    public BuyOrder(BigDecimal price, Integer quantity) {
        super("BUY" + LocalDateTime.now().toString(), price, quantity);
    }
}
