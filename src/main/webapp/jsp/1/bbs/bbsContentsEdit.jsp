<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.bbs.dto.BbsContentsDTO,com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%>
<%@include file="../common/header.jsp" %>
<%

	String pSec = (String)model.get("pSec");
	String pMode =  (String)model.get("pMode");
	String pBbsType = (String)model.get("pBbsType");
	String pBbsId = (String)model.get("pBbsId");
	String pFileUseYn = (String)model.get("pFileUseYn");
	int pFileCount = Integer.parseInt((String)model.get("pFileCount"));
    String img_path     =  CONTEXTPATH+"/img/"+SYSTEMCODE ;
	BbsContentsDTO contentsDto= (BbsContentsDTO)model.get("contentsDto");

	String 	bbsTitleImg	= "";
	String  bbsMainImg  = "";

    if (pMode.equals("MyPage"))  bbsMainImg = img_path+"/title/academy_m08.gif" ;
    else if (pMode.equals("Enroll")) bbsMainImg = img_path+"/title/process04.gif" ;


    boolean bHelp   = pMode.equals("Help"  ) ? true : false  ;
    boolean bEnroll = pMode.equals("Enroll") ? true : false  ;

	String titleImg = null;

	if (pBbsId.equals("1")) {
		titleImg = "tit10_01.gif";
	} else if (pBbsId.equals("2")) {
		titleImg = "tit10_05.gif";
	} else {
		titleImg = "mtit_10.gif";
	}
%>
<script language="javascript">
	function isEmpty(data)
	{
		for ( var i = 0 ; i < data.length ; i++ ) {
			if ( data.substring( i, i+1 ) != ' ' )
				return false;
		}
		return true;
	}

	function submitCheck()
	{
		/*  WEAS 삽입 스크립트3(시작) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS 삽입 스크립트3(끝) */
		var f = document.Input;

		var isValid = "N";
		var cnt = f.elements("pTarget").length;
		for( j = 0; j < cnt; j++){
			if (f.elements("pTarget")[j].checked) {
        		isValid = "Y";
            break;
         }
		}
		<%if(pBbsType.equals("notice")){%>
		if(isValid=="N"){
			alert("대상을 하나라도 체크하셔야합니다.");
			return false;
		}else{
			if(!validate(f))
		      return false;
//		   else
//		   	return true;
	   }
	   <%}else{%>
			if(!validate(f))
		      return false;
//		   else
//		   	return true;

	   <%}%>
	   f.submit();

	}

	function goDel(){
		/*
		var f = document.Input;
		f.action="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsDelete&pBbsId=<%=pBbsId%>&pSeqNo=<%=contentsDto.getSeqNo()%>";
		f.encoding = "application/x-www-form-urlencoded";
		f.submit();
		*/
		document.location.href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsDelete&pBbsId=<%=pBbsId%>&pSec=<%=pSec%>&pMode=<%=pMode%>&pSeqNo=<%=contentsDto.getSeqNo()%>";
	}


	function delFile(fileNum){
       	document.location.href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsFileDelete&pBbsId=<%=pBbsId%>&pSec=<%=pSec%>&pMode=<%=pMode%>&pSeqNo=<%=contentsDto.getSeqNo()%>&pFileNo="+fileNum;
    }


    function goContentsList(){
<%
		if (pSec.equals("site") || pSec.equals("total")) {
%>
			document.location.href="<%=CONTEXTPATH%>/BbsManagement.cmd?cmd=bbsContentsPagingList&pBbsId=<%=pBbsId%>&MENUNO=<%=MENUNO%>&pMode=<%=pMode%>&pSec=<%=pSec%>";
<%
		} else {
%>
		document.location.href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=<%=pBbsId%>&MENUNO=<%=MENUNO%>&pMode=<%=pMode%>&pSec=<%=pSec%>&MainMenu=<%=MainMenu%>";
<%
		}
%>
	}

    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }
</script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start onSubmit="return submitCheck()"  -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsRegist&pMode=<%=pMode%>&pRegMode=Edit&pBbsId=<%=pBbsId%>&pSec=<%=pSec%>&pSeqNo=<%=contentsDto.getSeqNo()%>&pRegDate=<%=StringUtil.nvl(contentsDto.getRegDate())%>" enctype="multipart/form-data" >
<input type="hidden" name='pMode'               value='<%=pMode%>'>
<input type="hidden" name='pBbsNo'              value='<%=contentsDto.getSeqNo()%>'>
<input type="hidden" name='pDepthNo'            value='<%=contentsDto.getDepthNo()%>'>
<input type="hidden" name='pOrderNo'            value='<%=contentsDto.getOrderNo()%>'>
<input type="hidden" name='pParentNo'           value='<%=contentsDto.getParentNo()%>'>
<input type="hidden" name="pEditorType"         value="<%=StringUtil.nvl(contentsDto.getEditorType())%>">
<input type="hidden" name="pOldrFileName[1]"    value="<%=StringUtil.nvl(contentsDto.getRfileName1())%>">
<input type="hidden" name="pOldsFileName[1]"    value="<%=StringUtil.nvl(contentsDto.getSfileName1())%>">
<input type="hidden" name="pOldFilePath[1]"     value="<%=StringUtil.nvl(contentsDto.getFilePath1())%>">
<input type="hidden" name="pOldFileSize[1]"     value="<%=StringUtil.nvl(contentsDto.getFileSize1())%>">
<input type="hidden" name="pOldrFileName[2]"    value="<%=StringUtil.nvl(contentsDto.getRfileName2())%>">
<input type="hidden" name="pOldsFileName[2]"    value="<%=StringUtil.nvl(contentsDto.getSfileName2())%>">
<input type="hidden" name="pOldFilePath[2]"     value="<%=StringUtil.nvl(contentsDto.getFilePath2())%>">
<input type="hidden" name="pOldFileSize[2]"     value="<%=StringUtil.nvl(contentsDto.getFileSize2())%>">
<input type="hidden" name="pOldrFileName[3]"    value="<%=StringUtil.nvl(contentsDto.getRfileName3())%>">
<input type="hidden" name="pOldsFileName[3]"    value="<%=StringUtil.nvl(contentsDto.getSfileName3())%>">
<input type="hidden" name="pOldFilePath[3]"     value="<%=StringUtil.nvl(contentsDto.getFilePath3())%>">
<input type="hidden" name="pOldFileSize[3]"     value="<%=StringUtil.nvl(contentsDto.getFileSize3())%>">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="100">제목</td>
												<td class="s_tab_view02" width="570"><input type="text" name="pSubject" size="70" maxlength=100 notNull  dispName="제목" dataType="text" lenCheck="180" value="<%=StringUtil.nvl(contentsDto.getSubject())%>"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	if((UserBroker.getUserType(request).equals("M")) && pBbsType.equals("notice")) {
%>
											<tr>
												<td class="s_tab_view01">공지대상</td>
												<td class="s_tab_view02">
<%
		CodeParam param1 = new CodeParam();

        param1.setSystemcode("1");
        param1.setType("check");
        param1.setName("pTarget");
        param1.setEvent("");
        param1.setSelected("A,M");
        out.print(CommonUtil.getSoCode(param1, "201"));
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	} else {
%>
										<input type="hidden" name="pTarget" value="A">
<%
	}

	if(UserBroker.getUserType(request).equals("M")) {
%>
											<tr>
												<td class="s_tab_view01">게시만료일</td>
												<td class="s_tab_view02">
<%
		if (pBbsType.equals("notice")) {
			DateParam dateParam = new DateParam();
            dateParam.setCount(1);
            dateParam.setDateType(1);
            dateParam.setForm("Input");
            dateParam.setDate("pDate");
            dateParam.setYear("pYear");
            dateParam.setMonth("pMonth");
            dateParam.setDay("pDay");
            dateParam.setHour("pHour");
            dateParam.setMinute("pMinute");
            //dateParam.setPosX(500);
            //dateParam.setPosY(300);
//          dateParam.setCssNm( txt_css );  // 단순히 보더색을 주기 위한 스타일시트임
            dateParam.setReadonly("false");

            out.print(CommonUtil.getCalendar(dateParam, (DateSetter)model.get("ds")));
            out.print("<input type=\"hidden\" name=\"pContentsType\" value=\"S\">");
		} else {
			out.println("<input type=radio name=\"pContentsType\" class=\"solid0\" value=\"N\" "+ (StringUtil.nvl(contentsDto.getContentsType()).equals("N") ? " checked" :"" )+"> 공지 &nbsp;");
            out.println("<input type=radio name=\"pContentsType\" class=\"solid0\" value=\"S\" "+ (StringUtil.nvl(contentsDto.getContentsType()).equals("S") ? " checked" :"" )+"> 일반 ");
        }
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	}

    if(UserBroker.getUserId(request).equals("")) {
%>
											<tr>
												<td class="s_tab_view01" >이름</td>
												<td class="s_tab_view02"><input type="text" name="pRegName" size="15" maxlength=30 notNull  dispName="등록자이름" dataType="text" lenCheck="60" value="<%=StringUtil.nvl(contentsDto.getRegName())%>"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" >비밀번호</td>
												<td class="s_tab_view02"><input type="password" name="pRegPasswd" size="15" maxlength=20 notNull  dispName="비밀번호" dataType="text" lenCheck="20" value="<%=StringUtil.nvl(contentsDto.getRegPasswd())%>"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" >이메일</td>
												<td class="s_tab_view02"><input type="text" name="pRegEmail" size="60" dispName="이메일" dataType="text" lenCheck="100" value="<%=StringUtil.nvl(contentsDto.getRegEmail())%>"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	}

	if (pFileUseYn.equals("Y")) {
		String rFileName = "";
        String sFileName = "";
        String FilePath = "";
        String FileSize = "";

    	for (int i=1;i<=pFileCount;i++) {
%>

											<tr>
												<td class="s_tab_view01">첨부 <%=i%></td>
												<td class="s_tab_view02">
<%
			if (i == 1 && !StringUtil.nvl(contentsDto.getSfileName1()).equals("")) {
			    rFileName = StringUtil.nvl(contentsDto.getRfileName1());
			    sFileName = StringUtil.nvl(contentsDto.getSfileName1());
			    FilePath = StringUtil.nvl(contentsDto.getFilePath1());
			    FileSize = StringUtil.nvl(contentsDto.getFileSize1());

			    out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>");
			    out.print("&nbsp;&nbsp;");
			    out.print("<a href=\"#\" onClick=\"javascript:delFile('"+i+"')\">[기존파일삭제]</a>");
			    out.print("<br>");
			}

			if (i == 2 && !StringUtil.nvl(contentsDto.getSfileName2()).equals("")) {
			    rFileName = StringUtil.nvl(contentsDto.getRfileName2());
			    sFileName = StringUtil.nvl(contentsDto.getSfileName2());
			    FilePath = StringUtil.nvl(contentsDto.getFilePath2());
			    FileSize = StringUtil.nvl(contentsDto.getFileSize2());

			    out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>");
			    out.print("&nbsp;&nbsp;");
			    out.print("<a href=\"#\" onClick=\"javascript:delFile('"+i+"')\">[기존파일삭제]</a>");
			    out.print("<br>");
			}

			if (i == 3 && !StringUtil.nvl(contentsDto.getSfileName3()).equals("")) {
			    rFileName = StringUtil.nvl(contentsDto.getRfileName3());
			    sFileName = StringUtil.nvl(contentsDto.getSfileName3());
			    FilePath = StringUtil.nvl(contentsDto.getFilePath3());
			    FileSize = StringUtil.nvl(contentsDto.getFileSize3());

			    out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>");
			    out.print("&nbsp;&nbsp;");
			    out.print("<a href=\"#\" onClick=\"javascript:delFile('"+i+"')\">[기존파일삭제]</a>");
			    out.print("<br>");
			}
%>

												<input name="pFile[<%=i%>]" type="file" size="70" id="pFile[<%=i%>]"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
		}
	}
%>
											<tr>
												<td class="s_tab_view01" colspan="2">내용</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
												<td class="s_tab_view03" colspan="2"><textarea name="pContents" rows=15 wrap="VIRTUAL" cols="90"><%=StringUtil.nvl(contentsDto.getContents())%></textarea>
<%
	    if(StringUtil.nvl(contentsDto.getEditorType()).equals("W")) {
            EditorParam editerParam = new EditorParam();
		    editerParam.setShowFlag("true");
		    editerParam.setWidth(650);
		    editerParam.setHeight(300);
		    editerParam.setReadWidth("650");
	    	editerParam.setToolBarHidden("attachFile");
		    out.print(CommonUtil.getEditorScript(editerParam));
		}
%>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("목록", "goContentsList()", "");</script>&nbsp;
<script language=javascript>Button3("수정", "submitCheck()", "");</script></td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>

<%@include file="../common/footer.jsp" %>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->