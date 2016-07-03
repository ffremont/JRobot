/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.jrobot;

import static com.github.ffremont.jrobot.ExempleTFTest.setFinalStatic;
import com.github.ffremont.jrobot.core.JRobot;
import com.github.ffremont.jrobot.core.caps.PhantomJsWebCapabilitiesFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Paths;
import java.util.Arrays;
import net.codestory.simplelenium.driver.Browser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author florent
 */
public class JRobotAppTest extends UiTest {
    
    @BeforeClass
    public static void before() throws Exception{
        setFinalStatic(PhantomJsWebCapabilitiesFactory.class.getField("CONFIG_FILE"), Paths.get("src", "test", "resources", "phantomjs.properties").toAbsolutePath().toString());
    }

    static void setFinalStatic(Field field, Object newValue) throws Exception {
      field.setAccessible(true);

      Field modifiersField = Field.class.getDeclaredField("modifiers");
      modifiersField.setAccessible(true);
      modifiersField.set(field, field.getModifiers() & ~Modifier.FINAL);

      field.set(null, newValue);
   }
    
    @Test
    public void instanceJRobotApp() throws NoSuchFieldException, Exception{
        JRobotApp app = new JRobotApp();
        Assert.assertEquals(1, app.getTests().size());
    }
        
    @Test
    public void run_ok() throws NoSuchFieldException, Exception{
        JRobotApp app = new JRobotApp();
        app.setPhantomJsCapsFactory(new PhantomJsWebCapabilitiesFactory());
        app.setTests(Arrays.asList(new ExempleTF()));
        
        app.run(Browser.PHANTOM_JS, "web");
    }
}
