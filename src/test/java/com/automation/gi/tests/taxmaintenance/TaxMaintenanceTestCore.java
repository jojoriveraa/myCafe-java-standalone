package com.automation.gi.tests.taxmaintenance;

import java.nio.file.Paths;

import org.apache.commons.lang3.builder.ToStringStyle;

import com.automation.common.logger.CerberusLogger;
import com.automation.gi.tests.GlobalInterfacesTestCore;
import com.walmart.pos.wits.format.taxmaintenance.TaxMaintenanceData;
import com.walmart.pos.wits.format.taxmaintenance.TaxMaintenanceDataLoader;

public class TaxMaintenanceTestCore extends GlobalInterfacesTestCore {

    public void verifyRequiredDirectories() {
	if (!sftpClientDriver.remoteDirectoryExists(getPOS_INBOUND_DIRECTORY(), getEtlProfile())) {
	    createEtlDirectory(getPOS_INBOUND_DIRECTORY());
	    changeFileOrDirectoryPermissions(getPOS_INBOUND_DIRECTORY(), ALL_PERMISSIONS);
	}
	if (!sftpClientDriver.remoteDirectoryExists(getPOS_OUTBOUND_DIRECTORY(), getEtlProfile())) {
	    createEtlDirectory(getPOS_OUTBOUND_DIRECTORY());
	    changeFileOrDirectoryPermissions(getPOS_OUTBOUND_DIRECTORY(), ALL_PERMISSIONS);
	}
	if (!sftpClientDriver.remoteDirectoryExists(get_C_ADX_IDT1_DIRECTORY(), getEtlProfile())) {
	    createEtlDirectory(get_C_ADX_IDT1_DIRECTORY());
	    changeFileOrDirectoryPermissions(get_C_ADX_IDT1_DIRECTORY(), ALL_PERMISSIONS);
	}
	if (!sftpClientDriver.remoteDirectoryExists(getARCHIVE_TAX_MAINTENANCE_DIRECTORY(), getEtlProfile())) {
	    createEtlDirectory(getARCHIVE_TAX_MAINTENANCE_DIRECTORY());
	    changeFileOrDirectoryPermissions(getARCHIVE_TAX_MAINTENANCE_DIRECTORY(), ALL_PERMISSIONS);
	}
	if (!sftpClientDriver.remoteDirectoryExists(getWORK_TAX_MAINTENEANCE_DIRECTORY(), getEtlProfile())) {
	    createEtlDirectory(getWORK_TAX_MAINTENEANCE_DIRECTORY());
	    changeFileOrDirectoryPermissions(getWORK_TAX_MAINTENEANCE_DIRECTORY(), ALL_PERMISSIONS);
	}
    }

    public String getRemoteEALGTX12FilePath(String taxNumber) {
	return getARCHIVE_TAX_MAINTENANCE_DIRECTORY() + "/EALGTX#.INP.corrupted".replace("#", String.valueOf(taxNumber));
    }

    public String getRemoteTaxCodeFilePath(String taxNumber) {
	return getPOS_OUTBOUND_DIRECTORY() + "/TaxCode#.xml".replace("#", String.valueOf(taxNumber));
    }

    public String getLocalEALGTX12FilePath(String taxNumber) {
	return Paths.get(getTemporaryFolderPath() + "/EALGTX#.INP.corrupted").toAbsolutePath().toString().replace("#", String.valueOf(taxNumber));
    }

    public String getLocalTaxCodeFilePath(String taxNumber) {
	return Paths.get(getTemporaryFolderPath() + "/TaxCode#.xml").toAbsolutePath().toString().replace("#", String.valueOf(taxNumber));
    }

    // DATA PARSERS
    public TaxMaintenanceData getTaxMaintenanceData(String taxNumber) {
	String taxFilePath = getLocalTaxCodeFilePath(taxNumber);
	try {
	    TaxMaintenanceDataLoader dataLoader = new TaxMaintenanceDataLoader(ToStringStyle.MULTI_LINE_STYLE);
	    return dataLoader.getData(taxFilePath);
	} catch (Exception e) {
	    CerberusLogger.log("Error loading file " + taxFilePath + ".", e);
	    return null;
	}
    }
}
