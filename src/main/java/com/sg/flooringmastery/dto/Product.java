/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author MadelineHebel
 */
public class Product {

    private String name;
    private BigDecimal costSqFt;
    private BigDecimal laborCostSqFt;

    public Product() {
    }

    public Product(String name, BigDecimal costSqFt, BigDecimal laborCostSqFt) {
        this.name = name;
        this.costSqFt = costSqFt;
        this.laborCostSqFt = laborCostSqFt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCostSqFt() {
        return costSqFt;
    }

    public void setCostSqFt(BigDecimal costSqFt) {
        this.costSqFt = costSqFt;
    }

    public BigDecimal getLaborCostSqFt() {
        return laborCostSqFt;
    }

    public void setLaborCostSqFt(BigDecimal laborCostSqFt) {
        this.laborCostSqFt = laborCostSqFt;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.costSqFt);
        hash = 17 * hash + Objects.hashCode(this.laborCostSqFt);
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
        final Product other = (Product) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.costSqFt, other.costSqFt)) {
            return false;
        }
        if (!Objects.equals(this.laborCostSqFt, other.laborCostSqFt)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Product{" + "name=" + name + ", costSqFt=" + costSqFt + ", "
                + "laborCostSqFt=" + laborCostSqFt + '}';
    }

}
