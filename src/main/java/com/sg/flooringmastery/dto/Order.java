/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author MadelineHebel
 */
public class Order {
    
    private int orderNum;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costSqFt;
    private BigDecimal laborCostSqFt;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal taxCost;
    private BigDecimal totalCost;
    private LocalDate orderDate;

    public Order() {
    }

    public Order(int orderNum, String customerName, String state, BigDecimal taxRate, String productType, BigDecimal area, BigDecimal costSqFt, BigDecimal laborCostSqFt, LocalDate orderDate) {
        this.orderNum = orderNum;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costSqFt = costSqFt;
        this.laborCostSqFt = laborCostSqFt;
        this.orderDate = orderDate;
    }
    
    

    public Order(int orderNum, String customerName, String state, BigDecimal taxRate, String productType, BigDecimal area, BigDecimal costSqFt, BigDecimal laborCostSqFt, BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxCost, BigDecimal totalCost, LocalDate orderDate) {
        this.orderNum = orderNum;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costSqFt = costSqFt;
        this.laborCostSqFt = laborCostSqFt;
        this.materialCost = materialCost;
        this.laborCost = laborCost;
        this.taxCost = taxCost;
        this.totalCost = totalCost;
        this.orderDate = orderDate;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
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

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTaxCost() {
        return taxCost;
    }

    public void setTaxCost(BigDecimal taxCost) {
        this.taxCost = taxCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.orderNum;
        hash = 11 * hash + Objects.hashCode(this.customerName);
        hash = 11 * hash + Objects.hashCode(this.state);
        hash = 11 * hash + Objects.hashCode(this.taxRate);
        hash = 11 * hash + Objects.hashCode(this.productType);
        hash = 11 * hash + Objects.hashCode(this.area);
        hash = 11 * hash + Objects.hashCode(this.costSqFt);
        hash = 11 * hash + Objects.hashCode(this.laborCostSqFt);
        hash = 11 * hash + Objects.hashCode(this.materialCost);
        hash = 11 * hash + Objects.hashCode(this.laborCost);
        hash = 11 * hash + Objects.hashCode(this.taxCost);
        hash = 11 * hash + Objects.hashCode(this.totalCost);
        hash = 11 * hash + Objects.hashCode(this.orderDate);
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
        if (this.orderNum != other.orderNum) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.costSqFt, other.costSqFt)) {
            return false;
        }
        if (!Objects.equals(this.laborCostSqFt, other.laborCostSqFt)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.taxCost, other.taxCost)) {
            return false;
        }
        if (!Objects.equals(this.totalCost, other.totalCost)) {
            return false;
        }
        if (!Objects.equals(this.orderDate, other.orderDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Order{" + "orderNum=" + orderNum + ", customerName=" + customerName + ", state=" + state + ", taxRate=" + taxRate + ", productType=" + productType + ", area=" + area + ", costSqFt=" + costSqFt + ", laborCostSqFt=" + laborCostSqFt + ", materialCost=" + materialCost + ", laborCost=" + laborCost + ", taxCost=" + taxCost + ", totalCost=" + totalCost + ", orderDate=" + orderDate + '}';
    }

    

    
    
    
}
