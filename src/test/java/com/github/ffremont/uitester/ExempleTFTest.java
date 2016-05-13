/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.uitester;

import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author florent
 */
public class ExempleTFTest {
    @Test
    public void test(){
        ExempleTF test = new ExempleTF();
        Assert.assertEquals("http://google.fr/myTest", test.getProperty("ws.url"));
    }
}
