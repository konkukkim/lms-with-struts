<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumInfoDTO"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumContentsDTO"%> 
<%@include file="../common/course_header.jsp" %>
<% 
    String pMode =  (String)model.get("pMode"); 
    String pCourseId = (String)model.get("pCourseId");
    String pForumId = (String)model.get("pForumId");
    
    String ckWrite = "N";
    ckWrite	= (String)model.get("ckWrite");
    CourseForumInfoDTO courseForumInfo = (CourseForumInfoDTO)model.get("courseForumInfo"); 
    //String pVoiceYn			= courseForumInfo.getVoiceYn();
    //String pEditorYn			= courseForumInfo.getEditorYn();
    //String pCommentUseYn		= courseForumInfo.getCommentUseYn();
    //String pEmoticonUseYn	= courseForumInfo.getEmoticonUseYn();
    //String pViewThreadYn		= courseForumInfo.getViewThreadYn();
    //String pViewPrevNextYn	= courseForumInfo.getViewPrevNextYn();   
    String rFileName        = courseForumInfo.getRfileName();
    String sFileName        = courseForumInfo.getSfileName();
    String FilePath         = courseForumInfo.getFilePath();
    String FileSize         = courseForumInfo.getFileSize();
    String pInfoSubject		= courseForumInfo.getSubject();
    String pInfoContents    = courseForumInfo.getContents();
    String pStartDate		= courseForumInfo.getStartDate();
    String pEndDate			= courseForumInfo.getEndDate();
    
   int	StartDate			= 	Integer.parseInt(pStartDate.substring(0,8));
   int	EndDate				= 	Integer.parseInt(pEndDate.substring(0,8));
   int	NowDate				=	Integer.parseInt(DateTimeUtil.getDate());   
     
   boolean chkLogin = true;
   
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CourseForumContentsWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/course_forum/courseForumContents.js"></script>
<Script Language=javascript>

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
<form name="f" method="post" action="<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsPagingList" >
<input type="hidden" name="curPage"     value="">
<input type="hidden" name="pCourseId"   value="<%=pCourseId%>">
<input type="hidden" name="pForumId"    value="<%=pForumId%>">									
    									<table width="670" align="center">
										<tr class="s_tab01">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td class="s_tab_view01" width="120">주제</td>
											<td class="s_tab_view02" width="550" colspan=3><%=pInfoSubject %></td>
										</tr>
										<tr class="s_tab03">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td class="s_tab_view01">토론개요</td>
											<td class="s_tab_view02"  colspan=3><%=StringUtil.getHtmlContents(pInfoContents) %></td>
										</tr>
										<tr class="s_tab03">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td class="s_tab_view01">첨부파일</td>
											<td class="s_tab_view02"  colspan=3>
                                            <%	if (!("").equals(sFileName)) {
                                                	out.print("<a href=\"javascript:fileDownload('"+rFileName+"','"+sFileName+"','"+FilePath+"','"+FileSize+"');\">"+rFileName+"</a>");
                                                }	%>
                                            </td>
										</tr>
										<tr class="s_tab05">
											<td colspan="4"></td>
										</tr>
										</table>
										<br>	
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
                						<tr class="s_tab01">
											<td colspan="20"></td>
										</tr>
										<tr class="s_tab02">
											<td width="070">번호</td>
											<td class="s_tablien"></td>
											<td width="330" >제목</td>
											<td class="s_tablien"></td>
											<td width="100" >등록자</td>
											<td class="s_tablien"></td>
											<td width="100" >등록일</td>
											<td class="s_tablien"></td>
											<td width="070" >조회수</td>
										</tr>
										<tr class="s_tab03">
											<td colspan="20"></td>
										</tr>
									    <!-- 리스트 -->
									    <tr>
											<td colspan="20"><div id="courseForumContentsList" style="width:100%;display:none"></div></td>
										</tr>
                        			  	<!-- 리스트 -->	
										<!--tr>
											<td colspan="20" height=25>
											    <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_ico01.gif" align="absmiddle" width="11" height="17" border="0">
											    &nbsp;<b>제목부분</b>에 마우스를 갖다 대시면 간략한 내용이 보여집니다.
											    
											</td>
										</tr-->
                        			  	<!-- 페이지 리스트 -->
                        			  	<tr>
                        			  	    <td class="s_list_btn" colspan="9" height="30" align="right">
                        			  	        <Script Language=Javascript>Button3('토론목록','goForumList()','')</Script>
<%	if (USERTYPE.equals("M") || USERTYPE.equals("C") || USERTYPE.equals("P") || (ckWrite.equals("Y") && USERTYPE.equals("S") &&  StartDate <= NowDate && NowDate <= EndDate )) {	%>											
                        			  	        <Script Language=Javascript>Button3('글쓰기','goAdd()','')</Script>
<%	}	%>												
											</td>
										</tr>	
                        			  	<tr>
											<td colspan="11" align=center>
												<table valign=top height="25">
													<tr>
														<td>
															<div id="getPagging" style="width:100%;display:none"></div>
														</td>
													</tr>
												</table>
												<table>
													<tr>
														<td align=middle height=30>
														    <select name="pSearchKey">
            				                                    <option value="subject" >제목</option>
            				                                    <option value="keyword" >내용</option>
            				                                </select>
														    <input maxlength=30 size=22 name=pKeyWord value="">
														    <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_search.gif" width=48 height=20 align=absmiddle Style="cursor:hand" onClick="javascript:goSearchList();"> 
														</td>
													</tr>
												</table>
											</td>
										</tr>
                                        </form>
										<!-- // 페이지 리스트 -->
									</table>
									<!-- // 게시판 리스트  끝 -->
									<!-- // 내용 -->
								</td>
							</tr>
						</table>
					</td>
					<!-- // 본문 -->

<Script Language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pCourseId%>','<%=pForumId %>');
</Script>

<%@include file="../common/course_footer.jsp" %>
