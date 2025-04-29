package com.edutrack.currisub.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edutrack.common.UserBroker;
import com.edutrack.common.dao.CommonDAO;
import com.edutrack.currisub.dao.CurriSubJogyoDAO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.struts.StrutsDispatchAction;

/*
 * @author Jamfam
 *
 * 개설과정 관리
 */

public class CurriSubJogyoAction extends StrutsDispatchAction{
	/**
	 *
	 */
	public CurriSubJogyoAction() {
		super();
		// TODO Auto-generated constructor stub
	}
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();


	/**
	 * 모든 조교자 목록을 가져온다.
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ArrayList curriSubJogyoListAuto(String curriCode, int curriYear, int curriTerm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
		
		ArrayList arrList = new ArrayList();
		String systemCode = UserBroker.getSystemCode(request);

		CurriSubJogyoDAO curriSubJogyoDao = new CurriSubJogyoDAO();
		
		arrList = curriSubJogyoDao.getCurriSubJogyoList(systemCode, curriCode, curriYear, curriTerm);
		
		return arrList;
	}
	
	/**
	 * 모든 조교자 목록을 가져온다.(all)
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ArrayList curriSubJogyoSelectAuto(HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{

		ArrayList arrList = new ArrayList();
		String systemCode = UserBroker.getSystemCode(request);

		CommonDAO commonDao = new CommonDAO();
		arrList = commonDao.getUserList(systemCode,"J");
		
		return arrList;
	}
	
	
	/**
	 * 조교를 등록한다
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param jogyoId
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ArrayList curriSubJogyoRegistAuto(String curriCode, int curriYear, int curriTerm, String[] jogyoId, String jogyoNm, String selboxId, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);
		ArrayList aList = new ArrayList();
		
		CurriSubJogyoDAO curriSubJogyoDao = new CurriSubJogyoDAO();

		aList.add(jogyoId);
		aList.add(jogyoNm);
		aList.add(selboxId);

		try{

			if(curriSubJogyoDao.addCurriSubJogyo(systemCode, curriCode, curriYear, curriTerm, jogyoId, regId)){
				aList.add("0");
			}
			else {
				aList.add("-1");
			}
			
		}catch(Exception e){
			aList.add("-1");
		}
			
		return aList;
	}
	
	
	/**
	 * 조교자를 삭제한다
	 * @param curriCode
	 * @param curriYear
	 * @param curriTerm
	 * @param jogyoId
	 * @param selboxId
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ArrayList curriSubJogyoDelAuto(String curriCode, int curriYear, int curriTerm, String jogyoId, String selboxId, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String regId = UserBroker.getUserId(request);
		ArrayList aList = new ArrayList();
		
		CurriSubJogyoDAO curriSubJogyoDao = new CurriSubJogyoDAO();

		aList.add(selboxId);
		
		try{

			if(curriSubJogyoDao.delCurriSubJogyo(systemCode, curriCode, curriYear, curriTerm, jogyoId)>0){
				aList.add("0");
			}
			else {
				aList.add("-1");
			}
			
		}catch(Exception e){
			aList.add("-1");
		}
			
		return aList;
	}
	
}