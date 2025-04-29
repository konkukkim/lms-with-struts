<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumInfoDTO"%> 
<%@ page import = "com.edutrack.courseforum.dao.CourseForumUserDAO"%>
<%@include file="../common/course_header.jsp" %>
<%
   String pCourseId = (String)model.get("pCourseId");
   String pForumId  = (String)model.get("pForumId");

   int	pMySubForumId = 0;
   if(USERTYPE.equals("S")){
   	pMySubForumId	= Integer.parseInt((String)model.get("pMySubForumId"));
   }
   
   int 	curPage	= Integer.parseInt((String)model.get("curPage"));
   
   CourseForumInfoDTO courseForumInfo = (CourseForumInfoDTO)model.get("courseForumInfo"); 
   String pForumType        = courseForumInfo.getForumType();
   int 	 pParentForumId     = courseForumInfo.getParentForumId();
   int 	 pSubTeamNo         = courseForumInfo.getSubTeamNo();
   String pSubject          = courseForumInfo.getSubject();
   String pContents         = courseForumInfo.getContents();
   String pRfileName        = courseForumInfo.getRfileName();
   String pSfileName        = courseForumInfo.getSfileName();
   String pFilePath         = courseForumInfo.getFilePath();
   String pFileSize         = courseForumInfo.getFileSize();
   int    pForumScore    = courseForumInfo.getForumScore();
   String pStartDate        = courseForumInfo.getStartDate();
   String pEndDate          = courseForumInfo.getEndDate();
   String pOpenYn           = courseForumInfo.getOpenYn();
   
   String	sForumType      =	pForumType.equals("A")? " 전체토론" : "조별토론";
   String	sOpenYn         =	pOpenYn.equals("Y")? " 공개 "	:	" 비공개 ";
   
%>
<Script Language="javascript">
   function goList(){
      var f = document.Input;  
      var pCourseId	= f.pCourseId.value; 
      var pForumId	= f.pForumId.value; 
      document.location = "<%=CONTEXTPATH%>/CourseForumInfo.cmd?cmd=courseForumInfoPagingList&curPage=<%=curPage%>&pCourseId="+pCourseId+"&pForumId="+pForumId;
   }
   function goForum(pForumId){
      var f = document.Input;  
      var pCourseId	= f.pCourseId.value; 
      document.location = "<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsPagingList&pConnectYN=Y&pCourseId="+pCourseId+"&pForumId="+pForumId;
   }
   function goScore(pForumId){
      var f = document.Input;  
      var pCourseId	= f.pCourseId.value; 
      document.location = "<%=CONTEXTPATH%>/CourseForumInfo.cmd?cmd=courseForumScoreWrite&pCourseId="+pCourseId+"&pForumId="+pForumId;
   }   
   
   function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;        
       hiddenFrame.document.location = loc;
    }
</Script>


								<tr valign="top"> 
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"토론방")%></b></font></td>
									<!-- // classroom title -->
									<!-- history -->
									<td class="c_stit_history" valign="bottom" align="right" width="327"> 
									<!-- // history -->
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
                						<!-- form start -->
                						<form name="Input" method="post" action="<%=CONTEXTPATH%>/CourseForumInfo.cmd?cmd=">
                						<input type="hidden" name="pCourseId" value="<%=pCourseId%>">
                						<input type="hidden" name="pForumId" value="<%=pForumId%>">								
    									<table width="670" align="center">
										<tr class="s_tab01">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td class="s_tab_view01" width="100">주제</td>
											<td class="s_tab_view02" width="570" colspan=3><%=StringUtil.getHtmlContents(pSubject)%></td>
										</tr>
										<tr class="s_tab03">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td class="s_tab_view01" width="100">배점</td>
											<td class="s_tab_view02" width="235"><%=pForumScore%> 점</td>
											<td class="s_tab_view01" width="100">첨부파일</td>
											<td class="s_tab_view02" width="235"><%	out.print("<a href=\"javascript:fileDownload('"+pRfileName+"','"+pSfileName+"','"+pFilePath+"','"+pFileSize+"');\">"+pRfileName+"</a>");	%></td>
										</tr>
										<tr class="s_tab03">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td class="s_tab_view01" width="100">토론종류</td>
											<td class="s_tab_view02" width="235"><%=sForumType%></td>
											<td class="s_tab_view01" width="100">공개여부</td>
											<td class="s_tab_view02" width="235"><%=sOpenYn%></td>
										</tr>
										<tr class="s_tab03">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td class="s_tab_view01" width="120">토론기간</td>
											<td class="s_tab_view02" width="550" colspan=3><%	out.print(DateTimeUtil.getDateType(1,pStartDate) + " - " + DateTimeUtil.getDateType(1,pEndDate));	%></td>
										</tr>
										<tr class="s_tab03">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td class="s_tab_view01" width="120">토론개요</td>
											<td class="s_tab_view02" width="550" colspan=3><%=pContents%></td>
										</tr>
										<tr class="s_tab05">
											<td colspan="4"></td>
										</tr>
										</table>
										<br>	
										<!-- 내용 -->
    									<table width="670" align="center">
										<tr>
											<td align="right" valing=top>
<%	if (pForumType.equals("A")) {	%>
<%		if (USERTYPE.equals("M") || USERTYPE.equals("C") || USERTYPE.equals("P")) {	%>
												<Script>Button3("채점하기","goScore('<%=pForumId%>')","")</Script>
<%		}	%>
												<Script>Button3("참여하기","goForum('<%=pForumId%>')","")</Script>
<%	} %>
												<Script>Button3("목록","goList()","")</Script>
											</td>
										</tr>
									    </table>
<%	if (pForumType.equals("T")) {	%>
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
											<tr>
												<td colspan="5" class="s_tabtit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" width="18" height="16" align="absmiddle"> 토론리스트</td>
											</tr>
										</table>
										<table width="670" align="center">
                						<%
                						String tmpTxt = "";
                						
                                        //이미 등록되어 있는 서브토론그룹리스트
                            			ArrayList list = (ArrayList)model.get("courseFourmInfoList");
                            			for(int i=0; i < list.size(); i++){
                            			CourseForumInfoDTO courseForumInfoList = (CourseForumInfoDTO)list.get(i);
                            			%>
                    					<tr class="s_tab05">
											<td colspan="20"></td>
										</tr>
                                        <tr>
                                            <td class="s_tab_view01" width="120">토론팀명</td>
                                            <td class="s_tab_view02" width="350">
                        					    <strong><%=courseForumInfoList.getSubTeamName()%><%=(pMySubForumId == courseForumInfoList.getForumId()) ? "&nbsp;&nbsp;[참여가능]" : "" %></strong>
                    					    </td>
                    					    <td class="s_tab04" align=right  width="200">
                                            <%  if (USERTYPE.equals("M") ||  USERTYPE.equals("C") || USERTYPE.equals("P")) {	%>
                                                    <Script>Button5("채점하기","goScore('<%=courseForumInfoList.getForumId()%>')","")</Script>
                                            <%	}
                                            			 	
                                            if ( USERTYPE.equals("M") || USERTYPE.equals("C") || USERTYPE.equals("P") || 
                                               ( USERTYPE.equals("S") && pOpenYn.equals("Y") ) || pMySubForumId == courseForumInfoList.getForumId() ) {
                                                    tmpTxt = "참여하기" ;
                                            }
                                            if ( USERTYPE.equals("S") && pMySubForumId != courseForumInfoList.getForumId() ) {
                                                    tmpTxt = "둘러보기" ;
                                            }
                                            %>
                                            <%            
                                            if ( USERTYPE.equals("M") || USERTYPE.equals("C") || USERTYPE.equals("P") || ( USERTYPE.equals("S") && pOpenYn.equals("Y") )  ) {
                                            %>
                                                <Script>Button5("<%=tmpTxt %>","goForum('<%=courseForumInfoList.getForumId()%>')","")</Script>
                                            <% } %>
                                            </td>
                                        </tr>
										<tr class="s_tab03">
											<td colspan="20"></td>
										</tr>
                    					<tr>
                                            <td class="s_tab_view01">제목</td>
                                            <td class="s_tab_view02" colspan=2><%=courseForumInfoList.getSubject()%></td>
                                        </tr>
										<tr class="s_tab03">
											<td colspan="20"></td>
										</tr>
                                        <tr>
                                            <td class="s_tab_view01">토론개요</td>
                                            <td class="s_tab04" colspan=2><%=courseForumInfoList.getContents()%></td>
                                        </tr>
										<tr class="s_tab03">
											<td colspan="20"></td>
										</tr>
										<tr>
											<td colspan="20" height=20></td>
										</tr>
										<% } // end if	%>	
										</table>
<%			}	%>	

									<!-- // 내용 -->
								</td>
							</tr>
						</table>
					</td>
					<!-- // 본문 -->
				  
      

<%@include file="../common/course_footer.jsp" %>