/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.State;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public interface FlooringStateDao {
    
    public void loadAllStates()
            throws FlooringPersistenceException;

    public void saveAllStates()
            throws FlooringPersistenceException;

    public State addState(State aState);

    public List<State> getAllStates();

    public State getAState(String stateAbv);

    public void updateAState(String stateAbv, State changedState);

    public State removeAState(String stateAbv);
    
}
