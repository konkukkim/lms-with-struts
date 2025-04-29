/*
 * Created on 2004. 10. 25.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.eventquiz.dto;

/**
 * @author sunny
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EventQuizInfoDTO {
	    protected String systemCode;
	    protected int quizId;
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
	    protected int quizAnswer;
	    protected String regId;
	    protected String regDate;
	    protected String modId;
	    protected String modDate;
	    
	    /** 추가 **/
	    protected int quizCnt;
	    protected String[] example = new String[5];   
	    protected int[] answerCnt = new int[5]; 
	    
	    
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
		 * @return Returns the endDate.
		 */
		public String getEndDate() {
			return endDate;
		}
		/**
		 * @param endDate The endDate to set.
		 */
		public void setEndDate(String endDate) {
			this.endDate = endDate;
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
		 * @return Returns the example1.
		 */
		public String getExample1() {
			return example1;
		}
		/**
		 * @param example1 The example1 to set.
		 */
		public void setExample1(String example1) {
			this.example1 = example1;
		}
		/**
		 * @return Returns the example2.
		 */
		public String getExample2() {
			return example2;
		}
		/**
		 * @param example2 The example2 to set.
		 */
		public void setExample2(String example2) {
			this.example2 = example2;
		}
		/**
		 * @return Returns the example3.
		 */
		public String getExample3() {
			return example3;
		}
		/**
		 * @param example3 The example3 to set.
		 */
		public void setExample3(String example3) {
			this.example3 = example3;
		}
		/**
		 * @return Returns the example4.
		 */
		public String getExample4() {
			return example4;
		}
		/**
		 * @param example4 The example4 to set.
		 */
		public void setExample4(String example4) {
			this.example4 = example4;
		}
		/**
		 * @return Returns the example5.
		 */
		public String getExample5() {
			return example5;
		}
		/**
		 * @param example5 The example5 to set.
		 */
		public void setExample5(String example5) {
			this.example5 = example5;
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
		 * @return Returns the quizAnswer.
		 */
		public int getQuizAnswer() {
			return quizAnswer;
		}
		/**
		 * @param quizAnswer The quizAnswer to set.
		 */
		public void setQuizAnswer(int quizAnswer) {
			this.quizAnswer = quizAnswer;
		}
		/**
		 * @return Returns the quizId.
		 */
		public int getQuizId() {
			return quizId;
		}
		/**
		 * @param quizId The quizId to set.
		 */
		public void setQuizId(int quizId) {
			this.quizId = quizId;
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
		 * @return Returns the startDate.
		 */
		public String getStartDate() {
			return startDate;
		}
		/**
		 * @param startDate The startDate to set.
		 */
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		/**
		 * @return Returns the status.
		 */
		public String getStatus() {
			return status;
		}
		/**
		 * @param status The status to set.
		 */
		public void setStatus(String status) {
			this.status = status;
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
		
		
		/**
		 * @return Returns the quizCnt.
		 */
		public int getQuizCnt() {
			return quizCnt;
		}
		/**
		 * @param quizCnt The quizCnt to set.
		 */
		public void setQuizCnt(int quizCnt) {
			this.quizCnt = quizCnt;
		}
		

		
		/**
		 * @return Returns the answerCnt.
		 */
		public int[] getAnswerCnt() {
			return answerCnt;
		}
		/**
		 * @param answerCnt The answerCnt to set.
		 */
		public void setAnswerCnt(int[] answerCnt) {
			this.answerCnt = answerCnt;
		}
}
