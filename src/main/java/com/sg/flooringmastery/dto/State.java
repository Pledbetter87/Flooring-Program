/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author MadelineHebel
 */
public class State {

    private String stateAbv;
    private String name;
    private BigDecimal taxRate;

    public State() {
    }

    public State(String stateAbv, String name, BigDecimal taxRate) {
        this.stateAbv = stateAbv;
        this.name = name;
        this.taxRate = taxRate;
    }

    public String getStateAbv() {
        return stateAbv;
    }

    public void setStateAbv(String stateAbv) {
        this.stateAbv = stateAbv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.stateAbv);
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + Objects.hashCode(this.taxRate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final State other = (State) obj;
        if (!Objects.equals(this.stateAbv, other.stateAbv)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "State{" + "stateAbv=" + stateAbv + ", name=" + name + ", "
                + "taxRate=" + taxRate + '}';
    }

}
