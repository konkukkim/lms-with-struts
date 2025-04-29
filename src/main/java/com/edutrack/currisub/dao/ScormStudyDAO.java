package com.edutrack.currisub.dao;

import java.sql.SQLException;
import java.util.Hashtable;

import javax.sql.RowSet;

import org.adl.datamodels.SCODataManager;
import org.adl.datamodels.cmi.CMIComments;
import org.adl.datamodels.cmi.CMICommentsFromLms;
import org.adl.datamodels.cmi.CMICore;
import org.adl.datamodels.cmi.CMIInteractionData;
import org.adl.datamodels.cmi.CMIInteractions;
import org.adl.datamodels.cmi.CMILaunchData;
import org.adl.datamodels.cmi.CMIObjectiveData;
import org.adl.datamodels.cmi.CMIObjectives;
import org.adl.datamodels.cmi.CMIResponse;
import org.adl.datamodels.cmi.CMIStudentData;
import org.adl.datamodels.cmi.CMIStudentPreference;
import org.adl.datamodels.cmi.CMISuspendData;

import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/*
 * @author Bschoi
 *
 * 스콤 데이터 처리
 */
public class ScormStudyDAO extends AbstractDAO {


	public Hashtable selectcmiCore(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String ScoId, String UserId)  throws DAOException{

	    Hashtable tmpcmiCore = new Hashtable();
	    QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select idref, system_code, curri_code, curri_year, curri_term, course_id,  sco_id, user_id, user_name");
		sb.append(", lesson_location, credit, lesson_status, core_entry, score_raw, score_max, score_min, total_time");
		sb.append(", lesson_mode, core_exit, session_time from cmi_core ");
		sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ?");
		sb.append(" and sco_id = ? and user_id = ? ");
		sql.setSql(sb.toString());

		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		sql.setString(ScoId);
		sql.setString(UserId);
		log.debug("[selectcmiCore]" + sql.toString());
		RowSet rs = null;
	    try {
	    	rs = broker.executeQuery(sql);
	        if (rs.next()) {
	            tmpcmiCore.put("IDRef", StringUtil.nvl(Integer.toString(rs.getInt("idref"))));
	            tmpcmiCore.put("Student_Name", StringUtil.nvl(rs.getString("user_name")));
	            tmpcmiCore.put("Lesson_Location", StringUtil.nvl(rs.getString("lesson_location")));
	            tmpcmiCore.put("Credit", StringUtil.nvl(rs.getString("credit")));
	            tmpcmiCore.put("Lesson_Status", StringUtil.nvl(rs.getString("lesson_status")));
	            tmpcmiCore.put("Core_Entry", StringUtil.nvl(rs.getString("core_entry")));
	            tmpcmiCore.put("Score_Raw", StringUtil.nvl(Integer.toString(rs.getInt("score_raw"))));
	            tmpcmiCore.put("Score_Max", StringUtil.nvl(Integer.toString(rs.getInt("score_max"))));
	            tmpcmiCore.put("Score_Min", StringUtil.nvl(Integer.toString(rs.getInt("score_min"))));
	            tmpcmiCore.put("Total_Time", StringUtil.nvl(rs.getString("total_time")));
	            tmpcmiCore.put("Lesson_Mode", StringUtil.nvl(rs.getString("lesson_mode")));
	            tmpcmiCore.put("Core_Exit", StringUtil.nvl(rs.getString("core_exit")));
	            tmpcmiCore.put("Session_Time", StringUtil.nvl(rs.getString("session_time")));
	        }
	        rs.close();

	    } catch ( Exception e ) {
	        e.printStackTrace();
	    } finally {
	        if (rs  != null)  try{rs.close();}   catch(Exception e){}
	    }
	    return tmpcmiCore;
	}

	public Hashtable selectcmiDataComm(int IDRef)  throws DAOException{
	    Hashtable tmpcmiDataComm = new Hashtable();
	    QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select idref, suspend_data, launch_data, cmts, cmts_from_lms from cmi_data_comm where idref =  ? ");
		sql.setSql(sb.toString());
		sql.setInteger(IDRef);

		log.debug("[selectcmiDataComm]" + sql.toString());
		RowSet rs = null;
	    try {

	    	rs = broker.executeQuery(sql);
	        if (rs.next()) {
	            tmpcmiDataComm.put("Suspend_Data", StringUtil.nvl(rs.getString("suspend_data")));
	            tmpcmiDataComm.put("Launch_Data", StringUtil.nvl(rs.getString("launch_data")));
	            tmpcmiDataComm.put("Cmts", StringUtil.nvl(rs.getString("cmts")));
	            tmpcmiDataComm.put("Cmts_From_LMS", StringUtil.nvl(rs.getString("cmts_from_lms")));
	        }
	        rs.close();

	    } catch ( Exception e ) {
	          e.printStackTrace();
	    } finally {
	        if (rs  != null)  try{rs.close();}   catch(Exception e){}
	    }

	    return tmpcmiDataComm;
	}

	public Hashtable selectcmiObjectives(int IDRef) throws DAOException{

		Hashtable tmpcmiObjectives = new Hashtable();
	    QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select idref, obj_count, obj_id, obj_score_raw, obj_score_max, ");
	    sb.append(" obj_score_min, obj_status from cmi_objectives where idref =  ? ");
		sql.setSql(sb.toString());
		sql.setInteger(IDRef);

		log.debug("[selectcmiObjectives]" + sql.toString());
		RowSet rs = null;
	    try {
	    	rs = broker.executeQuery(sql);
	        int i = 0;
	        while (rs.next()) {
	            tmpcmiObjectives.put("Obj_Num" + i, StringUtil.nvl(Integer.toString(rs.getInt("obj_count"))));
	            tmpcmiObjectives.put("Obj_ID" + i, StringUtil.nvl(rs.getString("obj_id")));
	            tmpcmiObjectives.put("Obj_Status" + i, StringUtil.nvl(rs.getString("obj_id")));
	            tmpcmiObjectives.put("Obj_Count", Integer.toString(i));
	            i++;

	        }
	        rs.close();

	    } catch ( Exception e ) {
	          e.printStackTrace();
	    } finally {
	        if (rs  != null)  try{rs.close();}   catch(Exception e){}
	    }

	    return tmpcmiObjectives;
	}

	public Hashtable selectcmiStuData(int IDRef) throws DAOException{

	    Hashtable tmpcmiStuData = new Hashtable();
	    QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select idref, mastery_score, max_time_allowed, time_limit_action from cmi_stu_data where idref =  ? ");
		sql.setSql(sb.toString());
		sql.setInteger(IDRef);

		log.debug("[selectcmiObjectives]" + sql.toString());
		RowSet rs = null;
	    try {
	    	rs = broker.executeQuery(sql);
	        if (rs.next()) {
	            tmpcmiStuData.put("Mastery_Score", StringUtil.nvl(Integer.toString(rs.getInt("mastery_score"))));
	            tmpcmiStuData.put("Max_Time_Allowed", StringUtil.nvl(rs.getString("max_time_allowed")));
	            tmpcmiStuData.put("Time_Limit_Action", StringUtil.nvl(rs.getString("time_limit_action")));
	        }
	        rs.close();

	    } catch ( Exception e ) {
	          e.printStackTrace();
	    } finally {
	        if (rs  != null)  try{rs.close();}   catch(Exception e){}
	    }

	    return tmpcmiStuData;
	}

	public Hashtable selectcmiStuPref(int IDRef) throws DAOException{

	    Hashtable tmpcmiStuPref = new Hashtable();
	    QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select idref, stpr_audio, stpr_language, stpr_speed, stpr_text from cmi_stu_pref where idref =  ? ");
		sql.setSql(sb.toString());
		sql.setInteger(IDRef);

		log.debug("[selectcmiStuPref]" + sql.toString());
		RowSet rs = null;
	    try {
	    	rs = broker.executeQuery(sql);
	        if (rs.next()) {
	            tmpcmiStuPref.put("Stpr_Audio", StringUtil.nvl(Integer.toString(rs.getInt("stpr_audio"))));
	            tmpcmiStuPref.put("Stpr_Language", StringUtil.nvl(rs.getString("stpr_language")));
	            tmpcmiStuPref.put("Stpr_Speed", StringUtil.nvl(Integer.toString(rs.getInt("stpr_speed"))));
	            tmpcmiStuPref.put("Stpr_Text", StringUtil.nvl(Integer.toString(rs.getInt("stpr_text"))));
	        }
	        rs.close();
	    } catch ( Exception e ) {
	          e.printStackTrace();
	    } finally {
	        if (rs  != null)  try{rs.close();}   catch(Exception e){}
	    }

	    return tmpcmiStuPref;
	}

	public Hashtable selectcmiInteractions( int IDRef) throws DAOException{

	    Hashtable tmpcmiInteractions = new Hashtable();
	    QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select idref, int_num, int_id, int_obj_id, int_time, int_type, int_cor_res_id, int_weighting ");
	    sb.append(", int_stu_resp, int_result, int_latency from cmi_interactions where idref =  ? ");
		sql.setSql(sb.toString());
		sql.setInteger(IDRef);

		log.debug("[selectcmiInteractions]" + sql.toString());
		RowSet rs = null;
	    try {

	    	rs = broker.executeQuery(sql);
	        int i = 0;
	        while (rs.next()) {
	            tmpcmiInteractions.put("Int_Num" + i, StringUtil.nvl(Integer.toString(rs.getInt("int_num"))));
	            tmpcmiInteractions.put("Int_ID" + i, StringUtil.nvl(rs.getString("int_id")));
	            tmpcmiInteractions.put("Int_Obj_ID" + i, StringUtil.nvl(Integer.toString(rs.getInt("int_obj_id"))));
	            tmpcmiInteractions.put("Int_Time" + i, StringUtil.nvl(rs.getString("int_time")));
	            tmpcmiInteractions.put("Int_Type" + i, StringUtil.nvl(rs.getString("int_type")));
	            tmpcmiInteractions.put("Int_Cor_Res_ID" + i, StringUtil.nvl(Integer.toString(rs.getInt("int_cor_res_id"))));
	            tmpcmiInteractions.put("Int_Weighting" + i, StringUtil.nvl(Integer.toString(rs.getInt("int_weighting"))));
	            tmpcmiInteractions.put("Int_Stu_Resp" + i, StringUtil.nvl(rs.getString("int_stu_resp")));
	            tmpcmiInteractions.put("Int_Result" + i, StringUtil.nvl(rs.getString("int_result")));
	            tmpcmiInteractions.put("Int_Latency" + i, StringUtil.nvl(rs.getString("int_latency")));
	            tmpcmiInteractions.put("Int_Count", Integer.toString(i));
	            i++;
	        }
	        rs.close();

	    } catch ( Exception e ) {
	          e.printStackTrace();
	    } finally {
	        if (rs  != null)  try{rs.close();}   catch(Exception e){}
	    }

	    return tmpcmiInteractions;
	}

	public Hashtable selectcmiInteractionObj(int IDRef, int Int_Obj_ID) throws DAOException{

	    Hashtable tmpcmiInteractionObj = new Hashtable();
	    QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select idref, int_obj_num, int_obj_n_id, int_obj_id from cmi_interaction_obj where idref =  ?  and int_obj_id = ? ");
		sql.setSql(sb.toString());
		sql.setInteger(IDRef);
		sql.setInteger(Int_Obj_ID);

		log.debug("[selectcmiInteractionObj]" + sql.toString());
		RowSet rs = null;
	    try {
	    	rs = broker.executeQuery(sql);
	        int i = 0;
	        while (rs.next()) {
	            tmpcmiInteractionObj.put("Int_Obj_Num" + i, StringUtil.nvl(Integer.toString(rs.getInt("int_obj_num"))));
	            tmpcmiInteractionObj.put("Int_Obj_N_ID" + i, StringUtil.nvl(rs.getString("int_obj_n_id")));
	            tmpcmiInteractionObj.put("Int_Obj_Count", Integer.toString(i));
	            i++;
	        }
	        rs.close();

	    } catch ( Exception e ) {
	          e.printStackTrace();
	    } finally {
	        if (rs  != null)  try{rs.close();}   catch(Exception e){}
	    }

	    return tmpcmiInteractionObj;
	}



	public int dataToDB(SCODataManager inSCOData,String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String ScoId, String UserId) throws DAOException{
		log.debug("---------- dataToDB Start --------------");
		 int  Cnt = 0;
		 int retVal = 0;
		 int idRef = 0;
		 QueryStatement sql = new QueryStatement();
		 StringBuffer sb = new StringBuffer();
		 sb.append(" select idref ");
		 sb.append(" from cmi_core ");
		 sb.append(" where system_code = ? and curri_code = ? and curri_year = ? and curri_term = ? and course_id = ?");
		 sb.append(" and sco_id = ? and user_id = ? ");
		 sql.setSql(sb.toString());

		 sql.setString(SystemCode);
		 sql.setString(CurriCode);
		 sql.setInteger(CurriYear);
		 sql.setInteger(CurriTerm);
		 sql.setString(CourseId);
		 sql.setString(ScoId);
		 sql.setString(UserId);

		 log.debug("[dataToDB]" + sql.toString());
		 RowSet rs = null;
		 try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	idRef = rs.getInt("idref");
			 	log.debug("_________ idRef = "+idRef);
			 	retVal = updateSCOData(inSCOData, SystemCode, CurriCode, CurriYear, CurriTerm, CourseId, ScoId, UserId, idRef);
			 }else{
			 	retVal = insertSCOData(inSCOData, SystemCode, CurriCode, CurriYear, CurriTerm, CourseId, ScoId, UserId);
			 }
			 rs.close();
		 }catch(Exception e){
		      e.printStackTrace();
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			 try{
			  if(rs != null) rs.close();
			 }catch(SQLException e){
				throw new DAOException(e.getMessage());
			 }
		 }
		 log.debug("---------- dataToDB End --------------");
		 return retVal;
	}


	public int updateSCOData(SCODataManager inSCOData,String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String ScoId, String UserId, int IdRef) throws DAOException{
		int retVal = 0;
		log.debug("=++++++++++++++++++++++++++++++++++[updateSCOData]" );
		try{
			retVal 			= updateCmiCore(inSCOData, SystemCode, CurriCode, CurriYear, CurriTerm, CourseId, ScoId, UserId, IdRef);
			if(retVal > 0)
				retVal 		= updateCmiDataComm(inSCOData, UserId, IdRef);
			if(retVal > 0)
				retVal 		= updateCmiObjectives(inSCOData, UserId, IdRef);
			if(retVal > 0)
				retVal 		= updateCmiStuData(inSCOData, UserId, IdRef);
			if(retVal > 0)
				retVal 		= updateCmiStuPref(inSCOData, UserId, IdRef);
			if(retVal > 0)
				retVal 		= updateCmiInteractions(inSCOData, UserId, IdRef);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	public int updateCmiCore(SCODataManager inSCOData,String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String ScoId, String UserId, int IdRef) throws DAOException{
		int retVal = 0;
	    CMICore              lmsCore               = inSCOData.getCore();

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update cmi_core set ");
		sb.append(" user_name = ?, lesson_location = ?, credit = ?, lesson_status = ?, core_entry = ? , score_raw = ? ");
		sb.append(" , score_max = ? , score_min = ? ,total_time = ? , lesson_mode = ? , core_exit = ? , session_time = ? ");
		sb.append(" , mod_id = ? , mod_date =CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		sb.append(" where system_code=? and curri_code = ? and curri_year = ? and curri_term = ?  and course_id=? ");
		sb.append(" and sco_id = ? and user_id = ? and idref = ? ");
		sql.setSql(sb.toString());

		sql.setString(lmsCore.getStudentName().getValue());
		sql.setString(lmsCore.getLessonLocation().getValue());
		sql.setString(lmsCore.getCredit().getValue());
		sql.setString(lmsCore.getLessonStatus().getValue());
		sql.setString(lmsCore.getEntry().getValue());
		sql.setInteger(Integer.parseInt(lmsCore.getScore().getRaw().getValue()));
		sql.setInteger(Integer.parseInt(lmsCore.getScore().getMax().getValue()));
		sql.setInteger(Integer.parseInt(lmsCore.getScore().getMin().getValue()));
		sql.setString(lmsCore.getTotalTime().getValue());
		sql.setString(lmsCore.getLessonMode().getValue());
		sql.setString(lmsCore.getExit().getValue());
		sql.setString(lmsCore.getSessionTime().getValue());
		sql.setString(UserId);

		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		sql.setString(ScoId);
		sql.setString(UserId);
		sql.setInteger(IdRef);


		log.debug("[updateCmiCore]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	public int updateCmiDataComm(SCODataManager inSCOData,String UserId,  int IdRef) throws DAOException{
		int retVal = 0;
	    CMIComments          lmsComments           = inSCOData.getComments();
	    CMICommentsFromLms   lmsCommentsFromLms    = inSCOData.getCommentsFromLMS();
	    CMILaunchData        lmsLaunchData         = inSCOData.getLaunchData();
	    CMISuspendData       lmsSuspendData        = inSCOData.getSuspendData();

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update cmi_data_comm set ");
		sb.append(" suspend_data = ?, launch_data = ?, cmts = ?, cmts_from_lms = ? ");
		sb.append(" , mod_id = ? , mod_date =CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		sb.append(" where  idref = ? ");
		sql.setSql(sb.toString());

		sql.setString(lmsSuspendData.getSuspendData().getValue());
		sql.setString(lmsLaunchData.getLaunchData().getValue());
		sql.setString(lmsComments.getComments().getValue());
		sql.setString(lmsCommentsFromLms.getCommentsFromLms().getValue());
		sql.setString(UserId);
		sql.setInteger(IdRef);


		log.debug("[updateCmiDataComm]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	public int updateCmiObjectives(SCODataManager inSCOData,String UserId,  int IdRef) throws DAOException{
		int retVal = 0;
	    CMIObjectives        lmsObjectives         = inSCOData.getObjectives();

	    QueryStatement sql = null;
		StringBuffer sb = null;
		try{
		    CMIObjectiveData lmsObjectiveData = new CMIObjectiveData();
	        for (int i = 0; i < lmsObjectives.getObjectives().size() ; i++) {
	        	sql = new QueryStatement();
	    		sb = new StringBuffer();
	    		sb.append(" update cmi_objectives set ");
	    		sb.append(" obj_score_raw = ?, obj_score_max = ?, obj_score_min = ?, obj_status = ? ");
	    		sb.append(" , mod_id = ? , mod_date =CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
	    		sb.append(" where  idref = ? and obj_count = ? and obj_id = ? ");
	    		sql.setSql(sb.toString());

	    		sql.setInteger(Integer.parseInt(lmsObjectiveData.getScore().getRaw().getValue()));
	    		sql.setInteger(Integer.parseInt(lmsObjectiveData.getScore().getMax().getValue()));
	    		sql.setInteger(Integer.parseInt(lmsObjectiveData.getScore().getMin().getValue()));
	    		sql.setString(lmsObjectiveData.getStatus().getValue());
	    		sql.setString(UserId);
	    		sql.setInteger(IdRef);
	    		sql.setInteger(i);
	    		sql.setString(lmsObjectiveData.getId().getValue());
	    		log.debug("[updateCmiObjectives]" + sql.toString());
	    		retVal = broker.executeUpdate(sql);
	        }
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	public int updateCmiStuData(SCODataManager inSCOData,String UserId,  int IdRef) throws DAOException{
		int retVal = 0;
//	    CMICore              lmsCore               = inSCOData.getCore();
//	    CMIComments          lmsComments           = inSCOData.getComments();
//	    CMICommentsFromLms   lmsCommentsFromLms    = inSCOData.getCommentsFromLMS();
//	    CMIInteractions      lmsInteractions       = inSCOData.getInteractions();
//	    CMILaunchData        lmsLaunchData         = inSCOData.getLaunchData();
//	    CMIObjectives        lmsObjectives         = inSCOData.getObjectives();
	    CMIStudentData       lmsStudentData        = inSCOData.getStudentData();
//	    CMIStudentPreference lmsStudentPreference  = inSCOData.getStudentPreference();
//	    CMISuspendData       lmsSuspendData        = inSCOData.getSuspendData();

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update cmi_stu_data set ");
		sb.append(" mastery_score = ?, max_time_allowed = ?, time_limit_action = ? ");
		sb.append(" , mod_id = ? , mod_date =CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		sb.append(" where  idref = ? ");
		sql.setSql(sb.toString());

		sql.setInteger(Integer.parseInt(lmsStudentData.getMasteryScore().getValue()));
		sql.setString(lmsStudentData.getMaxTimeAllowed().getValue());
		sql.setString(lmsStudentData.getTimeLimitAction().getValue());

		sql.setString(UserId);
		sql.setInteger(IdRef);


		log.debug("[updateCmiStuData]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	public int updateCmiStuPref(SCODataManager inSCOData,String UserId,  int IdRef) throws DAOException{
		int retVal = 0;
	    CMIStudentPreference lmsStudentPreference  = inSCOData.getStudentPreference();
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update cmi_stu_pref set ");
		sb.append(" stpr_audio = ?, stpr_language = ?, stpr_speed = ? , stpr_text = ? ");
		sb.append(" , mod_id = ? , mod_date =CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
		sb.append(" where  idref = ? ");
		sql.setSql(sb.toString());

		sql.setInteger(Integer.parseInt(lmsStudentPreference.getAudio().getValue()));
		sql.setString(lmsStudentPreference.getLanguage().getValue());
		sql.setString(lmsStudentPreference.getSpeed().getValue());
		sql.setString(lmsStudentPreference.getText().getValue());
		sql.setString(UserId);
		sql.setInteger(IdRef);
		log.debug("[updateCmiStuPref]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	public int updateCmiInteractions(SCODataManager inSCOData,String UserId,  int IdRef) throws DAOException{
		int retVal = 0;
//	    CMICore              lmsCore               = inSCOData.getCore();
//	    CMIComments          lmsComments           = inSCOData.getComments();
//	    CMICommentsFromLms   lmsCommentsFromLms    = inSCOData.getCommentsFromLMS();
	    CMIInteractions      lmsInteractions       = inSCOData.getInteractions();
//	    CMILaunchData        lmsLaunchData         = inSCOData.getLaunchData();
//	    CMIObjectives        lmsObjectives         = inSCOData.getObjectives();
//	    CMIStudentData       lmsStudentData        = inSCOData.getStudentData();
//	    CMIStudentPreference lmsStudentPreference  = inSCOData.getStudentPreference();
//	    CMISuspendData       lmsSuspendData        = inSCOData.getSuspendData();

	    QueryStatement sql = null;
		StringBuffer sb = null;
		try{
			CMIInteractionData lmsInteractionData = new CMIInteractionData();
	        for (int i = 0; i < lmsInteractions.getInteractions().size() ; i++) {
	        	lmsInteractionData = (CMIInteractionData)lmsInteractions.getInteractions().get(i);
	        	sql = new QueryStatement();
	    		sb = new StringBuffer();
	    		sb.append(" update cmi_interactions set ");
	    		sb.append(" int_time = ?, int_type = ?, int_weighting = ?, int_stu_resp = ? , int_result = ?,  int_latency = ?");
	    		sb.append(" , mod_id = ? , mod_date =CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)");
	    		sb.append(" where  idref = ? and int_num = ? and int_id = ? ");
	    		sql.setSql(sb.toString());

	    		sql.setString(lmsInteractionData.getTime().getValue());
	    		sql.setString(lmsInteractionData.getType().getValue());
	    		sql.setString(lmsInteractionData.getWeighting().getValue());
	    		sql.setString(lmsInteractionData.getStudentResponse().getValue());
	    		sql.setString(lmsInteractionData.getResult().getValue());
	    		sql.setString(lmsInteractionData.getLatency().getValue());
	    		sql.setString(UserId);
	    		sql.setInteger(IdRef);
	    		sql.setInteger(i);
	    		sql.setString(lmsInteractionData.getID().getValue());
	    		log.debug("[updateCmiInteractions]" + sql.toString());
	    		retVal = broker.executeUpdate(sql);
	        }
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}







	public int insertSCOData(SCODataManager inSCOData,String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String ScoId, String UserId) throws DAOException{
		int retVal = 0;
		int IdRef = 0;
		log.debug("=++++++++++++++++++++++++++++++++++[insertSCOData]" );
		IdRef = selectHighestInt("cmi_core", "idref");
		log.debug("_________ idRef = "+IdRef);
		try{
			retVal 			= insertCmiCore(inSCOData, SystemCode, CurriCode, CurriYear, CurriTerm, CourseId, ScoId, UserId, IdRef);
			if(retVal > 0)
				retVal 		= insertCmiDataComm(inSCOData, UserId, IdRef);
			if(retVal > 0)
				retVal 		= insertCmiObjectives(inSCOData, UserId, IdRef);
			if(retVal > 0)
				retVal 		= insertCmiStuData(inSCOData, UserId, IdRef);
			if(retVal > 0)
				retVal 		= insertCmiStuPref(inSCOData, UserId, IdRef);
			if(retVal > 0)
				retVal 		= insertCmiInteractions(inSCOData, UserId, IdRef);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	public int insertCmiCore(SCODataManager inSCOData,String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String ScoId, String UserId, int IdRef) throws DAOException{
		int retVal = 0;
	    CMICore              lmsCore               = inSCOData.getCore();

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into cmi_core (idref, system_code, curri_code, curri_year, curri_term, course_id,  sco_id, user_id, user_name");
		sb.append(", lesson_location, credit, lesson_status, core_entry, score_raw, score_max, score_min, total_time");
		sb.append(", lesson_mode, core_exit, session_time, reg_id, reg_date) ");
		sb.append(" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) )");
		sql.setSql(sb.toString());

		sql.setInteger(IdRef);
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		sql.setString(ScoId);
		sql.setString(UserId);
		sql.setString(lmsCore.getStudentName().getValue());
		sql.setString(lmsCore.getLessonLocation().getValue());
		sql.setString(lmsCore.getCredit().getValue());
		sql.setString(lmsCore.getLessonStatus().getValue());
		sql.setString(lmsCore.getEntry().getValue());
		sql.setInteger(Integer.parseInt(lmsCore.getScore().getRaw().getValue()));
		sql.setInteger(Integer.parseInt(lmsCore.getScore().getMax().getValue()));
		sql.setInteger(Integer.parseInt(lmsCore.getScore().getMin().getValue()));
		sql.setString(lmsCore.getTotalTime().getValue());
		sql.setString(lmsCore.getLessonMode().getValue());
		sql.setString(lmsCore.getExit().getValue());
		sql.setString(lmsCore.getSessionTime().getValue());
		sql.setString(UserId);


		log.debug("[insertCmiCore]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	public int insertCmiDataComm(SCODataManager inSCOData,String UserId,  int IdRef) throws DAOException{
		int retVal = 0;
	    CMIComments          lmsComments           = inSCOData.getComments();
	    CMICommentsFromLms   lmsCommentsFromLms    = inSCOData.getCommentsFromLMS();
	    CMILaunchData        lmsLaunchData         = inSCOData.getLaunchData();
	    CMISuspendData       lmsSuspendData        = inSCOData.getSuspendData();

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into cmi_data_comm (idref, suspend_data, launch_data, cmts, cmts_from_lms, reg_id, reg_date) ");
		sb.append(" values(? , ? , ? , ? , ? , ? , CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) )");
		sql.setSql(sb.toString());

		sql.setInteger(IdRef);
		sql.setString(lmsSuspendData.getSuspendData().getValue());
		sql.setString(lmsLaunchData.getLaunchData().getValue());
		sql.setString(lmsComments.getComments().getValue());
		sql.setString(lmsCommentsFromLms.getCommentsFromLms().getValue());
		sql.setString(UserId);

		log.debug("[insertCmiDataComm]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	public int insertCmiObjectives(SCODataManager inSCOData,String UserId,  int IdRef) throws DAOException{
		int retVal = 0;
	    CMIObjectives        lmsObjectives         = inSCOData.getObjectives();

	    QueryStatement sql = null;
		StringBuffer sb = null;
		try{
			CMIObjectiveData lmsObjectiveData = new CMIObjectiveData();
	        for (int i = 0; i < lmsObjectives.getObjectives().size()  ; i++) {
	        	sql = new QueryStatement();
	    		sb = new StringBuffer();
	    		sb.append(" insert into cmi_objectives (idref, obj_count, obj_id, obj_score_raw, obj_score_max, ");
	    		sb.append(" obj_score_min, obj_status, reg_id, reg_date) values(");
	    		sb.append(" ? ,? ,? ,? ,? ,? ,?, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) )");
	    		sql.setSql(sb.toString());

	    		sql.setInteger(IdRef);
	    		sql.setInteger(i);
	    		sql.setString(lmsObjectiveData.getId().getValue());
	    		sql.setInteger(Integer.parseInt(lmsObjectiveData.getScore().getRaw().getValue()));
	    		sql.setInteger(Integer.parseInt(lmsObjectiveData.getScore().getMax().getValue()));
	    		sql.setInteger(Integer.parseInt(lmsObjectiveData.getScore().getMin().getValue()));
	    		sql.setString(lmsObjectiveData.getStatus().getValue());
	    		sql.setString(UserId);

	    		log.debug("[insertCmiObjectives]" + sql.toString());
	    		retVal = broker.executeUpdate(sql);
	        }
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	public int insertCmiStuData(SCODataManager inSCOData,String UserId,  int IdRef) throws DAOException{
		int retVal = 0;
	    CMIStudentData       lmsStudentData        = inSCOData.getStudentData();

		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into cmi_stu_data (idref, mastery_score, max_time_allowed, time_limit_action ");
        sb.append(", reg_id, reg_date) values(?, ?, ?, ?, ? , CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) )");
		sql.setSql(sb.toString());

		sql.setInteger(IdRef);
		sql.setInteger(Integer.parseInt(lmsStudentData.getMasteryScore().getValue()));
		sql.setString(lmsStudentData.getMaxTimeAllowed().getValue());
		sql.setString(lmsStudentData.getTimeLimitAction().getValue());
		sql.setString(UserId);

		log.debug("[insertCmiStuData]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	public int insertCmiStuPref(SCODataManager inSCOData,String UserId,  int IdRef) throws DAOException{
		int retVal = 0;
	    CMIStudentPreference lmsStudentPreference  = inSCOData.getStudentPreference();
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into cmi_stu_pref (idref, stpr_audio, stpr_language, stpr_speed, stpr_text ");
        sb.append(", reg_id, reg_date) values(");
		sb.append(" ?, ?, ?, ?, ?, ?, CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) )");
		sql.setSql(sb.toString());

		sql.setInteger(IdRef);
		sql.setInteger(Integer.parseInt(lmsStudentPreference.getAudio().getValue()));
		sql.setString(lmsStudentPreference.getLanguage().getValue());
		sql.setString(lmsStudentPreference.getSpeed().getValue());
		sql.setString(lmsStudentPreference.getText().getValue());
		sql.setString(UserId);

		log.debug("[insertCmiStuPref]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	public int insertCmiInteractions(SCODataManager inSCOData,String UserId,  int IdRef) throws DAOException{
		int retVal = 0;
	    CMIInteractions      lmsInteractions       = inSCOData.getInteractions();

	    QueryStatement sql = null;
		StringBuffer sb = null;
		try{
			CMIInteractionData lmsInteractionData = new CMIInteractionData();
	        for (int i = 0; i < lmsInteractions.getInteractions().size() ; i++) {
	        	lmsInteractionData = (CMIInteractionData)lmsInteractions.getInteractions().get(i);
	        	int int_obj_id     = selectHighestInt("cmi_interactions", "int_obj_id");
	            int int_cor_res_id = selectHighestInt("cmi_interactions", "int_cor_res_id");

	        	sql = new QueryStatement();
	    		sb = new StringBuffer();
	    		sb.append(" insert into cmi_interactions (idref, int_num, int_id, int_obj_id, int_time ");
	    		sb.append(", int_type, int_cor_res_id, int_weighting, int_stu_resp, int_result, int_latency ");
                sb.append(", reg_id, reg_date) values(");
	    		sb.append(" ? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? , CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) )");
	    		sql.setSql(sb.toString());

	    		sql.setInteger(IdRef);
	    		sql.setInteger(i);
	    		sql.setString(lmsInteractionData.getID().getValue());
	    		sql.setString(lmsInteractionData.getTime().getValue());
	    		sql.setString(lmsInteractionData.getType().getValue());
	    		sql.setString(lmsInteractionData.getWeighting().getValue());
	    		sql.setString(lmsInteractionData.getStudentResponse().getValue());
	    		sql.setString(lmsInteractionData.getResult().getValue());
	    		sql.setString(lmsInteractionData.getLatency().getValue());
	    		sql.setString(UserId);

	    		log.debug("[insertCmiInteractions]" + sql.toString());
	    		retVal = broker.executeUpdate(sql);

	    		for (int j = 0; j < lmsInteractionData.getObjID().size() ; j++) {
	                String tmpStrID = (String)lmsInteractionData.getObjID().get(j);
	                sql = new QueryStatement();
		    		sb = new StringBuffer();
		    		sb.append(" insert into cmi_interaction_obj (idref, int_obj_num, int_obj_n_id, int_obj_id");
	                sb.append(", reg_id, reg_date) values(");
		    		sb.append(" ? ,? ,? ,? ,? , CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) )");
		    		sql.setSql(sb.toString());
		    		sql.setInteger(IdRef);
		    		sql.setInteger(j);
		    		sql.setString(tmpStrID);
		    		sql.setInteger(int_obj_id);
		    		sql.setString(UserId);

		    		log.debug("[insertCmiInteractions ----- cmi_interaction_obj]" + sql.toString());
		    		retVal = broker.executeUpdate(sql);
	            }

	    		for (int k = 0; k < lmsInteractionData.getCorrectResponses().size() ; k++) {
	                String tmpStrRes = (((CMIResponse)lmsInteractionData.getCorrectResponses().get(k)).getPattern()).getValue();
	                sql = new QueryStatement();
		    		sb = new StringBuffer();
		    		sb.append(" insert into cmi_int_correct_res (idref, int_cor_res_num, int_cor_res_id");
	                sb.append(", int_cor_res_pat, reg_id, reg_date) values(");
		    		sb.append(" ? ,? ,? ,? ,? , CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR) )");
		    		sql.setSql(sb.toString());
		    		sql.setInteger(IdRef);
		    		sql.setInteger(k);
		    		sql.setInteger(int_cor_res_id);
		    		sql.setString(tmpStrRes);
		    		sql.setString(UserId);

		    		log.debug("[insertCmiInteractions ----- cmi_int_correct_res]" + sql.toString());
		    		retVal = broker.executeUpdate(sql);
	            }
	        }
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * Get a highest int of a column from a Table;
	 * Ussage : selectHighestInt(tableName, columnName)
	 * @param tableName
	 * @param columnName
	 * @return
	 * @throws DAOException
	 */
	public int selectHighestInt(String tableName, String columnName)throws DAOException {
	    int columnInt = 0;
		StringBuffer sb = new StringBuffer();
		sb.append(" select ifnull(max("+columnName+"),0) as column_int from "+tableName+"  ");
		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		log.debug("[selectHighestInt]" + sql.toString());
		RowSet rs = null;
		try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	columnInt = rs.getInt("column_int")+1;
			 }
			 rs.close();
		 }catch(Exception e){
		      e.printStackTrace();
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			 try{
			  if(rs != null) rs.close();
			 }catch(SQLException e){
				throw new DAOException(e.getMessage());
			 }
		 }
		 return columnInt;
	}

	/**
	 *   Handling select cmi_int_correct_res Data from DB
	 *  Ussage : selectcmiIntCorrectRes(m_conn, IDRef, Int_Cor_Res_ID)
	 * @param IDRef
	 * @param Int_Cor_Res_ID
	 * @return
	 */
	public Hashtable selectcmiIntCorrectRes(int IDRef, int Int_Cor_Res_ID) {
	    Hashtable tmpcmiIntCorrectRes = new Hashtable();
	    StringBuffer sb = new StringBuffer();
		sb.append(" select idref, int_cor_res_num, int_cor_res_id, int_cor_res_pat ");
		sb.append(" from cmi_int_correct_res where idref = ? and int_cor_res_id = ? ");
		QueryStatement sql = new QueryStatement();
		sql.setInteger(IDRef);
		sql.setInteger(Int_Cor_Res_ID);
		sql.setSql(sb.toString());
		log.debug("[selectcmiIntCorrectRes]" + sql.toString());
		RowSet rs = null;
	    try {
	    	rs = broker.executeQuery(sql);
	        int i = 0;
	        while (rs.next()) {
	            String tmpInt_Cor_Res_Num = Integer.toString(rs.getInt("int_cor_res_num"));
	            String tmpInt_Cor_Res_Pat  = StringUtil.nvl(rs.getString("int_cor_res_pat"));

	            tmpcmiIntCorrectRes.put("Int_Cor_Res_Num" + i, tmpInt_Cor_Res_Num);
	            tmpcmiIntCorrectRes.put("Int_Cor_Res_Pat" + i, tmpInt_Cor_Res_Pat);
	            tmpcmiIntCorrectRes.put("Int_Cor_Res_Count", Integer.toString(i));
	            i++;
	        }
	        rs.close();
	    } catch ( Exception e ) {
	          e.printStackTrace();
	    } finally {
	        if (rs  != null)  try{rs.close();}   catch(Exception e){}
	    }

	    return tmpcmiIntCorrectRes;
	}

	/**
	 * cmi_core 가 생성 되어 있는지 확인을 위해 갯수를 가져온다.
	 * @param SystemCode
	 * @param CurriCode
	 * @param CurriYear
	 * @param CurriTerm
	 * @param CourseId
	 * @param ScoId
	 * @param UserId
	 * @return
	 * @throws DAOException
	 */
	public int getCmiCoreCnt(String SystemCode, String CurriCode, int CurriYear, int CurriTerm, String CourseId, String ScoId, String UserId) throws DAOException{
	    int cnt = 0;
	    QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select ifnull(count(idref),0) as cnt from cmi_core  ");
		sb.append(" where system_code=? and curri_code = ? and curri_year = ? and curri_term = ?  and course_id=? ");
		sb.append(" and sco_id = ? and user_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(CurriCode);
		sql.setInteger(CurriYear);
		sql.setInteger(CurriTerm);
		sql.setString(CourseId);
		sql.setString(ScoId);
		sql.setString(UserId);
		log.debug("[getCmiCoreCnt]" + sql.toString());
		RowSet rs = null;
		try{
			 rs = broker.executeQuery(sql);
			 if(rs.next()){
			 	cnt = rs.getInt("cnt");
			 }
			 rs.close();
		 }catch(Exception e){
		      e.printStackTrace();
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			 try{
			  if(rs != null) rs.close();
			 }catch(SQLException e){
				throw new DAOException(e.getMessage());
			 }
		 }
		 return cnt;
	}

}