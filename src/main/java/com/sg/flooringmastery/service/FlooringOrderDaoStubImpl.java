/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringOrderDao;
import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public class FlooringOrderDaoStubImpl implements FlooringOrderDao{
    
    public Order onlyOrder;
    
    public FlooringOrderDaoStubImpl() {
        onlyOrder = new Order();
        onlyOrder.setCustomerName("Dwight Schrute");
        onlyOrder.setState("KY");
        onlyOrder.setProductType("Wood");
        BigDecimal area = new BigDecimal(350.00).setScale(2, RoundingMode.HALF_UP);
        onlyOrder.setArea(area);
        onlyOrder.setOrderNum(1);
        LocalDate orderDate = LocalDate.parse("11/11/2019", DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        onlyOrder.setOrderDate(orderDate);
    }
    
    public FlooringOrderDaoStubImpl(Order testOrder) {
        this.onlyOrder = testOrder;
    }

    @Override
    public Order addOrder(Order anOrder) {
        if (anOrder.equals(onlyOrder)) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(onlyOrder);
        return orderList;
    }

    @Override
    public Order getAnOrder(Integer orderNum) {
        if (orderNum.equals(onlyOrder.getOrderNum())) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public void updateAnOrder(Integer orderNum, Order changedOrder) {
        
    }

    @Override
    public Order removeAnOrder(Integer orderNum) {
        if (orderNum.equals(onlyOrder.getOrderNum())) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public void loadAllOrders() throws FlooringPersistenceException {
        
    }

    @Override
    public void saveAllOrders() throws FlooringPersistenceException {
        
    }
    
}
