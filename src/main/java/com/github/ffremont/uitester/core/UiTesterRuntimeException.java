/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.uitester.core;

/**
 *
 * @author florent
 */
public class UiTesterRuntimeException extends RuntimeException{

    public UiTesterRuntimeException(String message) {
        super(message);
    }
    
    public UiTesterRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
