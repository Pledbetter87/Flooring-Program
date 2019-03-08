/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author MadelineHebel
 */
public class FlooringOrderDaoFileImpl implements FlooringOrderDao {

    private final String ORDER_FOLDER;
    public static final String DELIMITER = ",";

    public FlooringOrderDaoFileImpl() {
        ORDER_FOLDER = "/orders";
    }

    public FlooringOrderDaoFileImpl(String orderTextFile) {
        ORDER_FOLDER = orderTextFile;
    }
    
    String header = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,"
                + "CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,"
                + "LaborCost,TaxCost,TotalCost";

    Map<Integer, Order> orders = new HashMap<>();

    @Override
    public Order addOrder(Order anOrder) {
        return orders.put(anOrder.getOrderNum(), anOrder);
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> ordersAsList;
        ordersAsList = new ArrayList<>(orders.values());
        return ordersAsList;
    }

    @Override
    public Order getAnOrder(Integer orderNum) {
        return orders.get(orderNum);
    }

    @Override
    public void updateAnOrder(Integer orderNum, Order changedOrder) {
        orders.replace(orderNum, changedOrder);
    }

    @Override
    public Order removeAnOrder(Integer orderNum) {
        Order removedOrder = orders.remove(orderNum);
        return removedOrder;
    }

    @Override
    public void loadAllOrders() throws FlooringPersistenceException {
        Scanner scanner;
        String currentLine;
        Order currentOrder;

        File dir = new File("./orders");
        File[] files = dir.listFiles();
        if (files != null) {
            for (File child : files) {
                String name = child.getName();
                String date = name.substring(7, 15);
                LocalDate orderDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MMddyyyy"));

                try {
                    scanner = new Scanner(new BufferedReader(new FileReader("./orders" + "/" + name)));
                } catch (FileNotFoundException e) {
                    throw new FlooringPersistenceException("Could not load Order data into memory.", e);
                }
                header = scanner.nextLine();
                while (scanner.hasNextLine()) {
                    currentLine = scanner.nextLine();
                    currentOrder = unmarshallOrder(currentLine);
                    currentOrder.setOrderDate(orderDate);
                    orders.put(currentOrder.getOrderNum(), currentOrder);
                }
                scanner.close();
            }
        }
    }

    private Order unmarshallOrder(String orderAsText) {
        String[] orderTokens = orderAsText.split(DELIMITER);

        Order orderFromFile = new Order();

        int orderNum = Integer.parseInt(orderTokens[0]);
        orderFromFile.setOrderNum(orderNum);

        orderFromFile.setCustomerName(orderTokens[1]);

        orderFromFile.setState(orderTokens[2]);

        BigDecimal taxRate = new BigDecimal(orderTokens[3]);
        orderFromFile.setTaxRate(taxRate);

        orderFromFile.setProductType(orderTokens[4]);

        BigDecimal area = new BigDecimal(orderTokens[5]);
        orderFromFile.setArea(area);

        BigDecimal sqFtCost = new BigDecimal(orderTokens[6]);
        orderFromFile.setCostSqFt(sqFtCost);

        BigDecimal laborSqFt = new BigDecimal(orderTokens[7]);
        orderFromFile.setLaborCostSqFt(laborSqFt);

        BigDecimal materialCost = new BigDecimal(orderTokens[8]);
        orderFromFile.setMaterialCost(materialCost);

        BigDecimal laborCost = new BigDecimal(orderTokens[9]);
        orderFromFile.setLaborCost(laborCost);

        BigDecimal taxCost = new BigDecimal(orderTokens[10]);
        orderFromFile.setTaxCost(taxCost);

        BigDecimal totalCost = new BigDecimal(orderTokens[11]);
        orderFromFile.setTotalCost(totalCost);

        return orderFromFile;

    }

    @Override
    public void saveAllOrders() throws FlooringPersistenceException {
        //purge files before saving to ensure any orders we wanted to remove are not
        // still persisted in the orders folder
        Arrays.stream(new File("./orders").listFiles()).forEach(File::delete);

        PrintWriter out;

        Map<Integer, Order> orders = new HashMap<>();

        List<Order> ordersAsList = this.getAllOrders();
        for (int i = 0; i < ordersAsList.size(); i++) {
            Order current = ordersAsList.get(i);
            LocalDate date = current.getOrderDate();
            String fileName = "Orders_" + this.makeFileName(date) + ".txt";
            List<Order> ordersByDate = this.ordersByDate(date);

            try {
                out = new PrintWriter(new FileWriter("./orders" + "/" + fileName));
            } catch (IOException e) {
                throw new FlooringPersistenceException("Could not save Order data"
                        + " into memory", e);
            }
            out.println(header);
            String orderAsText;
            for (Order currentOrder : ordersByDate) {
                orderAsText = marshallOrder(currentOrder);
                out.println(orderAsText);
                out.flush();
            }
            out.close();
        }
    }

    private String marshallOrder(Order anOrder) {
        String orderAsText = anOrder.getOrderNum() + DELIMITER;

        orderAsText += anOrder.getCustomerName() + DELIMITER;

        orderAsText += anOrder.getState() + DELIMITER;

        orderAsText += anOrder.getTaxRate() + DELIMITER;

        orderAsText += anOrder.getProductType() + DELIMITER;

        orderAsText += anOrder.getArea() + DELIMITER;

        orderAsText += anOrder.getCostSqFt() + DELIMITER;

        orderAsText += anOrder.getLaborCostSqFt() + DELIMITER;

        orderAsText += anOrder.getMaterialCost() + DELIMITER;

        orderAsText += anOrder.getLaborCost() + DELIMITER;

        orderAsText += anOrder.getTaxCost() + DELIMITER;

        orderAsText += anOrder.getTotalCost();

        return orderAsText;
    }

    public String makeFileName(LocalDate date) {
        String orderDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        return orderDate;
    }

    public List<Order> ordersByDate(LocalDate date) {
        List<Order> byDate = new ArrayList<>();
        List<Order> allOrders = this.getAllOrders();

        for (int i = 0; i < allOrders.size(); i++) {
            Order current = allOrders.get(i);
            LocalDate orderDate = current.getOrderDate();
            if (date.equals(orderDate)) {
                byDate.add(current);
            }
        }
        return byDate;
    }

}
