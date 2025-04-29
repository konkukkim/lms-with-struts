/**
 * @(#)CurriResContentsDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.curriresearch.dto;


/**
 * CURRI_RES_CONTENTS 테이블 Dto 클래스.
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
public class CurriResContentsDTO {

    protected String systemCode="";
    protected String curriCode="";
    protected int curriYear;
    protected int curriTerm;
    protected int resId;
    protected int resNo;
    protected int sumExpCnt;
    protected String contents="";
    protected String contentsType="";
    protected String example1="";
    protected String example2="";
    protected String example3="";
    protected String example4="";
    protected String example5="";
    protected String example6="";
    protected String example7="";
    protected String example8="";
    protected String example9="";
    protected String example10="";
    protected String exampleNum="O";
    protected int exampleCnt=0;
    protected String regId="";
    protected String regDate="";
    protected String modId="";
    protected String modDate="";


    protected String answer[] = new String[]{"0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0","0.0"};
    protected int checkCnt[] = new int[]{0,0,0,0,0,0,0,0,0,0,0};

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
     * get resId.
     *
     * @return      resId
     */
    public int getResId() {
        return  this.resId;
    }

    /**
     * set resId.
     *
     * @param       resId
     */
    public void setResId(int resId) {
        this.resId = resId;
    }

    /**
     * get resNo.
     *
     * @return      resNo
     */
    public int getResNo() {
        return  this.resNo;
    }

    /**
     * set resNo.
     *
     * @param       resNo
     */
    public void setResNo(int resNo) {
        this.resNo = resNo;
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
     * get contentsType.
     *
     * @return      contentsType
     */
    public String getContentsType() {
        return  this.contentsType;
    }

    /**
     * set contentsType.
     *
     * @param       contentsType
     */
    public void setContentsType(String contentsType) {
        this.contentsType = contentsType;
    }

    /**
     * get example1.
     *
     * @return      example1
     */
    public String getExample1() {
        return  this.example1;
    }

    /**
     * set example1.
     *
     * @param       example1
     */
    public void setExample1(String example1) {
        this.example1 = example1;
    }

    /**
     * get example2.
     *
     * @return      example2
     */
    public String getExample2() {
        return  this.example2;
    }

    /**
     * set example2.
     *
     * @param       example2
     */
    public void setExample2(String example2) {
        this.example2 = example2;
    }

    /**
     * get example3.
     *
     * @return      example3
     */
    public String getExample3() {
        return  this.example3;
    }

    /**
     * set example3.
     *
     * @param       example3
     */
    public void setExample3(String example3) {
        this.example3 = example3;
    }

    /**
     * get example4.
     *
     * @return      example4
     */
    public String getExample4() {
        return  this.example4;
    }

    /**
     * set example4.
     *
     * @param       example4
     */
    public void setExample4(String example4) {
        this.example4 = example4;
    }

    /**
     * get example5.
     *
     * @return      example5
     */
    public String getExample5() {
        return  this.example5;
    }

    /**
     * set example5.
     *
     * @param       example5
     */
    public void setExample5(String example5) {
        this.example5 = example5;
    }

    /**
     * get example6.
     *
     * @return      example6
     */
    public String getExample6() {
        return  this.example6;
    }

    /**
     * set example6.
     *
     * @param       example6
     */
    public void setExample6(String example6) {
        this.example6 = example6;
    }

    /**
     * get example7.
     *
     * @return      example7
     */
    public String getExample7() {
        return  this.example7;
    }

    /**
     * set example7.
     *
     * @param       example7
     */
    public void setExample7(String example7) {
        this.example7 = example7;
    }

    /**
     * get example8.
     *
     * @return      example8
     */
    public String getExample8() {
        return  this.example8;
    }

    /**
     * set example8.
     *
     * @param       example8
     */
    public void setExample8(String example8) {
        this.example8 = example8;
    }

    /**
     * get example9.
     *
     * @return      example9
     */
    public String getExample9() {
        return  this.example9;
    }

    /**
     * set example9.
     *
     * @param       example9
     */
    public void setExample9(String example9) {
        this.example9 = example9;
    }

    /**
     * get example10.
     *
     * @return      example10
     */
    public String getExample10() {
        return  this.example10;
    }

    /**
     * set example10.
     *
     * @param       example10
     */
    public void setExample10(String example10) {
        this.example10 = example10;
    }

    /**
     * get exampleNum.
     *
     * @return      exampleNum
     */
    public String getExampleNum() {
        return  this.exampleNum;
    }

    /**
     * set exampleNum.
     *
     * @param       exampleNum
     */
    public void setExampleNum(String exampleNum) {
        this.exampleNum = exampleNum;
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
	 * @return Returns the answer.
	 */
	public String[] getAnswer() {
		return answer;
	}
	/**
	 * @param answer The answer to set.
	 */
	public void setAnswer(String[] answer) {
		this.answer = answer;
	}

	/**
	 * @return Returns the exampleCnt.
	 */
	public int getExampleCnt() {
		return exampleCnt;
	}
	/**
	 * @param exampleCnt The exampleCnt to set.
	 */
	public void setExampleCnt(int exampleCnt) {
		this.exampleCnt = exampleCnt;
	}

	/**
	 * @return Returns the checkCnt.
	 */
	public int[] getCheckCnt() {
		return checkCnt;
	}
	/**
	 * @param checkCnt The checkCnt to set.
	 */
	public void setCheckCnt(int[] checkCnt) {
		this.checkCnt = checkCnt;
	}
    /**
     * toString
     *
     * @return  String
     */
    public String toString() {
        return  "systemCode="+systemCode+",curriCode="+curriCode+",curriYear="+curriYear+",curriTerm="+curriTerm+",resId="+resId
               +",resNo="+resNo+",contents="+contents+",contentsType="+contentsType+",example1="+example1+",example2="+example2
               +",example3="+example3+",example4="+example4+",example5="+example5+",example6="+example6+",example7="+example7
               +",example8="+example8+",example9="+example9+",example10="+example10+",exampleNum="+exampleNum+",regId="+regId
               +",regDate="+regDate+",modId="+modId+",modDate="+modDate;
    }

	public int getSumExpCnt() {
		return sumExpCnt;
	}

	public void setSumExpCnt(int sumExpCnt) {
		this.sumExpCnt = sumExpCnt;
	}

    }
