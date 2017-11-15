/**
 * 
 */
package com.automation.gi.tests.bankcards;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.common.logger.CerberusLogger;
import com.automation.common.utils.CommonUtils;
import com.automation.middleware.CerberusMiddlewareTestListener;
import com.walmart.pos.wits.format.bankcard.BankcardData;

/**
 * @author ddv000b
 *
 */
@Listeners(CerberusMiddlewareTestListener.class)
public class BankcardsTest extends BankcardsTestCore {

    @Test(groups = { "RT" })
    public void tc01() {
	setCurrentTestCaseInAllDrivers("POS-129855");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Detail countequals 1", data.getDetailsCount() == 1);
	sftpClientDriver.assertEquals("TRANS_CODE:", "1", data.getDetail(0).getTransCode());
	sftpClientDriver.assertEquals("Store Nbr:", "" + getStoreNumber().substring(3), data.getDetail(0).getStoreNumber());
	sftpClientDriver.assertEquals("Store Nbr:", 6, data.getDetail(0).getStoreNumber().length());
	sftpClientDriver.assertEquals("Exception code:", " ", data.getDetail(0).getExceptionCode());
	sftpClientDriver.assertEquals("Amount:", "0000106664", data.getDetail(0).getTransAmount());
	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BANKCARDS_ID));
    }

    @Test(groups = { "RT" })
    public void tc02() {
	setCurrentTestCaseInAllDrivers("POS-129860");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");

	changeFileOrDirectoryPermissions(getWORK_BANKCARD_DIRECTORY(), "000");

	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileNotExists(getRemoteBankcardFilePath(), true);
	changeFileOrDirectoryPermissions(getWORK_BANKCARD_DIRECTORY(), "777");

	sftpClientDriver.assertEquals("Database message status code: ", STATUS_FIVE_THOUSAND, getRegMsgInterfaceMsgStatusCode(BANKCARDS_ID));
    }

    @Test(groups = { "RT" })
    public void tc03() {
	setCurrentTestCaseInAllDrivers("POS-129891");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	List<File> sendFiles = getCurrentXmlRequests();
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse(sendFiles.get(0)));
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	long firstSize = new File(getLocalBankcardFilePath()).length();
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse(sendFiles.get(1)));
	CommonUtils.sleepInSeconds(30);
	getEtlBankcardFile();

	long secondSize = new File(getLocalBankcardFilePath()).length();

	sftpClientDriver.assertTrue("Bankcard file appened.", firstSize < secondSize);
	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BANKCARDS_ID));
    }

    @Test(groups = { "RT" })
    public void tc04() {
	setCurrentTestCaseInAllDrivers("POS-129892");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	changeFileOrDirectoryPermissions(getWORK_BANKCARD_DIRECTORY(), "000");

	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileNotExists(getRemoteBankcardFilePath(), true);
	changeFileOrDirectoryPermissions(getWORK_BANKCARD_DIRECTORY(), "777");
    }

    @Test(groups = { "RT" }, enabled=false)
    public void tc05() {
	setCurrentTestCaseInAllDrivers("POS-129893");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	updateBusinessDateInDB();

	changeFileOrDirectoryPermissions(getWORK_BANKCARD_DIRECTORY(), "000");

	removeEtlFile(LOG_FILE_PATH);
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileNotExists(getRemoteBankcardFilePath(), true);

	changeFileOrDirectoryPermissions(getWORK_BANKCARD_DIRECTORY(), "777");
	sftpClientDriver.assertTrue("Alert (s) raised.", getListOfEvents().size() > 0);
    }

    @Test(groups = { "RT" })
    public void tc06() {
	setCurrentTestCaseInAllDrivers("POS-129894");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	copyLocalFileInEtl(getWORK_BANKCARD_DIRECTORY(), getCurrentTestCaseDataDirectoryPath() + "\\Bankcard_{BD}.tmp", getCurrentBankcardFileName());
	updateBusinessDateInDB();
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getWORK_END_OF_DAY_DIRECTORY(), "BC00\\d{4}_\\d{8}.ISP");
	getEtlIspBankcardFile();

	String date = getTimeStamp().split("T")[0].replace("-", "");
	String time = getTimeStamp().split("T")[1].replace(":", "");

	BankcardData data = getIspBankcardData();
	sftpClientDriver.assertEquals("Transcode value:", "0", data.getHeader().getTransCode());
	sftpClientDriver.assertEquals("Transcode length:", 1, data.getHeader().getTransCode().length());
	sftpClientDriver.assertEquals("First filler:", "0000000000000000000000000000000000000", data.getHeader().getFirstFiller());
	sftpClientDriver.assertEquals("Filler length:", 37, data.getHeader().getFirstFiller().length());
	sftpClientDriver.assertEquals("Date in header", getBusinessDate().replace("-", ""), data.getHeader().getDateOfRecord());
	sftpClientDriver.assertEquals("Date length in header", 8, data.getHeader().getDateOfRecord().length());
	sftpClientDriver.assertEquals("Time   in header", time, data.getHeader().getTimeOfRecord());
	sftpClientDriver.assertEquals("Time length in header", 6, data.getHeader().getTimeOfRecord().length());
	sftpClientDriver.assertEquals("Date in trailer", date, data.getTrailer().getDateOfRecord());
	sftpClientDriver.assertEquals("Date length in trailer", 8, data.getTrailer().getDateOfRecord().length());
	sftpClientDriver.assertEquals("Time in trailer", time, data.getTrailer().getTimeOfRecord());
	sftpClientDriver.assertEquals("Time length in trailer", 6, data.getTrailer().getTimeOfRecord().length());
	sftpClientDriver.assertEquals("Database message status code: ", STATUS_THREE_HUNDRED, getEodStoreInterfaceMsgStatusCode(BANKCARDS_EOD_ID));
    }

    @Test(groups = { "RT" })
    public void tc07() {
	setCurrentTestCaseInAllDrivers("POS-129895");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	copyLocalFileInEtl(getWORK_BANKCARD_DIRECTORY(), getCurrentTestCaseDataDirectoryPath() + "\\Bankcard_{BD}.tmp", getCurrentBankcardFileName());
	updateBusinessDateInDB();
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getWORK_END_OF_DAY_DIRECTORY(), "BC00\\d{4}_\\d{8}.ISP");
	getEtlIspBankcardFile();

	BankcardData data = getIspBankcardData();
	String filename = StringUtils.substring(getLocalIspBankcardFilePath(), getLocalBankcardFilePath().lastIndexOf('\\') + 1);
	sftpClientDriver.assertEquals("sequence number in header", "000" + StringUtils.substring(filename, 2, 8), data.getHeader().getSequenceNbr());
	sftpClientDriver.assertEquals("Sequence number in header", 9, data.getHeader().getSequenceNbr().length());
	sftpClientDriver.assertEquals("Second filler in header", "000000", data.getHeader().getSecondFiller());
	sftpClientDriver.assertEquals("Second filler length in header", 6, data.getHeader().getSecondFiller().length());
	sftpClientDriver.assertEquals("EPFID in header", "0000000000", data.getHeader().getEpfId());
	sftpClientDriver.assertEquals("EPFID number length in header", 10, data.getHeader().getEpfId().length());
	sftpClientDriver.assertEquals("3rd filler in header", "0000000000000000000000000", data.getHeader().getThirdFiller());
	sftpClientDriver.assertEquals("3rd filler length in header", 25, data.getHeader().getThirdFiller().length());
	sftpClientDriver.assertEquals("store number in header", StringUtils.substring(getStoreNumber(), 3), data.getHeader().getStore());
	sftpClientDriver.assertEquals("Store number length in header", 6, data.getHeader().getStore().length());
	sftpClientDriver.assertEquals("Total Amt in TRAILER Record to total Amount in DETAIL", "0010666400", data.getTrailer().getTotalAmount());

    }

    @Test(groups = { "RT" })
    public void tc08() {
	setCurrentTestCaseInAllDrivers("POS-129896");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	copyLocalFileInEtl(getWORK_BANKCARD_DIRECTORY(), getCurrentTestCaseDataDirectoryPath() + "\\Bankcard_{BD}.tmp", getCurrentBankcardFileName());
	updateBusinessDateInDB();
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getWORK_END_OF_DAY_DIRECTORY(), "BC00\\d{4}_\\d{8}.ISP");
	getEtlIspBankcardFile();

	BankcardData data = getIspBankcardData();
	sftpClientDriver.assertEquals("Version in header", "01", data.getHeader().getVersion());
	sftpClientDriver.assertEquals("Version in header", 2, data.getHeader().getVersion().length());
	sftpClientDriver.assertEquals("4th filler in header", "000000000000000", data.getHeader().getFourthFiller());
	sftpClientDriver.assertEquals("4th filler length in header", 15, data.getHeader().getFourthFiller().length());
	sftpClientDriver.assertEquals("5th filler in header", "000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000", data.getHeader().getFifthFiller());
	sftpClientDriver.assertEquals("5th filler length in header", 129, data.getHeader().getFifthFiller().length());
	sftpClientDriver.assertEquals("Nbr of Records are in header", "00000" + data.getDetailsCount().toString(), data.getTrailer().getNbrOfRecords());
    }

    @Test(groups = { "RT" })
    public void tc09() {
	setCurrentTestCaseInAllDrivers("POS-129897");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	copyLocalFileInEtl(getWORK_BANKCARD_DIRECTORY(), getCurrentTestCaseDataDirectoryPath() + "\\Bankcard_{BD}.tmp", getCurrentBankcardFileName());
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	CommonUtils.sleepInSeconds(30);
	getEtlBankcardFile();
	sftpClientDriver.assertTrue("Refund not added.", getBankcardData().getDetailsCount() == 1);
    }

    @Test(groups = { "RT" })
    public void tc10() {
	setCurrentTestCaseInAllDrivers("POS-129898");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("TRANS_CODE", "2", data.getDetail(0).getTransCode());
	sftpClientDriver.assertEquals("EALF_SEQ_NUMBER", "000000", data.getDetail(0).getEpfSeqNumber());
	sftpClientDriver.assertEquals("EALF_SEQ_NUMBER length", 6, data.getDetail(0).getEpfSeqNumber().length());
	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BANKCARDS_ID));
    }

    @Test(groups = { "RT" })
    public void tc11() {
	setCurrentTestCaseInAllDrivers("POS-129899");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type", "N", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("Authorization Time", "201104", data.getDetail(0).getTimeOfSale());
	sftpClientDriver.assertEquals("Authorization Time length", 6, data.getDetail(0).getTimeOfSale().length());
	sftpClientDriver.assertEquals("Swipe Indicator for CreditDebit Tender ", "1", data.getDetail(0).getSwipeIndicator());
	sftpClientDriver.assertEquals("Network ID", "0000", data.getDetail(0).getNetworkId());
	sftpClientDriver.assertEquals("Network ID length", 4, data.getDetail(0).getNetworkId().length());
	sftpClientDriver.assertEquals("Gateway Ref", "000000000000", data.getDetail(0).getGatewayRef());
	sftpClientDriver.assertEquals("GateWal Ref", 12, data.getDetail(0).getGatewayRef().length());
    }

    @Test(groups = { "RT" })
    public void tc12() {
	setCurrentTestCaseInAllDrivers("POS-129900");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type", "7", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("Auth Flag", "0000", data.getDetail(0).getAuthFlags());
	sftpClientDriver.assertEquals("Auth Floag legth", 4, data.getDetail(0).getAuthFlags().length());
	sftpClientDriver.assertEquals("", "0000", data.getDetail(0).getEpsNumber());
	sftpClientDriver.assertEquals("EPS_NUMBER length", 4, data.getDetail(0).getEpsNumber().length());
	sftpClientDriver.assertEquals("Card seq nbr", "00", data.getDetail(0).getCardSeq());
	sftpClientDriver.assertEquals("Card Seq Nbr length", 2, data.getDetail(0).getCardSeq().length());
    }

    @Test(groups = { "RT" })
    public void tc13() {
	setCurrentTestCaseInAllDrivers("POS-129901");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	String date = currentEndDateTime.split("T")[0].replace("-", "");
	String time = currentEndDateTime.split("T")[1].replace(":", "");
	sftpClientDriver.assertEquals("Card Type", "7", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("Date odf Sale", date, data.getDetail(0).getDateOfSale());
	sftpClientDriver.assertEquals("Date of Sale length", 8, data.getDetail(0).getDateOfSale().length());
//	sftpClientDriver.assertEquals("Time of Sale", time, data.getDetail(0).getTimeOfSale());
	sftpClientDriver.assertEquals("Time of Sale length", 6, data.getDetail(0).getTimeOfSale().length());
    }

    @Test(groups = { "RT" })
    public void tc14() {
	setCurrentTestCaseInAllDrivers("POS-129902");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	String date = currentEndDateTime.split("T")[0].replace("-", "");
	String time = currentEndDateTime.split("T")[1].replace(":", "");
	sftpClientDriver.assertEquals("Card Type", "L", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("Date of Sale", date, data.getDetail(0).getDateOfSale());
	sftpClientDriver.assertEquals("Date of Sale", 8, data.getDetail(0).getDateOfSale().length());
	sftpClientDriver.assertEquals("Time of Sale", time, data.getDetail(0).getTimeOfSale());
	sftpClientDriver.assertEquals("Time of Sale", 6, data.getDetail(0).getTimeOfSale().length());
    }

    @Test(groups = { "RT" })
    public void tc15() {
	setCurrentTestCaseInAllDrivers("POS-129904");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type", "Y", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("Prior Balance", "00000000", data.getDetail(0).getPriorBalDue());
	sftpClientDriver.assertEquals("Prior balance due length", 8, data.getDetail(0).getPriorBalDue().length());
	sftpClientDriver.assertEquals("Expire Date", "0000", data.getDetail(0).getExpireDate());
	sftpClientDriver.assertEquals("Expire Date length", 4, data.getDetail(0).getExpireDate().length());
	sftpClientDriver.assertEquals("EPS_AC_QUALIFIER", "00", data.getDetail(0).getEpsAcQualifier());
	sftpClientDriver.assertEquals("EPS_AC_QUALIFIER length", 2, data.getDetail(0).getEpsAcQualifier().length());
	sftpClientDriver.assertEquals("ACTION CODE length", "00", data.getDetail(0).getActionCode());
    }

    @Test(groups = { "RT" })
    public void tc16() {
	setCurrentTestCaseInAllDrivers("POS-129905");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Card Type", "Y", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("SCHEMA_CODE", "0", data.getDetail(0).getSchemeCode());
	sftpClientDriver.assertEquals("SCHEMA_CODE", 1, data.getDetail(0).getSchemeCode().length());
	sftpClientDriver.assertEquals("TRANS_SOURCE", "1", data.getDetail(0).getTransSource());
	sftpClientDriver.assertEquals("TRANS_SOURCE", 1, data.getDetail(0).getTransSource().length());
	sftpClientDriver.assertEquals("PLAN_ID", " ", data.getDetail(0).getPlanId());
	sftpClientDriver.assertEquals("PLAN_ID", 1, data.getDetail(0).getPlanId().length());
	sftpClientDriver.assertEquals("CLOSE_ID", " ", data.getDetail(0).getCloseId());
	sftpClientDriver.assertEquals("CLOSE_ID length", 1, data.getDetail(0).getCloseId().length());
    }

    @Test(groups = { "RT" }, enabled=false)
    public void tc17() {
	setCurrentTestCaseInAllDrivers("POS-129906");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Detail Records is 2", data.getDetailsCount() == 2);
	sftpClientDriver.assertEquals("CardType", "H", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardType'", "Y", data.getDetail(1).getCardType());
	sftpClientDriver.assertEquals("ORIGIN TICKET NBR", "0000", data.getDetail(1).getOriginalTicketNumber());
	sftpClientDriver.assertEquals("ORIGIN TICKET NBR length", 4, data.getDetail(1).getOriginalTicketNumber().length());
	sftpClientDriver.assertEquals("ORIGIN TICKET DATE", "000000", data.getDetail(1).getOriginalTicketDate());
	sftpClientDriver.assertEquals("ORIGIN TICKET DATE length", 6, data.getDetail(1).getOriginalTicketDate().length());
	sftpClientDriver.assertEquals("AUTH REQUEST CODE ", "000000", data.getDetail(1).getAuthRequestCode());
	sftpClientDriver.assertEquals("AUTH REQUEST CODE", 6, data.getDetail(1).getAuthRequestCode().length());
	sftpClientDriver.assertEquals("AUTH RESPONSE CODE", "00", data.getDetail(1).getAuthResponseCode());
	sftpClientDriver.assertEquals("AUTH RESPONSE CODE", 2, data.getDetail(1).getAuthResponseCode().length());
    }

    @Test(groups = { "RT" })
    public void tc18() {
	setCurrentTestCaseInAllDrivers("POS-129907");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Detail Records count is 2", data.getDetailsCount() == 2);
	sftpClientDriver.assertEquals("CardType", "L", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardType", "Y", data.getDetail(1).getCardType());
	sftpClientDriver.assertEquals("Card Sub Type", "0000", data.getDetail(0).getCardSubtype());
	sftpClientDriver.assertEquals("Card Sub Type length", 4, data.getDetail(1).getCardSubtype().length());
	sftpClientDriver.assertEquals("TC Number", "000000000000000000000000", data.getDetail(0).getTcNumber());
	sftpClientDriver.assertEquals("TC Number", 24, data.getDetail(1).getTcNumber().length());
	sftpClientDriver.assertEquals("Swipe Indicator", "4", data.getDetail(0).getSwipeIndicator());
    }

    @Test(groups = { "RT" })
    public void tc19() {
	setCurrentTestCaseInAllDrivers("POS-129908");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Detail Records count is 3", data.getDetailsCount() == 3);
	sftpClientDriver.assertEquals("CardType", "H", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardType", "L", data.getDetail(1).getCardType());
	sftpClientDriver.assertEquals("CardType", "Y", data.getDetail(2).getCardType());
	sftpClientDriver.assertEquals("Swipe Indicator", "4", data.getDetail(0).getSwipeIndicator());
    }

    @Test(groups = { "RT" })
    public void tc20() {
	setCurrentTestCaseInAllDrivers("POS-129909");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Detail Record count is 1", data.getDetailsCount() == 1);
	sftpClientDriver.assertEquals("CardType", "N", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("BANK PROMOTION MARK", "000000", data.getDetail(0).getBankPromoMark());
	sftpClientDriver.assertEquals("BANK PROMOTION MARK", 6, data.getDetail(0).getBankPromoMark().length());
	sftpClientDriver.assertEquals("FIRST FILLER", "0000000000000000000000000", data.getDetail(0).getFiller());
	sftpClientDriver.assertEquals("FIRST FILLER length", 25, data.getDetail(0).getFiller().length());
    }

    @Test(groups = { "RT" })
    public void tc21() {
	setCurrentTestCaseInAllDrivers("POS-129910");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Detail Records count is 1", data.getDetailsCount() == 1);
	sftpClientDriver.assertEquals("CardType", "N", data.getDetail(0).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc22() {
	setCurrentTestCaseInAllDrivers("POS-129911");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Detail Records count is 2", data.getDetailsCount() == 2);
	sftpClientDriver.assertEquals("CardType", "H", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardType", "N", data.getDetail(1).getCardType());
	sftpClientDriver.assertEquals("Transaction Amt", "0000000000", data.getDetail(0).getTransAmount());
	sftpClientDriver.assertEquals("Transaction Amt length", 10, data.getDetail(0).getTransAmount().length());
    }

    @Test(groups = { "RT" })
    public void tc23() {
	setCurrentTestCaseInAllDrivers("POS-129912");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Detail Records count", 2, data.getDetailsCount());
	sftpClientDriver.assertEquals("CardType", "L", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardType", "N", data.getDetail(1).getCardType());
	sftpClientDriver.assertEquals("Swipe Indicator", "1", data.getDetail(0).getSwipeIndicator());
    }

    @Test(groups = { "RT" })
    public void tc24() {
	setCurrentTestCaseInAllDrivers("POS-129913");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Detail Records count is 3", data.getDetailsCount() == 3);
	sftpClientDriver.assertEquals("CardType is 'H'", "H", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardType is 'L'", "L", data.getDetail(1).getCardType());
	sftpClientDriver.assertEquals("CardType is 'N'", "N", data.getDetail(2).getCardType());
	sftpClientDriver.assertEquals("Swipe Indicator is 1 for SwipAtPiped Entry method", "1", data.getDetail(0).getSwipeIndicator());
	sftpClientDriver.assertEquals("Swipe Indicator is E for Intregrated Chip Entry method", "E", data.getDetail(1).getSwipeIndicator());
    }

    @Test(groups = { "RT" }, enabled=false)
    public void tc25() {
	setCurrentTestCaseInAllDrivers("POS-129914");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Detail Records count is 2", data.getDetailsCount() == 2);
	sftpClientDriver.assertEquals("CardType is 'H'", "H", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardType is 'L'", "L", data.getDetail(1).getCardType());
	sftpClientDriver.assertEquals("Swipe Indicator is 9 for E-Wallet Entry method", "9", data.getDetail(0).getSwipeIndicator());
    }

    @Test(groups = { "RT" })
    public void tc26() {
	setCurrentTestCaseInAllDrivers("POS-129915");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Detail Records count is 2", data.getDetailsCount() == 2);
	sftpClientDriver.assertEquals("CardType is 'H'", "H", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("CardType is 'L'", "L", data.getDetail(1).getCardType());
    }
//
//    @Test(groups = { "RT" })
//    public void tc27() {
//	setCurrentTestCaseInAllDrivers("POS-129916");
//	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
//	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
//	verifyEtlFileExists(getRemoteBankcardFilePath());
//	getEtlBankcardFile();
//
//	BankcardData data = getBankcardData();
//	sftpClientDriver.assertTrue("Detail Records count is 1", data.getDetailsCount() == 1);
//	sftpClientDriver.assertEquals("CardType is 'Q'", "7", data.getDetail(0).getCardType());
//	sftpClientDriver.assertEquals("Transaction Nbr", "0000" + getSequenceNumber(), data.getDetail(0).getTransactionNumber());
//    }
//
//    @Test(groups = { "RT" })
//    public void tc28() {
//	setCurrentTestCaseInAllDrivers("POS-129917");
//	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
//	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
//	verifyEtlFileExists(getRemoteBankcardFilePath());
//	getEtlBankcardFile();
//
//	BankcardData data = getBankcardData();
//	sftpClientDriver.assertTrue("Detail Records count is 1", data.getDetailsCount() == 1);
//	sftpClientDriver.assertEquals("CardType is 'Q'", "Q", data.getDetail(0).getCardType());
//    }
//
//    @Test(groups = { "RT" })
//    public void tc29() {
//	setCurrentTestCaseInAllDrivers("POS-129918");
//	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
//	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
//	verifyEtlFileExists(getRemoteBankcardFilePath());
//	getEtlBankcardFile();
//
//	BankcardData data = getBankcardData();
//	sftpClientDriver.assertTrue("Detail Records count is 1", data.getDetailsCount() == 1);
//	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BANKCARDS_ID));
//    }
//
//    @Test(groups = { "RT" })
//    public void tc30() {
//	setCurrentTestCaseInAllDrivers("POS-129919");
//	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
//	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
//	verifyEtlFileExists(getRemoteBankcardFilePath());
//	getEtlBankcardFile();
//
//	sftpClientDriver.assertTrue("Detail count is 1", getBankcardData().getDetailsCount() == 1);
//    }
//
//    @Test(groups = { "RT" })
//    public void tc31() {
//	setCurrentTestCaseInAllDrivers("POS-129920");
//	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
//	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
//	verifyEtlFileNotExists(getRemoteBankcardFilePath(), true);
//    }
//
//    @Test(groups = { "RT" })
//    public void tc32() {
//	setCurrentTestCaseInAllDrivers("POS-129921");
//	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
//	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
//	verifyEtlFileExists(getRemoteBankcardFilePath());
//	getEtlBankcardFile();
//
//	BankcardData data = getBankcardData();
//	String date = currentEndDateTime.split("T")[0].replace("-", "");
//	String time = currentEndDateTime.split("T")[1].replace(":", "");
//	sftpClientDriver.assertTrue("Detail count is 1", data.getDetailsCount() == 1);
//	sftpClientDriver.assertEquals("Card Type is 'Q'", "Q", data.getDetail(0).getCardType());
//	sftpClientDriver.assertEquals("Card Type Length is 1", 1, data.getDetail(0).getCardType().length());
//	sftpClientDriver.assertEquals("Date of Sale", date, data.getDetail(0).getDateOfSale());
//	sftpClientDriver.assertEquals("Date of Sale length is 8", 8, data.getDetail(0).getDateOfSale().length());
//	sftpClientDriver.assertEquals("Time of Sale", time, data.getDetail(0).getTimeOfSale());
//	sftpClientDriver.assertEquals("Time of Sale length", 6, data.getDetail(0).getTimeOfSale().length());
//    }

    @Test(groups = { "RT" })
    public void tc33() {
	setCurrentTestCaseInAllDrivers("POS-129922");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Detail count is 1", data.getDetailsCount() == 1);
	sftpClientDriver.assertEquals("Card Type is 'S'", "S", data.getDetail(0).getCardType());
	sftpClientDriver.assertEquals("Incorrect length of card type", 1, data.getDetail(0).getCardType().length());
	sftpClientDriver.assertEquals("Terminal Nbr", "0011", data.getDetail(0).getTerminalNumber());
	sftpClientDriver.assertEquals("Terminal Nbr length is 4", 4, data.getDetail(0).getTerminalNumber().length());
	sftpClientDriver.assertEquals("Operator Nbr", "00001111", data.getDetail(0).getOperatorNumber());
	sftpClientDriver.assertEquals("Operator Nbr length is 8", 8, data.getDetail(0).getOperatorNumber().length());
    }

    @Test(groups = { "RT" })
    public void tc34() {
	setCurrentTestCaseInAllDrivers("POS-129923");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", 403, getStatusResponse());
	verifyEtlFileNotExists(getRemoteBankcardFilePath(), true);
    }

    @Test(groups = { "RT" })
    public void tc35() {
	setCurrentTestCaseInAllDrivers("POS-129924");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	String date = getTimeStamp().split("T")[0].replace("-", "");
	BankcardData data = getBankcardData();
	sftpClientDriver.assertTrue("Nbr of Detail Records is 2", data.getDetailsCount() == 2);
	sftpClientDriver.assertEquals("Card Type is S", "S", data.getDetail(1).getCardType());
	sftpClientDriver.assertEquals("Date of Sale", date, data.getDetail(0).getDateOfSale());
    }

    @Test(groups = { "RT" })
    public void tc36() {
	setCurrentTestCaseInAllDrivers("POS-129925");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	String date = getTimeStamp().split("T")[0].replace("-", "");
	sftpClientDriver.assertTrue("Nbr of Detail Records is 1", data.getDetailsCount() == 1);
	sftpClientDriver.assertEquals("Date of Sale", date, data.getDetail(0).getDateOfSale());
	sftpClientDriver.assertEquals("HEADER record", null, data.getHeader());
	sftpClientDriver.assertEquals("Trailer record", null, data.getTrailer());
    }

    @Test(groups = { "RT" })
    public void tc37() {
	setCurrentTestCaseInAllDrivers("POS-129926");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Swipe Indicator", "6", data.getDetail(0).getSwipeIndicator());
	sftpClientDriver.assertEquals("NumberInstall ", "00", data.getDetail(0).getNumberInstall());
	sftpClientDriver.assertEquals("NumberInstall  length ", 2, data.getDetail(0).getNumberInstall().length());
	sftpClientDriver.assertEquals("INST_TYPE ", "00", data.getDetail(0).getInstType());
	sftpClientDriver.assertEquals("INST_TYPE  length ", 2, data.getDetail(0).getInstType().length());
	sftpClientDriver.assertEquals("ROUTING NBR ", "000000000", data.getDetail(0).getRoutingNumber());
	sftpClientDriver.assertEquals("ROUTING NBR  length ", 9, data.getDetail(0).getRoutingNumber().length());
    }

    @Test(groups = { "RT" })
    public void tc38() {
	setCurrentTestCaseInAllDrivers("POS-129927");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileNotExists(getRemoteBankcardFilePath(), true);
    }

    @Test(groups = { "RT" })
    public void tc39() {
	setCurrentTestCaseInAllDrivers("POS-129928");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	copyLocalFileInEtl(getWORK_BANKCARD_DIRECTORY(), getCurrentTestCaseDataDirectoryPath() + "\\Bankcard_{BD}.tmp", getCurrentBankcardFileName());
	updateBusinessDateInDB();
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getWORK_END_OF_DAY_DIRECTORY(), "BC00\\d{4}_\\d{8}.ISP");
	getEtlIspBankcardFile();
	BankcardData data = getIspBankcardData();
	sftpClientDriver.assertEquals("1st filler in Trailer", "9999999999999999999999999999999999999", data.getTrailer().getFirstFiller());
	sftpClientDriver.assertEquals("1st Filler length in Trailer", 37, data.getTrailer().getFirstFiller().length());
	sftpClientDriver.assertEquals("2nd filler in Trailer", "9", data.getTrailer().getSecondFiller());
	sftpClientDriver.assertEquals("2nd length in Trailer", 1, data.getTrailer().getSecondFiller().length());
	sftpClientDriver.assertEquals("3rd filler in Trailer", "9999999999999999", data.getTrailer().getThirdFiller());
	sftpClientDriver.assertEquals("3rd length in Trailer", 16, data.getTrailer().getThirdFiller().length());
	sftpClientDriver.assertEquals("4th filler ", "99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999",
		data.getTrailer().getFourthFiller());
	sftpClientDriver.assertEquals("length of 4th filler 146", 146, data.getTrailer().getFourthFiller().length());
	sftpClientDriver.assertEquals("Database message status code: ", STATUS_THREE_HUNDRED, getEodStoreInterfaceMsgStatusCode(BANKCARDS_EOD_ID));
    }

    @Test(groups = { "RT" })
    public void tc40() {
	setCurrentTestCaseInAllDrivers("POS-129929");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	copyLocalFileInEtl(getWORK_BANKCARD_DIRECTORY(), getCurrentTestCaseDataDirectoryPath() + "\\Bankcard_{BD}.tmp", getCurrentBankcardFileName());
	updateBusinessDateInDB();
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getWORK_END_OF_DAY_DIRECTORY(), "BC00\\d{4}_\\d{8}.ISP");
	getEtlIspBankcardFile();
	BankcardData data = getIspBankcardData();
	sftpClientDriver.assertTrue("Detail record count", data.getDetailsCount() == 8);
        sftpClientDriver.assertEquals("Sum of Total amount", getTotalTransAmount(data.getDetails()), Long.valueOf(data.getTrailer().getTotalAmount()));
        sftpClientDriver.assertEquals("Sum of Total cash back amount",getTotalCashBackAmount(data.getDetails()), Long.valueOf(data.getTrailer().getTotCashBackAmt()));
        sftpClientDriver.assertEquals("Database message status code: ", STATUS_THREE_HUNDRED, getEodStoreInterfaceMsgStatusCode(BANKCARDS_EOD_ID));
    }

    @Test(groups = { "RT" })
    public void tc41() {
	setCurrentTestCaseInAllDrivers("POS-129930");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	copyLocalFileInEtl(getWORK_BANKCARD_DIRECTORY(), getCurrentTestCaseDataDirectoryPath() + "\\Bankcard_{BD}.tmp", getCurrentBankcardFileName());
	updateBusinessDateInDB();
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getWORK_END_OF_DAY_DIRECTORY(), "BC00\\d{4}_\\d{8}.ISP");
	getEtlIspBankcardFile();
	BankcardData data = getIspBankcardData();
	sftpClientDriver.assertTrue("Detail record count", data.getDetailsCount() == 8);
        sftpClientDriver.assertEquals("Sum of Total amount", getTotalTransAmount(data.getDetails()), Long.valueOf(data.getTrailer().getTotalAmount()));
        sftpClientDriver.assertEquals("Sum of Total cash back amount",getTotalCashBackAmount(data.getDetails()), Long.valueOf(data.getTrailer().getTotCashBackAmt()));
    }

    @Test(groups = { "RT" })
    public void tc42() {
	setCurrentTestCaseInAllDrivers("POS-129931");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	copyLocalFileInEtl(getWORK_BANKCARD_DIRECTORY(), getCurrentTestCaseDataDirectoryPath() + "\\Bankcard_{BD}.tmp", getCurrentBankcardFileName());
	updateBusinessDateInDB();
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getWORK_END_OF_DAY_DIRECTORY(), "BC00\\d{4}_\\d{8}.ISP");
	getEtlIspBankcardFile();
	BankcardData data = getIspBankcardData();
	sftpClientDriver.assertTrue("Detail record count", data.getDetailsCount() == 8);
        sftpClientDriver.assertEquals("Sum of Total amount", getTotalTransAmount(data.getDetails()), Long.valueOf(data.getTrailer().getTotalAmount()));
        sftpClientDriver.assertEquals("Sum of Total cash back amount",getTotalCashBackAmount(data.getDetails()), Long.valueOf(data.getTrailer().getTotCashBackAmt()));
    }

    @Test(groups = { "RT" })
    public void tc43() {
	setCurrentTestCaseInAllDrivers("POS-129932");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	copyLocalFileInEtl(getWORK_BANKCARD_DIRECTORY(), getCurrentTestCaseDataDirectoryPath() + "\\Bankcard_{BD}.tmp", getCurrentBankcardFileName());
	updateBusinessDateInDB();
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getWORK_END_OF_DAY_DIRECTORY(), "BC00\\d{4}_\\d{8}.ISP");
	getEtlIspBankcardFile();
	BankcardData data = getIspBankcardData();
	sftpClientDriver.assertTrue("Detail record count", data.getDetailsCount() == 8);
        sftpClientDriver.assertEquals("Sum of Total amount", getTotalTransAmount(data.getDetails()), Long.valueOf(data.getTrailer().getTotalAmount()));
        sftpClientDriver.assertEquals("Sum of Total cash back amount",getTotalCashBackAmount(data.getDetails()), Long.valueOf(data.getTrailer().getTotCashBackAmt()));
    }

    @Test(groups = { "RT" })
    public void tc44() {
	setCurrentTestCaseInAllDrivers("POS-129933");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	updateBusinessDateInDB();
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getWORK_END_OF_DAY_DIRECTORY(), "BC00\\d{4}_\\d{8}.ISP");
	getEtlIspBankcardFile();
	BankcardData data = getIspBankcardData();
	sftpClientDriver.assertEquals("Detail record count", 0, data.getDetails().size());
        sftpClientDriver.assertTrue("Trailer record exist",data.getTrailer() != null);
        sftpClientDriver.assertTrue("Header record exists",data.getHeader() != null);
        sftpClientDriver.assertEquals("Store Nbr in Trailer",getStoreNumber().substring(3), data.getTrailer().getStore());
        sftpClientDriver.assertEquals("Store Nbr length Trailer", 6,  data.getTrailer().getStore().length());
    }

    @Test(groups = { "RT" })
    public void tc45() {
	setCurrentTestCaseInAllDrivers("POS-129934");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileNotExists(getRemoteBankcardFilePath(), true);
    }

    @Test(groups = { "RT" })
    public void tc46() {
	setCurrentTestCaseInAllDrivers("POS-129935");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileNotExists(getRemoteBankcardFilePath(), true);
    }

    @Test(groups = { "RT" })
    public void tc47() {
	setCurrentTestCaseInAllDrivers("POS-129936");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileNotExists(getRemoteBankcardFilePath(), true);
    }

    @Test(groups = { "RT" })
    public void tc48() {
	setCurrentTestCaseInAllDrivers("POS-129937");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileNotExists(getRemoteBankcardFilePath(), true);
    }

    @Test(groups = { "RT" })
    public void tc49() {
	setCurrentTestCaseInAllDrivers("POS-129938");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();
	BankcardData data = getBankcardData();
	sftpClientDriver.assertEquals("Detail  record count", "2", data.getDetailsCount().toString());
        sftpClientDriver.assertEquals("Card Type", "N", data.getDetail(1).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc50() {
	setCurrentTestCaseInAllDrivers("POS-129939");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	List<File> sendFiles = getCurrentXmlRequests();
	changeFileOrDirectoryPermissions(getWORK_BANKCARD_DIRECTORY(), "000");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse(sendFiles.get(0)));
	verifyEtlFileNotExists(getRemoteBankcardFilePath(), true);
	sftpClientDriver.assertEquals("Database message status code: ", STATUS_FIVE_THOUSAND, getRegMsgInterfaceMsgStatusCode(BANKCARDS_ID));
	changeFileOrDirectoryPermissions(getWORK_BANKCARD_DIRECTORY(), "777");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse(sendFiles.get(1)));
	verifyEtlFileExists(getRemoteBankcardFilePath());
	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BANKCARDS_ID));
    }

    @Test(groups = { "RT" })
    public void tc51() {
	setCurrentTestCaseInAllDrivers("POS-129940");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	List<File> sendFiles = getCurrentXmlRequests();
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse(sendFiles.get(0)));
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();
	cleanRegMsgInterfaceTable();
	long bankcardSize = new File(getLocalBankcardFilePath()).length();
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse(sendFiles.get(0)));
	CommonUtils.sleepInSeconds(30);
	getEtlBankcardFile();
	long bankcardNewSize = new File(getLocalBankcardFilePath()).length();
	sftpClientDriver.assertEquals("Bankcards file size", bankcardSize, bankcardNewSize);
    }

    @Test(groups = { "RT" })
    public void tc52() {
	setCurrentTestCaseInAllDrivers("POS-129941");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();
	BankcardData data = getBankcardData();
	sftpClientDriver.assertNotEquals("Card Type ", "2", data.getDetail(0).getCardType());
        sftpClientDriver.assertEquals("Customer account nbr", "000000000000000000001234", data.getDetail(0).getCustAcct());
    }

    /*
     * HOT FIX TRANS CODE = 2 WHEN GIFT CARD ACTIVATION OR RELOAD VOIDED
     */
    @Test(groups = { "RT" })
    public void tc53() {
	setCurrentTestCaseInAllDrivers("POS-129839");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData bankcardData = getBankcardData();

	sftpClientDriver.assertEquals("Transaction code for void activation:", "2", bankcardData.getDetail(2).getTransCode());
	sftpClientDriver.assertEquals("Transaction Card number:", "000000006146160128274819", bankcardData.getDetail(2).getCustAcct());
	sftpClientDriver.assertEquals("Transaction Card Type:", "H", bankcardData.getDetail(2).getCardType());
    }

    @Test(groups = { "RT" })
    public void tc54() {
	setCurrentTestCaseInAllDrivers("POS-129840");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBankcardFilePath());
	getEtlBankcardFile();

	BankcardData bankcardData = getBankcardData();

	sftpClientDriver.assertEquals("Transaction code for void reload:", "2", bankcardData.getDetail(2).getTransCode());
	sftpClientDriver.assertEquals("Transaction Card number:", "000000006135119873325846", bankcardData.getDetail(2).getCustAcct());
	sftpClientDriver.assertEquals("Transaction Card Type:", "L", bankcardData.getDetail(2).getCardType());
    }
}
