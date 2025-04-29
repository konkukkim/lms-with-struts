package com.edutrack.code.action;

import java.io.PrintWriter;
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
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
import com.edutrack.framework.util.StringUtil;

/*
 * @author sangsang
 * 2007.03.26
 *
 * 대코드 관리 with Ajax
 */

public class CodeDaeAction extends StrutsDispatchAction{
	/**
	 *
	 */
	public CodeDaeAction()  {
		super();
		// TODO Auto-generated constructor stub
	}

    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	/**
	 * 대코드 리스트 페이지로 이동한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward codeDaeList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		new SiteNavigation(pMode).add(request,"시스템관리").link(model);
		return forward(request, model, "/code/codeDaeList.jsp");
	}


	/**
	 * 대코드정보 리스트를 받아온다 (Ajax)
	 * 2007.05.18 sangsang
	 * @param curPage
	 * @param col
	 * @param order
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO codeDaeListAuto(int curPage, String col, String order, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		// sorting
		col = StringUtil.nvl(col,"code_dae");
		order = StringUtil.nvl(order,"asc");

		// 페이징
		curPage = (curPage == 0) ? 1 : curPage;

		// 데이타를 담는다.
		ListDTO listObj = null;
		CodeDaeDAO codeDaeDao = new CodeDaeDAO();
		listObj = codeDaeDao.getCodeDaeList(curPage,systemCode,col,order);

		// 리턴 데이타 세팅
		AjaxListDTO ajaxListDto = new AjaxListDTO();
		ArrayList dataList = new ArrayList();
		CodeDaeDTO codeDaeDto = null;

		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				codeDaeDto = new CodeDaeDTO();
				codeDaeDto.setCodeDae(StringUtil.nvl(rs.getString("code_dae")));
				codeDaeDto.setDaeName(StringUtil.nvl(rs.getString("dae_name")));
				codeDaeDto.setUseYn(StringUtil.nvl(rs.getString("use_yn")));
				codeDaeDto.setUseName(StringUtil.nvl(rs.getString("use_name")));
				codeDaeDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				dataList.add(codeDaeDto);
			}
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 글 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}
		return ajaxListDto;
	}

	/**
	 * 대코드정보를 등록한다 (Ajax)
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
	public String codeDaeRegist(String regMode, String codeDae, String daeName, String comment, String useYn, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		CodeDaeDAO codeDaeDao = new CodeDaeDAO();
		CodeDaeDTO codeDaeDto = new CodeDaeDTO();

		codeDae = StringUtil.nvl(codeDae);
		daeName = AjaxUtil.ajaxDecoding(StringUtil.nvl(daeName));
		comment = AjaxUtil.ajaxDecoding(StringUtil.nvl(comment));
		useYn = StringUtil.nvl(useYn,"N");
		regMode = StringUtil.nvl(regMode,"Add");

		codeDaeDto.setSystemCode(systemCode);
		codeDaeDto.setCodeDae(codeDae);
		codeDaeDto.setDaeName(daeName);
		codeDaeDto.setXcomment(comment);
		codeDaeDto.setUseYn(useYn);
		codeDaeDto.setRegId(userId);

		int retVal = 0;
		if(regMode.equals("Add")){	// 입력모드
			retVal = codeDaeDao.addCodeDaeInfo(codeDaeDto);
		}else if(regMode.equals("Edit")){	// 수정모드
			retVal = codeDaeDao.editCodeDaeInfo(codeDaeDto);
		}else if(regMode.equals("Delete")){		// 삭제모드
			retVal = codeDaeDao.delCodeDaeInfo(systemCode,codeDae);
		}
		return String.valueOf(retVal);
	}

	/**
	 * 대코드정보를 받아온다 (Ajax)
	 * 2007.05.18 sangsang
	 * @param codeDae
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public CodeDaeDTO codeDaeInfo(String codeDae, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		CodeDaeDTO codeDaeDto = new CodeDaeDTO();
		CodeDaeDAO codeDaeDao = new CodeDaeDAO();

		RowSet rs = codeDaeDao.getCodeDaeInfo(systemCode, codeDae);
		if(rs.next()){
			codeDaeDto.setCodeDae(StringUtil.nvl(rs.getString("code_dae")));
			codeDaeDto.setDaeName(StringUtil.nvl(rs.getString("dae_name")));
			codeDaeDto.setUseYn(StringUtil.nvl(rs.getString("use_yn")));
			codeDaeDto.setXcomment(StringUtil.nvl(rs.getString("code_comment")));
			codeDaeDto.setCodeSoCnt(rs.getInt("code_so_cnt"));
		}
		rs.close();

		return codeDaeDto;
	}

	/**
	 * 입력시점에서 대코드 중복 체크 (Ajax)
	 * 2007.05.18 sangsang
	 * @param codeDae
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String codeDaeCheck(String codeDae, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);
		CodeDaeDAO codeDaeDao = new CodeDaeDAO();
		int retVal = codeDaeDao.getCodeDaeCount(systemCode, codeDae);

		return  String.valueOf(retVal);
	}
}