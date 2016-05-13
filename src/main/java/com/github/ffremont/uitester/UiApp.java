/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.uitester;

import com.github.ffremont.uitester.core.FunctionalTest;
import com.github.ffremont.uitester.core.caps.PhantomJsWebCapabilitiesFactory;
import com.github.ffremont.uitester.core.caps.WebCapabilitiesFactory;
import com.github.ffremont.uitester.core.console.ErrPrintStream;
import com.github.ffremont.uitester.core.console.StdPrintStream;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import net.codestory.simplelenium.driver.Browser;
import net.codestory.simplelenium.driver.Configuration;
import net.codestory.simplelenium.driver.SeleniumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author florent
 */
public class UiApp {

    final static Logger LOGGER = LoggerFactory.getLogger(UiApp.class);
    
    private List<FunctionalTest> tests;

    private PhantomJsWebCapabilitiesFactory phantomJsCapsFactory;
    
    public UiApp(){
        this.tests = new ArrayList<>();
        List<String> functionTests = new FastClasspathScanner().scan().getNamesOfSubclassesOf(FunctionalTest.class);
        for(String name : functionTests){
            try {
                try {
                    Class<?> aClass = Class.forName(name);
                    tests.add((FunctionalTest) aClass.newInstance());
                } catch (InstantiationException | IllegalAccessException ex) {
                    LOGGER.error("oops",ex);
                }
            } catch (ClassNotFoundException ex) {
                LOGGER.error("oops",ex);
            }
        }
        
        try {
            phantomJsCapsFactory = PhantomJsWebCapabilitiesFactory.class.newInstance();
        } catch (InstantiationException ex) {
            LOGGER.error("oops",ex);
        } catch (IllegalAccessException ex) {
            LOGGER.error("oops",ex);java.util.logging.Logger.getLogger(UiApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args){
        System.setErr(new ErrPrintStream());
        
        Browser browser = Browser.valueOf(Configuration.BROWSER.get());

        String ns = null;
        if (args.length > 0) {
            ns = args[0];
        }

        (new UiApp()).run(browser, ns);
    }

    /**
     *
     * @param ns
     * @param browser
     */
    public void run(Browser browser, String ns) {
        WebCapabilitiesFactory factory = null;
        if (Browser.PHANTOM_JS.name().equals(browser.name())) {
            factory = phantomJsCapsFactory;
        }
        
        final WebCapabilitiesFactory capsFactory = factory;
        tests.forEach((FunctionalTest tf) -> {
            if(ns != null && !tf.getId().startsWith(ns)){
                return;
            }
            browser.setDesiredCapabilities(capsFactory.create());
            SeleniumDriver driver = browser.getDriverForThread();

            //driver.manage().window().setSize(new Dimension(config.getScreen().getWidth(), config.getScreen().getHeight()));

            try{
                tf.test();
                LOGGER.info("{} -> {}", tf.getId(), "OK");
            }catch(AssertionError ae){
                LOGGER.info("{} -> {}", tf.getId(), "KO");
            }
        });
    }

    public List<FunctionalTest> getTests() {
        return tests;
    }

    public void setTests(List<FunctionalTest> tests) {
        this.tests = tests;
    }

    public PhantomJsWebCapabilitiesFactory getPhantomJsCapsFactory() {
        return phantomJsCapsFactory;
    }

    public void setPhantomJsCapsFactory(PhantomJsWebCapabilitiesFactory phantomJsCapsFactory) {
        this.phantomJsCapsFactory = phantomJsCapsFactory;
    }
}
