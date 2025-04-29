/**
 * @(#)PopupNoticeDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.popupnotice.dto;


/**
 * POPUP_NOTICE 테이블 Dto 클래스.
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
public class PopupNoticeDTO {

    protected String systemCode;
    protected int seqNo;
    protected String subject;
    protected String keyword;
    protected String contents;
    protected int winWidth;
    protected int winHeight;
    protected int winXposition;
    protected int winYposition;
    protected String useYn;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    protected String popupTarget;

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
     * get seqNo.
     *
     * @return      seqNo
     */
    public int getSeqNo() {
        return  this.seqNo;
    }

    /**
     * set seqNo.
     *
     * @param       seqNo
     */
    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
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
     * get keyword.
     *
     * @return      keyword
     */
    public String getKeyword() {
        return  this.keyword;
    }

    /**
     * set keyword.
     *
     * @param       keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
     * get winWidth.
     *
     * @return      winWidth
     */
    public int getWinWidth() {
        return  this.winWidth;
    }

    /**
     * set winWidth.
     *
     * @param       winWidth
     */
    public void setWinWidth(int winWidth) {
        this.winWidth = winWidth;
    }

    /**
     * get winHeight.
     *
     * @return      winHeight
     */
    public int getWinHeight() {
        return  this.winHeight;
    }

    /**
     * set winHeight.
     *
     * @param       winHeight
     */
    public void setWinHeight(int winHeight) {
        this.winHeight = winHeight;
    }

    /**
     * get winXposition.
     *
     * @return      winXposition
     */
    public int getWinXposition() {
        return  this.winXposition;
    }

    /**
     * set winXposition.
     *
     * @param       winXposition
     */
    public void setWinXposition(int winXposition) {
        this.winXposition = winXposition;
    }

    /**
     * get winYposition.
     *
     * @return      winYposition
     */
    public int getWinYposition() {
        return  this.winYposition;
    }

    /**
     * set winYposition.
     *
     * @param       winYposition
     */
    public void setWinYposition(int winYposition) {
        this.winYposition = winYposition;
    }

  
	/**
	 * @return Returns the useYn.
	 */
	public String getUseYn() {
		return useYn;
	}
	/**
	 * @param useYn The useYn to set.
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
     * get popupTarget.
     *
     * @return      popupTarget
     */
    public String getPopupTarget() {
        return  this.popupTarget;
    }

    /**
     * set popupTarget.
     *
     * @param       popupTarget
     */
    public void setPopupTarget(String popupTarget) {
        this.popupTarget = popupTarget;
    }

    /**
     * toString 
     *
     * @return  String 
     */
    public String toString() {
        return  "systemCode="+systemCode+",seqNo="+seqNo+",subject="+subject+",keyword="+keyword+",contents="+contents
               +",winWidth="+winWidth+",winHeight="+winHeight+",winXposition="+winXposition+",winYposition="+winYposition+",useYn="+useYn
               +",regId="+regId+",regDate="+regDate+",modId="+modId+",modDate="+modDate+",popup_target="+popupTarget;
    }

    }
