/**
 * 
 */
package com.automation.gi.tests.bankcards.br;

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
public class BR_BankcardsTest extends BankcardsTest {

    @Test(groups = { "RT" })
    public void tc01_BR() {
	setCurrentTestCaseInAllDrivers("POS-130463");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("ACTION_CODE", "00", data.getDetail(0).getActionCode());
	sftpClientDriver.assertEquals("ACTION_CODE length is 2", 2, data.getDetail(0).getActionCode().length());
    }

    @Test(groups = { "RT" })
    public void tc02_BR() {
	setCurrentTestCaseInAllDrivers("POS-130464");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is H for Activation", "H", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("Card Type is L for Reload", "L", data.getDetail(1).getCardType());
	sftpClientDriver.assertEquals("Card Type is 7 for visa type code", "7", data.getDetail(2).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc03_BR() {
	setCurrentTestCaseInAllDrivers("POS-130465");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is H for Activation", "H", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("Card Type is L for Reload", "L", data.getDetail(1).getCardType());
	sftpClientDriver.assertEquals("Card Type is 7 for Master type code", "7", data.getDetail(2).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc04_BR() {
	setCurrentTestCaseInAllDrivers("POS-130466");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is 7 for Visa Tender", data.getDetail(0).getCardType(), "7");
	sftpClientDriver.assertEquals("Card Type is N for Debit Tender", data.getDetail(1).getCardType(), "N");
	sftpClientDriver.assertEquals("Card Type is Q for GiftCard Tender", data.getDetail(2).getCardType(), "Q");
    }

    @Test(groups = { "RT" })
    public void tc05_BR() {
	setCurrentTestCaseInAllDrivers("POS-130467");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("record count does not match with total record", data.getDetailsCount() == 7);
	sftpClientDriver.assertEquals("card type is Y", data.getDetail(0).getCardType(), "Y");
	sftpClientDriver.assertEquals("card type is 7", data.getDetail(1).getCardType(), "7");
	sftpClientDriver.assertEquals("card type is N", data.getDetail(2).getCardType(), "N");
	sftpClientDriver.assertEquals("card type is 7", data.getDetail(3).getCardType(), "7");
	sftpClientDriver.assertEquals("card type is 7", data.getDetail(4).getCardType(), "7");
	sftpClientDriver.assertEquals("card type is 7", data.getDetail(5).getCardType(), "7");
	sftpClientDriver.assertEquals("card type is 7", data.getDetail(6).getCardType(), "7");
	sftpClientDriver.assertEquals("field length", data.getDetail(0).getResponseCode(), "000");
	sftpClientDriver.assertEquals("field length", data.getDetail(1).getResponseCode(), "000");
	sftpClientDriver.assertEquals("field length", data.getDetail(2).getResponseCode(), "000");
	sftpClientDriver.assertEquals("field length", data.getDetail(3).getResponseCode(), "000");
	sftpClientDriver.assertEquals("field length", data.getDetail(4).getResponseCode(), "000");
	sftpClientDriver.assertEquals("field length", data.getDetail(5).getResponseCode(), "000");
	sftpClientDriver.assertEquals("field length", data.getDetail(6).getResponseCode(), "000");
    }

    @Test(groups = { "RT" })
    public void tc06_BR() {
	setCurrentTestCaseInAllDrivers("POS-130468");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is 7 for Amex type code", "7", data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc07_BR() {
	setCurrentTestCaseInAllDrivers("POS-130469");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardType is 7 for Diner Card type code", "7", data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc08_BR() {
	setCurrentTestCaseInAllDrivers("POS-130470");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is Y for Private Label type code", "Y", data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc09_BR() {
	setCurrentTestCaseInAllDrivers("POS-130471");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Details count equals 1.", data.getDetailsCount() == 1);
	sftpClientDriver.assertEquals("Card Type is Y for WM Private Label tender type", "Y", data.getDetail(0).getCardType());

    }

    @Test(groups = { "RT" })
    public void tc10_BR() {
	setCurrentTestCaseInAllDrivers("POS-130472");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is 7 for Private Label type code", "7", data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc11_BR() {
	setCurrentTestCaseInAllDrivers("POS-130473");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is 7 for Private Label type code", "7", data.getDetail(0).getCardType());
    }
}
