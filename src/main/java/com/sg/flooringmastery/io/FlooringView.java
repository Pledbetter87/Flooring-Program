/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.io;

import com.sg.flooringmastery.dto.Mode;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.State;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public class FlooringView {

    //dependency injection
    private UserIO io;

    //constructor that initializes the io member field
    public FlooringView(UserIO io) {
        this.io = io;
    }

    public int printMenuGetSelection(String mode) {
        io.print(mode);
        if (mode.equalsIgnoreCase("training")) {
            io.print("--- PROGRAM IN TRAINING MODE ---");
            io.print("-- No changes can be saved! --");
        }
        io.print("---- Get it on the Floor ----");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Save Current Work");
        io.print("6. Quit");
        io.print("-----------------------");
        return io.readInt("Please make a selection", 1, 6);
    }

    public void exitMessage() {
        io.print("Goodbye.");
    }

    public String displayOrders() {
        return io.readString("Please enter date for orders you would like to display: "
                + "(MM/DD/YYYY");
    }

    public void addOrderBanner() {
        io.print("=== ADD AN ORDER ===");
        io.print("");
    }

    public LocalDate getOrderDate() {
        while (true) {
            try {
                String user = io.readString("Please enter order date (MM/DD/YYYY). Must be future date: ");
                LocalDate date = LocalDate.parse(user, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                LocalDate now = LocalDate.now();
                while (date.isBefore(now) | date.isEqual(now)) {
                    user = io.readString("Must be dated after today. Please re-enter: ");
                    date = LocalDate.parse(user, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                }
                return date;
            } catch (DateTimeParseException e) {
                io.print("Date must match given format. Please re-enter.");
            }
        }
    }

    public String getOrderCustName() {
        String entry = io.readString("Please enter customer's first and last name or business name: ");
        while (entry.isEmpty() | !entry.matches("^[a-zA-Z0-9,. ]*$")) {
            entry = io.readString("Name cannot be blank or contain special characters. Please re-enter name: ");
        }
        return entry;
    }

    public String getOrderState(List<State> allStates) {
        String name = null;
        String choice = "choice";
        choice = io.readString("Please enter state abbreviation (ex. OH, TX, KY): ").toUpperCase();
        while (choice.length() < 2 | choice.length() > 2) {
            choice = io.readString("Invalid entry. Please re-enter state abbreviation: ").toUpperCase();
        }
        do {
            for (int i = 0; i < allStates.size(); i++) {
                State current = allStates.get(i);
                name = current.getStateAbv();
                if (choice.equalsIgnoreCase(name)) {
                    return choice;
                }
            }
            choice = io.readString("Invalid entry. Please re-enter state abbreviation: ");
        } while (true);
    }

    public BigDecimal getArea() {
        double area = io.readDouble("Please enter total square footage of flooring "
                + "needed. 100sq ft minimum.", 100, 1000000);
        return BigDecimal.valueOf(area).setScale(2, RoundingMode.HALF_UP);
    }

    public void displayProducts(List<Product> inventory) {
        io.print("");
        for (Product current : inventory) {
            io.print(current.getName() + " : $" + current.getCostSqFt() + " per sq ft : "
                    + "$" + current.getLaborCostSqFt() + " installation per sq ft");
        }
    }

    public void displayStates(List<State> allStates) {
        io.print("Products currently available in:");
        for (State current : allStates) {
            io.print(current.getName() + " (" + current.getStateAbv() + ")");
        }
    }

    public String getUserProductChoice(List<Product> inventory) {
        String name = null;
        String choice = "choice";
        choice = io.readString("Please enter product selection: ");
        do {
            for (int i = 0; i < inventory.size(); i++) {
                Product current = inventory.get(i);
                name = current.getName();
                if (choice.equals(name)) {
                    return choice;
                }
            }
            choice = io.readString("Invalid entry. Please re-enter product selection: ");
        } while (true);
    }

    public void editOrderbanner() {
        io.print("=== EDIT ORDER ===");
    }

    public int getOrderNumber() {
        return io.readInt("Please enter order number: ");
    }

    public void unknownCommandBanner() {
        io.print("Unknown Command.");
    }

    public void errorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
        io.print("");
    }

    public void displayOrder(Order anOrder) {
        io.print("=== ORDER SUMMARY ===");
        io.print("Order date: " + anOrder.getOrderDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        io.print("Customer name: " + anOrder.getCustomerName());
        io.print("Product type: " + anOrder.getProductType());
        io.print("Customer state: " + anOrder.getState());
        io.print("Tax rate: " + anOrder.getTaxRate());
        io.print("Area: " + anOrder.getArea());
        io.print("Product sq ft cost: $" + anOrder.getCostSqFt());
        io.print("Labor cost per sq ft $" + anOrder.getLaborCostSqFt());
        io.print("Material cost: $" + anOrder.getMaterialCost());
        io.print("Labor cost: $" + anOrder.getLaborCost());
        io.print("Tax cost: $" + anOrder.getTaxCost());
        io.print("Total cost: $" + anOrder.getTotalCost());
    }

    public int saveOrderChoice() {
        int answer;
        io.print("Would you like to save order?");
        io.print("1. Yes");
        io.print("2. No");
        answer = io.readInt("", 1, 2);
        return answer;
    }

    public LocalDate searchDate() {
        while (true) {
            try {
                String user = io.readString("Please enter date you would like to display (MM/DD/YYYY). ");
                LocalDate date = LocalDate.parse(user, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                return date;
            } catch (DateTimeParseException e) {
                io.print("Date must match given format. Please re-enter.");
            }
        }
    }

    public void displaySearchResults(List<Order> ordersByDate) {
        for (Order current : ordersByDate) {
            io.print("");
            io.print("Order Number: " + current.getOrderNum());
            io.print("Customer name: " + current.getCustomerName());
            io.print("Order state: " + current.getState());
            io.print("Tax rate: " + current.getTaxRate() + "%");
            io.print("Area: " + current.getArea() + "sq ft");
            io.print("Product type: ");
            io.print("Product sq ft cost: $" + current.getCostSqFt());
            io.print("Labor cost per sq ft $" + current.getLaborCostSqFt());
            io.print("Material cost: $" + current.getMaterialCost());
            io.print("Labor cost: $" + current.getLaborCost());
            io.print("Tax cost: $" + current.getTaxCost());
            io.print("Total cost: $" + current.getTotalCost());
            io.print("");
            io.print("---------------------------");
        }
        io.readString("Please hit enter to continue.");
    }

    public void noOrdersMessage() {
        io.print("No orders exist for that date.");
        io.readString("Please hit enter to continue.");
    }

    public void displayOrderNum(Integer orderNum) {
        io.print("Your order number is " + orderNum);
    }

    public int exitSave() {
        int answer;
        io.print("Would you like to save changes?");
        io.print("1. Yes");
        io.print("2. No");
        answer = io.readInt("", 1, 2);
        return answer;
    }

    public LocalDate removeOrderDate() {
        while (true) {
            try {
                String user = io.readString("Please enter order date (MM/DD/YYYY).");
                LocalDate date = LocalDate.parse(user, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                return date;
            } catch (DateTimeParseException e) {
                io.print("Date must match given format. Please re-enter.");
            }
        }
    }

    public int removeOrder() {
        int answer;
        io.print("Would you like to remove order?");
        io.print("1. Yes");
        io.print("2. No");
        answer = io.readInt("", 1, 2);
        return answer;
    }

    public void orderRemoved() {
        io.print("Order successfully removed!");
        io.readString("Please hit enter to continue.");
    }

    public void noOrder() {
        io.print("No such order exists.");
        io.readString("Please hit enter to continue.");
    }

    public LocalDate editDate() {
        while (true) {
            try {
                String user = io.readString("Please enter the order date (MM/DD/YYYY). ");
                LocalDate date = LocalDate.parse(user, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                return date;
            } catch (DateTimeParseException e) {
                io.print("Date must match given format. Please re-enter.");
            }
        }
    }

    public void editOrder() {
        io.print("");
        io.print("Edit order field or hit Enter to skip");
        io.print("");
    }

    public String editOrderCustomer(Order toEdit) {
        String entry = io.readString("Enter customer name (" + toEdit.getCustomerName() + "): ");
        while (!entry.matches("^[a-zA-Z0-9,. ]*$")) {
            entry = io.readString("Name cannot contain special characters. Please re-enter name: ");
        }
        return entry;
    }

    public String editOrderState(Order toEdit, List<State> allStates) {
        String name = null;
        String choice = "choice";
        choice = io.readString("Enter state abbreviation (" + toEdit.getState()
                + "): ").toUpperCase();
        while (choice.length() > 2 | choice.length() == 1) {
            choice = io.readString("Invalid entry. Please re-enter state abbreviation: ").toUpperCase();
        }
        do {
            for (int i = 0; i < allStates.size(); i++) {
                State current = allStates.get(i);
                name = current.getStateAbv();
                if (choice.equalsIgnoreCase(name)) {
                    return choice;
                }
                if (choice.isEmpty()) {
                    return toEdit.getState();
                }
            }
            choice = io.readString("Invalid entry. Please re-enter state abbreviation: ");
        } while (true);
    }

    public String editOrderProduct(Order toEdit, List<Product> inventory) {
        String name = null;
        String choice = "choice";
        choice = io.readString("Enter product selection (" + toEdit.getProductType() + "): ");
        do {
            for (int i = 0; i < inventory.size(); i++) {
                Product current = inventory.get(i);
                name = current.getName();
                if (choice.equals(name)) {
                    return choice;
                }
                if (choice.isEmpty()) {
                    return toEdit.getProductType();
                }
            }
            choice = io.readString("Invalid entry. Please re-enter product selection: ");
        } while (true);
    }

    public BigDecimal editOrderArea(Order toEdit) {
        io.print("-- This field must be entered. Enter new value or re-confirm initial value --");
        double area = io.readDouble("Enter total square footage of flooring "
                + "needed. 100sq ft minimum. (" + toEdit.getArea() + "): ", 100, 1000000);
        return BigDecimal.valueOf(area).setScale(2, RoundingMode.HALF_UP);
    }

    public void cannotSave() {
        io.print("--- No data can be saved while in training mode! ---");
        io.print("");
    }

    public void savedMessage() {
        io.print("--- Changes successfully saved! ---");
        io.print("");
    }
}
