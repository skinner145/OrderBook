/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orderbook.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Jane
 */
public class Order {
    private String ID;
    private BigDecimal price;
    private Integer quantity;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.ID);
        hash = 19 * hash + Objects.hashCode(this.price);
        hash = 19 * hash + Objects.hashCode(this.quantity);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (!Objects.equals(this.ID, other.ID)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return Objects.equals(this.quantity, other.quantity);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Order(String ID, BigDecimal price, Integer quantity) {
        this.ID = ID;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order{" + "ID=" + ID + ", price=" + price + ", quantity=" 
                + quantity + '}';
    }
    
    
}
