<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%> 
<%@include file="../common/course_header.jsp" %>
<%
    String pResId = (String)model.get("pResId");
    String pResNo = (String)model.get("pResNo");
%>
<Script Language="javascript">
    function goCancel(){
       var f = document.f;
       var resid = f.pResId.value;
 	   var loc="<%=CONTEXTPATH%>/ResearchResult.cmd?cmd=getResearchResult&pResId="+resid;
	   document.location = loc;       
    }
</Script>

                 </tr>
                <!-- main 시작 -->
                <tr> 
					<td height="410" align="left" valign="top" style="padding:0 10 0 15">

						<table width="100%" height="41" border="0" cellpadding="0" cellspacing="0" bgcolor="#71A076">
              				<tr> 
                				<td width="21"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle_bg1.gif" width="21" height="41"></td>
                				<td class="class_title"><%=CURRINAME%> [<%=CURRIYEAR%>년 <%=CURRITERM%>기]<br></td>
                				<td width="300" align="right"><font color="#CCFF00"><%=USERNAME%>님</font>
									<a href="<%=CONTEXTPATH%>/Message.cmd?cmd=receiveMessagePagingList&MENUNO=0"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_msg.gif" border="0" align="absmiddle"><font color="#CCFF00"><%=UnReadCnt%></font></a>&nbsp; 
                				</td>
                				<td width="17"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle_bg2.gif" width="17" height="41"></td>
              				</tr>
            			</table>
            			
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
<!-- form start -->
<form name="f" method="post" action="<%=CONTEXTPATH%>/ResearchInfo.cmd?cmd=researchInfoWrite" >
<input type=hidden name="pMODE" value="">
<input type=hidden name="pResId" value="<%=pResId%>">
<input type=hidden name="pResNo" value="<%=pResNo%>">	
							
							<tr align="left" valign="middle"> 
			                	<td width="100%" height="45">
			                		<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움체' size="2"><b><%=CommonUtil.getPageTitle(request,"설문결과관리")%></b></font>
			                	</td>
			              	</tr>
							<tr>
								<td align="center" valign="top">
									
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="2" class="b_td01"></td>
										</tr>
										<tr>
											<td height="30" class="b_td02">
												<table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
													<tr align="center" valign="middle">
														<td width="9%" class="b_td02" align="center">번호</td>
														<td width="1%"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti.gif" width="3" height="21"></td>
														<td width="19%" class="b_td02" align="center">설문자명</td>
														<td width="1%"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti.gif" width="3" height="21"></td>
														<td width="70%" class="b_td02" align="center">진술내용</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td height="1" class="b_td03"> </td>
										</tr>
										<tr>
											<td>
												<!-- 리스트 -->
												<table width="100%" border="0" cellspacing="0" cellpadding="0">
<%	int No = 0;
    RowSet list = (RowSet)model.get("answerList"); 
	
	if (list != null) {
	
		while (list.next()) {	%>													
													<tr>
														<td height="25" onMouseOver="this.className='tdcolor2';" onMouseOut="this.className='tdcolor1'">
															<table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td width="9%" align="center"><%=(++No)%></td>
																	<td width="1%"></td>
																	<td width="19%" align="center"><%=StringUtil.nvl(list.getString("user_name"))%></td>
																	<td width="1%"></td>
																	<td width="70%" align="left" style="padding-left:9px;"><%=StringUtil.getHtmlContents(StringUtil.nvl(list.getString("answer")))%></td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td height="1" class="b_td03" ></td>
													</tr>
<%		}
		
		list.close();
	}
	
	if (No < 1) {	%>    
								             		<tr> 
								                		<td height="25" align="center">진술내용이 존재하지 않습니다.</td>
								              		</tr>
								              		<tr>
														<td height="1" class="b_td03" ></td>
													</tr>
<%	}	%>
												</table>
												<!-- 리스트 -->
											</td>
										</tr>
									</table>
							
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="30" align="right">
												<a href="javascript:goCancel();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bttn_result.gif" width="59" height="19" border="0"></a>
											</td>
										</tr>
										<tr>
											<td height="20" align="center" valign="top">
												<table height="15" border="0" cellpadding="0" cellspacing="0">
													<tr>
														<td><td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
</form>
<!-- form end -->
						</table>                
					</td>
				</tr>

<%@include file="../common/course_footer.jsp" %>
