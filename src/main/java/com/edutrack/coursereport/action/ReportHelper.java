/*
 * Created on 2004. 10. 9.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.coursereport.action;

import javax.servlet.http.HttpServletRequest;

import com.edutrack.coursereport.dto.ReportBankContentsDTO;
import com.edutrack.coursereport.dto.ReportInfoDTO;
import com.edutrack.coursereport.dto.ReportSendDTO;
import com.edutrack.coursereport.dto.ReportSubInfoDTO;
import com.edutrack.framework.util.StringUtil;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportHelper {
	/**
	 * 
	 */
	public ReportHelper() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
    public void getReportParam(MultipartRequest request, ReportInfoDTO report){    	
    	report.setCourseId(StringUtil.nvl(request.getParameter("pCourseId")));
    	report.setReportType1(StringUtil.nvl(request.getParameter("pReportType1")));
    	report.setReportType2(StringUtil.nvl(request.getParameter("pReportType2")));
        report.setReportSubject(StringUtil.nvl(request.getParameter("pSubject")));
        report.setReportStartDate(StringUtil.nvl(request.getParameter("reportTerm1")));
        report.setReportEndDate(StringUtil.nvl(request.getParameter("reportTerm2")));
        report.setReportExtendDate(StringUtil.nvl(request.getParameter("reportResult1")));
        report.setReportScoreYn(StringUtil.nvl(request.getParameter("pScoreYn")));
        report.setReportOpenYn(StringUtil.nvl(request.getParameter("pOpenYn")));	
        report.setReportRegistYn(StringUtil.nvl(request.getParameter("pRegistYn")));
        report.setAutoBankYn(StringUtil.nvl(request.getParameter("pAutoBankYn")));
        report.setReportBankId(StringUtil.nvl(request.getParameter("pBankId"),0));
        report.setReportSendDelYn(StringUtil.nvl(request.getParameter("pSendDelYn")));

    }
    
    public void getReportBankContentsParam(MultipartRequest request, ReportBankContentsDTO contents){
        contents.setOldSfile(StringUtil.nvl(request.getParameter("pOldSfile")));
        contents.setOldRfile(StringUtil.nvl(request.getParameter("pOldRfile")));
        contents.setOldFilePath(StringUtil.nvl(request.getParameter("pOldFilePath")));
        contents.setOldFileSize(StringUtil.nvl(request.getParameter("pOldFileSize")));
        contents.setFileCheck(StringUtil.nvl(request.getParameter("pFileDel")));
    }
    
    public void getReportSubInfoParam(MultipartRequest request, ReportSubInfoDTO reportSubInfoDto){
    	reportSubInfoDto.setOldSfile(StringUtil.nvl(request.getParameter("pOldSfile")));
    	reportSubInfoDto.setOldRfile(StringUtil.nvl(request.getParameter("pOldRfile")));
    	reportSubInfoDto.setOldFilePath(StringUtil.nvl(request.getParameter("pOldFilePath")));
    	reportSubInfoDto.setOldFileSize(StringUtil.nvl(request.getParameter("pOldFileSize")));
    	reportSubInfoDto.setFileCheck(StringUtil.nvl(request.getParameter("pFileDel")));
    }
    
    public void getReportSendParam(MultipartRequest request, ReportSendDTO reportSendDto){
    	reportSendDto.setOldSfile1(StringUtil.nvl(request.getParameter("pOldSfile1")));
    	reportSendDto.setOldRfile1(StringUtil.nvl(request.getParameter("pOldRfile1")));
    	reportSendDto.setOldFilePath1(StringUtil.nvl(request.getParameter("pOldFilePath1")));
    	reportSendDto.setOldFileSize1(StringUtil.nvl(request.getParameter("pOldFileSize1")));
    	reportSendDto.setFileCheck1(StringUtil.nvl(request.getParameter("pFileDel1")));
    	
    	reportSendDto.setOldSfile2(StringUtil.nvl(request.getParameter("pOldSfile2")));
    	reportSendDto.setOldRfile2(StringUtil.nvl(request.getParameter("pOldRfile2")));
    	reportSendDto.setOldFilePath2(StringUtil.nvl(request.getParameter("pOldFilePath2")));
    	reportSendDto.setOldFileSize2(StringUtil.nvl(request.getParameter("pOldFileSize2")));
    	reportSendDto.setFileCheck2(StringUtil.nvl(request.getParameter("pFileDel2")));
    }
    
    public void getReportProfSendParam(MultipartRequest request, ReportSendDTO reportSendDto){
    	reportSendDto.setOldSfile3(StringUtil.nvl(request.getParameter("pOldSfile3")));
    	reportSendDto.setOldRfile3(StringUtil.nvl(request.getParameter("pOldRfile3")));
    	reportSendDto.setOldFilePath3(StringUtil.nvl(request.getParameter("pOldFilePath3")));
    	reportSendDto.setOldFileSize3(StringUtil.nvl(request.getParameter("pOldFileSize3")));
    	reportSendDto.setFileCheck3(StringUtil.nvl(request.getParameter("pFileDel3")));
    }

}
