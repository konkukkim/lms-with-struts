<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%> 
<%@include file="../common/course_header.jsp" %>
<%
    String pCourseId = (String)model.get("pCourseId");
    String pReportId = (String)model.get("pReportId");
    String pReportSubject = (String)model.get("pReportSubject");
    
    long curDate = Long.parseLong(CommonUtil.getCurrentDate());
%>
<script language="javascript">
    
  //과제 상세 화면
  function goOtherReoprtShow(userid,username){
  	 var f = document.f;	   
  	 f.pUserId.value = userid;
  	 f.pUserName.value = username;
  	 f.action = "<%=CONTEXTPATH%>/ReportResult.cmd?cmd=reportOtherUserShow";
	 f.method = "post";
	 f.submit();

  }
   
   //파일 다운로드
	 function fileDownload(rfilename, sfilename, filepath, filesize){
		var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;        
		hiddenFrame.document.location = loc;
	 }
   
   //취소
   function goCancel(){
       var f = document.f;
       
       var courseid = f.pCourseId.value;

       var loc="/ReportAdmin.cmd?cmd=reportStList&pCourseId="+courseid;
       document.location = loc;
   }
    
 
</script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"전체제출리스트")%></b></font></td>
									<!-- // classroom title -->
									<!-- history -->
									<td class="c_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									<!-- // history -->
								</tr>
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
									<!-- 내용 -->
										<!-- 게시판 리스트 시작 --> 
										<table width="670" align="center">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
<!-- form start -->	
<form name="f" method="post" action="<%=CONTEXTPATH%>/ReportAnswer.cmd?cmd=reportRetestEdit" >
<input type=hidden name="pCourseId" value="<%=pCourseId%>">
<input type=hidden name="pReportId"   value="<%=pReportId%>">
<input type=hidden name="pReportSubject"   value="<%=pReportSubject%>">
<input type=hidden name="pUserId"   value="">
<input type=hidden name="pUserName"   value="">
<input type=hidden name="checkYn"   value="N">

														<tr>
															<td width="50%"><img
																src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" 
																align="absmiddle" border="0"><b>과제명 : <%=pReportSubject%></b>
															</td>
															<td align=right width="50%" height=30>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="70">번호</td>
												<td class="s_tablien"></td>
												<td width="125">성명</td>
												<td class="s_tablien"></td>
												<td width="125">학습자 ID</td>
												<td class="s_tablien"></td>
												<td width="100">제출여부</td>
												<td class="s_tablien"></td>
												<td width="150">제출파일</td>
												<td class="s_tablien"></td>
												<td width="100">제출일자</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%	int No = 0;
	String apply = "";
	String resultYn = "N";
	String userId = "";
	String rfileName1 = "";
	String sfileName1 = "";
	String filePath1 = "";
	String fileSize1 = "";
	String rfileName2 = "";
	String sfileName2 = "";
	String filePath2 = "";
	String fileSize2 = "";
	
	RowSet list = (RowSet)model.get("reportSendList"); 
	
	if (list != null) {  
		while(list.next()){
		
		rfileName1 	= StringUtil.nvl(list.getString("rfile_name1"));
		sfileName1 	= StringUtil.nvl(list.getString("sfile_name1"));
		filePath1 	= StringUtil.nvl(list.getString("file_path1"));
		fileSize1 	= StringUtil.nvl(list.getString("file_size1"));
		rfileName2 	= StringUtil.nvl(list.getString("rfile_name2"));
		sfileName2	= StringUtil.nvl(list.getString("sfile_name2"));
		filePath2	= StringUtil.nvl(list.getString("file_path2"));
		fileSize2	= StringUtil.nvl(list.getString("file_size2"));
%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=++No%></td>
												<td></td>
												<td class="s_tab04_cen">
													<a href="javascript:goOtherReoprtShow('<%=StringUtil.nvl(list.getString("user_id"))%>','<%=StringUtil.nvl(list.getString("user_name"))%>')">
														<%=StringUtil.nvl(list.getString("user_name"))%>
													</a>
												</td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(list.getString("user_id"))%></td>
												<td></td>
<%			// 제출여부 판단 
			if (StringUtil.nvl(list.getString("checkYn")).equals("")) {
				apply = "<font color=\"red\">미제출</font>";

			} else {
				apply = "<font color=\"blue\">제출</font>";
			}	
%>
												<td class="s_tab04_cen"><%=apply%></td>
												<td></td>
												<td class="s_tab04_cen">
											<% if(!rfileName1.equals("")) {%>
													<a href="javascript:fileDownload('<%=rfileName1%>','<%=sfileName1%>','<%=filePath1%>','<%=fileSize1%>');">
														<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/ico_file.gif" align="absmiddle" border="0">
													</a>
											<% } %>
											<% if(!rfileName2.equals("")) {%>
													&nbsp;
													<a href="javascript:fileDownload('<%=rfileName2%>','<%=sfileName2%>','<%=filePath2%>','<%=fileSize2%>');">
														<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/ico_file.gif" align="absmiddle" border="0">
													</a>
											<% } %>
												</td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("stu_mod_date")))%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%		}
		
		list.close();
	}
	
	if (No < 1) {	%>
											<tr>
												<td class="s_tab04_cen" colspan="11">제출자가 존재하지 않습니다.</td>
											</tr>
<%	}	%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="11" height="30" align="right">
<script language=javascript>Button3("목록", "goCancel()", "");</script>
												</td>
											</tr>
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->

<%@include file="../common/course_footer.jsp" %>