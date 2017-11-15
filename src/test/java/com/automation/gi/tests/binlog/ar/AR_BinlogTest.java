/**
 * 
 */
package com.automation.gi.tests.binlog.ar;

import java.util.Date;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.common.logger.CerberusLogger;
import com.automation.common.utils.CommonUtils;
import com.automation.gi.tests.binlog.BinlogTest;
import com.automation.middleware.CerberusMiddlewareTestListener;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogEightyTwoStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogFourteenStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogNinetyThreeStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogSixStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogThirtySixStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogTwelveStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogTwentyFourStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogZeroStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.binlogfifteenstrings.BinLogFifteenDtFiftyOne;

/**
 * @author David Villalobos
 *
 */
@Listeners(CerberusMiddlewareTestListener.class)
public class AR_BinlogTest extends BinlogTest {
    @Test(groups = { "RT" })
    public void tc06() {
	setCurrentTestCaseInAllDrivers("POS-118778");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	String date = CommonUtils.getDateTimeFromPattern(STRING_82_DATE_FORMAT, new Date());
	String time = currentEndDateTime.substring(11, 19).replace(":", "");

	BinLogSixStr priceErrorStringSix = getSixString(0, 1);
	BinLogSixStr automotiveStringSix = getSixString(0, 4);
	BinLogSixStr compAdvStringSix = getSixString(0, 7);
	BinLogSixStr miscStringSix = getSixString(0, 10);
	BinLogSixStr promoStringSix = getSixString(0, 16);
	BinLogTwelveStr stringTwelve = getTwelveString(0, 23);
	BinLogNinetyThreeStr strNintyThree = getNinetyThreeString(0, 21);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 22);

	sftpClientDriver.assertEquals("price override 6th string indicator 0:", "8194", priceErrorStringSix.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("price error in 6th string indicator 1:", "64", priceErrorStringSix.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("automotive adjustment in  6th string indicator 1:", "16", automotiveStringSix.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("comp adv  in  6th string indicator 1:", "F4", compAdvStringSix.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("misc adv  in  6th string indicator 1:", "32", miscStringSix.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("promotion  6th string indicator 0:", "8194", promoStringSix.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("12th string tender type:", "02", stringTwelve.getTenderType().toString());

	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0021", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", date, strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", time, strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0002", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "00001509", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Tender Amount in 82nd String:", "185500", strEightyTwo.getTenderAmt().toString());
	sftpClientDriver.assertEquals("Account Number in 82nd String:", strNintyThree.getPciKey().toString(), strEightyTwo.getAccountNumber().toString());
	sftpClientDriver.assertEquals("MICR ABA Number in 82nd String:", "", strEightyTwo.getMicrAbaNumber().toString());
	sftpClientDriver.assertEquals("Transaction Id in 82nd String:", "0058", strEightyTwo.getTransactionId().toString());
	sftpClientDriver.assertEquals("Network Id in 82nd String:", "", strEightyTwo.getNetworkId());
	sftpClientDriver.assertEquals("Gateway Ref in 82nd String:", "615700067374", strEightyTwo.getGatewayRefNumber());
	sftpClientDriver.assertEquals("Auth Requestor in 82nd String:", "01", strEightyTwo.getTransactionOriginator().toString());
	sftpClientDriver.assertEquals("Swipe Indicator in 82nd String:", "00", strEightyTwo.getSwipeInd().toString());
	sftpClientDriver.assertEquals("Account Type in 82nd String:", "0000", strEightyTwo.getAccountType().toString());
	sftpClientDriver.assertEquals("Host Response Time in 82nd String:", "001373", strEightyTwo.getHostResponseTime().toString());
	sftpClientDriver.assertEquals("Transaction Type in 82nd String:", "01", strEightyTwo.getTransactionType().toString());
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
    public void tc07() {
	setCurrentTestCaseInAllDrivers("POS-118779");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogSixStr stringSix = getSixString(0, 1);
	BinLogTwelveStr stringTwelve = getTwelveString(0, 6);
	BinLogNinetyThreeStr strNintyThree = getNinetyThreeString(0, 4);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 5);

	sftpClientDriver.assertEquals("6th string indicator 0:", "8258", stringSix.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("12th string tender type:", "01", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("12th string indicator 0:", "F1049600", stringTwelve.getIndicatorZero().toString());

	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0021", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", "011516", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", "005440", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0001", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "00497465", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Tender Amount in 82nd String:", "F0", strEightyTwo.getTenderAmt().toString());
	sftpClientDriver.assertEquals("Account Number in 82nd String:", strNintyThree.getPciKey().toString(), strEightyTwo.getAccountNumber().toString());
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
    public void tc11() {
	setCurrentTestCaseInAllDrivers("POS-118783");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwelveStr stringTwelve = getTwelveString(0, 5);
	BinLogZeroStr stringZero = getZeroString(0, 0);
	BinLogNinetyThreeStr strNintyThree = getNinetyThreeString(0, 3);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 4);

	sftpClientDriver.assertEquals("0th string indicator 0:", "16", stringZero.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("12th string tender type:", "10", stringTwelve.getTenderType().toString());

	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0021", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", "011516", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", "085440", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0096", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "00497465", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Tender Amount in 82nd String:", "727000", strEightyTwo.getTenderAmt().toString());
	sftpClientDriver.assertEquals("Account Number in 82nd String:", strNintyThree.getPciKey().toString(), strEightyTwo.getAccountNumber().toString());
	sftpClientDriver.assertEquals("MICR ABA Number in 82nd String:", "", strEightyTwo.getMicrAbaNumber().toString());
	sftpClientDriver.assertEquals("Transaction Id in 82nd String:", "0058", strEightyTwo.getTransactionId().toString());
	sftpClientDriver.assertEquals("Network Id in 82nd String:", "", strEightyTwo.getNetworkId());
	sftpClientDriver.assertEquals("Gateway Ref in 82nd String:", "615700067374", strEightyTwo.getGatewayRefNumber());
	sftpClientDriver.assertEquals("Auth Requestor in 82nd String:", "01", strEightyTwo.getTransactionOriginator().toString());
	sftpClientDriver.assertEquals("Swipe Indicator in 82nd String:", "00", strEightyTwo.getSwipeInd().toString());
	sftpClientDriver.assertEquals("Account Type in 82nd String:", "0000", strEightyTwo.getAccountType().toString());
	sftpClientDriver.assertEquals("Host Response Time in 82nd String:", "000000", strEightyTwo.getHostResponseTime().toString());
	sftpClientDriver.assertEquals("Transaction Type in 82nd String:", "01", strEightyTwo.getTransactionType().toString());
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
    public void tc13() {
	setCurrentTestCaseInAllDrivers("POS-118785");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwelveStr stringTwelve = getTwelveString(0, 9);
	BinLogSixStr stringSix = getSixString(0, 3);
	BinLogNinetyThreeStr strNintyThree = getNinetyThreeString(0, 7);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 8);

	sftpClientDriver.assertEquals("6th string indicator 0:", "F196", stringSix.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("12th string tender type:", "09", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0021", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", "050115", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", "121257", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0004", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "123456", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Tender Amount in 82nd String:", "F0", strEightyTwo.getTenderAmt().toString());
	sftpClientDriver.assertEquals("Account Number in 82nd String:", strNintyThree.getPciKey().toString(), strEightyTwo.getAccountNumber().toString());
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
    public void tc14() {
	setCurrentTestCaseInAllDrivers("POS-118786");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogSixStr stringSix = getSixString(0, 7);
	BinLogThirtySixStr stringThirtySix = getThirtySixString(0, 8);

	sftpClientDriver.assertEquals("6th string Indicator 0:", "F2", stringSix.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("6th string Indicator 3:", "F65536", stringSix.getIndicatorThree().toString());
	sftpClientDriver.assertEquals("36th string Tax Amt 1:", "F1", stringThirtySix.getTaxOneAmountCharged().toString());
	sftpClientDriver.assertEquals("36th string Sell price:", "F0", stringThirtySix.getVatSellPrice().toString());
	sftpClientDriver.assertEquals("36th string Tax flag:", "F1", stringThirtySix.getIndicator().toString());
    }

    @Test(groups = { "RT" })
    public void tc15() {
	setCurrentTestCaseInAllDrivers("POS-118787");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogSixStr stringSix = getSixString(0, 25);
	BinLogThirtySixStr stringThirtySix = getThirtySixString(0, 26);

	sftpClientDriver.assertEquals("6th string amount:", "F0", stringSix.getExtendedAmt().toString());
	sftpClientDriver.assertEquals("6th string Indicator 3:", "F65536", stringSix.getIndicatorThree().toString());
	sftpClientDriver.assertEquals("36th string Tax Amt 1:", "F1", stringThirtySix.getTaxOneAmountCharged().toString());
	sftpClientDriver.assertEquals("36th string Sell price:", "F1", stringThirtySix.getVatSellPrice().toString());
    }

    @Test(groups = { "RT" })
    public void tc22() {
	setCurrentTestCaseInAllDrivers("POS-118793");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogThirtySixStr stringThirtySix = getThirtySixString(0, 2);
	BinLogFourteenStr stringFourteen = getFourTeenString(0, 10);

	sftpClientDriver.assertEquals("36th string Tax Amt 1:", "F780", stringThirtySix.getTaxOneAmountCharged().toString());
	sftpClientDriver.assertEquals("36th string Tax Amt 4:", "F136", stringThirtySix.getTaxFourAmountCharged().toString());
	sftpClientDriver.assertEquals("36th string Tax Amt 5:", "23", stringThirtySix.getTaxFiveAmountCharged().toString());
	sftpClientDriver.assertEquals("36th string Tax Rate 1:", "2100", stringThirtySix.getTaxOneRatePercent().toString());
	sftpClientDriver.assertEquals("36th string Tax Rate 4:", "F367", stringThirtySix.getTaxFourRatePercent().toString());
	sftpClientDriver.assertEquals("36th string Tax Rate 5:", "62", stringThirtySix.getTaxFiveRatePercent().toString());
	sftpClientDriver.assertEquals("14th string Tax Amt 1:", "00001820", stringFourteen.getTaxOneAmount().toString());
	sftpClientDriver.assertEquals("14th string Tax Amt 4:", "00000317", stringFourteen.getTaxFourAmount().toString());
	sftpClientDriver.assertEquals("14th string Tax Amt 5:", "00000054", stringFourteen.getTaxFiveAmount().toString());
	sftpClientDriver.assertEquals("14th string Tax Amt 11:", "00002457", stringFourteen.getTaxElevenAmount().toString());
    }

    @Test(groups = { "RT" })
    public void tc23() {
	setCurrentTestCaseInAllDrivers("POS-118794");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwelveStr stringTwelve = getTwelveString(0, 4);

	sftpClientDriver.assertEquals("12th string tender type:", "23", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("12th string tender amount:", "0000013086", stringTwelve.getTenderAmt().toString());
	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BINLOG_ID));
    }

    @Test(groups = { "RT" })
    public void tc24() {
	setCurrentTestCaseInAllDrivers("POS-118795");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogFifteenDtFiftyOne stringFifteen = getFifteenDtFiftyOneString(0, 6);

	sftpClientDriver.assertEquals("15th string operator value value:", "00000215", stringFifteen.getOperator().toString());
	sftpClientDriver.assertEquals("15th string Terminal value value:", "0011", stringFifteen.getTermianl().toString());
	sftpClientDriver.assertEquals("15th string rounded plus value:", "00000004", stringFifteen.getRoundPlus().toString());
    }

    @Test(groups = { "RT" })
    public void tc28() {
	setCurrentTestCaseInAllDrivers("POS-118796");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogFifteenDtFiftyOne stringFifteen = getFifteenDtFiftyOneString(0, 8);

	sftpClientDriver.assertEquals("15th string operator value value:", "00000215", stringFifteen.getOperator().toString());
	sftpClientDriver.assertEquals("15th string Terminal value value:", "0011", stringFifteen.getTermianl().toString());
	sftpClientDriver.assertEquals("15th string rounded minus value:", "00000002", stringFifteen.getRoundMinus().toString());
    }

    @Test(groups = { "RT" })
    public void tc01_AR() {
	setCurrentTestCaseInAllDrivers("POS-126677");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwentyFourStr strTwentyfour1 = getTwentyFourString(0, 3);
	BinLogTwentyFourStr strTwentyfour2 = getTwentyFourString(0, 7);
	BinLogTwentyFourStr strTwentyfour3 = getTwentyFourString(0, 10);
	BinLogTwentyFourStr strTwentyfour4 = getTwentyFourString(0, 13);

	sftpClientDriver.assertEquals("24th string Type of approval:", "0006", strTwentyfour1.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Type of override:", "01", strTwentyfour1.getTypeOfOverride().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00012345", strTwentyfour1.getSupervisor().toString());
	sftpClientDriver.assertEquals("24th string Supervisor name:", "DDD EEE FFF         ", strTwentyfour1.getSupervisorName());
	sftpClientDriver.assertEquals("24th string Type of approval:", "0021", strTwentyfour2.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Type of override:", "01", strTwentyfour2.getTypeOfOverride().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00056789", strTwentyfour2.getSupervisor().toString());
	sftpClientDriver.assertEquals("24th string Supervisor name:", "DDD EEE FFF         ", strTwentyfour2.getSupervisorName().toString());
	sftpClientDriver.assertEquals("24th string Type of approval:", "0020", strTwentyfour3.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Type of override:", "01", strTwentyfour3.getTypeOfOverride().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00003456", strTwentyfour3.getSupervisor().toString());
	sftpClientDriver.assertEquals("24th string Supervisor name:", "DDD EEE FFF         ", strTwentyfour3.getSupervisorName().toString());
	sftpClientDriver.assertEquals("24th string Type of approval:", "0036", strTwentyfour4.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00001245", strTwentyfour4.getSupervisor().toString());
    }

    @Test(groups = { "RT" })
    public void tc02_AR() {
	setCurrentTestCaseInAllDrivers("POS-126687");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwentyFourStr strTwentyfour1 = getTwentyFourString(0, 4);
	BinLogTwentyFourStr strTwentyfour2 = getTwentyFourString(0, 8);
	BinLogTwentyFourStr strTwentyfour3 = getTwentyFourString(0, 11);

	sftpClientDriver.assertEquals("24th string Type of approval:", "0015", strTwentyfour1.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00012345", strTwentyfour1.getSupervisor().toString());
	sftpClientDriver.assertEquals("24th string Type of approval:", "0032", strTwentyfour2.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00000012", strTwentyfour2.getSupervisor().toString());
	sftpClientDriver.assertEquals("24th string Type of approval:", "0036", strTwentyfour3.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00000045", strTwentyfour3.getSupervisor().toString());
    }

    @Test(groups = { "RT" })
    public void tc03_AR() {
	setCurrentTestCaseInAllDrivers("POS-126691");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwentyFourStr strTwentyfour1 = getTwentyFourString(0, 4);
	BinLogTwentyFourStr strTwentyfour2 = getTwentyFourString(0, 8);
	BinLogTwentyFourStr strTwentyfour3 = getTwentyFourString(0, 11);

	sftpClientDriver.assertEquals("24th string Type of approval:", "0034", strTwentyfour1.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00012345", strTwentyfour1.getSupervisor().toString());
	sftpClientDriver.assertEquals("24th string Type of approval:", "0039", strTwentyfour2.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00000001", strTwentyfour2.getSupervisor().toString());
	sftpClientDriver.assertEquals("24th string Type of approval:", "0010", strTwentyfour3.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00000012", strTwentyfour3.getSupervisor().toString());
    }

    @Test(groups = { "RT" })
    public void tc04_AR() {
	setCurrentTestCaseInAllDrivers("POS-126694");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogSixStr priceErrorStringSix = getSixString(0, 1);
	BinLogSixStr automotiveStringSix = getSixString(0, 4);
	BinLogSixStr compAdvStringSix = getSixString(0, 7);
	BinLogSixStr miscStringSix = getSixString(0, 10);
	BinLogSixStr promoStringSix = getSixString(0, 16);
	BinLogTwelveStr stringTwelve = getTwelveString(0, 23);
	BinLogNinetyThreeStr strNintyThree = getNinetyThreeString(0, 21);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 22);

	sftpClientDriver.assertEquals("price override 6th string indicator 0:", "8194", priceErrorStringSix.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("price error in 6th string indicator 1:", "64", priceErrorStringSix.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("automotive adjustment in  6th string indicator 1:", "16", automotiveStringSix.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("comp adv  in  6th string indicator 1:", "F4", compAdvStringSix.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("misc adv  in  6th string indicator 1:", "32", miscStringSix.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("promotion  6th string indicator 0:", "8194", promoStringSix.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("12th string tender type incorrect", "02", stringTwelve.getTenderType().toString());

	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0021", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", "040715", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", "090336", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0008", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "00001509", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Tender Amount in 82nd String:", "185500", strEightyTwo.getTenderAmt().toString());
	sftpClientDriver.assertEquals("Account Number in 82nd String:", strNintyThree.getPciKey().toString(), strEightyTwo.getAccountNumber().toString());
	sftpClientDriver.assertEquals("MICR ABA Number in 82nd String:", "", strEightyTwo.getMicrAbaNumber().toString());
	sftpClientDriver.assertEquals("Transaction Id in 82nd String:", "0058", strEightyTwo.getTransactionId().toString());
	sftpClientDriver.assertEquals("Network Id in 82nd String:", "", strEightyTwo.getNetworkId());
	sftpClientDriver.assertEquals("Gateway Ref in 82nd String:", "615700067374", strEightyTwo.getGatewayRefNumber());
	sftpClientDriver.assertEquals("Auth Requestor in 82nd String:", "01", strEightyTwo.getTransactionOriginator().toString());
	sftpClientDriver.assertEquals("Swipe Indicator in 82nd String:", "00", strEightyTwo.getSwipeInd().toString());
	sftpClientDriver.assertEquals("Account Type in 82nd String:", "0000", strEightyTwo.getAccountType().toString());
	sftpClientDriver.assertEquals("Host Response Time in 82nd String:", "001373", strEightyTwo.getHostResponseTime().toString());
	sftpClientDriver.assertEquals("Transaction Type in 82nd String:", "01", strEightyTwo.getTransactionType().toString());
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
    public void tc05_AR() {
	setCurrentTestCaseInAllDrivers("POS-126702");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogSixStr priceErrorStringSix = getSixString(0, 1);
	BinLogSixStr automotiveStringSix = getSixString(0, 4);
	BinLogSixStr compAdvStringSix = getSixString(0, 7);
	BinLogSixStr miscStringSix = getSixString(0, 10);
	BinLogSixStr promoStringSix = getSixString(0, 16);
	BinLogTwelveStr stringTwelve = getTwelveString(0, 23);
	BinLogNinetyThreeStr strNintyThree = getNinetyThreeString(0, 21);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 22);

	sftpClientDriver.assertEquals("price override 6th string indicator 0:", "8194", priceErrorStringSix.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("price error in 6th string indicator 1:", "64", priceErrorStringSix.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("automotive adjustment in  6th string indicator 1:", "16", automotiveStringSix.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("comp adv  in  6th string indicator 1:", "F4", compAdvStringSix.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("misc adv  in  6th string indicator 1:", "32", miscStringSix.getIndicatorOne().toString());
	sftpClientDriver.assertEquals("promotion  6th string indicator 0:", "8194", promoStringSix.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("12th string tender type incorrect", "02", stringTwelve.getTenderType().toString());

	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0021", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", "040715", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", "090336", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0011", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "00001509", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Tender Amount in 82nd String:", "185500", strEightyTwo.getTenderAmt().toString());
	sftpClientDriver.assertEquals("Account Number in 82nd String:", strNintyThree.getPciKey().toString(), strEightyTwo.getAccountNumber().toString());
	sftpClientDriver.assertEquals("MICR ABA Number in 82nd String:", "", strEightyTwo.getMicrAbaNumber().toString());
	sftpClientDriver.assertEquals("Transaction Id in 82nd String:", "0058", strEightyTwo.getTransactionId().toString());
	sftpClientDriver.assertEquals("Network Id in 82nd String:", "", strEightyTwo.getNetworkId());
	sftpClientDriver.assertEquals("Gateway Ref in 82nd String:", "615700067374", strEightyTwo.getGatewayRefNumber());
	sftpClientDriver.assertEquals("Auth Requestor in 82nd String:", "01", strEightyTwo.getTransactionOriginator().toString());
	sftpClientDriver.assertEquals("Swipe Indicator in 82nd String:", "00", strEightyTwo.getSwipeInd().toString());
	sftpClientDriver.assertEquals("Account Type in 82nd String:", "0000", strEightyTwo.getAccountType().toString());
	sftpClientDriver.assertEquals("Host Response Time in 82nd String:", "001373", strEightyTwo.getHostResponseTime().toString());
	sftpClientDriver.assertEquals("Transaction Type in 82nd String:", "01", strEightyTwo.getTransactionType().toString());
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

    @Override
    @Test(groups = { "RT" })
    public void tc36() {
	setCurrentTestCaseInAllDrivers("POS-126712");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogEightyTwoStr strEightyTwoStr1 = getEightyTwostring(0, 4);
	BinLogEightyTwoStr strEightyTwoStr2 = getEightyTwostring(0, 7);

	sftpClientDriver.assertEquals("String Type in first 82nd String:", "82", strEightyTwoStr1.getStringType().toString());
	sftpClientDriver.assertEquals("Register in first 82nd String:", "11", strEightyTwoStr1.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in first 82nd String:", "00", strEightyTwoStr1.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in first 82nd String:", "0018", strEightyTwoStr1.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in first 82nd String:", "021612", strEightyTwoStr1.getDate().toString());
	sftpClientDriver.assertEquals("Time in first 82nd String:", "201104", strEightyTwoStr1.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in first 82nd String:", "0008", strEightyTwoStr1.getAuthorizationType().toString());
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

	sftpClientDriver.assertEquals("String Type in second 82nd String:", "82", strEightyTwoStr2.getStringType().toString());
	sftpClientDriver.assertEquals("Register in second 82nd String:", "11", strEightyTwoStr2.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in second 82nd String:", "00", strEightyTwoStr2.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in second 82nd String:", "0018", strEightyTwoStr2.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in second 82nd String:", "021612", strEightyTwoStr2.getDate().toString());
	sftpClientDriver.assertEquals("Time in second 82nd String:", "201204", strEightyTwoStr2.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in second 82nd String:", "0011", strEightyTwoStr2.getAuthorizationType().toString());
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

	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BINLOG_ID));
    }
}
