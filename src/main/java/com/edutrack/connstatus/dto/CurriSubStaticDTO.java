/**
 *
 */
package com.edutrack.connstatus.dto;


public class CurriSubStaticDTO {

	protected	String	systemCode		=	"";
	protected	String	curriCode		=	"";
	protected	int		curriYear		=	0;
	protected	int		curriTerm		=	0;
	protected	String	curriName		=	"";
	protected	String	pareCode1		=	"";
	protected	String	pareCode2		=	"";
	protected	String	cateCode		=	"";
	protected	String	userId			=	"";
	protected	String	userName		=	"";
	protected	String	enrollStatus	=	"";
	protected	int		contentsCnt		=	0;
	protected	String	juminNo			=	"";

	protected	int		completeManCnt		=	0;
	protected	int		completeWomenCnt	=	0;
	protected	int		noManCnt			=	0;
	protected	int		noWomenCnt			=	0;

	protected	int		completeTotalCnt	=	0;
	protected	int		noTotalCnt			=	0;
	protected	int		manTotalCnt			=	0;
	protected	int		womenTotalCnt		=	0;
	protected	int		totalCnt			=	0;

	protected	String	area		=	"";				//지역
	protected	int		age			=	0;				//연령대
	protected	String	codeName	=	"";

	protected	String	profName			=	"";
	protected	int		studentCnt			=	0;
	protected	int		qanCnt				=	0;
	protected	int		noticeCnt			=	0;
	protected	int		reqCnt				=	0;		//학생면담요청글
	protected	int		resCnt				=	0;		//면담답변글
	protected	int		forumCnt			=	0;
	protected	int		forumContentsCnt	=	0;
	protected	int		reportCnt			=	0;
	protected	int		examCnt				=	0;
	protected	String	resultYn			=	"";		//평가여부
	protected	double	scoreTotal			=	0;		//총점
	protected	double	courseRate			=	0;		//진도율
	protected	String	result				=	"";

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return the courseRate
	 */
	public double getCourseRate() {
		return courseRate;
	}
	/**
	 * @param courseRate the courseRate to set
	 */
	public void setCourseRate(double courseRate) {
		this.courseRate = courseRate;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the reqCnt
	 */
	public int getReqCnt() {
		return reqCnt;
	}
	/**
	 * @param reqCnt the reqCnt to set
	 */
	public void setReqCnt(int reqCnt) {
		this.reqCnt = reqCnt;
	}
	/**
	 * @return the resCnt
	 */
	public int getResCnt() {
		return resCnt;
	}
	/**
	 * @param resCnt the resCnt to set
	 */
	public void setResCnt(int resCnt) {
		this.resCnt = resCnt;
	}
	/**
	 * @return the forumContentsCnt
	 */
	public int getForumContentsCnt() {
		return forumContentsCnt;
	}
	/**
	 * @param forumContentsCnt the forumContentsCnt to set
	 */
	public void setForumContentsCnt(int forumContentsCnt) {
		this.forumContentsCnt = forumContentsCnt;
	}
	/**
	 * @return the examCnt
	 */
	public int getExamCnt() {
		return examCnt;
	}
	/**
	 * @param examCnt the examCnt to set
	 */
	public void setExamCnt(int examCnt) {
		this.examCnt = examCnt;
	}
	/**
	 * @return the forumCnt
	 */
	public int getForumCnt() {
		return forumCnt;
	}
	/**
	 * @param forumCnt the forumCnt to set
	 */
	public void setForumCnt(int forumCnt) {
		this.forumCnt = forumCnt;
	}
	/**
	 * @return the noticeCnt
	 */
	public int getNoticeCnt() {
		return noticeCnt;
	}
	/**
	 * @param noticeCnt the noticeCnt to set
	 */
	public void setNoticeCnt(int noticeCnt) {
		this.noticeCnt = noticeCnt;
	}
	/**
	 * @return the profName
	 */
	public String getProfName() {
		return profName;
	}
	/**
	 * @param profName the profName to set
	 */
	public void setProfName(String profName) {
		this.profName = profName;
	}
	/**
	 * @return the qanCnt
	 */
	public int getQanCnt() {
		return qanCnt;
	}
	/**
	 * @param qanCnt the qanCnt to set
	 */
	public void setQanCnt(int qanCnt) {
		this.qanCnt = qanCnt;
	}
	/**
	 * @return the reportCnt
	 */
	public int getReportCnt() {
		return reportCnt;
	}
	/**
	 * @param reportCnt the reportCnt to set
	 */
	public void setReportCnt(int reportCnt) {
		this.reportCnt = reportCnt;
	}
	/**
	 * @return the resultYn
	 */
	public String getResultYn() {
		return resultYn;
	}
	/**
	 * @param resultYn the resultYn to set
	 */
	public void setResultYn(String resultYn) {
		this.resultYn = resultYn;
	}
	/**
	 * @return the studentCnt
	 */
	public int getStudentCnt() {
		return studentCnt;
	}
	/**
	 * @param studentCnt the studentCnt to set
	 */
	public void setStudentCnt(int studentCnt) {
		this.studentCnt = studentCnt;
	}




	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * @return the codeName
	 */
	public String getCodeName() {
		return codeName;
	}
	/**
	 * @param codeName the codeName to set
	 */
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	/**
	 * @return the cateCode
	 */
	public String getCateCode() {
		return cateCode;
	}
	/**
	 * @param cateCode the cateCode to set
	 */
	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}
	/**
	 * @return the completeManCnt
	 */
	public int getCompleteManCnt() {
		return completeManCnt;
	}
	/**
	 * @param completeManCnt the completeManCnt to set
	 */
	public void setCompleteManCnt(int completeManCnt) {
		this.completeManCnt = completeManCnt;
	}
	/**
	 * @return the completeTotalCnt
	 */
	public int getCompleteTotalCnt() {
		return completeTotalCnt;
	}
	/**
	 * @param completeTotalCnt the completeTotalCnt to set
	 */
	public void setCompleteTotalCnt(int completeTotalCnt) {
		this.completeTotalCnt = completeTotalCnt;
	}
	/**
	 * @return the completeWomenCnt
	 */
	public int getCompleteWomenCnt() {
		return completeWomenCnt;
	}
	/**
	 * @param completeWomenCnt the completeWomenCnt to set
	 */
	public void setCompleteWomenCnt(int completeWomenCnt) {
		this.completeWomenCnt = completeWomenCnt;
	}
	/**
	 * @return the contentsCnt
	 */
	public int getContentsCnt() {
		return contentsCnt;
	}
	/**
	 * @param contentsCnt the contentsCnt to set
	 */
	public void setContentsCnt(int contentsCnt) {
		this.contentsCnt = contentsCnt;
	}
	/**
	 * @return the curriCode
	 */
	public String getCurriCode() {
		return curriCode;
	}
	/**
	 * @param curriCode the curriCode to set
	 */
	public void setCurriCode(String curriCode) {
		this.curriCode = curriCode;
	}
	/**
	 * @return the curriName
	 */
	public String getCurriName() {
		return curriName;
	}
	/**
	 * @param curriName the curriName to set
	 */
	public void setCurriName(String curriName) {
		this.curriName = curriName;
	}
	/**
	 * @return the curriTerm
	 */
	public int getCurriTerm() {
		return curriTerm;
	}
	/**
	 * @param curriTerm the curriTerm to set
	 */
	public void setCurriTerm(int curriTerm) {
		this.curriTerm = curriTerm;
	}
	/**
	 * @return the curriYear
	 */
	public int getCurriYear() {
		return curriYear;
	}
	/**
	 * @param curriYear the curriYear to set
	 */
	public void setCurriYear(int curriYear) {
		this.curriYear = curriYear;
	}
	/**
	 * @return the enrollStatus
	 */
	public String getEnrollStatus() {
		return enrollStatus;
	}
	/**
	 * @param enrollStatus the enrollStatus to set
	 */
	public void setEnrollStatus(String enrollStatus) {
		this.enrollStatus = enrollStatus;
	}
	/**
	 * @return the juminNo
	 */
	public String getJuminNo() {
		return juminNo;
	}
	/**
	 * @param juminNo the juminNo to set
	 */
	public void setJuminNo(String juminNo) {
		this.juminNo = juminNo;
	}
	/**
	 * @return the manTotalCnt
	 */
	public int getManTotalCnt() {
		return manTotalCnt;
	}
	/**
	 * @param manTotalCnt the manTotalCnt to set
	 */
	public void setManTotalCnt(int manTotalCnt) {
		this.manTotalCnt = manTotalCnt;
	}
	/**
	 * @return the noManCnt
	 */
	public int getNoManCnt() {
		return noManCnt;
	}
	/**
	 * @param noManCnt the noManCnt to set
	 */
	public void setNoManCnt(int noManCnt) {
		this.noManCnt = noManCnt;
	}
	/**
	 * @return the noTotalCnt
	 */
	public int getNoTotalCnt() {
		return noTotalCnt;
	}
	/**
	 * @param noTotalCnt the noTotalCnt to set
	 */
	public void setNoTotalCnt(int noTotalCnt) {
		this.noTotalCnt = noTotalCnt;
	}
	/**
	 * @return the noWomenCnt
	 */
	public int getNoWomenCnt() {
		return noWomenCnt;
	}
	/**
	 * @param noWomenCnt the noWomenCnt to set
	 */
	public void setNoWomenCnt(int noWomenCnt) {
		this.noWomenCnt = noWomenCnt;
	}
	/**
	 * @return the pareCode1
	 */
	public String getPareCode1() {
		return pareCode1;
	}
	/**
	 * @param pareCode1 the pareCode1 to set
	 */
	public void setPareCode1(String pareCode1) {
		this.pareCode1 = pareCode1;
	}
	/**
	 * @return the pareCode2
	 */
	public String getPareCode2() {
		return pareCode2;
	}
	/**
	 * @param pareCode2 the pareCode2 to set
	 */
	public void setPareCode2(String pareCode2) {
		this.pareCode2 = pareCode2;
	}
	/**
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}
	/**
	 * @param systemCode the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	/**
	 * @return the totalCnt
	 */
	public int getTotalCnt() {
		return totalCnt;
	}
	/**
	 * @param totalCnt the totalCnt to set
	 */
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the womenTotalCnt
	 */
	public int getWomenTotalCnt() {
		return womenTotalCnt;
	}
	/**
	 * @param womenTotalCnt the womenTotalCnt to set
	 */
	public void setWomenTotalCnt(int womenTotalCnt) {
		this.womenTotalCnt = womenTotalCnt;
	}
	/**
	 * @return the scoreTotal
	 */
	public double getScoreTotal() {
		return scoreTotal;
	}
	/**
	 * @param scoreTotal the scoreTotal to set
	 */
	public void setScoreTotal(double scoreTotal) {
		this.scoreTotal = scoreTotal;
	}







}
