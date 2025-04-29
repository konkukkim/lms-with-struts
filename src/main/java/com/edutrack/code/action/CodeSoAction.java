package com.edutrack.code.action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.code.dao.CodeDaeDAO;
import com.edutrack.code.dao.CodeSoDAO;
import com.edutrack.code.dto.CodeSoDTO;
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
 * 소코드 관리 with Ajax
 */

public class CodeSoAction extends StrutsDispatchAction{
	/**
	 *
	 */
	public CodeSoAction()  {
		super();
		// TODO Auto-generated constructor stub
	}
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	/**
	 * 대코드명을 가져온다.
	 * @param systemCode
	 * @param codeDae
	 * @return codeDaeName
	 * @throws Exception
	 */
	public String codeDaeName(String systemCode, String codeDae) throws Exception{
		CodeDaeDAO codeDaeDao = new CodeDaeDAO();
		RowSet rs = codeDaeDao.getCodeDaeInfo(systemCode, codeDae);
		rs.next();
		String codeDaeName = "<b>"+StringUtil.nvl(rs.getString("dae_name"))+" (DaeCode:"+StringUtil.nvl(rs.getString("code_dae"))+")</b>";
		rs.close();
		return codeDaeName;
	}

	/**
	 * 소코드 리스트 페이지로 이동.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward codeSoList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse, Map model) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);
		String userType = UserBroker.getUserType(request);
		String pMode = StringUtil.nvl(request.getParameter("pMode"),MYPAGE);

		String codeDae = StringUtil.nvl(request.getParameter("pCodeDae"));
		String codeDaeName = codeDaeName(systemCode, codeDae);
		model.put("codeDaeName", codeDaeName);
		model.put("codeDae", codeDae);

		new SiteNavigation(pMode).add(request,"시스템코드관리").link(model);
		return forward(request, model, "/code/codeSoList.jsp");
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
	public AjaxListDTO codeSoListAuto(int curPage, String col, String order, String codeDae, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		// sorting
		col = StringUtil.nvl(col,"code_so");
		order = StringUtil.nvl(order,"asc");

		// 페이징
		curPage = (curPage == 0) ? 1 : curPage;

		// 데이타를 담는다.
		ListDTO listObj = null;
		CodeSoDAO codeSoDao = new CodeSoDAO();
		listObj = codeSoDao.getCodeSoList(curPage, systemCode, codeDae, col, order);

		// 리턴 데이타 세팅
		AjaxListDTO ajaxListDto = new AjaxListDTO();
		ArrayList dataList = new ArrayList(1);
		CodeSoDTO codeSoDto = null;

		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				codeSoDto = new CodeSoDTO();
				codeSoDto.setCodeDae(StringUtil.nvl(codeDae));
				codeSoDto.setCodeSo(StringUtil.nvl(rs.getString("code_so")));
				codeSoDto.setSoName(StringUtil.nvl(rs.getString("so_name")));
				codeSoDto.setUseName(StringUtil.nvl(rs.getString("use_name")));
				codeSoDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				dataList.add(codeSoDto);
			}
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 데이타 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}
		return ajaxListDto;
	}

	/**
	 * 소코드 등록/수정/삭제(ajax)
	 * 2007.05.18 sangsang
	 * @param regMode
	 * @param codeDae
	 * @param codeSo
	 * @param soName
	 * @param comment
	 * @param useYn
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String codeSoRegist(String regMode, String codeDae, String codeSo, String soName, String comment, String useYn, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		codeDae = StringUtil.nvl(codeDae);
		codeSo = StringUtil.nvl(codeSo);
		soName = AjaxUtil.ajaxDecoding(StringUtil.nvl(soName));
		comment = AjaxUtil.ajaxDecoding(StringUtil.nvl(comment));
		useYn = StringUtil.nvl(useYn,"N");
		regMode = StringUtil.nvl(regMode,"Add");

		CodeSoDAO codeSoDao = new CodeSoDAO();
		CodeSoDTO codeSoDto = new CodeSoDTO();

		codeSoDto.setSystemCode(systemCode);
		codeSoDto.setCodeDae(codeDae);
		codeSoDto.setCodeSo(codeSo);
		codeSoDto.setSoName(soName);
		codeSoDto.setXcomment(comment);
		codeSoDto.setUseYn(useYn);
		codeSoDto.setRegId(userId);

		int retVal = 0;
		if(regMode.equals("Add")){	// 입력모드
			retVal = codeSoDao.addCodeSoInfo(codeSoDto);
		}else if(regMode.equals("Edit")){	// 수정모드
			retVal = codeSoDao.editCodeSoInfo(codeSoDto);
		}else if(regMode.equals("Delete")){		// 삭제모드
			retVal = codeSoDao.delCodeSoInfo(systemCode,codeDae, codeSo);
		}
		return String.valueOf(retVal);
	}

	/**
	 * 소코드정보를 받아온다 (Ajax)
	 * 2007.05.18 sangsang
	 * @param codeDae
	 * @param codeSo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public CodeSoDTO codeSoInfo(String codeDae, String codeSo, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		CodeSoDAO codeSoDao = new CodeSoDAO();
		CodeSoDTO codeSoDto = new CodeSoDTO();

		RowSet rs = codeSoDao.getCodeSoInfo(systemCode, codeDae, codeSo);
		if(rs.next()){
			codeSoDto.setCodeSo(StringUtil.nvl(rs.getString("code_so")));
			codeSoDto.setSoName(StringUtil.nvl(rs.getString("so_name")));
			codeSoDto.setUseYn(StringUtil.nvl(rs.getString("use_yn")));
			codeSoDto.setXcomment(StringUtil.nvl(rs.getString("code_comment")));
		}
		rs.close();

		return codeSoDto;
	}

	/**
	 * 입력시점에서 소코드 중복 체크 (Ajax)
	 * 2007.05.18 sangsang
	 * @param codeDae
	 * @param codeSo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String codeSoCheck(String codeDae, String codeSo, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String systemCode = UserBroker.getSystemCode(request);
		CodeSoDAO codeSoDao = new CodeSoDAO();
		int retVal = codeSoDao.getCodeSoCount(systemCode, codeDae, codeSo);

		return  String.valueOf(retVal);
	}
}