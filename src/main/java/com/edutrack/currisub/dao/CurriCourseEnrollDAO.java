/**
 * 
 */
package com.edutrack.currisub.dao;

import java.util.Map;

import javax.sql.RowSet;

import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author famlilia
 *
 */
public class CurriCourseEnrollDAO extends AbstractDAO {
	
	/**
	 * 카테고리와 대코드에 딸린 소코드 수
	 * @param systemCode
	 * @param pareCode1
	 * @param pareCode2
	 * @param cateCode
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriCategoryCountList(String systemCode, String pareCode1, String pareCode2, String cateCode) throws DAOException {		
		
		String	addWhere	=	"";
		if(!pareCode1.equals(""))	addWhere += " AND cp2.pare_code1 = ? ";
		if(!pareCode2.equals(""))	addWhere += " AND cp2.pare_code2 = ? ";
		if(!cateCode.equals(""))		addWhere += " AND cc.cate_code = ? ";
		
		StringBuffer	sb	=	new StringBuffer();
		QueryStatement	sql	=	new QueryStatement();		
		sb.append(" select " +
					" cp2.pare_code2, cp2.cate_name AS pare_name, cc.cate_code, cc.cate_name" +
					", (SELECT count(cate_code) FROM curri_category " +
						"WHERE pare_code2 = cp2.pare_code2 AND use_yn = 'Y') as cate_cnt ");
		sb.append(" FROM curri_pcategory2 cp2, curri_category cc ");
		sb.append(" where " +
					" cc.system_code = cp2.system_code AND cc.pare_code2 = cp2.pare_code2 " +
					" AND cp2.use_yn = 'Y' AND cc.use_yn = 'Y' ");
		sb.append(" AND cp2.system_code = ? ");
		sb.append(addWhere);
		
		sql.setSql(sb.toString());		
		sql.setString(systemCode);
		if(!pareCode1.equals(""))	sql.setString(pareCode1);
		if(!pareCode2.equals(""))	sql.setString(pareCode2);
		if(!cateCode.equals(""))	sql.setString(cateCode);
		
		log.debug(" [getCurriCategoryCountList] "+sql.toString());
		//System.out.println(" [getCurriCategoryCountList] "+sql.toString());
		
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			//---- rs.close() 는 반드시 jsp 페이지에서 해줄것!!!!!
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}
	
	/**
	 * 수강신청 가능한 과정의 리스트
	 * @param systemCode
	 * @param pareCode1
	 * @param pareCode2
	 * @param CateCode
	 * @param Property1
	 * @param Property2
	 * @param ParamMap
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCurriCourseEnrollList(String systemCode, String pareCode1, String pareCode2, String CateCode, String Property1, String Property2, Map ParamMap) throws DAOException {
		String	pPageGubun		=	StringUtil.nvl(ParamMap.get("pPageGubun"));	//학습자 수강중인 강좌에서 호출할 경우:PR(process)
		String	userId			=	StringUtil.nvl(ParamMap.get("pUserId"));
		int		code_size		=	StringUtil.nvl(ParamMap.get("code_size"), 0);
		String	pare_Code2[]	=	new String[code_size];
		String	cate_Code[]		=	new String[code_size];
		String	addSelect		=	"";
		String	currentDate 	= 	" CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%m%s') AS CHAR) ";
		
		for(int i=0; i<code_size; i++) {
			pare_Code2[i]	=	StringUtil.nvl(ParamMap.get("pareCode2["+i+"]"));
			cate_Code[i]		=	StringUtil.nvl(ParamMap.get("cateCode["+i+"]"));
			
			addSelect		+=	", (SELECT count(B.curri_code) FROM curri_top A, curri_sub B " +
								" WHERE B.system_code = A.system_code AND B.curri_code = A.curri_code " +
								" AND A.pare_code2 = '"+pare_Code2[i]+"' AND A.cate_code = '"+cate_Code[i]+"'" +
								" AND B.hakgi_term = cs.hakgi_term " ;
			
								//-- 학습자 수강중인 강좌에서 호출할 경우 : 학습자가 수강신청을 하지 않은 강좌 리스트
								if(pPageGubun.equals("PR")) {			
									addSelect		+=" AND ( "+currentDate+" >= cs.service_start  AND "+currentDate+" <= cs.service_end )  ";
								}
								// 수강신청목록에서 호출할경우..
								else{
									addSelect		+= " AND ( ("+currentDate+" >= B.enroll_start  AND "+currentDate+" <= B.enroll_end)   ";
									addSelect		+="    OR ("+currentDate+" >= B.cancel_start  AND "+currentDate+" <= B.cancel_end)  ) ";
								}
			
			addSelect		+=	") AS "+cate_Code[i]+"_cnt ";
		}		
		
		
		QueryStatement	sql		=	new QueryStatement();
		StringBuffer	sb		=	new StringBuffer();
		
		//-- 학습자 수강중인 강좌에서 호출할 경우
		if(pPageGubun.equals("PR")) {
			sb.append(" SELECT X.* FROM ( ");
		}
		
		sb.append(" SELECT " +
					" ct.system_code, ct.curri_code, cs.curri_year, cs.curri_term" +
					", cs.hakgi_term, ct.pare_code1, ct.pare_code2, ct.cate_code " +
					", ct.curri_goal, ct.curri_info , cs.curri_name, cs.prof_id" +
					", cs.credit , cs.enroll_start, cs.enroll_end, cs.cancel_start" +
					", cs.cancel_end, ct.curri_property2 as curri_type " +
					", CASE WHEN ( CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%m%s') AS CHAR) >= cs.enroll_start AND CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%m%s') AS CHAR) <= cs.enroll_end OR CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%m%s') AS CHAR) >= cs.cancel_start AND CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%m%s') AS CHAR) <= cs.cancel_end ) THEN 'Y' ELSE 'N' END AS enroll_yn " +
					", (SELECT cate_name FROM curri_category " +
						" WHERE system_code = ct.system_code AND pare_code1 = ct.pare_code1 " +
						" AND pare_code2 = ct.pare_code2 AND cate_code = ct.cate_code) AS cate_name " +
					", (SELECT user_name FROM users WHERE system_code = ct.system_code AND user_id = cs.prof_id) AS prof_name" +
					", (SELECT COUNT(curri_code) FROM student " +
						" WHERE system_code = cs.system_code AND curri_code = cs.curri_code " +
						" AND curri_year = cs.curri_year AND curri_term = cs.curri_term AND user_id = '"+userId+"') AS stu_cnt  ");
		
		sb.append(addSelect);
		sb.append(" FROM curri_top ct, curri_sub cs ");
		sb.append(" WHERE  cs.system_code = ct.system_code AND cs.curri_code = ct.curri_code ");

		//-- 학습자 수강중인 강좌에서 호출할 경우 : 학습자가 수강신청을 하지 않은 강좌 리스트
		if(pPageGubun.equals("PR")) {			
			sb.append(" AND ( "+currentDate+" >= cs.service_start  AND "+currentDate+" <= cs.service_end )  ");
		}
		// 수강신청목록에서 호출할경우..
		else{
			sb.append(" AND (("+currentDate+" >= cs.enroll_start  AND "+currentDate+" <= cs.enroll_end )  ");
			sb.append("  OR  ("+currentDate+" >= cs.cancel_start  AND "+currentDate+" <= cs.cancel_end )) ");
		}
		
		sb.append(" AND ct.system_code = ? ");
		sql.setString(systemCode);
		if(!pareCode2.equals("")) {
			sb.append(" AND ct.pare_code2 = ? ");
			sql.setString(pareCode2);
		}
		if(!Property1.equals("")) {
			sb.append(" AND ct.curri_property1 = ? ");
			sql.setString(Property1);
		}
		if(!Property2.equals("")) {
			sb.append(" AND ct.curri_property2 = ? ");
			sql.setString(Property2);
		}
		sb.append(" ORDER BY ct.cate_code, cs.curri_name ");
		
		//-- 학습자 수강중인 강좌에서 호출할 경우 : 학습자가 수강신청을 하지 않은 강좌 리스트
		if(pPageGubun.equals("PR")) {
			sb.append("	) X	WHERE X.stu_cnt = 0 ");
		}
		
		sql.setSql(sb.toString());
		
		log.debug("[getCurriCourseEnrollList] : "+sql.toString());
		
		RowSet rs = null;
		try{
			rs = broker.executeQuery(sql);
			//---- rs.close() 는 반드시 jsp 페이지에서 해줄것!!!!!
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;		
	}

}
