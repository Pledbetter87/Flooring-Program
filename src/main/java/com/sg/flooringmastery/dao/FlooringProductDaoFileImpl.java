/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author MadelineHebel
 */
public class FlooringProductDaoFileImpl implements FlooringProductDao {

    private final String PRODUCT_FILE;
    public static final String DELIMITER = ",";

    public FlooringProductDaoFileImpl() {
        PRODUCT_FILE = "product.txt";
    }

    public FlooringProductDaoFileImpl(String productTextFile) {
        PRODUCT_FILE = productTextFile;
    }
    
    String header = "ProductType,CostPerSqareFoot,LaborCostPerSquareFoot";

    private Map<String, Product> inventory = new HashMap<>();

    @Override
    public Product addProduct(Product aProduct) {
        return inventory.put(aProduct.getName(), aProduct);
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> productsAsList;
        productsAsList = new ArrayList<>(inventory.values());
        return productsAsList;
    }

    @Override
    public Product getAProduct(String name) {
        return inventory.get(name);
    }

    @Override
    public void updateAProduct(String name, Product changedProduct) {
        inventory.replace(changedProduct.getName(), changedProduct);
    }

    @Override
    public Product removeAProduct(String name) {
        Product removedProduct = inventory.remove(name);
        return removedProduct;
    }

    @Override
    public void loadAllProducts() throws FlooringPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException("Could not load Product data into memory", e);
        }

        String currentLine;
        Product currentProduct;
        
        header = scanner.nextLine();
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProduct(currentLine);
            inventory.put(currentProduct.getName(), currentProduct);
        }
        scanner.close();
    }

    @Override
    public void saveAllProducts() throws FlooringPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(PRODUCT_FILE));
        } catch (IOException e) {
            throw new FlooringPersistenceException("Could not save Product data"
                    + " into memory", e);
        }
        
        out.println(header);
        String productAsText;
        List<Product> productList = this.getAllProducts();
        for (Product currentProduct : productList) {
            productAsText = marshallProduct(currentProduct);
            out.println(productAsText);
            out.flush();
        }
        out.close();
    }

    private Product unmarshallProduct(String productAsText) {
        String[] productTokens = productAsText.split(DELIMITER);

        Product productFromFile = new Product();

        productFromFile.setName(productTokens[0]);

        BigDecimal costSqFt = new BigDecimal(productTokens[1]);
        productFromFile.setCostSqFt(costSqFt);

        BigDecimal laborSqFt = new BigDecimal(productTokens[2]);
        productFromFile.setLaborCostSqFt(laborSqFt);

        return productFromFile;
    }

    private String marshallProduct(Product aProduct) {
        String productAsText = aProduct.getName() + DELIMITER;

        productAsText += aProduct.getCostSqFt() + DELIMITER;

        productAsText += aProduct.getLaborCostSqFt();

        return productAsText;
    }

}
