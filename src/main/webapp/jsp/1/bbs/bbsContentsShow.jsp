<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.bbs.dto.BbsContentsDTO"%>
<%@include file="../common/header.jsp" %>
<%
	String 	pSec			 = StringUtil.nvl(model.get("pSec"));
    String 	pMode            = StringUtil.nvl(model.get("pMode"));
    String 	curPage          = StringUtil.nvl(model.get("curPage"));
    String 	pBbsType         = StringUtil.nvl(model.get("pBbsType"));
    String 	pBbsId           = StringUtil.nvl(model.get("pBbsId"));
    String 	pFileUseYn       = StringUtil.nvl(model.get("pFileUseYn"));
    int 	pFileCount       = StringUtil.nvl(model.get("pFileCount"), 0);
    String 	pLoginChkYn      = StringUtil.nvl(model.get("pLoginChkYn"));
    String 	pCommentUseYn    = StringUtil.nvl(model.get("pCommentUseYn"));
    String 	pEmoticonUseYn   = StringUtil.nvl(model.get("pEmoticonUseYn"));
    String 	pViewThreadYn    = StringUtil.nvl(model.get("pViewThreadYn"));
    String 	pViewPrevNextYn  = StringUtil.nvl(model.get("pViewPrevNextYn"));
    int    	pNewTime         = StringUtil.nvl(model.get("pNewTime"), 0);
    int    	pHotChk          = StringUtil.nvl(model.get("pHotChk"), 0);

	BbsContentsDTO contentsDto= (BbsContentsDTO)model.get("contentsDto");
	String userId       = UserBroker.getUserId(request);
	String userType     = UserBroker.getUserType(request);
	String regId        = StringUtil.nvl(contentsDto.getRegId());
	String editorType   = StringUtil.nvl(contentsDto.getEditorType());
	String regPasswd    = StringUtil.nvl(contentsDto.getRegPasswd());
	String bbsHot       = 	"";
	String bbsNew       =	"";

	String bbsTitleImg	=	"";
    String img_path     =  CONTEXTPATH+"/img/"+SYSTEMCODE ;
	String bbsMainImg   = img_path+"/title/academy_m08.gif" ;//"help_s05_cen01.gif";

    if (pMode.equals("MyPage"))  bbsMainImg = img_path+"/title/academy_m08.gif" ;
    else if (pMode.equals("Enroll")) bbsMainImg = img_path+"/title/process04.gif" ;


	String chkRegPass = "T";
	if(regPasswd.equals("")) chkRegPass= "F";

	int pPrevSeqNo = 0;
	String pPrevSubject = "";
	int pNextSeqNo = 0;
	String pNextSubject = "";
	if(pViewPrevNextYn.equals("Y")){
		 pPrevSeqNo = Integer.parseInt(StringUtil.nvl(model.get("pPrevSeqNo")));
		 pPrevSubject = StringUtil.nvl(model.get("pPrevSubject"));
		 pNextSeqNo = Integer.parseInt(StringUtil.nvl(model.get("pNextSeqNo")));
		 pNextSubject = StringUtil.nvl(model.get("pNextSubject"));
	}
	int listCnt = 1;
	if(pViewThreadYn.equals("Y")){
		listCnt = Integer.parseInt(StringUtil.nvl(model.get("listCnt")));
	}

	int pChildCnt = Integer.parseInt(StringUtil.nvl(model.get("pChildCnt")));

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
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/BbsContentsListWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/BbsCommentWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/bbs/bbsComment.js"></script>
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
		var f = document.Input;

	 	if(!validate(f))
        	return false;
        else
            return true;

	}

	function goDel(){
		var f = document.Input;
		var commCnt = (f !=null ? f.commentCount.value : 0 );

		if(<%=pChildCnt%> > 0 || (commCnt != "" && commCnt > 0)) {
			alert('하위글 또는 코멘트가 있어서 삭제 하실 수 없습니다.');
		} else {
<%
			if (userType.equals("M") || chkRegPass.equals("F")) { //-- 로그인 필요한 경우
				if (userId.equals("")) {
%>
					alert('로그인을 먼저 해 주세요');
<%
				} else if (!userId.equals(regId) && !userType.equals("M")) {
%>
					alert('등록자가 아니면 삭제하실 수 없습니다.');
<%
				} else {
%>
					var stat = confirm("게시글을 삭제하시겠습니까?");
					if (stat == true)
						document.location.href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsDelete&pBbsId=<%=pBbsId%>&pSec=<%=pSec%>&pMode=<%=pMode%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>";
<%
				}
			} else { /* 로그인 필요없는경우 */
%>
				//var url = "<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPassChkForm&pBbsId=<%=pBbsId%>&pSec=<%=pSec%>&pMode=<%=pMode%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>&pWorkMode=Del";
				//window.open(url,"pass_win","top=100,left=162,toolbar=0,scrollbars=yes,directories=0,status=0,menubar=0,width=300,height=200,resizable=yes");
				enterPasswd('Del');
<%
			}
%>
		}
	}

	function goEdit(){
<%
		if (editorType.equals("V")) {
%>
			alert('음성 게시글은 수정 할 수 없습니다.');
<%
		} else if (pLoginChkYn.equals("Y") || !regId.equals("")) {
			if (userId.equals("")) {
%>
				alert('로그인을 먼저 해 주세요');
<%
			} else if (!userId.equals(regId) && !userType.equals("M")) {
%>
				alert('등록자가 아니면 수정하실 수 없습니다.');
<%
			} else {
%>
				document.location.href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsEdit&pBbsId=<%=pBbsId%>&pSec=<%=pSec%>&pMode=<%=pMode%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>";
<%
			}
		} else {
%>
			//var url = "<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPassChkForm&pBbsId=<%=pBbsId%>&pSec=<%=pSec%>&pMode=<%=pMode%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>&pWorkMode=Edit";
			//window.open(url,"pass_win","top=100,left=162,toolbar=0,scrollbars=yes,directories=0,status=0,menubar=0,width=300,height=200,resizable=yes");
			enterPasswd('Edit');
<%
		}
%>
	}


	function goReply(){
<%
		if (pLoginChkYn.equals("Y") && userId.equals("")) {
%>
			alert('로그인을 먼저 해 주세요');
<%
		} else {
			if (editorType.equals("V") && !userId.equals("")) {
%>
				var chkRe = confirm("음성게시물로 답변 하시겠습니까?");
				if(chkRe)
					document.location.href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsVoiceWrite&pBbsId=<%=pBbsId%>&pMode=<%=pMode%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>";
				else
					document.location.href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsWrite&pBbsId=<%=pBbsId%>&pSec=<%=pSec%>&pMode=<%=pMode%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>";
<%
			} else {
%>
				document.location.href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsWrite&pBbsId=<%=pBbsId%>&pSec=<%=pSec%>&pMode=<%=pMode%>&pSeqNo=<%=contentsDto.getSeqNo()%>&curPage=<%=curPage%>";
<%
			}
		}
%>
	}


	function goList(){
<%
		if (pSec.equals("site") || pSec.equals("total")) {
%>
			document.location.href="<%=CONTEXTPATH%>/BbsManagement.cmd?cmd=bbsContentsPagingList&pBbsId=<%=pBbsId%>&pSec=<%=pSec%>&MENUNO=0&pMode=<%=pMode%>";
<%
		} else {
%>
			document.location.href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsPagingList&pBbsId=<%=pBbsId%>&MENUNO=<%=MENUNO%>&pMode=<%=pMode%>&pSec=<%=pSec%>&curPage=<%=curPage%>";
<%
		}
%>
	}

	function fileDownload(rfilename, sfilename, filepath, filesize){
       hiddenFrame.location.href = "<%=CONTEXTPATH%>/fileDownServlet?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
    }

</Script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
<!-- ADD 2012-11 link to SNS  (BEGIN) -------------------------------------->
<%
//if (userId.equals("clu001") || request.getRemoteAddr().equals("222.110.245.221") ){
%>
<!-- LINKS to SNS (twitter, facebook, ...) -->

<div id="short_url"> </div>

<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	var snsTitle  = "<%=StringUtil.getHtmlContents(StringUtil.nvl(contentsDto.getSubject()))%>";

	// MENUNO... : 목록URL 에 있는 값을 설정해줘야 볼 수 있다
	var snsUrl    = location.href + "&MENUNO=0&MainMenu=Y";
	var maxLength = 140 - (snsUrl.length + 1);

	if (snsTitle.length > maxLength) {
	snsTitle = snsTitle.substr(0, (maxLength - 3))+'...';
	}
	var snsLink = 'http://twitter.com/home?status='+encodeURIComponent(snsTitle + ' ' + snsUrl);
	
	msg = snsTitle;
	url = snsUrl;



	function goTwitter() {
	 var href = "http://twitter.com/home?status=" + encodeURIComponent(msg) + " " + encodeURIComponent(url);
	 var a = window.open(href, 'twitter', '');
	 if ( a ) {
	  a.focus();
	 }
	}

	function goPopFaceBook(msg,url) {
	 var href = "http://www.facebook.com/sharer.php?u=" + url + "&t=" + encodeURIComponent(msg);
	 var a = window.open(href, 'facebook', '');
	 if ( a ) {
	  a.focus();
	 }
	}
	
	function goFaceBook() {
		// firefox 에서 '이 프레임만 보기' 모드에서는 에러난다
		//var longURL = encodeURIComponent(snsUrl);
		var longURL = snsUrl;
//alert(' longURL ==>' +  longURL ); 
	


	var accessToken = "4c7e6801d3eb1c360aeab43bbb4e8e5837aae5d0";



	var params = {
		"long_url" : longURL
	};

	$.ajax({
		url: "https://api-ssl.bitly.com/v4/shorten",
		cache: false,
		dataType: "json",
		method: "POST",
		contentType: "application/json",
		beforeSend: function (xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + accessToken);
		},
		data: JSON.stringify(params)
	}).done(function(data) {
		console.log(data);

		console.log("data.link", data.link);
		url = data.link;
			goPopFaceBook("MESSAGE", url);
			// for DEBUG jQuery("div#short_url").text(url);
	}).fail(function(data) {
		console.log(data);
	});
	
	  
	}
</script>

<a href="javascript:goTwitter()"> <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/twitter.gif"  alt="트위터" /></a> 
<a href="javascript:goFaceBook()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/facebook.gif" alt="페이스북" /></a> 

<br>

<%
//} // END if (userId.equals("clu001"))
%>
<!-- ADD 2012-11 link to SNS  (END) -------------------------------------->
										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">제목</td>
												<td class="s_tab_view02" width="377"><%=StringUtil.getHtmlContents(StringUtil.nvl(contentsDto.getSubject()))%></td>
												<td class="s_tab_view01" width="80">조회수</td>
												<td class="s_tab_view02" width="121"><%=contentsDto.getHitNo() %></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">작성자</td>
												<td class="s_tab_view02"><%=StringUtil.nvl(contentsDto.getRegName())%></td>
												<td class="s_tab_view01">등록일</td>
												<td class="s_tab_view02"><%=DateTimeUtil.getDateType(1,StringUtil.nvl(contentsDto.getRegDate()))%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%
	String	uccFileName01 	= 	"";
	String	uccFileName02 	= 	"";
	String	uccFileName03 	= 	"";
	int		uccCnt			=	0;

	if (pFileUseYn.equals("Y") && !editorType.equals("V")) {
%>
											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view03" colspan="3">
<%
		String rFileName = "";
		String sFileName = "";
		String FilePath = "";
		String FileSize = "";
		
		for (int i=1;i<=pFileCount;i++) {

			if(i == 1 && !StringUtil.nvl(contentsDto.getSfileName1()).equals("") ){
    			rFileName = StringUtil.nvl(contentsDto.getRfileName1());
    			sFileName = StringUtil.nvl(contentsDto.getSfileName1());
    			FilePath = StringUtil.nvl(contentsDto.getFilePath1());
    			FileSize = StringUtil.nvl(contentsDto.getFileSize1());
    			if(CommonUtil.getFileType(sFileName).equals("asf") || CommonUtil.getFileType(sFileName).equals("wmv") || CommonUtil.getFileType(sFileName).equals("wma")) {
    				uccFileName01	=	sFileName;
    				uccCnt++;
    			}
    			
    			out.print("<img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/ico_file.gif\" align='absmiddle'>");
    			out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>");
    			out.print("&nbsp;&nbsp;");
			}

			if(i == 2 && !StringUtil.nvl(contentsDto.getSfileName2()).equals("") ){
			    rFileName = StringUtil.nvl(contentsDto.getRfileName2());
			    sFileName = StringUtil.nvl(contentsDto.getSfileName2());
			    FilePath = StringUtil.nvl(contentsDto.getFilePath2());
			    FileSize = StringUtil.nvl(contentsDto.getFileSize2());
			    if(CommonUtil.getFileType(sFileName).equals("asf") || CommonUtil.getFileType(sFileName).equals("wmv") || CommonUtil.getFileType(sFileName).equals("wma")) {
    				uccFileName02	=	sFileName;
    				uccCnt++;
    			}
    			
			    out.print("<img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/ico_file.gif\" align='absmiddle'>");
			    out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>");
			    out.print("&nbsp;&nbsp;");
			}

			if(i == 3 && !StringUtil.nvl(contentsDto.getSfileName3()).equals("") ){
			    rFileName = StringUtil.nvl(contentsDto.getRfileName3());
			    sFileName = StringUtil.nvl(contentsDto.getSfileName3());
			    FilePath = StringUtil.nvl(contentsDto.getFilePath3());
			    FileSize = StringUtil.nvl(contentsDto.getFileSize3());
			    if(CommonUtil.getFileType(sFileName).equals("asf") || CommonUtil.getFileType(sFileName).equals("wmv") || CommonUtil.getFileType(sFileName).equals("wma")) {
    				uccFileName03	=	sFileName;
    				uccCnt++;
    			}
    			
			    out.print("<img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/ico_file.gif\" align='absmiddle'>");
			    out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>");
			    out.print("&nbsp;&nbsp;");
			}
		}
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%
	}
%>
											<tr>
												<td class="s_tab_view01"  colspan="4">내용</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view03"  colspan="4">
													<table width="100%" border="0" cellpadding="0" cellspacing="0">
<%
	//-- 세상 돌아가는 이야기 게시판일 경우 동영상이 보여지기 위해서...
	if(pBbsId.equals("2")) {
%>
<form name="uccForm">
	<input type="hidden" name="uccCnt" value="<%=uccCnt%>">
	<input type="hidden" name="uccFileName01" value="<%=uccFileName01%>">
	<input type="hidden" name="uccFileName02" value="<%=uccFileName02%>">
	<input type="hidden" name="uccFileName03" value="<%=uccFileName03%>">
	<input type="hidden" name="pUccIp" value="<%=StringUtil.nvl(model.get("pUccIp"))%>">
	<input type="hidden" name="pUccAlias" value="<%=StringUtil.nvl(model.get("pUccAlias"))%>">
	<input type="hidden" name="pUccProtocol" value="<%=StringUtil.nvl(model.get("pUccProtocol"))%>">
</form>
<%
		for (int j=1; j<=uccCnt; j++) {
			
			if(j==1 && !uccFileName01.equals("")) {
%>
														<tr>
															<td align="center"><div id="UccViewDiv1" style="width:100%;display:none;"></div></td>
														</tr>
<%
			}
			
			if(j==2 && !uccFileName02.equals("")) {
%>
														<tr>
															<td align="center"><div id="UccViewDiv2" style="width:100%;display:none;"></div></td>
														</tr>
<%
			}
			
			if(j==3 && !uccFileName03.equals("")) {
%>
														<tr>
															<td align="center"><div id="UccViewDiv3" style="width:100%;display:none;"></div></td>
														</tr>
<%
			}
			
		}//end for
	}
%>
														<tr>
															<td>
<%
	if (editorType.equals("W")) {
    	out.println( StringUtil.ReplaceAll(StringUtil.nvl(contentsDto.getContents()),"&quot;","\"")  );
    } else if (editorType.equals("H")) {
    	out.println( StringUtil.getHtmlContents(StringUtil.nvl(contentsDto.getContents()))   );
	}
%>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
<%	//if(UserBroker.getUserId(request).equals("") || USERID.equals("") || USERID.equals("null")) {	%>
<form name="contentsPasswdForm">
<input type="hidden" name="pPageMode" value="">
<input type='hidden' name ='pBbsId' value='<%=pBbsId%>'>
<input type='hidden' name ='pSeqNo' value='<%=contentsDto.getSeqNo()%>'>
<input type='hidden' name ='curPage' value='<%=curPage%>'>
											<tr>
												<td colspan="4" height="" valign="middle" align="right">
<div id="ContentsRegPassDiv" style="height:30px;display:none;">
<b>* 글 등록시 입력하신 비밀번호를 입력해 주세요.</b>
<input name="ContentsConfirmPasswd" type="password" size="12">&nbsp;<img 
src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bttn_confirm02.gif" align="absmiddle" border="0" 
onClick="confirmContentsPasswd()" style="cursor:hand;"></div>
												</td>
											</tr>
</form>
<%	//}	%>
<!-- 버튼들의 모임 공백주의!! -->
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align="right">
<% if(CommonUtil.getAuthorCheck(request,  "R"))/* 권한체크 */  { %><script language=javascript>Button3("목록", "goList()", "");</script><%	}	%>
<%	if (!UserBroker.getUserId(request).equals("")) { %>
<%		if (!pBbsType.equals("notice") && StringUtil.nvl(contentsDto.getContentsType()).equals("S")) {	%>
	<% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button3("답글", "goReply()", "");</script><%	}	%>
<%		}	%>
	<% if(CommonUtil.getAuthorCheck(request,  "U"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button3("수정", "goEdit()", "");</script><%	}	%>
	<% if(CommonUtil.getAuthorCheck(request,  "D"))/* 권한체크 */  { %>&nbsp;<script language=javascript>Button3("삭제", "goDel()", "");</script><%	}	%>
<%	} else {

		int bbsId	=	StringUtil.nvl(pBbsId, 0);
		if(pBbsId.equals("9") || (bbsId >= 10 && bbsId <= 16)) {
%>
	&nbsp;<script language=javascript>Button3("답글", "goReply()", "");</script>
	&nbsp;<script language=javascript>Button3("수정", "enterPasswd('Edit')", "");</script>
	&nbsp;<script language=javascript>Button3("삭제", "enterPasswd('Del')", "");</script>
<%		}
	}	%>
												</td>
											</tr>

<%
	if(pCommentUseYn.equals("Y")) {
%>
											<tr>
												<td colspan="4" height="10"></td>
											</tr>
											<!-- 댓글테이블 -->
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td colspan="4">
													<!-- 댓글 쓰기 -->
													<table width="100%" class="reply_form">
<!-- form start  action="<%=CONTEXTPATH%>/BbsComment.cmd?cmd=bbsCommentRegist" onSubmit="return submitCheck()" -->
<form name=Input method="post" >
<input type="hidden" name="pMode"  		value="<%=pMode	%>">
<input type="hidden" name="pBbsId" 		value="<%=pBbsId%>">
<input type="hidden" name="pSeqNo" 		value="<%=contentsDto.getSeqNo()%>">
<input type="hidden" name="curPage" 	value="<%=curPage%>">
<input type="hidden" name="pSec" 		value="<%=pSec%>">
<input type="hidden" name="pRegId"  	value="<%=userId%>">
<input type="hidden" name="pRegPasswd" 	value="">
<input type="hidden" name="pRegEmail" 	value="">
<input type="hidden" name="pEmoticon" 	value="">
<input type="hidden" name="curPageComment" value="">
<input type="hidden" name="commentCount"   value="">

<input type="hidden" name="userId"  value="<%=userId%>">
<input type="hidden" name="userType"  value="<%=userType%>">
<input type="hidden" name="regId"  value="<%=regId%>">

<input type="hidden" name="OriginalPasswd" value="">
<input type="hidden" name="CommentNum" value="">
														<tr>
															<td class="reply_w_icon">
																<table width="100%">
																	<tr>
																		<td width=32><a href="javascript:selectEmoticon('1')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_01.gif"></a></td>
																		<td width=32><a href="javascript:selectEmoticon('2')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_02.gif"></a></td>
																		<td width=32><a href="javascript:selectEmoticon('3')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_03.gif"></a></td>
																		<td width=32><a href="javascript:selectEmoticon('4')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_04.gif"></a></td>
																		<td width=32><a href="javascript:selectEmoticon('5')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_05.gif"></a></td>
																		<td width=32><a href="javascript:selectEmoticon('6')" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_06.gif"></a></td>
																		<td width=180 height="25" align="right"><!-- 선택된 표정 시작 --><div id="selectedEmoticon" style="width:100%;display:none"></div></td>
																		<td width="" align="right">
이름 : <input name="pRegName" type="text" size="12" notNull type="text" value="<%if(!USERID.equals("")){%><%=UserBroker.getUserName(request)%><%}%>" <%if(!USERID.equals("")){%>readOnly<%}%>>
<%if(USERID.equals("")){%>비밀번호 : <input name="pRegPass" type="password" size="12" notNull type="text" maxLength="20"><%}%>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
														<tr>
															<td class="reply_w_text01">
																<table width="100%" border="0" cellpadding="0" cellspacing="0" align="right">
																	<tr>
																		<td width="670"><textarea name="pContents" wrap=virtual dispName="답변내용" lenCheck="4000" notNull style="width:600px;height:48px"></textarea>
																<!--등록버튼-->&nbsp;<a href="javascript:addComment();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_record.gif" align="absmiddle"></a>
																		</td>
																	</tr>
																</table>
															</td>
														</tr>
<tr>
	<td>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" align="right">
			<tr>
				<td align="right"><div id="regPassDiv" style="display:none;">
					<b>* 댓글 등록시 입력하신 비밀번호를 입력해 주세요.</b>
					<input name="ConfirmPasswd" type="password" size="12">
					&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bttn_confirm02.gif" align="absmiddle" border="0" onClick="deleteComment()" style="cursor:hand;"></div>
				</td>
			</tr>
		</table>
	</td>
</tr>
</form>
<!-- form end -->
													</table>
													<table>
														<tr>
															<td height="5"=></td>
														<tr>
													</table>
													<!-- 댓글 내용 -->
<!-- 리스트 -->
	<div id="commentList" style="width:100%;display:none"></div>
<!-- 리스트 -->
													<table width="97%" border=0>
														<tr>
															<td align="right" class="reply_list">
<!-- 페이징 -->
	<div id="getPagging" style="width:100%;display:none"></div>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- // 댓글테이블 끝 -->
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
<%  //댓글 가능시 끝
	} %>

<%
	// 이전글, 다음글 표시 여부 허용일경우
    if (pViewPrevNextYn.equals("Y")) {
%>
											<tr>
												<td colspan="4" height="10"></td>
											</tr>
											<tr class="s_tab_lien01">
												<td colspan="4"></td>
											</tr>
<%
		// 이전글
    	if (pPrevSeqNo > 0) {
%>
											<tr>
												<td class="s_tab_view01">이전글</td>
												<td class="s_tab_view02" colspan="3"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsShow&pBbsId=<%=pBbsId%>&pSec=<%=pSec%>&pMode=<%=pMode%>&pSeqNo=<%=pPrevSeqNo%>&curPage=<%=curPage%>"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(pPrevSubject),50)%></a></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
<%
		}
		// 다음 글
		if (pNextSeqNo > 0) {
%>
											<tr>
												<td class="s_tab_view01">다음글</td>
												<td class="s_tab_view02" colspan="3"><a href="<%=CONTEXTPATH%>/BbsContents.cmd?cmd=bbsContentsShow&pBbsId=<%=pBbsId%>&pSec=<%=pSec%>&pMode=<%=pMode%>&pSeqNo=<%=pNextSeqNo%>&curPage=<%=curPage%>"><%=StringUtil.setMaxLength(StringUtil.getHtmlContents(pNextSubject),50)%></a></td>
											</tr>
											<tr class="s_tab_lien02">
												<td colspan="4"></td>
											</tr>
<%
		}
	}
%>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pBbsId%>','<%=contentsDto.getSeqNo()%>','<%=pCommentUseYn %>');
</script>
<%@include file="../common/footer.jsp" %>
