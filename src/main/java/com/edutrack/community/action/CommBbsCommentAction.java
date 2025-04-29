/**
 *
 */
package com.edutrack.community.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CommMemDTO;
import com.edutrack.community.dao.CommBbsCommentDAO;
import com.edutrack.community.dto.CommBbsCommentDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.AjaxListDTO;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.AjaxUtil;
import com.edutrack.framework.util.StringUtil;

/**
 *
 *
 */
public class CommBbsCommentAction  extends StrutsDispatchAction{
	// 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	/**
	 * [2007.8.16] AJAX 코멘트 리스트
	 *
	 * @return
	 * @throws Exception
	 */
	public AjaxListDTO commBbsCommentList(int curPageComment, int bbsId, int seqNo, HttpServletRequest request, HttpServletResponse response) throws Exception{

		String systemCode = UserBroker.getSystemCode(request);
		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		String commName = commMemDto.commName;
		String commId = commMemDto.commId;
		String userLevel = commMemDto.userLevel;

		//-- 페이징
		curPageComment = (curPageComment == 0) ? 1 : curPageComment;

		// 데이타를 담는다.
		ListDTO listObj = null;
		CommBbsCommentDAO commbbsCommentDao = new CommBbsCommentDAO();
		listObj	=	commbbsCommentDao.getCommBbsCommentPagingList(curPageComment, systemCode, commId, bbsId, seqNo, 5, 10);

		// 리턴 데이타 세팅
		AjaxListDTO ajaxListDto = new AjaxListDTO();
		ArrayList dataList = new ArrayList();
		CommBbsCommentDTO commBbsCommentDto = null;
		if (listObj.getItemCount() > 0) {
			RowSet rs= listObj.getItemList();
			while(rs.next()){
				commBbsCommentDto	=	new CommBbsCommentDTO();
				commBbsCommentDto.setCommNo(rs.getInt("comm_no"));
				commBbsCommentDto.setContents(StringUtil.nvl(rs.getString("contents")));
				commBbsCommentDto.setEmoticonNum(StringUtil.nvl(rs.getString("emoticon_num")));
				commBbsCommentDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				commBbsCommentDto.setRegName(StringUtil.nvl(rs.getString("reg_name")));
				commBbsCommentDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

				dataList.add(commBbsCommentDto);
			}
			rs.close();

			ajaxListDto.setTotalDataCount(listObj.getStartPageNum());		// 전체 글 수
			ajaxListDto.setDataList(dataList);								// 데이타리스트
			ajaxListDto.setPagging(listObj.getPaggingAjax(systemCode,"goPage"));	// 페이징 스트링
		}
		return ajaxListDto;
	}

	/**
	 * [AJAX] 코멘트 글 갯수 가져오기
	 * @param bbsId
	 * @param seqNo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public int commBbsCommentCount(int bbsId, int seqNo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String	systemCode	=	UserBroker.getSystemCode(request);
		int		retVal		=	0;

		CommBbsCommentDAO commbbsCommentDao = new CommBbsCommentDAO();
		retVal	=	commbbsCommentDao.getBbsCommentCount(systemCode, bbsId, seqNo);

		return retVal;
	}

	/**
	 * [2007.8.16] AJAX 적용. 코멘트 등록
	 *
	 * @return
	 * @throws Exception
	 */
	public String commBbsCommentRegist(int bbsId, int seqNo, String regName, String contents, String emoticon, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String systemCode = UserBroker.getSystemCode(request);
		String userId = UserBroker.getUserId(request);

		CommMemDTO commMemDto = UserBroker.getCommInfo(request);
		String	commName = commMemDto.commName;
		int		commId = StringUtil.nvl(commMemDto.commId, 0);
		String	userLevel = commMemDto.userLevel;

		CommBbsCommentDAO commbbsCommentDao = new CommBbsCommentDAO();
		CommBbsCommentDTO commbbsCommentDto = new CommBbsCommentDTO();
		int retVal = 0;
		int commNo = 0;
		contents = AjaxUtil.ajaxDecoding(contents);
		emoticon = StringUtil.nvl(emoticon);
		if(emoticon.equals("") || emoticon.equals("0")) {
			emoticon	=	"1";
		}

		regName = AjaxUtil.ajaxDecoding(regName);

		if(regName.equals(""))
			regName = UserBroker.getUserName(request);
		int pCommNo = commbbsCommentDao.getMaxCommNo(systemCode, String.valueOf(commId), bbsId, seqNo);

		commbbsCommentDto.setSystemCode(systemCode);
		commbbsCommentDto.setCommId(commId);
		commbbsCommentDto.setBbsId(bbsId);
		commbbsCommentDto.setSeqNo(seqNo);
		commbbsCommentDto.setCommNo(pCommNo);
		commbbsCommentDto.setContents(contents);
		commbbsCommentDto.setEmoticonNum(emoticon);
		commbbsCommentDto.setRegId(userId);
		commbbsCommentDto.setRegName(regName);

		retVal	=	commbbsCommentDao.addBbsComment(commbbsCommentDto);
		return String.valueOf(retVal);
	}

	/**
	 * [2007.8.16] AJAX 적용. 코멘트 글 삭제하기
	 *
	 * @return
	 * @throws Exception
	 */
	public String commBbsCommentDelete(int bbsId, int seqNo, int commNo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String	systemCode	=	UserBroker.getSystemCode(request);
		String	userId		=	UserBroker.getUserId(request);

		CommMemDTO	commMemDto	=	UserBroker.getCommInfo(request);
		String		commName	=	commMemDto.commName;
		int			commId		=	StringUtil.nvl(commMemDto.commId, 0);
		String		userLevel	=	commMemDto.userLevel;
		int			retVal		=	0;

		CommBbsCommentDAO commbbsCommentDao = new CommBbsCommentDAO();
		retVal	=	commbbsCommentDao.delBbsComment(systemCode, commId, bbsId, seqNo, commNo);

		return String.valueOf(retVal);
	}


}
