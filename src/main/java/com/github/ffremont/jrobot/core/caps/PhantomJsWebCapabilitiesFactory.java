/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.jrobot.core.caps;

import com.github.ffremont.jrobot.core.UiRuntimeException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

    private final static String PHANTOMJS_FILENAME = "phantomjs.properties";
    public final static String CONFIG_FILE = Paths.get(PHANTOMJS_FILENAME).toAbsolutePath().toString();

    private final Properties sConfig;

    public PhantomJsWebCapabilitiesFactory() {
        sConfig = new Properties();
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(PHANTOMJS_FILENAME);
            if (is == null) {
                sConfig.load(Files.newInputStream(Paths.get(CONFIG_FILE), StandardOpenOption.READ));
            } else {
                sConfig.load(is);
            }
        } catch (IOException ex) {
            throw new UiRuntimeException("impossible de changer la config de phantomjs", ex);
        }

    }

    @Override
    public DesiredCapabilities create() {
        DesiredCapabilities sCaps = new DesiredCapabilities();
        sCaps.setJavascriptEnabled(true);
        sCaps.setCapability("takesScreenshot", true);

        // "phantomjs_exec_path"
        if (sConfig.getProperty("phantomjs_exec_path") != null) {
            sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, sConfig.getProperty("phantomjs_exec_path"));
        } else {
            throw new UiRuntimeException(String.format("Property '%s' not set!", PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY));
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
        sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS, new String[]{
            "--logLevel=" + (sConfig.getProperty("phantomjs_driver_loglevel") != null ? sConfig.getProperty("phantomjs_driver_loglevel") : "ERROR"),
            "--logFile=" + (sConfig.getProperty("phantomjs_driver_logFile") != null ? sConfig.getProperty("phantomjs_driver_logFile") : "phantomjs.log")
        });

        return sCaps;
    }
}
