package com.automation.gi.tests.eodmonitor;

import java.io.File;
import java.util.List;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.common.logger.CerberusLogger;
import com.automation.middleware.CerberusMiddlewareTestListener;

@Listeners(CerberusMiddlewareTestListener.class)
public class EndOfDayMonitorTest extends EndOfDayMonitorTestCore {

    @Test
    public void POS_109419() {
	//DUMMY TC ID, CHANGE WHEN REAL EXIST
	setCurrentTestCaseInAllDrivers("POS-109419");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	List<File> sendFiles = getCurrentXmlRequests();
	httpClientDriver.assertEquals("Front-End request status for Dept.Sales: ", STATUS_200, getStatusResponse(sendFiles.get(0)));
	sftpClientDriver.assertEquals("Front-End request status for Reg.Sales: ", STATUS_200, getStatusResponse(sendFiles.get(1)));
	sftpClientDriver.assertEquals("Front-End request status for ITax: ", STATUS_200, getStatusResponse(sendFiles.get(2)));
	sftpClientDriver.assertEquals("Front-End request status for End of day: ", STATUS_200, getStatusResponse(sendFiles.get(3)));
	verifyEtlFileExists(getWORK_END_OF_DAY_DIRECTORY(), "Binlog_" + getFilesBusinessDate() + "\\.processed\\d{14}_\\d{8}\\.gz");
	sftpClientDriver.assertEquals("Front-End request status for On Demmand EOD Monitor: ", STATUS_200, getStatusResponse(sendFiles.get(4)));

	verifyEtlFileExists(get_C_ADX_IDT1_DIRECTORY(), "BC00\\d{4}\\.ISP");
	verifyEtlFileExists(getARCHIVE_END_OF_DAY_DIRECTORY(), "Binlog_" + getFilesBusinessDate() + "\\.processed\\d{14}_\\d{8}\\.gz\\.processed");
	verifyEtlFileExists(get_C_ADX_IDT1_DIRECTORY() + "/" + "EALISP20.DAT");
	verifyEtlFileExists(get_C_ADX_IDT1_DIRECTORY() + "/" + "EALISP01.DAT");
    }
}
