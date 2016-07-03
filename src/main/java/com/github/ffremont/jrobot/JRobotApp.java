/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.jrobot;

import com.github.ffremont.jrobot.core.JRobot;
import com.github.ffremont.jrobot.core.caps.PhantomJsWebCapabilitiesFactory;
import com.github.ffremont.jrobot.core.caps.WebCapabilitiesFactory;
import com.github.ffremont.jrobot.core.console.ErrPrintStream;
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
public class JRobotApp {

    final static Logger LOGGER = LoggerFactory.getLogger(JRobotApp.class);
    
    private List<JRobot> tests;

    private PhantomJsWebCapabilitiesFactory phantomJsCapsFactory;
    
    public JRobotApp(){
        this.tests = new ArrayList<>();
        List<String> functionTests = new FastClasspathScanner().scan().getNamesOfSubclassesOf(JRobot.class);
        for(String name : functionTests){
            try {
                try {
                    Class<?> aClass = Class.forName(name);
                    tests.add((JRobot) aClass.newInstance());
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
            LOGGER.error("oops",ex);java.util.logging.Logger.getLogger(JRobotApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args){
        System.setErr(new ErrPrintStream());
        
        Browser browser = Browser.valueOf(Configuration.BROWSER.get());

        String ns = null;
        if (args.length > 0) {
            ns = args[0];
        }

        (new JRobotApp()).run(browser, ns);
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
        tests.forEach((JRobot tf) -> {
            if(ns != null && !tf.getId().startsWith(ns)){
                return;
            }
            browser.setDesiredCapabilities(capsFactory.create());
            SeleniumDriver driver = browser.getDriverForThread();

            //driver.manage().window().setSize(new Dimension(config.getScreen().getWidth(), config.getScreen().getHeight()));

            try{
                tf.useCase();
                LOGGER.info("{} -> {}", tf.getId(), "OK");
            }catch(AssertionError ae){
                LOGGER.info("{} -> {}", tf.getId(), "KO");
            }
        });
    }

    public List<JRobot> getTests() {
        return tests;
    }

    public void setTests(List<JRobot> tests) {
        this.tests = tests;
    }

    public PhantomJsWebCapabilitiesFactory getPhantomJsCapsFactory() {
        return phantomJsCapsFactory;
    }

    public void setPhantomJsCapsFactory(PhantomJsWebCapabilitiesFactory phantomJsCapsFactory) {
        this.phantomJsCapsFactory = phantomJsCapsFactory;
    }
}
