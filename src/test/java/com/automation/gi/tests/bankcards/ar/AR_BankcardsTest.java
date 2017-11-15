/**
 * 
 */
package com.automation.gi.tests.bankcards.ar;

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
public class AR_BankcardsTest extends BankcardsTest {

    @Test(groups = { "RT" })
    public void tc01_AR() {
	setCurrentTestCaseInAllDrivers("POS-130443");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is H for Activation", "H", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("Card Type is L for Reload", "L", data.getDetail(1).getCardType());
	sftpClientDriver.assertEquals("Card Type is V for Visa", "V", data.getDetail(2).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc02_AR() {
	setCurrentTestCaseInAllDrivers("POS-130444");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is M for MasterCard", "M", data.getDetail(2).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc03_AR() {
	setCurrentTestCaseInAllDrivers("POS-130445");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is V for Empty Subtype", "V", data.getDetail(1).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc04_AR() {
	setCurrentTestCaseInAllDrivers("POS-130446");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is V for Visa Tender", data.getDetail(0).getCardType(), "V");
	sftpClientDriver.assertEquals("Card Type is N for Debit Tender", data.getDetail(1).getCardType(), "N");
	sftpClientDriver.assertEquals("Card Type is Q for GiftCard Tender", data.getDetail(2).getCardType(), "Q");
    }

    @Test(groups = { "RT" })
    public void tc05_AR() {
	setCurrentTestCaseInAllDrivers("POS-130447");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	 sftpClientDriver.assertEquals("Card Type is A for Amex type code", "A",data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc06_AR() {
	setCurrentTestCaseInAllDrivers("POS-130448");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	 sftpClientDriver.assertEquals("Card Type is I", "I",data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc07_AR() {
	setCurrentTestCaseInAllDrivers("POS-130449");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	 sftpClientDriver.assertEquals("Card Type is Y for Private Label type code", "Y",data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc08_AR() {
	setCurrentTestCaseInAllDrivers("POS-130450");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is 7 for Private Label type code", "7",data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc09_AR() {
	setCurrentTestCaseInAllDrivers("POS-130451");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is V for Private Label type code", "V",data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc10_AR() {
	setCurrentTestCaseInAllDrivers("POS-130452");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is M for Private Label type code", "M",data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc11_AR() {
	setCurrentTestCaseInAllDrivers("POS-130453");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("record count does not match with total record", data.getDetailsCount() == 7);
        sftpClientDriver.assertEquals("card type is Y", "Y", data.getDetail(0).getCardType());
        sftpClientDriver.assertEquals("card type is 7", "7", data.getDetail(1).getCardType());
        sftpClientDriver.assertEquals("card type is N", "N", data.getDetail(2).getCardType());
        sftpClientDriver.assertEquals("card type is A", "A", data.getDetail(3).getCardType());
        sftpClientDriver.assertEquals("card type is 7", "7", data.getDetail(4).getCardType());
        sftpClientDriver.assertEquals("card type is M", "M", data.getDetail(5).getCardType());
        sftpClientDriver.assertEquals("card type is V", "V", data.getDetail(6).getCardType());
        sftpClientDriver.assertEquals("field length", "000", data.getDetail(0).getResponseCode());
        sftpClientDriver.assertEquals("field length", "000", data.getDetail(1).getResponseCode());
        sftpClientDriver.assertEquals("field length", "000", data.getDetail(2).getResponseCode());
        sftpClientDriver.assertEquals("field length", "000", data.getDetail(3).getResponseCode());
        sftpClientDriver.assertEquals("field length", "000", data.getDetail(4).getResponseCode());
        sftpClientDriver.assertEquals("field length", "000", data.getDetail(5).getResponseCode());
        sftpClientDriver.assertEquals("field length", "000", data.getDetail(6).getResponseCode());
        sftpClientDriver.assertEquals("Response code length", data.getDetail(2).getResponseCode().length(), 3);
    }
}
