package com.automation.gi.tests.binlog;

import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringStyle;
import org.testng.annotations.BeforeClass;

import com.automation.common.logger.CerberusLogger;
import com.automation.gi.tests.GlobalInterfacesTestCore;
import com.walmart.pos.wits.format.binlog.BinLogDataLoader;
import com.walmart.pos.wits.format.binlog.BinLogDetails;
import com.walmart.pos.wits.format.binlog.BinLogTransactions;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogEightyTwoStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogFourteenStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogNinetyThreeStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogSevenStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogSixStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogTenStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogThirteenStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogThirtySixStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogThirtyTwoStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogTwelveStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogTwentyFiveStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogTwentyFourStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.BinLogZeroStr;
import com.walmart.pos.wits.format.binlog.binlogstrings.binlogfifteenstrings.BinLogFifteenDtEighteen;
import com.walmart.pos.wits.format.binlog.binlogstrings.binlogfifteenstrings.BinLogFifteenDtEleven;
import com.walmart.pos.wits.format.binlog.binlogstrings.binlogfifteenstrings.BinLogFifteenDtFiftyOne;

public class BinlogTestCore extends GlobalInterfacesTestCore {

    private String filePath = "";
    private BinLogTransactions binlogDetails;

    @BeforeClass(alwaysRun = true)
    public void binlogSetUpBeforeClass() {
	setInterfaceName("binlog");
    }

    public void getEtlBinlogFile() {
	getEtlFile(getRemoteBinlogFilePath());
    }

    public void getEtlTlogExtractFile() {
	getEtlFile(getRemoteTlogExtractFilePath());
    }

    public String getCurrentBinlogFileName() {
	return "Binlog_" + getFilesBusinessDate() + ".tmp";
    }

    public String getCurrentTlogExtractFileName() {
	return "TlogExtract_" + getFilesBusinessDate() + ".tmp";
    }

    public String getRemoteBinlogFilePath() {
	return getWORK_MOVEMENT_AND_TLOG_DIRECTORY() + "/" + getCurrentBinlogFileName();
    }

    public String getRemoteTlogExtractFilePath() {
	return getWORK_MOVEMENT_AND_TLOG_DIRECTORY() + "/" + getCurrentTlogExtractFileName();
    }

    public String getLocalBinlogFilePath() {
	return Paths.get(getTemporaryFolderPath() + "/" + getCurrentBinlogFileName()).toAbsolutePath().toString();
    }

    public String getLocatTlogExtractFilePath() {
	return Paths.get(getTemporaryFolderPath() + "/" + getCurrentTlogExtractFileName()).toAbsolutePath().toString();
    }

    // Data Parsers

    private boolean dataInitialized(String previousFilePath, String currentFilePath) {
	return previousFilePath.equals(currentFilePath);
    }

    private void loadDataIfNecessary() {
	try {
	    if (!dataInitialized(filePath, getLocalBinlogFilePath())) {
		filePath = getLocalBinlogFilePath();
		BinLogDataLoader data = new BinLogDataLoader(ToStringStyle.MULTI_LINE_STYLE);
		binlogDetails = data.getData(filePath);
	    }
	} catch (Exception e) {
	    CerberusLogger.log("Error loading Binlog Data: ", e);
	}
    }

    public int getNumberOfStringsByType(int detailPosition, String stringType) {
	loadDataIfNecessary();
	List<BinLogDetails> strings = binlogDetails.getDetails().get(detailPosition).getStringDetails();
	strings.removeIf(x -> x.getStringType().toString() != stringType);
	return strings.size();
    }

    public BinLogZeroStr getZeroString(int detailPosition, int stringPosition) {
	try {
	    loadDataIfNecessary();
	    return (BinLogZeroStr) binlogDetails.getDetails().get(detailPosition).getStringDetails().get(stringPosition);
	} catch (Exception e) {
	    CerberusLogger.log("Error getting Zero String: ", e);
	    return null;
	}
    }

    public BinLogSixStr getSixString(int detailPosition, int stringPosition) {
	try {
	    loadDataIfNecessary();
	    return (BinLogSixStr) binlogDetails.getDetails().get(detailPosition).getStringDetails().get(stringPosition);
	} catch (Exception e) {
	    CerberusLogger.log("Error getting Six String: ", e);
	    return null;
	}
    }

    public BinLogSevenStr getSevenString(int detailPosition, int stringPosition) {
	try {
	    loadDataIfNecessary();
	    return (BinLogSevenStr) binlogDetails.getDetails().get(detailPosition).getStringDetails().get(stringPosition);
	} catch (Exception e) {
	    CerberusLogger.log("Error getting Seven String: ", e);
	    return null;
	}
    }

    public BinLogTenStr getTenString(int detailPosition, int stringPosition) {
	try {
	    loadDataIfNecessary();
	    return (BinLogTenStr) binlogDetails.getDetails().get(detailPosition).getStringDetails().get(stringPosition);
	} catch (Exception e) {
	    CerberusLogger.log("Error getting Ten String: ", e);
	    return null;
	}
    }

    public BinLogTwelveStr getTwelveString(int detailPosition, int stringPosition) {
	try {
	    loadDataIfNecessary();
	    return (BinLogTwelveStr) binlogDetails.getDetails().get(detailPosition).getStringDetails().get(stringPosition);
	} catch (Exception e) {
	    CerberusLogger.log("Error getting Twelve String: ", e);
	    return null;
	}
    }

    public BinLogThirteenStr getThirteenString(int detailPosition, int stringPosition) {
	try {
	    loadDataIfNecessary();
	    return (BinLogThirteenStr) binlogDetails.getDetails().get(detailPosition).getStringDetails().get(stringPosition);
	} catch (Exception e) {
	    CerberusLogger.log("Error getting Thirteen String: ", e);
	    return null;
	}
    }

    public BinLogFourteenStr getFourTeenString(int detailPosition, int stringPosition) {
	try {
	    loadDataIfNecessary();
	    return (BinLogFourteenStr) binlogDetails.getDetails().get(detailPosition).getStringDetails().get(stringPosition);
	} catch (Exception e) {
	    CerberusLogger.log("Error getting Fourteen String: ", e);
	    return null;
	}
    }

    public BinLogFifteenDtEleven getFifteenDtElevenString(int detailPosition, int stringPosition) {
	try {
	    loadDataIfNecessary();
	    return (BinLogFifteenDtEleven) binlogDetails.getDetails().get(detailPosition).getStringDetails().get(stringPosition);
	} catch (Exception e) {
	    CerberusLogger.log("Error getting Fifteen Dt Eleven String: ", e);
	    return null;
	}
    }

    public BinLogFifteenDtEighteen getFifteenDtEighteenString(int detailPosition, int stringPosition) {
	try {
	    loadDataIfNecessary();
	    return (BinLogFifteenDtEighteen) binlogDetails.getDetails().get(detailPosition).getStringDetails().get(stringPosition);
	} catch (Exception e) {
	    CerberusLogger.log("Error getting Fifteen Dt Eighteen String: ", e);
	    return null;
	}
    }

    public BinLogFifteenDtFiftyOne getFifteenDtFiftyOneString(int detailPosition, int stringPosition) {
	try {
	    loadDataIfNecessary();
	    return (BinLogFifteenDtFiftyOne) binlogDetails.getDetails().get(detailPosition).getStringDetails().get(stringPosition);
	} catch (Exception e) {
	    CerberusLogger.log("Error getting Fifteen Dt Fifty One String: ", e);
	    return null;
	}
    }

    public BinLogTwentyFourStr getTwentyFourString(int detailPosition, int stringPosition) {
	try {
	    loadDataIfNecessary();
	    return (BinLogTwentyFourStr) binlogDetails.getDetails().get(detailPosition).getStringDetails().get(stringPosition);
	} catch (Exception e) {
	    CerberusLogger.log("Error getting Twenty Four String: ", e);
	    return null;
	}
    }

    public BinLogTwentyFiveStr getTwentyFiveString(int detailPosition, int stringPosition) {
	try {
	    loadDataIfNecessary();
	    return (BinLogTwentyFiveStr) binlogDetails.getDetails().get(detailPosition).getStringDetails().get(stringPosition);
	} catch (Exception e) {
	    CerberusLogger.log("Error getting Twenty Five String: ", e);
	    return null;
	}
    }

    public BinLogThirtyTwoStr getThirtyTwoString(int detailPosition, int stringPosition) {
	try {
	    loadDataIfNecessary();
	    return (BinLogThirtyTwoStr) binlogDetails.getDetails().get(detailPosition).getStringDetails().get(stringPosition);
	} catch (Exception e) {
	    CerberusLogger.log("Error getting Thirty Two String: ", e);
	    return null;
	}
    }

    public BinLogThirtySixStr getThirtySixString(int detailPosition, int stringPosition) {
	try {
	    loadDataIfNecessary();
	    return (BinLogThirtySixStr) binlogDetails.getDetails().get(detailPosition).getStringDetails().get(stringPosition);
	} catch (Exception e) {
	    CerberusLogger.log("Error getting Thirty Six String: ", e);
	    return null;
	}
    }

    public BinLogEightyTwoStr getEightyTwostring(int detailPosition, int stringPosition) {
	try {
	    loadDataIfNecessary();
	    return (BinLogEightyTwoStr) binlogDetails.getDetails().get(detailPosition).getStringDetails().get(stringPosition);
	} catch (Exception e) {
	    CerberusLogger.log("Error getting Eighty Two String: ", e);
	    return null;
	}
    }

    public BinLogNinetyThreeStr getNinetyThreeString(int detailPosition, int stringPosition) {
	try {
	    loadDataIfNecessary();
	    return (BinLogNinetyThreeStr) binlogDetails.getDetails().get(detailPosition).getStringDetails().get(stringPosition);
	} catch (Exception e) {
	    CerberusLogger.log("Error getting Ninty Three String: ", e);
	    return null;
	}
    }
}
