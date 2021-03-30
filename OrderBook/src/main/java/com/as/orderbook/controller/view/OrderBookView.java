/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.view;

import java.util.Scanner;

/**
 *
 * @author Skininho
 */
public class OrderBookView {
    public Integer printMenuAndGetSelection() {
        Scanner inputReader = new Scanner(System.in);
        Boolean correctInput = false;
        Integer input = 0;
        while (!correctInput) {
            System.out.println("<<Orderbook>>");
            System.out.println("1. View Orderbook");
            System.out.println("2. Manage Orders");
            System.out.println("3. Exit Program");
            input = inputReader.nextInt();
            if (input > 0 && input < 4) {
                correctInput = true;
            }
        }
        return input;
    }
    
    public Integer manageOrders() {
        Scanner inputReader = new Scanner(System.in);
        Boolean correctInput = false;
        Integer input = 0;
        while (!correctInput) {
            System.out.println("1. Match One Order");
            System.out.println("2. Match All Orders");
            System.out.println("3. View A Trade");
            System.out.println("4. View All Trades (Sorted by exection time)");
            System.out.println("5. Return To Menu");
            input = inputReader.nextInt();
            if (input > 0 && input < 6) {
                correctInput = true;
            }
        }
        return input;
    }
}
