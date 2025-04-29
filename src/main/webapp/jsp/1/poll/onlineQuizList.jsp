<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@ page import ="com.edutrack.onlinequiz.dto.OnlineQuizDTO"%> 
<%@ page import ="com.edutrack.common.CommonUtil"%> 
<%@include file="../common/header.jsp" %>

<Script Language="javascript">

    
/** desc : 온라인 퀴즈목록을 조회한다
 ** doSearch() 
 */
function doSearch()
{
    var f=document.Frm ;

    f.action = "<%=CONTEXTPATH%>/OnlineQuiz.cmd?cmd=onlineQuizList";
    f.submit();
    
}

/** desc : 온라인 퀴즈를 입력한다
 ** doInsert() 
 */
function goAdd()
{
    var f=document.Frm ;

    if(!validate(f)) return;
    
    if(f.pRegMode.value =="W")
        f.pSeqNo.value = "" ;

        
    f.action = "<%=CONTEXTPATH%>/OnlineQuiz.cmd?cmd=onlineQuizWrite";
    f.submit();
    
}


/** desc : 온라인 퀴즈를 수정한다
 ** doEdit() 
 */
function doEdit(seqNo)
{
    var f=document.Frm ;

    f.pSeqNo.value = seqNo ;
    f.pRegMode.value = 'E' ;

    f.action = "<%=CONTEXTPATH%>/OnlineQuiz.cmd?cmd=onlineQuizWrite";
    f.submit();
    
}


</Script>

						<table width="100%" height="35" border="0" cellpadding="0" cellspacing="0" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/03_cen_titlebg.gif">
							<tr>
								<td align="left" valign="middle" style="padding:0 0 0 8">
									<font size='3' face='돋움체'><b>온라인퀴즈</b></font>
								</td>
								<td width="50%" align="right" valign="middle" style="padding:0 8 0 0"></td>
							</tr>
						</table>
							
						<br>
							
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<!-- form start -->
						<form name=Frm method=post action="<%=CONTEXTPATH%>/OnlieQuiz.cmd?cmd=onlineQuizList">
                        <input type=hidden name=pMode    value="<%=PMODE%>">
                        <input type=hidden name=pRegMode value="W">
                        <input type=hidden name=pSeqNo   value="">	
							<tr>
								<td align="center" valign="top">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td align="center" valign="top">
												<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%" height="28">
													<tr>
														<td width="50%" align="left">&nbsp;</td>
														<td width="50%" align="right">
															<a href="javascript:goAdd()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/quiz_write.gif" width="86" height="19" border="0"></a>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
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
														<td width="54%" class="b_td02" align="center">퀴즈</td>
														<td width="1%"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti.gif" width="3" height="21"></td>
														<td width="14%" class="b_td02" align="center">표시</td>
														<td width="1%"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti.gif" width="3" height="21"></td>
														<td width="15%" class="b_td02" align="center">등록일</td>
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
<%	ArrayList list = (ArrayList)model.get("onlineQuizList");
    OnlineQuizDTO onlineQuizDTO = new OnlineQuizDTO();
    
    for (int i=0 ; i<list.size(); i++) {
    	onlineQuizDTO = (OnlineQuizDTO)list.get(i);	%>
													<tr>
														<td height="25" onMouseOver="this.className='tdcolor2';" onMouseOut="this.className='tdcolor1'">
															<table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
																<tr>
																	<td width="9%" align="center"><%=(i+1)%></td>
																	<td width="1%"></td>
																	<td width="54%" align="left" style="padding-left:9px;"><a href="javascript:doEdit('<%=onlineQuizDTO.getSeqNo() %>')"><%=onlineQuizDTO.getSubject() %></a></td>
																	<td width="1%"></td>
																	<td width="14%" align="center"><%=onlineQuizDTO.getUseYn() %></td>
																	<td width="1%"></td>
																	<td width="15%" align="center"><%=DateTimeUtil.getDateType(1, onlineQuizDTO.getRegDate() )%></td>
																</tr>
															</table>
														</td>
													</tr>
													<tr>
														<td height="1" class="b_td03" ></td>
													</tr>
<%	}
	
	if (list.size() <=0 ) {	%>
				                                	<tr> 
				                                    	<td align="center" height="25">등록된 온라인 퀴즈가 없습니다.</td>
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
												&nbsp;
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

</form>
<!-- form end -->
        
         
<%@include file="../common/footer.jsp" %>