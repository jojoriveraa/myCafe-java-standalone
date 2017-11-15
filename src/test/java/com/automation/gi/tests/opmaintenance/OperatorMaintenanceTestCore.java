package com.automation.gi.tests.opmaintenance;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.automation.common.logger.CerberusLogger;
import com.automation.common.utils.CommonUtils;
import com.automation.gi.tests.GlobalInterfacesTestCore;
import com.walmart.pos.wits.format.operatorMaintenance.OperatorMaintenanceData;
import com.walmart.pos.wits.format.operatorMaintenance.OperatorMaintenanceDataLoader;

public class OperatorMaintenanceTestCore extends GlobalInterfacesTestCore {

    public String getRemoteOperatorDataXmlGzFilePath() {
	return getPOS_OUTBOUND_DIRECTORY() + "/OperatorData.xml.gz";
    }

    public String getRemoteOperatorDataTrigFilePath() {
	return getPOS_OUTBOUND_DIRECTORY() + "/OperatorData.trig";
    }

    public String getLocalOperatorDataXmlGzFilePath() {
	return Paths.get(getTemporaryFolderPath() + "/OperatorData.xml.gz").toAbsolutePath().toString();
    }

    public String getLocalOperatorDataTrigFilePath() {
	return Paths.get(getTemporaryFolderPath() + "/OperatorData.trig").toAbsolutePath().toString();
    }

    public String unzipOperatorData() {
	return CommonUtils.unGzipFile(getLocalOperatorDataXmlGzFilePath());
    }

    public List<Map<String, Object>> getCashOfficeOperatorData() {
	switchToSmartDb();
	CerberusLogger.log("Getting Operators data...");
	List<Map<String, Object>> results = dataBaseDriver.executeNonParameterizedQuery(CASH_OFFICE_OP_MANT);
	resetDataSource();
	return results;
    }

    // DATA PARSERS
    public OperatorMaintenanceData getOperatorMaintenanceData() {
	String filePath = unzipOperatorData();
	try {
	    OperatorMaintenanceDataLoader loader = new OperatorMaintenanceDataLoader(ToStringStyle.MULTI_LINE_STYLE);
	    return loader.getData(filePath);
	} catch (Exception e) {
	    CerberusLogger.log("Error loading file " + filePath + ":", e);
	    return null;
	}
    }
}
