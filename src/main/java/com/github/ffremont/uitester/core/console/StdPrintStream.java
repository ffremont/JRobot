/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.uitester.core.console;

import java.io.PrintStream;
import org.apache.commons.io.output.NullOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author florent
 */
public class StdPrintStream extends PrintStream {

    final static Logger LOGGER = LoggerFactory.getLogger(StdPrintStream.class);

    public StdPrintStream() {
        super(new NullOutputStream());
    }

    @Override
    public void print(String s) {
        LOGGER.info(s);
    }

}
