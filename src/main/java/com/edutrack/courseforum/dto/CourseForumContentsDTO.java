/**
* @(#)CurriBbsContentsDTO
*
* Copyright 2004 mediopia. All Rights Reserved.
*/
package com.edutrack.courseforum.dto;

/**
 * CURRI_BBS_CONTENTS 테이블 Dto 클래스.
 *
 ************************************************
 * DATE         AUTHOR      DESCRIPTION
 * ----------------------------------------------
 * 2004. 9. 13.  mediopia       Initial Release
 ************************************************
 *
 * @author      mediopia
 * @version     1.0
 * @since       2004. 10. 15.
 */
public class CourseForumContentsDTO {
	    protected String systemCode;
	    protected String curriCode;
	    protected int curriYear;
	    protected int curriTerm;
	    protected String courseId;
	    protected int forumId;
	    protected int seqNo;
	    protected int bbsNo;
	    protected int depthNo;
	    protected int levelNo;
	    protected int parentNo;
	    protected String contentsType;
	    protected String editorType;
	    protected String subject;
	    protected String keyword;
	    protected String contents;
	    protected String rfileName;
	    protected String sfileName;
	    protected String filePath;
	    protected String fileSize;
	    protected int hitNo;
	    protected String regId;
	    protected String regName;
	    protected String regPasswd;
	    protected int dispLine;
	    protected String regDateEtc;
	    protected int commCnt;
	    
	    protected String startDate;
	    protected String endDate;
	    
	    
		/**
		 * @return endDate
		 */
		public String getEndDate() {
			return endDate;
		}
		/**
		 * @param endDate 설정하려는 endDate
		 */
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		/**
		 * @return startDate
		 */
		public String getStartDate() {
			return startDate;
		}
		/**
		 * @param startDate 설정하려는 startDate
		 */
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		/**
		 * @return commCnt
		 */
		public int getCommCnt() {
			return commCnt;
		}
		/**
		 * @param commCnt 설정하려는 commCnt
		 */
		public void setCommCnt(int commCnt) {
			this.commCnt = commCnt;
		}
		/**
		 * @return regDateEtc
		 */
		public String getRegDateEtc() {
			return regDateEtc;
		}
		/**
		 * @param regDateEtc 설정하려는 regDateEtc
		 */
		public void setRegDateEtc(String regDateEtc) {
			this.regDateEtc = regDateEtc;
		}
		/**
		 * @return dispLine
		 */
		public int getDispLine() {
			return dispLine;
		}
		/**
		 * @param dispLine 설정하려는 dispLine
		 */
		public void setDispLine(int dispLine) {
			this.dispLine = dispLine;
		}
		/**
		 * @return regPasswd
		 */
		public String getRegPasswd() {
			return regPasswd;
		}
		/**
		 * @param regPasswd 설정하려는 regPasswd
		 */
		public void setRegPasswd(String regPasswd) {
			this.regPasswd = regPasswd;
		}
		/**
		 * @return Returns the forumCnt.
		 */
		public int getForumCnt() {
			return forumCnt;
		}
		/**
		 * @param forumCnt The forumCnt to set.
		 */
		public void setForumCnt(int forumCnt) {
			this.forumCnt = forumCnt;
		}
	    /** 추가 **/
	    protected int forumCnt;

		/**
		 * @return Returns the bbsNo.
		 */
		public int getBbsNo() {
			return bbsNo;
		}
		/**
		 * @param bbsNo The bbsNo to set.
		 */
		public void setBbsNo(int bbsNo) {
			this.bbsNo = bbsNo;
		}
		/**
		 * @return Returns the contents.
		 */
		public String getContents() {
			return contents;
		}
		/**
		 * @param contents The contents to set.
		 */
		public void setContents(String contents) {
			this.contents = contents;
		}
		/**
		 * @return Returns the contentsType.
		 */
		public String getContentsType() {
			return contentsType;
		}
		/**
		 * @param contentsType The contentsType to set.
		 */
		public void setContentsType(String contentsType) {
			this.contentsType = contentsType;
		}
		/**
		 * @return Returns the courseId.
		 */
		public String getCourseId() {
			return courseId;
		}
		/**
		 * @param courseId The courseId to set.
		 */
		public void setCourseId(String courseId) {
			this.courseId = courseId;
		}
		/**
		 * @return Returns the curriCode.
		 */
		public String getCurriCode() {
			return curriCode;
		}
		/**
		 * @param curriCode The curriCode to set.
		 */
		public void setCurriCode(String curriCode) {
			this.curriCode = curriCode;
		}
		/**
		 * @return Returns the curriTerm.
		 */
		public int getCurriTerm() {
			return curriTerm;
		}
		/**
		 * @param curriTerm The curriTerm to set.
		 */
		public void setCurriTerm(int curriTerm) {
			this.curriTerm = curriTerm;
		}
		/**
		 * @return Returns the curriYear.
		 */
		public int getCurriYear() {
			return curriYear;
		}
		/**
		 * @param curriYear The curriYear to set.
		 */
		public void setCurriYear(int curriYear) {
			this.curriYear = curriYear;
		}
		/**
		 * @return Returns the depthNo.
		 */
		public int getDepthNo() {
			return depthNo;
		}
		/**
		 * @param depthNo The depthNo to set.
		 */
		public void setDepthNo(int depthNo) {
			this.depthNo = depthNo;
		}
		/**
		 * @return Returns the editorType.
		 */
		public String getEditorType() {
			return editorType;
		}
		/**
		 * @param editorType The editorType to set.
		 */
		public void setEditorType(String editorType) {
			this.editorType = editorType;
		}
		/**
		 * @return Returns the filePath.
		 */
		public String getFilePath() {
			return filePath;
		}
		/**
		 * @param filePath The filePath to set.
		 */
		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}
		/**
		 * @return Returns the fileSize.
		 */
		public String getFileSize() {
			return fileSize;
		}
		/**
		 * @param fileSize The fileSize to set.
		 */
		public void setFileSize(String fileSize) {
			this.fileSize = fileSize;
		}
		/**
		 * @return Returns the forumId.
		 */
		public int getForumId() {
			return forumId;
		}
		/**
		 * @param forumId The forumId to set.
		 */
		public void setForumId(int forumId) {
			this.forumId = forumId;
		}
		/**
		 * @return Returns the hitNo.
		 */
		public int getHitNo() {
			return hitNo;
		}
		/**
		 * @param hitNo The hitNo to set.
		 */
		public void setHitNo(int hitNo) {
			this.hitNo = hitNo;
		}
		/**
		 * @return Returns the keyword.
		 */
		public String getKeyword() {
			return keyword;
		}
		/**
		 * @param keyword The keyword to set.
		 */
		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}
		/**
		 * @return Returns the levelNo.
		 */
		public int getLevelNo() {
			return levelNo;
		}
		/**
		 * @param levelNo The levelNo to set.
		 */
		public void setLevelNo(int levelNo) {
			this.levelNo = levelNo;
		}
		/**
		 * @return Returns the modDate.
		 */
		public String getModDate() {
			return modDate;
		}
		/**
		 * @param modDate The modDate to set.
		 */
		public void setModDate(String modDate) {
			this.modDate = modDate;
		}
		/**
		 * @return Returns the modId.
		 */
		public String getModId() {
			return modId;
		}
		/**
		 * @param modId The modId to set.
		 */
		public void setModId(String modId) {
			this.modId = modId;
		}
		/**
		 * @return Returns the parentNo.
		 */
		public int getParentNo() {
			return parentNo;
		}
		/**
		 * @param parentNo The parentNo to set.
		 */
		public void setParentNo(int parentNo) {
			this.parentNo = parentNo;
		}
		/**
		 * @return Returns the regDate.
		 */
		public String getRegDate() {
			return regDate;
		}
		/**
		 * @param regDate The regDate to set.
		 */
		public void setRegDate(String regDate) {
			this.regDate = regDate;
		}
		/**
		 * @return Returns the regId.
		 */
		public String getRegId() {
			return regId;
		}
		/**
		 * @param regId The regId to set.
		 */
		public void setRegId(String regId) {
			this.regId = regId;
		}
		/**
		 * @return Returns the rfileName.
		 */
		public String getRfileName() {
			return rfileName;
		}
		/**
		 * @param rfileName The rfileName to set.
		 */
		public void setRfileName(String rfileName) {
			this.rfileName = rfileName;
		}
		/**
		 * @return Returns the seqNo.
		 */
		public int getSeqNo() {
			return seqNo;
		}
		/**
		 * @param seqNo The seqNo to set.
		 */
		public void setSeqNo(int seqNo) {
			this.seqNo = seqNo;
		}
		/**
		 * @return Returns the sfileName.
		 */
		public String getSfileName() {
			return sfileName;
		}
		/**
		 * @param sfileName The sfileName to set.
		 */
		public void setSfileName(String sfileName) {
			this.sfileName = sfileName;
		}
		/**
		 * @return Returns the subject.
		 */
		public String getSubject() {
			return subject;
		}
		/**
		 * @param subject The subject to set.
		 */
		public void setSubject(String subject) {
			this.subject = subject;
		}
		/**
		 * @return Returns the systemCode.
		 */
		public String getSystemCode() {
			return systemCode;
		}
		/**
		 * @param systemCode The systemCode to set.
		 */
		public void setSystemCode(String systemCode) {
			this.systemCode = systemCode;
		}
	    protected String regDate;
	    protected String modId;
	    protected String modDate;
		/**
		 * @return Returns the regName.
		 */
		public String getRegName() {
			return regName;
		}
		/**
		 * @param regName The regName to set.
		 */
		public void setRegName(String regName) {
			this.regName = regName;
		}
}
