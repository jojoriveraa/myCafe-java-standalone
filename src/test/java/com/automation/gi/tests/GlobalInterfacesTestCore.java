package com.automation.gi.tests;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.automation.common.logger.CerberusLogger;
import com.automation.common.utils.CommonUtils;
import com.automation.gi.tests.common.Constants;
import com.automation.gi.tests.common.Queries;
import com.automation.middleware.CerberusMiddlewareTestCore;
import com.automation.middleware.sftp.CerberusSftpProfile;

/**
 * Core class for Global Interfaces project
 * 
 * @author David Villalobos
 *
 */
public class GlobalInterfacesTestCore extends CerberusMiddlewareTestCore implements Constants, Queries {

    protected String currentEndDateTime;
    private String storeNumber;
    private String countryCode;
    private String interfaceName;

    public void setInterfaceName(String interfaceName) {
	this.interfaceName = interfaceName;
    }

    /**
     * GENERAL METHODS
     */

    @Parameters({ "storeNumber", "countryCode" })
    @BeforeClass(alwaysRun = true)
    public void globalInterfacesSetupBeforeClass(@Optional("") String storeNumber, @Optional("") String countryCode) {
	if (StringUtils.isNotEmpty(storeNumber))
	    this.storeNumber = storeNumber;
	else
	    this.storeNumber = getProperty(STORE_NUMBER);

	if (StringUtils.isNoneEmpty(countryCode))
	    this.countryCode = countryCode;
	else
	    this.countryCode = getProperty(COUNTRY_CODE);

	CerberusLogger.log("********************************** PREPAIRING STORE " + getCountryCode() + "-" + getStoreNumber() + " *********************************");
	cleanUpStoreDB();
	cleanUpStoreEtl();
	CerberusLogger.log("************************************* STORE " + getCountryCode() + "-" + getStoreNumber() + " READY ***********************************\n");
    }

    @BeforeMethod(alwaysRun = true)
    public void setupBeforeMethod() {
	currentEndDateTime = CommonUtils.getDateTimeFromPattern(END_DATE_TIME_FORMAT, new Date());
    }

    @AfterMethod(alwaysRun = true)
    public void setupAfterMethod() {
	CerberusLogger.log("***************************************** CLEANING DATA *******************************************");
	cleanUpStoreDB();
	cleanUpStoreEtl();
	cleanupTemporaryFiles();
	CerberusLogger.log("*************************************** CLEANING FINISHED *****************************************");
    }

    public short getRegMsgInterfaceMsgStatusCode(int interfaceId) {
	Map<String, Object> record = getRegisterMessageInterfaceByInterfaceId(interfaceId);
	return record.get(MESSAGE_STATUS_CODE) != null ? (short) record.get(MESSAGE_STATUS_CODE) : 0;
    }

    public short getEodStoreInterfaceMsgStatusCode(int interfaceId) {
	Map<String, Object> record = getEodStoreInterfaceByInterfaceId(interfaceId);
	return record.get(MESSAGE_STATUS_CODE) != null ? (short) record.get(MESSAGE_STATUS_CODE) : 0;
    }

    public List<AlertModel> getListOfEvents() {
	getEtlFile(LOG_FILE_PATH);
	List<AlertModel> alertList = new ArrayList<>();
	String localFilePath = Paths.get(getTemporaryFolderPath() + "/ListOfEvents.log").toAbsolutePath().toString();
	try (Scanner scanner = new Scanner(new File(localFilePath))) {
	    while (scanner.hasNext()) {
		String[] eventDetails = scanner.nextLine().split(":");
		String application = eventDetails[0].substring(eventDetails[0].indexOf("=") + 1);
		String severity = eventDetails[1].substring(eventDetails[1].indexOf("=") + 1);
		String msg_text = eventDetails[2].substring(eventDetails[2].indexOf("=") + 1);
		String country = eventDetails[3].substring(eventDetails[3].indexOf("=") + 1);
		String store = eventDetails[4].substring(eventDetails[4].indexOf("=") + 1);
		String errorCode = eventDetails[5].substring(eventDetails[5].indexOf("=") + 1);
		alertList.add(new AlertModel(application, severity, msg_text, country, store, errorCode));
	    }
	    alertList.removeIf(alert -> !alert.getStore().equals(getStoreNumber()));
	    return alertList;
	} catch (FileNotFoundException e) {
	    CerberusLogger.log("Error reading " + localFilePath, e);
	    return null;
	}
    }

    public void enableInterfaceById(int interfaceId) {
	Date startDate = CommonUtils.getDateTimeWithOffset(-5);
	CerberusLogger.log("Enabling interface with id " + interfaceId + "...");
	updateStoreInterface(interfaceId, startDate);
	CerberusLogger.log("Interface enabled.");
    }

    public void disableInterfaceById(int interfaceId) {
	Date startDate = CommonUtils.getDateTimeWithOffset(5);
	CerberusLogger.log("Disabling interface with id " + interfaceId + "...");
	updateStoreInterface(interfaceId, startDate);
	CerberusLogger.log("Interface disabled.");
    }

    public void cleanUpStoreEtl() {
	CerberusLogger.log("Cleaning etl in " + getSTORE_BASE_DIRECTORY() + "...");
	sftpClientDriver.cleanFilesOnRemoteDirectory(getSTORE_BASE_DIRECTORY(), getEtlProfile());
    }

    public CerberusSftpProfile getEtlProfile() {
	return new CerberusSftpProfile(getProperty(HOST_NAME_SERVER), getProperty(ETL_USER), getProperty(ETL_PASSWORD));
    }

    public CerberusSftpProfile getSmartProfile() {
	String smartServer = getProperty(SMART_SERVER_ADDRESS).replace(COUNTRY_CODE_TAG, getProperty(COUNTRY_CODE).toLowerCase()).replace(STORE_NUMBER_TAG, getProperty(STORE_NUMBER).substring(4));
	return new CerberusSftpProfile(smartServer, getProperty(SMART_USER), getProperty(SMART_PASSWORD));
    }

    public String getProperty(String property) {
	return getAutomationProperties().getProperty(property);
    }

    public String getTestExecutionId() {
	return getProperty(TEST_EXECUTION_PROPERTY);
    }

    public String getStoreNumber() {
	return storeNumber;
    }

    public String getCountryCode() {
	return this.countryCode;
    }

    public String getSequenceNumber() {
	return getProperty(SEQUENCE_NUMBER);
    }

    public String getWorkStationId() {
	return getProperty(WORK_STATION_ID);
    }

    public int getSearchTime() {
	return Integer.parseInt(getProperty(SEARCH_TIME));
    }

    public String getRequestTarget() {
	return getProperty(REQUEST_TARGET_URL).replace(HOST_NAME_TAG, getProperty(HOST_NAME_SERVER));
    }

    public String getMipsRequestTarget() {
	return getProperty(MIPS_TARGET_URL).replace(HOST_NAME_TAG, getProperty(HOST_NAME_SERVER));
    }

    public String getBusinessDate() {
	if (StringUtils.isEmpty(getProperty(BUSINESS_DATE))) {
	    return CommonUtils.getDateTimeFromPattern(BUSSINESS_DATE_FORMAT, new Date());
	} else
	    return getProperty(BUSINESS_DATE);
    }

    public void setEndDateTime() {
	if (StringUtils.isEmpty(getProperty(END_DATE_TIME))) {
	    currentEndDateTime = CommonUtils.getDateTimeFromPattern(END_DATE_TIME_FORMAT, new Date());
	} else {
	    currentEndDateTime = getProperty(END_DATE_TIME);
	}
    }

    public String getTimeStamp() {
	Date date = CommonUtils.convertStringToDateFromPattern(END_DATE_TIME_FORMAT, currentEndDateTime);
	return CommonUtils.getDateTimeFromPattern(END_DATE_TIME_FORMAT, date, -5);
    }

    public String getFilesBusinessDate() {
	return getBusinessDate().replace("-", "");
    }

    public void createEtlDirectory(String remoteDirectoryPath) {
	sftpClientDriver.createRemoteDirectory(remoteDirectoryPath, getEtlProfile());
	CerberusLogger.log("Directory " + remoteDirectoryPath + " created.");
    }

    public void removeEtlFile(String remoteFilePath) {
	sftpClientDriver.deleteRemoteFile(remoteFilePath, getEtlProfile());
    }

    public List<File> getCurrentXmlRequests() {
	setEndDateTime();
	FileFilter ff = ((File pathname) -> {
	    return pathname.getName().endsWith(".xml");
	});

	List<File> currentXmlFiles = new ArrayList<>();
	List<File> xmlFiles = Arrays.asList(new File(getCurrentTestCaseDataDirectoryPath()).listFiles(ff));
	for (File xmlFile : xmlFiles) {
	    String outputFile = getTemporaryFolderPath() + "\\" + xmlFile.getName();
	    try {
		new File(getTemporaryFolderPath()).mkdir();
		Scanner scanner = new Scanner(xmlFile);
		String content = scanner.useDelimiter("\\Z").next();
		scanner.close();
		content = content.replace(STORE_NUMBER_TAG, getStoreNumber()).replace(COUNTRY_CODE_TAG, getCountryCode()).replace(BUSSINES_DATE_TAG, getBusinessDate()).replace(SEQ_NUMBER_TAG, getSequenceNumber())
			.replace(WORK_STATION_TAG, getWorkStationId()).replace(END_DATE_TIME_TAG, currentEndDateTime).replace(TIME_STAMP_TAG, getTimeStamp());
		Files.write(Paths.get(outputFile), content.getBytes());
		currentXmlFiles.add(new File(outputFile));
	    } catch (Exception e) {
		CerberusLogger.log("Failed to read xml request.", e);
		return null;
	    }
	}
	Collections.sort(currentXmlFiles);
	return currentXmlFiles;
    }

    public int getStatusResponse() {
	HttpResponse response = httpClientDriver.sendXmlPost(getRequestTarget(), getCurrentXmlRequests().get(0));
	int status = httpClientDriver.getResponseStatus(response);
	if (status != 200) {
	    CerberusLogger.logError("Exception in POSLog: " + httpClientDriver.getEntityResponse(response));
	}
	return status;
    }

    public int getStatusResponse(File xmlFile) {
	return httpClientDriver.sendXmlPost(getRequestTarget(), xmlFile).getStatusLine().getStatusCode();
    }

    public int getMipsStatusResponse() {
	return httpClientDriver.sendXmlPost(getMipsRequestTarget(), getCurrentXmlRequests().get(0)).getStatusLine().getStatusCode();
    }

    public void changeFileOrDirectoryPermissions(String remoteFilePath, String permission) {
	sftpClientDriver.changePermission(remoteFilePath, permission, getEtlProfile());
    }

    public void getEtlFile(String remoteFilePath) {
	sftpClientDriver.copyRemoteFileInLocal(remoteFilePath, getTemporaryFolderPath(), getEtlProfile());
    }

    public void getEtlFile(String remoteDirectoryPath, String regex) {
	sftpClientDriver.copyRemoteFileInLocal(remoteDirectoryPath, regex, 0, getTemporaryFolderPath(), getEtlProfile());
    }

    public void getSmartFile(String remoteFilePath, String currentTestcase) {
	sftpClientDriver.copyRemoteFileInLocal(remoteFilePath, getTemporaryFolderPath(), getSmartProfile());
    }

    public void copyLocalFileInEtl(String remoteDirectory, String localFilePath, String renameFile) {
	sftpClientDriver.copyLocalFileToRemoteDirectory(remoteDirectory, localFilePath, renameFile, getEtlProfile());
    }

    public void copyLocalFilesInEtl(String remoteDirectory, File[] files) {
	for (File file : files) {
	    sftpClientDriver.copyLocalFileToRemoteDirectory(remoteDirectory, file.getAbsolutePath(), "", getEtlProfile());
	}
    }

    public File getRemoteEtlDirectory(String remoteDirectoryPath) {
	return sftpClientDriver.getRemoteDirectoryAndChild(remoteDirectoryPath, getEtlProfile());
    }

    public void verifyEtlFileExists(String remoteFilePath) {
	sftpClientDriver.assertTrue("File " + remoteFilePath + " exists.", sftpClientDriver.remoteFileExist(remoteFilePath, getEtlProfile(), getSearchTime()));
    }

    public void verifyEtlFileExists(String remoteFileDirectory, String regex) {
	sftpClientDriver.assertTrue("File match for " + regex + " exists.", sftpClientDriver.remoteFileExist(remoteFileDirectory, regex, getEtlProfile(), getSearchTime()));
    }

    public void verifyEtlFileNotExists(String remoteFilePath, boolean waitFor) {
	if (waitFor)
	    sftpClientDriver.assertFalse("File " + remoteFilePath + " not exists.", sftpClientDriver.remoteFileExist(remoteFilePath, getEtlProfile(), getSearchTime()));
	else
	    sftpClientDriver.assertFalse("File " + remoteFilePath + " not exists.", sftpClientDriver.remoteFileExist(remoteFilePath, getEtlProfile()));
    }

    public void verifyEtlFileRemoved(String remoteFilePath) {
	int count = 0;
	while (sftpClientDriver.remoteFileExist(remoteFilePath, getEtlProfile())) {
	    if (count < getSearchTime()) {
		count += 2;
		CommonUtils.sleepInSeconds(2);
	    } else
		break;
	}
	sftpClientDriver.assertFalse("File " + remoteFilePath + " was removed.", sftpClientDriver.remoteFileExist(remoteFilePath, getEtlProfile()));
    }

    public void verifyFilesContentsEquals(File file1, File file2) {
	try {
	    sftpClientDriver.assertTrue("The contents of the files " + file1.getName() + " and " + file2.getName() + " are equal.", FileUtils.contentEquals(file1, file2));
	} catch (IOException e) {
	    CerberusLogger.log("Error getting file.", e);
	}
    }

    public void verifySmartFileExists(String remoteFilePath) {
	sftpClientDriver.assertTrue("File " + remoteFilePath + " exists.", sftpClientDriver.remoteFileExist(remoteFilePath, getSmartProfile(), getSearchTime()));
    }

    public void cleanUpStoreDB() {
	CerberusLogger.log("Cleaning store DB...");
	cleanupStoreDataBase();
    }

    public void updateBusinessDateInDB() {
	int update = updateBusinessDate();
	CerberusLogger.log("Updating business date in data base: " + update + " updated rows.");
    }

    /**
     * LOCAL PATHS AND DIRECTORIES
     */
    public String getTestCaseDataDirectoryFilePath(String fileName) {
	return getCurrentTestCaseDataDirectoryPath() + "\\" + fileName;
    }

    public String getCurrentTestCaseDataDirectoryPath() {
	return Paths.get(getProperty(RESOURCES_HOME_PATH) + "/" + interfaceName + "/" + sftpClientDriver.getCurrentTestCase()).toAbsolutePath().toString();
    }

    public String getTemporaryResponsePath(String currentTestcase) {
	return Paths.get(getTemporaryFolderPath() + "/" + RESPONSE_DATA).toAbsolutePath().toString();
    }

    public File getPreconditionFolder(String currentTestcase) {
	return new File(Paths.get(getProperty(RESOURCES_HOME_PATH) + "/" + currentTestcase + "/" + PRECONDITION_FOLDER).toAbsolutePath().toString());
    }

    public String getTemporaryFolderPath() {
	return Paths.get(getProperty(RESOURCES_HOME_PATH) + "/" + interfaceName + "/" + sftpClientDriver.getCurrentTestCase() + TEMP_FOLDER).toAbsolutePath().toString();
    }

    public void cleanupTemporaryFiles() {
	String path = getTemporaryFolderPath();
	File tempFolder = new File(path);
	try {
	    if (tempFolder.exists()) {
		CerberusLogger.log("Cleaning temporary data...");
		FileUtils.deleteDirectory(tempFolder);
	    } else {
		CerberusLogger.log("There are no data to clean.");
	    }
	} catch (Exception e) {
	    CerberusLogger.log("Failed cleaning temporary data.", e);
	}
    }

    /**
     * REMOTE PATHS AND DIRECTORIES ETL
     */
    public String getPOS_OUTBOUND_DIRECTORY() {
	return POS_OUTBOUND_DIRECTORY.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getPOS_INBOUND_DIRECTORY() {
	return POS_INBOUND_DIRECTORY.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getSTORE_BASE_DIRECTORY() {
	return STORE_BASE_DIRECTORY.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String get_C_ADX_IDT1_DIRECTORY() {
	return ADX_C_IDT1.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String get_D_ADX_IDT1_DIRECTORY() {
	return ADX_D_IDT1.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String get_D_ADX_UDT1_DIRECTORY() {
	return ADX_D_UDT1.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getWORK_ACCOUNTABILTY_TYPE_DIRECTORY() {
	return WORK_ACCOUNTABILTY_TYPE.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getWORK_BANKCARD_DIRECTORY() {
	return WORK_BANKCARD.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getWORK_END_OF_DAY_DIRECTORY() {
	return WORK_END_OF_DAY.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getWORK_MOVEMENT_AND_TLOG_DIRECTORY() {
	return WORK_MOVEMENT_AND_TLOG.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getWORK_PRICE_OVERRIDE_DIRECTORY() {
	return WORK_PRICE_OVERRIDE.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getWORK_PROMO_MD_DIRECTORY() {
	return WORK_PROMO_MD.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getWORK_TAX_MAINTENEANCE_DIRECTORY() {
	return WORK_TAX_MAINTENEANCE.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getWORK_XML_LOG_DIRECTORY() {
	return WORK_XML_LOG.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getARCHIVE_ACCOUNTABILITY_TYPE_DIRECTORY() {
	return ARCHIVE_ACCOUNTABILITY_TYPE.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getARCHIVE_BRICK_RATES_DIRECTORY() {
	return ARCHIVE_BRICK_RATES.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getARCHIVE_END_OF_DAY_DIRECTORY() {
	return ARCHIVE_END_OF_DAY.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber()) + "/" + getFilesBusinessDate();
    }

    public String getARCHIVE_OPERATOR_MAINTENANCE_DIRECTORY() {
	return ARCHIVE_OPERATOR_MAINTENANCE.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getARCHIVE_PROMO_MD_DIRECTORY() {
	return ARCHIVE_PROMO_MD.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getARCHIVE_XML_LOG_DIRECTORY() {
	return ARCHIVE_XML_LOG.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getARCHIVE_BANKCARD_DIRECTORY() {
	return ARCHIVE_BANKCARD.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getARCHIVE_CATEGORYMAINTENANCE_DIRECTORY() {
	return ARCHIVE_CATEGORYMAINTENANCE.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getARCHIVE_MOVEMENT_AND_TLOG_DIRECTORY() {
	return ARCHIVE_MOVEMENT_AND_TLOG.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getARCHIVE_PRICE_OVERRIDE_DIRECTORY() {
	return ARCHIVE_PRICE_OVERRIDE.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public String getARCHIVE_TAX_MAINTENANCE_DIRECTORY() {
	return ARCHIVE_TAX_MAINTENANCE.replace(COUNTRY_CODE_TAG, getCountryCode()).replace(STORE_NUMBER_TAG, getStoreNumber());
    }

    public short getRegisterMessageInterfaceStatusByInterfaceId(int interfaceId) {
	return (short) getRegisterMessageInterfaceByInterfaceId(interfaceId).get(MESSAGE_STATUS_CODE);
    }

    public short getBatchStoreInterfaceStatusByInterfaceId(int interfaceId) {
	return (short) getBatchStoreInterfaceByInterfaceId(interfaceId).get(MESSAGE_STATUS_CODE);
    }

    /**
     * DATA BASE MANAGE
     */
    private BasicDataSource getSmartDatasource() {
	BasicDataSource dataSource = new BasicDataSource();
	Properties properties = CommonUtils.loadPropertiesFromClasspath(AUTOMATION_PROPERTIES);
	String url = properties.getProperty(SMART_DB_URL).replace(STORE_NUMBER_TAG, getStoreNumber().substring(4)).replace(COUNTRY_CODE_TAG, getCountryCode().toLowerCase());
	if (StringUtils.isNotBlank(url)) {
	    dataSource.setDriverClassName(properties.getProperty(SMART_DB_DRIVER));
	    dataSource.setUrl(url);
	    dataSource.setUsername(properties.getProperty(SMART_DB_USERNAME));
	    dataSource.setPassword(properties.getProperty(SMART_DB_PASSWORD));
	    dataSource.setInitialSize(30);
	}
	return dataSource;
    }

    public void switchToSmartDb() {
	CerberusLogger.log("Switching to SMART DB.");
	dataBaseDriver.setCurrentDataSource(getSmartDatasource());
    }

    public void resetDataSource() {
	CerberusLogger.log("Restoring connection to default DB.");
	dataBaseDriver.resetDataSourceToDefault();
    }

    private void cleanupStoreDataBase() {
	Map<String, Object> parameters = new HashMap<>();
	parameters.put("storeNumber", Integer.parseInt(getStoreNumber()));
	parameters.put("countryCode", getCountryCode());
	dataBaseDriver.executeParameterizedUpdateQuery(CLEAN_EOD_STORE_INTERFACE_QUERY, parameters);
	dataBaseDriver.executeParameterizedUpdateQuery(CLEAN_EOD_MSG_QUERY, parameters);
	dataBaseDriver.executeParameterizedUpdateQuery(CLEAN_SALES_MSG_INTERFACE_QUERY, parameters);
	dataBaseDriver.executeParameterizedUpdateQuery(CLEAN_SALES_MSG_QUERY, parameters);
	dataBaseDriver.executeParameterizedUpdateQuery(CLEAN_REGISTER_MESSAGE_INTERFACE_QUERY, parameters);
	dataBaseDriver.executeParameterizedUpdateQuery(CLEAN_REGISTER_MESSAGE_QUERY, parameters);
	dataBaseDriver.executeParameterizedUpdateQuery(CLEAN_BATCH_STORE_INTERFACE_QUERY, parameters);
	dataBaseDriver.executeParameterizedUpdateQuery(CLEAN_STORE_INTERFACE_PROCESS_QUERY, parameters);
    }

    public void cleanRegMsgInterfaceTable() {
	Map<String, Object> parameters = new HashMap<>();
	parameters.put("storeNumber", Integer.parseInt(getStoreNumber()));
	parameters.put("countryCode", getCountryCode());
	CerberusLogger.log("Cleaning reg_msg_interface table...");
	dataBaseDriver.executeParameterizedUpdateQuery(CLEAN_REGISTER_MESSAGE_INTERFACE_QUERY, parameters);
    }

    private int updateStoreInterface(int interfaceId, Date startDate) {
	Map<String, Object> parameters = new HashMap<>();
	parameters.put("storeNumber", Integer.parseInt(getStoreNumber()));
	parameters.put("countryCode", getCountryCode());
	parameters.put("interfaceId", interfaceId);
	parameters.put("startDate", startDate);
	return dataBaseDriver.executeParameterizedUpdateQuery(UPDATE_STORE_INTERFACE, parameters);
    }

    private int updateBusinessDate() {
	Map<String, Object> parameters = new HashMap<>();
	parameters.put("storeNumber", Integer.parseInt(getStoreNumber()));
	parameters.put("countryCode", getCountryCode());
	parameters.put("businessDate", getBusinessDate());
	return dataBaseDriver.executeParameterizedUpdateQuery(UPDATE_BUSSINESS_DATE, parameters);
    }

    public Map<String, Object> getBatchStoreInterfaceByInterfaceId(int interfaceId) {
	Map<String, Object> parameters = new HashMap<>();
	parameters.put("storeNumber", Integer.parseInt(getStoreNumber()));
	parameters.put("countryCode", getCountryCode());
	parameters.put("interfaceId", interfaceId);
	return dataBaseDriver.executeParameterizedQuery(BATCH_STORE_INTERFACE, parameters).get(0);
    }

    public Map<String, Object> getRegisterMessageInterfaceByInterfaceId(int interfaceId) {
	Map<String, Object> parameters = new HashMap<>();
	parameters.put("storeNumber", Integer.parseInt(getStoreNumber()));
	parameters.put("countryCode", getCountryCode());
	parameters.put("interfaceId", interfaceId);
	parameters.put("workStationId", Integer.parseInt(getWorkStationId()));
	parameters.put("endDateTime", CommonUtils.convertStringToDateFromPattern(END_DATE_TIME_FORMAT, currentEndDateTime));
	parameters.put("sequenceNumber", Integer.parseInt(getSequenceNumber()));
	return dataBaseDriver.executeParameterizedQuery(REGISTER_MESSAGE_INTERFACE, parameters).get(0);
    }

    public Map<String, Object> getEodStoreInterfaceByInterfaceId(int interfaceId) {
	Map<String, Object> parameters = new HashMap<>();
	parameters.put("storeNumber", Integer.parseInt(getStoreNumber()));
	parameters.put("countryCode", getCountryCode());
	parameters.put("interfaceId", interfaceId);
	return dataBaseDriver.executeParameterizedQuery(EOD_STORE_INTERFACE, parameters).get(0);
    }

    public List<Map<String, Object>> query() {
	Map<String, Object> parameters = new HashMap<>();
	parameters.put("storeNumber", Integer.parseInt(getStoreNumber()));
	parameters.put("countryCode", getCountryCode());
	parameters.put("interfaceId", OPERATOR_MAINTENANCE_ID);
	String query = "SELECT * FROM batch_store_interface WHERE store_nbr=:storeNumber AND country_code=:countryCode AND interface_id=:interfaceId";
	List<Map<String, Object>> results = dataBaseDriver.executeParameterizedQuery(query, parameters);
	return results;
    }
}
