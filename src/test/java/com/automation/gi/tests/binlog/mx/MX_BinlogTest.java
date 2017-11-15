/**
 * 
 */
package com.automation.gi.tests.binlog.mx;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.gi.tests.binlog.BinlogTest;
import com.automation.middleware.CerberusMiddlewareTestListener;

/**
 * @author David Villalobos
 *
 */
@Listeners(CerberusMiddlewareTestListener.class)
public class MX_BinlogTest extends BinlogTest {
    
    @Test(groups = { "RT" })
    public void tc01_MX() {
	
    }
}
