package com.automation.gi.tests.movement;

import java.nio.file.Paths;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.automation.common.logger.CerberusLogger;
import com.automation.gi.tests.GlobalInterfacesTestCore;
import com.walmart.pos.wits.format.tranxmov.TransactionMovementData;
import com.walmart.pos.wits.format.tranxmov.TransactionMovementDataLoader;

public class ItemMovementTestCore extends GlobalInterfacesTestCore {

    public String getRemoteMovementFilePath() {
	return getWORK_MOVEMENT_AND_TLOG_DIRECTORY() + "/Movement_" + getFilesBusinessDate() + ".tmp";
    }

    public String getLocalMovementFilePath() {
	return Paths.get(getTemporaryFolderPath() + "/Movement_" + getFilesBusinessDate() + ".tmp").toAbsolutePath().toString();
    }

    // DATA PARSERS

    public TransactionMovementData getMovementData() {
	String filePath = getLocalMovementFilePath();
	try {
	    TransactionMovementDataLoader dataLoader = new TransactionMovementDataLoader(ToStringStyle.MULTI_LINE_STYLE);
	    return dataLoader.getData(filePath);
	} catch (Exception e) {
	    CerberusLogger.log("Error loading file " + filePath + ":", e);
	    return null;
	}
    }
}
