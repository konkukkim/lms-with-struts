<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%>
<%@include file="../common/header.jsp" %>
<%
	String pSec				= (String)model.get("pSec");
	String pMode			= (String)model.get("pMode");
	String pRegMode 		= (String)model.get("pRegMode");
	String pBbsType 		= (String)model.get("pBbsType");
	String pBbsId 			= (String)model.get("pBbsId");
	String pFileUseYn 		= (String)model.get("pFileUseYn");
	int pFileCount 			= Integer.parseInt((String)model.get("pFileCount"));
	String pEditorYn 		= (String)model.get("pEditorYn");
	String pBadWordUseYn	= (String)model.get("pBadWordUseYn");
	String pBadWord 		= (String)model.get("pBadWord");

	String pBbsNo  			= "";
	String pDepthNo  		= "";
	String pOrderNo  		= "";
	String pParentNo  		= "";
	String	pContentsType	= "S";

	if (pRegMode.equals("Reply")) {
		pBbsNo			= (String)model.get("pBbsNo");
		pDepthNo		= (String)model.get("pDepthNo");
		pOrderNo  		= (String)model.get("pOrderNo");
		pParentNo  		= (String)model.get("pParentNo");
		pContentsType	= (String)model.get("pContentsType");
	}

	String sContentsType = pContentsType.equals("S") ? "일반" : "공지";

    boolean bHelp = pMode.equals("Help") ? true : false ;
    boolean bEnroll = pMode.equals("Enroll") ? true : false  ;

	String titleImg = null;

	if (pBbsId.equals("1")) {
		titleImg = "tit10_01.gif";
	} else if (pBbsId.equals("2")) {
		titleImg = "tit10_05.gif";
	} else {
		titleImg = "mtit_10.gif";
	}
	
	String[]	randomStr	=	(String[])model.get("RandomStr");
	String		inputStr	=	"";
	for(int i=0; i<randomStr.length; i++) {
		inputStr += randomStr[i];
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
	<%if(pBadWordUseYn.equals("Y") && !pBadWord.equals("")){%>
	//유해어 체크
    function CheckBkWord(SearchStr, field)
    {
      	var bklist = "<%=pBadWord%>";
      	var currentstring = "";

      	for( var i = 0; i < bklist.length; i++ ) {
          thischar = bklist.charAt( i );
          if( thischar == ',' ){
          	index = SearchStr.indexOf( currentstring,0 );
    		    if( index > -1 ) {
    		        alert( field+"에 유해어 '"+ currentstring +"'가 포함되어 있습니다. \r\n" +
    		               "고운말을 써 주세요.");
    		        return  false ;
    		    }
    		    currentstring = "";
          }else{
          	currentstring += thischar;
          }
        }

        return  true ;
    }
	<%}%>
	function submitCheck()
	{
		<%if(pEditorYn.equals("Y")){%>
		/*  WEAS 삽입 스크립트3(시작) */
		if(window.VBN_prepareSubmit != null){if(!VBN_prepareSubmit()) return false;}
		/* WEAS 삽입 스크립트3(끝) */
		<%}%>
		var f = document.Input;
		
	<%if(pBadWordUseYn.equals("Y") && !pBadWord.equals("")){%>
		 if (CheckBkWord(f.pSubject.value,'제목') == false){
        	f.pSubject.focus();
        	return false;
         }
         if (CheckBkWord(f.pContents.value,'내용') == false){
        	return false;
         }
	<%}%>
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
	   }
	   <%}else{%>
			if(!validate(f))
		      return false;
	   <%}%>
	   
<%	//-- 로그인 하지않고 작성하는 글일 경우 도배방지를 위해서 숫자를 입력하는 창을 둔다.
		if(UserBroker.getUserId(request).equals("")) {	%>
		var inputStr = f.inputStr.value;
	   	if(inputStr != f.needBasicStr.value) {
			alert('도배방지 텍스트를 정확하게 입력해 주십시오.');
			f.needBasicStr.focus();
			return false;
		}
		<% } %>
		
		f.action = "<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsRegist&pRegMode=<%=pRegMode%>&pBbsId=<%=pBbsId%>&pSec=<%=pSec%>&pMode=<%=PMODE%>";
		f.submit();
		$("registButtonDiv").style.display = "none";
		$("listButtonDiv").style.display = "block";
	}
	function goList(){
<%
		if (pSec.equals("site") || pSec.equals("total")) {
%>
			document.location.href="<%=CONTEXTPATH%>/BbsManagement.cmd?cmd=bbsContentsPagingList&pBbsId=<%=pBbsId%>&pSec=<%=pSec%>&pMode=<%=PMODE%>&MENUNO=<%=StringUtil.nvl(model.get("MENUNO"))%>";
<%
		} else {
%>
			document.location.href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=<%=pBbsId%>&pSec=<%=pSec%>&pMode=<%=PMODE%>&MENUNO=<%=StringUtil.nvl(model.get("MENUNO"))%>";
<%
		}
%>
	}
</script>
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start  onSubmit="return submitCheck()" -->
<form name=Input method="post" enctype="multipart/form-data">
<input type="hidden" name="pMode"       value="<%=pMode %>">
<input type="hidden" name="pBbsNo"      value="<%=pBbsNo%>">
<input type="hidden" name="pDepthNo"    value="<%=pDepthNo%>">
<input type="hidden" name="pOrderNo"    value="<%=pOrderNo%>">
<input type="hidden" name="pParentNo"   value="<%=pParentNo%>">
<input type="hidden" name="MENUNO" 	value="<%=StringUtil.nvl(model.get("MENUNO"))%>">
<input type="hidden" name="MainMenu" 	value="<%=StringUtil.nvl(model.get("MainMenu"))%>">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="100">제목</td>
												<td class="s_tab_view02" width="570"><input type="text" name="pSubject" size="70" maxlength=100 notNull  dispName="제목" dataType="text" lenCheck="180" value=""></td>
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
		if(pBbsType.equals("notice")) {
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
            out.print("<input type='hidden' name='pContentsType' value='S'>");
		} else {
        	if(pRegMode.equals("Reply")){
            	out.println(sContentsType );
                out.println("<input type='hidden' name='pContentsType' value=\""+pContentsType+"\">");
            } else {
                out.println("<input type=radio name=\"pContentsType\" value=\"N\" class=\"solid0\"> 공지 &nbsp;");
                out.println("<input type=radio name=\"pContentsType\" value=\"S\" class=\"solid0\" checked> 일반 ");
            }
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
												<td class="s_tab_view02"><input type="text" name="pRegName" size="15" maxlength=30 notNull  dispName="등록자이름" dataType="text" lenCheck="60" value=""></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" >비밀번호</td>
												<td class="s_tab_view02"><input type="password" name="pRegPasswd" size="15" maxlength=20 notNull  dispName="비밀번호" dataType="text" lenCheck="20" value=""></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" >이메일</td>
												<td class="s_tab_view02"><input type="text" name="pRegEmail" size="60" dispName="이메일" dataType="text" lenCheck="100" value=""></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
	}
	if (pFileUseYn.equals("Y")) {
    	for (int i=1;i<=pFileCount;i++) {
%>
											<tr>
												<td class="s_tab_view01">첨부 <%=i%></td>
												<td class="s_tab_view02"><input name="pFile[<%=i%>]" type="file" size="70" id="pFile[<%=i%>]"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
		}
	}
%>
											<tr>
												<td class="s_tab_view01"  colspan="2">내용</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
												<td class="s_tab_view03" colspan="2"><textarea name="pContents" rows=15 wrap="VIRTUAL" cols="90" dispName="내용"></textarea>
<%
	if(pEditorYn.equals("Y")) {
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
<%	//-- 로그인 하지않고 작성하는 글일 경우 도배방지를 위해서 숫자를 입력하는 창을 둔다.
	if(UserBroker.getUserId(request).equals("")) {	%>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td align="center"><input type="text" name="inputStr" value="<%=inputStr%>" size="6" style="border:0px;font-weight:bold;" readOnly></td>
												<td class="s_tab_view02"><input type="text" name="needBasicStr" value="" notNull  dispName="도배방지 텍스트" dataType="text" size="15" lenCheck="5" maxlength='5'>
													* 도배방지를 위한 텍스트 코드를 입력해 주세요. </td>
											</tr>
											
<%	}	%>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<div id="registButtonDiv" style="display:block"><script language=javascript>Button3("등록", "submitCheck()", "");</script>&nbsp;<script language=javascript>Button3("목록", "goList()", "");</script></div>
<div id="listButtonDiv" style="display:none"><script language=javascript>Button3("목록", "goList()", "");</script></div>

												</td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
</form>
							</table>
<%@include file="../common/footer.jsp" %>
<%if(pEditorYn.equals("Y")){%>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->

<%}%>
