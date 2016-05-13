/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.uitester.core.models;

/**
 *
 * @author florent
 */
public class UiConfig {
    private String id;
    private String label;
    private Dimension screen;
    

    public UiConfig(String id, String label, Dimension screen) {
        this.id = id;
        this.label = label;
        this.screen = screen;
    }
    
    public static UiConfig create(String id, String label){
        return new UiConfig(id, label, new Dimension(800, 600));
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Dimension getScreen() {
        return screen;
    }
}
