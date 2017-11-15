package com.automation.gi.tests.common;

public interface Queries {
    // Cleanup queries
    String CLEAN_STORE_INTERFACE_PROCESS_QUERY = "DELETE FROM store_interface_process WHERE interface_id not IN (2,7) AND store_nbr = :storeNumber AND country_code = :countryCode";
    String CLEAN_SALES_MSG_INTERFACE_QUERY = "DELETE FROM sales_msg_interface WHERE store_nbr = :storeNumber AND country_code = :countryCode";
    String CLEAN_SALES_MSG_QUERY = "DELETE FROM sales_msg WHERE store_nbr = :storeNumber AND country_code = :countryCode";
    String CLEAN_REGISTER_MESSAGE_INTERFACE_QUERY = "DELETE FROM reg_msg_interface WHERE store_nbr = :storeNumber AND country_code = :countryCode";
    String CLEAN_REGISTER_MESSAGE_QUERY = "DELETE FROM register_msg WHERE store_nbr = :storeNumber AND country_code = :countryCode";
    String CLEAN_BATCH_STORE_INTERFACE_QUERY = "DELETE FROM batch_store_interface WHERE store_nbr = :storeNumber AND country_code = :countryCode";
    String CLEAN_EOD_STORE_INTERFACE_QUERY = "DELETE FROM eod_store_interface WHERE store_nbr = :storeNumber AND country_code = :countryCode";
    String CLEAN_EOD_MSG_QUERY = "DELETE FROM eod_msg WHERE store_nbr = :storeNumber AND country_code = :countryCode";

    // Update store_interace
    String UPDATE_STORE_INTERFACE = "UPDATE store_interface SET str_interface_start_date=:startDate WHERE country_code=:countryCode AND store_nbr=:storeNumber AND interface_id=:interfaceId";

    // Update business date
    String UPDATE_BUSSINESS_DATE = "UPDATE store_interface_process SET business_date=:businessDate WHERE country_code=:countryCode AND store_nbr=:storeNumber AND process_name IN ('binlog','movement')";

    // ETL queries
    String REGISTER_MESSAGE_INTERFACE = "SELECT * FROM reg_msg_interface WHERE store_nbr=:storeNumber AND country_code=:countryCode AND register_nbr=:workStationId AND interface_id=:interfaceId AND register_txn_ts=:endDateTime AND register_txn_nbr=:sequenceNumber ORDER BY message_status_code DESC";
    String BATCH_STORE_INTERFACE = "SELECT * FROM batch_store_interface WHERE store_nbr=:storeNumber AND country_code=:countryCode AND interface_id=:interfaceId ORDER BY message_status_code DESC";
    String EOD_STORE_INTERFACE = "SELECT * FROM eod_store_interface WHERE store_nbr=:storeNumber AND country_code=:countryCode AND interface_id=:interfaceId ORDER BY message_status_code DESC";

    // SMART queries
    String CASH_OFFICE_OP_MANT = "SELECT  a.cash_reg_opr_id,  a.assoc_id,  a.operator_type_code,  a.opr_secur_lvl_nbr,  a.opr_secur_lvl_desc,  a.oper_end_date,  a.operator_name FROM TABLE(MULTISET(SELECT o.cash_reg_opr_id,  o.assoc_id,  o.operator_type_code,  ot.opr_secur_lvl_nbr,  os.opr_secur_lvl_desc, CASE WHEN   o.oper_end_date is null Then   MDY(12,31,2199) Else   o.oper_end_date End   as end_date_check,  o.oper_end_date,  o.operator_name FROM  cash_office:operator o,  cash_office:operator_type ot,  cash_office:operator_security os WHERE  o.operator_type_code = ot.operator_type_code  AND ot.opr_secur_lvl_nbr = os.opr_secur_lvl_nbr )) as a  WHERE  a.end_date_check > current  ORDER BY  a.cash_reg_opr_id; ";
    String CISAM_ITEM_FILE_CAT_MANT = "SELECT a.ucode_item_combo, a.item_num, a.upc_status, a.ucode, a.rcd_add_date, a.rcd_chg_date, b.item_nbr, b.dept_catg_grp_nbr, b.dept_category_nbr, b.dept_subcatg_nbr FROM cisam:smm012 a, item_file:item b WHERE a.item_num = b.item_nbr AND a.upc_status = 0";
}
