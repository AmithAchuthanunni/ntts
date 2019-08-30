package com.cts.dao;

public class ActivityLookupDaoFactory {

	private static ActivityLookupDao dao;
	
	
	static{
		
		dao=new ActivityLookupDaoImpl();
	}

	public static ActivityLookupDao getActivityLookupDao(){
		return dao;
	}
	
	
	
}
