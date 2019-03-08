/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author MadelineHebel
 */
public class FlooringOrderDaoFileImplTest {
   
    FlooringOrderDao testDao;
    
    Order[] testOrders = {
      new Order(1, "A Customer", "OH", new BigDecimal(6.50), "Tile", new BigDecimal(200.00),
       new BigDecimal(4.50), new BigDecimal(3.00), LocalDate.now()),
        new Order(2, "B Customer", "KY", new BigDecimal(6.00), "Wood", new BigDecimal(250.00),
       new BigDecimal(5.00), new BigDecimal(3.50), LocalDate.now())
    };
    
    private List<Order> orders = Arrays.asList(testOrders);
    
    @Before
    public void setUp() throws Exception {
        String testFile = "testOrders.txt";
        new FileWriter(testFile);
        new FileReader(testFile);
        testDao = new FlooringOrderDaoFileImpl(testFile);
    }
    
    @Test
    public void testAddGetOrder() {
        Order order = orders.get(0);
        
        Order shouldBeNull = testDao.addOrder(order);
        
        Order shouldBeOrder = testDao.getAnOrder(order.getOrderNum());
        
        Assert.assertNull("Should not be Order", shouldBeNull);
        Assert.assertNotNull("Should be Order", shouldBeOrder);
        
        Assert.assertEquals("OH", order.getState());
        Assert.assertEquals("A Customer", order.getCustomerName());
        Assert.assertEquals("Tile", order.getProductType());
        Assert.assertEquals(order, shouldBeOrder);
    }
    
    @Test
    public void testGetAllOrders() {
        testDao.addOrder(testOrders[0]);
        testDao.addOrder(testOrders[1]);
        
        List<Order> allOrders = testDao.getAllOrders();
        
        Assert.assertNotNull(allOrders);
        Assert.assertEquals(2, allOrders.size());
        
        Assert.assertTrue(testDao.getAllOrders().contains(testOrders[0]));
        Assert.assertTrue(testDao.getAllOrders().contains(testOrders[1]));
    }
    
    @Test
    public void testRemoveOrder() {
        testDao.addOrder(testOrders[0]);
        testDao.addOrder(testOrders[1]);
        
        Order removedOrder = testDao.removeAnOrder(1);
        
        Assert.assertEquals(removedOrder, testOrders[0]);
        
        List<Order> allOrders = testDao.getAllOrders();
        Assert.assertEquals(1, allOrders.size());
        Assert.assertFalse(allOrders.contains(testOrders[0]));
        Assert.assertTrue(allOrders.contains(testOrders[1]));
        
        removedOrder = testDao.removeAnOrder(2);
        
        Assert.assertEquals(removedOrder, testOrders[1]);
        
        allOrders = testDao.getAllOrders();
        Assert.assertTrue(allOrders.isEmpty());     
    }
    @Test
    public void testUpdateOrder() {
        testDao.addOrder(testOrders[0]);
        
        Order updateArea = new Order(1, "A Customer", "OH", new BigDecimal(6.50), 
                "Tile", new BigDecimal(350.00), new BigDecimal(4.50), 
                new BigDecimal(3.00), LocalDate.now());
        
        testDao.updateAnOrder(1, updateArea);
        
        Order newOrder = testDao.getAnOrder(1);
        
        Assert.assertEquals(updateArea, newOrder);
    }
}
