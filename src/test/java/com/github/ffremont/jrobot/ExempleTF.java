package com.github.ffremont.jrobot;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.github.ffremont.jrobot.core.JRobot;
import com.github.ffremont.jrobot.core.models.UiConfig;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author florent
 */
public class ExempleTF extends JRobot{

    public ExempleTF(){
        super(UiConfig.create("web.test", "mon test"));
    }

    @Override
    public void useCase() {
        goTo("http://localhost:4567");
        
        assertEquals("Page de test", title());
        takeSnapshot();
        
    }
    
}
