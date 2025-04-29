package com.edutrack.currisub.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.adl.datamodels.SCODataManager;
import org.adl.datamodels.cmi.CMICommentsFromLms;
import org.adl.datamodels.cmi.CMICore;
import org.adl.datamodels.cmi.CMIInteractionData;
import org.adl.datamodels.cmi.CMIInteractions;
import org.adl.datamodels.cmi.CMILaunchData;
import org.adl.datamodels.cmi.CMIObjectiveData;
import org.adl.datamodels.cmi.CMIObjectives;
import org.adl.datamodels.cmi.CMIResponse;
import org.adl.datamodels.cmi.CMIScore;
import org.adl.datamodels.cmi.CMIStudentData;
import org.adl.datamodels.cmi.CMIStudentPreference;
import org.adl.datamodels.cmi.CMISuspendData;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.edutrack.common.UserBroker;
import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.common.dto.UserMemDTO;
import com.edutrack.currisub.dao.ScormStudyDAO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.struts.StrutsDispatchAction;
import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;

/*
 * @author Jamfam
 *
 * 과정 과목 연결 관리 
 */

public class ScormStudyAction extends StrutsDispatchAction{
	/**
	 * 
	 */
	public ScormStudyAction() {
		super();
		// TODO Auto-generated constructor stub
	}

    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private Configuration config = ConfigurationFactory.getInstance().getConfiguration();
	
	/**
	 * 강의리스트 히든프레임에 위치할 Scorm API Applet 을 호출한다.
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ActionForward scormAppletInclude(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("---------- AppletInclude Start --------------");
//		----- 메모리에서 정보 가져오기.
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    String userId = userMemDto.userId;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode,"");
	    int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
	    int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		model.put("systemCode",systemCode);
		model.put("pCourseId",pCourseId);
		log.debug("---------- AppletInclude End --------------");
		return forward(request, model, "/curri_contents/scormAppletInclude.jsp");
	}
	
	public void set_session(HttpServletRequest req, String cname, String cvalue) {
		   try {
		        HttpSession session = req.getSession();
		        cname = URLEncoder.encode(cname);
		        if (cvalue == null) {
		            session.removeAttribute(cname);
		        }
		        else {
		            session.setAttribute(cname, cvalue);
		        }
		    }
		    catch(IllegalStateException e) {
		        e.printStackTrace();
		    }
		}
	
	public String get_session(HttpServletRequest req, String cname) {
	    try {
	        HttpSession session = req.getSession(false);
	        String c_value = (String)session.getAttribute(cname);
	        if (c_value == null) {
	            c_value = "";
	        }
	        return URLDecoder.decode(c_value);
	    }
	    catch (IllegalStateException  e) {
	        return "";
	    }
	}
	
	public void getScormRte(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Map model) throws Exception{
		log.debug("---------- getScormRte Start --------------");
//		----- 메모리에서 정보 가져오기.
		UserMemDTO userMemDto = UserBroker.getUserInfo(request);
		CurriMemDTO curriMemDto = userMemDto.curriInfo;
	    String systemCode = userMemDto.systemCode;
	    String userType = userMemDto.userType;
	    String userId = userMemDto.userId;
	    String pCurriCode = StringUtil.nvl(curriMemDto.curriCode);
	    int pCurriYear = StringUtil.nvl(curriMemDto.curriYear,0);
	    int pCurriTerm = StringUtil.nvl(curriMemDto.curriTerm,0);
		String pCourseId = StringUtil.nvl(request.getParameter("pCourseId"));
		String scoID = get_session(request,"scoID");
		String credit = get_session(request,"credit");
		String lessonMode = get_session(request,"lessonMode");
		
		ScormStudyDAO 	scormStudyDao = new ScormStudyDAO();
		int retVal = 0;
		boolean logoutFlag;
	    SCODataManager scoData;
	    String homeDir = FileUtil.ABS_PATH;
	    String scoFile =  homeDir+ FileUtil.FILE_DIR+systemCode+"/temp_rtefiles/"+pCourseId+"/"+userId+"/"+scoID;
	    log.debug("__________%%%%%%%%%%% scoFile = "+scoFile);
	    ObjectInputStream inStream = new ObjectInputStream( request.getInputStream() );
	    ObjectOutputStream outStream = new ObjectOutputStream( response.getOutputStream() );
	    String command = (String)inStream.readObject();
	    log.debug("Command to LMSCMIServlet is: " + command);
		
	    if (command.equalsIgnoreCase("cmiputcat")) {
	        logoutFlag = false;
	        SCODataManager inSCOData = (SCODataManager)inStream.readObject();

			if (credit.equalsIgnoreCase("credit")) {
				log.debug("dataToDB");
	            retVal = scormStudyDao.dataToDB(inSCOData, systemCode,pCurriCode, pCurriYear, pCurriTerm, pCourseId,scoID, userId);
	        }
	        //deleteFile( scoFile );
	    }else if (command.equalsIgnoreCase("cmigetcat")) {
	        FileInputStream fi        = new FileInputStream( scoFile );
	        ObjectInputStream file_in = new ObjectInputStream( fi );
	        scoData = (SCODataManager)file_in.readObject();
	        scoData.getCore().setSessionTime("00:00:00.0");
	        file_in.close();
	        outStream.writeObject( scoData );
	    }
	    else { // invalid command sent, real LMS would handle this more gracefully
	        String err_msg = "invalid command";
	        outStream.writeObject( err_msg );
	    }
	   

	    inStream.close();
	    outStream.close();
	    //out.clearBuffer();
		log.debug("---------- getScormRte End --------------");
	}
	
	
	
	
	//	-----------------------------------------------------------------------------
	//  Handling SCO Data
	//  Ussage : HandleData(inSCOData, scoFile)
	//-----------------------------------------------------------------------------
	
	public void deleteFile( String scoFile ) {
	    try {
	
	        File theRTESCODataFile = new File( scoFile );
	        if ( theRTESCODataFile.exists()) {
	            theRTESCODataFile.delete();
	        }
	
	    } catch ( Exception e ) {
	        e.printStackTrace(); 
	    }
	} // end HandleData
	
	
	//	-----------------------------------------------------------------------------
	//  Handling SCO Data initializing
	//  Ussage : initSCOData(scoData, userID, courseID, scoID, op_year, op_term)
	//-----------------------------------------------------------------------------
	public SCODataManager initSCOData(SCODataManager tmpSCOData,String systemCode, String curriCode, int curriYear, int curriTerm,
			 String courseID, String scoID,String userID, String userName, String mastery_score,
	    String max_time_allowed, String time_limit_action, String data_from_lms, String Credit, String LessonMode) {
		log.debug("---------- initSCOData Start --------------");
	    try {
			log.debug("initSCOData");
	        // Build Core
	        CMIScore tmpScore   = new CMIScore();
	        tmpScore.setRaw("0");
	        tmpScore.setMax("0");
	        tmpScore.setMin("0");
	
	        CMICore tmpCore = new CMICore();
	        tmpCore.setStudentId(userID);
	        tmpCore.setStudentName(userName);
	        tmpCore.setLessonLocation( "" );  // "location"
	        //tmpCore.setCredit( "credit" );
	        tmpCore.setCredit(Credit);
	        tmpCore.setLessonStatus( "not attempted" );
	        tmpCore.setEntry( "ab-initio" );
	        tmpCore.setTotalTime( "00:00:00.0" );
	        //tmpCore.setLessonMode( "normal" );
	        tmpCore.setLessonMode(LessonMode);
	        tmpCore.setExit( "" );
	        tmpCore.setScore( tmpScore );
	        tmpSCOData.setCore( tmpCore );
	
	
	        // Build Suspend Data
	        CMISuspendData tmpSuspendData = new CMISuspendData();
	        tmpSuspendData.setSuspendData("");
	        tmpSCOData.setSuspendData(tmpSuspendData);
	
	        // Build Launch Data
	        CMILaunchData tmpLaunchData = new CMILaunchData();
	        tmpLaunchData.setLaunchData( data_from_lms );
	        tmpSCOData.setLaunchData(tmpLaunchData);
	
	        // Build Comments
	        CMICommentsFromLms tmpComments = new CMICommentsFromLms("");
	        tmpSCOData.setCommentsFromLMS(tmpComments);
	
	        // Build Objective Data
	
	        /*
	
	        CMIObjectives tmpObjectives = new CMIObjectives();
	        CMIObjectiveData tmpObjectiveData = new CMIObjectiveData();
	        tmpObjectiveData.setId("O001");
	        tmpObjectiveData.setScore(tmpScore);
	        tmpObjectiveData.setStatus("not attempted");
	        tmpObjectives.setObjectives(tmpObjectiveData,0);
	        tmpSCOData.setObjectives(tmpObjectives);
	        */
	
	        // Build Student Data
	        CMIStudentData tmpStudentData = new CMIStudentData();
	
	        tmpStudentData.setMasteryScore( mastery_score );
	        tmpStudentData.setMaxTimeAllowed( max_time_allowed );
	        tmpStudentData.setTimeLimitAction( time_limit_action );
	        tmpSCOData.setStudentData(tmpStudentData);
	
	        // Build Student Preference
	        CMIStudentPreference tmpStudentPreference = new CMIStudentPreference();
	        tmpStudentPreference.setAudio("3");
	        tmpStudentPreference.setLanguage("English");
	        tmpStudentPreference.setSpeed("4");
	        tmpStudentPreference.setText("10");
	        tmpSCOData.setStudentPreference(tmpStudentPreference);
	    } catch ( Exception e ) {
	          e.printStackTrace();
	    }
	    log.debug("---------- initSCOData End --------------");
	    return tmpSCOData;
	}
	
	//	-----------------------------------------------------------------------------
	//  Handling Exist SCO Data initialize
	//  Ussage : existSCOData(scoData, conn, userID, courseID, scoID)
	//-----------------------------------------------------------------------------
	public SCODataManager existSCOData(SCODataManager tmpSCOData,  String systemCode, String curriCode, int curriYear, int curriTerm,
			 String courseId, String scoId,String userId) {
		log.debug("---------- existSCOData Start --------------");

	    try {
	        
	        Hashtable cmiCore = new Hashtable();
	        Hashtable cmiDataComm = new Hashtable();
	        Hashtable cmiObjectives = new Hashtable();
	        Hashtable cmiStuData = new Hashtable();
	        Hashtable cmiStuPref = new Hashtable();
	        Hashtable cmiInteractions = new Hashtable();
	        Hashtable cmiInteractionObj = new Hashtable();
	        Hashtable cmiIntCorrectRes = new Hashtable();
	        ScormStudyDAO	scormStudyDao = new ScormStudyDAO();
	        cmiCore           = scormStudyDao.selectcmiCore(systemCode, curriCode, curriYear, curriTerm, courseId, scoId, userId);
	        int IDRef         = Integer.parseInt((String)cmiCore.get("IDRef"));
	        cmiDataComm       = scormStudyDao.selectcmiDataComm(IDRef);
	        cmiObjectives     = scormStudyDao.selectcmiObjectives(IDRef);
	        cmiStuData        = scormStudyDao.selectcmiStuData(IDRef);
	        cmiStuPref        = scormStudyDao.selectcmiStuPref(IDRef);
	        cmiInteractions   = scormStudyDao.selectcmiInteractions(IDRef);
	        if (cmiInteractions.size() > 0 ) {
	
	            for (int i=0;i<Integer.parseInt((String)cmiInteractions.get("Int_Count"));i++) {
	
	                if ((String)cmiInteractions.get("Int_Obj_ID" + i) != null && !((String)cmiInteractions.get("Int_Cor_Res_ID" + i)).equals("")) {
	                    cmiInteractionObj = scormStudyDao.selectcmiInteractionObj(IDRef, Integer.parseInt((String)cmiInteractions.get("Int_Obj_ID" + i)));
	                    cmiIntCorrectRes  = scormStudyDao.selectcmiIntCorrectRes(IDRef, Integer.parseInt((String)cmiInteractions.get("Int_Cor_Res_ID" + i)));
	                }
	            }
	        }
	
	        // Build Core
	        CMIScore tmpScore   = new CMIScore();
	        tmpScore.setRaw((String)cmiCore.get("Score_Raw"));
	        tmpScore.setMax((String)cmiCore.get("Score_Max"));
	        tmpScore.setMin((String)cmiCore.get("Score_Min"));
	
	        CMICore tmpCore = new CMICore();
	        tmpCore.setStudentId( userId );
	        tmpCore.setStudentName((String)cmiCore.get("Student_Name"));
	        //tmpCore.setStudentName( userID );
	        tmpCore.setLessonLocation((String)cmiCore.get("Lesson_Location"));
	        tmpCore.setCredit((String)cmiCore.get("Credit"));
	        tmpCore.setLessonStatus((String)cmiCore.get("Lesson_Status"));
	        tmpCore.setEntry((String)cmiCore.get("Core_Entry"));
	        tmpCore.setTotalTime((String)cmiCore.get("Total_Time"));
	        tmpCore.setLessonMode((String)cmiCore.get("Lesson_Mode"));
	        tmpCore.setExit((String)cmiCore.get("Core_Exit"));
	        tmpCore.setScore( tmpScore );
	        tmpSCOData.setCore( tmpCore );
	
	        if (cmiDataComm.size() > 0 ) {
	
	            // Build Suspend Data
	            CMISuspendData tmpSuspendData = new CMISuspendData();
	            tmpSuspendData.setSuspendData(StringUtil.nvl((String)cmiDataComm.get("Suspend_Data")));
	            tmpSCOData.setSuspendData(tmpSuspendData);
	            // Build Launch Data
	            CMILaunchData tmpLaunchData = new CMILaunchData();
	            tmpLaunchData.setLaunchData(StringUtil.nvl((String)cmiDataComm.get("Launch_Data")));
	            tmpSCOData.setLaunchData(tmpLaunchData);
	            // Build Comments
	            CMICommentsFromLms tmpComments = new CMICommentsFromLms(StringUtil.nvl((String)cmiDataComm.get("Cmts")));
	            tmpSCOData.setCommentsFromLMS(tmpComments);
	        }
	
	        // Build Objective Data
	
	        if (cmiObjectives.size() > 0 ) {
	
	            for (int i=0;i<Integer.parseInt((String)cmiObjectives.get("Obj_Count"));i++) {
	
	                CMIObjectives tmpObjectives = new CMIObjectives();
	                CMIObjectiveData tmpObjectiveData = new CMIObjectiveData();
	                tmpObjectiveData.setId(StringUtil.nvl((String)cmiObjectives.get("Obj_ID" + i)));
	                tmpObjectiveData.setScore(tmpScore);
	                tmpObjectiveData.setStatus(StringUtil.nvl((String)cmiObjectives.get("Obj_Status" + i)));
	                tmpObjectives.setObjectives(tmpObjectiveData,Integer.parseInt((String)cmiObjectives.get("Obj_Num" + i)));
	                tmpSCOData.setObjectives(tmpObjectives);
	            }
	        }
	
	        // Build Student Data
	        if (cmiStuData.size() > 0 ) {
	            CMIStudentData tmpStudentData = new CMIStudentData();
	            tmpStudentData.setMasteryScore(StringUtil.nvl((String)cmiStuData.get("Mastery_Score")));
	            tmpStudentData.setMaxTimeAllowed(StringUtil.nvl((String)cmiStuData.get("Max_Time_Allowed")));
	            tmpStudentData.setTimeLimitAction(StringUtil.nvl((String)cmiStuData.get("Time_Limit_Action")));
	            tmpSCOData.setStudentData(tmpStudentData);
	        }
	
	        // Build Student Preference
	        if (cmiStuPref.size() > 0 ) {
	            CMIStudentPreference tmpStudentPreference = new CMIStudentPreference();
	            tmpStudentPreference.setAudio(StringUtil.nvl((String)cmiStuPref.get("Stpr_Audio")));
	            tmpStudentPreference.setLanguage(StringUtil.nvl((String)cmiStuPref.get("Stpr_Language")));
	            tmpStudentPreference.setSpeed(StringUtil.nvl((String)cmiStuPref.get("Stpr_Speed")));
	            tmpStudentPreference.setText(StringUtil.nvl((String)cmiStuPref.get("Stpr_Text")));
	            tmpSCOData.setStudentPreference(tmpStudentPreference);
	        }
	
	        if (cmiInteractions.size() > 0 ) {
	
	            for (int i=0;i<Integer.parseInt((String)cmiInteractions.get("Int_Count"));i++) {
	
	                CMIInteractions tmpInteractions = new CMIInteractions();
	                CMIInteractionData tmpInteractionData = new CMIInteractionData();
	                tmpInteractionData.setID(StringUtil.nvl((String)cmiInteractions.get("Int_ID" + i)));
	                tmpInteractionData.setTime(StringUtil.nvl((String)cmiInteractions.get("Int_Time" + i)));
	                tmpInteractionData.setType(StringUtil.nvl((String)cmiInteractions.get("Int_Type" + i)));
	                tmpInteractionData.setWeighting(StringUtil.nvl((String)cmiInteractions.get("Int_Weighting" + i)));
	                tmpInteractionData.setStudentResponse(StringUtil.nvl((String)cmiInteractions.get("Int_Stu_Resp" + i)));
	                tmpInteractionData.setResult(StringUtil.nvl((String)cmiInteractions.get("Int_Result" + i)));
	                tmpInteractionData.setLatency(StringUtil.nvl((String)cmiInteractions.get("Int_Latency" + i)));
	
	                if (cmiInteractionObj.size() > 0 ) {
	
	                    for (int j=0;j<Integer.parseInt((String)cmiInteractionObj.get("Int_Obj_Count"));j++) {
	                        tmpInteractionData.setObjID(StringUtil.nvl((String)cmiInteractionObj.get("Int_Obj_N_ID" + j)), Integer.parseInt((String)cmiInteractionObj.get("Obj_Num" + j)));
	                    }
	                }
	                if (cmiIntCorrectRes.size() > 0 ) {
	
	                    for (int k=0;k<Integer.parseInt((String)cmiIntCorrectRes.get("Int_Cor_Res_Count"));k++) {
	                        CMIResponse inResponse = new CMIResponse();
	                        inResponse.setPattern(StringUtil.nvl((String)cmiIntCorrectRes.get("Int_Cor_Res_Pat" + k)));
	                        tmpInteractionData.setCorrectResponses(inResponse, Integer.parseInt((String)cmiIntCorrectRes.get("Int_Cor_Res_Num" + k)));
	                    }
	                }
	                tmpInteractions.setInteractions(tmpInteractionData,StringUtil.nvl((String)cmiInteractions.get("int_Num" + i),0) );
	                tmpSCOData.setInteractions(tmpInteractions);
	            }
	        }
	        
	    } catch ( Exception e ) {
	          e.printStackTrace();
	    }
	    log.debug("---------- existSCOData End --------------");
	    return tmpSCOData;
	}
	
	
	
}