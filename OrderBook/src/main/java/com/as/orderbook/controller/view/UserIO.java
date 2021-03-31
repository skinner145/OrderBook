/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.view;

import com.as.orderbook.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Skininho
 */
public interface UserIO {
    void print(String msg);
    
    void printOrderList(List<Order> orders1, List<Order> orders2);
    
    int readInt(String prompt, int min, int max);
    
    BigDecimal readBigDecimal(String prompt);
    BigDecimal readBigDecimal(String prompt, BigDecimal min);
    
    String readString(String prompt);
}
