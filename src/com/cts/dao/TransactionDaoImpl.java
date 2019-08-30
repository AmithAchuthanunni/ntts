package com.cts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.cts.beans.TransactionBean;
import com.cts.utility.DBUtil;

public class TransactionDaoImpl implements TransactionDao{

	@Override
	public boolean addTransaction(TransactionBean tb) {
		// TODO Auto-generated method stub
		boolean status= false;
		
		Connection conn=DBUtil.getDBConnection();
		
		try {
			PreparedStatement ps=conn.prepareStatement("insert into Transaction values(?,?,?,?)");
			
			ps.setString(4,tb.getId());
			ps.setString(1,tb.getActivity());
			ps.setString(2,tb.getDateRange());
			ps.setString(3,tb.getEffort());
			
			int x=ps.executeUpdate();
			
			if(x>0)
				status=true;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		return status;
	}

	@Override
	public boolean checkDataEntryByDateRangeAppIDandActivity(String id, String range,String activity) {
		// TODO Auto-generated method stub
		boolean status=false;
		
		Connection conn=DBUtil.getDBConnection();
		
		try {
			
			PreparedStatement ps = conn.prepareStatement("select * from Transaction where DateRange=? and AppID=? and Activity=?");
			
			ps.setString(1,range);
			ps.setString(2,id);
			ps.setString(3,activity);
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()){
				
				status=true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return status;
	}

	@Override
	public String getEffortByDateRangeAppIDandActivity(String id, String range, String activity) {
		// TODO Auto-generated method stub
		String effort="";
		
		Connection conn=DBUtil.getDBConnection();
		
		try {
			
			PreparedStatement ps = conn.prepareStatement("select Effort from Transaction where AppID=? and DateRange=? and Activity=?");
			
			ps.setString(1,id);
			ps.setString(2,range);
			ps.setString(3,activity);
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()){
				effort=rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return effort;
	}

	@Override
	public boolean checkDataEntryByDateRangeAndAppID(String id, String range) {

boolean status=false;
		
		Connection conn=DBUtil.getDBConnection();
		
		try {
			
			PreparedStatement ps = conn.prepareStatement("select * from Transaction where DateRange=? and AppID=?");
			
			ps.setString(1,range);
			ps.setString(2,id);
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()){
				
				status=true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return status;
		
		
	}





}
