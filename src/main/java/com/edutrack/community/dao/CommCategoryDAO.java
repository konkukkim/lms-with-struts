/*
 * Created on 2004. 6. 28.
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.edutrack.community.dao;


import java.sql.SQLException;

import javax.sql.RowSet;

import com.edutrack.common.CommonUtil;
import com.edutrack.community.dto.CommCategoryDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;


/**
 * @author bschoi To change the template for this generated type comment go to Window>Preferences>Java>Code
 *         Generation>Code and Comments
 */
public class CommCategoryDAO extends AbstractDAO {

	/**
	 * 커뮤니티분류 추가
	 * 
	 * @param commCategory
	 * @return
	 * @throws DAOException
	 */
	public int addCommCategory(CommCategoryDTO commCategory) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into comm_category(system_code, cate_code, cate_name, cate_img1, cate_img2, reg_id, reg_date) ");
		sb.append(" values(?, ?, ?, ?, ?, ?, ?)");
		sql.setSql(sb.toString());

		sql.setString(commCategory.getSystemCode());
		sql.setString(commCategory.getCateCode());
		sql.setString(commCategory.getCateName());
		sql.setString(commCategory.getCateImg1());
		sql.setString(commCategory.getCateImg2());
		sql.setString(commCategory.getRegId());
		sql.setString(CommonUtil.getCurrentDate());

		log.debug("[addCommCategory]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 커뮤니티분류 삭제
	 * 
	 * @param SystemCode
	 * @param cateCode
	 * @return
	 * @throws DAOException
	 */
	public int delCommCategory(String SystemCode, String cateCode) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" delete from comm_category ");
		sb.append(" where system_code = ? and cate_code = ?");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(cateCode);

		log.debug("[delCommCategory]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 커뮤니티카테고리 이미지파일삭제
	 * 
	 * @param SystemCode
	 * @param cateCode
	 * @param FileNo
	 * @return
	 * @throws DAOException
	 */
	public int delCommCategoryFile(String SystemCode, String cateCode, String FileNo) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update comm_category set cate_img" + FileNo + " = '' ");
		sb.append(" where system_code = ? and cate_code = ? ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(cateCode);

		log.debug("[delCommCategoryFile]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * @param commCategory
	 * @return
	 * @throws DAOException
	 */
	public int editCommCategory(CommCategoryDTO commCategory) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update comm_category set cate_name = ? ,cate_img1 = ? , cate_img2 = ? , mod_id = ? ,mod_date = ? ");
		sb.append(" where system_code = ? and cate_code = ? ");
		sql.setSql(sb.toString());
		log.debug("-----getCateCode : " + commCategory.getCateCode());
		log.debug("-----getCateName : " + commCategory.getCateName());
		log.debug("-----getCateImg1 : " + commCategory.getCateImg1());
		log.debug("-----getCateImg2 : " + commCategory.getCateImg2());
		log.debug("-----getModId : " + commCategory.getModId());
		log.debug("-----CommonUtil.getCurrentDate : " + CommonUtil.getCurrentDate());
		sql.setString(commCategory.getCateName());
		sql.setString(commCategory.getCateImg1());
		sql.setString(commCategory.getCateImg2());
		sql.setString(commCategory.getModId());
		sql.setString(CommonUtil.getCurrentDate());

		sql.setString(commCategory.getSystemCode());
		sql.setString(commCategory.getCateCode());

		log.debug("[editCommCategory]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 커뮤니티카테고리 정보가져온다 작성자 : 김청현
	 * 
	 * @param SystemCode
	 * @param cateCode
	 * @return
	 * @throws DAOException
	 */
	public RowSet getCommCategoryInfo(String SystemCode, String cateCode) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("select system_code, cate_code, cate_name, cate_img1, cate_img2, reg_id, reg_date");
		sb.append(" from comm_category");
		sb.append(" where system_code=? and cate_code=? ");

		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(cateCode);

		//---- 디버그 출력
		log.debug("[getCommCategoryInfo]" + sql.toString());
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			//---- rs.close() 는 반드시 jsp 페이지에서 해줄것!!!!!
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 게시판 정보 리스트 가져오기(페이징 없이)
	 * 
	 * @param Systemcode
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getCommCategoryList(String Systemcode) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT system_code, cate_code, cate_name, cate_img1, cate_img2, ");
		sb.append("\n        (select count(comm_id) from comm_info where cate_code = cate_code) as cnt ");
		sb.append("\n   FROM comm_category ");
		sb.append("\n  WHERE system_code = ? ");
		sb.append("\n ORDER BY cate_code");
		sql.setSql(sb.toString());
		sql.setString(Systemcode);

		log.debug("[getCommCategoryList]" + sql.toString());
		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			//-- rs.close() 는 반드시 JSP 에서 해 줄
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
		return rs;
	}

	/**
	 * 게시판 정보 리스트 가져오기(페이징처리)
	 * 
	 * @param Systemcode
	 * @return RowSet
	 * @throws DAOException
	 */
	public ListDTO getCommCategoryPagingList(int curpage, String Systemcode, String SearchKey, String KeyWord) throws DAOException {
		ListDTO retVal = null;

		try {
			StringBuffer sb = new StringBuffer();
			StringBuffer sbAlias = new StringBuffer();
			sbAlias.append(" system_code, cate_code, cate_name, cate_img1, cate_img2 ");
			sb.append(" system_code, cate_code, cate_name, cate_img1, cate_img2");
			
			//List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol(" cate_code ");
			sql.setCutCol(" cate_code ");
			sql.setAlias(sbAlias.toString()+", cnt ");
			sql.setSelect(sb.toString()+", (select count(comm_id) from comm_info where cate_code = cate_code) as cnt ");
			sql.setFrom(" comm_category ");
			
			StringBuffer where = new StringBuffer();
			where.append(" system_code = ? ");
			sql.setString(Systemcode);
			if( !KeyWord.equals("")) {
				where.append(" and " + SearchKey + " like ? ");
				sql.setString(KeyWord);
			}
			sql.setWhere(where.toString());
			sql.setOrderby(" cate_code ");
			
			
			log.debug("[getCommCategoryPagingList]" + sql.toString());
			retVal = broker.executeListQuery(sql, curpage);

			return retVal;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
	}

	/**
	 * @param SystemCode
	 * @return
	 * @throws DAOException
	 */

	public int getMaxCateCode(String SystemCode) throws DAOException {
		int maxCateCode = 0;

		StringBuffer sb = new StringBuffer();
		sb.append(" select ISNULL(max(cate_code),0)+1 as max_cate_code ");
		sb.append(" from comm_category ");
		sb.append(" where system_code = ? ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);

		log.debug("[getMaxCateCode]" + sql.toString());

		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			if (rs.next()) {
				maxCateCode = rs.getInt("max_cate_code");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}

		return maxCateCode;
	}

	/**
	 * 커뮤니티 카테고리코드의 중복여부를 체크한다. 작성자 : 김청현
	 * 
	 * @param SystemCode
	 * @param cateCode
	 * @return boolean (true=중복아님, false=중복)
	 * @throws DAOException
	 */
	public boolean isCateCodeValid(String SystemCode, String cateCode) throws DAOException {
		boolean cateCodeFlag = false;
		int cateCodeCnt = 0;

		StringBuffer sb = new StringBuffer();
		sb.append(" select count(cate_code) as cate_code_cnt ");
		sb.append(" from comm_category ");
		sb.append(" where system_code = ? and cate_code = ? ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(cateCode);

		log.debug("[cate_code_cnt]" + sql.toString());

		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			if (rs.next()) {
				cateCodeCnt = rs.getInt("cate_code_cnt");
			}
			rs.close();

			if (cateCodeCnt == 0)
				cateCodeFlag = true;
			else
				cateCodeFlag = false;

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}

		return cateCodeFlag;
	}

}

