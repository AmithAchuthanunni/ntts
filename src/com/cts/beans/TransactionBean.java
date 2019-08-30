package com.cts.beans;

import java.io.Serializable;

public class TransactionBean implements Serializable{
	
	private String id;
	private String activity;
	private String dateRange;
	private String effort;
	
	public TransactionBean() {
		// TODO Auto-generated constructor stub
	}

	public TransactionBean(String id, String activity, String dateRange, String effort) {
		super();
		this.id = id;
		this.activity = activity;
		this.dateRange = dateRange;
		this.effort = effort;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	public String getEffort() {
		return effort;
	}

	public void setEffort(String effort) {
		this.effort = effort;
	}
}
