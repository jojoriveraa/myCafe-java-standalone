/**
 * 
 */
package com.automation.gi.tests.bankcards.sams.mx;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import com.automation.gi.tests.bankcards.mx.MX_BankcardsTest;
import com.automation.middleware.CerberusMiddlewareTestListener;

/**
 * @author ddv000b
 *
 */
@Listeners(CerberusMiddlewareTestListener.class)
public class MX_Sams_BankcardsTest extends MX_BankcardsTest {
    
    @Override
    @BeforeClass
    public void bankcardsSetUpBeforeClass() {
	setInterfaceName("bankcards/sams");
    }
}
