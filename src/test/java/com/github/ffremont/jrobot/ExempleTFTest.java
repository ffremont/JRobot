/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.jrobot;

import com.github.ffremont.jrobot.core.JRobot;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author florent
 */
public class ExempleTFTest {
    
    static void setFinalStatic(Field field, Object newValue) throws Exception {
      field.setAccessible(true);

      Field modifiersField = Field.class.getDeclaredField("modifiers");
      modifiersField.setAccessible(true);
      modifiersField.set(field, field.getModifiers() & ~Modifier.FINAL);

      field.set(null, newValue);
   }
    
    @Test
    public void test() throws Exception{
        setFinalStatic(JRobot.class.getField("LOCATION"), Paths.get("").toAbsolutePath().toString()+"/target/test-classes");
        
        ExempleTF test = new ExempleTF();
        Assert.assertEquals("http://google.fr/myTest", test.getProperty("ws.url"));
    }
}
