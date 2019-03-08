/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.State;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author MadelineHebel
 */
public class FlooringStateDaoFileImplTest {

    FlooringStateDao testDao;

    State[] testStates = {
        new State("OH", "Ohio", new BigDecimal(7.15).setScale(2, RoundingMode.HALF_UP)),
        new State("KY", "Kentucky", new BigDecimal(6.00).setScale(2, RoundingMode.HALF_UP)),
        new State("TN", "Tennessee", new BigDecimal(9.46).setScale(2, RoundingMode.HALF_UP)),
        new State("IN", "Indiana", new BigDecimal(7.00).setScale(2, RoundingMode.HALF_UP))
    };

    private List<State> states = Arrays.asList(testStates);

    @Before
    public void setUp() throws Exception {
        String testFile = "testStates.txt";
        new FileWriter(testFile);
        new FileReader(testFile);
        testDao = new FlooringStateDaoFileImpl(testFile);
    }

    @Test
    public void testAddGetState() {
        State state = states.get(0);

        State shouldBeNull = testDao.addState(state);

        State shouldBeState = testDao.getAState(state.getStateAbv());

        Assert.assertNull("Should not have state", shouldBeNull);
        Assert.assertNotNull("Should have state", shouldBeState);

        Assert.assertEquals("OH", state.getStateAbv());
        Assert.assertEquals("Ohio", state.getName());
        Assert.assertEquals(new BigDecimal(7.15).setScale(2, RoundingMode.HALF_UP), state.getTaxRate());
    }

    @Test
    public void testGetAllStates() {

        testDao.addState(testStates[0]);
        testDao.addState(testStates[1]);
        testDao.addState(testStates[2]);
        testDao.addState(testStates[3]);

        List<State> allStates = testDao.getAllStates();

        Assert.assertNotNull(allStates);
        Assert.assertEquals(4, allStates.size());

        Assert.assertTrue(testDao.getAllStates().contains(testStates[0]));
        Assert.assertTrue(testDao.getAllStates().contains(testStates[1]));
        Assert.assertTrue(testDao.getAllStates().contains(testStates[2]));
        Assert.assertTrue(testDao.getAllStates().contains(testStates[3]));

    }

    @Test
    public void testRemoveItem() {
        testDao.addState(testStates[1]);
        testDao.addState(testStates[2]);

        State removedState = testDao.removeAState("TN");

        Assert.assertEquals(removedState, testStates[2]);

        List<State> allStates = testDao.getAllStates();
        Assert.assertEquals(1, allStates.size());
        Assert.assertFalse(allStates.contains(testStates[2]));
        Assert.assertTrue(allStates.contains(testStates[1]));

        removedState = testDao.removeAState("KY");

        Assert.assertEquals(removedState, testStates[1]);

        allStates = testDao.getAllStates();
        Assert.assertTrue(allStates.isEmpty());

    }

    @Test
    public void testUpdateState() {
        testDao.addState(testStates[3]);

        State indianaNewTax= new State("IN", "Indiana", new 
            BigDecimal(6.50).setScale(2, RoundingMode.HALF_UP));

        testDao.updateAState("IN", indianaNewTax);
        
        State indianaUpdate = testDao.getAState("IN");

        Assert.assertEquals(indianaUpdate.getTaxRate(), indianaNewTax.getTaxRate());
    }
    
    @Test
    public void testPersistence() throws FlooringPersistenceException {

        State state1 = states.get(0);
        String abv1 = state1.getStateAbv();
        State state2 = states.get(1);
        String abv2 = state2.getStateAbv();

        testDao.addState(state1);
        testDao.addState(state2);

        testDao.saveAllStates();

        testDao.loadAllStates();

        State new1 = testDao.getAState(abv1);
        State new2 = testDao.getAState(abv2);

        Assert.assertEquals(new1, state1);
        Assert.assertEquals(new2, state2);
    }

}
