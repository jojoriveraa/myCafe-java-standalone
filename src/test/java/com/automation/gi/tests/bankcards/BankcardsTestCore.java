/**
 * 
 */
package com.automation.gi.tests.bankcards;

import java.io.File;
import java.io.FileFilter;
import java.nio.file.Paths;
import java.util.List;

import org.testng.annotations.BeforeClass;

import com.automation.common.logger.CerberusLogger;
import com.automation.gi.tests.GlobalInterfacesTestCore;
import com.walmart.pos.wits.format.bankcard.BankcardData;
import com.walmart.pos.wits.format.bankcard.BankcardDataLoader;
import com.walmart.pos.wits.format.bankcard.BankcardDetail;

/**
 * @author ddv000b
 *
 */
public class BankcardsTestCore extends GlobalInterfacesTestCore {

    String bankcardIspFileName = "";

    @BeforeClass(alwaysRun = true)
    public void bankcardsSetUpBeforeClass() {
	setInterfaceName("bankcards");
    }

    public String getCurrentBankcardFileName() {
	return "Bankcard_" + getFilesBusinessDate() + ".tmp";
    }

    public String getRemoteBankcardFilePath() {
	return getWORK_BANKCARD_DIRECTORY() + "/" + getCurrentBankcardFileName();
    }

    public String getLocalBankcardFilePath() {
	return Paths.get(getTemporaryFolderPath() + "/" + getCurrentBankcardFileName()).toAbsolutePath().toString();
    }

    public void getEtlBankcardFile() {
	getEtlFile(getRemoteBankcardFilePath());
    }

    public String getLocalIspBankcardFilePath() {
	FileFilter filter = ((File pathname) -> {
	    return pathname.getName().endsWith(".ISP");
	});
	File ispFile = new File(getTemporaryFolderPath()).listFiles(filter)[0];
	return ispFile.getAbsolutePath().toString();
    }

    public void getEtlIspBankcardFile() {
	getEtlFile(getWORK_END_OF_DAY_DIRECTORY(), "BC00\\d{4}_\\d{8}.ISP");
    }

    // Data Parsers

    public BankcardData getBankcardData() {
	try {
	    BankcardDataLoader loader = new BankcardDataLoader();
	    return loader.getData(getLocalBankcardFilePath());
	} catch (Exception e) {
	    CerberusLogger.log("Error reading bankcard file. ", e);
	    return null;
	}
    }

    public BankcardData getIspBankcardData() {
	try {
	    BankcardDataLoader loader = new BankcardDataLoader();
	    return loader.getData(getLocalIspBankcardFilePath());
	} catch (Exception e) {
	    CerberusLogger.log("Error reading ISP bankcard file. ", e);
	    return null;
	}
    }

    public long getTotalTransAmount(List<BankcardDetail> details) {	
	long total = 0;
	for(BankcardDetail detail : details) {
	    total += Long.valueOf(detail.getTransAmount());
	}
	return total;
    }

    public long getTotalCashBackAmount(List<BankcardDetail> details) {
	long total = 0;
	for(BankcardDetail detail : details) {
	    total += Long.valueOf(detail.getCashBackAmount());
	}
	return total;
    }
}
