/**
 * 
 */
package com.automation.gi.tests.bankcards.sams.br;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import com.automation.gi.tests.bankcards.br.BR_BankcardsTest;
import com.automation.middleware.CerberusMiddlewareTestListener;

/**
 * @author ddv000b
 *
 */
@Listeners(CerberusMiddlewareTestListener.class)
public class BR_Sams_BankcardsTest extends BR_BankcardsTest {
    
    @Override
    @BeforeClass
    public void bankcardsSetUpBeforeClass() {
	setInterfaceName("bankcards/sams");
    }
}
