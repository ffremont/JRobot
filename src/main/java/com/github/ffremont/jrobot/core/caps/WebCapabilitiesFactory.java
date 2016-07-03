/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.jrobot.core.caps;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author florent
 */
public interface WebCapabilitiesFactory {
    
    public DesiredCapabilities create();    
}
