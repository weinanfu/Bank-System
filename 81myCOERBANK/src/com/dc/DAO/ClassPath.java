package com.dc.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.dc.utils.ConnDB;

public class ClassPath {
	
	public String getClassPath(String server_id){
		String path = null;
		Connection conn = ConnDB.getConnection();		CallableStatement cs = null;
		try {
			cs = conn.prepareCall("{call SP_BproFindClassPath(?,?)}");
			
			cs.setString(1, server_id);
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);
			
			cs.execute();
			path = cs.getString(2);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(cs != null){
					cs.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return path;
	}
}
