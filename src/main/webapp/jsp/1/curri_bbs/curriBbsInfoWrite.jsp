<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@include file="../common/header.jsp" %>
<Script Language="javascript">
	function submitCheck()
	{
		var f = document.Input;

	 	if(!validate(f))
        	return false;
        else
            return true;

	}
	function goList(){
		document.location.href="<%=CONTEXTPATH%>/CurriBbsInfo.cmd?cmd=curriBbsInfoPagingList";
	}
</Script>



                        <table width="680" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td rowspan="3" valing="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="30" height="1"></td>
                            <td valign="top" width="660" height="36"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="36"></td>
                        </tr>
                        <tr>
                            <td align="left" valign="top">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <form name=Input method="post" action="<%=CONTEXTPATH%>/CurriBbsInfo.cmd?cmd=curriBbsInfoRegist&pRegMode=Add" enctype="multipart/form-data" onSubmit="return submitCheck()">
                                <input type=hidden name='pUseYn' value='Y'>
                                <tr>
                                    <td colspan="6" bgcolor="90B293"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="2"></td>
                                </tr>
                                <tr>
                                    <td width="100" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img272-1.gif" width="64" height="11"></td>
                                    <td width="10" align="center" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td width="560" align="left" colspan="4">
                                        <input type=text name="pBbsName" size=60 class=green maxlength=100 dispName="게시판명" notNull  lenCheck="100" value="">
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6" height="1" bgcolor="#D5E4CF"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
                                </tr>
                                <tr>
                                    <td align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img273.gif" width="20" height="11"></td>
                                    <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td align="left" width="300" class=greenb colspan=4>
                                        <!-- 이미 등록되어 있는 타입은 리스트 에 안 보이게 -->
                                        <select name='pBbsType' class="green">
    										<%
    										RowSet list = (RowSet)model.get("InfoRs");
    										String	BbsTypeString = "";

    										while(list.next()){
    											BbsTypeString += StringUtil.nvl(list.getString("bbs_type")) + " ";
    										}
    										%>
    										<% if(BbsTypeString.indexOf("notice") == -1 ){ %>
    												<option value='notice'>공지사항</option>
    										<% } %>
    										<% if(BbsTypeString.indexOf("bbs") == -1 ){ %>
    												<option value='bbs'>일반게시판</option>
    										<% } %>
    										<% if(BbsTypeString.indexOf("pds") == -1 ){ %>
    												<option value='pds'>자료실</option>
    										<% } %>
    										<% if(BbsTypeString.indexOf("qna") == -1 ){ %>
    												<option value='qna'>Q&A</option>
    										<% } %>
									    </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6" height="1" bgcolor="#D5E4CF"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
                                </tr>
                                <tr>
                                    <td align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img275.gif" width="32" height="11"></td>
                                    <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td align="left">
                                    <input type=text name="pDispLine" size=3 class=green style="text-align:right"   dataType="number"  dispName="리스트 라인수" value=''>
                                    줄</td>
                                    <td align="right"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img276.gif" width="44" height="11"></td>
                                    <td align="left" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td align="left" >
                                        <input type=text name="pDispPage" size=3 class=green style="text-align:right"   dateType="number"   dispName="페이지 표시수" value='' >페이지
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6" height="1" bgcolor="#D5E4CF"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
                                </tr>
                                <tr>
                                    <td align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img277.gif" width="44" height="11"></td>
                                    <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td align="left" width="420" colspan="4">
                                        <input type=radio name="pFileUseYn" value="Y" class=no checked > 사용 &nbsp;&nbsp;
                                        <input type=radio name="pFileUseYn" value="N" class=no > 미사용 </td>
                                </tr>
                                <tr>
                                    <td colspan="6" height="1" bgcolor="#D5E4CF"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
                                </tr>
                                <tr>
                                    <td valign="middle"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img278.gif" width="65" height="11"></td>
                                    <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td align="left">
                                        <select name='pFileCount' class=green>
                                            <option value='1'>1</option>
                                            <option value='2'>2</option>
                                            <option value='3'>3</option>
                                        </select>
                                    </td>
                                    <td align="right"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img279.gif" width="64" height="11"></td>
                                    <td align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td align="left">
                                        <input type=text name="pFileLimit" class=green value='2' size=3  dataType="number"  dispName="첨부파일최대크기"  style="text-align:right" value="" >
                                        Mbyte </td>
                                </tr>
                                <tr>
                                    <td colspan="6" height="1" bgcolor="#D5E4CF"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
                                </tr>
                                <tr>
                                    <td valign="middle"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img280.gif" width="42" height="11" alt="웹에디터"></td>
                                    <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td align="left" colspan="4">
                                        <input type=radio name="pEditorYn" value="Y" class=no > 사용&nbsp;&nbsp;
                                        <input type=radio name="pEditorYn" value="N" class=no checked > 미사용
                                        <!-- 음성게시판 사용 안함으로 설정

                                        <td width="88" height="20" align="center"><font color="606060"><strong>음성게시판</strong></font></td>
                                        <td width="1" height="20"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/board_line.gif" width="1" height="13"></td>
                                        <td width="170" height="20">
                                        <input type=radio name="pVoiceYn" value="Y" class=no > 사용&nbsp;&nbsp;
                                        <input type=radio name="pVoiceYn" value="N" class=no > 미사용 </td>
                                        -->
                                        <input type=hidden name="pVoiceYn" value="N">
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6" height="1" bgcolor="#D5E4CF"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
                                </tr>
                                <tr>
                                    <td valign="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img283_2.gif" width="49" height="11" vspace="2" alt="상단이미지"></td>
                                    <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td align="left" colspan="4">
                                        <input type=file name="pTitleImg" size=50 class=green value="">
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6" height="1" bgcolor="#D5E4CF"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
                                </tr>
                                <tr>
                                    <td valign="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img283.gif" width="49" height="11" vspace="2"></td>
                                    <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td align="left" colspan="4">
                                        <textarea name="pBbsComment" class="green" cols="80"></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6" height="1" bgcolor="#D5E4CF"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
                                </tr>
                                <tr>
                                    <td valign="middle"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img284.gif" width="48" height="11"></td>
                                    <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td align="left">
                                        <input type=text name="pNewTime" class=green size=3 value='1' dispName="New 표시 일수"  dataType="number" value='' style="text-align:right">일 동안</td>
                                    <td align="right"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img285.gif" width="58" height="11"></td>
                                    <td align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td align="left">
                                        <input type=text name="pHotChk" class=green size=3 value='100' dispName="Hot 표시 조회수" dataType="number"  value='' style="text-align:right">회 이상</td>
                                </tr>
                                <tr>
                                    <td colspan="6" height="1" bgcolor="#D5E4CF"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
                                </tr>
                                <tr>
                                    <td valign="top"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img286.gif" width="43" height="11" vspace="2"></td>
                                    <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td align="left" colspan="4">
                                        <input type=radio name="pBadWordUseYn" value="Y" class=no> 금지단어제한
                                        <input type=radio name="pBadWordUseYn" value="N" class=no checked> 제한하지않음.<br>
                                        <textarea name="pBadWord" class=green rows=3 cols=80></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="6" height="1" bgcolor="#D5E4CF"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
                                </tr>
                                <tr>
                                    <td colspan="6" height="1" bgcolor="#D5E4CF"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
                                </tr>
                                <tr>
                                    <td valign="middle"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img289.gif" width="43" height="11"></td>
                                    <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td align="left">
                                        <input type="radio" name="pCommentUseYn" value="Y" class=no> 사용
                                        <input type="radio" name="pCommentUseYn" value="N" class=no checked > 미사용
                                        <br>
                                        <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="2"></td>
                                    <td align="right"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img290.gif" width="64" height="11"></td>
                                    <td align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td align="left">
                                        <input type="radio" name="pEmoticonUseYn" value="Y" class=no > 사용
                                        <input type="radio" name="pEmoticonUseYn" value="N" class=no checked > 미사용</td>
                                </tr>
                                <tr>
                                    <td colspan="6" height="1" bgcolor="#D5E4CF"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
                                </tr>
                                <tr>
                                    <td valign="middle"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img291.gif" width="54" height="11"></td>
                                    <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td align="left" colspan="4">
                                        <input type="radio" name="pViewThreadYn" value="Y" class=no > 보임
                                        <input type="radio" name="pViewThreadYn" value="N" class=no checked > 안보임 * 뷰 화면에서 관련글 목록 보기<br>
                                        <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="2"></td>
                                </tr>
                                <tr>
                                    <td colspan="6" height="1" bgcolor="#D5E4CF"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
                                </tr>
                                <tr>
                                    <td valign="middle"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/03/03_img292.gif" width="57" height="12"></td>
                                    <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/table_line_green.gif" width="1" height="24" hspace="5"></td>
                                    <td align="left" colspan="4">
                                        <input type="radio" name="pViewPrevNextYn" value="Y" class=no > 보임
                                        <input type="radio" name="pViewPrevNextYn" value="N" class=no checked > 안보임 * 뷰 화면에서 이전글, 다음글 보기 <br>
                                        <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="2"></td>
                                </tr>
                                <tr>
                                    <td colspan="6" height="2" bgcolor="#90B293"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="2"></td>
                                </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td align="center" valign="middle" height="50"><a
                                href="javascript:goList()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/list_green.gif" width="54" height="19" vspace="2" hspace="5" border="0"></a><input
                                type="image" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/ok_green.gif" width="54" height="19" vspace="2" hspace="5"><a
                                href="document.Input.reset()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/cancle_green.gif" width="54" height="19" vspace="2" hspace="5"></a>
                            </td>
                        </tr>
                        <tr>
                            <td valing="top">&nbsp;</td>
                            <td align="center" valign="middle" height="20">&nbsp;</td>
                        </tr>
                        </table>

                <!-- main 끝 -->
                </form>



<%@include file="../common/footer.jsp" %>