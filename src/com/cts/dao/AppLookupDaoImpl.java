package com.cts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cts.utility.DBUtil;

public class AppLookupDaoImpl implements AppLookupDao{

	@Override
	public List<String> getAllAppByID(String id) {
		
		List<String> l = new ArrayList<String>();
		
		Connection conn=DBUtil.getDBConnection();
		
		try {
			
			PreparedStatement ps = conn.prepareStatement("select AppName from AppLookup where AppId LIKE ?");
			
			ps.setString(1,id+"%");
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()){
				
				l.add(rs.getString(1));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		return l;
	}

	@Override
	public String getIDByApp(String app) {
		// TODO Auto-generated method stub
		
		String id = "";
		
		Connection conn=DBUtil.getDBConnection();
		
		try {
			
			PreparedStatement ps = conn.prepareStatement("select AppID from AppLookup where AppName=?");
			
			ps.setString(1,app);
			
			ResultSet rs=ps.executeQuery();
			
			if(rs.next()){
				
				id=rs.getString(1);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
				
		
		
		
		return id;
	}

}
