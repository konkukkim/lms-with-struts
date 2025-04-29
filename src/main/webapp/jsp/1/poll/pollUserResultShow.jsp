<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.poll.dto.InternetPollDTO,com.edutrack.poll.dto.InternetPollCommentDTO "%>
<%@include file="../common/header.jsp" %>
<%
	String pPollId  = (String)model.get("pPollId");

	InternetPollDTO pollView = (InternetPollDTO)model.get("pollShow");
	int tot_cnt = pollView.getHitCnt();	//전체 응답수

    String 	pMode 		=  (String)model.get("pMode");

    boolean bHome   = pMode.equals("Home"  ) ? true : false  ;
    String  img_path    =  CONTEXTPATH+"/img/"+SYSTEMCODE ;

    // 제목 이미지 변수 정의
    String title_img      = img_path + ( bHome ? "/00/00_tdimg07.gif" : "/03/03_img303.gif"  );   // 제목
    String comment_img    = img_path + ( bHome ? "/00/00_tdimg08.gif" : "/03/03_img318.gif"  );   // 설명
    String result_img     = img_path + ( bHome ? "/00/00_tdimg09.gif" : "/03/03_img319.gif"  );   // 결과
    String vert_line      = img_path + ( bHome ? "/common/table_line_brown.gif" : "/common/table_line_green.gif"  );   // 세로라인

    String bar_line       = img_path + ( bHome ? "/common/bar_pink.gif"     : "/common/ing_bar.gif"       );   // 그래프 라인
    String list_btn       = img_path + ( bHome ? "/button/list_brown01.gif" : "/button/list_green.gif"    );   // 목록 버튼
    String cancle_btn     = img_path + ( bHome ? "" : "/button/cancle_green.gif"  );   // 취소 버튼

    String horiz_line     = ( bHome ? "bgcolor='C2B29D'" : "bgcolor='90B293'"  );   // 상단, 하단라인
    String mid_line       = ( bHome ? "background='" +img_path + "/03/03_bgimg03.gif'" : "bgcolor='D5E4CF'"  );   // 중간라인  //D5E4CF

%>
<script language="javascript">
	function submitCheck()
	{
		var f = document.Input;

	 	if(!validate(f))
        	return false;
//        else
//            return true;
		f.submit();

	}

	function goCommDel(poll_id, seq_no) {
		var stat = confirm("한줄 답변을 삭제하시겠습니까?");
		if (stat == true) {
			document.location = "<%=CONTEXTPATH%>/Poll.cmd?cmd=pollCommentDelete&pMode=<%=PMODE%>&pPollId="+poll_id+"&pSeqNo="+seq_no;
		}
	}

	function goList() {
<%
	if(PMODE.equals("Home")) {
%>
		document.location = "<%=CONTEXTPATH%>";
<%
	} else {
%>
		document.location = "<%=CONTEXTPATH%>/Poll.cmd?cmd=pollList&pMode=<%=PMODE %>";
<%	}	%>
	}

</Script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="90">제목</td>
												<td class="s_tab_view02" width="580"><%=pollView.getSubject()%><font color="#3399FF"> (응답인원 :&nbsp;<%=tot_cnt%>명)</font></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="90">설명</td>
												<td class="s_tab_view02" width="580"><%=StringUtil.getHtmlContents(pollView.getContents())%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">결과</td>
												<td class="s_tab_view03">
													<!-- 투표결과 -->
<%
	int img_w   = 0;    // 이미지 넓이
//  int img_w2  = 0;    // 이미지 넓이2
	int img_h   = 13;   // 이미지 높이
	double pcnt    = 0;    // 비율
	String img_bar = "poll_color_01";

	for(int i=0; i < pollView.getExampleCnt(); i++) {

    	if (pollView.getHit()[i] > 0) {
        	pcnt = Math.round( ((double)pollView.getHit()[i] / tot_cnt * 100) * 10    ) / 10.0;
        	img_w = (pollView.getHit()[i] * 100) / tot_cnt;
    	} else {
        	img_w = 1;
    	}

    	if((i+1)%2==0){
        	//img_bar	=	"ing_bar01.gif";
    	}else{
        	//img_bar	=	"ing_bar.gif";
    	}
%>
													<table>
														<tr>
															<td colspan="3" height="25"><%=i+1%>. <%=pollView.getExample()[i]%></td>
														</tr>
														<tr>
															<td width="280" height="17">
																<table height=17 cellspacing=1 cellpadding=1 bgcolor=#f4f4f4>
																	<tr>
																		<td width=250 bgcolor=#ffffff height=17>
																			<table height="17" width="250" align="center">
																				<tr>
																					<td class="ballot"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/ing_bar.gif" width="<%=img_w%>" height="<%=img_h%>"></td>
																				</tr>
																			</table>
																		</td>
																	</tr>
																</table>
															</td>
															<td width=177><b><font class="s_text03"><%=pollView.getHit()[i]%></font></b>표</td>
															<td width=188><font class="s_text03"><%=pcnt%></font>%</td>
														</tr>
													</table>
<%
	}
%>
													<!-- // 투표결과 -->

												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
												</td>
											</tr>
<%
	//로그인일경우
    if(!USERID.equals("")) {
%>
											<!-- 댓글테이블 -->
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td colspan="2">
													<!-- 댓글 쓰기 -->
													<table width="100%" class="reply_form">
<!-- form start   onSubmit="return submitCheck()"  -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/Poll.cmd?cmd=pollCommentRegist">
<input type='hidden' name ='pPollId' value='<%=pPollId%>'>
<input type='hidden' name ='pMode' value='<%=PMODE%>'>

														<tr>
															<td class="reply_w_icon">
																<table>
																	<tr>
<%
		// 이모티콘
		for(int i=1; i<=6; i++) {
%>
																		<td><input type=radio value="<%=i%>" name=pEmoticon <%if(i==1){%>checked<%}%>></td>
																		<td width=32><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_0<%=i%>.gif"></td>
<%		}	%>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td class="reply_w_text01">
																<textarea name="pContents" rows=2 wrap=virtual cols=96 maxlength='4000' dispName="답변내용" lenCheck="2000" notnull></textarea>
																<!--등록버튼-->&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_record.gif" align="absmiddle" onClick="submitCheck()" style="cursor:hand">
															</td>
														</tr>
</form>
<!-- form end -->
													</table>
<!-- 댓글 내용 -->
<%
	String emotion = "";
	ArrayList list = (ArrayList)model.get("commentList");
	InternetPollCommentDTO poll = null;
	int	No = list.size();
	if(No > 0) {
%>
													<table>
														<tr>
															<td height="5"></td>
														<tr>
													</table>
<%
	}
	for (int i=0; i < list.size(); i++) {
		poll = (InternetPollCommentDTO)list.get(i);
%>
													<table width="100%" align="center">
														<tr>
															<td class="reply_r_textform">
																<table width="650" align="center">
																	<tr>
																		<td valign="top" width="30">
<%
		if (!poll.getEmoticonNum().equals("")) {
%>
																			<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_0<%=poll.getEmoticonNum()%>.gif">
<%
		}
%>
																		</td>
																		<td width="100"><font color=#9a7441><%=poll.getRegName()%></font></td>
																		<td>
																			<%=StringUtil.getHtmlContents(poll.getContents())%>
																			&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_reply.gif" align="absmiddle">&nbsp;
																		</td>
																		<td width="100" align="right" style="padding-right:6px"><%=DateTimeUtil.getDateType(1, poll.getRegDate())%></td>
																		<td width="18" align="right" valign="top">
<!-- 글등록자와 관리자인 경우에만 리플 삭제 -->
<%		if(USERID.equals(poll.getRegId()) || USERTYPE.equals("M")) { %>
																			<a href="javascript:goCommDel('<%=pPollId%>','<%=poll.getSeqNo()%>');"><img src="<%=img_path%>/common/re_del.gif" alt="리플삭제하기" width="9" height="9" align="absmiddle" border="0"></a>
<%		} %>
																		</td>
																	</tr>
																</table>
																<table width="640" align="center">
																	<tr>
																		<td class="s_tab03" height=1></td>
																	</tr>
																</table>
															</td>
														</tr>
													</table>
<%
	}
%>
												</td>
											</tr>
<%	if(No > 0) {	%>
											<table>
												<tr>
													<td height="15"></td>
												<tr>
											</table>
<%	}	%>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<!-- // 댓글테이블 끝 -->
<%	}	%>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>
