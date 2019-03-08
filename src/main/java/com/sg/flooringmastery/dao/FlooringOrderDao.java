/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public interface FlooringOrderDao {

    public Order addOrder(Order anOrder);

    public List<Order> getAllOrders();

    public Order getAnOrder(Integer orderNum);

    public void updateAnOrder(Integer orderNum, Order changedOrder);

    public Order removeAnOrder(Integer orderNum);

    public void loadAllOrders()
            throws FlooringPersistenceException;

    public void saveAllOrders()
            throws FlooringPersistenceException;

}
