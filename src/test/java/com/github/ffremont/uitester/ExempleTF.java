package com.github.ffremont.uitester;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.github.ffremont.uitester.core.FunctionalTest;
import com.github.ffremont.uitester.core.models.UiConfig;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author florent
 */
public class ExempleTF extends FunctionalTest{

    public ExempleTF(){
        super(UiConfig.create("web.test", "mon test"));
    }

    @Override
    public void test() {
        goTo("http://localhost:4567");
        
        assertEquals("Page de test", title());
        takeSnapshot();
        
    }
    
}
