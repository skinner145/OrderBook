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
public class OrderBookOrderException extends Exception{
    public OrderBookOrderException(String e) {
        super(e);
    }
    public OrderBookOrderException(String e, Throwable cause) {
        super(e, cause);
    }
}
