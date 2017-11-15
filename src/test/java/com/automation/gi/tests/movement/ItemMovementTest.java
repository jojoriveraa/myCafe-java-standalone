package com.automation.gi.tests.movement;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.common.logger.CerberusLogger;
import com.automation.middleware.CerberusMiddlewareTestListener;
import com.walmart.pos.wits.format.tranxmov.TransactionMovementData;

@Listeners(CerberusMiddlewareTestListener.class)
public class ItemMovementTest extends ItemMovementTestCore {

    @Test
    public void POS_116055() {
	//DUMMY TC ID, CHANGE WHEN REAL EXIST
	setCurrentTestCaseInAllDrivers("POS-116055");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");
	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());

	verifyEtlFileExists(getRemoteMovementFilePath());
	getEtlFile(getRemoteMovementFilePath(), sftpClientDriver.getCurrentTestCase());

	TransactionMovementData data = getMovementData();
	CerberusLogger.log("" + data.getDetails().get(0).getItemFilePrice());
    }
}
