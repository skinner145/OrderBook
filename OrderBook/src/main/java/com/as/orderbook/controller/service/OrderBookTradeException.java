/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.service;

/**
 *
 * @author Skininho
 */
//exception for trade object
public class OrderBookTradeException extends Exception{
    public OrderBookTradeException(String e){
        super(e);
    }
    public OrderBookTradeException(String e, Throwable cause){
        super(e, cause);
    }
}
