package com.cts.dao;

public class TransactionDaoFactory {
	
	private static TransactionDao dao;
	
	
	static{
		
		dao=new TransactionDaoImpl();
	}

	public static TransactionDao getTransactionDao(){
		return dao;
	}
	

}
