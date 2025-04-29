/**
 * @(#)InternetPollDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.poll.dto;


/**
 * INTERNET_POLL 테이블 Dto 클래스.
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
public class InternetPollDTO {

    protected String systemCode;
    protected int pollId;
    protected String startDate;
    protected String endDate;
    protected String status;
    protected String subject;
    protected String contents;
    protected int exampleCnt;
    protected String example1;
    protected String example2;
    protected String example3;
    protected String example4;
    protected String example5;
    protected String example6;
    protected String example7;
    protected String example8;
    protected String example9;
    protected String example10;
    protected int hit1;
    protected int hit2;
    protected int hit3;
    protected int hit4;
    protected int hit5;
    protected int hit6;
    protected int hit7;
    protected int hit8;
    protected int hit9;
    protected int hit10;
    protected String regId;
    protected String regDate;
    protected String modId;
    protected String modDate;
    protected String[] example = new String[10];
    protected int hitCnt = 0;
    protected int[] hit = new int[10];
    protected int chkCnt;
    
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
     * get pollId.
     *
     * @return      pollId
     */
    public int getPollId() {
        return  this.pollId;
    }

    /**
     * set pollId.
     *
     * @param       pollId
     */
    public void setPollId(int pollId) {
        this.pollId = pollId;
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
     * get status.
     *
     * @return      status
     */
    public String getStatus() {
        return  this.status;
    }

    /**
     * set status.
     *
     * @param       status
     */
    public void setStatus(String status) {
        this.status = status;
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
     * get exampleCnt.
     *
     * @return      exampleCnt
     */
    public int getExampleCnt() {
        return  this.exampleCnt;
    }

    /**
     * set exampleCnt.
     *
     * @param       exampleCnt
     */
    public void setExampleCnt(int exampleCnt) {
        this.exampleCnt = exampleCnt;
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
     * get hit1.
     *
     * @return      hit1
     */
    public int getHit1() {
        return  this.hit1;
    }

    /**
     * set hit1.
     *
     * @param       hit1
     */
    public void setHit1(int hit1) {
        this.hit1 = hit1;
    }

    /**
     * get hit2.
     *
     * @return      hit2
     */
    public int getHit2() {
        return  this.hit2;
    }

    /**
     * set hit2.
     *
     * @param       hit2
     */
    public void setHit2(int hit2) {
        this.hit2 = hit2;
    }

    /**
     * get hit3.
     *
     * @return      hit3
     */
    public int getHit3() {
        return  this.hit3;
    }

    /**
     * set hit3.
     *
     * @param       hit3
     */
    public void setHit3(int hit3) {
        this.hit3 = hit3;
    }

    /**
     * get hit4.
     *
     * @return      hit4
     */
    public int getHit4() {
        return  this.hit4;
    }

    /**
     * set hit4.
     *
     * @param       hit4
     */
    public void setHit4(int hit4) {
        this.hit4 = hit4;
    }

    /**
     * get hit5.
     *
     * @return      hit5
     */
    public int getHit5() {
        return  this.hit5;
    }

    /**
     * set hit5.
     *
     * @param       hit5
     */
    public void setHit5(int hit5) {
        this.hit5 = hit5;
    }

    /**
     * get hit6.
     *
     * @return      hit6
     */
    public int getHit6() {
        return  this.hit6;
    }

    /**
     * set hit6.
     *
     * @param       hit6
     */
    public void setHit6(int hit6) {
        this.hit6 = hit6;
    }

    /**
     * get hit7.
     *
     * @return      hit7
     */
    public int getHit7() {
        return  this.hit7;
    }

    /**
     * set hit7.
     *
     * @param       hit7
     */
    public void setHit7(int hit7) {
        this.hit7 = hit7;
    }

    /**
     * get hit8.
     *
     * @return      hit8
     */
    public int getHit8() {
        return  this.hit8;
    }

    /**
     * set hit8.
     *
     * @param       hit8
     */
    public void setHit8(int hit8) {
        this.hit8 = hit8;
    }

    /**
     * get hit9.
     *
     * @return      hit9
     */
    public int getHit9() {
        return  this.hit9;
    }

    /**
     * set hit9.
     *
     * @param       hit9
     */
    public void setHit9(int hit9) {
        this.hit9 = hit9;
    }

    /**
     * get hit10.
     *
     * @return      hit10
     */
    public int getHit10() {
        return  this.hit10;
    }

    /**
     * set hit10.
     *
     * @param       hit10
     */
    public void setHit10(int hit10) {
        this.hit10 = hit10;
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
	 * @return Returns the example.
	 */
	public String[] getExample() {
		return example;
	}
	/**
	 * @param example The example to set.
	 */
	public void setExample(String[] example) {
		this.example = example;
	}	
	
	/**
	 * @return Returns the hitCnt.
	 */
	public int getHitCnt() {
		return hitCnt;
	}
	/**
	 * @param hitCnt The hitCnt to set.
	 */
	public void setHitCnt(int hitCnt) {
		this.hitCnt = hitCnt;
	}
	
	
	/**
	 * @return Returns the hit.
	 */
	public int[] getHit() {
		return hit;
	}
	/**
	 * @param hit The hit to set.
	 */
	public void setHit(int[] hit) {
		this.hit = hit;
	}
	
	/**
     * get chkCnt.
     *
     * @return      chkCnt
     */
    public int getChkCnt() {
        return  this.chkCnt;
    }

    /**
     * set chkCnt.
     *
     * @param       chkCnt
     */
    public void setChkCnt(int chkCnt) {
        this.chkCnt = chkCnt;
    }
    /**
     * toString 
     *
     * @return  String 
     */
    public String toString() {
        return  "systemCode="+systemCode+",pollId="+pollId+",startDate="+startDate+",endDate="+endDate+",status="+status
               +",subject="+subject+",contents="+contents+",exampleCnt="+exampleCnt+",example1="+example1+",example2="+example2
               +",example3="+example3+",example4="+example4+",example5="+example5+",example6="+example6+",example7="+example7
               +",example8="+example8+",example9="+example9+",example10="+example10+",hit1="+hit1+",hit2="+hit2
               +",hit3="+hit3+",hit4="+hit4+",hit5="+hit5+",hit6="+hit6+",hit7="+hit7
               +",hit8="+hit8+",hit9="+hit9+",hit10="+hit10+",regId="+regId+",regDate="+regDate
               +",modId="+modId+",modDate="+modDate;
    }

    }
