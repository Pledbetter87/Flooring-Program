/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dto.Mode;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.State;
import com.sg.flooringmastery.exceptions.NoSuchOrderException;
import com.sg.flooringmastery.io.FlooringView;
import com.sg.flooringmastery.service.FlooringServiceLayer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public class FlooringController {

    private FlooringView view;
    private FlooringServiceLayer service;

    public FlooringController(FlooringView view, FlooringServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

        try {
            service.loadProgram();

            while (keepGoing) {
                menuSelection = displayMenuGetSelection();

                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addAnOrder();
                        break;
                    case 3:
                        try {
                            editAnOrder();
                        } catch (NoSuchOrderException e) {
                            view.errorMessage(e.getLocalizedMessage());
                        }
                        break;
                    case 4:
                        try {
                            removeAnOrder();
                        } catch (NoSuchOrderException e) {
                            view.errorMessage(e.getLocalizedMessage());
                        }
                        break;
                    case 5:
                        saveCurrentWork();
                        break;
                    case 6:
                        exitProgram();
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        } catch (FlooringPersistenceException e) {
            view.errorMessage(e.getMessage());
        }
    }

    private void displayOrders() {
        LocalDate displayDate = view.searchDate();
        List<Order> ordersByDate = service.searchOrders(displayDate);
        if (ordersByDate.isEmpty()) {
            view.noOrdersMessage();
        } else {
            view.displaySearchResults(ordersByDate);
        }
    }

    private void addAnOrder() {
        view.addOrderBanner();
        // ----- get user order input -----
        LocalDate orderDate = view.getOrderDate();
        String custName = view.getOrderCustName();
        //need to validate state entry
        List<State> allStates = service.getAllStates();
        view.displayStates(allStates);
        String orderState = view.getOrderState(allStates);
        // ----- get all products from service layer -----
        List<Product> inventory = service.getAllProducts();
        view.displayProducts(inventory);
        String userChoice = view.getUserProductChoice(inventory);
        BigDecimal area = view.getArea();
        // ----- have service layer do pricing calculations -----
        Order newOrder = service.calculateCosts(userChoice, area, orderState);
        newOrder.setCustomerName(custName);
        newOrder.setOrderDate(orderDate);
        // ----- have view display order summary -----
        view.displayOrder(newOrder);
        // ----- get user save choice -----
        int saveOrder = view.saveOrderChoice();
        // ----- if yes -----
        // ----- have service layer generate order number and save order -----
        if (saveOrder == 1) {
            Integer orderNum = service.createOrderNumber(newOrder);
            newOrder.setOrderNum(orderNum);
            service.addOrder(newOrder);
            view.displayOrderNum(orderNum);
        }
    }

    private void editAnOrder() throws NoSuchOrderException {
        view.editOrderbanner();
        LocalDate orderDate = view.editDate();
        int orderNum = view.getOrderNumber();
        List<State> allStates = service.getAllStates();
        List<Product> inventory = service.getAllProducts();
        Order toEdit = service.getOrder(orderDate, orderNum);
        String newName = view.editOrderCustomer(toEdit);
        view.displayStates(allStates);
        String newState = view.editOrderState(toEdit, allStates);
        view.displayProducts(inventory);
        String newProduct = view.editOrderProduct(toEdit, inventory);
        BigDecimal newArea = view.editOrderArea(toEdit);
        Order newOrder = service.calculateCosts(newProduct, newArea, newState);
        newOrder.setOrderNum(toEdit.getOrderNum());
        newOrder.setCustomerName(toEdit.getCustomerName());
        newOrder.setOrderDate(toEdit.getOrderDate());
        newOrder.setCustomerName(newName);
        view.displayOrder(newOrder);
        int save = view.saveOrderChoice();
        if (save == 1) {
            Order toSave = newOrder;
            service.calculateCosts(newProduct, newArea, newState);
            toSave = service.editAnOrder(newOrder, newName, newState, newProduct, newArea);
            service.addOrder(toSave);
        }
    }

    private void removeAnOrder() throws NoSuchOrderException {
        LocalDate orderDate = view.removeOrderDate();
        Integer orderNum = view.getOrderNumber();
        Order toRemove = service.getOrder(orderDate, orderNum);
        view.displayOrder(toRemove);
        int remove = view.removeOrder();
        if (remove == 1) {
            service.removeOrder(orderNum);
            view.orderRemoved();
        }
    }

    private void saveCurrentWork() {
        String theMode = service.checkAppMode();
        if (theMode.equals("production")) {
            try {
                service.saveChanges();
                view.savedMessage();
            } catch (FlooringPersistenceException e) {
                view.errorMessage(e.getMessage());
            }
        } else if (theMode.equals("training")) {
            view.cannotSave();
        }
    }

    private void unknownCommand() {
        view.unknownCommandBanner();
    }

    private void exitMessage() {
        view.exitMessage();
    }

    private int displayMenuGetSelection() {
        String theMode = service.checkAppMode();
        return view.printMenuGetSelection(theMode);
    }

    private void exitProgram() {
        int saveChanges = view.exitSave();
        String theMode = service.checkAppMode();
        if (theMode.equals("production")) {
            if (saveChanges == 1) {
                try {
                    service.saveChanges();
                    view.savedMessage();
                } catch (FlooringPersistenceException e) {
                    view.errorMessage(e.getMessage());
                }
            }
        } else if (theMode.equals("training")) {
            view.cannotSave();
        }
    }

}
