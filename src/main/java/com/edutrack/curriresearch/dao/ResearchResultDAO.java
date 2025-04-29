/*
 * Created on 2004. 10. 26.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curriresearch.dao;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.RowSet;

import com.edutrack.common.dto.CurriMemDTO;
import com.edutrack.curriresearch.dto.CurriResContentsDTO;
import com.edutrack.curriresearch.dto.ResResultDataDTO;
import com.edutrack.curriresearch.dto.ResUserResultDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResearchResultDAO  extends AbstractDAO {

	/**
	 *
	 */
	public ResearchResultDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 설문리스트와 정보를 가져온다.
	 * @param systemcode
	 * @param curriinfo
	 * @param courseid
	 * @param resid
	 * @return
	 * @throws DAOException
	 */
	public RowSet getResearchResultList(String systemcode,CurriMemDTO curriinfo) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select res_id,subject,contents,start_date,end_date,open_yn,reg_id,reg_date ");
			sb.append(" from curri_res_info ");
			sb.append(" where system_code = ? and curri_code = ? and curri_year =? and curri_term =? and open_yn = 'Y'");
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);

			sql.setSql(sb.toString());

			log.debug("[getResearchInofList]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
	     return rs;
	}

	/**
	 * 설문 참여자 리스트를 가져온다.
	 * @param systemcode
	 * @param curriinfo
	 * @return
	 * @throws DAOException
	 */
	public RowSet getResearchUserList(String systemcode,CurriMemDTO curriinfo,int resid) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select distinct s.user_id,(select user_name from users where system_code = s.system_code and user_id = s.user_id) user_name,");
			sb.append(" s.enroll_status,ifnull(a.user_id,'') res_user_id , ifnull(a.reg_date,'') answer_date ");
			sb.append(" from student s left outer join curri_res_answer a on s.system_code = a.system_code and s.curri_code = a.curri_code ");
			sb.append(" and s.curri_year = a.curri_year and s.curri_term = a.curri_term and s.user_id = a.user_id and a.res_id = ?");
			sb.append(" where s.system_code = ? and s.curri_code = ? and s.curri_year = ? and s.curri_term = ? and s.enroll_status = 'S' ");
			sql.setInteger(resid);
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setSql(sb.toString());

			log.debug("[getExamResultList]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
	     return rs;
	}

	public ArrayList getResearchResult(String systemcode,CurriMemDTO curriinfo,int resid) throws DAOException{
		ArrayList contentsList = new ArrayList();
		RowSet rs = null, rs2 = null;

		try{
           // 설문내용을 가져온다.
			ResearchContentsDAO contentsDao = new ResearchContentsDAO();
			rs = contentsDao.getResearchContentsList(systemcode, curriinfo,resid,0);
			CurriResContentsDTO contentsInfo = null;
			int count = 0;
			while(rs.next()){
				count = 0;
	           	contentsInfo = new CurriResContentsDTO();
				contentsInfo.setResNo(rs.getInt("res_no"));
				contentsInfo.setContents(StringUtil.nvl(rs.getString("contents")));
				contentsInfo.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
				contentsInfo.setExample1(StringUtil.nvl(rs.getString("example1")));
				if(!contentsInfo.getExample1().equals("")) count++;
				contentsInfo.setExample2(StringUtil.nvl(rs.getString("example2")));
				if(!contentsInfo.getExample2().equals("")) count++;
				contentsInfo.setExample3(StringUtil.nvl(rs.getString("example3")));
				if(!contentsInfo.getExample3().equals("")) count++;
				contentsInfo.setExample4(StringUtil.nvl(rs.getString("example4")));
				if(!contentsInfo.getExample4().equals("")) count++;
				contentsInfo.setExample5(StringUtil.nvl(rs.getString("example5")));
				if(!contentsInfo.getExample5().equals("")) count++;
				contentsInfo.setExample6(StringUtil.nvl(rs.getString("example6")));
				if(!contentsInfo.getExample6().equals("")) count++;
				contentsInfo.setExample7(StringUtil.nvl(rs.getString("example7")));
				if(!contentsInfo.getExample7().equals("")) count++;
				contentsInfo.setExample8(StringUtil.nvl(rs.getString("example8")));
				if(!contentsInfo.getExample8().equals("")) count++;
				contentsInfo.setExample9(StringUtil.nvl(rs.getString("example9")));
				if(!contentsInfo.getExample9().equals("")) count++;
				contentsInfo.setExample10(StringUtil.nvl(rs.getString("example10")));
				if(!contentsInfo.getExample10().equals("")) count++;
				contentsInfo.setExampleNum(StringUtil.nvl(rs.getString("example_num")));
				contentsInfo.setExampleCnt(count);
				contentsList.add(contentsInfo);
			}

			ResearchAnswerDAO answerDao = new ResearchAnswerDAO();
			double answerUserCnt = Double.parseDouble(""+answerDao.getResearchAnswerCnt(systemcode, curriinfo, resid, ""));

		    // 설문결과를 가져온다.
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select i.res_no,");
			sb.append(" sum(CASE a.example1 WHEN 'O' THEN 1 ELSE 0 END) as example1, ");
			sb.append(" sum(CASE a.example2 WHEN 'O' THEN 1 ELSE 0 END) as example2, ");
			sb.append(" sum(CASE a.example3 WHEN 'O' THEN 1 ELSE 0 END) as example3, ");
			sb.append(" sum(CASE a.example4 WHEN 'O' THEN 1 ELSE 0 END) as example4, ");
			sb.append(" sum(CASE a.example5 WHEN 'O' THEN 1 ELSE 0 END) as example5, ");
			sb.append(" sum(CASE a.example6 WHEN 'O' THEN 1 ELSE 0 END) as example6, ");
			sb.append(" sum(CASE a.example7 WHEN 'O' THEN 1 ELSE 0 END) as example7, ");
			sb.append(" sum(CASE a.example8 WHEN 'O' THEN 1 ELSE 0 END) as example8, ");
			sb.append(" sum(CASE a.example9 WHEN 'O' THEN 1 ELSE 0 END) as example9, ");
			sb.append(" sum(CASE a.example10 WHEN 'O' THEN 1 ELSE 0 END) as example10 ");
			sb.append(" from curri_res_contents i, curri_res_answer a ");
			sb.append(" where i.system_code = a.system_code ");
			sb.append(" and i.curri_code = a.curri_code ");
			sb.append(" and i.curri_year = a.curri_year ");
			sb.append(" and i.curri_term = a.curri_term ");
			sb.append(" and i.res_id = a.res_id ");
			sb.append(" and i.res_no = a.res_no ");
			sb.append(" and i.system_code = ? ");
			sb.append(" and i.curri_code = ? ");
			sb.append(" and i.curri_year = ? ");
			sb.append(" and i.curri_term = ? ");
			sb.append(" and i.res_id = ? ");
			sb.append(" group by i.res_no");
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setInteger(resid);
			sql.setSql(sb.toString());

			log.debug("[getResearchResult]" + sql.toString());

			rs2 = broker.executeQuery(sql);

			count = 0;
			String[] answerRate = null;
			int[] answerCnt = null;
			DecimalFormat decimal = new DecimalFormat("#.0");
			while(rs2.next()){
				contentsInfo = (CurriResContentsDTO)contentsList.get(count);
				answerRate = contentsInfo.getAnswer();
				answerCnt = contentsInfo.getCheckCnt();
				answerCnt[1] = rs2.getInt("example1");
			    answerRate[1] = decimal.format((answerCnt[1]/answerUserCnt)*100.0);
				answerCnt[2] = rs2.getInt("example2");
			    answerRate[2] = decimal.format((answerCnt[2]/answerUserCnt)*100.0);
				answerCnt[3] = rs2.getInt("example3");
			    answerRate[3] = decimal.format((answerCnt[3]/answerUserCnt)*100.0);
				answerCnt[4] = rs2.getInt("example4");
			    answerRate[4] = decimal.format((answerCnt[4]/answerUserCnt)*100.0);
				answerCnt[5] = rs2.getInt("example5");
			    answerRate[5] = decimal.format((answerCnt[5]/answerUserCnt)*100.0);
				answerCnt[6] = rs2.getInt("example6");
			    answerRate[6] = decimal.format((answerCnt[6]/answerUserCnt)*100.0);
				answerCnt[7] = rs2.getInt("example7");
			    answerRate[7] = decimal.format((answerCnt[7]/answerUserCnt)*100.0);
				answerCnt[8] = rs2.getInt("example8");
			    answerRate[8] = decimal.format((answerCnt[8]/answerUserCnt)*100.0);
				answerCnt[9] = rs2.getInt("example9");
			    answerRate[9] = decimal.format((answerCnt[9]/answerUserCnt)*100.0);
				answerCnt[10] = rs2.getInt("example10");
			    answerRate[10] = decimal.format((answerCnt[10]/answerUserCnt)*100.0);

			    contentsInfo.setCheckCnt(answerCnt);
				contentsInfo.setAnswer(answerRate);
				count++;
			}

		}catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			 try{
				  if(rs != null) rs.close();
				  if(rs2 != null) rs2.close();
				 }catch(SQLException e){
					throw new DAOException(e.getMessage());
				 }
	     }

	     return contentsList;
	}

	/**
	 * 질의형에 대한 답을 가져온다.
	 * @param systemcode
	 * @param curriinfo
	 * @param resid
	 * @param resno
	 * @return
	 * @throws DAOException
	 */
	public RowSet getResearchResultAnswer(String systemcode,CurriMemDTO curriinfo, int resid, int resno) throws DAOException{
		RowSet rs = null;
		try{
			// List Sql문 생성
			QueryStatement sql = new QueryStatement();
			StringBuffer sb = new StringBuffer();
			sb.append(" select user_id, (select user_name from users where system_code = a.system_code and user_id = a.user_id) as user_name,  answer ");
			sb.append(" from curri_res_answer a ");
			sb.append(" where a.system_code = ? and a.curri_code = ? and a.curri_year = ? and a.curri_term = ? and a.res_id = ? and a.res_no = ? ");
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setInteger(resid);
			sql.setInteger(resno);

			sql.setSql(sb.toString());

			log.debug("[getResearchResultAnswer]" + sql.toString());

			rs = broker.executeQuery(sql);
		 }catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }
	     return rs;
	}


	public ResResultDataDTO getResearchResultExcel(String systemcode,CurriMemDTO curriinfo,int resid) throws DAOException{
		RowSet rs = null,rs1 = null, rs2 = null;
		ResResultDataDTO data = new ResResultDataDTO();

		try{
 			CurriResContentsDTO contentsInfo = null;
			ResearchAnswerDAO answerDao = new ResearchAnswerDAO();
			QueryStatement sql = new QueryStatement();

            // 설문문항 갯수를 알아온다.
			StringBuffer sb = new StringBuffer();
			sb.append(" select distinct  u.user_id,u.user_name, case u.sex_type when '1' then '남자' else '여자' end  as sex_type, ");
			sb.append(" SUBSTRING(CAST(DATE_FORMAT(NOW(), '%Y') AS CHAR) - SUBSTRING(u.jumin_no,1,2), 3,2) age ");
			sb.append(" from users u, student s ");
			sb.append(" where u.system_code = s.system_code and u.user_id = s.user_id and s.system_code = ? and s.curri_code = ?  ");
			sb.append(" and s.curri_year = ? and s.curri_term = ? and s.enroll_status = 'S'  ");
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setSql(sb.toString());

			log.debug("[getResearchResultExcel 1]" + sql.toString());

			rs = broker.executeQuery(sql);
			ResUserResultDTO user = null;
			ArrayList userList = new ArrayList();
			while(rs.next()){
				user = new ResUserResultDTO();
				user.setUserId(StringUtil.nvl(rs.getString("user_id")));
				user.setUserName(StringUtil.nvl(rs.getString("user_name")));
				user.setSexType(StringUtil.nvl(rs.getString("sex_type")));
				user.setAge(Integer.parseInt(StringUtil.nvl(rs.getString("age"))));

				userList.add(user);
			}

			rs.close();
			sb.delete(0, sb.length());

			data.setUserList(userList);

			sql.clearParam();
			sb.append(" select count(res_no) contents_cnt from curri_res_contents ");
			sb.append(" where  system_code = ? and curri_code = ? and curri_year = ? ");
			sb.append(" and curri_term = ? and res_id = ? ");
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setInteger(resid);
			sql.setSql(sb.toString());

			log.debug("[getResearchResultExcel 2]" + sql.toString());

            int contentsCnt = 0;

            rs1 = broker.executeQuery(sql);
			if(rs1.next()){
                contentsCnt = rs1.getInt("contents_cnt");
			}

			rs1.close();

			sb.delete(0,sb.length());

			// 설문결과를 가져온다.
			sql.clearParam();
			sb.append(" select a.user_id, a.res_no,a.contents_type,");
			sb.append(" CASE a.example1 WHEN 'O' THEN '1,' ELSE '' END + ");
			sb.append(" CASE a.example2 WHEN 'O' THEN '2,' ELSE '' END + ");
			sb.append(" CASE a.example3 WHEN 'O' THEN '3,' ELSE '' END + ");
			sb.append(" CASE a.example4 WHEN 'O' THEN '4,' ELSE '' END + ");
			sb.append(" CASE a.example5 WHEN 'O' THEN '5,' ELSE '' END + ");
			sb.append(" CASE a.example6 WHEN 'O' THEN '6,' ELSE '' END + ");
			sb.append(" CASE a.example7 WHEN 'O' THEN '7,' ELSE '' END + ");
			sb.append(" CASE a.example8 WHEN 'O' THEN '8,' ELSE '' END + ");
			sb.append(" CASE a.example9 WHEN 'O' THEN '9,' ELSE '' END + ");
			sb.append(" CASE a.example10 WHEN 'O' THEN '10' ELSE '' END as example, ifnull(a.answer,'') as answer ");
			sb.append(" from curri_res_answer a ");
			sb.append(" where  a.system_code = ? and a.curri_code = ? and a.curri_year = ? ");
			sb.append(" and a.curri_term = ? and a.res_id = ? order by a.user_id ");
			sql.setString(systemcode);
			sql.setString(curriinfo.curriCode);
			sql.setInteger(curriinfo.curriYear);
			sql.setInteger(curriinfo.curriTerm);
			sql.setInteger(resid);
			sql.setSql(sb.toString());

			log.debug("[getResearchResultExcel 3]" + sql.toString());

			rs2 = broker.executeQuery(sql);

			String[] answerArray = null;
			String[] example = null;
			HashMap answerList = new HashMap();
			String userId = "", preUserId = "", contentsType="";;
			int count = 0, userCnt = 0;
			String imsi = "";
			while(rs2.next()){
				userId = StringUtil.nvl(rs2.getString("user_id"));
				if(!userId.equals(preUserId)){
					count= 0;
					if(userCnt > 0) answerList.put(preUserId, answerArray);
					answerArray = new String[contentsCnt];
				}

				if(StringUtil.nvl(rs2.getString("contents_type")).equals("J")){
                     answerArray[count] = StringUtil.nvl(rs2.getString("answer"));
				}else{
					 imsi = StringUtil.nvl(rs2.getString("example"));
					 if(imsi.length() < 1) answerArray[count] = "";
					 else answerArray[count] = imsi.substring(0,imsi.length() - 1);
				}

				preUserId = userId;
				count++;
				userCnt++;
			}

			if(userCnt > 0) answerList.put(preUserId, answerArray);

			data.setResultList(answerList);
			data.setContentsCnt(contentsCnt);
			rs2.close();
		}catch(Exception e){
			  log.error(e.getMessage());
			 throw new DAOException(e.getMessage());
		 }finally{
			 try{
				  if(rs != null) rs.close();
				  if(rs1 != null) rs.close();
				  if(rs2 != null) rs2.close();
				 }catch(SQLException e){
					throw new DAOException(e.getMessage());
				 }
	     }

	     return data;
	}
}
