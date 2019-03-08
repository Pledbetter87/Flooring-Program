/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringModeDao;
import com.sg.flooringmastery.dao.FlooringOrderDao;
import com.sg.flooringmastery.dao.FlooringProductDao;
import com.sg.flooringmastery.dao.FlooringStateDao;
import com.sg.flooringmastery.dto.Mode;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.State;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author MadelineHebel
 */
public class FlooringServiceLayerFileImplTest {

    FlooringServiceLayerFileImpl testService;

    public FlooringServiceLayerFileImplTest() {
        
    }

    @Test
    public void testGetAllStates() {
       FlooringStateDao stateStub = new FlooringStateDaoStubImpl();
        FlooringProductDao productStub = new FlooringProductDaoStubImpl();
        FlooringOrderDao orderStub = new FlooringOrderDaoStubImpl();
        FlooringModeDao modeStub = new FlooringModeDaoStubImpl();
        testService = new FlooringServiceLayerFileImpl(orderStub, productStub, stateStub, modeStub);
        State state = stateStub.getAState("KY");

        List<State> allStates = null;

        allStates = testService.getAllStates();

        Assert.assertNotNull("States should not be null", allStates);
        Assert.assertEquals("States should have size of 1", allStates.size(), 1);
        Assert.assertTrue("State should be Kentucky", allStates.contains(state));
    }

    @Test
    public void testGetProduct() {
        FlooringStateDao stateStub = new FlooringStateDaoStubImpl();
        FlooringProductDao productStub = new FlooringProductDaoStubImpl();
        FlooringOrderDao orderStub = new FlooringOrderDaoStubImpl();
        FlooringModeDao modeStub = new FlooringModeDaoStubImpl();
        testService = new FlooringServiceLayerFileImpl(orderStub, productStub, stateStub, modeStub);

        Product product = productStub.getAProduct("Wood");

        Assert.assertEquals("Should have sq ft cost of $4.00", new BigDecimal(4.00).setScale(2), product.getCostSqFt());
        Assert.assertEquals("Should both be Wood", "Wood", product.getName());
    }

    @Test
    public void testCreateOrderNumber() {
         FlooringStateDao stateStub = new FlooringStateDaoStubImpl();
        FlooringProductDao productStub = new FlooringProductDaoStubImpl();
        FlooringOrderDao orderStub = new FlooringOrderDaoStubImpl();
        FlooringModeDao modeStub = new FlooringModeDaoStubImpl();
        testService = new FlooringServiceLayerFileImpl(orderStub, productStub, stateStub, modeStub);
        Order order = orderStub.getAnOrder(1);

        Assert.assertEquals("Dwight Schrute", order.getCustomerName());

        List<Order> allOrders = new ArrayList<>();
        allOrders.add(order);

        Order newOrder = new Order();

        Integer maxNum = 0;

        for (int i = 0; i < allOrders.size(); i++) {
            Order current = allOrders.get(i);
            maxNum = current.getOrderNum();
            for (int j = 0; j < current.getOrderNum(); j++) {
                if (current.getOrderNum() > maxNum) {
                    maxNum = current.getOrderNum();
                }
            }
        }
        int orderNum = maxNum + 1;

        newOrder.setOrderNum(orderNum);

        Assert.assertEquals("Order number should be 2", 2, newOrder.getOrderNum());

    }

    @Test
    public void testAddOrder() {
       FlooringStateDao stateStub = new FlooringStateDaoStubImpl();
        FlooringProductDao productStub = new FlooringProductDaoStubImpl();
        FlooringOrderDao orderStub = new FlooringOrderDaoStubImpl();
        FlooringModeDao modeStub = new FlooringModeDaoStubImpl();
        testService = new FlooringServiceLayerFileImpl(orderStub, productStub, stateStub, modeStub);
        Order order = orderStub.getAnOrder(1);

        List<Order> allOrders = new ArrayList<>();
        allOrders.add(order);

        Assert.assertEquals(1, allOrders.size());
        Assert.assertEquals("Dwight Schrute", order.getCustomerName());

    }

    @Test
    public void testSearchOrders() {
        FlooringStateDao stateStub = new FlooringStateDaoStubImpl();
        FlooringProductDao productStub = new FlooringProductDaoStubImpl();
        FlooringOrderDao orderStub = new FlooringOrderDaoStubImpl();
        FlooringModeDao modeStub = new FlooringModeDaoStubImpl();
        testService = new FlooringServiceLayerFileImpl(orderStub, productStub, stateStub, modeStub);
        Order order = orderStub.getAnOrder(1);
        List<Order> byDate = new ArrayList<>();
        LocalDate orderDate = order.getOrderDate();

        List<Order> allOrders = new ArrayList<>();
        allOrders.add(order);

        for (int i = 0; i < allOrders.size(); i++) {
            Order current = allOrders.get(i);
            LocalDate date = current.getOrderDate();
            if (date.equals(orderDate)) {
                byDate.add(current);
            }
        }

        Assert.assertEquals(1, byDate.size());
        Assert.assertTrue(byDate.contains(order));
    }

    @Test
    public void testGetOrder() {
        FlooringStateDao stateStub = new FlooringStateDaoStubImpl();
        FlooringProductDao productStub = new FlooringProductDaoStubImpl();
        FlooringOrderDao orderStub = new FlooringOrderDaoStubImpl();
        FlooringModeDao modeStub = new FlooringModeDaoStubImpl();
        testService = new FlooringServiceLayerFileImpl(orderStub, productStub, stateStub, modeStub);

        Order order = orderStub.getAnOrder(1);

        Assert.assertEquals("Dwight Schrute", order.getCustomerName());
    }

    public void testEditAnOrder() {
       FlooringStateDao stateStub = new FlooringStateDaoStubImpl();
        FlooringProductDao productStub = new FlooringProductDaoStubImpl();
        FlooringOrderDao orderStub = new FlooringOrderDaoStubImpl();
        FlooringModeDao modeStub = new FlooringModeDaoStubImpl();
        testService = new FlooringServiceLayerFileImpl(orderStub, productStub, stateStub, modeStub);
        Order order = orderStub.getAnOrder(1);

        Order toEdit = order;

    }

    @Test
    public void testCheckAppMode() {
        FlooringStateDao stateStub = new FlooringStateDaoStubImpl();
        FlooringProductDao productStub = new FlooringProductDaoStubImpl();
        FlooringOrderDao orderStub = new FlooringOrderDaoStubImpl();
        FlooringModeDao modeStub = new FlooringModeDaoStubImpl();
        testService = new FlooringServiceLayerFileImpl(orderStub, productStub, stateStub, modeStub);
        List<Mode> allModes = modeStub.getMode();
        Mode theMode = allModes.get(0);

        Assert.assertEquals("training", theMode.getMode());
    }

    @Test
    public void testCalculateCosts() {
        FlooringStateDao stateStub = new FlooringStateDaoStubImpl();
        FlooringProductDao productStub = new FlooringProductDaoStubImpl();
        FlooringOrderDao orderStub = new FlooringOrderDaoStubImpl();
        FlooringModeDao modeStub = new FlooringModeDaoStubImpl();
        testService = new FlooringServiceLayerFileImpl(orderStub, productStub, stateStub, modeStub);
        
        Order order = orderStub.getAnOrder(1);
        BigDecimal area = order.getArea();
        Product product = productStub.getAProduct("Wood");
        State state = stateStub.getAState("KY");
        
        order = testService.calculateCosts("wood", area, "KY");

        Assert.assertEquals(3.00, order.getMaterialCost());
       
    }

    public void testGetAllProducts() {
       FlooringStateDao stateStub = new FlooringStateDaoStubImpl();
        FlooringProductDao productStub = new FlooringProductDaoStubImpl();
        FlooringOrderDao orderStub = new FlooringOrderDaoStubImpl();
        FlooringModeDao modeStub = new FlooringModeDaoStubImpl();
        testService= new FlooringServiceLayerFileImpl(orderStub, productStub, stateStub, modeStub);
        List<Product> allProducts = new ArrayList<>();
        allProducts = productStub.getAllProducts();

        Assert.assertEquals(1, allProducts.size());

    }

}
