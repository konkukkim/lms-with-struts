<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.currisub.dto.CurriSubInfoDTO"%> 
<%@include file="../common/header.jsp" %>
<%
CurriSubInfoDTO	curriSubInfo	= new CurriSubInfoDTO();
curriSubInfo = (CurriSubInfoDTO)model.get("curriSubInfo");

// 단체 할인 정보 수 가져오기
int deptSaleCnt = Integer.parseInt((String)model.get("deptSaleCnt"));

String curriType = StringUtil.nvl(curriSubInfo.getCurriProperty2(),"R");

String tmpPrice = "";
if(curriSubInfo.getPrice() > 0)
	tmpPrice = StringUtil.getMoneyType(curriSubInfo.getPrice())+"원";
else
	tmpPrice = "무료";
%>

<SCRIPT LANGUAGE="JavaScript">
<!--
	
	function fileDownload(rfilename, sfilename, filepath, filesize){
       hiddenFrame.location.href = "<%=CONTEXTPATH%>/fileDownServlet?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;        //loc;
    }
    function submitCheck(f)
	{

		if(f.pFile.value == ''){
			alert('파일을 선택해 주세요');
			return false;
		}else{
			return true;
		}
		
	}
//-->
</SCRIPT>
                 </tr>
                <!-- main 시작 -->
                <tr> 
					<td height="410" align="left" valign="top" style="padding:0 10 0 15">



            <table width="100%" height="35" border="0" cellpadding="0" cellspacing="0" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/02_cen_titlebg.gif">
              <tr> 
                <td align="left" valign="middle" style="padding:0 0 0 8"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/02_centitle06.gif" align="absmiddle" border="0"></td>
              </tr>
            </table>

            <table width="100%" border="0" cellpadding="0" cellspacing="0">
<form name="Input" method="post" action="<%=CONTEXTPATH%>/Student.cmd?cmd=enrollDeptProcess2&pProperty1=<%=model.get("pProperty1")%>&pCurriCode=<%=model.get("pCurriCode")%>&pCurriYear=<%=model.get("pCurriYear")%>&pCurriTerm=<%=model.get("pCurriTerm")%>" 
enctype="multipart/form-data" onSubmit="return submitCheck(this)">              
<input type="hidden" name="pCurriCode value="<%=model.get("pCurriCode")%>">
<input type="hidden" name="pCurriYear" value="<%=model.get("pCurriYear")%>">
<input type="hidden" name="pCurriTerm" value="<%=model.get("pCurriTerm")%>">
<!-- 과정유형 ==> 정규과정 : R 상시과정 : O -->
<input type="hidden" name="pCurriType" value="<%=curriType%>">
              <tr>
                <td align="center" valign="top" class="padding:20 0 0 0">
				  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="45" align="left" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/02_cen_stitle04.gif" align="absmiddle" border="0"> 
                      </td>
                    </tr>
                  </table>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="center" valign="top" style="padding:10 0 20 0"> 
                        <table width="100%" border="0" cellpadding="0" cellspacing="2" bgcolor="#96ABCA">
                          <tr> 
                            <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="20%" height="25"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="15" height="20" align="absmiddle"><font color="#003399">- 
                                    과정분류</font></td>
                                  <td height="25" bgcolor="#EEF4F9"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="15" height="20" align="absmiddle"><%=curriSubInfo.getCurriTypeName()%></td>
                                </tr>
                                <tr > 
                                  <td height="1" colspan="2" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/dot_hor02.gif"> 
                                  </td>
                                </tr>
                                <tr> 
                                  <td width="20%" height="25"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="15" height="20" align="absmiddle"><font color="#003399">- 
                                    신청과정 </font></td>
                                  <td height="25" bgcolor="#EEF4F9"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="15" height="20" align="absmiddle"><%=curriSubInfo.getCurriName()%></td>
                                </tr>
                                <tr > 
                                  <td height="1" colspan="2" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/dot_hor02.gif"> 
                                  </td>
                                </tr>
                                <tr> 
                                  <td width="20%" height="25"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="15" height="20" align="absmiddle"><font color="#003399">- 
                                    수강기간</font></td>
                                  <td height="25" bgcolor="#EEF4F9"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="15" height="20" align="absmiddle">
<%
	if (curriSubInfo.getCurriProperty2().equals("R")) {
%>
<%=DateTimeUtil.getDateType(1,StringUtil.nvl(curriSubInfo.getServiceStart()))%> ~ <%=DateTimeUtil.getDateType(1,StringUtil.nvl(curriSubInfo.getServiceEnd()))%>
<%
	}
	else {
%>
결제일로부터 <%=StringUtil.nvl(curriSubInfo.getServiceStart())%> 일간
<%
	}
%>
								  </td>
                                </tr>
                                <tr > 
                                  <td height="1" colspan="2" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/dot_hor02.gif"> 
                                  </td>
                                </tr>
                                <tr> 
                                  <td width="20%" height="25"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="15" height="20" align="absmiddle"><font color="#003399">- 
                                    수강료</font></td>
                                  <td height="25" bgcolor="#EEF4F9"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="15" height="20" align="absmiddle"><%=tmpPrice%></td>
                                </tr>
                              
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>



				  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="left" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/02_cen_stitle08.gif" align="absmiddle" border="0"> 
                      </td>
                    </tr>
                  </table>



				  <br>

<%
	if (deptSaleCnt > 0 ) {
%> 
						<TABLE  width="100%" cellspacing="1" cellpadding="1" bgcolor="#96ABCA">
							<TR align=center bgcolor="#EEF4F9" height=23>
								<TD>From</TD>
								<TD>To</TD>
								<TD>할인수강료</TD>
							</TR>
<% 
		for (int i=1;i<=deptSaleCnt;i++) {
%>
							<TR align=center bgcolor="#FFFFFF" height=23>
								<TD><%=model.get("fromCnt"+i)%> 명 부터</TD>
								<TD><%=model.get("toCnt"+i)%> 명 까지</TD>
								<TD><%=StringUtil.getMoneyType(Integer.parseInt((String)model.get("saleFee"+i)))%> 원</TD>
							</TR>
<%
		} // end for
%>
							</TABLE>
<%
	// 단체 할인정보 End
	}
	else {
		out.println("<br>※ 할인 정보는 없지만 단체 수강 신청 하실 수 있습니다.<br>");
	}
%>	

<br>





                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="80" height="25" align="left" valign="top" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/02_cen_stitle06.gif" width="130" height="19" vspace="2"> 
                      </td>
                    </tr>
                    <tr> 
                      <td height="50" align="left" valign="middle" style="padding:0 0 0 15" ><font color="#0066CC"> 
                        수강인원 20명 이상부터 단체수강이 가능합니다.<br>
                        단체수강은 수강신청 완료 시 온라인을 통한 취소, 환불 을 하실 수 없습니다.<br>
                        </font></td>
                    </tr>
                    <tr> 
                      <td height="20" align="left" valign="top"  style="padding:0 0 0 15"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr > 
                            <td height="1" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/dot_hor02.gif"> 
                            </td>
                          </tr>
                          <tr> 
                            <td height="50" align="center" valign="middle" bgcolor="#FFFFFF"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="15" height="20" align="absmiddle"> 
							<input type="file" name="pFile" size="40" class="solid">
							&nbsp;
                              <a href="javascript:fileDownload('단체수강신청샘플.txt','enrollSample.txt','files/data1/','1');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/02_cen32.gif" align="absmiddle" border="0" align="absmiddle"></a>
                            </td>
                          </tr>
                          <tr > 
                            <td height="1" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/dot_hor02.gif"> 
                            </td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td align="left" valign="top"  style="padding:5 0 0 15"><br>
                        단체수강을 위해서는 모든 회원이 e-Design Academy에 회원으로 가입되어 있어야 합니다.
                        <br>
                        e-Design Academy에 등록된 회원에 한해서만 단체 수강이 가능합니다.<br>
                        형식에 맞게 파일을 작성하신 후 등록하시면 단체수강인원이 자동 등록됩니다.</td>
                    </tr>
                  </table>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="45" align="center" valign="middle"><input type="image" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/02_cen33.gif" align="absmiddle" border="0" class="solid0"></td>
                    </tr>
                  </table>
                  <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table> </td>
              </tr>
</form>
            </table> 




					</td>
				 </tr>
				 <!-- main 끝 -->
<%@include file="../common/footer.jsp" %>
