package com.edutrack.dept.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.code.dao.CodeDaeDAO;
import com.edutrack.code.dto.CodeDaeDTO;
import com.edutrack.common.CommonUtil;
import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.dept.dao.DeptDaeDAO;
import com.edutrack.dept.dao.DeptSoDAO;
import com.edutrack.dept.dto.DeptDaeCodeDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
import com.edutrack.framework.util.StringUtil;

/*
 * @author sej
 *
 * 소속코드 관리
 */

public class DeptDaeAction extends StrutsDispatchAction{

	public DeptDaeAction()  {
		super();
		// TODO Auto-generated constructor stub
	}

    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
    private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	/**
	 * 소속코드 (DAPT_DAECODE) 대코드 리스트로 이동한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward deptDaeList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		new SiteNavigation(pMode).add(request,"소속코드관리").link(model);
		return forward(request, model, "/dept/deptDaeList.jsp");
	}

	/**
	 * 소속코드정보 리스트를 받아온다 (Ajax)
	 * 2007.05.18 sangsang
	 * @param curPage
	 * @param col
	 * @param order
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO deptDaeListAuto(int curPage, String col, String order, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		// sorting
		col = StringUtil.nvl(col,"dept_daecode");
		order = StringUtil.nvl(order,"asc");

		// 페이징
		curPage = (curPage == 0) ? 1 : curPage;

		// 데이타를 담는다.
		ListDTO listObj = null;
		DeptDaeDAO deptDaeDao = new DeptDaeDAO();
		listObj = deptDaeDao.getDeptDaeList(curPage,systemCode,col,order);

		// 리턴 데이타 세팅
		AjaxListDTO ajaxListDto = new AjaxListDTO();
		ArrayList dataList = new ArrayList();
		DeptDaeCodeDTO deptDaeCodeDto = null;

		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				deptDaeCodeDto = new DeptDaeCodeDTO();
				deptDaeCodeDto.setDeptDaecode(StringUtil.nvl(rs.getString("dept_daecode")));
				deptDaeCodeDto.setDeptDaename(StringUtil.nvl(rs.getString("dept_daename")));
				deptDaeCodeDto.setUseYn(StringUtil.nvl(rs.getString("use_yn")));
				deptDaeCodeDto.setUseName(StringUtil.nvl(rs.getString("use_name")));
				deptDaeCodeDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				dataList.add(deptDaeCodeDto);
			}
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 글 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}
		return ajaxListDto;
	}

	/**
	 * 소속코드정보를 등록한다 (Ajax)
	 * @param regMode
	 * @param codeDae
	 * @param daeName
	 * @param comment
	 * @param useYn
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String deptDaeRegist(String regMode, String deptDaecode, String deptDaename, String useYn, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		DeptDaeCodeDTO deptDaeCodeDto = new DeptDaeCodeDTO();
		DeptDaeDAO deptDaeDao = new DeptDaeDAO();

		deptDaecode = StringUtil.nvl(deptDaecode);
		deptDaename = AjaxUtil.ajaxDecoding(StringUtil.nvl(deptDaename));
		useYn = StringUtil.nvl(useYn,"N");
		regMode = StringUtil.nvl(regMode,"Add");

		deptDaeCodeDto.setSystemCode(systemCode);
		deptDaeCodeDto.setDeptDaecode(deptDaecode);
		deptDaeCodeDto.setDeptDaename(deptDaename);
		deptDaeCodeDto.setUseYn(useYn);
		deptDaeCodeDto.setRegId(userId);

		int retVal = 0;
		if(regMode.equals("Add")){	// 입력모드
			retVal = deptDaeDao.addDeptDaeInfo(deptDaeCodeDto);
		}else if(regMode.equals("Edit")){	// 수정모드
			retVal = deptDaeDao.editDeptDaeInfo(deptDaeCodeDto);
		}else if(regMode.equals("Delete")){		// 삭제모드
			retVal = deptDaeDao.delDeptDaeInfo(systemCode,deptDaecode);
		}
		return String.valueOf(retVal);
	}

	/**
	 * 소속코드정보를 받아온다 (Ajax)
	 * 2007.05.18 sangsang
	 * @param codeDae
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public DeptDaeCodeDTO deptDaeInfo(String deptDaecode, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		DeptDaeCodeDTO deptDaeCodeDto = new DeptDaeCodeDTO();
		DeptDaeDAO deptDaeDao = new DeptDaeDAO();

		RowSet rs = deptDaeDao.getDeptDaeInfo(systemCode, deptDaecode);
		if(rs.next()){
			deptDaeCodeDto.setDeptDaecode(StringUtil.nvl(rs.getString("dept_daecode")));
			deptDaeCodeDto.setDeptDaename(StringUtil.nvl(rs.getString("dept_daename")));
			deptDaeCodeDto.setUseYn(StringUtil.nvl(rs.getString("use_yn")));
			deptDaeCodeDto.setDeptSoCnt(rs.getInt("dept_so_cnt"));
		}
		rs.close();

		return deptDaeCodeDto;
	}

	/**
	 * 입력시점에서 소속코드 중복 체크 (Ajax)
	 * 2007.05.18 sangsang
	 * @param codeDae
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String deptDaeCheck(String deptDaecode, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);
		DeptDaeDAO deptDaeDao = new DeptDaeDAO();
		int retVal = deptDaeDao.getDeptDaeCount(systemCode, deptDaecode);

		return  String.valueOf(retVal);
	}
}