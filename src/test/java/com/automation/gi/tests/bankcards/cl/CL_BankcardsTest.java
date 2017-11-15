/**
 * 
 */
package com.automation.gi.tests.bankcards.cl;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.common.logger.CerberusLogger;
import com.automation.gi.tests.bankcards.BankcardsTest;
import com.automation.middleware.CerberusMiddlewareTestListener;
import com.walmart.pos.wits.format.bankcard.BankcardData;

/**
 * @author ddv000b
 *
 */
@Listeners(CerberusMiddlewareTestListener.class)
public class CL_BankcardsTest extends BankcardsTest {
    
    @Override
    @Test(groups = { "RT" })
    public void tc01() {
	setCurrentTestCaseInAllDrivers("POS-129855");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();	
	sftpClientDriver.assertEquals("Details count", 1, data.getDetailsCount());
        sftpClientDriver.assertEquals("TRANS_CODE is 01", "1", data.getDetail(0).getTransCode());
        sftpClientDriver.assertEquals("Store Nbr",getStoreNumber().substring(3), data.getDetail(0).getStoreNumber());
        sftpClientDriver.assertEquals("Store Nbr length", 6, data.getDetail(0).getStoreNumber().length());
        sftpClientDriver.assertEquals("Exception code", " ", data.getDetail(0).getExceptionCode());
        sftpClientDriver.assertEquals("Amount ", "0000106664",data.getDetail(0).getTransAmount());
	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BANKCARDS_ID));
    }
    
    @Test(groups = { "RT" })
    public void tc01_CL() {
	setCurrentTestCaseInAllDrivers("POS-130499");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();	
	sftpClientDriver.assertEquals("Card Type is H for Activation", "H",data.getDetail(0).getCardType());
        sftpClientDriver.assertEquals("Card Type is L for Reload", "L",data.getDetail(1).getCardType());
        sftpClientDriver.assertEquals("Card Type is 7 for visa type code", "7",data.getDetail(2).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc02_CL() {
	setCurrentTestCaseInAllDrivers("POS-130500");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();	
	sftpClientDriver.assertEquals("Card Type is 7", "7",data.getDetail(1).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc03_CL() {
	setCurrentTestCaseInAllDrivers("POS-130501");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();	
	sftpClientDriver.assertEquals("Card Type is H for Activation", "H",data.getDetail(0).getCardType());
        sftpClientDriver.assertEquals("Card Type is L for Reload", "L",data.getDetail(1).getCardType());
        sftpClientDriver.assertEquals("Card Type is 7 for Master type code", "7",data.getDetail(2).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc04_CL() {
	setCurrentTestCaseInAllDrivers("POS-130502");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();	
	sftpClientDriver.assertEquals("Card Type is 7", "7",data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc05_CL() {
	setCurrentTestCaseInAllDrivers("POS-130503");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();	
	 sftpClientDriver.assertEquals("Card Type is 7 for Master Card type code", "7",data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc06_CL() {
	setCurrentTestCaseInAllDrivers("POS-130504");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();	
	sftpClientDriver.assertEquals("Card Type is 7 for Amex type code", "7",data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc07_CL() {
	setCurrentTestCaseInAllDrivers("POS-130505");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();	
	sftpClientDriver.assertEquals("Card Type is 7 for Diners type code", "7",data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc08_CL() {
	setCurrentTestCaseInAllDrivers("POS-130506");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();	
	sftpClientDriver.assertEquals("Card Type is 7 for Discover Card type code", "7",data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc09_CL() {
	setCurrentTestCaseInAllDrivers("POS-130507");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is 7 for Private Label type code", "7",data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc10_CL() {
	setCurrentTestCaseInAllDrivers("POS-130508");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();	
	sftpClientDriver.assertEquals("Card Type is S for Presto Shopping Card type code", "S",data.getDetail(0).getCardType());
    }   

}
