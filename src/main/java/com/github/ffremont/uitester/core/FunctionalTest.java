/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.uitester.core;

import com.github.ffremont.uitester.core.models.UiConfig;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import net.codestory.simplelenium.SeleniumTest;
import org.slf4j.LoggerFactory;

/**
 *
 * @author florent
 */
public abstract class FunctionalTest extends SeleniumTest {

    final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(FunctionalTest.class);

    public final static String ENV_PROPERTY = "env";

    private static Properties COMMONS_PROPS;
    private static Properties COMMONS_ENV_PROPS;

    private String id;
    private String baseUrl;
    private PropertiesWrapper props;

    public FunctionalTest(UiConfig config) {
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
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(testProps);
            if (is != null) {
                effectiveProps.load(is);
            }
            effectiveProps.putAll(commonsEnvProps);

            Properties envv = new Properties();
            if (env != null) {
                String envTestProps = baseprop + "_" + env + ".properties";
                is = Thread.currentThread().getContextClassLoader().getResourceAsStream(envTestProps);
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

    public abstract void test();

    public static Properties getCommonsProps(String env, Properties from) {
        if (COMMONS_ENV_PROPS == null) {
            String commonsPropsPath = "commons" + "_" + env + ".properties";
            COMMONS_ENV_PROPS = new Properties(from);
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(commonsPropsPath);
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
            String commonsPropsPath = "commons.properties";
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(commonsPropsPath);
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
