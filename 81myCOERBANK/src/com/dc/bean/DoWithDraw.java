package com.dc.bean;

import com.dc.DAO.AddFlowOrChangeState;
import com.dc.DAO.AddProof;
import com.dc.DAO.AddProofDetail;
import com.dc.DAO.ChangeAccountBalance;
import com.dc.DAO.CheckAccount;
import com.dc.DAO.FindSubject;
import com.dc.DAO.SplitString;


public class DoWithDraw implements DoSthBank{

	@Override
	public String doIt(String messages) {
		// TODO Auto-generated method stub
		String back = "BANK_LIST_ID=";
		String[][] mess = new SplitString(messages).getMessages();
		AddFlowOrChangeState addFlowOrChange = new AddFlowOrChangeState();
		int listId = addFlowOrChange.AddFlowToTable(messages);
		//�����ˮ��
		System.out.println("Bank�ˣ���ˮ�Ų���ɹ�");
		back = back + listId;
		CheckAccount checkAccount = new CheckAccount();
		boolean check = checkAccount.checkIt(mess[4][1], mess[5][1]);
		if(check){
			System.out.println("Bank�ˣ��û���ȷ��");
			ChangeAccountBalance changeBalance = new ChangeAccountBalance();
			String result = changeBalance.changeMoney(0, mess[4][1], Integer.parseInt(mess[6][1]));
			if(result.equals("0")){
				addFlowOrChange.changeFlowState(listId, "0");
				back = back + ";BANK_STATE=0;" +mess[0][0]+"="+mess[0][1]+";TELLER_FLOW="+mess[3][1];
				System.out.println("Bank�ˣ�ȡ��ɹ�");
				AddProof proof = new AddProof();
				int proofId = proof.addToTable("ȡ��", mess[1][1], listId);
				System.out.println("ƾ֤�ţ�"+proofId);
				FindSubject findSubject = new FindSubject();
				String[] subjects = findSubject.dealToFindSubject(mess[1][1]);
				System.out.println("Ӱ���Ŀ"+subjects[0]+"  "+subjects[1]);
				AddProofDetail proofDetail = new AddProofDetail();
				proofDetail.AddToTable(mess[1][1], proofId, subjects[1], subjects[0], Integer.parseInt(mess[6][1]));
				System.out.println("Bank�ˣ����˳ɹ�");
			}
		} else if(!check) {
			back = back + ";BANK_STATE=1;" +mess[0][0]+"="+mess[0][1]+";TELLER_FLOW="+mess[3][1];
		}
		
		return back;
	}

}
