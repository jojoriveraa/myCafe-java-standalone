/**
 * 
 */
package com.automation.gi.tests.binlog.cam;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.common.logger.CerberusLogger;
import com.automation.gi.tests.binlog.BinlogTest;
import com.automation.middleware.CerberusMiddlewareTestListener;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogEightyTwoStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogNinetyThreeStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogSixStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogTwelveStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogTwentyFourStr;

/**
 * @author David Villalobos
 *
 */
@Listeners(CerberusMiddlewareTestListener.class)
public class CAM_BinlogTest extends BinlogTest {
    
    @Test(groups = { "RT" })
    public void tc01_CAM() {
	setCurrentTestCaseInAllDrivers("POS-127003");
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
	BinLogTwentyFourStr strTwentyfour6 = getTwentyFourString(0, 20);
	BinLogTwentyFourStr strTwentyfour7 = getTwentyFourString(0, 23);
	
	sftpClientDriver.assertEquals("24th string Type of approval:", "0006", strTwentyfour1.getTypeOfApproval().toString());
        sftpClientDriver.assertEquals("24th string Type of override:", "01", strTwentyfour1.getTypeOfOverride().toString());
        sftpClientDriver.assertEquals("24th string Supervisor nbr:", "00012345", strTwentyfour1.getSupervisor().toString());
        sftpClientDriver.assertEquals("24th string Supervisor name:", "DDD EEE FFF         ", strTwentyfour1.getSupervisorName());
        sftpClientDriver.assertEquals("24th string Type of approval:", "0021", strTwentyfour2.getTypeOfApproval().toString());
        sftpClientDriver.assertEquals("24th string Type of approval:", "0020", strTwentyfour3.getTypeOfApproval().toString());
        sftpClientDriver.assertEquals("24th string Type of approval:", "0015", strTwentyfour4.getTypeOfApproval().toString());
        sftpClientDriver.assertEquals("24th string Type of approval:", "0036", strTwentyfour5.getTypeOfApproval().toString());
        sftpClientDriver.assertEquals("24th string Type of approval:", "0071", strTwentyfour6.getTypeOfApproval().toString());
        sftpClientDriver.assertEquals("24th string Type of approval:", "0010", strTwentyfour7.getTypeOfApproval().toString());
    }
    
    @Test(groups = { "RT" })
    public void tc02_CAM() {
	setCurrentTestCaseInAllDrivers("POS-127005");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();
	
	BinLogTwentyFourStr strTwentyfour1 = getTwentyFourString(0, 4);
	BinLogTwentyFourStr strTwentyfour2 = getTwentyFourString(0, 8);
	BinLogTwentyFourStr strTwentyfour3 = getTwentyFourString(0, 11);
	BinLogTwentyFourStr strTwentyfour4 = getTwentyFourString(0, 14);
	BinLogTwentyFourStr strTwentyfour5 = getTwentyFourString(0, 17);
	BinLogTwentyFourStr strTwentyfour6 = getTwentyFourString(0, 20);
	BinLogTwentyFourStr strTwentyfour7 = getTwentyFourString(0, 24);
	
	sftpClientDriver.assertEquals("24th string Type of approval:", "0015", strTwentyfour1.getTypeOfApproval().toString());
        sftpClientDriver.assertEquals("24th string Type of approval:", "0080", strTwentyfour2.getTypeOfApproval().toString());
        sftpClientDriver.assertEquals("24th string Type of approval:", "0036", strTwentyfour3.getTypeOfApproval().toString());
        sftpClientDriver.assertEquals("24th string Type of approval:", "0089", strTwentyfour4.getTypeOfApproval().toString());
        sftpClientDriver.assertEquals("24th string Type of approval:", "0108", strTwentyfour5.getTypeOfApproval().toString());
        sftpClientDriver.assertEquals("24th string Type of approval:", "0031", strTwentyfour6.getTypeOfApproval().toString());
        sftpClientDriver.assertEquals("24th string Type of approval:", "0039", strTwentyfour7.getTypeOfApproval().toString());
    }
    
    @Test(groups = { "RT" })
    public void tc03_CAM() {
	setCurrentTestCaseInAllDrivers("POS-127007");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteBinlogFilePath());
	verifyEtlFileExists(getRemoteTlogExtractFilePath());
	getEtlBinlogFile();
	
	BinLogSixStr priceErrorStringSix = getSixString(0, 1);
	BinLogSixStr automotiveStringSix = getSixString(0, 4);
	BinLogSixStr miscStringSix = getSixString(0, 10);
	BinLogSixStr compAdvStringSix = getSixString(0, 7);
	BinLogSixStr promoStringSix = getSixString(0, 16);
	BinLogTwelveStr stringTwelve = getTwelveString(0, 23);
	BinLogNinetyThreeStr strNintyThree = getNinetyThreeString(0, 21);
	BinLogEightyTwoStr strEightyTwo = getEightyTwostring(0, 22);
	
	sftpClientDriver.assertEquals("price override 6th string indicator 0:", "8194", priceErrorStringSix.getIndicatorZero().toString());
        sftpClientDriver.assertEquals("price error in 6th string indicator 1:", "64",  priceErrorStringSix.getIndicatorOne().toString());
        sftpClientDriver.assertEquals("automotive adjustment in  6th string indicator 1:", "16", automotiveStringSix.getIndicatorOne().toString());
        sftpClientDriver.assertEquals("comp adv  in  6th string indicator 1:", "F4", compAdvStringSix.getIndicatorOne().toString());
        sftpClientDriver.assertEquals("misc adv  in  6th string indicator 1:", "32", miscStringSix.getIndicatorOne().toString());
        sftpClientDriver.assertEquals("promotion  6th string indicator 0:", "8194", promoStringSix.getIndicatorZero().toString());
        sftpClientDriver.assertEquals("12th string tender type incorrect", "59", stringTwelve.getTenderType().toString());
        
        sftpClientDriver.assertEquals("String Type in 82nd String is incorrect", "82", strEightyTwo.getStringType().toString());
        sftpClientDriver.assertEquals("Register in 82nd String is incorrect", "11", strEightyTwo.getRegisterNumber().toString());
        sftpClientDriver.assertEquals("Sequence in 82nd String is incorrect", "00", strEightyTwo.getSequenceNumber().toString());
        sftpClientDriver.assertEquals("Operator in 82nd String is incorrect", "0021", strEightyTwo.getOperatorNumber().toString());
        sftpClientDriver.assertEquals("Date in 82nd String is incorrect", "040715", strEightyTwo.getDate().toString());
        sftpClientDriver.assertEquals("Time in 82nd String is incorrect", "090336", strEightyTwo.getTime().toString());
        sftpClientDriver.assertEquals("Authorization Type in 82nd String is incorrect", "0008", strEightyTwo.getAuthorizationType().toString());
        sftpClientDriver.assertEquals("Approved? in 82nd String is incorrect", "01", strEightyTwo.getApprove().toString());
        sftpClientDriver.assertEquals("Approval Code in 82nd String is incorrect", "00001509", strEightyTwo.getApprovalCode());
        sftpClientDriver.assertEquals("Auth. Response Code in 82nd String is incorrect", "0001", strEightyTwo.getAuthorizationStatusUS().toString());
        sftpClientDriver.assertEquals("Tender Amount in 82nd String is incorrect", "185500", strEightyTwo.getTenderAmt().toString());
        sftpClientDriver.assertEquals("Account Number in 82nd String is incorrect", strNintyThree.getPciKey().toString(),
                                                                    strEightyTwo.getAccountNumber().toString());
        sftpClientDriver.assertEquals("MICR ABA Number in 82nd String is incorrect", "", strEightyTwo.getMicrAbaNumber().toString());
        sftpClientDriver.assertEquals("Transaction Id in 82nd String is incorrect", "0058", strEightyTwo.getTransactionId().toString());
        sftpClientDriver.assertEquals("Network Id in 82nd String is incorrect", "", strEightyTwo.getNetworkId());
        sftpClientDriver.assertEquals("Gateway Ref in 82nd String is incorrect", "615700067374", strEightyTwo.getGatewayRefNumber());
        sftpClientDriver.assertEquals("Auth Requestor in 82nd String is incorrect", "01", strEightyTwo.getTransactionOriginator().toString());
        sftpClientDriver.assertEquals("Swipe Indicator in 82nd String is incorrect", "00", strEightyTwo.getSwipeInd().toString());
        sftpClientDriver.assertEquals("Account Type in 82nd String is incorrect", "0000", strEightyTwo.getAccountType().toString());
        sftpClientDriver.assertEquals("Host Response Time in 82nd String is incorrect", "001373", strEightyTwo.getHostResponseTime().toString());
        sftpClientDriver.assertEquals("Transaction Type in 82nd String is incorrect", "01", strEightyTwo.getTransactionType().toString());
        sftpClientDriver.assertEquals("Event Flags in 82nd String is incorrect", "0000", strEightyTwo.getEventFlags().toString());
        sftpClientDriver.assertEquals("Bin Length in 82nd String is incorrect", "0000", strEightyTwo.getBinLength().toString());
        sftpClientDriver.assertEquals("Bin Type in 82nd String is incorrect", "F0", strEightyTwo.getBinType().toString());
        sftpClientDriver.assertEquals("Debt Cash back Amt in 82nd String is incorrect", "00000000", strEightyTwo.getDebitCashBackAmt().toString());
        sftpClientDriver.assertEquals("Use PCI String in 82nd String is incorrect", "01", strEightyTwo.getUsePciString().toString());
        sftpClientDriver.assertEquals("Authorization Path in 82nd String is incorrect", "00", strEightyTwo.getAuthorizationPathTaken().toString());
        sftpClientDriver.assertEquals("Original Tender Amount in 82nd String is incorrect", "0000", strEightyTwo.getOriginalTenderAmt().toString());
        sftpClientDriver.assertEquals("External Wallet Id in 82nd String is incorrect", "", strEightyTwo.getExternalWalletId());
        sftpClientDriver.assertEquals("External Trans Id in 82nd String is incorrect", "", strEightyTwo.getExternalTransId());
        sftpClientDriver.assertEquals("External Error Code in 82nd String is incorrect", "F0", strEightyTwo.getExternalErrorCode().toString());
    }
}
