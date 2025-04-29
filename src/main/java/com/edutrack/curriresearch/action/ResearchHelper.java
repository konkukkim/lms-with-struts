/*
 * Created on 2004. 10. 25.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.curriresearch.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.edutrack.curriresearch.dto.CurriResAnswerDTO;
import com.edutrack.curriresearch.dto.CurriResContentsDTO;
import com.edutrack.curriresearch.dto.CurriResInfoDTO;
import com.edutrack.curriresearch.dto.ResBkContentsDTO;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ResearchHelper {

	/**
	 * 
	 */
	public ResearchHelper() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param request
	 * @param resInfo
	 */
	public void getResearchParam(HttpServletRequest request, CurriResInfoDTO resinfo) {
        resinfo.setSubject(StringUtil.nvl(request.getParameter("pSubject")));
        resinfo.setContents(StringUtil.nvl(request.getParameter("pContents")));
        resinfo.setStartDate(StringUtil.nvl(request.getParameter("resTerm1")));
        resinfo.setEndDate(StringUtil.nvl(request.getParameter("resTerm2")));
        resinfo.setOpenYn(StringUtil.nvl(request.getParameter("pOpenYn")));
	}
	
	public void getResearchContentsParam(HttpServletRequest request, CurriResContentsDTO contents){
    	contents.setResId(StringUtil.nvl(request.getParameter("pResId"),0));
    	contents.setResNo(StringUtil.nvl(request.getParameter("pResNo"),0));
        contents.setContentsType(StringUtil.nvl(request.getParameter("pContentsType")));
        contents.setContents(StringUtil.nvl(request.getParameter("pContents")));
        contents.setExample1(StringUtil.nvl(request.getParameter("pExample1")));
        contents.setExample2(StringUtil.nvl(request.getParameter("pExample2")));
        contents.setExample3(StringUtil.nvl(request.getParameter("pExample3")));
        contents.setExample4(StringUtil.nvl(request.getParameter("pExample4")));
        contents.setExample5(StringUtil.nvl(request.getParameter("pExample5")));
        contents.setExample6(StringUtil.nvl(request.getParameter("pExample6")));
        contents.setExample7(StringUtil.nvl(request.getParameter("pExample7")));
        contents.setExample8(StringUtil.nvl(request.getParameter("pExample8")));
        contents.setExample9(StringUtil.nvl(request.getParameter("pExample9")));
        contents.setExample10(StringUtil.nvl(request.getParameter("pExample10")));
        contents.setExampleNum(StringUtil.nvl(request.getParameter("pExampleNum")));
    }

	public void getResearchContentsParam(HttpServletRequest request, ResBkContentsDTO contents){
    	contents.setResId(StringUtil.nvl(request.getParameter("pResId"),0));
    	contents.setResNo(StringUtil.nvl(request.getParameter("pResNo"),0));
        contents.setContentsType(StringUtil.nvl(request.getParameter("pContentsType")));
        contents.setContents(StringUtil.nvl(request.getParameter("pContents")));
        contents.setExample1(StringUtil.nvl(request.getParameter("pExample1")));
        contents.setExample2(StringUtil.nvl(request.getParameter("pExample2")));
        contents.setExample3(StringUtil.nvl(request.getParameter("pExample3")));
        contents.setExample4(StringUtil.nvl(request.getParameter("pExample4")));
        contents.setExample5(StringUtil.nvl(request.getParameter("pExample5")));
        contents.setExample6(StringUtil.nvl(request.getParameter("pExample6")));
        contents.setExample7(StringUtil.nvl(request.getParameter("pExample7")));
        contents.setExample8(StringUtil.nvl(request.getParameter("pExample8")));
        contents.setExample9(StringUtil.nvl(request.getParameter("pExample9")));
        contents.setExample10(StringUtil.nvl(request.getParameter("pExample10")));
        contents.setExampleNum(StringUtil.nvl(request.getParameter("pExampleNum")));
    }

	public void getResearchAnswerParam(HttpServletRequest request, ArrayList answerlist){
        int maxNum = StringUtil.nvl(request.getParameter("maxNum"),0); 
		String contentsInfo = "";
		CurriResAnswerDTO contentsDto = null;
        String[] info = null;
        String[] example = null;
        String[] answerExample = null;
        String resNo = "";
        String answer = "";
        String k = "";
		for(int i =1; i <= maxNum; i++){
			contentsInfo = StringUtil.nvl(request.getParameter("pContentsInfo"+i));
			
			if(!contentsInfo.equals("")){
				contentsDto = new CurriResAnswerDTO();
				answerExample = new String[]{"X","X","X","X","X","X","X","X","X","X","X"};
				info = StringUtil.split(contentsInfo, "/");
				if(info != null){
					contentsDto.setResNo(Integer.parseInt(info[0]));
					contentsDto.setContentsType(info[1]);
					if(info[1].equals("K") || info[1].equals("S")){
			       		example = request.getParameterValues("pExample"+info[0]);
			       		if(example != null){
			       			for(int j =0; j < example.length; j++){
			       				answerExample[Integer.parseInt(example[j])] = "O";  
			       			}
			       		}
					}else {
						contentsDto.setAnswer(StringUtil.nvl(request.getParameter("pAnswer"+i)));
					}
				}
			 
			 contentsDto.setExample(answerExample);	
			 answerlist.add(contentsDto);	
		   }
	   }	
	}	
}
