package com.cts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cts.utility.DBUtil;

public class ActivityLookupDaoImpl implements ActivityLookupDao {

	@Override
	public List<String> getAllActivityById(String id) {
		// TODO Auto-generated method stub
		
		List<String> l = new ArrayList<String>();
		
		Connection conn=DBUtil.getDBConnection();
		
		try {
			
			PreparedStatement ps = conn.prepareStatement("select ActivityName from ActivityLookup where AppID=(select AppID from AppLookup where AppName=?)");
			
			ps.setString(1,id);
			
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

}
