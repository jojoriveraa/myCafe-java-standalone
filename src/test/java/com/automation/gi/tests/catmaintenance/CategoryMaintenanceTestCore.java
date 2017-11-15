package com.automation.gi.tests.catmaintenance;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.automation.common.logger.CerberusLogger;
import com.automation.common.utils.CommonUtils;
import com.automation.gi.tests.GlobalInterfacesTestCore;
import com.walmart.pos.wits.format.categoryMaintenance.CategoryMaintenanceData;
import com.walmart.pos.wits.format.categoryMaintenance.CategoryMaintenanceDataLoader;

public class CategoryMaintenanceTestCore extends GlobalInterfacesTestCore {

    public String getRemoteCategoryDataXmlGzFilePath() {
	return getPOS_OUTBOUND_DIRECTORY() + "/CategoryMaintenance.xml.gz";
    }

    public String getLocalCategoryDataXmlGzFilePath() {
	return Paths.get(getTemporaryFolderPath() + "/CategoryMaintenance.xml.gz").toAbsolutePath().toString();
    }

    public String unzipCategoryData() {
	return CommonUtils.unGzipFile(getLocalCategoryDataXmlGzFilePath());
    }

    public List<Map<String, Object>> getCisamItemFileCategoryMant() {
	switchToSmartDb();
	CerberusLogger.log("Getting category data...");
	List<Map<String, Object>> results = dataBaseDriver.executeNonParameterizedQuery(CISAM_ITEM_FILE_CAT_MANT);
	resetDataSource();
	return results;
    }

    // DATA PARSERS
    public CategoryMaintenanceData getCategoryMaintenanceData() {
	String filePath = unzipCategoryData();
	try {
	    CategoryMaintenanceDataLoader dataLoader = new CategoryMaintenanceDataLoader(ToStringStyle.MULTI_LINE_STYLE);
	    return dataLoader.getData(filePath);
	} catch (Exception e) {
	    CerberusLogger.log("Error loading file " + filePath + ":", e);
	    return null;
	}

    }
}
