/*
 * Created on 2004. 10. 12.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curribbs.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.edutrack.code.dao.CodeSoDAO;
import com.edutrack.code.dto.CodeSoDTO;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.curribbs.dao.CurriBbsCommentDAO;
import com.edutrack.curribbs.dto.CurriBbsCommentDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
import com.edutrack.framework.util.StringUtil;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CurriBbsCommentAction  extends StrutsDispatchAction{
	// 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	/**
	 * 댓글을 목록을 가져온다 (Ajax)
	 * 2007.04.07 sangsang
	 * @param curPageComment
	 * @param bbsType
	 * @param seqNo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO curriBbsCommentList(int curPageComment, String bbsType, int seqNo, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);

		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
        String curriCode = StringUtil.nvl(curriMemDto.curriCode);
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		bbsType = StringUtil.nvl(bbsType);

		// 페이징
		curPageComment = (curPageComment == 0) ? 1 : curPageComment;

		// 데이타를 담는다.
		ListDTO listObj = null;
		CurriBbsCommentDAO curriBbsCommentDao = new CurriBbsCommentDAO();
		listObj = curriBbsCommentDao.getCurriBbsCommentPagingList(curPageComment, systemCode, curriCode, curriYear, curriTerm, bbsType, seqNo, 5, 10);

		// 리턴 데이타 세팅
		AjaxListDTO ajaxListDto = new AjaxListDTO();
		ArrayList dataList = new ArrayList();
		CurriBbsCommentDTO curriBbsCommentDto = null;

		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				curriBbsCommentDto = new CurriBbsCommentDTO();
				curriBbsCommentDto.setCommNo(rs.getInt("comm_no"));
				curriBbsCommentDto.setContents(StringUtil.nvl(rs.getString("contents")));
				curriBbsCommentDto.setEmoticonNum(StringUtil.nvl(rs.getString("emoticon_num")));
				curriBbsCommentDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				curriBbsCommentDto.setRegName(StringUtil.nvl(rs.getString("reg_name")));
				curriBbsCommentDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				dataList.add(curriBbsCommentDto);
			}
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 글 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}
		return ajaxListDto;
	}

	/**
	 * 댓글을 등록한다 (Ajax)
	 * 2007.04.07 sangsang
	 * @param regMode
	 * @param bbsType
	 * @param seqNo
	 * @param commNo
	 * @param regName
	 * @param contents
	 * @param emoticon
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String curriBbsCommentRegist(String bbsType, int seqNo, String regName, String contents, String emoticon,  HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
        String curriCode = StringUtil.nvl(curriMemDto.curriCode);
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		CurriBbsCommentDAO curriBbsCommentDao = new CurriBbsCommentDAO();
		CurriBbsCommentDTO curriBbsCommentDto = new CurriBbsCommentDTO();
		int retVal = 0;
		int commNo = 0;
		contents = AjaxUtil.ajaxDecoding(contents);
		emoticon = StringUtil.nvl(emoticon);
		if(emoticon.equals("0") || emoticon.equals("")) {
			emoticon = "1";
		}

		regName = AjaxUtil.ajaxDecoding(regName);
		if(regName.equals(""))
			regName = UserBroker.getUserName(request);

		commNo = curriBbsCommentDao.getMaxCommNo(systemCode,curriCode, curriYear, curriTerm, bbsType, seqNo);

		curriBbsCommentDto.setSystemCode(systemCode);
		curriBbsCommentDto.setCurriCode(curriCode);
		curriBbsCommentDto.setCurriYear(curriYear);
		curriBbsCommentDto.setCurriTerm(curriTerm);
		curriBbsCommentDto.setBbsType(bbsType);
		curriBbsCommentDto.setSeqNo(seqNo);
		curriBbsCommentDto.setCommNo(commNo);
		curriBbsCommentDto.setContents(contents);
		curriBbsCommentDto.setEmoticonNum(emoticon);
		curriBbsCommentDto.setRegId(userId);
		curriBbsCommentDto.setRegName(regName);

		retVal = curriBbsCommentDao.addCurriBbsComment(curriBbsCommentDto);

		return String.valueOf(retVal);
	}

	/**
	 * 댓글을 삭제한다 (Ajax)
	 * 2007.04.07 sangsang
	 * @param bbsType
	 * @param seqNo
	 * @param commNo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String curriBbsCommentDelete(String bbsType, int seqNo, int commNo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);

		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
        String curriCode = StringUtil.nvl(curriMemDto.curriCode);
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		int retVal = 0;
		CurriBbsCommentDAO curriBbsCommentDao = new CurriBbsCommentDAO();
		retVal = curriBbsCommentDao.delCurriBbsComment(systemCode,curriCode, curriYear, curriTerm, bbsType,seqNo,commNo);

		return String.valueOf(retVal);
	}

	/**
	 * [ASAX] 댓글의 카운트
	 * @param bbsType
	 * @param seqNo
	 * @param commNo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public int curriBbsCommentCount(String bbsType, int seqNo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);

		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	    CurriMemDTO curriMemDto = userMemDto.curriInfo;
        String curriCode = StringUtil.nvl(curriMemDto.curriCode);
		int curriYear = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);

		int retVal = 0;
		CurriBbsCommentDAO curriBbsCommentDao = new CurriBbsCommentDAO();
		retVal	=	curriBbsCommentDao.getCurriBbsCommentCount(systemCode, curriCode, curriYear, curriTerm, bbsType, seqNo);

		return retVal;
	}

}
