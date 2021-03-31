/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.view;

import com.as.orderbook.dto.Order;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Skininho
 */
@Component
public class UserIOConsoleImpl implements UserIO{

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public void printOrderList(List<Order> orders1, List<Order> orders2) {
        for(int i = 0; i < orders1.size(); i++){
            print(orders1.get(i).getID() + " --- " + orders2.get(i).getID());
        }
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String readString(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
