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

							<table width="685" height="100%" align="right">
								<tr valign="top">
									<!-- sub title -->
									<td height="34" class="stit_blet"><div class="stit_title"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/title/mtit15_01.gif" alt="타이틀" width="247" height="34"></div></td>
									<!-- // sub title -->
									<!-- history -->
									<td class="stit_history" valign="bottom" align="right" width="368">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									</td>
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
<form name=Frm method=post action="<%=CONTEXTPATH%>/OnlieQuiz.cmd?cmd=onlineQuizList">
<input type=hidden name=pMode    value="<%=PMODE%>">
<input type=hidden name=pRegMode value="W">
<input type=hidden name=pSeqNo   value="">
														<tr>
															<td width="50%">
															</td>
															<td align=right width="50%" height=30>
<script language=javascript>Button5("온라인퀴즈등록", "goAdd()", "");</script>&nbsp;
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
												<td width="">퀴즈</td>
												<td class="s_tablien"></td>
												<td width="60">표시</td>
												<td class="s_tablien"></td>
												<td width="110">등록일</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%	ArrayList list = (ArrayList)model.get("onlineQuizList");
    OnlineQuizDTO onlineQuizDTO = new OnlineQuizDTO();

    for (int i=0 ; i<list.size(); i++) {
    	onlineQuizDTO = (OnlineQuizDTO)list.get(i);	%>
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=(i+1)%></td>
												<td></td>
												<td class="s_tab04"><a href="javascript:doEdit('<%=onlineQuizDTO.getSeqNo() %>')"><%=onlineQuizDTO.getSubject() %></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=onlineQuizDTO.getUseYn() %></td>
												<td></td>
												<td class="s_tab04_cen"><%=DateTimeUtil.getDateType(1, onlineQuizDTO.getRegDate() )%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
<%	}

	if (list.size() <=0 ) {	%>
											<tr>
												<td class="s_tab04_cen" colspan="11">등록된 온라인 퀴즈가 없습니다.</td>
											</tr>
<%	}	%>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
</form>
<!-- form end -->

										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/footer.jsp" %>



