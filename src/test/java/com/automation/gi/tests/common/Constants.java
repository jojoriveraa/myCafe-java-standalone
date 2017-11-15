package com.automation.gi.tests.common;

public interface Constants {
    int STATUS_200 = 200;
    String STRING_82_DATE_FORMAT = "MMddyy";
    String BUSSINESS_DATE_FORMAT = "yyyy-MM-dd";
    String END_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    String BUSINESS_DATE = "business.date";
    String COUNTRY_CODE = "country.code";
    String STORE_NUMBER = "store.number";
    String SEQUENCE_NUMBER = "trans.seq.number";
    String WORK_STATION_ID = "trans.work.station.id";
    String END_DATE_TIME = "trans.end.date.time";
    String COUNTRY_CODE_TAG = "{CC}";
    String STORE_NUMBER_TAG = "{SN}";
    String BUSSINES_DATE_TAG = "{BD}";
    String SEQ_NUMBER_TAG = "{SQN}";
    String WORK_STATION_TAG = "{WS}";
    String END_DATE_TIME_TAG = "{EDT}";
    String TIME_STAMP_TAG = "{TS}";
    String HOST_NAME_TAG = "{HN}";
    String SEARCH_TIME = "sftp.search.time";
    String REQUEST_TARGET_URL = "request.target.url";
    String MIPS_TARGET_URL = "mips.target.url";
    String RESOURCES_HOME_PATH = "resources.home";
    String HOST_NAME_SERVER = "server.host.name";

    // Mips precondition
    String MIPS_PRECONDITION_FILE = "MIP.48580210012";

    // Directories in ETL
    String LOG_FILE_PATH = "/etldir/LOG/ListOfEvents.log";
    String STORE_BASE_DIRECTORY = "/etldir/{CC}/{SN}";

    String ADX_C_IDT1 = STORE_BASE_DIRECTORY + "/C/ADX_IDT1";
    String ADX_D_IDT1 = STORE_BASE_DIRECTORY + "/D/ADX_IDT1";
    String ADX_D_UDT1 = STORE_BASE_DIRECTORY + "/D/ADX_UDT1";

    String POS_OUTBOUND_DIRECTORY = STORE_BASE_DIRECTORY + "/POSOutbound";
    String POS_INBOUND_DIRECTORY = STORE_BASE_DIRECTORY + "/POSInbound";

    String WORK_BASE = STORE_BASE_DIRECTORY + "/Work/";
    String WORK_ACCOUNTABILTY_TYPE = WORK_BASE + "AccountabilityType";
    String WORK_BANKCARD = WORK_BASE + "Bankcard";
    String WORK_END_OF_DAY = WORK_BASE + "EndOfDay";
    String WORK_MOVEMENT_AND_TLOG = WORK_BASE + "MovementAndTlog";
    String WORK_PRICE_OVERRIDE = WORK_BASE + "PriceOverride";
    String WORK_PROMO_MD = WORK_BASE + "PromoMD";
    String WORK_TAX_MAINTENEANCE = WORK_BASE + "TaxMaintenance";
    String WORK_XML_LOG = WORK_BASE + "XMLLog";

    String ARCHIVE_BASE = STORE_BASE_DIRECTORY + "/Archive/";
    String ARCHIVE_ACCOUNTABILITY_TYPE = ARCHIVE_BASE + "AccountabilityType";
    String ARCHIVE_BRICK_RATES = ARCHIVE_BASE + "BrickRates";
    String ARCHIVE_END_OF_DAY = ARCHIVE_BASE + "EndOfDay";
    String ARCHIVE_OPERATOR_MAINTENANCE = ARCHIVE_BASE + "OperatorMaintenance";
    String ARCHIVE_PROMO_MD = ARCHIVE_BASE + "PromoMD";
    String ARCHIVE_XML_LOG = ARCHIVE_BASE + "XMLLog";
    String ARCHIVE_BANKCARD = ARCHIVE_BASE + "Bankcard";
    String ARCHIVE_CATEGORYMAINTENANCE = ARCHIVE_BASE + "CategoryMaintenance";
    String ARCHIVE_MOVEMENT_AND_TLOG = ARCHIVE_BASE + "MovementAndTlog";
    String ARCHIVE_PRICE_OVERRIDE = ARCHIVE_BASE + "PriceOverride";
    String ARCHIVE_TAX_MAINTENANCE = ARCHIVE_BASE + "TaxMaintenance";

    // Local data
    String RESPONSE_DATA = "Response.xml";
    String PRECONDITION_FOLDER = "PreconditionsData";
    String TAX_MANT_BR_INPUT_FOLDER = "BRInputFiles";
    String TAX_MANT_EALCTX_FOLDER = "EALCTX";
    String TAX_MANT_EALGTX_FOLDER = "EALGTX";
    String TAX_MANT_EALITAX_FOLDER = "EALITAX";
    String TEMP_FOLDER = "/tempFiles";

    // SftpProfiles
    String ETL_USER = "sftp.etl.user.id";
    String ETL_PASSWORD = "sftp.etl.password";
    String SMART_SERVER_ADDRESS = "sftp.smart.server.address";
    String SMART_USER = "sftp.smart.user.id";
    String SMART_PASSWORD = "sftp.smart.password";

    // SMART DB
    String SMART_DB_URL = "smart.db.url";
    String SMART_DB_DRIVER = "smart.db.driver";
    String SMART_DB_USERNAME = "smart.db.username";
    String SMART_DB_PASSWORD = "smart.db.password";

    // ETL permissions
    String ALL_PERMISSIONS = "777";

    // Interfaces id
    short OPERATOR_MAINTENANCE_ID = 29;
    short CATEGORY_MAINTENANCE_ID = 37;
    short TAX_MAINTENANCE = 17;
    short BINLOG_ID = 3;
    short END_OF_DAY_ID = 20;
    short BANKCARDS_ID = 5;
    short BANKCARDS_EOD_ID = 7;
    short ACCOUNTABILITY_ID = 16;

    // Message statuses
    short STATUS_ELEVEN_THOUSAND = 11000;
    short STATUS_TEN_THOUSAND = 10000;
    short STATUS_FIVE_THOUSAND = 5000;
    short STATUS_THREE_HUNDRED = 300;

    // Database fields
    // #General
    String MESSAGE_STATUS_CODE = "message_status_code";
    // #Category mant
    String OPR_SECUR_LVL_DESC = "opr_secur_lvl_desc";
    // #Operator mant
    String ITEM_NBR = "item_nbr";
    String UCODE = "ucode";
    String DEPT_CATG_GRP_NBR = "dept_catg_grp_nbr";
    String DEPT_CATEGORY_NBR = "dept_category_nbr";
    String DEPT_SUBCATG_NBR = "dept_subcatg_nbr";
}
