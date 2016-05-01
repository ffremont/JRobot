/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ffremont.uitester;

import com.github.ffremont.uitester.core.caps.PhantomJsWebCapabilitiesFactory;
import java.util.Arrays;
import net.codestory.simplelenium.driver.Browser;
import org.junit.Test;

/**
 *
 * @author florent
 */
public class UiAppTest extends UiTest {

    @Test
    public void run_ok(){
        UiApp app = new UiApp();
        app.setPhantomJsCapsFactory(new PhantomJsWebCapabilitiesFactory());
        app.setTests(Arrays.asList(new ExempleTF()));
        
        app.run(Browser.PHANTOM_JS, null);
        
        
    }
}
