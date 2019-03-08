/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dao.FlooringProductDao;
import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public class FlooringProductDaoStubImpl implements FlooringProductDao {

    public Product onlyProduct;

    public FlooringProductDaoStubImpl() {
        onlyProduct = new Product();
        onlyProduct.setName("Wood");
        BigDecimal costSqFt = new BigDecimal(4.00).setScale(2, RoundingMode.HALF_UP);
        onlyProduct.setCostSqFt(costSqFt);
        BigDecimal laborCostSqFt = new BigDecimal(3.75).setScale(2, RoundingMode.HALF_UP);
        onlyProduct.setLaborCostSqFt(laborCostSqFt);
    }

    public FlooringProductDaoStubImpl(Product testProduct) {
        this.onlyProduct = testProduct;
    }

    @Override
    public void loadAllProducts() throws FlooringPersistenceException {

    }

    @Override
    public void saveAllProducts() throws FlooringPersistenceException {

    }

    @Override
    public Product addProduct(Product aProduct) {
        if (aProduct.equals(onlyProduct)) {
            return onlyProduct;
        } else {
            return null;
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(onlyProduct);
        return productList;
    }

    @Override
    public Product getAProduct(String name) {
        if (name.equals(onlyProduct.getName())) {
            return onlyProduct;
        } else {
            return null;
        }
    }

    @Override
    public void updateAProduct(String name, Product changedProduct) {

    }

    @Override
    public Product removeAProduct(String name) {
        if (name.equals(onlyProduct.getName())) {
            return onlyProduct;
        } else {
            return null;
        }
    }
}
