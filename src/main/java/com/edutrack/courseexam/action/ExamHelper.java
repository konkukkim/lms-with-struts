/*
 * Created on 2004. 10. 9.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.courseexam.action;

import javax.servlet.http.HttpServletRequest;

import com.edutrack.courseexam.dto.ExamBkContentsDTO;
import com.edutrack.courseexam.dto.ExamContentsDTO;
import com.edutrack.courseexam.dto.ExamInfoDTO;
import com.edutrack.framework.util.StringUtil;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ExamHelper {
	/**
	 *
	 */
	public ExamHelper() {
		super();
		// TODO Auto-generated constructor stub
	}

    public void getExamParam(HttpServletRequest request, ExamInfoDTO exam){
    	exam.setCourseId(StringUtil.nvl(request.getParameter("pCourseId")));
        exam.setSubject(StringUtil.nvl(request.getParameter("pSubject")));
        exam.setStartDate(StringUtil.nvl(request.getParameter("examTerm1")));
        exam.setEndDate(StringUtil.nvl(request.getParameter("examTerm2")));
        exam.setRunTime(StringUtil.nvl(request.getParameter("pRumTime"),0));
        exam.setResultDate(StringUtil.nvl(request.getParameter("examResult1")));
        exam.setViewStyle(StringUtil.nvl(request.getParameter("pViewStyle")));
        exam.setExamType(StringUtil.nvl(request.getParameter("pExamType")));
        exam.setFlagTime(StringUtil.nvl(request.getParameter("pFlagTime")));
        exam.setComment(StringUtil.nvl(request.getParameter("pComment")));
        exam.setFlagUse("N");

    }

    public void getExamContentsParam(MultipartRequest request, ExamContentsDTO contents){
    	String examType = StringUtil.nvl(request.getParameter("pExamType"));
		String example1 = "";
		String example2 = "";
		String example3 = "";
		String example4 = "";
		String example5 = "";
        String pMultiAnswer ="";
		if(examType.equals("K")){	//객관식일때만 처리
			example1 = StringUtil.nvl(request.getParameter("pExample1"));
			example2 = StringUtil.nvl(request.getParameter("pExample2"));
			example3 = StringUtil.nvl(request.getParameter("pExample3"));
			example4 = StringUtil.nvl(request.getParameter("pExample4"));
			example5 = StringUtil.nvl(request.getParameter("pExample5"));

	        String[] pAnswers = null;
	        pAnswers=request.getParameterValues("pAnswers");
	        if(pAnswers != null){
	        	for(int i =0; i < pAnswers.length; i++){
	        		if(i > 0) pMultiAnswer += "/";
	        		pMultiAnswer += pAnswers[i];
	        	}
	        }
		}

    	contents.setCourseId(StringUtil.nvl(request.getParameter("pCourseId")));
    	contents.setExamId(StringUtil.nvl(request.getParameter("pExamId"),0));
    	contents.setExamOrder(StringUtil.nvl(request.getParameter("pExamOrder"),0));
    	contents.setExamNo(StringUtil.nvl(request.getParameter("pExamNo"),0));
    	contents.setExamType(examType);
        contents.setContents(StringUtil.nvl(request.getParameter("pContents")));
        contents.setExample1(example1);
        contents.setExample2(example2);
        contents.setExample3(example3);
        contents.setExample4(example4);
        contents.setExample5(example5);
        contents.setAnswer(StringUtil.nvl(request.getParameter("pAnswer"+examType)));
        contents.setMultiAnswer(pMultiAnswer);
        contents.setComment(StringUtil.nvl(request.getParameter("pComment")));
        contents.setOldSfile(StringUtil.nvl(request.getParameter("pOldSfile")));
        contents.setOldRfile(StringUtil.nvl(request.getParameter("pOldRfile")));
        contents.setOldFilePath(StringUtil.nvl(request.getParameter("pOldFilePath")));
        contents.setOldFileSize(StringUtil.nvl(request.getParameter("pOldFileSize")));
        contents.setFileCheck(StringUtil.nvl(request.getParameter("pFileDel")));

        //System.out.println(contents.toString());
    }

    public void getExamBkContentsParam(MultipartRequest request, ExamBkContentsDTO contents){
    	String examType = StringUtil.nvl(request.getParameter("pExamType"));
		String example1 = "";
		String example2 = "";
		String example3 = "";
		String example4 = "";
		String example5 = "";
        String pMultiAnswer ="";
		if(examType.equals("K")){	//객관식일때만 처리
			example1 = StringUtil.nvl(request.getParameter("pExample1"));
			example2 = StringUtil.nvl(request.getParameter("pExample2"));
			example3 = StringUtil.nvl(request.getParameter("pExample3"));
			example4 = StringUtil.nvl(request.getParameter("pExample4"));
			example5 = StringUtil.nvl(request.getParameter("pExample5"));

	        String[] pAnswers = null;
	        pAnswers=request.getParameterValues("pAnswers");
	        if(pAnswers != null){
	        	for(int i =0; i < pAnswers.length; i++){
	        		if(i > 0) pMultiAnswer += "/";
	        		pMultiAnswer += pAnswers[i];
	        	}
	        }
		}

    	contents.setCourseId(StringUtil.nvl(request.getParameter("pCourseId")));
    	contents.setExamNo(StringUtil.nvl(request.getParameter("pExamNo"),0));
        contents.setExamType(examType);
        contents.setContents(StringUtil.nvl(request.getParameter("pContents")));
        contents.setExample1(example1);
        contents.setExample2(example2);
        contents.setExample3(example3);
        contents.setExample4(example4);
        contents.setExample5(example5);
        contents.setAnswer(StringUtil.nvl(request.getParameter("pAnswer"+examType)));
        contents.setMultiAnswer(pMultiAnswer);
        contents.setComment(StringUtil.nvl(request.getParameter("pComment")));
        contents.setOldSfile(StringUtil.nvl(request.getParameter("pOldSfile")));
        contents.setOldRfile(StringUtil.nvl(request.getParameter("pOldRfile")));
        contents.setOldFilePath(StringUtil.nvl(request.getParameter("pOldFilePath")));
        contents.setOldFileSize(StringUtil.nvl(request.getParameter("pOldFileSize")));
        contents.setFileCheck(StringUtil.nvl(request.getParameter("pFileDel")));
    }

}
