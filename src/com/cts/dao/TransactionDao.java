package com.cts.dao;

import java.util.List;

import com.cts.beans.TransactionBean;

public interface TransactionDao {
	
	public boolean addTransaction(TransactionBean tb);
	
	public boolean checkDataEntryByDateRangeAppIDandActivity(String id,String range,String activity);
	
	public boolean checkDataEntryByDateRangeAndAppID(String id,String range);
	
	public String getEffortByDateRangeAppIDandActivity(String id,String range,String activity);

}
