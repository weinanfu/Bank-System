/**   
* @Title: ConnDB.java  
* @Description: TODO(用一句话描述该文件做什么) 
* @author jinlin 
* @date 2011-9-13 下午09:21:06 
* @version V1.0   
*/


package com.db.utils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author jinlin
 *
 */
public class ConnDB {
	public static Connection getConnection(){
		Connection conn=null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:cissst", "teller", "teller");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
