/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook;

import com.as.orderbook.controller.OrderBookController;
import com.as.orderbook.controller.view.OrderBookView;

/**
 *
 * @author Skininho
 */
public class App {
    public static void main(String[] args) {
        OrderBookController controller = new OrderBookController(new OrderBookView());
        controller.run();
    }
}
