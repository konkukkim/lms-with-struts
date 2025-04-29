<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@include file="../common/header.jsp" %>  
<%
	String  pSec		= (String)model.get("pSec");
    String 	pMode 		= (String)model.get("pMode");
    String 	pBbsId 		= (String)model.get("pBbsId");
    String 	pBbsType 	= (String)model.get("pBbsType");
    int     pNewTime 	= Integer.parseInt((String)model.get("pNewTime"));
    int     pHotChk 	= Integer.parseInt((String)model.get("pHotChk"));
	int		curPage		= Integer.parseInt((String)model.get("curPage"));

    String 	userType    = UserBroker.getUserType(request);
    String 	bbsHot      = "";
    String 	bbsNew	    = "";
    String 	bbsTitleImg	= "";  // MyPage 에서만 사용함
    String  img_path    =  CONTEXTPATH+"/img/"+SYSTEMCODE ;

%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/BbsContentsListWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/bbs/bbsContentsList.js"></script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<form name="ff1">
<input type="hidden" name="pHotChk"	value="<%=pHotChk%>">
<input type="hidden" name="pAddDate" value="<%=DateTimeUtil.getAddDate(-1)%>">
</form>

<form name="f" method="post" action="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList" >
<input type="hidden" name="curPage"	value="<%=curPage%>">
<input type="hidden" name="pBbsId" 	value="<%=pBbsId%>">
<input type="hidden" name="pSec"	value="<%=pSec%>">
<input type="hidden" name="pMode" 	value="<%=pMode%>">
<input type="hidden" name="MENUNO" 	value="<%=StringUtil.nvl(model.get("MENUNO"))%>">
<input type="hidden" name="MainMenu" 	value="<%=StringUtil.nvl(model.get("MainMenu"))%>">
											<tr class="s_tab01">
												<td colspan="9"></td>
											</tr>
											<tr class="s_tab02">
												<td width="54">번호</td>
												<td class="s_tablien"></td>
												<td width="360">제목</td>
												<td class="s_tablien"></td>
												<td width="94">등록자</td>
												<td class="s_tablien"></td>
												<td width="94">등록일</td>
												<td class="s_tablien"></td>
												<td width="65">조회수</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="9"></td>
											</tr>
											<tr>
												<td colspan="13">
													<!-- 리스트 -->
														<div id="bbsList" style="width:100%;display:none"></div>
													<!-- 리스트 -->
												</td>
											</tr>

											<tr class="s_tab05">
												<td colspan="9"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="9" height="30" align="right">
<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %>
	<%
		int bbsId	=	StringUtil.nvl(pBbsId, 0);
		if(USERID.equals("") && (pBbsId.equals("9") || (bbsId >= 10 && bbsId <= 16)) ) {
	%>
	<script language=javascript>Button3("글쓰기", "goAdd()", "");</script>
	<%
		} else if((pBbsType.equals("notice") && userType.equals("M")) || (!pBbsType.equals("notice") && (userType.equals("M") || userType.equals("P") || userType.equals("J") || userType.equals("S")))) {
	%>
	<script language=javascript>Button3("글쓰기", "goAdd()", "");</script>
	<%
		}
	%>
<%	}	%>

												</td>
											</tr>
											<tr>
												<td colspan="9" align=center>
													<table valign=top height="25">
														<tr>
															<td><!-- 페이징 -->
																<div id="getPagging" style="width:100%;display:none"></div></td>
														</tr>
													</table>
													<table>
														<tr>
															<td align=middle height=30>
																<select name=pSearchKey>
																	<option value=subject selected>제목</option>
																	<option value=keyword>내용</option>
																	<option value=reg_name>작성자</option>
																</select>
																<input maxlength=30 size=22 name=pKeyWord>
																<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle onClick="autoReload()" style="cursor:hand;">
															</td>
														</tr>
													</table>
												</td>
											</tr>
</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						

<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>');
</script>
<%@include file="../common/footer.jsp" %>