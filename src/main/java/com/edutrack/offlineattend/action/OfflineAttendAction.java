package com.edutrack.offlineattend.action;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.SiteNavigation;
import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.currisub.dao.CurriContentsDAO;
import com.edutrack.currisub.dto.CurriContentsDTO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.offlineattend.dao.OfflineAttendDAO;

/**
 * @author bschoi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OfflineAttendAction extends StrutsDispatchAction{
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();

	/** Go offline attendance
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward offlineAttendList(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest request,HttpServletResponse httpServletResponse,Map model) throws Exception{
		String pMode    = StringUtil.nvl(request.getParameter("pMode"),LECTURE);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));

		model.put("pCourseId", pCourseId);

		new SiteNavigation(pMode).add(request,"출결관리").link(model);
		return forward(request, model, "/offline_attend/offlineAttendList.jsp");
	}


	/**
	 * Get offline attendance ....Ajax
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ArrayList offlineAttendListAuto(String pCourseId, int pPageNo, int columnCnt, HttpServletRequest request,HttpServletResponse httpServletResponse) throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    String userId   = userMemDto.userId;

		//----- 메모리에서 정보 가져오기.
	    String curriCode = StringUtil.nvl(curriMemDto.curriCode);
		int curriYear 	 = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm 	 = StringUtil.nvl(curriMemDto.curriTerm,0);
		//String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));

		CurriContentsDAO curriContentsDao = new CurriContentsDAO();
		OfflineAttendDAO offlineAttendDao = new OfflineAttendDAO();
		// 컨텐츠 목록(offline)
		ListDTO rsOffDate = offlineAttendDao.getCurriContentsPagingList(pPageNo, columnCnt, systemCode,curriCode,curriYear,curriTerm, pCourseId, "2");	// only offline lesson
		// 오프라인 출석 목록
		RowSet rsOffStu = offlineAttendDao.getOffStudentAttendList(systemCode, curriCode, curriYear, curriTerm, pCourseId);
		// 학습자 목록
		String pOrder = "";
		RowSet rsStudent = curriContentsDao.getLecStudyStatusList(systemCode, curriCode, curriYear, curriTerm, "", "" , pOrder);

		ArrayList arrColTitle = new ArrayList();
		ArrayList arrColList = new ArrayList();
		ArrayList arrKeyList = new ArrayList();
		CurriContentsDTO curriContentsDto = new CurriContentsDTO();


//		================================================
		int[] pageCnt = new int[2];
		pageCnt[0] = rsOffDate.getCurrentPage();
		pageCnt[1] = rsOffDate.getTotalPageCount();

		arrColTitle.add("ID");
		arrColTitle.add("이름");
		arrKeyList.add("");
		arrKeyList.add("");

		if (rsOffDate.getItemCount() > 0) {

			RowSet rs= rsOffDate.getItemList();
			while(rs.next()){
				curriContentsDto = new CurriContentsDTO();
				curriContentsDto.setStartDate(StringUtil.nvl(rs.getString("start_date")));
				curriContentsDto.setContentsId(StringUtil.nvl(rs.getString("contents_id")));
				arrColTitle.add(curriContentsDto.getStartDate());
				arrKeyList.add(curriContentsDto.getContentsId());
			}
			rs.close();
		}


		int cntP = arrColTitle.size();
		String[] colList  = new String[cntP];    // list

		int i=0;
		// 학생수만큼...
		while(rsStudent.next()){
			colList = new String[cntP];
			colList[0] = rsStudent.getString("user_id");
			colList[1] = rsStudent.getString("user_name");

			rsOffStu.beforeFirst();
			// while studnet offline attendance is exist
			while(rsOffStu.next()){
				if(!colList[0].equals(rsOffStu.getString("user_id"))) continue;

				for(int p=2;p<(cntP) ;p++){
					if(!arrColTitle.get(p).equals(rsOffStu.getString("start_date"))) continue;

					colList[p] = rsOffStu.getString("attendance");
				}

			}
			arrColList.add(colList);

			i++;

		}
		rsOffStu.close();
		rsStudent.close();
//================================================

		ArrayList arrColList2 = new ArrayList();
		arrColList2.add(arrColTitle);	// attendance date
		arrColList2.add(arrColList);	// attendance List
		arrColList2.add(arrKeyList);	// contents List
		arrColList2.add(pageCnt);		// paging management

		return arrColList2;
	}

	/**
	 * 출석정보를 저장한다..
	 * @param pCourseId
	 * @param pStuId
	 * @param pAttSt
	 * @param pConId
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public String offlineAttendAllRegist(String pCourseId, String pStuId, String pAttSt, String pConId, HttpServletRequest request,HttpServletResponse httpServletResponse) throws Exception{
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    String userId   = userMemDto.userId;
		String msg = "";

		//----- 메모리에서 정보 가져오기.
	    String curriCode = StringUtil.nvl(curriMemDto.curriCode);
		int curriYear 	 = StringUtil.nvl(curriMemDto.curriYear,0);
		int curriTerm 	 = StringUtil.nvl(curriMemDto.curriTerm,0);

		String[] aStuId = pStuId.split("!");
		String[] aAttSt = pAttSt.split("!");

		OfflineAttendDAO offlineAttendDao = new OfflineAttendDAO();
		boolean retVal = offlineAttendDao.addOfflineAttendAll(systemCode, curriCode, curriYear, curriTerm, pCourseId, aStuId, aAttSt, pConId, userId);

		if(retVal){
			msg = "출결정보를 성공적으로 저장하였습니다.";
		}else{
			msg = "출결정보를 저장하는데 실패하였습니다.";
		}

		return msg;

	}

}
