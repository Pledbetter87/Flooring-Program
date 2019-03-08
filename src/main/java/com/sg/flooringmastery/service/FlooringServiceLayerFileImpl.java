/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringModeDao;
import com.sg.flooringmastery.dao.FlooringOrderDao;
import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dao.FlooringProductDao;
import com.sg.flooringmastery.dao.FlooringStateDao;
import com.sg.flooringmastery.dto.Mode;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.State;
import com.sg.flooringmastery.exceptions.NoSuchOrderException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public class FlooringServiceLayerFileImpl implements FlooringServiceLayer {

    private FlooringOrderDao orderDao;
    private FlooringProductDao productDao;
    private FlooringStateDao stateDao;
    private FlooringModeDao modeDao;

    public FlooringServiceLayerFileImpl(FlooringOrderDao orderDao, FlooringProductDao productDao,
            FlooringStateDao stateDao, FlooringModeDao modeDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.stateDao = stateDao;
        this.modeDao = modeDao;
    }


    @Override
    public Order calculateCosts(String product, BigDecimal area, String state) {
        //------- Gather required information -------
        //get user product information
        Product userChoice = productDao.getAProduct(product);
        //get user state tax info
        State orderState = stateDao.getAState(state);
        BigDecimal taxRate = orderState.getTaxRate().setScale(2, RoundingMode.HALF_UP);
        //get sq ft cost and labor sq ft cost        
        BigDecimal costSqFt = userChoice.getCostSqFt().setScale(2, RoundingMode.HALF_UP);
        BigDecimal laborCostSqFt = userChoice.getLaborCostSqFt().setScale(2, RoundingMode.HALF_UP);

        //------- Calculations ----------
        //calculate material cost
        BigDecimal materialCost = area.multiply(costSqFt).setScale(2, RoundingMode.HALF_UP);
        //calculate labor cost
        BigDecimal laborCost = area.multiply(laborCostSqFt).setScale(2, RoundingMode.HALF_UP);
        //add them
        BigDecimal costs = materialCost.add(laborCost).setScale(2, RoundingMode.HALF_UP);
        //calculate tax amount
        BigDecimal taxDivisor = new BigDecimal(100).setScale(2, RoundingMode.HALF_UP);
        BigDecimal taxDivided = taxRate.divide(taxDivisor).setScale(2, RoundingMode.HALF_UP);
        BigDecimal taxCost = costs.multiply(taxDivided).setScale(2, RoundingMode.HALF_UP);
        //calculate total cost
        BigDecimal totalCost = costs.add(taxCost).setScale(2, RoundingMode.HALF_UP);

        Order newOrder = new Order();
        newOrder.setState(state);
        newOrder.setTaxRate(taxRate);
        newOrder.setProductType(product);
        newOrder.setArea(area);
        newOrder.setCostSqFt(costSqFt);
        newOrder.setLaborCostSqFt(laborCostSqFt);
        newOrder.setMaterialCost(materialCost);
        newOrder.setLaborCost(laborCost);
        newOrder.setTaxCost(taxCost);
        newOrder.setTotalCost(totalCost);

        return newOrder;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> productsFromDao = new ArrayList<>();
        productsFromDao = productDao.getAllProducts();
        return productsFromDao;
    }

    @Override
    public void loadProgram() throws FlooringPersistenceException {
        productDao.loadAllProducts();
        stateDao.loadAllStates();
        orderDao.loadAllOrders();
        modeDao.loadMode();
    }
    
    @Override
    public void saveChanges() throws FlooringPersistenceException {
        orderDao.saveAllOrders();
    }

    @Override
    public List<State> getAllStates() {
        List<State> statesFromDao = new ArrayList<>();
        statesFromDao = stateDao.getAllStates();
        return statesFromDao;
    }

    @Override
    public Product getProduct(String name) {
        return productDao.getAProduct(name);
    }

    @Override
    public Integer createOrderNumber(Order anOrder) {
        List<Order> allOrders = orderDao.getAllOrders();
        Integer maxNum = 0;

        for (int i = 0; i < allOrders.size(); i++) {
            Order current = allOrders.get(i);
            maxNum = current.getOrderNum();
            for(int j = 0; j < current.getOrderNum(); j++){
                if(current.getOrderNum() > maxNum){
                    maxNum = current.getOrderNum();
                }
            }
        }
        int orderNum = maxNum + 1;
        return orderNum;
    }
    
    @Override
    public void addOrder(Order anOrder){
        orderDao.addOrder(anOrder);
    }

    @Override
    public List<Order> searchOrders(LocalDate orderDate) {
        List<Order> byDate = new ArrayList<>();
        List<Order> allOrders = orderDao.getAllOrders();

        for (int i = 0; i < allOrders.size(); i++) {
            Order current = allOrders.get(i);
            LocalDate date = current.getOrderDate();
            if (date.equals(orderDate)) {
                byDate.add(current);
            }
        }
        return byDate;
    }

    @Override
    public Order getOrder(LocalDate orderDate, Integer orderNumber) 
            throws NoSuchOrderException { 
        Order orderResult = orderDao.getAnOrder(orderNumber);
        if (orderResult == null){
            throw new NoSuchOrderException ("No such order exists");
        }
        return orderResult;
    }

    @Override
    public void removeOrder(Integer orderNum) {
        orderDao.removeAnOrder(orderNum);
    }

    @Override
    public Order editAnOrder(Order toEdit, String customerName, String state, String productType, BigDecimal area) {        
        if (!customerName.isEmpty()){
        toEdit.setCustomerName(customerName);
        }
        if (!state.isEmpty()){
            toEdit.setState(state);
        }
        if(!productType.isEmpty()){
            toEdit.setProductType(productType);
        }
        toEdit.setArea(area);
        
        orderDao.updateAnOrder(toEdit.getOrderNum(), toEdit);
        
        return toEdit;
    }
    
    @Override
    public String checkAppMode() {
        List<Mode> modes = modeDao.getMode();
        Mode mode = modes.get(0);
        String theMode = mode.getMode();
        return theMode;
    }
}
