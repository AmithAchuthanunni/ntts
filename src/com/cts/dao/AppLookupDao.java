package com.cts.dao;

import java.util.List;

public interface AppLookupDao {
	
	public List<String> getAllAppByID(String id);
	
	public String getIDByApp(String app);

}
