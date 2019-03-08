/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author MadelineHebel
 */
public class FlooringProductDaoFileImplTest {

    FlooringProductDao testDao;

    Product[] testProducts = {
        new Product("Poured Concrete", new BigDecimal(4.00), new BigDecimal(4.75)),
        new Product("Bamboo", new BigDecimal(5.75), new BigDecimal(3.50)),
        new Product("Marble Tile", new BigDecimal(8.00), new BigDecimal(5.00))
    };

    private List<Product> inventory = Arrays.asList(testProducts);

    @Before
    public void setUp() throws Exception {
        String testFile = "testProducts.txt";
        new FileWriter(testFile);
        new FileReader(testFile);
        testDao = new FlooringProductDaoFileImpl(testFile);
    }

    @Test
    public void testAddGetItem() {
        Product product = inventory.get(1);

        Product shouldBeNull = testDao.addProduct(product);

        Product shouldBeProduct = testDao.getAProduct(product.getName());

        Assert.assertNull("Should not have a product", shouldBeNull);
        Assert.assertNotNull("Should be a product", shouldBeProduct);

        Assert.assertEquals("Bamboo", product.getName());
        Assert.assertEquals(new BigDecimal(5.75), product.getCostSqFt());
        Assert.assertEquals(new BigDecimal(3.50), product.getLaborCostSqFt());
    }

    @Test
    public void testGetAllProducts() {

        testDao.addProduct(testProducts[0]);
        testDao.addProduct(testProducts[1]);
        testDao.addProduct(testProducts[2]);

        List<Product> allProducts = testDao.getAllProducts();

        Assert.assertNotNull(allProducts);
        Assert.assertEquals(3, allProducts.size());

        Assert.assertTrue(testDao.getAllProducts().contains(testProducts[0]));
        Assert.assertTrue(testDao.getAllProducts().contains(testProducts[1]));
        Assert.assertTrue(testDao.getAllProducts().contains(testProducts[2]));

    }

    @Test
    public void testRemoveItem() {
        testDao.addProduct(testProducts[1]);
        testDao.addProduct(testProducts[2]);

        Product removedProduct = testDao.removeAProduct("Marble Tile");

        Assert.assertEquals(removedProduct, testProducts[2]);

        List<Product> allProducts = testDao.getAllProducts();
        Assert.assertEquals(1, allProducts.size());
        Assert.assertFalse(allProducts.contains(testProducts[2]));
        Assert.assertTrue(allProducts.contains(testProducts[1]));

        removedProduct = testDao.removeAProduct("Bamboo");

        Assert.assertEquals(removedProduct, testProducts[1]);

        allProducts = testDao.getAllProducts();
        Assert.assertTrue(allProducts.isEmpty());

    }

    @Test
    public void testUpdateProduct() {
        testDao.addProduct(testProducts[1]);

        Product bambooOnSale = new Product("Bamboo", new BigDecimal(4.00), new BigDecimal(3.50));
        
        testDao.updateAProduct("Bamboo", bambooOnSale);
        
        Product bambooSale = testDao.getAProduct("Bamboo");
        
        Assert.assertEquals(bambooSale.getCostSqFt(), bambooSale.getCostSqFt());
    }
    
    @Test
    public void testPersistence() throws FlooringPersistenceException {
        Product product1 = inventory.get(0);
        String name1 = product1.getName();
        Product product2 = inventory.get(1);
        String name2 = product2.getName();

        testDao.addProduct(product1);
        testDao.addProduct(product2);

        testDao.saveAllProducts();

        testDao.loadAllProducts();

        Product new1 = testDao.getAProduct(name1);
        Product new2 = testDao.getAProduct(name2);

        Assert.assertEquals(new1, product1);
        Assert.assertEquals(new2, product2);
    } 

}
