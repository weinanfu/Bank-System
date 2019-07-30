package com.dc.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.db.utils.ConnDB;

public class TellerWriteFlow {
	/*
	  ��ˮ��--FLOWNUM
	  ����ʱ��--sysdate�Զ���ȡ
	  ���׽��--FLOWMONEY
	  �����˻���--CARDID
	  ���׹�Ա��--USERID
	  ����״̬--FLOWSTATE
	  ���ױ�ע--FLOWMEDO
	  ������--FLOWWORKDAY
	  ���ױ��--DEALID
	**/
	private String flowNo = null;
	private float DealMoney = 0;
	private String CardId = null;
	private String UserId = null;
	private String DealState = null;
	private String DealDemo = null;
	private String workday = null;
	private String serverNo = null;
	
	public TellerWriteFlow(String flow_num, float flow_money, String card_id, String user_id,String flow_state,String flow_medo,String workday,String deal_id){
		this.flowNo = flow_num;
		this.DealMoney = flow_money;
		this.CardId = card_id;
		this.UserId = user_id;
		this.DealState = flow_state;
		this.DealDemo = flow_medo;
		this.workday = workday;
		this.serverNo = deal_id;
	}
	public boolean write(){
		boolean flag = false;
		Connection conn=ConnDB.getConnection();
		CallableStatement cs = null;
		ResultSet rs = null;
		
		try{
		cs = conn.prepareCall("{call TELLERliushui(?,?,?,?,?,?,?,?,?,?)}");
		//��?��ֵ
		cs.setString(1,this.flowNo);
		cs.setFloat(2,this.DealMoney);
		cs.setString(5,this.DealDemo);
		cs.setString(3, this.CardId);
		cs.setString(4,this.UserId);
		cs.setString(6,this.DealState);
		cs.setString(7,this.workday);
		cs.setString(8,this.serverNo);

		//ע�᷵�ر�־flag
		cs.registerOutParameter(9, oracle.jdbc.OracleTypes.INTEGER);
		//ע�᷵�صĽ����p_cursor
		cs.registerOutParameter(10, oracle.jdbc.OracleTypes.CURSOR);
		
		//ִ��
		cs.execute();
		//���շ��ر�־��true��ʾ�����ɹ���false��ʾ����ʧ��
		flag = cs.getBoolean(9);
		if(flag)
			System.out.println("Teller�ˣ���ˮ����ɹ���");
		rs=(ResultSet)cs.getObject(10);
		//���TELLER��ˮ��
		/*while(rs.next()){
			
			System.out.println("����ʱ��:"+rs.getString(1)+
					"  ��ˮ��:"+rs.getString(2)+"  ���׽��:"+rs.getFloat(3)+
					"  �����˻���:"+rs.getString(4)+"  ���׹�Ա��:"+rs.getString(5)+
					"  ����״̬:"+rs.getString(6)+"  ����״̬:"+rs.getString(7)+"  ����״̬:" +
							""+rs.getString(8)+"  ����״̬:"+rs.getString(9));
			
		}*/
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	} finally {
		try {
			if(rs != null){
				rs.close();
			}
			if(cs != null){
				cs.close();
			}
			if(conn != null ){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

		return flag;
	}
	
	
	public static void main(String[] args) {
		TellerWriteFlow flow = new TellerWriteFlow("112", 100, "card_id", "user_id", "demo", "state", "2011-8-25", "01");
		System.out.println(flow.write());
	}
}
