/**
 * 
 */
package com.automation.gi.tests.binlog.br;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.common.logger.CerberusLogger;
import com.automation.gi.tests.binlog.BinlogTest;
import com.automation.middleware.CerberusMiddlewareTestListener;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogEightyTwoStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogNinetyThreeStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogSixStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogTenStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogThirteenStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogThirtySixStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogTwelveStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogTwentyFourStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogZeroStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.binlogfifteenstrings.BinLogFifteenDtEighteen;

/**
 * @author David Villalobos
 *
 */
@Listeners(CerberusMiddlewareTestListener.class)
public class BR_BinlogTest extends BinlogTest {

    @Test(groups = { "RT" })
    public void tc01_BR() {
	setCurrentTestCaseInAllDrivers("POS-126866");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogSixStr stringSix = getSixString(0, 3);
	BinLogNinetyThreeStr strNintyThree = getNinetyThreeString(0, 5);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 6);

	sftpClientDriver.assertEquals("6th indicator 3:", "1056", stringSix.getIndicatorThree().toString());
	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0018", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", "040715", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", "090336", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0008", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "00001509", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Tender Amount in 82nd String:", "1950", strEightyTwo.getTenderAmt().toString());
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

	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BINLOG_ID));
    }

    @Test(groups = { "RT" })
    public void tc02_BR() {
	setCurrentTestCaseInAllDrivers("POS-126875");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogFifteenDtEighteen stringFifteenDtEighteen = getFifteenDtEighteenString(0, 6);
	BinLogNinetyThreeStr strNintyThree = getNinetyThreeString(0, 3);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 4);

	sftpClientDriver.assertEquals("15th string subtype:", "18", stringFifteenDtEighteen.getSubstringType().toString());
	sftpClientDriver.assertEquals("15th string nbr of installments:", "F5", stringFifteenDtEighteen.getQtyOfIntsallments().toString());
	sftpClientDriver.assertEquals("15th string intrest w/o tax type:", "00", stringFifteenDtEighteen.getIntrestWithoutTax().toString());
	sftpClientDriver.assertEquals("15th string PCI indicator:", "01", stringFifteenDtEighteen.getPciIndicator().toString());
	sftpClientDriver.assertEquals("15th promo id:", "F136", stringFifteenDtEighteen.getPromoId().toString());
	sftpClientDriver.assertEquals("15th promo mark:", "38303030", stringFifteenDtEighteen.getPromoMark().toString());
	sftpClientDriver.assertEquals("15th skip and payment qyt:", "", stringFifteenDtEighteen.getSkipAndPaymentQty().toString());

	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0020", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", "063016", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", "162356", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0018", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "123456", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Tender Amount in 82nd String:", "F14265", strEightyTwo.getTenderAmt().toString());
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

	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BINLOG_ID));
    }

    @Test(groups = { "RT" })
    public void tc03_BR() {
	setCurrentTestCaseInAllDrivers("POS-126888");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogSixStr stringSix = getSixString(0, 1);
	BinLogTenStr stringTen = getTenString(0, 7);

	sftpClientDriver.assertEquals("6th indicator 2:", "F32768", stringSix.getIndicatorTwo().toString());
	sftpClientDriver.assertEquals("10th Discount Amt:", "0000000453", stringTen.getDiscountAmt().toString());
	sftpClientDriver.assertEquals("10th VAT Amt:", "0000000572", stringTen.getDisscountVatAmt().toString());

	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BINLOG_ID));
    }

    @Test(groups = { "RT" })
    public void tc04_BR() {
	setCurrentTestCaseInAllDrivers("POS-126891");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwelveStr stringTwelve = getTwelveString(0, 9);
	BinLogThirteenStr stringThirteen = getThirteenString(0, 10);

	sftpClientDriver.assertEquals("12th tender type:", "99", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("13th card subtype:", "0101", stringThirteen.getCardSubType().toString());
    }

    @Override
    @Test(groups = { "RT" })
    public void tc05() {
	setCurrentTestCaseInAllDrivers("POS-126894");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogSixStr stringSix = getSixString(0, 1);
	BinLogThirtySixStr stringThirtySix = getThirtySixString(0, 2);
	BinLogTwelveStr stringTwelve = getTwelveString(0, 7);

	sftpClientDriver.assertEquals("6th string extented amount:", "F46283", stringSix.getExtendedAmt().toString());
	sftpClientDriver.assertEquals("6th string indicator 0:", "F4", stringSix.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("6th string upc:", "0000000007896584062560", stringSix.getUpcNo().toString());
	sftpClientDriver.assertEquals("12th string tender type:", "08", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("36th string tax 2 rate:", "F165", stringThirtySix.getTaxTwoRatePercent().toString());
	sftpClientDriver.assertEquals("36th string tax 2 amount:", "F842", stringThirtySix.getTaxTwoAmountCharged().toString());
	sftpClientDriver.assertEquals("25th strings created:", 0, getNumberOfStringsByType(0, "25"));
    }

    @Test(groups = { "RT" })
    public void tc06_BR() {
	setCurrentTestCaseInAllDrivers("POS-126929");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwelveStr stringTwelve = getTwelveString(0, 7);
	BinLogThirteenStr stringThirteen = getThirteenString(0, 8);

	sftpClientDriver.assertEquals("12th tender type:", "99", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("13th card subtype:", "0201", stringThirteen.getCardSubType().toString());

	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BINLOG_ID));
    }

    @Test(groups = { "RT" })
    public void tc07_BR() {
	setCurrentTestCaseInAllDrivers("POS-126935");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwelveStr stringTwelve = getTwelveString(0, 7);
	BinLogThirteenStr stringThirteen = getThirteenString(0, 8);

	sftpClientDriver.assertEquals("12th tender type:", "99", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("13th card subtype:", "0100", stringThirteen.getCardSubType().toString());
    }

    @Test(groups = { "RT" })
    public void tc08_BR() {
	setCurrentTestCaseInAllDrivers("POS-126939");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwelveStr stringTwelve = getTwelveString(0, 5);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 4);
	BinLogNinetyThreeStr strNintyThree = getNinetyThreeString(0, 3);

	sftpClientDriver.assertEquals("12th tender type:", "08", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("12th indicator 0:", "F1048577", stringTwelve.getIndicatorZero().toString());

	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0018", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", "040715", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", "090336", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0012", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "00001509", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Tender Amount in 82nd String:", "F950", strEightyTwo.getTenderAmt().toString());
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
    public void tc09_BR() {
	setCurrentTestCaseInAllDrivers("POS-126945");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogSixStr stringSixForDirect = getSixString(0, 1);
	BinLogSixStr stringSixForAcquired = getSixString(0, 3);

	sftpClientDriver.assertEquals("6th direct Import flag:", "1040", stringSixForDirect.getIndicatorThree().toString());
	sftpClientDriver.assertEquals("6th Acquired Import flag:", "1056", stringSixForAcquired.getIndicatorThree().toString());
    }

    @Test(groups = { "RT" })
    public void tc10_BR() {
	setCurrentTestCaseInAllDrivers("POS-126959");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwelveStr stringTwelve = getTwelveString(0, 3);

	sftpClientDriver.assertEquals("12th String Tender type:", "58", stringTwelve.getTenderType().toString());

	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, getRegMsgInterfaceMsgStatusCode(BINLOG_ID));
    }

    @Test(groups = { "RT" })
    public void tc11_BR() {
	setCurrentTestCaseInAllDrivers("POS-126961");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwelveStr stringTwelve = getTwelveString(0, 6);
	BinLogThirteenStr stringThirteen = getThirteenString(0, 7);

	sftpClientDriver.assertEquals("12th tender type:", "99", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("13th card subtype:", "0200", stringThirteen.getCardSubType().toString());
    }

    @Test(groups = { "RT" })
    public void tc12_BR() {
	setCurrentTestCaseInAllDrivers("POS-126963");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwelveStr stringTwelve = getTwelveString(0, 4);

	sftpClientDriver.assertEquals("12th tender type:", "23", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("12th amount:", "0000012368", stringTwelve.getTenderAmt().toString());
    }

    @Test(groups = { "RT" })
    public void tc13_BR() {
	setCurrentTestCaseInAllDrivers("POS-126979");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwentyFourStr strTwentyfour1 = getTwentyFourString(0, 3);
	BinLogTwentyFourStr strTwentyfour2 = getTwentyFourString(0, 7);
	BinLogTwentyFourStr strTwentyfour3 = getTwentyFourString(0, 11);
	BinLogTwentyFourStr strTwentyfour4 = getTwentyFourString(0, 13);

	sftpClientDriver.assertEquals("24th string Type of approval:", "0020", strTwentyfour1.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00010340", strTwentyfour1.getSupervisor().toString());
	sftpClientDriver.assertEquals("24th string Type of approval:", "0025", strTwentyfour4.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00001141", strTwentyfour4.getSupervisor().toString());
	sftpClientDriver.assertEquals("24th string Type of approval:", "0039", strTwentyfour2.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00000001", strTwentyfour2.getSupervisor().toString());
	sftpClientDriver.assertEquals("24th string Type of approval:", "0034", strTwentyfour3.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00000012", strTwentyfour3.getSupervisor().toString());
    }

    @Test(groups = { "RT" })
    public void tc14_BR() {
	setCurrentTestCaseInAllDrivers("POS-126980");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwentyFourStr strTwentyfour1 = getTwentyFourString(0, 3);
	BinLogTwentyFourStr strTwentyfour2 = getTwentyFourString(0, 7);
	BinLogTwentyFourStr strTwentyfour3 = getTwentyFourString(0, 10);
	BinLogTwentyFourStr strTwentyfour4 = getTwentyFourString(0, 14);
	BinLogTwentyFourStr strTwentyfour5 = getTwentyFourString(0, 17);

	sftpClientDriver.assertEquals("24th string Type of approval:", "0006", strTwentyfour1.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00012345", strTwentyfour1.getSupervisor().toString());
	sftpClientDriver.assertEquals("24th string Type of approval:", "0015", strTwentyfour2.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00056789", strTwentyfour2.getSupervisor().toString());
	sftpClientDriver.assertEquals("24th string Type of approval:", "0115", strTwentyfour3.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00003456", strTwentyfour3.getSupervisor().toString());
	sftpClientDriver.assertEquals("24th string Type of approval:", "0014", strTwentyfour4.getTypeOfApproval().toString());
	sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00031256", strTwentyfour4.getSupervisor().toString());
	sftpClientDriver.assertEquals("24th string Type of approval:", "0050", strTwentyfour5.getTypeOfApproval().toString());
    }

    @Test(groups = { "RT" })
    public void tc15_BR() {
	setCurrentTestCaseInAllDrivers("POS-126983");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwelveStr stringTwelve = getTwelveString(0, 7);
	BinLogZeroStr stringZero = getZeroString(0, 0);
	BinLogNinetyThreeStr strNintyThree = getNinetyThreeString(0, 5);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 6);

	sftpClientDriver.assertEquals("0th string indicator 0:", "", stringZero.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("12th string tender type incorrect", "08", stringTwelve.getTenderType().toString());

	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0018", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", "011516", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", "085440", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0096", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "00497465", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Tender Amount in 82nd String:", "1950", strEightyTwo.getTenderAmt().toString());
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
    public void tc16_BR() {
	setCurrentTestCaseInAllDrivers("POS-126965");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogSixStr stringSix = getSixString(0, 1);
	BinLogTwelveStr stringTwelve = getTwelveString(0, 5);

	sftpClientDriver.assertEquals("6th string extented amount:", "3000", stringSix.getExtendedAmt().toString());
	sftpClientDriver.assertEquals("6th string indicator 0:", "F4", stringSix.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("6th string upc:", "0000000000005004309580", stringSix.getUpcNo().toString());
	sftpClientDriver.assertEquals("12th string tender type:", "08", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("25th strings created:", 0, getNumberOfStringsByType(0, "25"));
    }

    @Test(groups = { "RT" })
    public void tc17_BR() {
	setCurrentTestCaseInAllDrivers("POS-126970");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogSixStr stringSix = getSixString(0, 1);
	BinLogTwelveStr stringTwelve = getTwelveString(0, 5);

	sftpClientDriver.assertEquals("6th string extented amount:", "3000", stringSix.getExtendedAmt().toString());
	sftpClientDriver.assertEquals("6th string indicator 0:", "F4", stringSix.getIndicatorZero().toString());
	sftpClientDriver.assertEquals("6th string upc:", "0000000000005004309580", stringSix.getUpcNo().toString());
	sftpClientDriver.assertEquals("12th string tender type:", "08", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("25th strings created:", 0, getNumberOfStringsByType(0, "25"));
    }

    @Test(groups = { "RT" })
    public void tc18_BR() {
	setCurrentTestCaseInAllDrivers("POS-126991");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwelveStr stringTwelve = getTwelveString(0, 5);
	BinLogNinetyThreeStr strNintyThree = getNinetyThreeString(0, 3);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 4);

	sftpClientDriver.assertEquals("12th tender type:", "08", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("12th indicator 0:", "F1048577", stringTwelve.getIndicatorZero().toString());

	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0018", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", "040715", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", "090336", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0104", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "00001509", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Tender Amount in 82nd String:", "F950", strEightyTwo.getTenderAmt().toString());
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
    public void tc19_BR() {
	setCurrentTestCaseInAllDrivers("POS-126995");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwelveStr stringTwelve = getTwelveString(0, 5);
	BinLogNinetyThreeStr strNintyThree = getNinetyThreeString(0, 3);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 4);

	sftpClientDriver.assertEquals("12th tender type:", "08", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("12th indicator 0:", "F1048577", stringTwelve.getIndicatorZero().toString());

	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0018", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", "040715", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", "090336", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0122", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "00001509", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Tender Amount in 82nd String:", "F950", strEightyTwo.getTenderAmt().toString());
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
    public void tc20_BR() {
	setCurrentTestCaseInAllDrivers("POS-126997");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();

	BinLogTwelveStr stringTwelve = getTwelveString(0, 5);
	BinLogNinetyThreeStr strNintyThree = getNinetyThreeString(0, 3);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 4);

	sftpClientDriver.assertEquals("12th tender type:", "08", stringTwelve.getTenderType().toString());
	sftpClientDriver.assertEquals("12th indicator 0:", "F1048577", stringTwelve.getIndicatorZero().toString());

	sftpClientDriver.assertEquals("String Type in 82nd String:", "82", strEightyTwo.getStringType().toString());
	sftpClientDriver.assertEquals("Register in 82nd String:", "11", strEightyTwo.getRegisterNumber().toString());
	sftpClientDriver.assertEquals("Sequence in 82nd String:", "00", strEightyTwo.getSequenceNumber().toString());
	sftpClientDriver.assertEquals("Operator in 82nd String:", "0018", strEightyTwo.getOperatorNumber().toString());
	sftpClientDriver.assertEquals("Date in 82nd String:", "040715", strEightyTwo.getDate().toString());
	sftpClientDriver.assertEquals("Time in 82nd String:", "090336", strEightyTwo.getTime().toString());
	sftpClientDriver.assertEquals("Authorization Type in 82nd String:", "0123", strEightyTwo.getAuthorizationType().toString());
	sftpClientDriver.assertEquals("Approved? in 82nd String:", "01", strEightyTwo.getApprove().toString());
	sftpClientDriver.assertEquals("Approval Code in 82nd String:", "00001509", strEightyTwo.getApprovalCode());
	sftpClientDriver.assertEquals("Auth. Response Code in 82nd String:", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
	sftpClientDriver.assertEquals("Tender Amount in 82nd String:", "F950", strEightyTwo.getTenderAmt().toString());
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
}
