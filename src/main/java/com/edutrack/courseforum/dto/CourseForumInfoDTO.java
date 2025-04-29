/**
 * @(#)CourseForumInfoDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.courseforum.dto;


/**
 * COURSE_FORUM_INFO 테이블 Dto 클래스.
 *
 ************************************************
 * DATE         AUTHOR      DESCRIPTION
 * ----------------------------------------------
 * 2004. 9. 13.  mediopia       Initial Release
 ************************************************
 *
 * @author      mediopia
 * @version     1.0
 * @since       2004. 9. 13.
 */
public class CourseForumInfoDTO {

    protected String systemCode;
    protected String curriCode;
    protected int curriYear;
    protected int curriTerm;
    protected String courseId;
    protected String courseName;
    protected int forumId;
    protected int subForumId;
    protected String forumType;
    protected int parentForumId;
    protected int subTeamNo;
    protected String subTeamName;
    protected String subject;
    protected String contents;
    protected String rfileName;
    protected String sfileName;
    protected String filePath;
    protected String fileSize;
    protected int forumScore;
    protected String startDate;
    protected String endDate;
    protected String openYn;
    protected String registYn;
    protected String editorYn;
    protected String voiceYn;
    protected String commentUseYn;
    protected String emoticonUseYn;
    protected String viewThreadYn;
    protected String viewPrevNextYn;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    protected int	 contentsCnt;
    protected int	 hitCnt;
    protected String joinForumYN;	// forum join capable yes or no
    protected int	 teamUserCnt;
    protected String[]  sfileName2;
    protected String curriName;

    
    /**
	 * @return curriName
	 */
	public String getCurriName() {
		return curriName;
	}

	/**
	 * @param curriName 설정하려는 curriName
	 */
	public void setCurriName(String curriName) {
		this.curriName = curriName;
	}

	/**
     * get systemCode.
     *
     * @return      systemCode
     */
    public String getSystemCode() {
        return  this.systemCode;
    }

    /**
     * set systemCode.
     *
     * @param       systemCode
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * get curriCode.
     *
     * @return      curriCode
     */
    public String getCurriCode() {
        return  this.curriCode;
    }

    /**
     * set curriCode.
     *
     * @param       curriCode
     */
    public void setCurriCode(String curriCode) {
        this.curriCode = curriCode;
    }

    /**
     * get curriYear.
     *
     * @return      curriYear
     */
    public int getCurriYear() {
        return  this.curriYear;
    }

    /**
     * set curriYear.
     *
     * @param       curriYear
     */
    public void setCurriYear(int curriYear) {
        this.curriYear = curriYear;
    }

    /**
     * get curriTerm.
     *
     * @return      curriTerm
     */
    public int getCurriTerm() {
        return  this.curriTerm;
    }

    /**
     * set curriTerm.
     *
     * @param       curriTerm
     */
    public void setCurriTerm(int curriTerm) {
        this.curriTerm = curriTerm;
    }

    /**
     * get courseId.
     *
     * @return      courseId
     */
    public String getCourseId() {
        return  this.courseId;
    }

    /**
     * set courseId.
     *
     * @param       courseId
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * get forumId.
     *
     * @return      forumId
     */
    public int getForumId() {
        return  this.forumId;
    }

    /**
     * set forumId.
     *
     * @param       forumId
     */
    public void setForumId(int forumId) {
        this.forumId = forumId;
    }

    /**
     * get forumType.
     *
     * @return      forumType
     */
    public String getForumType() {
        return  this.forumType;
    }

    /**
     * set forumType.
     *
     * @param       forumType
     */
    public void setForumType(String forumType) {
        this.forumType = forumType;
    }

    /**
     * get parentForumId.
     *
     * @return      parentForumId
     */
    public int getParentForumId() {
        return  this.parentForumId;
    }

    /**
     * set parentForumId.
     *
     * @param       parentForumId
     */
    public void setParentForumId(int parentForumId) {
        this.parentForumId = parentForumId;
    }

    /**
     * get subTeamNo.
     *
     * @return      subTeamNo
     */
    public int getSubTeamNo() {
        return  this.subTeamNo;
    }

    /**
     * set subTeamNo.
     *
     * @param       subTeamNo
     */
    public void setSubTeamNo(int subTeamNo) {
        this.subTeamNo = subTeamNo;
    }

    /**
     * get subject.
     *
     * @return      subject
     */
    public String getSubject() {
        return  this.subject;
    }

    /**
     * set subject.
     *
     * @param       subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * get contents.
     *
     * @return      contents
     */
    public String getContents() {
        return  this.contents;
    }

    /**
     * set contents.
     *
     * @param       contents
     */
    public void setContents(String contents) {
        this.contents = contents;
    }

    /**
     * get rfileName.
     *
     * @return      rfileName
     */
    public String getRfileName() {
        return  this.rfileName;
    }

    /**
     * set rfileName.
     *
     * @param       rfileName
     */
    public void setRfileName(String rfileName) {
        this.rfileName = rfileName;
    }

    /**
     * get sfileName.
     *
     * @return      sfileName
     */
    public String getSfileName() {
        return  this.sfileName;
    }

    /**
     * set sfileName.
     *
     * @param       sfileName
     */
    public void setSfileName(String sfileName) {
        this.sfileName = sfileName;
    }

    /**
     * get filePath.
     *
     * @return      filePath
     */
    public String getFilePath() {
        return  this.filePath;
    }

    /**
     * set filePath.
     *
     * @param       filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * get fileSize.
     *
     * @return      fileSize
     */
    public String getFileSize() {
        return  this.fileSize;
    }

    /**
     * set fileSize.
     *
     * @param       fileSize
     */
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * get forumScore.
     *
     * @return      forumScore
     */
    public int getForumScore() {
        return  this.forumScore;
    }

    /**
     * set forumScore.
     *
     * @param       forumScore
     */
    public void setForumScore(int forumScore) {
        this.forumScore = forumScore;
    }

    /**
     * get startDate.
     *
     * @return      startDate
     */
    public String getStartDate() {
        return  this.startDate;
    }

    /**
     * set startDate.
     *
     * @param       startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * get endDate.
     *
     * @return      endDate
     */
    public String getEndDate() {
        return  this.endDate;
    }

    /**
     * set endDate.
     *
     * @param       endDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * get openYn.
     *
     * @return      openYn
     */
    public String getOpenYn() {
        return  this.openYn;
    }

    /**
     * set openYn.
     *
     * @param       openYn
     */
    public void setOpenYn(String openYn) {
        this.openYn = openYn;
    }

    /**
     * get registYn.
     *
     * @return      registYn
     */
    public String getRegistYn() {
        return  this.registYn;
    }

    /**
     * set registYn.
     *
     * @param       registYn
     */
    public void setRegistYn(String registYn) {
        this.registYn = registYn;
    }

    /**
     * get editorYn.
     *
     * @return      editorYn
     */
    public String getEditorYn() {
        return  this.editorYn;
    }

    /**
     * set editorYn.
     *
     * @param       editorYn
     */
    public void setEditorYn(String editorYn) {
        this.editorYn = editorYn;
    }

    /**
     * get voiceYn.
     *
     * @return      voiceYn
     */
    public String getVoiceYn() {
        return  this.voiceYn;
    }

    /**
     * set voiceYn.
     *
     * @param       voiceYn
     */
    public void setVoiceYn(String voiceYn) {
        this.voiceYn = voiceYn;
    }

    /**
     * get commentUseYn.
     *
     * @return      commentUseYn
     */
    public String getCommentUseYn() {
        return  this.commentUseYn;
    }

    /**
     * set commentUseYn.
     *
     * @param       commentUseYn
     */
    public void setCommentUseYn(String commentUseYn) {
        this.commentUseYn = commentUseYn;
    }

    /**
     * get emoticonUseYn.
     *
     * @return      emoticonUseYn
     */
    public String getEmoticonUseYn() {
        return  this.emoticonUseYn;
    }

    /**
     * set emoticonUseYn.
     *
     * @param       emoticonUseYn
     */
    public void setEmoticonUseYn(String emoticonUseYn) {
        this.emoticonUseYn = emoticonUseYn;
    }

    /**
     * get viewThreadYn.
     *
     * @return      viewThreadYn
     */
    public String getViewThreadYn() {
        return  this.viewThreadYn;
    }

    /**
     * set viewThreadYn.
     *
     * @param       viewThreadYn
     */
    public void setViewThreadYn(String viewThreadYn) {
        this.viewThreadYn = viewThreadYn;
    }

    /**
     * get viewPrevNextYn.
     *
     * @return      viewPrevNextYn
     */
    public String getViewPrevNextYn() {
        return  this.viewPrevNextYn;
    }

    /**
     * set viewPrevNextYn.
     *
     * @param       viewPrevNextYn
     */
    public void setViewPrevNextYn(String viewPrevNextYn) {
        this.viewPrevNextYn = viewPrevNextYn;
    }

    /**
     * get regId.
     *
     * @return      regId
     */
    public String getRegId() {
        return  this.regId;
    }

    /**
     * set regId.
     *
     * @param       regId
     */
    public void setRegId(String regId) {
        this.regId = regId;
    }

    /**
     * get regDate.
     *
     * @return      regDate
     */
    public String getRegDate() {
        return  this.regDate;
    }

    /**
     * set regDate.
     *
     * @param       regDate
     */
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    /**
     * get modId.
     *
     * @return      modId
     */
    public String getModId() {
        return  this.modId;
    }

    /**
     * set modId.
     *
     * @param       modId
     */
    public void setModId(String modId) {
        this.modId = modId;
    }

    /**
     * get modDate.
     *
     * @return      modDate
     */
    public String getModDate() {
        return  this.modDate;
    }

    /**
     * set modDate.
     *
     * @param       modDate
     */
    public void setModDate(String modDate) {
        this.modDate = modDate;
    }
    
    
    /**
     * get contentsCnt.
     *
     * @return      contentsCnt
     */
    public int getContentsCnt() {
        return  this.contentsCnt;
    }

    /**
     * set contentsCnt.
     *
     * @param       contentsCnt
     */
    public void setContentsCnt(int contentsCnt) {
        this.contentsCnt = contentsCnt;
    }

    /**
     * get commentCnt.
     *
     * @return      commentCnt
     */
    public int getHitCnt() {
        return  this.hitCnt;
    }

    /**
     * set commentCnt.
     *
     * @param       commentCnt
     */
    public void setHitCnt(int hitCnt) {
        this.hitCnt = hitCnt;
    }
    
	/**
	 * @return Returns the courseName.
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * @param courseName The courseName to set.
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * @return Returns the subforumId.
	 */
	public int getSubForumId() {
		return subForumId;
	}
	/**
	 * @param subforumId The subforumId to set.
	 */
	public void setSubForumId(int subForumId) {
		this.subForumId = subForumId;
	}
	/**
	 * @return Returns the joinForumYN.
	 */
	public String getJoinForumYN() {
		return joinForumYN;
	}
	/**
	 * @param startForumYn The joinForumYN to set.
	 */
	public void setJoinForumYN(String joinForumYN) {
		this.joinForumYN = joinForumYN;
	}
	
	
    /**
     * toString 
     *
     * @return  String 
     */
    public String toString() {
        return  "systemCode="+systemCode+",curriCode="+curriCode+",curriYear="+curriYear+",curriTerm="+curriTerm+",courseId="+courseId
               +",forumId="+forumId+",forumType="+forumType+",parentForumId="+parentForumId+",subTeamNo="+subTeamNo+",subject="+subject
               +",contents="+contents+",rfileName="+rfileName+",sfileName="+sfileName+",filePath="+filePath+",fileSize="+fileSize
               +",forumScore="+forumScore+",startDate="+startDate+",endDate="+endDate+",openYn="+openYn+",registYn="+registYn
               +",editorYn="+editorYn+",voiceYn="+voiceYn+",commentUseYn="+commentUseYn+",emoticonUseYn="+emoticonUseYn+",viewThreadYn="+viewThreadYn
               +",viewPrevNextYn="+viewPrevNextYn+",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }
    
	/**
	 * @return Returns the subTeamName.
	 */
	public String getSubTeamName() {
		return subTeamName;
	}
	/**
	 * @param subTeamName The subTeamName to set.
	 */
	public void setSubTeamName(String subTeamName) {
		this.subTeamName = subTeamName;
	}


	/**
	 * @return Returns the teamUserCnt.
	 */
	public int getTeamUserCnt() {
		return teamUserCnt;
	}
	/**
	 * @param teamUserCnt The teamUserCnt to set.
	 */
	public void setTeamUserCnt(int teamUserCnt) {
		this.teamUserCnt = teamUserCnt;
	}
	/**
	 * @return Returns the sfileName2.
	 */
	public String[] getSfileName2() {
		return sfileName2;
	}
	/**
	 * @param sfileName2 The sfileName2 to set.
	 */
	public void setSfileName2(String[] sfileName2) {
		this.sfileName2 = sfileName2;
	}
}
