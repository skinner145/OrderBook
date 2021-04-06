/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.controller.view;

import com.as.orderbook.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author Skininho
 */
@Component
public class UserIOConsoleImpl implements UserIO{

    Scanner userInput = new Scanner(System.in);
    
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public void printOrderList(List<Order> orders1, List<Order> orders2) {
        print(orders1.size() + "   " + orders2.size());
        int length = Math.max(orders1.size(), orders2.size());
        for(int i = 0; i < length; i++){
            String output = "";
            try{
                output = "" + orders1.get(i).toString();
            }catch(IndexOutOfBoundsException e){
                output = "                  ";
            }
            output = output.concat(" --- ");
            try{
                output = output.concat(""+orders2.get(i).toString());
            }
            catch(IndexOutOfBoundsException e){
            }
            print(output);
        }
    }

    @Override
    public int readInt(String prompt){
        print(prompt);
        return Integer.parseInt(userInput.nextLine());
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
        BigDecimal price = new BigDecimal("0");
        while(price.compareTo(min) != 1){
            print(prompt);
            price = new BigDecimal(userInput.nextLine());   
        }
        return price;
    }
    
    @Override
    public LocalDateTime readDate(String prompt){
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        return userInput.nextLine();
    }

    @Override
    public int getMenuSelection(String[] options, int min, int max) {
        boolean keepGoing = true;
        int input = 0;
        while(keepGoing){
            print(options);
            try{
                input = Integer.parseInt(userInput.nextLine());   
                if(checkInput(input, min, max)){
                    keepGoing = false;
                }
            }catch(NumberFormatException e){
                print("Input must be a number between " + min + " - " + max);
            }
        }
        return input;
    }

    @Override
    public void print(String[] arr) {
        for (String arr1 : arr) {
            print(arr1);
        }
    }
    
    public boolean checkInput(int input, int min, int max){
        if(input >= min && input <= max){
            return true;
        }else{
            print("Input must be between " + min + " - " + max);
            return false;
        }
    }

    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min, BigDecimal max) {
        boolean acceptable = false;
        BigDecimal price = null;
        while(!acceptable){
            String input = readString(prompt);
            try{
                price = new BigDecimal(input);
                if((price.compareTo(min) >= 0) && (price.compareTo(max)  <= 0)){
                    acceptable = true;
                }else{
                    print("Price must be between " + min + " - " + max);
                }
            }catch(NumberFormatException e){
                print("Input must be a number");
            }
        }
        return price;
    }
}
