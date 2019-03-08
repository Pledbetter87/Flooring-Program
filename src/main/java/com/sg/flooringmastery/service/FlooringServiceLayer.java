/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dto.Mode;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.State;
import com.sg.flooringmastery.exceptions.NoSuchOrderException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public interface FlooringServiceLayer {
    
    public Order calculateCosts(String product, BigDecimal area, String state);
    
    public List<Product> getAllProducts();
    
    public void loadProgram() throws FlooringPersistenceException;
    
    public void saveChanges() throws FlooringPersistenceException;
    
    public List<State> getAllStates();
    
    public Product getProduct(String name);
    
    public Integer createOrderNumber(Order anOrder);
    
    public void addOrder(Order anOrder);
    
    public List<Order> searchOrders(LocalDate orderDate);
    
    public Order getOrder(LocalDate orderDate, Integer orderNumber)
            throws NoSuchOrderException;
    
    public void removeOrder(Integer orderNum);
    
    public Order editAnOrder(Order toEdit, String customerName, String state, 
            String productType, BigDecimal area);
    
    public String checkAppMode();
}
