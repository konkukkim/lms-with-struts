/*
 * Created on 2004. 6. 28.
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.edutrack.community.dao;


import java.sql.SQLException;
import java.util.ArrayList;
import java.io.CharArrayReader;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.sql.RowSet;

import oracle.jdbc.OracleResultSet;
import oracle.sql.CLOB;

import com.edutrack.common.CommonUtil;
import com.edutrack.community.dto.CommBbsContentsDTO;
import com.edutrack.community.dto.CommBbsInfoDTO;
import com.edutrack.framework.persist.AbstractDAO;
import com.edutrack.framework.persist.DAOException;
import com.edutrack.framework.persist.DBConnectionManager;
import com.edutrack.framework.persist.ListDTO;
import com.edutrack.framework.persist.ListStatement;
import com.edutrack.framework.persist.QueryStatement;
import com.edutrack.framework.util.StringUtil;


/**
 * @author bschoi
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommBbsInfoDAO extends AbstractDAO {

	/**
	 * 통합보드 관련 글 등록
	 * @param bbsContents
	 * @return
	 * @throws DAOException
	 */
	public int addcommSubBoardRegister(CommBbsContentsDTO bbsContents) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into comm_bbs_contents(system_code, comm_id, bbs_id, seq_no, bbs_no, depth_no, order_no, parent_no, editor_type,"
				+ "contents_type, subject, keyword, contents, rfile_name1, sfile_name1, file_path1, file_size1,"
				+ "rfile_name2, sfile_name2, file_path2, file_size2, rfile_name3, sfile_name3, file_path3, file_size3,"
				+ "hit_no, expire_date, target, reg_id, reg_name, reg_email, reg_passwd, reg_date) ");
		sb.append(" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
		sql.setSql(sb.toString());

		sql.setString(bbsContents.getSystemCode());
		sql.setInteger(bbsContents.getCommId());
		sql.setInteger(bbsContents.getBbsId());
		sql.setInteger(bbsContents.getSeqNo());
		sql.setInteger(bbsContents.getBbsNo());
		sql.setInteger(bbsContents.getDepthNo());
		sql.setInteger(bbsContents.getOrderNo());
		sql.setInteger(bbsContents.getParentNo());
		sql.setString(bbsContents.getEditorType());
		sql.setString(bbsContents.getContentsType());
		sql.setString(bbsContents.getSubject());
		sql.setString(bbsContents.getKeyword());
		sql.setString(bbsContents.getContents());
		sql.setString(bbsContents.getRfileName1());
		sql.setString(bbsContents.getSfileName1());
		sql.setString(bbsContents.getFilePath1());
		sql.setString(bbsContents.getFileSize1());
		sql.setString(bbsContents.getRfileName2());
		sql.setString(bbsContents.getSfileName2());
		sql.setString(bbsContents.getFilePath2());
		sql.setString(bbsContents.getFileSize2());
		sql.setString(bbsContents.getRfileName3());
		sql.setString(bbsContents.getSfileName3());
		sql.setString(bbsContents.getFilePath3());
		sql.setString(bbsContents.getFileSize3());
		sql.setInteger(bbsContents.getHitNo());
		sql.setString(bbsContents.getExpireDate());
		sql.setString(bbsContents.getTarget());
		sql.setString(bbsContents.getRegId());
		sql.setString(bbsContents.getRegName());
		sql.setString(bbsContents.getRegEmail());
		sql.setString(bbsContents.getRegPasswd());
		sql.setString(CommonUtil.getCurrentDate());

		log.debug("[addcommSubBoardRegister]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 게시판 정보를 등록한다.
	 * @param bbsContents
	 * @return
	 * @throws DAOException
	 */
	public int addcommSubNoticeRegister(CommBbsContentsDTO bbsContents) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into comm_bbs_contents(system_code, comm_id, bbs_id, seq_no, bbs_no, depth_no, order_no, parent_no, editor_type,"
				+ "contents_type, subject, keyword, contents, rfile_name1, sfile_name1, file_path1, file_size1,"
				+ "rfile_name2, sfile_name2, file_path2, file_size2, rfile_name3, sfile_name3, file_path3, file_size3,"
				+ "hit_no, expire_date, target, reg_id, reg_name, reg_email, reg_passwd, reg_date) ");
		sb.append(" values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
		sql.setSql(sb.toString());

		sql.setString(bbsContents.getSystemCode());
		sql.setInteger(bbsContents.getCommId());
		sql.setInteger(bbsContents.getBbsId());
		sql.setInteger(bbsContents.getSeqNo());
		sql.setInteger(bbsContents.getBbsNo());
		sql.setInteger(bbsContents.getDepthNo());
		sql.setInteger(bbsContents.getOrderNo());
		sql.setInteger(bbsContents.getParentNo());
		sql.setString(bbsContents.getEditorType());
		sql.setString(bbsContents.getContentsType());
		sql.setString(bbsContents.getSubject());
		sql.setString(bbsContents.getKeyword());
		sql.setString(bbsContents.getContents());
		sql.setString(bbsContents.getRfileName1());
		sql.setString(bbsContents.getSfileName1());
		sql.setString(bbsContents.getFilePath1());
		sql.setString(bbsContents.getFileSize1());
		sql.setString(bbsContents.getRfileName2());
		sql.setString(bbsContents.getSfileName2());
		sql.setString(bbsContents.getFilePath2());
		sql.setString(bbsContents.getFileSize2());
		sql.setString(bbsContents.getRfileName3());
		sql.setString(bbsContents.getSfileName3());
		sql.setString(bbsContents.getFilePath3());
		sql.setString(bbsContents.getFileSize3());
		sql.setInteger(bbsContents.getHitNo());
		sql.setString(bbsContents.getExpireDate());
		sql.setString(bbsContents.getTarget());
		sql.setString(bbsContents.getRegId());
		sql.setString(bbsContents.getRegName());
		sql.setString(bbsContents.getRegEmail());
		sql.setString(bbsContents.getRegPasswd());
		sql.setString(CommonUtil.getCurrentDate());

		log.debug("[addPrideRegister]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 게시판 정보를 등록한다.
	 * @param bbsContents
	 * @return
	 * @throws DAOException
	 */
	public int addPrideRegister(CommBbsContentsDTO bbsContents) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO comm_bbs_contents(system_code, comm_id, bbs_id, seq_no, bbs_no, ");
		sb.append("\n                               depth_no, order_no, parent_no, editor_type, ");
		sb.append("\n                               contents_type, subject, keyword, contents, ");
		sb.append("\n                               rfile_name1, sfile_name1, file_path1, file_size1, ");
		sb.append("\n                               rfile_name2, sfile_name2, file_path2, file_size2, ");
		sb.append("\n                               rfile_name3, sfile_name3, file_path3, file_size3, ");
		sb.append("\n                               hit_no, expire_date, target, reg_id, ");
		sb.append("\n                               reg_name, reg_email, reg_passwd, reg_date) ");
		sb.append("\n                        VALUES( ?, ?, ?, ?, ?, ");
		sb.append("\n                                ?, ?, ?, ?, ");
		sb.append("\n                                ?, ?, ?, ?, ");
		sb.append("\n                                ?, ?, ?, ?, ");
		sb.append("\n                                ?, ?, ?, ?, ");
		sb.append("\n                                ?, ?, ?, ?, ");
		sb.append("\n                                ?, ?, ?, ?, ");
		sb.append("\n                                ?, ?, ?, ? )");
		sql.setSql(sb.toString());

		sql.setString(bbsContents.getSystemCode());
		sql.setInteger(bbsContents.getCommId());
		sql.setInteger(bbsContents.getBbsId());
		sql.setInteger(bbsContents.getSeqNo());
		sql.setInteger(bbsContents.getBbsNo());

		sql.setInteger(bbsContents.getDepthNo());
		sql.setInteger(bbsContents.getOrderNo());
		sql.setInteger(bbsContents.getParentNo());
		sql.setString(bbsContents.getEditorType());

		sql.setString(bbsContents.getContentsType());
		sql.setString(bbsContents.getSubject());
		sql.setString(bbsContents.getKeyword());
		sql.setString(bbsContents.getContents());

		sql.setString(bbsContents.getRfileName1());
		sql.setString(bbsContents.getSfileName1());
		sql.setString(bbsContents.getFilePath1());
		sql.setString(bbsContents.getFileSize1());

		sql.setString(bbsContents.getRfileName2());
		sql.setString(bbsContents.getSfileName2());
		sql.setString(bbsContents.getFilePath2());
		sql.setString(bbsContents.getFileSize2());

		sql.setString(bbsContents.getRfileName3());
		sql.setString(bbsContents.getSfileName3());
		sql.setString(bbsContents.getFilePath3());
		sql.setString(bbsContents.getFileSize3());

		sql.setInteger(bbsContents.getHitNo());
		sql.setString(bbsContents.getExpireDate());
		sql.setString(bbsContents.getTarget());
		sql.setString(bbsContents.getRegId());

		sql.setString(bbsContents.getRegName());
		sql.setString(bbsContents.getRegEmail());
		sql.setString(bbsContents.getRegPasswd());
		sql.setString(CommonUtil.getCurrentDate());

		log.debug("[addPrideRegister]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 게시판을 수정한다.
	 * @param bbsinfo
	 * @return
	 * @throws DAOException
	 */
	public int commBbsEdit(CommBbsInfoDTO bbsinfo) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update comm_bbs_info ");
		sb.append(" set  bbs_name =? ,use_yn = ?,editor_yn =?,voice_yn =?,file_use_yn =?,file_limit=?,disp_line=?,disp_page=?,sub_manager=?,comment_use_yn=?,mod_id=?, ");
		sb.append(" mod_date = ? ");
		sb.append(" where system_code = ? and comm_id = ? and bbs_id = ? ");
		sql.setSql(sb.toString());

		sql.setString(bbsinfo.getBbsName());
		sql.setString(bbsinfo.getUseYn());
		sql.setString(bbsinfo.getEditorYn());
		sql.setString(bbsinfo.getVoiceYn());
		sql.setString(bbsinfo.getFileUseYn());
		sql.setInteger(bbsinfo.getFileLimit());
		sql.setInteger(bbsinfo.getDispLine());
		sql.setInteger(bbsinfo.getDispPage());
		sql.setString(bbsinfo.getSubManager());
		sql.setString(bbsinfo.getCommentUseYn());
		sql.setString(bbsinfo.getModId());
		sql.setString(CommonUtil.getCurrentDate());
		sql.setString(bbsinfo.getSystemCode());
		sql.setInteger(bbsinfo.getCommId());
		sql.setInteger(bbsinfo.getBbsId());

		log.debug("[commBbsEdit]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 커뮤니티 게시판을 추가한다.
	 * @param bbsinfo
	 * @return
	 * @throws DAOException
	 */
	public int commBbsRegister(CommBbsInfoDTO bbsinfo) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("\n INSERT INTO comm_bbs_info(system_code, comm_id, bbs_id, bbs_type, " +
				  "\n							bbs_name, use_yn, editor_yn, voice_yn, " +
				  "\n                           file_use_yn, file_limit, disp_line, disp_page, " +
				  "\n                           sub_manager, comment_use_yn, reg_id, reg_date) ");
		sb.append("\n SELECT ?, ?, ifnull(max(bbs_id),0)+1 as bbs_id, ?, " +
				  "\n ?, 'Y', ?, ?, " +
				  "\n ?, ?, ?, ?, "
				+ "\n ?, ?, ?, ? ");
		sb.append("\n FROM comm_bbs_info ");
		sb.append("\n WHERE system_code = ? and comm_id = ? ");
		sql.setSql(sb.toString());

		sql.setString(bbsinfo.getSystemCode());
		sql.setInteger(bbsinfo.getCommId());
		sql.setString(bbsinfo.getBbsType());

		sql.setString(bbsinfo.getBbsName());
		sql.setString(bbsinfo.getEditorYn());
		sql.setString(bbsinfo.getVoiceYn());

		sql.setString(bbsinfo.getFileUseYn());
		sql.setInteger(bbsinfo.getFileLimit());
		sql.setInteger(bbsinfo.getDispLine());
		sql.setInteger(bbsinfo.getDispPage());

		sql.setString(bbsinfo.getSubManager());
		sql.setString(bbsinfo.getCommentUseYn());
		sql.setString(bbsinfo.getRegId());
		sql.setString(CommonUtil.getCurrentDate());

		sql.setString(bbsinfo.getSystemCode());
		sql.setInteger(bbsinfo.getCommId());

		log.debug("[commBbsRegister]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 게시물을 삭제한다.
	 * @param SystemCode
	 * @param BbsId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int delBbsContents(String SystemCode, int commId, int BbsId, int SeqNo) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" delete from comm_bbs_contents ");
		sb.append(" where system_code = ? and comm_id = ? and bbs_id = ?");
		if (SeqNo > 0) sb.append(" and seq_no = ?");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setInteger(commId);
		sql.setInteger(BbsId);
		sql.setInteger(SeqNo);

		log.debug("[delBbsContents]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 게시물을 삭제한다.
	 * @param SystemCode
	 * @param BbsId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int delBbsContents(String SystemCode, int commId) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" delete from comm_bbs_contents ");
		sb.append(" where system_code = ? and comm_id = ? ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setInteger(commId);

		log.debug("[delBbsContents]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 첨부 파일 삭제시 정보에서 파일 이름 Null 로 update
	 * @param SystemCode
	 * @param BbsId
	 * @param SeqNo
	 * @param FileNo
	 * @return
	 * @throws DAOException
	 */
	public int delBbsContentsFile(String SystemCode, int commId, int BbsId, int SeqNo, String FileNo) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" update comm_bbs_contents set rfile_name" + FileNo + " = '',sfile_name" + FileNo + " = '',file_path" + FileNo + " = '',file_size" + FileNo + " = '' ");
		sb.append(" where system_code = ? and comm_id= ? and bbs_id = ? and seq_no = ? ");
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setInteger(commId);
		sql.setInteger(BbsId);
		sql.setInteger(SeqNo);

		log.debug("[delBbsContentsFile]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 게시판 내용을 수정한다.
	 * @param bbsContents
	 * @return
	 * @throws DAOException
	 */
	public int editPrideRegister(CommBbsContentsDTO bbsContents) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE comm_bbs_contents ");
		sb.append("\n    SET editor_type = ?, contents_type = ?, subject = ?, keyword = ?, ");
		sb.append("\n        contents = ?, rfile_name1 = ?, sfile_name1 = ?,  file_path1 = ?, ");
		sb.append("\n        file_size1 = ?, rfile_name2 = ?, sfile_name2 = ?, file_path2 = ?, ");
		sb.append("\n        file_size2 = ?, rfile_name3 = ?, sfile_name3 = ?, file_path3 = ?, ");
		sb.append("\n        file_size3 = ?, expire_date = ?, target = ?, reg_name = ?, ");
		sb.append("\n        reg_email = ?, mod_id = ?, mod_date = ?  ");
		sb.append("\n  WHERE system_code = ? ");
		sb.append("\n    AND comm_id = ? ");
		sb.append("\n    AND bbs_id = ? ");
		sb.append("\n    AND seq_no = ? ");
		sql.setSql(sb.toString());

		sql.setString(bbsContents.getEditorType());
		sql.setString(bbsContents.getContentsType());
		sql.setString(bbsContents.getSubject());
		sql.setString(bbsContents.getKeyword());
		sql.setString(bbsContents.getContents());
		sql.setString(bbsContents.getRfileName1());
		sql.setString(bbsContents.getSfileName1());
		sql.setString(bbsContents.getFilePath1());
		sql.setString(bbsContents.getFileSize1());
		sql.setString(bbsContents.getRfileName2());
		sql.setString(bbsContents.getSfileName2());
		sql.setString(bbsContents.getFilePath2());
		sql.setString(bbsContents.getFileSize2());
		sql.setString(bbsContents.getRfileName3());
		sql.setString(bbsContents.getSfileName3());
		sql.setString(bbsContents.getFilePath3());
		sql.setString(bbsContents.getFileSize3());
		sql.setString(bbsContents.getExpireDate());
		sql.setString(bbsContents.getTarget());
		sql.setString(bbsContents.getRegName());
		sql.setString(bbsContents.getRegEmail());
		sql.setString(bbsContents.getModId());
		sql.setString(CommonUtil.getCurrentDate());

		sql.setString(bbsContents.getSystemCode());
		sql.setInteger(bbsContents.getCommId());
		sql.setInteger(bbsContents.getBbsId());
		sql.setInteger(bbsContents.getSeqNo());

		log.debug("[editPrideRegister]" + sql.toString());
		try{
			retVal = broker.executeUpdate(sql);
		}catch(Exception e){
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
/*
		Connection conn = null;
		DBConnectionManager pool = null;
		ResultSet rs = null;
		QueryStatement sql2 = new QueryStatement();
		StringBuffer sb2 = new StringBuffer();
		try {
			pool = DBConnectionManager.getInstance();
			conn = pool.getConnection();
			conn.setAutoCommit(false);

			retVal = broker.executeUpdate(sql, conn);

			sb2.append(" select contents from comm_bbs_contents ");
			sb2.append(" where system_code = ? and comm_id = ? and bbs_id = ? and seq_no = ? FOR UPDATE");
			sql2.setSql(sb2.toString());
			sql2.setString(bbsContents.getSystemCode());
			sql2.setInteger(bbsContents.getCommId());
			sql2.setInteger(bbsContents.getBbsId());
			sql2.setInteger(bbsContents.getSeqNo());
			log.debug("[UseAdd_getCommBbsContents]" + sql2.toString());

			rs = broker.executeQuery(sql2, conn);
			if (rs.next()) {
				//	     		log.debug("______ rs "+rs.getClob(1));
				CLOB clob = ((OracleResultSet) rs).getCLOB("contents");
				Writer writer = clob.getCharacterOutputStream();
				Reader src = new CharArrayReader(bbsContents.getContents().toCharArray());
				char[] buffer = new char[1024];
				int read = 0;
				while ((read = src.read(buffer, 0, 1024)) != -1) {
					writer.write(buffer, 0, read);
				}
				//clob.close();
				src.close();
				writer.close();
			}
			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (conn != null) conn.rollback();
			} catch (SQLException ignore) {
				log.error(ignore.getMessage(), ignore);
			}
			throw new DAOException(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.setAutoCommit(true);
					pool.freeConnection(conn); // 컨넥션 해제
				}
			} catch (Exception ignore) {
				log.error(ignore.getMessage(), ignore);
			}
		}
*/

		return retVal;
	}

	/**
	 * 등록된 게시글 총 수를 가져온다.
	 * @param SystemCode
	 * @param BbsId
	 * @param Where
	 * @return
	 * @throws DAOException
	 */
	public int getCommBbsContentsCount(String SystemCode, int BbsId, String Where) throws DAOException {
		int cnt = 0;

		StringBuffer sb = new StringBuffer();
		sb.append(" select ifnull(count(seq_no),0) as cnt ");
		sb.append(" from comm_bbs_contents ");
		sb.append(" where system_code = ? and bbs_id= ?");
		if (!Where.equals("")) {
			sb.append(" and " + Where);
		}

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setInteger(BbsId);

		log.debug("[getCommBbsContentsCount]" + sql.toString());

		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			if (rs.next()) {
				cnt = rs.getInt("cnt");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());

		}

		return cnt;
	}

	/**
	 * 게시글의 총수 가져옴
	 * @param SystemCode
	 * @param commId
	 * @param BbsId
	 * @param Where
	 * @return
	 * @throws DAOException
	 */
	public int getCommBbsContentsCount(String SystemCode, String commId, int BbsId, String Where) throws DAOException {
		int cnt = 0;

		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT ifnull(count(seq_no),0) as cnt ");
		sb.append("\n   FROM comm_bbs_contents ");
		sb.append("\n  WHERE system_code = ? ");
		sb.append("\n    AND comm_id = ? ");
		sb.append("\n    AND bbs_id= ?");

		if (!Where.equals("")) {
			sb.append(" and " + Where);
		}

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(commId);
		sql.setInteger(BbsId);

		log.debug("[getCommBbsContentsCount]" + sql.toString());

		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			if (rs.next()) {
				cnt = rs.getInt("cnt");
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return cnt;
	}

	/**
	 * 게시글 리스트 가져오기(페이징 없이)
	 * @param Systemcode
	 * @param BbsId
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getCommBbsContentsList(String Systemcode, int BbsId, String addWhere, String order) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select system_code, bbs_id, seq_no, bbs_no, depth_no, order_no, parent_no, editor_type," + "contents_type, subject, rfile_name1, sfile_name1, file_path1, file_size1,"
				+ "rfile_name2, sfile_name2, file_path2, file_size2, rfile_name3, sfile_name3, file_path3, file_size3,"
				+ "hit_no, expire_date, target, reg_id, reg_name, reg_email, reg_passwd, reg_date ");
		sb.append(" from comm_bbs_contents ");
		sb.append(" where system_code = ? and bbs_id = ?");
		if (!addWhere.equals("")) sb.append(" and " + addWhere);
		if (order.equals(""))
			sb.append(" order by bbs_no desc, order_no asc ");
		else
			sb.append(" " + order);

		sql.setSql(sb.toString());
		sql.setString(Systemcode);
		sql.setInteger(BbsId);

		log.debug("[getCommBbsContentsList]" + sql.toString());
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

	public CommBbsInfoDTO getCommBbsInfo(String systemcode, String commid, String bbsid) throws DAOException {
		CommBbsInfoDTO bbsInfo = null;

		StringBuffer sb = new StringBuffer();
		sb.append(" select " +
							"bbs_id, bbs_type, bbs_name, use_yn" +
							", editor_yn, voice_yn, file_use_yn, file_limit" +
							", disp_line, disp_page, sub_manager, comment_use_yn" +
							", reg_id, reg_date ");
		sb.append(" from comm_bbs_info ");
		sb.append(" where system_code = ? and comm_id = ? and bbs_id = ? ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setInteger(commid);
		sql.setInteger(bbsid);

		log.debug("[getCommBbsInfo]" + sql.toString());

		RowSet rs = null;
		try {
			bbsInfo = new CommBbsInfoDTO();
			rs = broker.executeQuery(sql);
			if (rs.next()) {
				bbsInfo.setBbsId(rs.getInt("bbs_id"));
				bbsInfo.setBbsType(rs.getString("bbs_type"));
				bbsInfo.setBbsName(rs.getString("bbs_name"));
				bbsInfo.setUseYn(rs.getString("use_yn"));
				bbsInfo.setEditorYn(rs.getString("editor_yn"));
				bbsInfo.setFileUseYn(rs.getString("file_use_yn"));
				bbsInfo.setFileLimit(StringUtil.nvl(rs.getString("file_limit"), 2));
				bbsInfo.setDispLine(rs.getInt("disp_line"));
				bbsInfo.setDispPage(rs.getInt("disp_page"));
				bbsInfo.setSubManager(rs.getString("sub_manager"));
				bbsInfo.setCommentUseYn(rs.getString("comment_use_yn"));
				bbsInfo.setRegId(rs.getString("reg_id"));
				bbsInfo.setRegDate(rs.getString("reg_date"));
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

		return bbsInfo;
	}

	/**
	 * 게시판 목록을 보여준다.
	 * @param systemcode
	 * @param commid
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getCommBbsInfoList(String systemcode, String commid) throws DAOException {
		ArrayList bbsList = new ArrayList();
		String[] info = null;
		StringBuffer sb = new StringBuffer();
		sb.append(" select bbs_id, bbs_name ");
		sb.append(" from comm_bbs_info ");
		sb.append(" where system_code = ? and comm_id = ? and use_yn = 'Y'");
//		sb.append(" where system_code = ? and comm_id = ? and use_yn = 'Y' and bbs_id > 4 ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(systemcode);
		sql.setInteger(commid);

		log.debug("[getCommBbsInfoList]" + sql.toString());

		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			while (rs.next()) {
				info = new String[] { "", "" };
				info[0] = rs.getString("bbs_name");
				info[1] = "" + rs.getInt("bbs_id");

				bbsList.add(info);
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

		return bbsList;
	}

	/**
	 * 게시판 정보 리스트를 페이징 처리하여 가져온다.
	 * @param curpage
	 * @param Systemcode
	 * @return ListDTO
	 * @throws DAOException
	 */
//	public ListDTO getCommPrideBbsPagingList(int curpage, int DispLine, int DispPage, String Systemcode, int BbsId, String userType, String expireDate, String searchKey, String keyWord, String order, String commId) throws DAOException {
//		ListDTO retVal = null;
//		try {
//			StringBuffer sb = new StringBuffer();
//			StringBuffer sbAlias = new StringBuffer();
//
//			sbAlias.append(" system_code, bbs_id, seq_no, bbs_no, depth_no, ");
//			sbAlias.append(" order_no, parent_no, editor_type, contents_type, subject, ");
//			sbAlias.append(" rfile_name1, sfile_name1, file_path1, file_size1, ");
//			sbAlias.append(" rfile_name2, sfile_name2, file_path2, file_size2, ");
//			sbAlias.append(" rfile_name3, sfile_name3, file_path3, file_size3, ");
//			sbAlias.append(" hit_no, expire_date, target, reg_id, ");
//			sbAlias.append(" reg_name, reg_email, reg_passwd, reg_date ");
//
//			sb.append(" system_code, bbs_id, seq_no, bbs_no, depth_no, ");
//			sb.append(" order_no, parent_no, editor_type, contents_type, subject, ");
//			sb.append(" rfile_name1, sfile_name1, file_path1, file_size1, ");
//			sb.append(" rfile_name2, sfile_name2, file_path2, file_size2, ");
//			sb.append(" rfile_name3, sfile_name3, file_path3, file_size3, ");
//			sb.append(" hit_no, expire_date, target, reg_id, ");
//			sb.append(" reg_name, reg_email, reg_passwd, reg_date ");
//
//			// List Sql문 생성
//			ListStatement sql = new ListStatement();
//			sql.setTotalCol("seq_no");
//			sql.setCutCol("bbs_id+seq_no");
//			sql.setAlias(sbAlias.toString());
//			sql.setSelect(sb.toString());
//
//			sql.setFrom(" comm_bbs_contents ");
//			StringBuffer sbWhere = new StringBuffer();
//			sbWhere.append(" system_code = ? and comm_id = ? and bbs_id = ? ");
//			sql.setString(Systemcode);
//			sql.setString(commId);
//			sql.setInteger(BbsId);
//
//			if (!userType.equals("") && !userType.equals("M")) {
//				sbWhere.append(" and (target like ? or target like '%A%' ) ");
//				sql.setString(userType);
//			}
//			if (!expireDate.equals("") && !userType.equals("M")) {
//				sbWhere.append(" and (expire_date='' or expire_date <= ? ) ");
//				sql.setString(expireDate);
//			}
//			if (!keyWord.equals("")) {
//				sbWhere.append(" and " + searchKey + " like ? ");
//				sql.setString("%" + keyWord + "%");
//			}
//			sql.setWhere(sbWhere.toString());
//
//			if (order.equals(""))
//				sql.setOrderby(" bbs_no desc, order_no asc");
//			else
//				sql.setOrderby(" " + order);
//
//			// 파라미터 셋팅
//			log.debug("[getCommBbsPagingList]" + sql.toString());
//
//			retVal = broker.executeListQuery(sql, curpage, DispLine, DispPage);
//
//			return retVal;
//
//		} catch (SQLException e) {
//			log.error(e);
//			throw new DAOException(e.getMessage());
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			throw new DAOException(e.getMessage());
//		}
//	}

	/**
	 * 게시판 정보 리스트를 페이징 처리하여 가져온다.
	 * @param curpage
	 * @param Systemcode
	 * @return ListDTO
	 * @throws DAOException
	 */
	public ListDTO getCommPrideBbsPagingList(int curpage, int bbsId, String Systemcode, String searchKey, String keyWord, String order) throws DAOException {
		ListDTO retVal = null;
		try {
			StringBuffer sb = new StringBuffer();
			StringBuffer sbAlias = new StringBuffer();

			sbAlias.append(" system_code, comm_id, ");
			sbAlias.append(" comm_name, ");
			sbAlias.append(" bbs_id, seq_no, bbs_no, depth_no, ");
			sbAlias.append(" order_no, parent_no, editor_type, contents_type, subject, ");
			sbAlias.append(" rfile_name1, sfile_name1, file_path1, file_size1, ");
			sbAlias.append(" rfile_name2, sfile_name2, file_path2, file_size2, ");
			sbAlias.append(" rfile_name3, sfile_name3, file_path3, file_size3, ");
			sbAlias.append(" hit_no, expire_date, target, reg_id, ");
			sbAlias.append(" reg_name, reg_email, reg_passwd, reg_date ");

			sb.append(" a.system_code, a.comm_id, ");
			sb.append(" (select comm_name from comm_info where system_code=a.system_code and comm_id=a.comm_id ) comm_name, ");
			sb.append(" a.bbs_id, a.seq_no, a.bbs_no, a.depth_no, ");
			sb.append(" a.order_no, a.parent_no, a.editor_type, a.contents_type, a.subject, ");
			sb.append(" a.rfile_name1, a.sfile_name1, a.file_path1, a.file_size1, ");
			sb.append(" a.rfile_name2, a.sfile_name2, a.file_path2, a.file_size2, ");
			sb.append(" a.rfile_name3, a.sfile_name3, a.file_path3, a.file_size3, ");
			sb.append(" a.hit_no, a.expire_date, a.target, a.reg_id, ");
			sb.append(" a.reg_name, a.reg_email, a.reg_passwd, a.reg_date ");

			// List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("a.seq_no");
			sql.setCutCol("a.bbs_id+a.seq_no");
			sql.setAlias(sbAlias.toString());
			sql.setSelect(sb.toString());

			sql.setFrom(" comm_bbs_contents a");
			StringBuffer sbWhere = new StringBuffer();
			sbWhere.append(" a.system_code = ? and a.bbs_id = ? ");
			sql.setString(Systemcode);
			sql.setInteger(bbsId);



			if (!keyWord.equals("")) {
				sbWhere.append(" and a." + searchKey + " like ? ");
				sql.setString("%" + keyWord + "%");
			}
			sql.setWhere(sbWhere.toString());

			if (order.equals(""))
				sql.setOrderby("a.bbs_no desc, a.order_no asc");
			else
				sql.setOrderby(" " + order);

			// 파라미터 셋팅
			log.debug("[getCommBbsPagingList]" + sql.toString());

			retVal = broker.executeListQuery(sql, curpage);

			return retVal;

		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
	}

	/**
	 * 게시물 정보 가져오기
	 * @param Systemcode
	 * @param BbsId
	 * @param SeqNo
	 * @return RowSet
	 * @throws DAOException
	 */
	public RowSet getcommPrideContents(String Systemcode, int BbsId, int SeqNo) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" select system_code, bbs_id, seq_no, bbs_no, depth_no, order_no, parent_no, editor_type,"
				+ " contents_type, subject, keyword, contents, rfile_name1, sfile_name1, file_path1, file_size1,"
				+ " rfile_name2, sfile_name2, file_path2, file_size2, rfile_name3, sfile_name3, file_path3, file_size3,"
				+ " hit_no, expire_date, target, reg_id, reg_name, reg_email, reg_passwd, reg_date ");
		sb.append(" from comm_bbs_contents ");
		sb.append(" where system_code = ? and bbs_id = ? and seq_no = ?");
		sql.setSql(sb.toString());
		sql.setString(Systemcode);
		sql.setInteger(BbsId);
		sql.setInteger(SeqNo);
		log.debug("[getcommPrideContents]" + sql.toString());
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
	 * getcommPrideContents(String commId) Overriding
	 * @param Systemcode
	 * @param BbsId
	 * @param SeqNo
	 * @param commId
	 * @return
	 * @throws DAOException
	 */
/*
	public RowSet getcommPrideContents(String Systemcode, int BbsId, int SeqNo, String commId) throws DAOException {
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT system_code, bbs_id, seq_no, bbs_no, ");
		sb.append(" depth_no, order_no, parent_no, editor_type, ");
		sb.append(" contents_type, subject, keyword, contents, ");
		//sb.append(" contents_type, subject, keyword, ");
		sb.append(" rfile_name1, sfile_name1, file_path1, file_size1, ");
		sb.append(" rfile_name2, sfile_name2, file_path2, file_size2, ");
		sb.append(" rfile_name3, sfile_name3, file_path3, file_size3, ");
		sb.append(" hit_no, expire_date, target, reg_id, ");
		sb.append(" reg_name, reg_email, reg_passwd, reg_date ");
		sb.append(" FROM comm_bbs_contents ");
		sb.append(" WHERE system_code = ? ");
		sb.append(" AND comm_id = ? ");
		sb.append(" AND bbs_id = ? ");
		sb.append(" AND seq_no = ? ");

		sql.setSql(sb.toString());
		sql.setString(Systemcode);
		sql.setString(commId);
		sql.setString(String.valueOf(BbsId));
		sql.setString(String.valueOf(SeqNo));

		log.debug("[getcommPrideContents]" + sql.toString());
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
*/


	/**
	 *
	 * @param Systemcode
	 * @param BbsId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public CommBbsContentsDTO getCommPrideContents(String Systemcode, int BbsId, int SeqNo) throws DAOException {
		CommBbsContentsDTO contentsDto = null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT system_code, bbs_id, seq_no, bbs_no, depth_no, order_no, parent_no, editor_type,");
		sb.append("\n        contents_type, subject, keyword, contents, rfile_name1, sfile_name1, file_path1, file_size1,");
		sb.append("\n        rfile_name2, sfile_name2, file_path2, file_size2, rfile_name3, sfile_name3, file_path3, file_size3,");
		sb.append("\n        hit_no, expire_date, target, reg_id, reg_name, reg_email, reg_passwd, reg_date ");
		sb.append("\n   FROM comm_bbs_contents ");
		sb.append("\n  WHERE system_code = ? and bbs_id = ? and seq_no = ?");
		sql.setSql(sb.toString());
		sql.setString(Systemcode);
		sql.setInteger(BbsId);
		sql.setInteger(SeqNo);
		log.debug("[getcommPrideContents]" + sql.toString());

		ResultSet rs = null;
//		Connection conn = null;
//		DBConnectionManager pool = null;
		StringBuffer output = new StringBuffer();

		try {
//			pool = DBConnectionManager.getInstance();
//			conn = pool.getConnection();
			rs = broker.executeQuery(sql);
			if (rs.next()) {
				contentsDto = new CommBbsContentsDTO();
				contentsDto.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				contentsDto.setBbsId(rs.getInt("bbs_id"));
				contentsDto.setSeqNo(rs.getInt("seq_no"));
				contentsDto.setBbsNo(rs.getInt("bbs_no"));
				contentsDto.setDepthNo(rs.getInt("depth_no"));
				contentsDto.setOrderNo(rs.getInt("order_no"));
				contentsDto.setParentNo(rs.getInt("parent_no"));
				contentsDto.setEditorType(StringUtil.nvl(rs.getString("editor_type")));
				contentsDto.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
				contentsDto.setSubject(StringUtil.nvl(rs.getString("subject")));
				contentsDto.setKeyword(StringUtil.nvl(rs.getString("keyword")));
				contentsDto.setContents(StringUtil.nvl(rs.getString("contents")));
				contentsDto.setRfileName1(StringUtil.nvl(rs.getString("rfile_name1")));
				contentsDto.setSfileName1(StringUtil.nvl(rs.getString("sfile_name1")));
				contentsDto.setFilePath1(StringUtil.nvl(rs.getString("file_path1")));
				contentsDto.setFileSize1(StringUtil.nvl(rs.getString("file_size1")));
				contentsDto.setRfileName2(StringUtil.nvl(rs.getString("rfile_name2")));
				contentsDto.setSfileName2(StringUtil.nvl(rs.getString("sfile_name2")));
				contentsDto.setFilePath2(StringUtil.nvl(rs.getString("file_path2")));
				contentsDto.setFileSize2(StringUtil.nvl(rs.getString("file_size2")));
				contentsDto.setRfileName3(StringUtil.nvl(rs.getString("rfile_name3")));
				contentsDto.setSfileName3(StringUtil.nvl(rs.getString("sfile_name3")));
				contentsDto.setFilePath3(StringUtil.nvl(rs.getString("file_path3")));
				contentsDto.setFileSize3(StringUtil.nvl(rs.getString("file_size3")));
				contentsDto.setHitNo(rs.getInt("hit_no"));
				contentsDto.setExpireDate(StringUtil.nvl(rs.getString("expire_date")));
				contentsDto.setTarget(StringUtil.nvl(rs.getString("target")));
				contentsDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				contentsDto.setRegName(StringUtil.nvl(rs.getString("reg_name")));
				contentsDto.setRegEmail(StringUtil.nvl(rs.getString("reg_email")));
				contentsDto.setRegPasswd(StringUtil.nvl(rs.getString("reg_passwd")));
				contentsDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));

//				Reader input = rs.getCharacterStream("contents");
//				char[] buffer = new char[1024];
//				int byteRead;
//
//				while ((byteRead = input.read(buffer, 0, 1024)) != -1) {
//					output.append(buffer, 0, byteRead);
//				}
//				input.close();
//
//				contentsDto.setContents(StringUtil.nvl(output.toString()));
//				output.delete(0, output.length());
			}
			rs.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
//				if (conn != null) {
//					conn.setAutoCommit(true);
//					pool.freeConnection(conn); // 컨넥션 해제
//				}
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}

		return contentsDto;
	}

	/**
	 * 게시판 게시물 정보 가져오기
	 * @param Systemcode
	 * @param BbsId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public CommBbsContentsDTO getCommPrideContents(String Systemcode, String commId, int BbsId, int SeqNo) throws DAOException {
		CommBbsContentsDTO contentsDto = null;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT c.system_code, c.bbs_id, c.seq_no, c.bbs_no, ");
		sb.append("\n        c.depth_no, c.order_no, c.parent_no, c.editor_type,");
		sb.append("\n        c.contents_type, c.subject, c.keyword, c.contents, ");
		sb.append("\n        c.rfile_name1, c.sfile_name1, c.file_path1, c.file_size1,");
		sb.append("\n        c.rfile_name2, c.sfile_name2, c.file_path2, c.file_size2, ");
		sb.append("\n        c.rfile_name3, c.sfile_name3, c.file_path3, c.file_size3,");
		sb.append("\n        c.hit_no, c.expire_date, c.target, c.reg_id, ");
		sb.append("\n        c.reg_name, c.reg_email, c.reg_passwd, c.reg_date, ");
		sb.append("\n        ifnull(( select m.user_nick from comm_members m where m.system_code = c.system_code and m.comm_id = c.comm_id and m.user_id = c.reg_id),'unknown') as nick_name ");
		sb.append("\n   FROM comm_bbs_contents c ");
		sb.append("\n  WHERE c.system_code = ? ");
		sb.append("\n    AND c.comm_id = ? ");
		sb.append("\n    AND c.bbs_id = ? ");
		sb.append("\n    AND c.seq_no = ?");
		sql.setSql(sb.toString());
		sql.setString(Systemcode);
		sql.setString(commId);
		sql.setInteger(BbsId);
		sql.setInteger(SeqNo);
		log.debug("[getcommPrideContents]" + sql.toString());

		ResultSet rs = null;
//		Connection conn = null;
//		DBConnectionManager pool = null;
		StringBuffer output = new StringBuffer();

		try {
//			pool = DBConnectionManager.getInstance();
//			conn = pool.getConnection();
			rs = broker.executeQuery(sql);
			if (rs.next()) {
				contentsDto = new CommBbsContentsDTO();
				contentsDto.setSystemCode(StringUtil.nvl(rs.getString("system_code")));
				contentsDto.setBbsId(rs.getInt("bbs_id"));
				contentsDto.setSeqNo(rs.getInt("seq_no"));
				contentsDto.setBbsNo(rs.getInt("bbs_no"));
				contentsDto.setDepthNo(rs.getInt("depth_no"));
				contentsDto.setOrderNo(rs.getInt("order_no"));
				contentsDto.setParentNo(rs.getInt("parent_no"));
				contentsDto.setEditorType(StringUtil.nvl(rs.getString("editor_type")));
				contentsDto.setContentsType(StringUtil.nvl(rs.getString("contents_type")));
				contentsDto.setSubject(StringUtil.nvl(rs.getString("subject")));
				contentsDto.setKeyword(StringUtil.nvl(rs.getString("keyword")));
				contentsDto.setContents(StringUtil.nvl(rs.getString("contents")));
				contentsDto.setRfileName1(StringUtil.nvl(rs.getString("rfile_name1")));
				contentsDto.setSfileName1(StringUtil.nvl(rs.getString("sfile_name1")));
				contentsDto.setFilePath1(StringUtil.nvl(rs.getString("file_path1")));
				contentsDto.setFileSize1(StringUtil.nvl(rs.getString("file_size1")));
				contentsDto.setRfileName2(StringUtil.nvl(rs.getString("rfile_name2")));
				contentsDto.setSfileName2(StringUtil.nvl(rs.getString("sfile_name2")));
				contentsDto.setFilePath2(StringUtil.nvl(rs.getString("file_path2")));
				contentsDto.setFileSize2(StringUtil.nvl(rs.getString("file_size2")));
				contentsDto.setRfileName3(StringUtil.nvl(rs.getString("rfile_name3")));
				contentsDto.setSfileName3(StringUtil.nvl(rs.getString("sfile_name3")));
				contentsDto.setFilePath3(StringUtil.nvl(rs.getString("file_path3")));
				contentsDto.setFileSize3(StringUtil.nvl(rs.getString("file_size3")));
				contentsDto.setHitNo(rs.getInt("hit_no"));
				contentsDto.setExpireDate(StringUtil.nvl(rs.getString("expire_date")));
				contentsDto.setTarget(StringUtil.nvl(rs.getString("target")));
				contentsDto.setRegId(StringUtil.nvl(rs.getString("reg_id")));
				contentsDto.setRegName(StringUtil.nvl(rs.getString("reg_name")));
				contentsDto.setRegEmail(StringUtil.nvl(rs.getString("reg_email")));
				contentsDto.setRegPasswd(StringUtil.nvl(rs.getString("reg_passwd")));
				contentsDto.setRegDate(StringUtil.nvl(rs.getString("reg_date")));
				contentsDto.setNickName(StringUtil.nvl(rs.getString("nick_name")));

//				Reader input = rs.getCharacterStream("contents");
//				char[] buffer = new char[1024];
//				int byteRead;
//
//				while ((byteRead = input.read(buffer, 0, 1024)) != -1) {
//					output.append(buffer, 0, byteRead);
//				}
//				input.close();
//
//				contentsDto.setContents(StringUtil.nvl(output.toString()));
//				output.delete(0, output.length());
			}
			rs.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		} finally {
			try {
				if (rs != null) rs.close();
//				if (conn != null) {
//					conn.setAutoCommit(true);
//					pool.freeConnection(conn); // 컨넥션 해제
//				}
			} catch (SQLException e) {
				throw new DAOException(e.getMessage());
			}
		}

		return contentsDto;
	}

	/**************************************/
	/*    통합 게시판 관련 메서드                            */
	/**************************************/

	/**
	 * 통합보드 관련 데이터 페이징
	 */
	public ListDTO getCommSubBoardPagingList(int curpage, int DispLine, int DispPage, String Systemcode, String commId, String userType, String expireDate, String searchKey, String keyWord, String order, int pBbsId) throws DAOException {
		ListDTO retVal = null;

		try {
			StringBuffer sb = new StringBuffer();
			StringBuffer sbAlias = new StringBuffer();

			sbAlias.append(" system_code, comm_id, bbs_id, seq_no, bbs_no, depth_no, order_no, parent_no, editor_type, ");
			sbAlias.append(" contents_type, subject, rfile_name1, sfile_name1, file_path1, file_size1, ");
			sbAlias.append(" rfile_name2, sfile_name2, file_path2, file_size2, rfile_name3, sfile_name3, file_path3, file_size3, ");
			sbAlias.append(" hit_no, expire_date, target, reg_id, reg_name, reg_email, reg_passwd, reg_date ");

			sb.append(" c.system_code, c.comm_id, c.bbs_id, c.seq_no, c.bbs_no, c.depth_no, c.order_no, c.parent_no, c.editor_type, ");
			sb.append(" c.contents_type, c.subject, c.rfile_name1, c.sfile_name1, c.file_path1, c.file_size1, ");
			sb.append(" c.rfile_name2, c.sfile_name2, c.file_path2, c.file_size2, c.rfile_name3, c.sfile_name3, c.file_path3, c.file_size3, ");
			sb.append(" c.hit_no, c.expire_date, c.target, c.reg_id, c.reg_name, c.reg_email, c.reg_passwd, c.reg_date ");
			// List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("c.seq_no");
			sql.setCutCol("c.bbs_id+c.seq_no");
			sql.setAlias(sbAlias.toString() + ", comm_cnt, nick_name ");
			sql.setSelect(sb.toString() + ", ifnull(count(b.comm_no),0) as comm_cnt, ifnull((select m.user_nick from comm_members m where m.system_code=c.system_code and m.comm_id=c.comm_id and m.user_id=c.reg_id), 'unknown') as nick_name ");
			sql.setFrom(" comm_bbs_contents c LEFT OUTER JOIN comm_bbs_comment b ON c.system_code=b.system_code and c.bbs_id=b.bbs_id and c.seq_no=b.seq_no");
			StringBuffer sbWhere = new StringBuffer();
			sbWhere.append(" c.system_code = ? and c.comm_id = ? and c.bbs_id = ?");
			sql.setString(Systemcode);
			sql.setString(commId);
			sql.setInteger(pBbsId);

			String strWhere = "";
			if (!userType.equals("") && !userType.equals("M")) {
				sbWhere.append(" and (c.target like ? or c.target like '%A%' ) ");
				sql.setString(userType);
			}
			if (!expireDate.equals("") && !userType.equals("M")) {
				sbWhere.append(" and (c.expire_date='' or c.expire_date <= ? ) ");
				sql.setString(expireDate);
			}
			if (!keyWord.equals("")) {
				sbWhere.append(" and c." + searchKey + " like ? ");
				sql.setString("%" + keyWord + "%");
			}
			sql.setWhere(sbWhere.toString());

			if (order.equals(""))
				sql.setOrderby(" c.bbs_no desc, c.order_no asc");
			else
				sql.setOrderby(" " + order);

			sql.setGroupby(sb.toString() + ", c.comm_id ");

			// 파라미터 셋팅
			log.debug("[getCommSubBoardPagingList]" + sql.toString());

			retVal = broker.executeListQuery(sql, curpage, DispLine, DispPage);
			//retVal = broker.executeListQuery(sql, curpage);
			log.debug("============================= step 111111");
			return retVal;

		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
	}

	/**
	 * 커뮤니티 공지사항을 페이지처리 하여 가져온다.
	 * @param curpage
	 * @param DispLine
	 * @param DispPage
	 * @param Systemcode
	 * @param commId
	 * @param userType
	 * @param expireDate
	 * @param searchKey
	 * @param keyWord
	 * @param order
	 * @param pBbsId
	 * @return
	 * @throws DAOException
	 */
	public ListDTO getCommSubNoticePagingList(int curpage, int DispLine, int DispPage, String Systemcode, String commId, String userType, String expireDate, String searchKey, String keyWord, String order, int pBbsId) throws DAOException {
		ListDTO retVal = null;

		try {

			StringBuffer sb = new StringBuffer();
			StringBuffer sbAlias = new StringBuffer();

			sbAlias.append(" system_code, bbs_id, seq_no, bbs_no, depth_no, order_no, parent_no, editor_type, ");
			sbAlias.append(" contents_type, subject, rfile_name1, sfile_name1, file_path1, file_size1, ");
			sbAlias.append(" rfile_name2, sfile_name2, file_path2, file_size2, rfile_name3, sfile_name3, file_path3, file_size3, ");
			sbAlias.append(" hit_no, expire_date, target, reg_id, reg_name, reg_email, reg_passwd, reg_date ");

			sb.append(" c.system_code, c.bbs_id, c.seq_no, c.bbs_no, c.depth_no, c.order_no, c.parent_no, c.editor_type, ");
			sb.append(" c.contents_type, c.subject, c.rfile_name1, c.sfile_name1, c.file_path1, c.file_size1, ");
			sb.append(" c.rfile_name2, c.sfile_name2, c.file_path2, c.file_size2, c.rfile_name3, c.sfile_name3, c.file_path3, c.file_size3, ");
			sb.append(" c.hit_no, c.expire_date, c.target, c.reg_id, c.reg_name, c.reg_email, c.reg_passwd, c.reg_date ");
			// List Sql문 생성
			ListStatement sql = new ListStatement();
			sql.setTotalCol("c.seq_no");
			sql.setCutCol("c.bbs_id+c.seq_no");
			sql.setAlias(sbAlias.toString() + ", nick_name ");
			sql.setSelect(sb.toString() + ", ifnull((select m.user_nick from comm_members m where m.system_code=c.system_code and m.comm_id=c.comm_id and m.user_id=c.reg_id), 'unknown') as nick_name ");
			sql.setFrom(" comm_bbs_contents c");
			StringBuffer sbWhere = new StringBuffer();
			sbWhere.append(" c.system_code = ? and c.comm_id = ? and c.bbs_id = ?");
			sql.setString(Systemcode);
			sql.setString(commId);
			sql.setInteger(pBbsId);

			String strWhere = "";
			if (!userType.equals("") && !userType.equals("M")) {
				sbWhere.append(" and (c.target like ? or c.target like '%A%' ) ");
				sql.setString(userType);
			}
			if (!expireDate.equals("") && !userType.equals("M")) {
				sbWhere.append(" and (c.expire_date='' or c.expire_date <= ? ) ");
				sql.setString(expireDate);
			}
			if (!keyWord.equals("")) {
				sbWhere.append(" and c." + searchKey + " like ? ");
				sql.setString("%" + keyWord + "%");
			}
			sql.setWhere(sbWhere.toString());

			if (order.equals(""))
				sql.setOrderby(" c.bbs_no desc, c.order_no asc");
			else
				sql.setOrderby(" " + order);

			sql.setGroupby(sb.toString() + ", c.comm_id ");

			// 파라미터 셋팅
			log.debug("[getCommSubNoticePagingList]" + sql.toString());

			retVal = broker.executeListQuery(sql, curpage, DispLine, DispPage);
			//retVal = broker.executeListQuery(sql, curpage);
			log.debug("============================= step 111111");
			return retVal;

		} catch (SQLException e) {
			log.error(e);
			throw new DAOException(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}
	}

	/**
	 * 게시판 seq_no 최고값+1 을 가져온다
	 * @param SystemCode
	 * @param BbsId
	 * @return
	 * @throws DAOException
	 */
	public int getMaxSeqNo(String SystemCode, int BbsId) throws DAOException {
		int maxSeqNo = 0;

		StringBuffer sb = new StringBuffer();
		sb.append("\n SELECT ifnull(max(seq_no),0)+1 as max_seq_no ");
		sb.append("\n   FROM comm_bbs_contents ");
		sb.append("\n  WHERE system_code = ? ");
		sb.append("\n    AND bbs_id = ? ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setInteger(BbsId);

		log.debug("[getMaxSeqNo]" + sql.toString());

		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			if (rs.next()) {
				maxSeqNo = rs.getInt("max_seq_no");
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

		return maxSeqNo;
	}

	/**
	 * Overriding Method
	 * @param SystemCode
	 * @param BbsId
	 * @return
	 * @throws DAOException
	 */
	public int getMaxSeqNo(String SystemCode, String commId, int BbsId) throws DAOException {
		int maxSeqNo = 0;

		StringBuffer sb = new StringBuffer();
		sb.append(" select ifnull(max(seq_no),0)+1 as max_seq_no ");
		sb.append(" from comm_bbs_contents ");
		sb.append(" where system_code = ? and comm_id = ? and bbs_id = ? ");

		QueryStatement sql = new QueryStatement();
		sql.setSql(sb.toString());
		sql.setString(SystemCode);
		sql.setString(commId);
		sql.setInteger(BbsId);

		log.debug("[getMaxSeqNo]" + sql.toString());

		RowSet rs = null;
		try {
			rs = broker.executeQuery(sql);
			if (rs.next()) {
				maxSeqNo = rs.getInt("max_seq_no");
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

		return maxSeqNo;
	}

	/**
	 * 게시물 조회수 증가하기
	 * @param SystemCode
	 * @param BbsId
	 * @param SeqNo
	 * @return
	 * @throws DAOException
	 */
	public int hitUpBbsContents(String SystemCode, int commId, int BbsId, int SeqNo) throws DAOException {
		int retVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		sb.append("\n UPDATE comm_bbs_contents SET hit_no=hit_no+1");
		sb.append("\n  WHERE system_code = ? AND comm_id= ? AND bbs_id = ? AND seq_no = ?");
		sql.setSql(sb.toString());

		sql.setString(SystemCode);
		sql.setInteger(commId);
		sql.setInteger(BbsId);
		sql.setInteger(SeqNo);

		log.debug("[hitUpBbsContents]" + sql.toString());
		try {
			retVal = broker.executeUpdate(sql);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}

	/**
	 * 답글 등록시 순서 정렬을 위한 Update
	 * @param bbsContents
	 * @param actionMode	Ins,Del
	 * @return
	 * @throws DAOException
	 */
	public boolean replyUpdateBbsContents(CommBbsContentsDTO bbsContents, String actionMode) throws DAOException {
		boolean retVal = false;
		int iVal = 0;
		QueryStatement sql = new QueryStatement();
		StringBuffer sb = new StringBuffer();
		if (actionMode.equals("Ins"))
			sb.append("\n UPDATE comm_bbs_contents SET order_no = order_no+1  ");
		else
			sb.append("\n UPDATE comm_bbs_contents SET order_no = order_no-1  ");
		sb.append("\n  WHERE system_code = ? AND bbs_id = ? AND bbs_no = ? AND order_no >= ? ");
		sql.setSql(sb.toString());

		sql.setString(bbsContents.getSystemCode());
		sql.setInteger(bbsContents.getBbsId());
		sql.setInteger(bbsContents.getBbsNo());
		sql.setInteger(bbsContents.getOrderNo());

		log.debug("[replyUpdateBbsContents]" + sql.toString());
		try {
			iVal = broker.executeUpdate(sql);
			retVal = true;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new DAOException(e.getMessage());
		}

		return retVal;
	}



}

