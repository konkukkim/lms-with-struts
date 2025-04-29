/**
 * 
 */
package com.edutrack.currisub.dao;

import java.sql.SQLException;
import java.util.Map;

import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.util.StringUtil;

/**
 * @author famlilia
 *
 */
public class PublishCurriSubDAO extends AbstractDAO {
	
	/**
	 * 공개과정에 개설된 과정리스트를 가져온다.
	 * @param curpage
	 * @param DispLine
	 * @param DispPage
	 * @param systemCode
	 * @param paramMap
	 * @return
	 * @throws DAOException
	 */
	public ListDTO getPublishCurriSubPageList(int curpage,int DispLine, int DispPage, String systemCode, Map paramMap) throws DAOException{
		ListDTO	retVal			=	null;
		
		String	searchKey		=	StringUtil.nvl(paramMap.get("searchKey"));
		String	keyWord			=	StringUtil.nvl(paramMap.get("keyWord"));
		String	order			=	StringUtil.nvl(paramMap.get("order"));
		String	pPareCode1		=	StringUtil.nvl(paramMap.get("pPareCode1"));
		String	pPareCode2		=	StringUtil.nvl(paramMap.get("pPareCode2"));
		String	pCateCode		=	StringUtil.nvl(paramMap.get("pCateCode"));
		String	pProperty1		=	StringUtil.nvl(paramMap.get("pProperty1"));
		String	pProperty2		=	StringUtil.nvl(paramMap.get("pProperty2"));
		String	addWhere		=	"";
		String currentDate = "CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
		
		try{
			ListStatement sql = new ListStatement();
			
			sql.setAlias("system_code, curri_code, curri_goal, curri_info, curri_year, curri_term, curri_name, prof_name");
			sql.setSelect(" ct.system_code, ct.curri_code, ct.curri_goal, ct.curri_info, cs.curri_year, cs.curri_term, cs.curri_name, (SELECT user_name FROM users WHERE system_code = ct.system_code AND user_id = cs.prof_id) AS prof_name ");
			sql.setFrom(" curri_sub cs, curri_top ct ");
			
			StringBuffer sbWhere = new StringBuffer();
			sbWhere.append(	" ct.system_code = cs.system_code AND ct.curri_code = cs.curri_code " +
							" AND ct.system_code = ? ");
			sql.setString(systemCode);
			
			if (!pPareCode1.equals("")) {
				sbWhere.append(" AND ct.pare_code1 = ? ");
				sql.setString(pPareCode1);
			}
			if (!pPareCode2.equals("")) {
				sbWhere.append(" AND ct.pare_code2 = ? ");
				sql.setString(pPareCode2);
			}
			if (!pCateCode.equals("")) {
				sbWhere.append(" AND ct.cate_code = ? ");
				sql.setString(pCateCode);
			}
			if (!pProperty1.equals("")) {
				sbWhere.append(" AND ct.curri_property1 = ? ");
				sql.setString(pProperty1);
			}
			if (!pProperty2.equals("")) {
				sbWhere.append(" AND ct.curri_property2 = ? ");
				sql.setString(pProperty2);
			}
			if(!searchKey.equals("") && !keyWord.equals("")) {
				sbWhere.append(" AND "+searchKey+" like '%?%' ");
				sql.setString(keyWord);
			}
			
			sql.setWhere(sbWhere.toString());
			sql.setOrderby(order);
			
			//---- 디버그 출력
			log.debug("[getPublishCurriPagingList] : " + sql.toString());
			System.out.println("[getPublishCurriPagingList] : " + sql.toString());

			retVal	=	broker.executeListQuery(sql, curpage, DispLine, DispPage);
			
		}catch(SQLException e){
			log.error(e);
			throw new DAOException(e.getMessage());
		}catch(Exception e){
			 log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

}
