<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.courseexam.dto.ExamContentsDTO, com.edutrack.courseexam.dto.ExamInfoDTO, com.edutrack.courseexam.dto.ExamAnswerDTO"%>
<%@ page import ="com.edutrack.common.dto.UserMemDTO, com.edutrack.common.dto.CurriMemDTO" %>
<%@include file="../common/popup_header.jsp" %>
<%
    ExamInfoDTO examInfo = (ExamInfoDTO)model.get("examInfo");
    ExamAnswerDTO answerInfo = (ExamAnswerDTO)model.get("answerInfo");
    ExamContentsDTO cI = null; // contents 정보
    ArrayList contentsList = (ArrayList)model.get("contentsList");
    String pMODE = (String)model.get("pMODE");
    String pCourseId = (String)model.get("pCourseId");
    String pExamId = (String)model.get("pExamId");
    String pExamType = "";
    int remainTime = examInfo.getRunTime() - answerInfo.getAnswerTime();
    int remainTimeSec = remainTime*60;
    String LineBgColor = "#40B389";

	UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	CurriMemDTO curriMemDto = userMemDto.curriInfo;
	String CURRINAME = curriMemDto.curriName;



%>
<script language="javascript">
   <% if(examInfo.getViewStyle().equals("S")){%>
	var totalDbLength=<%=contentsList.size()%>; //전체 문제의 갯수를 판단함.
    function setQuestion(mode){
        var f = document.navigation;

        var curNo = parseInt(f.curNo.value);
        var curDiv = eval("window.question" + curNo + ".style");
        var imsiNo = 0;
        var objDiv;
        if(mode == "N"){
           imsiNo = curNo + 1;
           if(imsiNo > totalDbLength){
              alert("다음문제가 없습니다.");
              return;
           }else{
	           imsiNo = curNo + 1;
	           objDiv = eval("window.question" + imsiNo + ".style");
	           curDiv.display = "none";
	           objDiv.display = "";
	           f.curNo.value=imsiNo;
	       }
        }else{
            imsiNo = curNo - 1;
           if(imsiNo < 1){
              alert("이전문제가 없습니다.");
              return;
           }else{
	           imsiNo = curNo - 1;
	           objDiv = eval("window.question" + imsiNo + ".style");
	           curDiv.display = "none";
	           objDiv.display = "";
	           f.curNo.value=imsiNo;
	  	  }
        }
    }
    <% }%>
	self.moveTo(0,0);
	self.resizeTo(950,750);


	// 마우스 오른쪽 클릭 막기
	function hidestatus() {
		window.status='평생교육 사이버대학';
		return true;
	}
	if (document.layers)
	document.captureEvents(Event.MOUSEOVER | Event.MOUSEOUT)
	document.onmouseover=hidestatus
	document.onmouseout=hidestatus

	function clickIE4(){
		if (event.button==2){
			return false;
		}
	}
	function clickNS4(e){
		if (document.layers||document.getElementById&&!document.all){
			if (e.which==2||e.which==3){
				return false;
			}
		}
	}
	if (document.layers){
		document.captureEvents(Event.MOUSEDOWN);
		document.onmousedown=clickNS4;
	}else if (document.all&&!document.getElementById){
		document.onmousedown=clickIE4;
	}
	document.oncontextmenu=new Function("return false")
	//document.onselectstart=new Function("return false");
	document.ondragstart=new Function("return false");

<% if(examInfo.getFlagTime().equals("Y")){%>
	function Show_ExTime(etime)
	{
	    var f = document.f;

		ex_time		= etime;
		//--	남은시간(초)
		last_time	= ex_time;
		//--	시험시간이 종료되면,
		if (ex_time < 0)
		{
			alert('시험시간이 종료되었습니다.\n\n시험을 마침니다. 수고하셨습니다.');
			<% if(!pMODE.equals("P")){%>
			 document.Input.submit();
			<%}else{%>
                window.close();
            <% } %>
		}
		//--	시험시간이 종료되지 않았으면,
		if (ex_time >= 0)
		{
			if (ex_time >= 3600)
			{
				var t1 = parseInt(ex_time / 3600);
				if (t1 == 0)
					t1	= '00';
				else if (t1 < 10)
					t1	= '0' + t1;
			}
			else
				t1		= '00';

			if (ex_time >= 60)
			{
				if (ex_time >= 3600)
					var t2 = parseInt(parseInt(ex_time % 3600) / 60);
				else
					var t2 = parseInt(ex_time / 60);

				if (t2 == 0)
					t2 = '00';
				else if (t2 < 10)
					t2 = '0' + t2;
			}
			else
				t2		= '00';

			var t3	= ex_time % 60;
			if (t3 == 0)
				t3	= '00';
			else if (t3 < 10)
				t3	= '0' + t3;

			var time_str = t1 + ':' + t2 + ':' + t3 ;
			f.examTime.value = time_str;
			f.lastTime.value = last_time;
			ex_time		= ex_time - 1;
			timer_id	= setTimeout('Show_ExTime(ex_time)',1000);
		}
	}
<%}%>
	function exam_cancel(){
	   var st = confirm('답안 제출을 하지 않았습니다 \n\n      종료 합니까?');
		if (st == true) {
		   window.close();
		}
	}

	function Answer_Submit(){
	    var f = document.Input;
		var st = confirm('정답 제출은 한번만 가능합니다 답안을 제출합니까? \n\n  문제 발생시 재시험을 담당자에게 요청하세요');
		if (st == true) {
           f.submit();
		}else return;
	}

	function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }
</script>
<iframe name="hiddenFrame" id="hiddenFrame" width="5" height="0" frameborder="0" border="0"></iframe>
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="c_test_bodybg" oncontextmenu="return false" onselectstart="return false" ondragstart="return false">
		<tr>
			<td height="65" align="left" valign="top" style="padding:0 0 5 0" >
				<table width="100%" height="35" border="0" cellpadding="0" cellspacing="0" class="c_test_titbg">
<form name="f">
					<tr>
						<td width="33" align="left" valign="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_test01.gif" width="40" height="50"></td>
						<td align="left" valign="middle" class="c_test_tit" >시험명 : <%=examInfo.getSubject()%>
							<!--<font size="2">[<%=CURRINAME%> &gt; <%=CommonUtil.getCourseName(SYSTEMCODE,pCourseId)%>]</font>-->
						</td>
						<td width="130" align="left" valign="middle">
<%	if (examInfo.getFlagTime().equals("Y")) {	%>
							<!-- 시간 체크 -->
							<table width="114" height="21" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_timebar.gif">
								<tr>
									<td width="34" align="right" class="c_test_time" height="21">시간</td>
									<td width="68" align="center" valign="top" class="c_test_time" height="21">
										<input type="text" name="examTime" size="7" style="height:15px;font-weight:bold; color:black; text-align:center; background-color:rgb(219,236,250); border-width:0px; border-color:rgb(178,211,145); border-style:no;">
										<input type="hidden" name="lastTime">
									</td>
								</tr>
							</table>
							<!-- // 시간 체크 -->
<%	}	%>
						</td>
						<td width="11" align="right" valign="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_test02.gif" width="17" height="50"></td>
					</tr>
</form>
				</table>
			</td>
		</tr>
		<tr>
			<td height="100%" align="left" valign="top" style="padding:10 15 5 15">
				<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="60%" height="20" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_test03.gif" width="87" height="21"></td>
						<td height="20" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_test04.gif" width="87" height="21"></td>
					</tr>
					<tr>
						<td width="65%" height="100%" align="left" valign="top">
							<!-- 문제 시작 -->
<!--++++++++ 문제 출제 부분 시작 +++++++++++++++++++++-->
							<table width="97%" height="100%" border="0" cellpadding="0" cellspacing="1" class="c_test01bg">
								<tr>
									<td height="100%" align="left" valign="top" bgcolor="#FFFFFF" style="padding:15 15 15 15">
										<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td align="center" valign="top">
<%
	//시험문제를 한 화면에서 볼때
	if (examInfo.getViewStyle().equals("L")) {
%>
<DIV id="question" style="OVERFLOW:auto; OVERFLOW-x:hidden; WIDTH:540; HEIGHT: 515px; visibility:visible; ">
<%
		for (int i =0; i < contentsList.size(); i++) {
        	cI = (ExamContentsDTO)contentsList.get(i);
        	pExamType = cI.getExamType();
%>
													<table width="" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td height="20" align="left" valign="middle" class="c_test_text01"><strong><%=i+1%>번 문제</strong> (<%=CommonUtil.getContentsTypeName(cI.getExamType())%> | <%=cI.getScore()%>점)</td>
														</tr>
														<tr>
															<td height="1" class="c_test_tablien01"> </td>
														</tr>
														<tr>
															<td height="25" class="c_test_tab01">
																<%=StringUtil.ReplaceAll(StringUtil.ReplaceAll(cI.getContents(),"&quot;","\""), "550", "500")%>
															</td>
														</tr>
<%			if (pExamType.equals("K")) {	%>
														<tr>
															<td height="25" class="c_test_tab02">
																&nbsp;정답수 (<%=cI.getAnswer()%>개)
															</td>
														</tr>
<%			}
			if (!cI.getRfileName().equals("")) {	%>
														<tr>
															<td height="1" class="c_test_tablien02"> </td>
														</tr>
														<tr>
															<td height="25" class="c_test_tab02">&nbsp;<a 	href="javascript:fileDownload('<%=cI.getRfileName()%>','<%=cI.getSfileName()%>','<%=cI.getFilePath()%>','<%=cI.getFileSize()%>');"><font color="#FF6600">* 관련파일 다운</font></a>
															</td>
														</tr>
<%
			}
			if (pExamType.equals("J")) {
%>
														<tr>
															<td height="1" class="c_test_tablien01"> </td>
														</tr>
														<tr>
															<td height="22" align="left" valign="top" style="padding: 5 0 0 0"><font class="s_text03">* 답란에 답을 기술하여 주십시요.</font></td>
														</tr>
<%			} else if (pExamType.equals("K")) {	%>
														<tr>
															<td class="c_test_tab02">
<%				if (!cI.getExample1().equals("")) {	%>
																<b>1.</b> <%=cI.getExample1()%><br>
<%
				}
				if (!cI.getExample2().equals("")) {
%>
																<b>2.</b> <%=cI.getExample2()%><br>
<%
				}
				if (!cI.getExample3().equals("")) {
%>
																<b>3.</b> <%=cI.getExample3()%><br>
<%
				}
				if (!cI.getExample4().equals("")) {
%>
																<b>4.</b> <%=cI.getExample4()%><br>
<%
				}
				if (!cI.getExample5().equals("")) {
%>
																<b>5.</b> <%=cI.getExample5()%>
<%				}	%>
															</td>
														</tr>
<%			} else if (pExamType.equals("D")) {	%>
														<tr>
															<td height="1" class="c_test_tablien01"> </td>
														</tr>
														<tr>
															<td height="22" align="left" valign="top" style="padding: 5 0 0 0">* 단답형의 중복답안 처리에 대한 답안 구분은 파이프('|' Shift+￦)로 해 주세요.</td>
														</tr>
<%			} else if (pExamType.equals("S")) {	%>
														<tr>
															<td height="1" class="c_test_tablien01"> </td>
														</tr>
														<tr>
															<td height="22" align="left" valign="top" style="padding: 5 0 0 0">*맞음, 틀림 중 하나를 선택해 주세요.</td>
														</tr>

<%			}	%>
														<tr>
															<td height="50"></td>
														</tr>
													</table>
<%		}	//end for	%>
</div>
<%
	}

	//	시험문제를 한장씩 볼 때
	else {
		String visible = "hidden";

		for (int i =0; i < contentsList.size(); i++) {
    		cI = (ExamContentsDTO)contentsList.get(i);
        	pExamType = cI.getExamType();

        	if (i > 0 )
        		visible = "none";

        	else
        		visible = "";
%>
<DIV id="question<%=i+1%>" style="OVERFLOW: auto; WIDTH: 540px; HEIGHT: 470px; display:<%=visible%>;">
													<table width="100%" border="0" cellspacing="0" cellpadding="0">
														<tr>
															<td height="20" align="left" valign="middle" class="c_test_text01"><strong><%=i+1%>번 문제</strong> (<%=CommonUtil.getContentsTypeName(cI.getExamType())%> | <%=cI.getScore()%>점)</td>
														</tr>
														<tr>
															<td height="1" class="c_test_tablien01"> </td>
														</tr>
														<tr>
															<td height="25" class="c_test_tab01">
																<%=cI.getContents()%>
															</td>
														</tr>
<%				if (pExamType.equals("K")) {	%>
														<tr>
															<td height="25" class="c_test_tab02">
																&nbsp;정답수 (<%=cI.getAnswer()%>개)
															</td>
														</tr>
<%				}

				if (!cI.getRfileName().equals("")) {
%>
														<tr>
															<td height="25" class="c_test_tab02">&nbsp;<a href="javascript:fileDownload('<%=cI.getRfileName()%>','<%=cI.getSfileName()%>','<%=cI.getFilePath()%>','<%=cI.getFileSize()%>');"><font color="#FF6600">* 관련파일 다운</font></td>
														</tr>
<%
				}
				if (pExamType.equals("J")) {
%>
														<tr>
															<td height="1" class="c_test_tablien01"> </td>
														</tr>
														<tr>
															<td height="22" align="left" valign="top" style="padding: 5 0 0 0"><font class="s_text03">* 답란에 답을 기술하여 주십시요.</font></td>
														</tr>
<%				} else if (pExamType.equals("K")) {	%>
														<tr>
															<td class="c_test_tab02">
<%					if (!cI.getExample1().equals("")) {	%>
																<b>1.</b> <%=cI.getExample1()%><br>
<%
					}
					if (!cI.getExample2().equals("")) {
%>
																<b>2.</b> <%=cI.getExample2()%><br>
<%
					}
					if (!cI.getExample3().equals("")) {
%>
																<b>3.</b> <%=cI.getExample3()%><br>
<%
					}
					if (!cI.getExample4().equals("")) {
%>
																<b>4.</b> <%=cI.getExample4()%><br>
<%
					}
					if (!cI.getExample5().equals("")) {
%>
																<b>5.</b> <%=cI.getExample5()%>
<%					}	%>
															</td>
														</tr>
<%				} else if (pExamType.equals("D")) {	%>
														<tr>
															<td height="1" class="c_test_tablien01"> </td>
														</tr>
														<tr>
															<td height="22" align="left" valign="top" style="padding: 5 0 0 0">* 단답형의 중복답안 처리에 대한 답안 구분은 파이프('|' Shift+￦)로 해 주세요.</td>
														</tr>
<%				} else if (pExamType.equals("S")) {	%>
														<tr>
															<td height="1" class="c_test_tablien01"> </td>
														</tr>
														<tr>
															<td height="22" align="left" valign="top" style="padding: 5 0 0 0">*맞음, 틀림 중 하나를 선택해 주세요.</td>
														</tr>
<%				}	%>
													</table>
</div>
<%		}	//end for
	}	//end else	%>
												</td>
											</tr>
<%	if (examInfo.getViewStyle().equals("S")) {	%>
<form name="navigation">
<input type=hidden name="curNo" value="1">
											<tr>
												<td height="35" align="center" valign="middle">
													<a href="javascript:setQuestion('P');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_test_p.gif" width="73" height="22" hspace="4"></a>
													<a href="javascript:setQuestion('N');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_test_n.gif" width="73" height="22" hspace="4"></a>
												</td>
											</tr>
</form>
<%	}	%>
<!--++++++++ 문제 출제 부분 종료 +++++++++++++++++++++-->
										</table>
									</td>
								</tr>
							</table>
							<!-- // 문제 시작 -->
						</td>
						<td width="35%" height="100%" align="left" valign="top">
							<!--  답안작성 시작 -->
							<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="1" class="c_test01bg">
								<tr>
									<td align="left" valign="top" bgcolor="#FFFFFF" style="padding:10 10 10 10">
<!--++++++++ 답안 작성 부분 시작 +++++++++++++++++++++-->
<div id="answer" style="OVERFLOW: auto; WIDTH:100%; HEIGHT: 500px;">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
<%
	String objType = "";

  	for (int i =0; i < contentsList.size(); i++) {
		cI = (ExamContentsDTO)contentsList.get(i);
	 	pExamType = cI.getExamType();
%>
<form name=Input method="post" action="<%=CONTEXTPATH%>/ExamAnswer.cmd?cmd=examAnswerRegist">
<input type=hidden name="pMODE"     value="<%=pMODE%>">
<input type=hidden name="pCourseId" value="<%=pCourseId%>">
<input type=hidden name="pExamId"   value="<%=pExamId%>">
																						<tr>
												<td height="20" align="left" valign="middle" class="c_test_text02"><%=i+1%>번 답안</td>
											</tr>
<%		if (pExamType.equals("J")) {	%>
											<tr>
												<td height="20" style="padding: 0 0 5 0">
													<textarea name="pAnswer<%=cI.getExamOrder()%>" style="width:100%"></textarea>
												</td>
											</tr>
<%
		}
		else if (pExamType.equals("K")) {

        	if (Integer.parseInt(cI.getAnswer()) > 1)
        		objType = "checkbox";

        	else
        		objType = "radio";
%>
											<tr>
												<td height="20" style="padding: 0 0 5 0">
<%			if (!cI.getExample1().equals("")) {	%>
													<font class="c_test_text03">1.</font><input class=chek1 type='<%=objType%>' checked value=1 name="pAnswer<%=cI.getExamOrder()%>" align="absmiddle">
<%			}
			if (!cI.getExample2().equals("")) {
%>
													<font class="c_test_text03">2.</font><input class=chek1 type='<%=objType%>' value=2 name="pAnswer<%=cI.getExamOrder()%>">
<%			}
			if (!cI.getExample3().equals("")) {
%>
													<font class="c_test_text03">3.</font><input class=chek1 type='<%=objType%>' value=3 name="pAnswer<%=cI.getExamOrder()%>">
<%			}
			if (!cI.getExample4().equals("")) {
%>
													<font class="c_test_text03">4.</font><input class=chek1 type='<%=objType%>' value=4 name="pAnswer<%=cI.getExamOrder()%>">
<%			}
			if (!cI.getExample5().equals("")) {
%>
													<font class="c_test_text03">5.</font><input class=chek1 type='<%=objType%>' value=5 name="pAnswer<%=cI.getExamOrder()%>">
<%			}	%>
												</td>
											</tr>
<%
		}
		else if (pExamType.equals("D")) {
%>
											<tr>
												<td height="20" style="padding: 0 0 5 0">
													<textarea name="pAnswer<%=cI.getExamOrder()%>" style="width:100%"></textarea>
												</td>
											</tr>
<%
		}
		else if (pExamType.equals("S")) {
%>
											<tr>
												<td height="20" style="padding: 0 0 5 0">
													맞음:<input type="radio" name="pAnswer<%=cI.getExamOrder()%>" value="O" style=border=0 checked>
													&nbsp;&nbsp;
													틀림:<input type="radio" name="pAnswer<%=cI.getExamOrder()%>" value="X" style=border=0 >
												</td>
											</tr>
<%		}	%>
<input type="hidden" name="pAnswerInfo<%=cI.getExamOrder()%>" value="<%=cI.getExamNo()%>/<%=cI.getExamType()%>/<%=cI.getScore()%>">
											<tr>
												<td height="1" class="c_test_tablien01"></td>
											</tr>
											<tr>
												<td height="20" align="left" valign="top"></td>
											</tr>
<%
	}	// for
%>
<!--<input type="hidden" name="pMaxOrder" value="<%=cI.getExamOrder()%>">-->
<input type="hidden" name="pMaxOrder" value="<%=contentsList.size()%>">
</form>
										</table>
</div>
									</td>
								</tr>
							</table>
							<!--  // 답안작성 시작 -->
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td height="50" align="right" valign="middle" class="c_test_btn">
<%	if (!pMODE.equals("P")) {	%>
	<a href="javascript:Answer_Submit();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_test_ok.gif" width="96" height="22"></a>
<%
	}
	else {
%>
&nbsp;&nbsp;<a href="javascript:exam_cancel();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_test_close.gif" width="48" height="22"></a>
<%	}	%>
			</td>
		</tr>
	</table>
<% if(!pMODE.equals("P") && examInfo.getFlagTime().equals("Y")){%>
	<IFRAME ID="connectFrame" NAME="connectFrame" SRC="<%=CONTEXTPATH%>/ExamAnswer.cmd?cmd=examAnswerTimeEdit&pCourseId=<%=pCourseId%>&pExamId=<%=pExamId%>" WIDTH="0" HEIGHT="0" frameborder="0" scrolling="NO" noresize>
	</IFRAME>
<%}%>
<SCRIPT LANGUAGE="JavaScript">
<!--
<%
	StringBuffer sbMsg = new StringBuffer();
	sbMsg.append("시험응시간 유의사항 ※ \\n\\n");
	sbMsg.append("1. 문제를 풀기전에 문제지 혹은 답안지에 "+contentsList.size()+"문제가 다 보이는지 확인후 진행하시기 바랍니다.  \\n\\n");
	sbMsg.append("2. 컴퓨터 등의 문제로 인하여 시험이 비정상종료 되었을때...   \\n\\n");
	sbMsg.append("   관리자 혹은 교수님에게 메일이나 질의응답으로 재시험 요청을 하시기 바랍니다. \\n\\n");
	sbMsg.append("   단, 시험기간내에 처리가 가능하므로 되도록 빨리 알려주시기 바랍니다.\\n\\n");
	if(examInfo.getFlagTime().equals("Y"))
		sbMsg.append("3. 시험시간("+remainTime+"분)이 종료되기전에 문제를 다 푸시고 종료하시기 바랍니다.\\n\\n\\n\\n");
	sbMsg.append("그럼 최선을 다하셔서 좋은결과가 있기를...");
	String alertMsg = sbMsg.toString();
%>
<% if(examInfo.getFlagTime().equals("Y")){%>
    Show_ExTime(<%=remainTimeSec%>);
<%}%>
	//alert("※ 시험응시간 유의사항 ※\n\n 1. 문제를 풀기전에 <%=contentsList.size()%>문제가 다 보이는지 확인후 진행하시기 바랍니다.\n\n (만약 문제수가 부족하면 시험창을 새로고침(F5)후 문제수를 확인해보시기 바랍니다 - 이상시 재실행)\n\n 2. 컴퓨터 등의 문제로 인하여 시험이 비정상종료 되었을때... \n\n 시험리스트 -> 해당과목명 을 클릭하셔서 시험의 상태를 확인해보시기 바랍니다.	\n\n 해당 시험의 응시/결과가 '응시[미완료]'일때는 계속해서 시험창을 띄워서 남은시간만큼 응시가 가능합니다.\n\n 하지만 비정상종료되었는데 '응시'로 되어 있다면 과정운영자에게 메일이나 질의응답에 글을 남겨주시기\n\n 바랍니다. 단, 시험기간내에 처리가 가능하므로 되도록 빨리 알려주시기 바랍니다.\n\n 3. 시험시간(<%=remainTime%>분)이 종료되기전에 문제를 다 푸시고 종료하시기 바랍니다.\n\n  그럼 최선을 다하셔서 좋은결과가 있기를...");
	<% if(!pMODE.equals("P")){%>
	opener.document.location.reload();
	<%}%>
	alert("<%=alertMsg%>");

//-->
</SCRIPT>
<%@include file="../common/popup_footer.jsp" %>