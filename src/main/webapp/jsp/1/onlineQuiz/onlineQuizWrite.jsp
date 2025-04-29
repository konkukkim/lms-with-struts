<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@ page import ="com.edutrack.onlinequiz.dto.OnlineQuizDTO"%>
<%@ page import ="com.edutrack.currisub.dto.CurriSubDTO"%>
<%@ page import ="com.edutrack.common.CommonUtil"%>
<%@include file="../common/header.jsp" %>
<%

/**  값을 받아온다
 */
OnlineQuizDTO onlineQuizDTO = (OnlineQuizDTO) model.get("onlineQuizInfo");


/** variable declare
 */
//String sSystemCode      = "";
String sSeqNo           = "";
String sSubject         = "";
String sQuizType        = "S";
String[] sExample       = new String[6];
String sAnswer          = "N";
String sQuizComment     = "";
String sQuizCurriUrl    = "";
String sQuizLinkUrl     = "";
String sUseYn           = "N";
String sRegMode         = "W";

if(onlineQuizDTO!=null)
{
  //sSystemCode      =  StringUtil.nvl( onlineQuizDTO.getSystemCode  ()  , "")  ;
    sSeqNo           =  StringUtil.nvl( onlineQuizDTO.getSeqNo       ()  , "")  ;
    sSubject         =  StringUtil.nvl( onlineQuizDTO.getSubject     ()  , "")  ;
    sQuizType        =  StringUtil.nvl( onlineQuizDTO.getQuizType    ()  , "")  ;
    sExample[1]      =  StringUtil.nvl( onlineQuizDTO.getExample1    ()  , "")  ;
    sExample[2]      =  StringUtil.nvl( onlineQuizDTO.getExample2    ()  , "")  ;
    sExample[3]      =  StringUtil.nvl( onlineQuizDTO.getExample3    ()  , "")  ;
    sExample[4]      =  StringUtil.nvl( onlineQuizDTO.getExample4    ()  , "")  ;
    sExample[5]      =  StringUtil.nvl( onlineQuizDTO.getExample5    ()  , "")  ;
    sAnswer          =  StringUtil.nvl( onlineQuizDTO.getAnswer      ()  , "")  ;
    sQuizComment     =  StringUtil.nvl( onlineQuizDTO.getQuizComment ()  , "")  ;
    sQuizCurriUrl    =  StringUtil.nvl( onlineQuizDTO.getQuizCurriUrl()  , "")  ;
    sQuizLinkUrl     =  StringUtil.nvl( onlineQuizDTO.getQuizLinkUrl ()  , "")  ;
    sUseYn           =  StringUtil.nvl( onlineQuizDTO.getUseYn       ()  , "")  ;
    sRegMode         =  "E"   ;
}

%>
<Script Language="javascript">


/** desc : 온라인 퀴즈타입을 변경한다.
 ** doChgQuizType()
 */
function doChgQuizType(val)
{
    var f=document.all ;

    if( val =="S" ) // 선택형
    {
        f.quizTypeSelect.style.display  = "";
        f.quizTypeOX    .style.display  = "none";
    }
    else
    {
        f.quizTypeSelect.style.display  = "none";
        f.quizTypeOX    .style.display  = "";
    }

    f.pQuizType.value = val;

}

/** desc : 온라인 퀴즈 목록으로 이동한다.
 ** goList()
 */
function goList()
{
    var f=document.Frm ;

    f.action = "<%=CONTEXTPATH%>/OnlineQuiz.cmd?cmd=onlineQuizList&pMode=<%=PMODE%>&MENUNO=0";
    f.submit();

}


/** desc : 온라인 퀴즈를 수정한다
 ** doEdit()
 */
function doEdit()
{

    var f=document.Frm ;

    if(!validate(f)) return ;

    Obj = ( f.pQuizType.value=="S" ) ? f.pAnswer_S : f.pAnswer_O ;
    var cnt =0 ;

    for(i=0 ;i< Obj.length;i++)
    {
        if( Obj[i].checked == true)
        {
            f.pAnswer.value = Obj[i].value ;
            cnt++;
            break;
        }
    }

    if( cnt==0)
    {
        alert("정답을 선택해 주세요.");
        return;
    }

    f.action = "<%=CONTEXTPATH%>/OnlineQuiz.cmd?cmd=onlineQuizRegist";
    f.submit();
}


/** desc : 온라인 퀴즈를 삭제한다
 ** doDelete()
 */
function doDelete()
{
    var f=document.Frm ;

    if(confirm("온라인 퀴즈를 삭제하시겠읍니까?")==false) return;

    f.pRegMode.value = 'D' ;

    f.action = "<%=CONTEXTPATH%>/OnlineQuiz.cmd?cmd=onlineQuizRegist";
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
<!-- form start -->
<form name=Frm method=post>
<input type=hidden name=pMode     value="<%=PMODE       %>">
<input type=hidden name=pRegMode  value="<%=sRegMode    %>">
<input type=hidden name=pSeqNo    value="<%=sSeqNo      %>">
<input type=hidden name=pAnswer   value="<%=sAnswer     %>">
<input type=hidden name=pQuizType value="<%=sQuizType   %>">

											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">제목</td>
												<td class="s_tab_view02"><input type="text" name="pSubject" value="<%=sSubject %>" size="70" maxLength=100 dispName="제목" notNull></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">표시여부</td>
												<td class="s_tab_view02">
													<input type="radio" name="pUseYn" value="Y" class="solid0" <%=( ("Y").equals(sUseYn )? "checked" : "" )%>>공개
                                   					<input type="radio" name="pUseYn" value="N" class="solid0" <%=( ("N").equals(sUseYn )? "checked" : "" )%>>비공개
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">보기유형</td>
												<td class="s_tab_view02">
													<script language=javascript>Button5("선택형", "doChgQuizType('S')", "");</script>&nbsp;<script language=javascript>Button5("OX형", "doChgQuizType('O')", "");</script>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
										</table>
										<!-- 선택형문제출제 테이블 -->
										<table width="670" align="center" border="0" cellspacing="0" cellpadding="0" id=quizTypeSelect Style="display:<%=( ("S").equals(sQuizType) ? "": "none") %>">
											<tr>
												<td align="center" colspan="3"  class="s_tab_view03">
													<font color="AB8264">* 보기를 입력하시고 정답을 선택하십시오.</font>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="3"></td>
											</tr>
<%	for (int i=1; i<6; i++) {	%>
											<tr>
												<td class="s_tab_view01" width="92">보기 <%=i %></td>
												<td class="s_tab_view02"><input type="text" name="pExample<%=i %>" value="<%=StringUtil.nvl(sExample[i]) %>" size="80" maxLength=200 dispName="보기 <%=i %>"></td>
												<td class="s_tab_view02"><input type="radio" name="pAnswer_S" value="<%=i %>" class="no" <%=( Integer.toString(i).equals(sAnswer)? "checked" : "" ) %>></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="3"></td>
											</tr>
<%	}	%>
										</table>
										<!-- OX문제출제 테이블 -->
										<table width="670" align="center" border="0" cellspacing="0" cellpadding="0" id=quizTypeOX Style="display:<%=( ("O").equals(sQuizType) ? "": "none") %>">
											<tr>
												<td class="s_tab_view01" width="92">선택</td>
												<td class="s_tab_view02">
													<input type="radio" name="pAnswer_O" value="O" class="solid0" <%=( ("O").equals(sAnswer)? "checked" : "" ) %>>O(맞음)
                                        			<input type="radio" name="pAnswer_O" value="X" class="solid0" <%=( ("X").equals(sAnswer)? "checked" : "" ) %>>X(틀림)
												</td>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
										</table>
										<!-- 설명 등의 테이블 -->
										<table width="670" align="center">
											<tr>
												<td class="s_tab_view01" width="92">설명</td>
												<td class="s_tab_view03"><textarea name="pQuizComment" cols="70" rows="3"><%=sQuizComment %></textarea></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">연결강좌</td>
												<td class="s_tab_view02">
													<select name=pQuizCurriUrl>
<%
	String curriName  = "";
	String curriValue = "";

	ArrayList curriSubList = (ArrayList)model.get("curriSubList");
	CurriSubDTO curriSubDTO = new CurriSubDTO() ;

	for (int i=0 ; i<curriSubList.size() ; i++) {
    	curriSubDTO = (CurriSubDTO)curriSubList.get(i);
    	curriValue = curriSubDTO.getSystemCode() + "!" + curriSubDTO.getCurriCode() + "!"  + curriSubDTO.getCurriYear() + "!"  + curriSubDTO.getCurriTerm() ;
    	curriName  = curriSubDTO.getCurriName()  + "[" + curriSubDTO.getCurriYear() + "년" + curriSubDTO.getCurriTerm() +"기]" ;
    	out.println("<option value='"+ curriValue +"'>"+ curriName +"</option>");
    }
%>
                                        			</select>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">연결주소</td>
												<td class="s_tab_view03">
													<input type="text" name="pQuizLinkUrl" value="<%=sQuizLinkUrl %>" class="green" size="70">
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
<%	if (("E").equals(sRegMode)) {	%>
&nbsp;<script language=javascript>Button3("수정", "doEdit()", "");</script>
<%	} else {	%>
&nbsp;<script language=javascript>Button3("등록", "doEdit()", "");</script>
<%	}

	if (("E").equals(sRegMode)) {	%>
&nbsp;<script language=javascript>Button3("삭제", "doDelete()", "");</script>
<%	}	%>
												</td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->

<%@include file="../common/footer.jsp" %>