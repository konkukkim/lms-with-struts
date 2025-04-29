/**
 * @(#)UsersDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.user.dto;

import java.io.Serializable;


/**
 * USERS 테이블 Dto 클래스.
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
public class UsersDTO implements Serializable{

    protected String systemCode="";
    protected String userId="";
    protected String deptDaecode="";
    protected String deptSocode="";
    protected String deptSoname="";
    protected String userName="";
	protected String userNameEng="";
	protected String rfileName="";
	protected String sfileName="";
	protected String filePath="";
	protected String fileSize="";
    protected String juminNo="";
    protected String passwd="";
    protected String userType="";
    protected String useStatus="";
    protected String phoneHome="";
    protected String phoneMobile="";
    protected String postCode="";
    protected String address="";
    protected String email="";
    protected String sexType="";
    protected String jobCode="";
	protected String compType="";
	protected String compName="";
	protected String compNo="";
	protected String compPostCode="";
	protected String compAddress="";
	protected String compPhone="";
	protected String compGrade="";
	protected String career="";
	protected String major="";
	protected String school="";
	protected String recvMail="";
	protected String recvSms="";
    protected String regId="";
    protected String regDate="";
    protected String modId="";
    protected String modDate="";
    protected int lineNo = 0;

    protected int curriCount;
    protected int completeCount;
    protected int connectCount;
    
    protected String schoolYear;

    public String getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}
	/**
	 * @return the completeCount
	 */
	public int getCompleteCount() {
		return completeCount;
	}
	/**
	 * @param completeCount the completeCount to set
	 */
	public void setCompleteCount(int completeCount) {
		this.completeCount = completeCount;
	}
	/**
	 * @return the connectCount
	 */
	public int getConnectCount() {
		return connectCount;
	}
	/**
	 * @param connectCount the connectCount to set
	 */
	public void setConnectCount(int connectCount) {
		this.connectCount = connectCount;
	}
	/**
	 * @return the curriCount
	 */
	public int getCurriCount() {
		return curriCount;
	}
	/**
	 * @param curriCount the curriCount to set
	 */
	public void setCurriCount(int curriCount) {
		this.curriCount = curriCount;
	}


	public String getCareer() {
		return career;
	}


	public void setCareer(String career) {
		this.career = career;
	}


	public String getCompAddress() {
		return compAddress;
	}


	public void setCompAddress(String compAddress) {
		this.compAddress = compAddress;
	}


	public String getCompGrade() {
		return compGrade;
	}


	public void setCompGrade(String compGrade) {
		this.compGrade = compGrade;
	}


	public String getCompName() {
		return compName;
	}


	public void setCompName(String compName) {
		this.compName = compName;
	}


	public String getCompNo() {
		return compNo;
	}


	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}


	public String getCompPhone() {
		return compPhone;
	}


	public void setCompPhone(String compPhone) {
		this.compPhone = compPhone;
	}


	public String getCompPostCode() {
		return compPostCode;
	}


	public void setCompPostCode(String compPostCode) {
		this.compPostCode = compPostCode;
	}


	public String getCompType() {
		return compType;
	}


	public void setCompType(String compType) {
		this.compType = compType;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getFileSize() {
		return fileSize;
	}


	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}


	public String getMajor() {
		return major;
	}


	public void setMajor(String major) {
		this.major = major;
	}


	public String getRecvMail() {
		return recvMail;
	}


	public void setRecvMail(String recvMail) {
		this.recvMail = recvMail;
	}


	public String getRecvSms() {
		return recvSms;
	}


	public void setRecvSms(String recvSms) {
		this.recvSms = recvSms;
	}


	public String getRfileName() {
		return rfileName;
	}


	public void setRfileName(String rfileName) {
		this.rfileName = rfileName;
	}


	public String getSchool() {
		return school;
	}


	public void setSchool(String school) {
		this.school = school;
	}


	public String getSfileName() {
		return sfileName;
	}


	public void setSfileName(String sfileName) {
		this.sfileName = sfileName;
	}


	public String getUserNameEng() {
		return userNameEng;
	}


	public void setUserNameEng(String userNameEng) {
		this.userNameEng = userNameEng;
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

    /**
     * get deptDaecode.
     *
     * @return      deptDaecode
     */
    public String getDeptDaecode() {
        return  this.deptDaecode;
    }

    /**
     * set deptDaecode.
     *
     * @param       deptDaecode
     */
    public void setDeptDaecode(String deptDaecode) {
        this.deptDaecode = deptDaecode;
    }

    /**
     * get deptSocode.
     *
     * @return      deptSocode
     */
    public String getDeptSocode() {
        return  this.deptSocode;
    }

    /**
     * set deptSocode.
     *
     * @param       deptSocode
     */
    public void setDeptSocode(String deptSocode) {
        this.deptSocode = deptSocode;
    }

    /**
     * get deptSoname.
     *
     * @return      deptSoname
     */
    public String getDeptSoname() {
        return  this.deptSoname;
    }

    /**
     * set deptSoname.
     *
     * @param       deptSoname
     */
    public void setDeptSoname(String deptSoname) {
        this.deptSoname = deptSoname;
    }

    /**
     * get userName.
     *
     * @return      userName
     */
    public String getUserName() {
        return  this.userName;
    }

    /**
     * set userName.
     *
     * @param       userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * get juminNo.
     *
     * @return      juminNo
     */
    public String getJuminNo() {
        return  this.juminNo;
    }

    /**
     * set juminNo.
     *
     * @param       juminNo
     */
    public void setJuminNo(String juminNo) {
        this.juminNo = juminNo;
    }

    /**
     * get passwd.
     *
     * @return      passwd
     */
    public String getPasswd() {
        return  this.passwd;
    }

    /**
     * set passwd.
     *
     * @param       passwd
     */
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    /**
     * get userType.
     *
     * @return      userType
     */
    public String getUserType() {
        return  this.userType;
    }

    /**
     * set userType.
     *
     * @param       userType
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * get useStatus.
     *
     * @return      useStatus
     */
    public String getUseStatus() {
        return  this.useStatus;
    }

    /**
     * set useStatus.
     *
     * @param       useStatus
     */
    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    /**
     * get phoneHome.
     *
     * @return      phoneHome
     */
    public String getPhoneHome() {
        return  this.phoneHome;
    }

    /**
     * set phoneHome.
     *
     * @param       phoneHome
     */
    public void setPhoneHome(String phoneHome) {
        this.phoneHome = phoneHome;
    }

    /**
     * get phoneMobile.
     *
     * @return      phoneMobile
     */
    public String getPhoneMobile() {
        return  this.phoneMobile;
    }

    /**
     * set phoneMobile.
     *
     * @param       phoneMobile
     */
    public void setPhoneMobile(String phoneMobile) {
        this.phoneMobile = phoneMobile;
    }

    /**
     * get postCode.
     *
     * @return      postCode
     */
    public String getPostCode() {
        return  this.postCode;
    }

    /**
     * set postCode.
     *
     * @param       postCode
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * get address.
     *
     * @return      address
     */
    public String getAddress() {
        return  this.address;
    }

    /**
     * set address.
     *
     * @param       address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * get email.
     *
     * @return      email
     */
    public String getEmail() {
        return  this.email;
    }

    /**
     * set email.
     *
     * @param       email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get sexType.
     *
     * @return      sexType
     */
    public String getSexType() {
        return  this.sexType;
    }

    /**
     * set sexType.
     *
     * @param       sexType
     */
    public void setSexType(String sexType) {
        this.sexType = sexType;
    }

    /**
     * get jobCode.
     *
     * @return      jobCode
     */
    public String getJobCode() {
        return  this.jobCode;
    }

    /**
     * set jobCode.
     *
     * @param       jobCode
     */
    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
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
	 * @return Returns the lineNo.
	 */
	public int getLineNo() {
		return lineNo;
	}
	/**
	 * @param lineNo The lineNo to set.
	 */
	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}
    /**
     * toString
     *
     * @return  String
     */
    public String toString() {
        return  "systemCode="+systemCode+",userId="+userId+",deptDaecode="+deptDaecode+",deptSocode="+deptSocode+",userName="+userName
               +",juminNo="+juminNo+",passwd="+passwd+",userType="+userType+",useStatus="+useStatus+",phoneHome="+phoneHome
               +",phoneMobile="+phoneMobile+",postCode="+postCode+",address="+address+",email="+email+",sexType="+sexType
               +",jobCode="+jobCode+",regId="+regId+",regDate="+regDate
               +",modId="+modId+",modDate="+modDate;
    }

    }
