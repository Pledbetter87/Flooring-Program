/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.State;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author MadelineHebel
 */
public class FlooringStateDaoFileImpl implements FlooringStateDao {

    private final String STATE_FILE;
    public static final String DELIMITER = ",";

    public FlooringStateDaoFileImpl() {
        STATE_FILE = "taxes.txt";
    }

    public FlooringStateDaoFileImpl(String stateTextFile) {
        STATE_FILE = stateTextFile;
    }
    
    String header = "StateAbbreviation,StateName,TaxRate";

    private Map<String, State> states = new HashMap<>();

    @Override
    public State addState(State aState) {
        return states.put(aState.getStateAbv(), aState);
    }

    @Override
    public List<State> getAllStates() {
        List<State> statesAsList;
        statesAsList = new ArrayList<>(states.values());
        return statesAsList;
    }

    @Override
    public State getAState(String stateAbv) {
        return states.get(stateAbv);
    }

    @Override
    public void updateAState(String stateAbv, State changedState) {
        states.replace(changedState.getStateAbv(), changedState);
    }

    @Override
    public State removeAState(String stateAbv) {
        State removedState = states.remove(stateAbv);
        return removedState;
    }

    @Override
    public void loadAllStates() throws FlooringPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(STATE_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException("Could not load State data into memory");
        }

        String currentLine;
        State currentState;
        
        header = scanner.nextLine();
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentState = unmarshallState(currentLine);
            states.put(currentState.getStateAbv(), currentState);
        }
        scanner.close();
    }

    @Override
    public void saveAllStates() throws FlooringPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(STATE_FILE));
        } catch (IOException e) {
            throw new FlooringPersistenceException("Could not save Product data"
                    + " into memory", e);
        }

        out.println(header);    
        String stateAsText;
        List<State> stateList = this.getAllStates();
        for (State currentState : stateList) {
            stateAsText = marshallState(currentState);
            out.println(stateAsText);
            out.flush();
        }
        out.close();

    }
    
    private State unmarshallState(String stateAsText) {
        String[] stateTokens = stateAsText.split(DELIMITER);
        
        State stateFromFile = new State();
        
        stateFromFile.setStateAbv(stateTokens[0]);
        
        stateFromFile.setName(stateTokens[1]);
        
        BigDecimal taxRate = new BigDecimal(stateTokens[2]).setScale(2, RoundingMode.HALF_UP);
        stateFromFile.setTaxRate(taxRate);
        
        return stateFromFile;
    }
    
    private String marshallState(State aState) {
        String stateAsText = aState.getStateAbv() + DELIMITER;
        
        stateAsText += aState.getName() + DELIMITER;
        
        stateAsText += aState.getTaxRate();
        
        return stateAsText;
    }
}
