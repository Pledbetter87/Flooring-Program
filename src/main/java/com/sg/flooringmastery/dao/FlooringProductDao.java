/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public interface FlooringProductDao {

    public void loadAllProducts()
            throws FlooringPersistenceException;

    public void saveAllProducts()
            throws FlooringPersistenceException;

    public Product addProduct(Product aProduct);

    public List<Product> getAllProducts();

    public Product getAProduct(String name);

    public void updateAProduct(String name, Product changedProduct);

    public Product removeAProduct(String name);

}
