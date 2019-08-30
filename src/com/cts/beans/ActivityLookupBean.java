package com.cts.beans;

import java.io.Serializable;

public class ActivityLookupBean implements Serializable{
	
	private String id;
	private String activityName;
	
	public ActivityLookupBean() {
		// TODO Auto-generated constructor stub
	}

	public ActivityLookupBean(String id, String activityName) {
		super();
		this.id = id;
		this.activityName = activityName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	

}
