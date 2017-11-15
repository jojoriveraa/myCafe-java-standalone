package com.automation.gi.tests.binlog;

import java.io.File;
import java.util.List;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.common.logger.CerberusLogger;
import com.automation.common.utils.CommonUtils;
import com.automation.gi.tests.AlertModel;
import com.automation.middleware.CerberusMiddlewareTestListener;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogEightyTwoStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogNinetyThreeStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogSevenStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogSixStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogThirtyTwoStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogTwelveStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogTwentyFiveStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogTwentyFourStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogZeroStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.binlogfifteenstrings.BinLogFifteenDtEleven;

@Listeners(CerberusMiddlewareTestListener.class)
public class BinlogTest extends BinlogTestCore {

    @Test(groups = { "RT" })
    public void tc01() {
	setCurrentTestCaseInAllDrivers("POS-118773");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	getEtlBinlogFile();
	verifyEtlFileExists(getRemoteTlogExtractFilePath());

	BinLogZeroStr zeroString = getZeroString(0, 0);
	BinLogSixStr sixString = getSixString(0, 1);

	sftpClientDriver.assertEquals("0th String String Type:", "00", zeroString.getStringType().toString());
	sftpClientDriver.assertEquals("0th String Transaction number: ", "0058", zeroString.getTransactionNumber().toString());
	sftpClientDriver.assertEquals("0th String number of fields:", 23, zeroString.getTotalNumOfFields());

	sftpClientDriver.assertEquals("6th String  String Type:", "06", sixString.getStringType().toString());
	sftpClientDriver.assertEquals("6th String  number of fields:", 39, sixString.getTotalNumOfFields());
	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BINLOG_ID));
    }

    @Test(groups = { "RT" })
    public void tc02() {
	setCurrentTestCaseInAllDrivers("POS-118774");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	List<File> sendFiles = getCurrentXmlRequests();
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse(sendFiles.get(0)));
	verifyEtlFileExists(getRemoteBinlogFilePath());
	getEtlBinlogFile();
	getEtlTlogExtractFile();
	long oldBinlogSize = new File(getLocalBinlogFilePath()).length();
	long oldTlogExtractSize = new File(getLocatTlogExtractFilePath()).length();
	
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse(sendFiles.get(1)));
	CommonUtils.sleepInSeconds(30);
	getEtlBinlogFile();
	getEtlTlogExtractFile();
	long newBinlogSize = new File(getLocalBinlogFilePath()).length();
	long newTlogExtractSize = new File(getLocatTlogExtractFilePath()).length();
	sftpClientDriver.assertTrue("Binlog appened.", oldBinlogSize < newBinlogSize);
	sftpClientDriver.assertTrue("Tlog Extract appened.", oldTlogExtractSize < newTlogExtractSize);
	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BINLOG_ID));
    }

    @Test(groups = { "RT" })
    public void tc03() {
	setCurrentTestCaseInAllDrivers("POS-118775");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	getEtlBinlogFile();
	BinLogSixStr sixStr = getSixString(0, 1);
	BinLogSevenStr sevenStr = getSevenString(0, 2);
	BinLogTwelveStr stringTwelve = getTwelveString(0, 10);
	sftpClientDriver.assertEquals("6th String indicator 1:", "64", sixStr.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("6th String indicator 0:", "8194", sixStr.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("7th String new Price: ", "F293", sevenStr.getUnitPrice().toString());
	sftpClientDriver.assertEquals("7th String previous Price:", "F335", sevenStr.getOrigItemPrice().toString());
	sftpClientDriver.assertEquals("12th string indicator 0:", "F1048576", stringTwelve.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("12th string tender type:", "48", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("12th string tender amount:", "0000106664", stringTwelve.getTenderAmt().toString());
    }

    @Test(groups = { "RT" })
    public void tc04() {
	setCurrentTestCaseInAllDrivers("POS-118776");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	disableInterfaceById(3);
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileNotExists(getRemoteBinlogFilePath(), true);
	verifyEtlFileNotExists(getRemoteTlogExtractFilePath(), false);
	enableInterfaceById(3);
    }

    @Test(groups = { "RT" })
    public void tc05() {
	setCurrentTestCaseInAllDrivers("POS-118777");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	getEtlBinlogFile();
	verifyEtlFileExists(getRemoteTlogExtractFilePath());

	BinLogTwelveStr stringTwelve = getTwelveString(0, 8);
	BinLogSevenStr stringSeven = getSevenString(0, 4);
	BinLogTwentyFiveStr stringtwentyFive = getTwentyFiveString(0, 6);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 7);

	sftpClientDriver.assertEquals("12th string tender type:", "25", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("25th string indicator 1:", "0002", stringtwentyFive.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("25th string account number:", "123620167204087070123035", stringtwentyFive.getAcctNbr().toString());
	sftpClientDriver.assertEquals("25th string authorization code:", "068651", stringtwentyFive.getAuthCode().toString());
	sftpClientDriver.assertEquals("25th string amount:", "F36614", stringtwentyFive.getExtendedAmt().toString());
	sftpClientDriver.assertEquals("7th string quantity:", "2000", stringSeven.getWeightQty().toString());

	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Length of Register in 82nd String:", 1, strEightyTwo.getRegisterNumber().getSize());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Length of Sequence in 82nd String:", 1, strEightyTwo.getSequenceNumber().getSize());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0021", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Length of Operator in 82nd String:", 2, strEightyTwo.getOperatorNumber().getSize());
	sftpClientDriver.assertEquals("Date in 82nd String:", "021612", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Length of Date in 82nd String:", 3, strEightyTwo.getDate().getSize());
	sftpClientDriver.assertEquals("Time in 82nd String:", "201104", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Length of Time in 82nd String:", 3, strEightyTwo.getTime().getSize());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0016", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Length of Authorization Type in 82nd String:", 2, strEightyTwo.getAuthorizationType().getSize());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Length of Approved? in 82nd String:", 1, strEightyTwo.getApprove().getSize());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "068651", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Length of Approval Code in 82nd String:", 6, strEightyTwo.getApprovalCode().length());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Length of Auth. Response Code in 82nd String:", 2, strEightyTwo.getAuthorizationStatusUS().getSize());
	sftpClientDriver.assertEquals("Tender Amount in 82nd String:", "F36614", strEightyTwo.getTenderAmt().toString());
	sftpClientDriver.assertEquals("Length of Tender Amount in 82nd String:", 3, strEightyTwo.getTenderAmt().getSize());
	sftpClientDriver.assertEquals("MICR ABA Number in 82nd String:", "", strEightyTwo.getMicrAbaNumber().toString());
	sftpClientDriver.assertEquals("Transaction Id in 82nd String:", "0058", strEightyTwo.getTransactionId().toString());
	sftpClientDriver.assertEquals("Length of Transaction Id in 82nd String:", 2, strEightyTwo.getTransactionId().getSize());
	sftpClientDriver.assertEquals("Network Id in 82nd String:", "", strEightyTwo.getNetworkId());
	sftpClientDriver.assertEquals("Gateway Ref in 82nd String:", "615700067374", strEightyTwo.getGatewayRefNumber());
	sftpClientDriver.assertEquals("Length of Gateway Ref in 82nd String:", 12, strEightyTwo.getGatewayRefNumber().length());
	sftpClientDriver.assertEquals("Auth Requestor in 82nd String:", "01", strEightyTwo.getTransactionOriginator().toString());
	sftpClientDriver.assertEquals("Length of Auth Requestor in 82nd String:", 1, strEightyTwo.getTransactionOriginator().getSize());
	sftpClientDriver.assertEquals("Swipe Indicator in 82nd String:", "08", strEightyTwo.getSwipeInd().toString());
	sftpClientDriver.assertEquals("Length of Swipe Indicator in 82nd String:", 1, strEightyTwo.getSwipeInd().getSize());
	sftpClientDriver.assertEquals("Account Type in 82nd String:", "0000", strEightyTwo.getAccountType().toString());
	sftpClientDriver.assertEquals("Length of Account Type in 82nd String:", 2, strEightyTwo.getAccountType().getSize());
	sftpClientDriver.assertEquals("Host Response Time in 82nd String:", "000000", strEightyTwo.getHostResponseTime().toString());
	sftpClientDriver.assertEquals("Length of Host Response Time in 82nd String:", 3, strEightyTwo.getHostResponseTime().getSize());
	sftpClientDriver.assertEquals("Transaction Type in 82nd String:", "01", strEightyTwo.getTransactionType().toString());
	sftpClientDriver.assertEquals("Length of Transaction Type in 82nd String:", 1, strEightyTwo.getTransactionType().getSize());
	sftpClientDriver.assertEquals("Event Flags in 82nd String:", "0000", strEightyTwo.getEventFlags().toString());
	sftpClientDriver.assertEquals("Length of Event Flags in 82nd String:", 2, strEightyTwo.getEventFlags().getSize());
	sftpClientDriver.assertEquals("Bin Length in 82nd String:", "0000", strEightyTwo.getBinLength().toString());
	sftpClientDriver.assertEquals("Length of Bin Length in 82nd String:", 2, strEightyTwo.getBinLength().getSize());
	sftpClientDriver.assertEquals("Bin Type in 82nd String:", "F0", strEightyTwo.getBinType().toString());
	sftpClientDriver.assertEquals("Length of Bin Type in 82nd String:", 1, strEightyTwo.getBinType().getSize());
	sftpClientDriver.assertEquals("Debt Cash back Amt in 82nd String:", "00000000", strEightyTwo.getDebitCashBackAmt().toString());
	sftpClientDriver.assertEquals("Length of Debt Cash back Amt in 82nd String:", 4, strEightyTwo.getDebitCashBackAmt().getSize());
	sftpClientDriver.assertEquals("Use PCI String in 82nd String:", "00", strEightyTwo.getUsePciString().toString());
	sftpClientDriver.assertEquals("Length of Use PCI String in 82nd String:", 1, strEightyTwo.getUsePciString().getSize());
	sftpClientDriver.assertEquals("Authorization Path in 82nd String:", "00", strEightyTwo.getAuthorizationPathTaken().toString());
	sftpClientDriver.assertEquals("Original Tender Amount in 82nd String:", "0000", strEightyTwo.getOriginalTenderAmt().toString());
	sftpClientDriver.assertEquals("External Wallet Id in 82nd String:", "", strEightyTwo.getExternalWalletId());
	sftpClientDriver.assertEquals("External Trans Id in 82nd String:", "", strEightyTwo.getExternalTransId());
	sftpClientDriver.assertEquals("External Error Code in 82nd String:", "F0", strEightyTwo.getExternalErrorCode().toString());
    }

    @Test(groups = { "RT" })
    public void tc08() {
	setCurrentTestCaseInAllDrivers("POS-118780");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	getEtlBinlogFile();
	verifyEtlFileExists(getRemoteTlogExtractFilePath());

	BinLogSixStr stringSix = getSixString(0, 1);
	BinLogTwelveStr stringTwelve = getTwelveString(0, 7);
	BinLogNinetyThreeStr strNintyThree = getNinetyThreeString(0, 5);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 6);

	sftpClientDriver.assertEquals("6th string indicator 0:", "68", stringSix.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("6th string indicator 1:", "F2", stringSix.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("12th string tender type incorrect", "08", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("12th string indicator 0 incorrect", "F1049600", stringTwelve.getIndicatorZero().toString());

	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0021", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", "021612", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", "201104", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0009", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "802088 B", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Tender Amount in 82nd String:", "33", strEightyTwo.getTenderAmt().toString());
	sftpClientDriver.assertEquals("Account Number in 82nd String:", strNintyThree.getPciKey().toString(), strEightyTwo.getAccountNumber().toString());
	sftpClientDriver.assertEquals("Length of Account Number in 82nd String:", 12, strEightyTwo.getAccountNumber().getSize());
	sftpClientDriver.assertEquals("MICR ABA Number in 82nd String:", "", strEightyTwo.getMicrAbaNumber().toString());
	sftpClientDriver.assertEquals("Transaction Id in 82nd String:", "0058", strEightyTwo.getTransactionId().toString());
	sftpClientDriver.assertEquals("Network Id in 82nd String:", "", strEightyTwo.getNetworkId());
	sftpClientDriver.assertEquals("Gateway Ref in 82nd String:", "615700067374", strEightyTwo.getGatewayRefNumber());
	sftpClientDriver.assertEquals("Auth Requestor in 82nd String:", "01", strEightyTwo.getTransactionOriginator().toString());
	sftpClientDriver.assertEquals("Swipe Indicator in 82nd String:", "12", strEightyTwo.getSwipeInd().toString());
	sftpClientDriver.assertEquals("Account Type in 82nd String:", "0000", strEightyTwo.getAccountType().toString());
	sftpClientDriver.assertEquals("Host Response Time in 82nd String:", "001373", strEightyTwo.getHostResponseTime().toString());
	sftpClientDriver.assertEquals("Transaction Type in 82nd String:", "02", strEightyTwo.getTransactionType().toString());
	sftpClientDriver.assertEquals("Event Flags in 82nd String:", "0001", strEightyTwo.getEventFlags().toString());
	sftpClientDriver.assertEquals("Bin Length in 82nd String:", "0000", strEightyTwo.getBinLength().toString());
	sftpClientDriver.assertEquals("Bin Type in 82nd String:", "F0", strEightyTwo.getBinType().toString());
	sftpClientDriver.assertEquals("Debt Cash back Amt in 82nd String:", "00000000", strEightyTwo.getDebitCashBackAmt().toString());
	sftpClientDriver.assertEquals("Use PCI String in 82nd String:", "01", strEightyTwo.getUsePciString().toString());
	sftpClientDriver.assertEquals("Authorization Path in 82nd String:", "00", strEightyTwo.getAuthorizationPathTaken().toString());
	sftpClientDriver.assertEquals("Original Tender Amount in 82nd String:", "0000", strEightyTwo.getOriginalTenderAmt().toString());
	sftpClientDriver.assertEquals("External Wallet Id in 82nd String:", "", strEightyTwo.getExternalWalletId());
	sftpClientDriver.assertEquals("External Trans Id in 82nd String:", "", strEightyTwo.getExternalTransId());
	sftpClientDriver.assertEquals("External Error Code in 82nd String:", "F0", strEightyTwo.getExternalErrorCode().toString());
    }

    @Test(groups = { "RT" })
    public void tc09() {
	setCurrentTestCaseInAllDrivers("POS-118781");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	getEtlBinlogFile();
	verifyEtlFileExists(getRemoteTlogExtractFilePath());

	BinLogZeroStr stringZero = getZeroString(0, 0);

	sftpClientDriver.assertEquals("0th string indicator 0:", "F8", stringZero.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BINLOG_ID));
    }

    @Test(groups = { "RT" })
    public void tc10() {
	setCurrentTestCaseInAllDrivers("POS-118782");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	getEtlBinlogFile();
	verifyEtlFileExists(getRemoteTlogExtractFilePath());

	BinLogZeroStr stringZero = getZeroString(0, 0);

	sftpClientDriver.assertEquals("0th string indicator 0:", "32", stringZero.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BINLOG_ID));
    }

    @Test(groups = { "RT" })
    public void tc12() {
	setCurrentTestCaseInAllDrivers("POS-118784");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	getEtlBinlogFile();
	verifyEtlFileExists(getRemoteTlogExtractFilePath());

	BinLogSixStr stringSix = getSixString(0, 7);
	BinLogTwelveStr stringTwelve = getTwelveString(0, 13);
	sftpClientDriver.assertEquals("6th string indicator 0:", "F130", stringSix.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("12th string tender type:", "48", stringTwelve.getTenderType().toString());
    }

    @Test(groups = { "RT" })
    public void tc16() {
	setCurrentTestCaseInAllDrivers("POS-118788");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	getEtlBinlogFile();
	verifyEtlFileExists(getRemoteTlogExtractFilePath());

	BinLogSixStr stringSix = getSixString(0, 2);
	BinLogTwentyFiveStr strTwentyFive = getTwentyFiveString(0, 1);

	sftpClientDriver.assertEquals("6th string indicator 0:", "F0", stringSix.getExtendedAmt().toString());
	sftpClientDriver.assertEquals("25th string indicator 1:", "0000", strTwentyFive.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("25th string UPC:", "0000000007803473001150", strTwentyFive.getUpcNum().toString());
	sftpClientDriver.assertEquals("25th string dept number:", "81", strTwentyFive.getAccountingDept().toString());
	sftpClientDriver.assertEquals("25th string amount:", "F990", strTwentyFive.getExtendedAmt().toString());
	sftpClientDriver.assertEquals("25th string account nbr:", "F123456789", strTwentyFive.getAcctNbr().toString());
	sftpClientDriver.assertEquals("25th string authorization code:", "012345", strTwentyFive.getAuthCode().toString());
    }

    @Test(groups = { "RT" })
    public void tc17() {
	setCurrentTestCaseInAllDrivers("POS-118789");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	getEtlBinlogFile();
	verifyEtlFileExists(getRemoteTlogExtractFilePath());

	BinLogTwentyFiveStr strTwentyFive = getTwentyFiveString(0, 1);

	sftpClientDriver.assertEquals("6th strings created:", 0, getNumberOfStringsByType(0, "06"));
	sftpClientDriver.assertEquals("25th string indicator 1:", "0001", strTwentyFive.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("25th string UPC:", "0000000007803473001150", strTwentyFive.getUpcNum().toString());
	sftpClientDriver.assertEquals("25th string dept number:", "81", strTwentyFive.getAccountingDept().toString());
	sftpClientDriver.assertEquals("25th string amount:", "F990", strTwentyFive.getExtendedAmt().toString());
	sftpClientDriver.assertEquals("25th string account nbr:", "F123456789", strTwentyFive.getAcctNbr().toString());
	sftpClientDriver.assertEquals("25th string authorization code:", "012345", strTwentyFive.getAuthCode().toString());
    }

    @Test(groups = { "RT" }, enabled=false)
    public void tc18() {
	setCurrentTestCaseInAllDrivers("POS-118817");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	List<File> sendFiles = getCurrentXmlRequests();

	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse(sendFiles.get(0)));
	verifyEtlFileExists(getRemoteBinlogFilePath());
	getEtlBinlogFile();
	getEtlTlogExtractFile();
	long oldBinlogSize = new File(getLocalBinlogFilePath()).length();
	long oldTlogExtractSize = new File(getLocatTlogExtractFilePath()).length();

	changeFileOrDirectoryPermissions(getRemoteBinlogFilePath(), "000");
	changeFileOrDirectoryPermissions(getRemoteTlogExtractFilePath(), "000");

	removeEtlFile(LOG_FILE_PATH);

	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse(sendFiles.get(1)));
	CommonUtils.sleepInSeconds(30);
	getEtlBinlogFile();
	getEtlTlogExtractFile();
	long newBinlogSize = new File(getLocalBinlogFilePath()).length();
	long newTlogExtractSize = new File(getLocatTlogExtractFilePath()).length();

	List<AlertModel> alerts = getListOfEvents();

	sftpClientDriver.assertTrue("Binlog not appened.", oldBinlogSize == newBinlogSize);
	sftpClientDriver.assertTrue("Tlog Extract not appened.", oldTlogExtractSize == newTlogExtractSize);
	sftpClientDriver.assertEquals("Alert application:", "Binlog", alerts.get(alerts.size() - 1).getApplication());
	sftpClientDriver.assertContains("Alert message like:", "Cannot store file", alerts.get(alerts.size() - 1).getMsg_text());
    }

    @Test(groups = { "RT" })
    public void tc19() {
	setCurrentTestCaseInAllDrivers("POS-118790");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	List<File> sendFiles = getCurrentXmlRequests();
	changeFileOrDirectoryPermissions(getWORK_MOVEMENT_AND_TLOG_DIRECTORY(), "000");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse(sendFiles.get(0)));
	verifyEtlFileNotExists(getRemoteBinlogFilePath(), true);
	verifyEtlFileNotExists(getRemoteTlogExtractFilePath(), false);

	changeFileOrDirectoryPermissions(getWORK_MOVEMENT_AND_TLOG_DIRECTORY(), "777");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse(sendFiles.get(1)));
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
    }

    @Test(groups = { "RT" })
    public void tc21() {
	setCurrentTestCaseInAllDrivers("POS-118792");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogThirtyTwoStr strThirtyTwo = getThirtyTwoString(0, 0);
	BinLogSixStr strSix = getSixString(1, 1);

	String date = currentEndDateTime.substring(0, 10).replace("-", "");
	String time = currentEndDateTime.substring(11, 19).replace(":", "");

	sftpClientDriver.assertEquals("32th string indicator 1:", "01", strThirtyTwo.getDiscount().toString());
	sftpClientDriver.assertEquals("32th string Date:", date, strThirtyTwo.getDate().toString());
	sftpClientDriver.assertEquals("32th string Time:", time, strThirtyTwo.getTime().toString());
	sftpClientDriver.assertEquals("32th string Terminal:", "0011", strThirtyTwo.getTerminal().toString());
	sftpClientDriver.assertEquals("32th string source value:", "F6", strThirtyTwo.getSource().toString());
	sftpClientDriver.assertEquals("32th string UPC:", "0000000007790036001640", strThirtyTwo.getUpc().toString());
	sftpClientDriver.assertEquals("32th string Dept nbr:", "95", strThirtyTwo.getDept().toString());
	sftpClientDriver.assertEquals("32th string Neg Flag:", "00", strThirtyTwo.getNegFlag().toString());
	sftpClientDriver.assertEquals("32th string quantity:", "0001", strThirtyTwo.getQuantity().toString());
	sftpClientDriver.assertEquals("32th string transaction number:", "00001111", strThirtyTwo.getOperator().toString());
	sftpClientDriver.assertEquals("6th string indicator 2:", "33554432", strSix.getIndicatorTwo().toString());
    }

    @Test(groups = { "RT" })
    public void tc25() {
	setCurrentTestCaseInAllDrivers("POS-126291");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogThirtyTwoStr strThirtyTwo = getThirtyTwoString(0, 0);
	BinLogSixStr strSix = getSixString(1, 1);

	String date = currentEndDateTime.substring(0, 10).replace("-", "");
	String time = currentEndDateTime.substring(11, 19).replace(":", "");

	sftpClientDriver.assertEquals("32th string indicator 1:", "01", strThirtyTwo.getDiscount().toString());
	sftpClientDriver.assertEquals("32th string Date:", date, strThirtyTwo.getDate().toString());
	sftpClientDriver.assertEquals("32th string Time:", time, strThirtyTwo.getTime().toString());
	sftpClientDriver.assertEquals("32th string Terminal:", "0011", strThirtyTwo.getTerminal().toString());
	sftpClientDriver.assertEquals("32th string source value:", "F7", strThirtyTwo.getSource().toString());
	sftpClientDriver.assertEquals("32th string UPC:", "0000000007790036001640", strThirtyTwo.getUpc().toString());
	sftpClientDriver.assertEquals("32th string Dept nbr:", "95", strThirtyTwo.getDept().toString());
	sftpClientDriver.assertEquals("32th string Neg Flag:", "00", strThirtyTwo.getNegFlag().toString());
	sftpClientDriver.assertEquals("32th string quantity:", "0001", strThirtyTwo.getQuantity().toString());
	sftpClientDriver.assertEquals("32th string transaction number:", "00000215", strThirtyTwo.getOperator().toString());
	sftpClientDriver.assertEquals("6th string indicator 2:", "16777344", strSix.getIndicatorTwo().toString());

    }

    @Test(groups = { "RT" })
    public void tc26() {
	setCurrentTestCaseInAllDrivers("POS-126293");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogThirtyTwoStr strThirtyTwo = getThirtyTwoString(0, 0);
	BinLogSixStr strSix = getSixString(1, 1);

	String date = currentEndDateTime.substring(0, 10).replace("-", "");
	String time = currentEndDateTime.substring(11, 19).replace(":", "");

	sftpClientDriver.assertEquals("32th string indicator 1:", "01", strThirtyTwo.getDiscount().toString());
	sftpClientDriver.assertEquals("32th string Date:", date, strThirtyTwo.getDate().toString());
	sftpClientDriver.assertEquals("32th string Time:", time, strThirtyTwo.getTime().toString());
	sftpClientDriver.assertEquals("32th string Terminal:", "0011", strThirtyTwo.getTerminal().toString());
	sftpClientDriver.assertEquals("32th string source value:", "F8", strThirtyTwo.getSource().toString());
	sftpClientDriver.assertEquals("32th string UPC:", "0000000007790036001640", strThirtyTwo.getUpc().toString());
	sftpClientDriver.assertEquals("32th string Dept nbr:", "95", strThirtyTwo.getDept().toString());
	sftpClientDriver.assertEquals("32th string Neg Flag:", "00", strThirtyTwo.getNegFlag().toString());
	sftpClientDriver.assertEquals("32th string quantity:", "0001", strThirtyTwo.getQuantity().toString());
	sftpClientDriver.assertEquals("32th string transaction number:", "00000215", strThirtyTwo.getOperator().toString());
	sftpClientDriver.assertEquals("6th string indicator 2:", "F128", strSix.getIndicatorTwo().toString());
    }

    @Test(groups = { "RT" })
    public void tc27() {
	setCurrentTestCaseInAllDrivers("POS-126296");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogThirtyTwoStr strThirtyTwo = getThirtyTwoString(0, 0);
	BinLogSixStr strSix = getSixString(1, 1);

	String date = currentEndDateTime.substring(0, 10).replace("-", "");
	String time = currentEndDateTime.substring(11, 19).replace(":", "");

	sftpClientDriver.assertEquals("32th string indicator 1:", "01", strThirtyTwo.getDiscount().toString());
	sftpClientDriver.assertEquals("32th string Date:", date, strThirtyTwo.getDate().toString());
	sftpClientDriver.assertEquals("32th string Time:", time, strThirtyTwo.getTime().toString());
	sftpClientDriver.assertEquals("32th string Terminal:", "0011", strThirtyTwo.getTerminal().toString());
	sftpClientDriver.assertEquals("32th string source value:", "F8", strThirtyTwo.getSource().toString());
	sftpClientDriver.assertEquals("32th string UPC:", "0000000007790036001640", strThirtyTwo.getUpc().toString());
	sftpClientDriver.assertEquals("32th string Dept nbr:", "95", strThirtyTwo.getDept().toString());
	sftpClientDriver.assertEquals("32th string Neg Flag:", "00", strThirtyTwo.getNegFlag().toString());
	sftpClientDriver.assertEquals("32th string quantity:", "0001", strThirtyTwo.getQuantity().toString());
	sftpClientDriver.assertEquals("32th string transaction number:", "00000215", strThirtyTwo.getOperator().toString());
	sftpClientDriver.assertEquals("6th string indicator 2:", "F128", strSix.getIndicatorTwo().toString());
    }

    @Test(groups = { "RT" })
    public void tc29() {
	setCurrentTestCaseInAllDrivers("POS-126304");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	List<File> sendFiles = getCurrentXmlRequests();
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse(sendFiles.get(0)));
	verifyEtlFileExists(getRemoteBinlogFilePath());
	cleanRegMsgInterfaceTable();

	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse(sendFiles.get(1)));
	CommonUtils.sleepInSeconds(30);

	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BINLOG_ID));
    }

    @Test(groups = { "RT" })
    public void tc31() {
	setCurrentTestCaseInAllDrivers("POS-126314");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwelveStr stringTwelve = getTwelveString(0, 27);

	sftpClientDriver.assertEquals("12th string tender type:", "00", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("12th string tender type length:", 1, stringTwelve.getTenderType().getSize());
	sftpClientDriver.assertEquals("12th string tender Amonut:", "0000021483", stringTwelve.getTenderAmt().toString());
	sftpClientDriver.assertEquals("12th string tender Amonut length:", 5, stringTwelve.getTenderAmt().getSize());
	sftpClientDriver.assertEquals("12th string Account nbr:", "000000000011133333333333", stringTwelve.getAccountNumber().toString());
	sftpClientDriver.assertEquals("12th string Account nbr length:", 12, stringTwelve.getAccountNumber().getSize());
	sftpClientDriver.assertEquals("12th string MICR Account nbr:", "000000033333333333", stringTwelve.getMicrAcntNo().toString());
	sftpClientDriver.assertEquals("12th string MICR Account nbr length:", 9, stringTwelve.getMicrAcntNo().getSize());
	sftpClientDriver.assertEquals("12th string MICR ABA nbr:", "0000000111", stringTwelve.getMicrAbaNo().toString());
	sftpClientDriver.assertEquals("12th string MICR ABA nbr length:", 5, stringTwelve.getMicrAbaNo().getSize());
	sftpClientDriver.assertEquals("12th string MICR Check  nbr:", "0000000004444444", stringTwelve.getMicrCheckNo().toString());
	sftpClientDriver.assertEquals("12th string MICR Check length:", 8, stringTwelve.getMicrCheckNo().getSize());
    }

    @Test(groups = { "RT" })
    public void tc32() {
	setCurrentTestCaseInAllDrivers("POS-126422");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwelveStr stringTwelve = getTwelveString(0, 4);

	sftpClientDriver.assertEquals("12th string tender type:", "00", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("12th string tender type:", 1, stringTwelve.getTenderType().getSize());
	sftpClientDriver.assertEquals("12th string tender Amonut:", "0000001100", stringTwelve.getTenderAmt().toString());
	sftpClientDriver.assertEquals("12th string tender Amonut:", 5, stringTwelve.getTenderAmt().getSize());
	sftpClientDriver.assertEquals("12th string Account nbr:", "000000000001600045659648", stringTwelve.getAccountNumber().toString());
	sftpClientDriver.assertEquals("12th string Account nbr:", 12, stringTwelve.getAccountNumber().getSize());
	sftpClientDriver.assertEquals("12th string MICR Account nbr:", "000000000045659648", stringTwelve.getMicrAcntNo().toString());
	sftpClientDriver.assertEquals("12th string MICR Account nbr:", 9, stringTwelve.getMicrAcntNo().getSize());
	sftpClientDriver.assertEquals("12th string MICR ABA nbr:", "0000000016", stringTwelve.getMicrAbaNo().toString());
	sftpClientDriver.assertEquals("12th string MICR ABA nbr:", 5, stringTwelve.getMicrAbaNo().getSize());
	sftpClientDriver.assertEquals("12th string Approval code:", "504101686", stringTwelve.getApprovalCode().toString());
	sftpClientDriver.assertEquals("12th string MICR Check nbr:", "0000000005304731", stringTwelve.getMicrCheckNo().toString());
	sftpClientDriver.assertEquals("12th string MICR Check nbr:", 8, stringTwelve.getMicrCheckNo().getSize());
    }

    @Test(groups = { "RT" })
    public void tc33() {
	setCurrentTestCaseInAllDrivers("POS-126426");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwentyFourStr strTwentyfour = getTwentyFourString(0, 5);

	sftpClientDriver.assertEquals("24th string Type of approval:", "0003", strTwentyfour.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Type of approval size:", 2, strTwentyfour.getTypeOfApproval().getSize());
	sftpClientDriver.assertEquals("24th string Type of override is:", "01", strTwentyfour.getTypeOfOverride().toString());
	sftpClientDriver.assertEquals("24th string Type of override size:", 1, strTwentyfour.getTypeOfOverride().getSize());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00012345", strTwentyfour.getSupervisor().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr size:", 4, strTwentyfour.getSupervisor().getSize());
	sftpClientDriver.assertEquals("24th string Supervisor name:", "AAA FFF             ", strTwentyfour.getSupervisorName().toString());
	sftpClientDriver.assertEquals("24th string Supervisor name length:", 20, strTwentyfour.getSupervisorName().length());
    }

    @Test(groups = { "RT" })
    public void tc35() {
	setCurrentTestCaseInAllDrivers("POS-126433");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwentyFourStr strTwentyfour = getTwentyFourString(0, 3);

	sftpClientDriver.assertEquals("24th string Type of approval:", "0000", strTwentyfour.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Type of override:", "01", strTwentyfour.getTypeOfOverride().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00000000", strTwentyfour.getSupervisor().toString());
	sftpClientDriver.assertEquals("24th string Supervisor name:", "                    ", strTwentyfour.getSupervisorName().toString());
    }

    @Test(groups = { "RT" })
    public void tc36() {
	setCurrentTestCaseInAllDrivers("POS-126451");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogEightyTwoStr strEightyTwoStr1 = getEightyTwostring(0, 4);
	sftpClientDriver.assertEquals("String Type in first 82nd String:", "82", strEightyTwoStr1.getStringType().toString());
	sftpClientDriver.assertEquals("Register in first 82nd String:", "11", strEightyTwoStr1.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in first 82nd String:", "00", strEightyTwoStr1.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in first 82nd String:", "0018", strEightyTwoStr1.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in first 82nd String:", "021612", strEightyTwoStr1.getDate().toString());
	sftpClientDriver.assertEquals("Time in first 82nd String:", "201104", strEightyTwoStr1.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in first 82nd String:", "0014", strEightyTwoStr1.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in first 82nd String:", "01", strEightyTwoStr1.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in first 82nd String:", "00497465", strEightyTwoStr1.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in first 82nd String:", "0001", strEightyTwoStr1.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("MICR ABA Number in first 82nd String:", "", strEightyTwoStr1.getMicrAbaNumber().toString());
	sftpClientDriver.assertEquals("Transaction Id in first 82nd String:", "0058", strEightyTwoStr1.getTransactionId().toString());
	sftpClientDriver.assertEquals("Network Id in first 82nd String:", "", strEightyTwoStr1.getNetworkId());
	sftpClientDriver.assertEquals("Gateway Ref in first 82nd String:", "615700067374", strEightyTwoStr1.getGatewayRefNumber());
	sftpClientDriver.assertEquals("Auth Requestor in first 82nd String:", "01", strEightyTwoStr1.getTransactionOriginator().toString());
	sftpClientDriver.assertEquals("Swipe Indicator in first 82nd String:", "00", strEightyTwoStr1.getSwipeInd().toString());
	sftpClientDriver.assertEquals("Account Type in first 82nd String:", "0000", strEightyTwoStr1.getAccountType().toString());
	sftpClientDriver.assertEquals("Host Response Time in first 82nd String:", "000000", strEightyTwoStr1.getHostResponseTime().toString());
	sftpClientDriver.assertEquals("Transaction Type in first 82nd String:", "00", strEightyTwoStr1.getTransactionType().toString());

	BinLogEightyTwoStr strEightyTwoStr2 = getEightyTwostring(0, 7);
	sftpClientDriver.assertEquals("String Type in second 82nd String:", "82", strEightyTwoStr2.getStringType().toString());
	sftpClientDriver.assertEquals("Register in second 82nd String:", "11", strEightyTwoStr2.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in second 82nd String:", "00", strEightyTwoStr2.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in second 82nd String:", "0018", strEightyTwoStr2.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in second 82nd String:", "021612", strEightyTwoStr2.getDate().toString());
	sftpClientDriver.assertEquals("Time in second 82nd String:", "201204", strEightyTwoStr2.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in second 82nd String:", "0032", strEightyTwoStr2.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in second 82nd String:", "01", strEightyTwoStr2.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in second 82nd String:", "00497466", strEightyTwoStr2.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in second 82nd String:", "0001", strEightyTwoStr2.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("MICR ABA Number in second 82nd String:", "", strEightyTwoStr2.getMicrAbaNumber().toString());
	sftpClientDriver.assertEquals("Transaction Id in second 82nd String:", "0058", strEightyTwoStr2.getTransactionId().toString());
	sftpClientDriver.assertEquals("Network Id in second 82nd String:", "", strEightyTwoStr2.getNetworkId());
	sftpClientDriver.assertEquals("Gateway Ref in second 82nd String:", "615700077375", strEightyTwoStr2.getGatewayRefNumber());
	sftpClientDriver.assertEquals("Auth Requestor in second 82nd String:", "01", strEightyTwoStr2.getTransactionOriginator().toString());
	sftpClientDriver.assertEquals("Swipe Indicator in second 82nd String:", "00", strEightyTwoStr2.getSwipeInd().toString());
	sftpClientDriver.assertEquals("Account Type in second 82nd String:", "0000", strEightyTwoStr2.getAccountType().toString());
	sftpClientDriver.assertEquals("Host Response Time in second 82nd String:", "000000", strEightyTwoStr2.getHostResponseTime().toString());
	sftpClientDriver.assertEquals("Transaction Type in second 82nd String:", "00", strEightyTwoStr2.getTransactionType().toString());
    }

    @Test(groups = { "RT", "AR" })
    public void tc37() {
	setCurrentTestCaseInAllDrivers("POS-126454");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 4);

	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0018", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", "021612", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", "201104", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0014", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "00497465", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("MICR ABA Number in 82nd String:", "", strEightyTwo.getMicrAbaNumber().toString());
	sftpClientDriver.assertEquals("Transaction Id in 82nd String:", "0058", strEightyTwo.getTransactionId().toString());
	sftpClientDriver.assertEquals("Network Id in 82nd String:", "", strEightyTwo.getNetworkId());
	sftpClientDriver.assertEquals("Gateway Ref in 82nd String:", "615700067374", strEightyTwo.getGatewayRefNumber());
	sftpClientDriver.assertEquals("Auth Requestor in 82nd String:", "01", strEightyTwo.getTransactionOriginator().toString());
	sftpClientDriver.assertEquals("Swipe Indicator in 82nd String:", "00", strEightyTwo.getSwipeInd().toString());
	sftpClientDriver.assertEquals("Account Type in 82nd String:", "0000", strEightyTwo.getAccountType().toString());
	sftpClientDriver.assertEquals("Host Response Time in 82nd String:", "000000", strEightyTwo.getHostResponseTime().toString());
	sftpClientDriver.assertEquals("Transaction Type in 82nd String:", "00", strEightyTwo.getTransactionType().toString());
	sftpClientDriver.assertEquals("Event Flags in 82nd String:", "0000", strEightyTwo.getEventFlags().toString());
	sftpClientDriver.assertEquals("Bin Length in 82nd String:", "0000", strEightyTwo.getBinLength().toString());
	sftpClientDriver.assertEquals("Bin Type in 82nd String:", "F0", strEightyTwo.getBinType().toString());
	sftpClientDriver.assertEquals("Debt Cash back Amt in 82nd String:", "00000000", strEightyTwo.getDebitCashBackAmt().toString());
	sftpClientDriver.assertEquals("Use PCI String in 82nd String:", "01", strEightyTwo.getUsePciString().toString());
	sftpClientDriver.assertEquals("Authorization Path in 82nd String:", "00", strEightyTwo.getAuthorizationPathTaken().toString());
	sftpClientDriver.assertEquals("Original Tender Amount in 82nd String:", "0000", strEightyTwo.getOriginalTenderAmt().toString());
	sftpClientDriver.assertEquals("External Wallet Id in 82nd String:", "", strEightyTwo.getExternalWalletId());
	sftpClientDriver.assertEquals("External Trans Id in 82nd String:", "", strEightyTwo.getExternalTransId());
	sftpClientDriver.assertEquals("External Error Code in 82nd String:", "F0", strEightyTwo.getExternalErrorCode().toString());
    }

    @Test(groups = { "RT", "AR" })
    public void tc38() {
	setCurrentTestCaseInAllDrivers("POS-126457");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 4);

	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0018", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", "021612", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", "201104", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0032", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "00497465", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("MICR ABA Number in 82nd String:", "", strEightyTwo.getMicrAbaNumber().toString());
	sftpClientDriver.assertEquals("Transaction Id in 82nd String:", "0058", strEightyTwo.getTransactionId().toString());
	sftpClientDriver.assertEquals("Network Id in 82nd String:", "", strEightyTwo.getNetworkId());
	sftpClientDriver.assertEquals("Gateway Ref in 82nd String:", "615700067374", strEightyTwo.getGatewayRefNumber());
	sftpClientDriver.assertEquals("Auth Requestor in 82nd String:", "01", strEightyTwo.getTransactionOriginator().toString());
	sftpClientDriver.assertEquals("Swipe Indicator in 82nd String:", "00", strEightyTwo.getSwipeInd().toString());
	sftpClientDriver.assertEquals("Account Type in 82nd String:", "0000", strEightyTwo.getAccountType().toString());
	sftpClientDriver.assertEquals("Host Response Time in 82nd String:", "000000", strEightyTwo.getHostResponseTime().toString());
	sftpClientDriver.assertEquals("Transaction Type in 82nd String:", "00", strEightyTwo.getTransactionType().toString());
	sftpClientDriver.assertEquals("Event Flags in 82nd String:", "0000", strEightyTwo.getEventFlags().toString());
	sftpClientDriver.assertEquals("Bin Length in 82nd String:", "0000", strEightyTwo.getBinLength().toString());
	sftpClientDriver.assertEquals("Bin Type in 82nd String:", "F0", strEightyTwo.getBinType().toString());
	sftpClientDriver.assertEquals("Debt Cash back Amt in 82nd String:", "00000000", strEightyTwo.getDebitCashBackAmt().toString());
	sftpClientDriver.assertEquals("Use PCI String in 82nd String:", "01", strEightyTwo.getUsePciString().toString());
	sftpClientDriver.assertEquals("Authorization Path in 82nd String:", "00", strEightyTwo.getAuthorizationPathTaken().toString());
	sftpClientDriver.assertEquals("Original Tender Amount in 82nd String:", "0000", strEightyTwo.getOriginalTenderAmt().toString());
	sftpClientDriver.assertEquals("External Wallet Id in 82nd String:", "", strEightyTwo.getExternalWalletId());
	sftpClientDriver.assertEquals("External Trans Id in 82nd String:", "", strEightyTwo.getExternalTransId());
	sftpClientDriver.assertEquals("External Error Code in 82nd String:", "F0", strEightyTwo.getExternalErrorCode().toString());
    }

    @Test(groups = { "RT" })
    public void tc39() {
	setCurrentTestCaseInAllDrivers("POS-126461");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogSixStr stringSix = getSixString(0, 1);
	BinLogTwelveStr stringTwelve = getTwelveString(0, 7);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 6);
	BinLogNinetyThreeStr strNintyThree = getNinetyThreeString(0, 5);

	sftpClientDriver.assertEquals("6th string indicator 0:", "68", stringSix.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("6th string indicator 1:", "F2", stringSix.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("12th string tender type incorrect", "08", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("12th string indicator 0 incorrect", "F1049600", stringTwelve.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0021", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", "021612", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", "201104", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0017", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "802088 B", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Tender Amount in 82nd String:", "33", strEightyTwo.getTenderAmt().toString());
	sftpClientDriver.assertEquals("Account Number in 82nd String:", strNintyThree.getPciKey().toString(), strEightyTwo.getAccountNumber().toString());
	sftpClientDriver.assertEquals("Length of Account Number in 82nd String:", 12, strEightyTwo.getAccountNumber().getSize());
	sftpClientDriver.assertEquals("MICR ABA Number in 82nd String:", "", strEightyTwo.getMicrAbaNumber().toString());
	sftpClientDriver.assertEquals("Transaction Id in 82nd String:", "0058", strEightyTwo.getTransactionId().toString());
	sftpClientDriver.assertEquals("Network Id in 82nd String:", "", strEightyTwo.getNetworkId());
	sftpClientDriver.assertEquals("Gateway Ref in 82nd String:", "615700067374", strEightyTwo.getGatewayRefNumber());
	sftpClientDriver.assertEquals("Auth Requestor in 82nd String:", "01", strEightyTwo.getTransactionOriginator().toString());
	sftpClientDriver.assertEquals("Swipe Indicator in 82nd String:", "07", strEightyTwo.getSwipeInd().toString());
	sftpClientDriver.assertEquals("Account Type in 82nd String:", "0000", strEightyTwo.getAccountType().toString());
	sftpClientDriver.assertEquals("Host Response Time in 82nd String:", "001373", strEightyTwo.getHostResponseTime().toString());
	sftpClientDriver.assertEquals("Transaction Type in 82nd String:", "02", strEightyTwo.getTransactionType().toString());
	sftpClientDriver.assertEquals("Event Flags in 82nd String:", "0001", strEightyTwo.getEventFlags().toString());
	sftpClientDriver.assertEquals("Bin Length in 82nd String:", "0000", strEightyTwo.getBinLength().toString());
	sftpClientDriver.assertEquals("Bin Type in 82nd String:", "F0", strEightyTwo.getBinType().toString());
	sftpClientDriver.assertEquals("Debt Cash back Amt in 82nd String:", "00000000", strEightyTwo.getDebitCashBackAmt().toString());
	sftpClientDriver.assertEquals("Use PCI String in 82nd String:", "01", strEightyTwo.getUsePciString().toString());
	sftpClientDriver.assertEquals("Authorization Path in 82nd String:", "00", strEightyTwo.getAuthorizationPathTaken().toString());
	sftpClientDriver.assertEquals("Original Tender Amount in 82nd String:", "0000", strEightyTwo.getOriginalTenderAmt().toString());
	sftpClientDriver.assertEquals("External Wallet Id in 82nd String:", "", strEightyTwo.getExternalWalletId());
	sftpClientDriver.assertEquals("External Trans Id in 82nd String:", "", strEightyTwo.getExternalTransId());
	sftpClientDriver.assertEquals("External Error Code in 82nd String:", "F0", strEightyTwo.getExternalErrorCode().toString());
    }

    @Test(groups = { "RT" })
    public void tc40() {
	setCurrentTestCaseInAllDrivers("POS-126474");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogSixStr stringSix = getSixString(0, 1);
	BinLogTwelveStr stringTwelve = getTwelveString(0, 7);
	BinLogEightyTwoStr strEightyTwoStr1 = getEightyTwostring(0, 4);

	sftpClientDriver.assertEquals("6th string indicator 0:", "F2", stringSix.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("6th string indicator 1:", "", stringSix.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("12th string tender type:", "00", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("12th string indicator 0:", "", stringTwelve.getIndicatorZero().toString());

	sftpClientDriver.assertEquals("String Type in first 82nd String:", "82", strEightyTwoStr1.getStringType().toString());
	sftpClientDriver.assertEquals("Register in first 82nd String:", "11", strEightyTwoStr1.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in first 82nd String:", "00", strEightyTwoStr1.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in first 82nd String:", "0021", strEightyTwoStr1.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in first 82nd String:", "000000", strEightyTwoStr1.getDate().toString());
	sftpClientDriver.assertEquals("Time in first 82nd String:", "000000", strEightyTwoStr1.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in first 82nd String:", "00", strEightyTwoStr1.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in first 82nd String:", "01", strEightyTwoStr1.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in first 82nd String:", "00497466", strEightyTwoStr1.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in first 82nd String:", "0001", strEightyTwoStr1.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("MICR ABA Number in first 82nd String:", "", strEightyTwoStr1.getMicrAbaNumber().toString());
	sftpClientDriver.assertEquals("Transaction Id in first 82nd String:", "0058", strEightyTwoStr1.getTransactionId().toString());
	sftpClientDriver.assertEquals("Network Id in first 82nd String:", "", strEightyTwoStr1.getNetworkId());
	sftpClientDriver.assertEquals("Gateway Ref in first 82nd String:", "615700077375", strEightyTwoStr1.getGatewayRefNumber());
	sftpClientDriver.assertEquals("Auth Requestor in first 82nd String:", "01", strEightyTwoStr1.getTransactionOriginator().toString());
	sftpClientDriver.assertEquals("Swipe Indicator in first 82nd String:", "00", strEightyTwoStr1.getSwipeInd().toString());
	sftpClientDriver.assertEquals("Account Type in first 82nd String:", "0000", strEightyTwoStr1.getAccountType().toString());
	sftpClientDriver.assertEquals("Host Response Time in first 82nd String:", "000000", strEightyTwoStr1.getHostResponseTime().toString());
	sftpClientDriver.assertEquals("Transaction Type in first 82nd String:", "01", strEightyTwoStr1.getTransactionType().toString());
	sftpClientDriver.assertEquals("Original Tender Amount in 82nd String:", "0000", strEightyTwoStr1.getOriginalTenderAmt().toString());

	BinLogEightyTwoStr strEightyTwoStr2 = getEightyTwostring(0, 6);
	sftpClientDriver.assertEquals("String Type in second 82nd String:", "82", strEightyTwoStr2.getStringType().toString());
	sftpClientDriver.assertEquals("Register in second 82nd String:", "11", strEightyTwoStr2.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in second 82nd String:", "00", strEightyTwoStr2.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in second 82nd String:", "0021", strEightyTwoStr2.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in second 82nd String:", "000000", strEightyTwoStr2.getDate().toString());
	sftpClientDriver.assertEquals("Time in second 82nd String:", "000000", strEightyTwoStr2.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in second 82nd String:", "00", strEightyTwoStr2.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in second 82nd String:", "01", strEightyTwoStr2.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in second 82nd String:", "B46346", strEightyTwoStr2.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in second 82nd String:", "0001", strEightyTwoStr2.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Account Number in 82nd String:", "00", strEightyTwoStr2.getAccountNumber().toString());
	sftpClientDriver.assertEquals("MICR ABA Number in second 82nd String:", "", strEightyTwoStr2.getMicrAbaNumber().toString());
	sftpClientDriver.assertEquals("Transaction Id in second 82nd String:", "0058", strEightyTwoStr2.getTransactionId().toString());
	sftpClientDriver.assertEquals("Network Id in second 82nd String:", "", strEightyTwoStr2.getNetworkId());
	sftpClientDriver.assertEquals("Gateway Ref in second 82nd String:", "", strEightyTwoStr2.getGatewayRefNumber());
	sftpClientDriver.assertEquals("Auth Requestor in second 82nd String:", "01", strEightyTwoStr2.getTransactionOriginator().toString());
	sftpClientDriver.assertEquals("Swipe Indicator in second 82nd String:", "00", strEightyTwoStr2.getSwipeInd().toString());
	sftpClientDriver.assertEquals("Account Type in second 82nd String:", "0000", strEightyTwoStr2.getAccountType().toString());
	sftpClientDriver.assertEquals("Host Response Time in second 82nd String:", "000000", strEightyTwoStr2.getHostResponseTime().toString());
	sftpClientDriver.assertEquals("Transaction Type in second 82nd String:", "01", strEightyTwoStr2.getTransactionType().toString());
    }
}
