/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.jrobot.core;

/**
 *
 * @author florent
 */
public class UiRuntimeException extends RuntimeException{

    public UiRuntimeException(String message) {
        super(message);
    }
    
    public UiRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
