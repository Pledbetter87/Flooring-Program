/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Mode;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public interface FlooringModeDao {
    public void loadMode() throws FlooringPersistenceException;
    
    public List<Mode> getMode();
}
