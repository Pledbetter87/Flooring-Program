/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Mode;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author MadelineHebel
 */
public class FlooringModeDaoFileImpl implements FlooringModeDao {

    private final String MODE_FILE;
    public static final String DELIMITER = "::";

    public FlooringModeDaoFileImpl() {
        MODE_FILE = "mode.txt";
    }

    public FlooringModeDaoFileImpl(String modeTextFile) {
        MODE_FILE = modeTextFile;
    }

    private Map<String, Mode> modes = new HashMap<>();

    @Override
    public List<Mode> getMode() {
        List<Mode> theMode;
        theMode = new ArrayList<>(modes.values());
        return theMode;
    }

    @Override
    public void loadMode() throws FlooringPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader(MODE_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException("Could not load Mode data into memory", e);
        }

        String currentLine;
        Mode currentMode;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentMode = unmarshallMode(currentLine);
            modes.put(currentMode.getAppSettings(), currentMode);
        }
        scanner.close();
    }
    
    private Mode unmarshallMode(String modeAsText) {
        String[] modeTokens = modeAsText.split(DELIMITER);

        Mode modeFromFile = new Mode();

        modeFromFile.setAppSettings(modeTokens[0]);
        modeFromFile.setMode(modeTokens[1]);

        return modeFromFile;
    }
}
