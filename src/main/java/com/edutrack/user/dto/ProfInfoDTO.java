/**
 * @(#)ProfInfoDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.user.dto;


/**
 * PROF_INFO 테이블 Dto 클래스.
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
public class ProfInfoDTO {

    protected String systemCode="";
    protected String userId="";
    protected String userName="";
    protected String userPhoto="";
    protected String photoPath="";
    protected String oldUserPhoto="";
    protected String phoneNumber="";
    protected String career="";
    protected String major="";
    protected String books="";
    protected String intro="";
    protected String regId="";
    protected String regDate="";
    protected String modId="";
    protected String modDate="";

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
     * get userId.
     *
     * @return      userId
     */
    public String getUserId() {
        return  this.userId;
    }

    /**
     * set userId.
     *
     * @param       userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
     * get userPhoto.
     *
     * @return      userPhoto
     */
    public String getUserPhoto() {
        return  this.userPhoto;
    }

    /**
     * set userPhoto.
     *
     * @param       userPhoto
     */
    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    /**
     * get phoneNumber.
     *
     * @return      phoneNumber
     */
    public String getPhoneNumber() {
        return  this.phoneNumber;
    }

    /**
     * set phoneNumber.
     *
     * @param       phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * get career.
     *
     * @return      career
     */
    public String getCareer() {
        return  this.career;
    }

    /**
     * set career.
     *
     * @param       career
     */
    public void setCareer(String career) {
        this.career = career;
    }

    /**
     * get major.
     *
     * @return      major
     */
    public String getMajor() {
        return  this.major;
    }

    /**
     * set major.
     *
     * @param       major
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * get books.
     *
     * @return      books
     */
    public String getBooks() {
        return  this.books;
    }

    /**
     * set books.
     *
     * @param       books
     */
    public void setBooks(String books) {
        this.books = books;
    }

    /**
     * get intro.
     *
     * @return      intro
     */
    public String getIntro() {
        return  this.intro;
    }

    /**
     * set intro.
     *
     * @param       intro
     */
    public void setIntro(String intro) {
        this.intro = intro;
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
	 * @return Returns the oldUserPhoto.
	 */
	public String getOldUserPhoto() {
		return oldUserPhoto;
	}
	/**
	 * @param oldUserPhoto The oldUserPhoto to set.
	 */
	public void setOldUserPhoto(String oldUserPhoto) {
		this.oldUserPhoto = oldUserPhoto;
	}
	
	/**
	 * @return Returns the photoPath.
	 */
	public String getPhotoPath() {
		return photoPath;
	}
	/**
	 * @param photoPath The photoPath to set.
	 */
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
    /**
     * toString 
     *
     * @return  String 
     */
    public String toString() {
        return  "systemCode="+systemCode+",userId="+userId+",userPhoto="+userPhoto+",phoneNumber="+phoneNumber+",career="+career
               +",major="+major+",books="+books+",intro="+intro+",regId="+regId+",regDate="+regDate
               +",modId="+modId+",modDate="+modDate;
    }

    }
