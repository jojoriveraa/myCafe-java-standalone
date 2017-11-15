package com.automation.gi.tests.taxmaintenance;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.common.logger.CerberusLogger;
import com.automation.middleware.CerberusMiddlewareTestListener;

@Listeners(CerberusMiddlewareTestListener.class)
public class TaxMaintenanceTest extends TaxMaintenanceTestCore {

    @Test
    public void POS_109418() {
	//DUMMY TC ID, CHANGE WHEN REAL EXIST
	setCurrentTestCaseInAllDrivers("POS-109418");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	copyLocalFilesInEtl(get_D_ADX_IDT1_DIRECTORY(), getPreconditionFolder(sftpClientDriver.getCurrentTestCase()).listFiles());
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteTaxCodeFilePath("1"));
	sftpClientDriver.assertEquals("Files in directory:", 10, getRemoteEtlDirectory(getPOS_OUTBOUND_DIRECTORY()).listFiles().length);
    }
}
