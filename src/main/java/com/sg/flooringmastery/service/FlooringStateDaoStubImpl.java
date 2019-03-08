/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dao.FlooringStateDao;
import com.sg.flooringmastery.dto.State;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public class FlooringStateDaoStubImpl implements FlooringStateDao {

    public State onlyState;

    public FlooringStateDaoStubImpl() {
        onlyState = new State();
        onlyState.setStateAbv("KY");
        onlyState.setName("Kentucky");
        BigDecimal taxRate = new BigDecimal(6.00).setScale(2, RoundingMode.HALF_UP);
        onlyState.setTaxRate(taxRate);
    }
    
    public FlooringStateDaoStubImpl(State testState) {
        this.onlyState = testState;
    }

    @Override
    public void loadAllStates() throws FlooringPersistenceException {

    }

    @Override
    public void saveAllStates() throws FlooringPersistenceException {

    }

    @Override
    public State addState(State aState) {
        if (aState.equals(onlyState)) {
            return onlyState;
        } else {
            return null;
        }
    }

    @Override
    public List<State> getAllStates() {
        List<State> stateList = new ArrayList<>();
        stateList.add(onlyState);
        return stateList;
    }

    @Override
    public State getAState(String stateAbv) {
        if (stateAbv.equals(onlyState.getStateAbv())) {
            return onlyState;
        } else {
            return null;
        }
    }

    @Override
    public void updateAState(String stateAbv, State changedState) {
        
    }

    @Override
    public State removeAState(String stateAbv) {
        if (stateAbv.equals(onlyState.getStateAbv())) {
            return onlyState;
        } else {
            return null;
        }
    }

}
