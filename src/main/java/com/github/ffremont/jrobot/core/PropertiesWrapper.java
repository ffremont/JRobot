/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.jrobot.core;

import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author florent
 */
public class PropertiesWrapper {

    private final static Pattern PATTERN = Pattern.compile("\\$\\{([a-zA-Z0-9\\.]+)\\}", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

    private Properties props;

    public PropertiesWrapper(Properties props) {
        this.props = new Properties();

        for (Entry<Object, Object> entry : props.entrySet()) {
            Matcher matcher = PATTERN.matcher((String)entry.getValue());
            if (matcher.groupCount() > 0) {
                    String value = (String)entry.getValue();
                    while(matcher.find()){
                        value = value.replace("${"+matcher.group(1)+"}", props.getProperty(matcher.group(1)) );
                    }
                    this.props.setProperty((String)entry.getKey(), value);
            } else {
                this.props.setProperty((String)entry.getKey(), (String)entry.getValue());
            }
        }

    }

    public String getProperty(String key) {
        return this.props.getProperty(key);
    }

    public Properties getProps() {
        return props;
    }

}
