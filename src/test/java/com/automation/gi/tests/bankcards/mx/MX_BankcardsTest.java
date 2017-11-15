/**
 * 
 */
package com.automation.gi.tests.bankcards.mx;

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
public class MX_BankcardsTest extends BankcardsTest {

    @Override
    @Test(groups = { "RT" })
    public void tc15() {
	setCurrentTestCaseInAllDrivers("POS-129904");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is equal to '7'", "7", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("Prior Balance due ", "00000000", data.getDetail(0).getPriorBalDue());
	sftpClientDriver.assertEquals("Prior balance due length is 8", 8, data.getDetail(0).getPriorBalDue().length());
	sftpClientDriver.assertEquals("Expire Date ", "0000", data.getDetail(0).getExpireDate());
	sftpClientDriver.assertEquals("Expire Date length is 4", 4, data.getDetail(0).getExpireDate().length());
	sftpClientDriver.assertEquals("EPS_AC_QUALIFIER ", "00", data.getDetail(0).getEpsAcQualifier());
	sftpClientDriver.assertEquals("EPS_AC_QUALIFIER length is 2", 2, data.getDetail(0).getEpsAcQualifier().length());
    }

    @Override
    @Test(groups = { "RT" })
    public void tc16() {
	setCurrentTestCaseInAllDrivers("POS-129905");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is equal to '7'", "7", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("SCHEMA_CODE", "0", data.getDetail(0).getSchemeCode());
	sftpClientDriver.assertEquals("SCHEMA_CODE is length is 1", 1, data.getDetail(0).getSchemeCode().length());
	sftpClientDriver.assertEquals("TRANS_SOURCE", "1", data.getDetail(0).getTransSource());
	sftpClientDriver.assertEquals("TRANS_SOURCE is length is 1", 1, data.getDetail(0).getTransSource().length());
	sftpClientDriver.assertEquals("PLAN_ID", " ", data.getDetail(0).getPlanId());
	sftpClientDriver.assertEquals("PLAN_ID is length is 1", 1, data.getDetail(0).getPlanId().length());
	sftpClientDriver.assertEquals("CLOSE_ID", " ", data.getDetail(0).getCloseId());
	sftpClientDriver.assertEquals("CLOSE_ID is length is 1", 1, data.getDetail(0).getCloseId().length());
    }

    @Override
    @Test(groups = { "RT" })
    public void tc17() {
	setCurrentTestCaseInAllDrivers("POS-129906");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Detail Records count is 2", data.getDetailsCount() == 2);
	sftpClientDriver.assertEquals("CardType is 'H'", "H", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardType is 'Y'", "7", data.getDetail(1).getCardType());
	sftpClientDriver.assertEquals("ORIGIN TICKET NBR is correct", "0000", data.getDetail(1).getOriginalTicketNumber());
	sftpClientDriver.assertEquals("ORIGIN TICKET NBR length is 4", 4, data.getDetail(1).getOriginalTicketNumber().length());
	sftpClientDriver.assertEquals("ORIGIN TICKET DATE is correct", "000000", data.getDetail(1).getOriginalTicketDate());
	sftpClientDriver.assertEquals("ORIGIN TICKET DATE length is 6", 6, data.getDetail(1).getOriginalTicketDate().length());
	sftpClientDriver.assertEquals("AUTH REQUEST CODE is correct", "000000", data.getDetail(1).getAuthRequestCode());
	sftpClientDriver.assertEquals("AUTH REQUEST CODE length is 6", 6, data.getDetail(1).getAuthRequestCode().length());
	sftpClientDriver.assertEquals("AUTH RESPONSE CODE is correct", "00", data.getDetail(1).getAuthResponseCode());
	sftpClientDriver.assertEquals("AUTH RESPONSE CODE length is 2", 2, data.getDetail(1).getAuthResponseCode().length());
    }

    @Override
    @Test(groups = { "RT" })
    public void tc18() {
	setCurrentTestCaseInAllDrivers("POS-129907");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Detail Records count is 2", data.getDetailsCount() == 2);
	sftpClientDriver.assertTrue("incorrect num of records", data.getDetailsCount() == 2);
	sftpClientDriver.assertEquals("CardType is 'L'", "L", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardType is '7'", "7", data.getDetail(1).getCardType());
	sftpClientDriver.assertEquals("Card Sub Type is correct", "0000", data.getDetail(0).getCardSubtype());
	sftpClientDriver.assertEquals("Card Sub Type length is 4", 4, data.getDetail(1).getCardSubtype().length());
	sftpClientDriver.assertEquals("TC Number is correct", "000000000000000000000000", data.getDetail(0).getTcNumber());
	sftpClientDriver.assertEquals("TC Number length is 24", 24, data.getDetail(1).getTcNumber().length());
	sftpClientDriver.assertEquals("Swipe Indicator is 4 for CreditDebit Tender ", "4", data.getDetail(0).getSwipeIndicator());
    }

    @Override
    @Test(groups = { "RT" })
    public void tc19() {
	setCurrentTestCaseInAllDrivers("POS-129908");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Detail Records count is 3", data.getDetailsCount() == 3);
	sftpClientDriver.assertEquals("CardType is 'H'", "H", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardType is 'L'", "L", data.getDetail(1).getCardType());
	sftpClientDriver.assertEquals("CardType is '7'", "7", data.getDetail(2).getCardType());
	sftpClientDriver.assertEquals("Swipe Indicator is 4 for EntryMethod keyed", "4", data.getDetail(0).getSwipeIndicator());
    }

//    @Override
//    @Test(groups = { "RT" })
//    public void tc27() {
//	setCurrentTestCaseInAllDrivers("POS-129916");
//	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
//	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
//	verifyEtlFileExists(getRemoteBankcardFilePath());
//	getEtlBankcardFile();
//
//	BankcardData data = getBankcardData();
//	sftpClientDriver.assertTrue("Detail Records count is  not 1", data.getDetailsCount() == 1);
//	sftpClientDriver.assertEquals("CardType is 'Q'", "Q", data.getDetail(0).getCardType());
//	sftpClientDriver.assertEquals("Transaction Nbr", "00" + getSequenceNumber() + "00", data.getDetail(0).getTransactionNumber());
//	sftpClientDriver.assertEquals("Card Sub Type", "0000", data.getDetail(0).getCardSubtype());
//    }

    @Override
    @Test(groups = { "RT" })
    public void tc49() {
	setCurrentTestCaseInAllDrivers("POS-129938");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Detail  record count", "2", data.getDetailsCount().toString());
	sftpClientDriver.assertEquals("card Type", "V", data.getDetail(1).getCardType());
    }

    @Test
    public void tc01_MX() {
	setCurrentTestCaseInAllDrivers("POS-131025");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is equal to 'H'", "H", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("Card Sub Type", "0000", data.getDetail(0).getCardSubtype());
    }

    @Test
    public void tc02_MX() {
	setCurrentTestCaseInAllDrivers("POS-131026");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardType is 'L'", "L", data.getDetail(1).getCardType());
	sftpClientDriver.assertEquals("Card Sub Type", "0000", data.getDetail(1).getCardSubtype());
    }

    @Test
    public void tc03_MX() {
	setCurrentTestCaseInAllDrivers("POS-131027");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Details count is 1.", data.getDetailsCount() == 1);
	sftpClientDriver.assertEquals("CardType is 'S'", "S", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("Card Sub Type", "0000", data.getDetail(0).getCardSubtype());
    }

    @Test
    public void tc04_MX() {
	setCurrentTestCaseInAllDrivers("POS-131028");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardSubType value", "0001", data.getDetail(0).getCardSubtype());
	sftpClientDriver.assertEquals("CardSubType length is 4", 4, data.getDetail(0).getCardSubtype().length());
    }

    @Test
    public void tc05_MX() {
	setCurrentTestCaseInAllDrivers("POS-131029");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardSubType for Banamex is 0003", "0003", data.getDetail(0).getCardSubtype());
	sftpClientDriver.assertEquals("CardSubType length is 4", 4, data.getDetail(0).getCardSubtype().length());
    }

    @Test
    public void tc06_MX() {
	setCurrentTestCaseInAllDrivers("POS-131030");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardSubType for Banamex is 0010", "0010", data.getDetail(0).getCardSubtype());
	sftpClientDriver.assertEquals("CardSubType length is 4", 4, data.getDetail(0).getCardSubtype().length());
    }

    @Test
    public void tc07_MX() {
	setCurrentTestCaseInAllDrivers("POS-131031");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardSubType for Maestro is 0007", "0007", data.getDetail(0).getCardSubtype());
	sftpClientDriver.assertEquals("CardSubType length is 4", 4, data.getDetail(0).getCardSubtype().length());
    }

    @Test
    public void tc08_MX() {
	setCurrentTestCaseInAllDrivers("POS-131032");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardSubType is 0006", "0006", data.getDetail(0).getCardSubtype());
	sftpClientDriver.assertEquals("CardSubType length is 4", 4, data.getDetail(0).getCardSubtype().length());
    }

    @Test
    public void tc09_MX() {
	setCurrentTestCaseInAllDrivers("POS-131033");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type is H for Activation type code", "H", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("Card Type is M for Master type code", "M", data.getDetail(2).getCardType());
	sftpClientDriver.assertEquals("CardSubType is 0007", "0007", data.getDetail(2).getCardSubtype());
    }

    @Test
    public void tc10_MX() {
	setCurrentTestCaseInAllDrivers("POS-131034");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardSubType is 0004 for Amexco SubTypeCode", "0004", data.getDetail(0).getCardSubtype());
	sftpClientDriver.assertEquals("CardSubType length is 4", 4, data.getDetail(0).getCardSubtype().length());
    }

    @Test
    public void tc11_MX() {
	setCurrentTestCaseInAllDrivers("POS-131035");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardSubType is 0009 for Walmart SubTypeCode", "0009", data.getDetail(0).getCardSubtype());
	sftpClientDriver.assertEquals("CardSubType length is 4", 4, data.getDetail(0).getCardSubtype().length());
    }

    @Test
    public void tc12_MX() {
	setCurrentTestCaseInAllDrivers("POS-131036");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardType is D for Discover Card type code", "D", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardSubType is 0002 for Discover Card type", "0002", data.getDetail(0).getCardSubtype());
    }

    @Test
    public void tc13_MX() {
	setCurrentTestCaseInAllDrivers("POS-131038");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardType is D for Discover Card type code", "D", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardSubType is 0012 for Discover Card type", "0012", data.getDetail(0).getCardSubtype());
    }

    @Test
    public void tc14_MX() {
	setCurrentTestCaseInAllDrivers("POS-131039");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardType is D for WMBenefitCard Card type code", "S", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardSubType is 0011 for WMBenefitCard Card type", "0011", data.getDetail(0).getCardSubtype());
    }

    @Test
    public void tc15_MX() {
	setCurrentTestCaseInAllDrivers("POS-131040");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardType is O for Prosa Card type code", "O", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardSubType is 0015 for Prosa Card type", "0015", data.getDetail(0).getCardSubtype());
    }

    @Test
    public void tc16_MX() {
	setCurrentTestCaseInAllDrivers("POS-131041");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardType is K for Payroll Card type code", "K", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardSubType is 0014 for Payroll Card type", "0014", data.getDetail(0).getCardSubtype());
    }

    @Test
    public void tc17_MX() {
	setCurrentTestCaseInAllDrivers("POS-131042");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardType is G for Payroll Card type code", "G", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardSubType is 0014 for Payroll Card type", "0014", data.getDetail(0).getCardSubtype());
    }

    @Test
    public void tc18_MX() {
	setCurrentTestCaseInAllDrivers("POS-131043");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardType is 7 for Amex Card type code", "7", data.getDetail(0).getCardType());
    }

    @Test
    public void tc19_MX() {
	setCurrentTestCaseInAllDrivers("POS-131044");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("CardType is 7 for Diner Card type code", "7", data.getDetail(0).getCardType());
    }
}
