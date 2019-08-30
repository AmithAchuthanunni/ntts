package com.cts.dao;

public class AppLookupDaoFactory {

	private static AppLookupDao dao;
	
	
	static{
		
		dao=new AppLookupDaoImpl();
	}

	public static AppLookupDao getAppLookupDao(){
		return dao;
	}
}
