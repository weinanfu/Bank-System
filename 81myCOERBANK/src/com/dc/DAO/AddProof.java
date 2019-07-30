package com.dc.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.dc.utils.ConnDB;

public class AddProof {
	
	/**
	 * ��¼ƾ֤ͷ
	 * @param name
	 * @param deal_style_id
	 * @param BC_LIST_ID
	 * @return ���ػ�Ʒ�¼����ˮ�ųɹ�  -1��ʧ��
	 */
	public int addToTable(String name, String deal_style_id, int BC_LIST_ID) {
		int result = -1;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(Calendar.getInstance().getTime());
		Connection conn = ConnDB.getConnection();		CallableStatement cs = null;
		try {
			cs = conn.prepareCall("{call SP_BproProof(?,?,?,?,?)}");
			cs.setString(1, date);
			cs.setString(2, name);
			cs.setString(3, deal_style_id);
			cs.setInt(4, BC_LIST_ID);
			cs.registerOutParameter(5, oracle.jdbc.OracleTypes.NUMBER);
			cs.execute();
			result = cs.getInt(5);
			
		} catch (SQLException e) {
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
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * �ı��Ʒ�¼��״̬
	 * @return ����0��ı�ɹ� ������Ϊʧ��
	 */
	public int changState(int List_id){
		int result = -1;
		Connection conn = ConnDB.getConnection();		CallableStatement cs = null;
		try {
			cs = conn.prepareCall("{call SP_BproChangeProof(?,?,?)}");
			
			cs.setInt(1, List_id);
			cs.setString(2, "2");
			cs.registerOutParameter(3, oracle.jdbc.OracleTypes.NUMBER);
			cs.execute();
			result = cs.getInt(3);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block 000001
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
		
		return result;
	}
}
