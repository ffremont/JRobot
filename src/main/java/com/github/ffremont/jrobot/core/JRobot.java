/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.jrobot.core;

import com.github.ffremont.jrobot.core.models.UiConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;
import net.codestory.simplelenium.SeleniumTest;
import org.slf4j.LoggerFactory;

/**
 *
 * @author florent
 */
public abstract class JRobot extends SeleniumTest {

    final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JRobot.class);

    public final static String LOCATION = Paths.get("").toAbsolutePath().toString();
    
    public final static String ENV_PROPERTY = "env";

    private static Properties COMMONS_PROPS;
    private static Properties COMMONS_ENV_PROPS;

    private String id;
    private String baseUrl;
    private PropertiesWrapper props;

    public JRobot(UiConfig config) {
        super();
        this.id = config.getId();
        try {
            String env = System.getProperty(ENV_PROPERTY);

            Properties commonsEnvProps;
            if (env != null) {
                commonsEnvProps = getCommonsProps(env, getCommonsProps());
            }else{
                commonsEnvProps = getCommonsProps();
            }

            String baseprop = config.getId().replace(".", System.getProperty("file.separator"));
            String testProps = baseprop + ".properties";
            Properties effectiveProps = new Properties();
            File basepropFile = new File(
                    LOCATION
                    +System.getProperty("file.separator")
                    +testProps);
            InputStream is = null;
            if(basepropFile.exists()){
                is = new FileInputStream(basepropFile);
            }
            if (is != null) {
                effectiveProps.load(is);
            }
            effectiveProps.putAll(commonsEnvProps);

            Properties envv = new Properties();
            if (env != null) {
                String envTestProps = baseprop + "_" + env + ".properties";
                File envTestPropsFile = new File(LOCATION+System.getProperty("file.separator")+envTestProps);
                if(envTestPropsFile.exists()){
                    is = new FileInputStream(envTestPropsFile);
                }
                if (is != null) {
                    envv.load(is);
                }
            }
            effectiveProps.putAll(envv);
            
            this.props = new PropertiesWrapper(effectiveProps);
        } catch (IOException ex) {
            LOGGER.error("impossible de charger les properties", ex);
        }

        this.baseUrl = "";
    }

    public abstract void useCase();

    public static Properties getCommonsProps(String env, Properties from) {
        if (COMMONS_ENV_PROPS == null) {
            String commonsPropsPath = 
                    LOCATION
                    +System.getProperty("file.separator")
                    +"commons" + "_" + env + ".properties";
            COMMONS_ENV_PROPS = new Properties(from);
            
            InputStream is = null;
            File commonsPropsFile = new File(commonsPropsPath);
            if(commonsPropsFile.exists()){
                try {
                    is = new FileInputStream(commonsPropsFile);
                } catch (FileNotFoundException ex) {
                    LOGGER.error("oups",ex);
                }
            }
            if (is != null) {
                try {
                    COMMONS_ENV_PROPS.load(is);
                } catch (IOException ex) {
                    LOGGER.error("impossible de charge les props communes", ex);
                }
            }
        }

        return COMMONS_ENV_PROPS;
    }

    public static Properties getCommonsProps() {
        if (COMMONS_PROPS == null) {
            String commonsPropsPath = 
                    LOCATION
                    +System.getProperty("file.separator")
                    +"commons.properties";
            //InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(commonsPropsPath);
             InputStream is = null;
            File commonsPropsFile = new File(commonsPropsPath);
            if(commonsPropsFile.exists()){
                try {
                    is = new FileInputStream(commonsPropsFile);
                } catch (FileNotFoundException ex) {
                    LOGGER.error("oups",ex);
                }
            }
            COMMONS_PROPS = new Properties();
            if (is != null) {
                try {
                    COMMONS_PROPS.load(is);
                } catch (IOException ex) {
                    LOGGER.error("impossible de charge les props communes", ex);
                }
            }
        }

        return COMMONS_PROPS;
    }

    @Override
    protected String getDefaultBaseUrl() {
        return this.baseUrl;
    }

    public String getId() {
        return id;
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }
    
    
}
