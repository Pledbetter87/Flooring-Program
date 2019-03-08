/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.util.Objects;

/**
 *
 * @author MadelineHebel
 */
public class Mode {
    private String appSettings;
    private String mode;

    public Mode(String appSettings, String mode) {
        this.appSettings = appSettings;
        this.mode = mode;
    }

    public Mode() {
    }

    public String getAppSettings() {
        return appSettings;
    }

    public void setAppSettings(String appSettings) {
        this.appSettings = appSettings;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.appSettings);
        hash = 83 * hash + Objects.hashCode(this.mode);
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
        final Mode other = (Mode) obj;
        if (!Objects.equals(this.appSettings, other.appSettings)) {
            return false;
        }
        if (!Objects.equals(this.mode, other.mode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mode{" + "appSettings=" + appSettings + ", mode=" + mode + '}';
    }
    
    
}
