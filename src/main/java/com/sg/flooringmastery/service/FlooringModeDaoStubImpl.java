/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringModeDao;
import com.sg.flooringmastery.dao.FlooringPersistenceException;
import com.sg.flooringmastery.dto.Mode;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MadelineHebel
 */
public class FlooringModeDaoStubImpl implements FlooringModeDao {
    
    public Mode onlyMode;
    
    public FlooringModeDaoStubImpl() {
        onlyMode = new Mode();
        onlyMode.setAppSettings("appSettings");
        onlyMode.setMode("training");
    }
    
    public FlooringModeDaoStubImpl(Mode testMode) {
        this.onlyMode = testMode;
    }

    @Override
    public void loadMode() throws FlooringPersistenceException {
        
    }

    @Override
    public List<Mode> getMode() {
         List<Mode> modeList = new ArrayList<>();
        modeList.add(onlyMode);
        return modeList;
    }
    
}
