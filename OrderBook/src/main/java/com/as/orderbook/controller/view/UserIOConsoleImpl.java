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

    //scanner
    Scanner userInput = new Scanner(System.in);
    
    //method for printing string
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }
    
    //print array of strings
    @Override
    public void print(String[] arr) {
        for (String arr1 : arr) {
            print(arr1);
        }
    }

    //print order list
    @Override
    public void printOrderList(List<Order> orders1, List<Order> orders2) {
        //header
        print("                                                             "
                + "BUY ORDERS" + "                       ---                   "
                + "   " + "SELL ORDERS");
        //length is set to longer of two lists
        int length = Math.max(orders1.size(), orders2.size());
        //for loop using length variable
        for(int i = 0; i < length; i++){
            //empty string
            String output = "";
            try{
            //try add order object at index i in orders 1 to output String
                output = "" + orders1.get(i).toString();
            //if index is out of range
            }catch(IndexOutOfBoundsException e){
                //output is set to blank space
                output = "                                      ";
            }
            //add --- to string to break up orders
            output = output.concat(" --- ");
            //try add order object at index i in order 2 to output String
            try{
                output = output.concat(""+orders2.get(i).toString());
            }
            //catch index out of bounds error
            catch(IndexOutOfBoundsException e){
            }
            //print output string
            print(output);
        }
    }

    //returns int above minimum
    @Override
    public int readInt(String prompt, int min){
        int input = 0;
        boolean acceptable = false;
        //print prompt
        print(prompt);
        //while acceptable is false
        while(!acceptable){
            try{   
                //try parse input to int
                input = Integer.parseInt(userInput.nextLine());
                //if equal or greater than min stop loop
                if(input >= min){
                    acceptable = true;
                //or else display error
                }else{
                    numberError(min);
                }
            //if input cannot be parsed to int throw error
            }catch(NumberFormatException e){
                print("Input must be a number");
            }
        }
        return input;
    }
    
    //get int within range
    @Override
    public int readInt(String prompt, int min, int max){
        int input = 0;
        boolean acceptable = false;
        //print prompt
        print(prompt);
        //while not acceptable
        while(!acceptable){
            try{   
                //try parse input to int
                input = Integer.parseInt(userInput.nextLine());
                //if input in range acceptable
                if(checkInput(input, min, max)){
                    acceptable = true;
                }
                //print error if not acceptable
                else{
                    numberError(min, max);
                }
            //if input cannot be parsed to int throw error
            }catch(NumberFormatException e){
                print("Input must be a number");
            }
        }
        //return input
        return input;
    }

    //prints prompt then returns user input as String
    @Override
    public String readString(String prompt) {
        print(prompt);
        return userInput.nextLine();
    }

    //method for getting user menu selection method
    @Override
    public int getMenuSelection(String[] options, int min, int max) {
        boolean keepGoing = true;
        int input = 0;
        //while keepGoing is true
        while(keepGoing){
            //print options array
            print(options);
            try{
                //try parse user input to int
                input = Integer.parseInt(userInput.nextLine());   
                //if within range stop loop
                if(checkInput(input, min, max)){
                    keepGoing = false;
                }
            //if input cannot be converted to int throw error
            }catch(NumberFormatException e){
                numberError(min, max);
            }
        }
        //return error
        return input;
    }
    
    //checks if int is within range
    public boolean checkInput(int input, int min, int max){
        if(input >= min && input <= max){
            return true;
        }else{
            numberError(min, max);
            return false;
        }
    }

    //get BigDecimal within range
    @Override
    public BigDecimal readBigDecimal(String prompt, BigDecimal min, 
            BigDecimal max) {
        boolean acceptable = false;
        BigDecimal price = null;
        //while acceptable is false
        while(!acceptable){
            //print prompt and store string in input
            String input = readString(prompt);
            try{
                //try to create BigDecimal with input String
                price = new BigDecimal(input);
                //cehck if in range
                if((price.compareTo(min) >= 0) && (price.compareTo(max)  <= 0)){
                    //acceptable is set to true stopping the loop
                    acceptable = true;
                //if not in range print number error - pass min and max
                }else{
                    numberError(min.intValue(), max.intValue());
                }
            //if string cannot be converted to BigDecimal throw error
            }catch(NumberFormatException e){
                print("Input must be a number");
            }
        }
        return price;
    }
    
    //error for number not being above min
    public void numberError(int min){
        print("Input must be at least " + min);
    }
    
    //error for number being outside range
    public void numberError(int min, int max){
        print("Input must be between " + min + " - " + max);
    }
}
