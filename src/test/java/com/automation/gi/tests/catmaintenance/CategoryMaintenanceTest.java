package com.automation.gi.tests.catmaintenance;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.common.logger.CerberusLogger;
import com.automation.middleware.CerberusMiddlewareTestListener;
import com.walmart.pos.wits.format.categoryMaintenance.CategoryMaintenanceData;

@Listeners(CerberusMiddlewareTestListener.class)
public class CategoryMaintenanceTest extends CategoryMaintenanceTestCore {

    @Test
    public void POS_109423() {
	//DUMMY TC ID, CHANGE WHEN REAL EXIST
	setCurrentTestCaseInAllDrivers("POS-109423");
	CerberusLogger.log("******************************************** " + sftpClientDriver.getCurrentTestCase() + " ******************************************");

	httpClientDriver.assertEquals("Front-End request status: ", STATUS_200, getStatusResponse());
	verifyEtlFileExists(getRemoteCategoryDataXmlGzFilePath());
	getEtlFile(getRemoteCategoryDataXmlGzFilePath(), sftpClientDriver.getCurrentTestCase());

	CategoryMaintenanceData data = getCategoryMaintenanceData();
	int lastIndexOfData = data.getOffering().size() - 1;

	List<Map<String, Object>> categoryMaintenanceDataList = getCisamItemFileCategoryMant();
	int lastIndexOfList = categoryMaintenanceDataList.size() - 1;

	Map<String, Object> dbRecord = getBatchStoreInterfaceByInterfaceId(CATEGORY_MAINTENANCE_ID);

	sftpClientDriver.assertEquals("Total records in db and file:", data.getOffering().size(), categoryMaintenanceDataList.size());

	// First Record
	sftpClientDriver.assertEquals("First Offering id: ", (int) categoryMaintenanceDataList.get(0).get(ITEM_NBR), data.getOffering().get(0).getId());
	sftpClientDriver.assertEquals("First Offering trade item:", categoryMaintenanceDataList.get(0).get(UCODE).toString(), data.getOffering().get(0).getGlobalTradeItem().getGtin());
	sftpClientDriver.assertEquals("First Offering category group number", (int) categoryMaintenanceDataList.get(0).get(DEPT_CATG_GRP_NBR), data.getOffering().get(0).getCategoryGroup().getNumber());
	sftpClientDriver.assertEquals("First Offering category number:", (int) categoryMaintenanceDataList.get(0).get(DEPT_CATEGORY_NBR), data.getOffering().get(0).getCategoryGroup().getCategory().getNumber());
	sftpClientDriver.assertEquals("First Offering sub-category number:", (int) categoryMaintenanceDataList.get(0).get(DEPT_SUBCATG_NBR), data.getOffering().get(0).getCategoryGroup().getCategory().getSubcategory().getNumber());

	// Last Record
	sftpClientDriver.assertEquals("Last Offering id: ", (int) categoryMaintenanceDataList.get(lastIndexOfList).get(ITEM_NBR), data.getOffering().get(lastIndexOfData).getId());
	sftpClientDriver.assertEquals("Last Offering trade item:", categoryMaintenanceDataList.get(lastIndexOfList).get(UCODE).toString(), data.getOffering().get(lastIndexOfData).getGlobalTradeItem().getGtin());
	sftpClientDriver.assertEquals("Last Offering category group number", (int) categoryMaintenanceDataList.get(lastIndexOfList).get(DEPT_CATG_GRP_NBR), data.getOffering().get(lastIndexOfData).getCategoryGroup().getNumber());
	sftpClientDriver.assertEquals("Last Offering category number:", (int) categoryMaintenanceDataList.get(lastIndexOfList).get(DEPT_CATEGORY_NBR), data.getOffering().get(lastIndexOfData).getCategoryGroup().getCategory().getNumber());
	sftpClientDriver.assertEquals("Last Offering sub-category number:", (int) categoryMaintenanceDataList.get(lastIndexOfList).get(DEPT_SUBCATG_NBR),
		data.getOffering().get(lastIndexOfData).getCategoryGroup().getCategory().getSubcategory().getNumber());

	sftpClientDriver.assertEquals("Database message status code: ", STATUS_ELEVEN_THOUSAND, (short) dbRecord.get(MESSAGE_STATUS_CODE));
    }
}
