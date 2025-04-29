<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.dto.CodeParam"%> 
<%@include file="../common/course_header.jsp" %>
<%  
	String pRegMode = (String)model.get("pRegMode");
	String pBbsType = (String)model.get("pBbsType");
	String pFileUseYn = (String)model.get("pFileUseYn");
	int pFileCount = Integer.parseInt((String)model.get("pFileCount"));
	String pEditorYn = (String)model.get("pEditorYn");
	String pBadWordUseYn = (String)model.get("pBadWordUseYn");
	String pBadWord = (String)model.get("pBadWord");
	String pBbsNo  = "";
	String pDepthNo  = "";
	String pOrderNo  = "";
	String pParNo  = "";
	String 	bbsTitleImg		=	"ma_notify.gif";
	String	pContentsType	=	"S";
	if(pRegMode.equals("Reply"))
	{
		pBbsNo  =(String)model.get("pBbsNo");
		pDepthNo  =(String)model.get("pDepthNo");
		pOrderNo  =(String)model.get("pOrderNo");
		pParNo  =(String)model.get("pParNo");
		pContentsType	=	(String)model.get("pContentsType");		
		
	}
	if(pBbsType.equals("notice")){
			bbsTitleImg 	= 	"cl_notify.gif"; 
	}else	if(pBbsType.equals("bbs")){
			bbsTitleImg 	= 	"cl_board.gif"; 
	}else	if(pBbsType.equals("qna")){
			bbsTitleImg 	= 	"cl_qa.gif"; 
	}else	if(pBbsType.equals("pds")){
			bbsTitleImg 	= 	"cl_data.gif"; 
	}
%>
<script language="javascript">
	function goSubmit(){
		if(VoiceBBS.SetSaveFile()){
			goList();
		}
	}
	function goList(){
		document.location.href="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsPagingList&pBbsType=<%=pBbsType%>";
	}
</script>
      <table width="538" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td height="10"></td>
        </tr>
        <tr> 
          <td><!-- title imgae -->
          	<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/title/<%=bbsTitleImg%>" >
          </td>
        </tr>
        <tr> 
          <td height="5"></td>
        </tr>
  
        <tr> 
          <td height="2" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/title/line_01.gif"></td>
        </tr>
        <tr> 
          <td height="15">&nbsp;</td>
        </tr>
 <% String titleImg = (String)model.get("titleImg"); 
	if(!titleImg.equals("")){%>
	<tr><td height=30 ><img src="<%=CONTEXTPATH%>/data/sys_img/<%=SYSTEMCODE%>/curri_bbs/<%=titleImg%>" border=0></td></tr>
<%  }%>   
         <tr> 
          <td height="10">&nbsp;</td>
        </tr>      
        <tr> 
          <td>        
<table width="538" border="0" cellspacing="0" cellpadding="0">
					<tr> 
                <td height="2" bgcolor="<%=LineBgColor%>"></td>
              </tr>
	 				<tr> 
                <td><table border=0 cellpadding=0 cellspacing=1 bgcolor="gray" width="100%">
							<tr>
								<td bgcolor="#ffffff" colspan=4>
								<object ID = "VoiceBBS" classid="clsid:9B161B48-3A5B-417D-B823-D8F17E37EBFE"
					    		codebase="/VoiceBbs/AxEduTrackWriter.ocx#version=1,0,0,0" width='538' height='400'>						
				<param name="ActionSrc" value="<%=CONTEXTPATH%>/CurriBbsContents.cmd?cmd=curriBbsContentsVoiceRegist&pRegMode=<%=pRegMode%>&pBbsType=<%=pBbsType%>&pSystemCode=<%=UserBroker.getSystemCode(request)%>&pUserId=<%=UserBroker.getUserId(request)%>&pUserName=<%=UserBroker.getUserName(request)%>&pCurriCode=<%=CURRICODE%>&pCurriYear=<%=CURRIYEAR%>&pCurriTerm=<%=CURRITERM%>">    
				<param name="FormDataString" value="pEditorType=V&pBbsNo=<%=pBbsNo%>&pDeptNo=<%=pDepthNo%>&pOrderNo=<%=pOrderNo%>&pParNo=<%=pParNo%>&pContentsType=<%=pContentsType%>">								
				<param name="TitleVarName" value="pSubject">      
				<param name="FileVarName" value="pFile[1]">      
				<param name="TargetFrame" value="">						
				<param name="MaxFileSize" value="">   
				
									
									</object>
								</td>
							</tr>
							</table>
						</td>
              </tr>
              <tr> 
                <td height="2" bgcolor="<%=LineBgColor%>"></td>
              </tr>
            </table>
            
            
            
             </td>
        </tr>
        <tr>
          <td height="4"></td>
        </tr>
        <tr> 
          <td align="right"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/list.gif" width="48" height="20" onClick="javascript:goList()" style="border:0;align:absmiddle;cursor:hand">&nbsp;<input type="image" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/reg_01.gif" width="48" height="20" style="border:0;align:absmiddle" onClick="javascript:goSubmit()"></td>
        </tr>
        <tr> 
          <td height="50">&nbsp;</td>
        </tr>
      </table> 
      </form>
      <!-- contents end -->
    </td>
<%@include file="../common/course_footer.jsp" %>