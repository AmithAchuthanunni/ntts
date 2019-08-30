package com.cts.beans;

import java.io.Serializable;

public class AppLookupBean implements Serializable {
	
	private String id;
	private String appName;
	
	public AppLookupBean() {
		// TODO Auto-generated constructor stub
	}

	public AppLookupBean(String id, String appName) {
		super();
		this.id = id;
		this.appName = appName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	
	

}
