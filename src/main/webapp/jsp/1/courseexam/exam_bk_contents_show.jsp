<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.courseexam.dto.ExamBkContentsDTO"%> 
<%@include file="../common/popup_header.jsp" %>
<%		
    ExamBkContentsDTO contentsInfo = (ExamBkContentsDTO)model.get("contentsInfo");
    String pExamType = (String)model.get("pExamType");
    String[] width = new String[]{"100","300"};
    String LineBgColor = "#40B389";
%>	
<script language="javascript">
    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;        
       hiddenFrame.document.location = loc;
    }
</script>
						<table align="center" border="0" cellpadding="0" cellspacing="0" width="98%">
							<tr>
								<td width="100%" height="42" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/pop_title13.gif">&nbsp;</td>
							</tr>
						</table>
    <!-- contents start -->
      <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td height="10"></td>
        </tr>
        <tr align=right> 
          <td height="25">&nbsp;문제형식 :<%=CommonUtil.getContentsTypeName(pExamType)%></td>
        </tr>
        <tr> 
          <td>
            <table width="99%" border="0" cellspacing="0" cellpadding="0" align="center">
              <tr> 
                <td height="2" class="b_td01"></td>
              </tr>
              <tr> 
                <td height="4">
                   <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                    <tr> 
                      <td height="25" colspan="3" align="left" class="b_td02"><strong><font color=red>※</font>문제</strong></td>
                    </tr>
                    <tr bgcolor="D5D5D5"> 
                      <td width="<%=width[0]%>" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="<%=width[1]%>" height="1"></td>
                    </tr>
                    <tr>  
                      <td height="50" colspan="3" align="left"><%=StringUtil.ReplaceAll(contentsInfo.getContents(),"&quot;","\"")%></td>
                    </tr>
                    <tr bgcolor="D5D5D5"> 
                      <td width="<%=width[0]%>" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="<%=width[1]%>" height="1"></td>
                    </tr>
                    <tr> 
                      <td width="<%=width[0]%>" height="25"><strong>첨부파일</strong></td>
                      <td width="1" height="25"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="<%=width[1]%>" height="25">
                          <%if(!contentsInfo.getRfileName().equals("")){%>&nbsp;올린 파일 : 
							<a href="javascript:fileDownload('<%=contentsInfo.getRfileName()%>','<%=contentsInfo.getSfileName()%>','<%=contentsInfo.getFilePath()%>','<%=contentsInfo.getFileSize()%>');">
							<%=contentsInfo.getRfileName()%></a>
                          <%}%>
                      </td>
                    </tr>
                    <tr bgcolor="D5D5D5"> 
                      <td width="<%=width[0]%>" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="<%=width[1]%>" height="1"></td>
                    </tr>
                 <%if(pExamType.equals("K")){%>  
                    <tr> 
                      <td width="<%=width[0]%>" height="25"><strong>보기1</strong></td>
                      <td width="1" height="25"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="<%=width[1]%>" height="25"><%=contentsInfo.getExample1()%></td>
                    </tr>
                    <tr bgcolor="D5D5D5"> 
                      <td width="<%=width[0]%>" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="<%=width[1]%>" height="1"></td>
                    </tr>
                    <tr> 
                      <td width="<%=width[0]%>" height="25"><strong>보기2</strong></td>
                      <td width="1" height="25"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="<%=width[1]%>" height="25"><%=contentsInfo.getExample2()%></td>
                    </tr>
                    <tr bgcolor="D5D5D5"> 
                      <td width="<%=width[0]%>" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="<%=width[1]%>" height="1"></td>
                    </tr>
                    <tr> 
                      <td width="<%=width[0]%>" height="25"><strong>보기3</strong></td>
                      <td width="1" height="25"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="<%=width[1]%>" height="25"><%=contentsInfo.getExample3()%></td>
                    </tr>
                    <tr bgcolor="D5D5D5"> 
                      <td width="<%=width[0]%>" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="<%=width[1]%>" height="1"></td>
                    </tr>
                    <tr> 
                      <td width="<%=width[0]%>" height="25"><strong>보기4</strong></td>
                      <td width="1" height="25"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="<%=width[1]%>" height="25"><%=contentsInfo.getExample4()%></td>
                    </tr>
                    <tr bgcolor="D5D5D5"> 
                      <td width="<%=width[0]%>" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="<%=width[1]%>" height="1"></td>
                    </tr>
                                        <tr> 
                      <td width="<%=width[0]%>" height="25"><strong>보기5</strong></td>
                      <td width="1" height="25"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                      <td width="<%=width[1]%>" height="25"><%=contentsInfo.getExample5()%></td>
                    </tr>
                    <tr bgcolor="D5D5D5"> 
                      <td width="<%=width[0]%>" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="<%=width[1]%>" height="1"></td>
                    </tr>
				 <% } %>
                    <tr> 
                      <td height="25" colspan="3" align="left" class="b_td02"><strong><font color=red>※</font>정답</strong></td>
                    </tr>
                    <tr bgcolor="D5D5D5"> 
                      <td width="<%=width[0]%>" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="<%=width[1]%>" height="1"></td>
                    </tr>
				<%if(pExamType.equals("J")){%>
                    <tr> 
                      <td height="25" colspan="3" align="center"><%=contentsInfo.getAnswer()%></td>
                    </tr>
				 <%}else if(pExamType.equals("K")){ %>  
                    <tr> 
                      <td height="25" colspan="3">
                            <%=StringUtil.ReplaceAll(contentsInfo.getMultiAnswer(),"/",",")%>
                      </td>
                    </tr>
                 <%}else{%>  
                    <tr> 
                      <td height="25" colspan="3" align="center">
 						<%=contentsInfo.getAnswer()%>
                      </td>
                    </tr>
				 <%} %>	
                    <tr bgcolor="D5D5D5"> 
                      <td width="<%=width[0]%>" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="<%=width[1]%>" height="1"></td>
                    </tr>
                    <tr> 
                      <td height="25" colspan="3" align="left" class="b_td02"><strong><font color=red>※</font>답에 대한 해설</strong></td>
                    </tr>
                    <tr bgcolor="D5D5D5"> 
                      <td width="<%=width[0]%>" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="<%=width[1]%>" height="1"></td>
                    </tr>
                    <tr> 
                      <td height="25" colspan="3" align="center"><%=StringUtil.getHtmlContents(contentsInfo.getComment())%></td>
                    </tr>
                    <tr bgcolor="D5D5D5"> 
                      <td width="<%=width[0]%>" height="1"></td>
                      <td width="1" height="1"></td>
                      <td width="<%=width[1]%>" height="1"></td>
                    </tr>
                    </table>
                 </td>
              </tr>
              <tr> 
                <td height="2" class="b_td01"></td>
              </tr>
            </table>
            <!--write form end-->
          </td>
        </tr>
        <tr>
          <td height="4"></td>
        </tr>
        <tr> 
          <td align="right">
          &nbsp;<a href="javascript:window.close();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_co_close.gif" ></a></a>
          </td>
        </tr>
        <tr> 
          <td height="50">&nbsp;</td>
        </tr>
      </table> 
      <!-- contents end -->    
<%@include file="../common/popup_footer.jsp" %>
