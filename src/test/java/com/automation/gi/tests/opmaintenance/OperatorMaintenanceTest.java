package com.automation.gi.tests.opmaintenance;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.common.logger.CerberusLogger;
import com.automation.middleware.CerberusMiddlewareTestListener;
import com.walmart.pos.wits.format.operatorMaintenance.OperatorMaintenanceData;

@Listeners(CerberusMiddlewareTestListener.class)
public class OperatorMaintenanceTest extends OperatorMaintenanceTestCore {

    @Test
    public void POS_109422() {
	//DUMMY TC ID, CHANGE WHEN REAL EXIST
	setCurrentTestCaseInAllDrivers("POS-109422");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");

	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteOperatorDataXmlGzFilePath());
	getEtlFile(getRemoteOperatorDataXmlGzFilePath(), sftpClientDriver.getCurrentTestCase());
	verifyEtlFileExists(getRemoteOperatorDataTrigFilePath());

	OperatorMaintenanceData data = getOperatorMaintenanceData();

	List<Map<String, Object>> omList = getCashOfficeOperatorData();
	Map<String, Object> dbRecord = getBatchStoreInterfaceByInterfaceId(OPERATOR_MAINTENANCE_ID);

	sftpClientDriver.assertEquals("Access level description :", data.getOperators().get(0).getAccesslLevel().getName(), omList.get(0).get(OPR_SECUR_LVL_DESC).toString());
	sftpClientDriver.assertEquals("Access level description:", data.getOperators().get(data.getOperators().size() - 1).getAccesslLevel().getName(), omList.get(omList.size() - 1).get(OPR_SECUR_LVL_DESC));
	sftpClientDriver.assertEquals("Matching number of records in XML and DB:", data.getOperators().size(), omList.size());
	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, (short) dbRecord.get(MESSAGE_STATUS_CODE));
    }
}
