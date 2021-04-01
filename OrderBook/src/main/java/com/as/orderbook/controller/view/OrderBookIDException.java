/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.view;

/**
 *
 * @author Skininho
 */
public class OrderBookIDException extends Exception{
    public OrderBookIDException(String e){
        super(e);
    }
    public OrderBookIDException(String e, Throwable cause){
        super(e, cause);
    }
}
