/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.jrobot;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.resource.FileResourceManager;
import java.nio.file.Paths;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 *
 * @author florent
 */
public abstract class UiTest {
    
    private static Undertow server;
    
    @BeforeClass
    public static void beforeClass(){
        FileResourceManager prm = new FileResourceManager(Paths.get("./src/test/resources/publicTest/").toFile(), 1024);
        
         server = Undertow.builder()                                                    
                .addHttpListener(4567, "localhost")
                .setHandler( Handlers.resource(prm).setDirectoryListingEnabled(false) ).build();
        
        server.start();
        
    }
    
    @AfterClass
    public static void afterClass(){
        server.stop();
    }
    
    
}
