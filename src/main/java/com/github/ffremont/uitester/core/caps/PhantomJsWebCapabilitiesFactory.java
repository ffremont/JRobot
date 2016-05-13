/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.uitester.core.caps;

import com.github.ffremont.uitester.core.UiTesterRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author florent
 */
public class PhantomJsWebCapabilitiesFactory implements WebCapabilitiesFactory {

    final static Logger LOGGER = LoggerFactory.getLogger(PhantomJsWebCapabilitiesFactory.class);
    
    private static final String CONFIG_FILE = "phantomjs.properties";
    
    private final Properties sConfig;

    public PhantomJsWebCapabilitiesFactory() {
        sConfig = new Properties();
        try {
            sConfig.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE));
        } catch (IOException ex) {
            throw new UiTesterRuntimeException("impossible de changer la config de phantomjs", ex); 
       }
        
    }

    @Override
    public DesiredCapabilities create() {        
        DesiredCapabilities sCaps = new DesiredCapabilities();
        sCaps.setJavascriptEnabled(true);
        sCaps.setCapability("takesScreenshot", false);
        
        // "phantomjs_exec_path"
        if (sConfig.getProperty("phantomjs_exec_path") != null) {
            sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, sConfig.getProperty("phantomjs_exec_path"));
        } else {
            throw new UiTesterRuntimeException(String.format("Property '%s' not set!", PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY));
        }
        // "phantomjs_driver_path"
        if (sConfig.getProperty("phantomjs_driver_path") != null) {
            LOGGER.debug("Test will use an external GhostDriver");
            sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_PATH_PROPERTY, sConfig.getProperty("phantomjs_driver_path"));
        } else {
            LOGGER.debug("Test will use PhantomJS internal GhostDriver");
        }
        
        List<String> cliArgsCap = new ArrayList<>();
        cliArgsCap.add("--web-security=false");
        cliArgsCap.add("--ssl-protocol=any");
        cliArgsCap.add("--ignore-ssl-errors=true");
        sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);

        // Control LogLevel for GhostDriver, via CLI arguments
        sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS, new String[] {
            "--logLevel=" + (sConfig.getProperty("phantomjs_driver_loglevel") != null ? sConfig.getProperty("phantomjs_driver_loglevel") : "ERROR"),
            "--logFile=" + (sConfig.getProperty("phantomjs_driver_logFile") != null ? sConfig.getProperty("phantomjs_driver_logFile") : "phantomjs.log")
        });
        
        return sCaps;
    }
}
