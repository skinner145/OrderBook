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
//exception for Order object ID
public class OrderBookOrderIDException extends Exception{
    public OrderBookOrderIDException(String e){
        super(e);
    }
    public OrderBookOrderIDException(String e, Throwable cause){
        super(e, cause);
    }
}
