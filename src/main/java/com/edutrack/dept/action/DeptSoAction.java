/*
 * Created on 2004. 10. 7.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.dept.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.curritop.dao.ContentsDAO;
import com.edutrack.curritop.dto.ContentsDTO;
import com.edutrack.dept.dao.DeptDaeDAO;
import com.edutrack.dept.dao.DeptSoDAO;
import com.edutrack.dept.dto.DeptSoCodeDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.MailUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.user.action.UserHelper;
import com.edutrack.user.dao.UserAdminDAO;
import com.edutrack.user.dao.UserDAO;
import com.edutrack.user.dto.UsersDTO;


/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DeptSoAction extends StrutsDispatchAction{

	/**
	 *
	 */
	public DeptSoAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	/**
	 * 소속 대코드명을 가져온다.
	 * @param systemCode
	 * @param deptDaecode
	 * @return deptDaename
	 * @throws Exception
	 */
	public String deptDaename(String systemCode, String deptDaecode) throws Exception{
		DeptDaeDAO deptDaeDao = new DeptDaeDAO();
		RowSet rs = deptDaeDao.getDeptDaeInfo(systemCode, deptDaecode);
		rs.next();
		String deptDaename = "<b>"+StringUtil.nvl(rs.getString("dept_daename"))+" (code:"+StringUtil.nvl(rs.getString("dept_daecode"))+")</b>";
		rs.close();
		return deptDaename;
	}


	/**
	 * 소속코드 (DAPT_SOCODE) 소코드 리스트 페이지로 이동..
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward deptSoList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception {
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		String deptDaecode = request.getParameter("pDeptDaecode");
		String deptDaename = deptDaename(systemCode, deptDaecode);

		model.put("deptDaename", deptDaename);
		model.put("deptDaecode", deptDaecode);

		new SiteNavigation(pMode).add(request,"소속코드관리").link(model);
		return forward(request, model, "/dept/deptSoList.jsp");

	}

	/**
	 * 소코드 리스트 (ajax)
	 * 2007.05.18 sangsang
	 * @param curPage
	 * @param col
	 * @param order
	 * @param codeDae
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO deptSoListAuto(int curPage, String col, String order, String deptDaecode, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		// sorting
		col = StringUtil.nvl(col,"dept_socode");
		order = StringUtil.nvl(order,"asc");

		// 페이징
		curPage = (curPage == 0) ? 1 : curPage;

		// 데이타를 담는다.
		ListDTO listObj = null;
		DeptSoDAO deptSoDao = new DeptSoDAO();
		listObj = deptSoDao.getDeptSoList(curPage, systemCode, deptDaecode, col, order);

		// 리턴 데이타 세팅
		AjaxListDTO ajaxListDto = new AjaxListDTO();
		ArrayList dataList = new ArrayList(1);
		DeptSoCodeDTO deptSoCodeDto = null;

		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				deptSoCodeDto = new DeptSoCodeDTO();
				deptSoCodeDto.setDeptDaecode(StringUtil.nvl(deptDaecode));
				deptSoCodeDto.setDeptSocode(StringUtil.nvl(rs.getString("dept_socode")));
				deptSoCodeDto.setDeptSoname(StringUtil.nvl(rs.getString("dept_soname")));
				deptSoCodeDto.setUseName(StringUtil.nvl(rs.getString("use_name")));
				deptSoCodeDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				dataList.add(deptSoCodeDto);
			}
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 데이타 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}
		return ajaxListDto;
	}

	/**
	 * 소코드 등록/수정/삭제 (ajax)
	 * 2007.05.18 sangsang
	 * @param deptSoCodeDto
	 * @param regMode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String deptSoRegist(DeptSoCodeDTO deptSoCodeDto, String regMode, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		regMode = StringUtil.nvl(regMode);

		String deptDaecode = StringUtil.nvl(deptSoCodeDto.getDeptDaecode());
		String deptSocode = StringUtil.nvl(deptSoCodeDto.getDeptSocode());
		String deptSoname = AjaxUtil.ajaxDecoding(deptSoCodeDto.getDeptSoname());
		String address = AjaxUtil.ajaxDecoding(deptSoCodeDto.getAddress());
		String position = AjaxUtil.ajaxDecoding(deptSoCodeDto.getPosition());

		deptSoCodeDto.setSystemCode(systemCode);
		deptSoCodeDto.setDeptSoname(deptSoname);
		deptSoCodeDto.setAddress(address);
		deptSoCodeDto.setPosition(position);

		DeptSoDAO deptSoDao = new DeptSoDAO();

		int retVal = 0;
		if(regMode.equals("ADD")){	// 입력모드
			deptSoCodeDto.setRegId(userId);
			retVal = deptSoDao.addDeptSoInfo(deptSoCodeDto); ;

		}else if(regMode.equals("EDIT")){	// 수정모드
			deptSoCodeDto.setModId(userId);
			retVal = deptSoDao.editDeptSoInfo(deptSoCodeDto);

		}else if(regMode.equals("DEL")){	// 삭제모드
			retVal = deptSoDao.delDeptSoInfo(systemCode, deptDaecode, deptSocode);
		}

		return String.valueOf(retVal);
	}

	/**
	 * 소코드정보를 받아온다 (Ajax)
	 * 2007.05.18 sangsang
	 * @param deptDaecode
	 * @param deptSocode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public DeptSoCodeDTO deptSoInfo(String deptDaecode, String deptSocode, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		DeptSoDAO deptSoDao = new DeptSoDAO();
		DeptSoCodeDTO deptSoCodeDto = new DeptSoCodeDTO();

		RowSet rs = deptSoDao.getDeptSoInfo(systemCode, deptDaecode, deptSocode);
		if(rs.next()){

			deptSoCodeDto.setDeptDaecode(StringUtil.nvl(deptDaecode ));
			deptSoCodeDto.setDeptSocode(StringUtil.nvl(deptSocode  ));
			deptSoCodeDto.setDeptSoname(StringUtil.nvl(rs.getString("dept_soname")));
			deptSoCodeDto.setUserId(StringUtil.nvl(rs.getString("user_id")));
		    deptSoCodeDto.setPostCode(StringUtil.nvl(rs.getString("post_code")));
		    deptSoCodeDto.setAddress(StringUtil.nvl(rs.getString("address")));
		    deptSoCodeDto.setPhone(StringUtil.nvl(rs.getString("phone")));
		    deptSoCodeDto.setPosition(StringUtil.nvl(rs.getString("position")));
		    deptSoCodeDto.setUseYn(StringUtil.nvl(rs.getString("use_yn")));
		}
		rs.close();

		return deptSoCodeDto;
	}

	/**
	 * 입력시점에서 소코드 중복 체크 (Ajax)
	 * 2007.05.18 sangsang
	 * @param deptDaecode
	 * @param deptSocode
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String deptSoCheck(String deptDaecode, String deptSocode, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);
		DeptSoDAO deptSoDao = new DeptSoDAO();
		int retVal = deptSoDao.getDeptSoCount(systemCode, deptDaecode, deptSocode);

		return  String.valueOf(retVal);
	}
}
