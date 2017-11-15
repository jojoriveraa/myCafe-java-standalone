/**
 * 
 */
package com.automation.gi.tests;

/**
 * @author David Villalobos
 *
 */
public class AlertModel {
    private String application;
    private String severity;
    private String msg_text;
    private String country;
    private String store;
    private String errorCode;

    public AlertModel(String application, String severity, String msg_text, String country, String store, String errorCode) {
	this.application = application;
	this.severity = severity;
	this.msg_text = msg_text;
	this.country = country;
	this.store = store;
	this.errorCode = errorCode;
    }

    public String getApplication() {
	return application;
    }

    public String getSeverity() {
	return severity;
    }

    public String getMsg_text() {
	return msg_text;
    }

    public String getCountry() {
	return country;
    }

    public String getStore() {
	return store;
    }

    public String getErrorCode() {
	return errorCode;
    }
}
