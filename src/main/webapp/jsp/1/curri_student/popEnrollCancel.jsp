<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.currisub.dto.CurriSubInfoDTO"%> 
<%@include file="../common/popup_header.jsp" %>
<%

	String YrN 		= 	StringUtil.nvl(request.getParameter("YrN"));
	int height		=	0;
	if (YrN.equals("Y")){
		height		=	5;
	}
	else{
		height		=	20;
	}

%>
<Script Language=javascript>

//var tmpBankInf = REAL_BANK_INF ;
//var bank =  REAL_BANK_INF.split(":");

// 은행명과 은행코드 세팅
<%
	if (YrN.equals("Y")) {
%>
function doOnLoad()
{
    for(i=0;i<bank.length;i++){

        bankNm = bank[i].substring(0, bank[i].indexOf("(")) ;
        bankCd = bank[i].substring(bank[i].indexOf("(")+1, bank[i].indexOf(")")) ;

	    form1.pBankCode.options[i] = new Option(bankNm, bankCd );
    }
    
}

/** 환불받기.
 */
function goRepayment()
{
    var f = form1;
    
    if(!validate(f)) return;
    
    f.pBankName.value = f.pBankCode[f.pBankCode.selectedIndex].text ;

    f.action = "<%=CONTEXTPATH%>/Student.cmd?cmd=enrollCancelProcess&pMode=MYPAGE";
    f.submit();
    
}
<%
	}
	else {
%>    
function goRepayment()
{
    var f = form1;
    
    if(!validate(f)) return;
    
    //f.pBankName.value = f.pBankCode[f.pBankCode.selectedIndex].text ;

    f.action = "<%=CONTEXTPATH%>/Student.cmd?cmd=enrollCancelProcess&pMode=MYPAGE";
    f.submit();
    
}
<%
	}
%>
  
</Script>

<form name="form1" method="post" action="">
<input type="hidden" name="pCurriCode" value="<%=request.getParameter("pCurriCode")%>">
<input type="hidden" name="pCurriYear" value="<%=request.getParameter("pCurriYear")%>">
<input type="hidden" name="pCurriTerm" value="<%=request.getParameter("pCurriTerm")%>">
<input type="hidden" name="pRetPrice" value="<%=request.getParameter("pRetPrice")%>">
<input type="hidden" name="pEnrollStatus" value="<%=StringUtil.nvl(request.getParameter("pEnrollStatus"), "")%>">
<input type="hidden" name="pPaymentIdx" value="<%=request.getParameter("pPaymentIdx")%>">




					<center>
						<table align="center" border="0" cellpadding="0" cellspacing="0" width="98%">
							<tr>
								<td width="100%" height="42" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/pop_title16.gif">&nbsp;</td>
							</tr>
							<tr>
								<td width="100%">
									<!-- 라운드 박스 -->
									<table width="98%" border="0" cellpadding="0" cellspacing="0" align="center">
									<!-- form start -->
										<tr>
											<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_01.gif" width="7" height="6"></td>
											<td width="100%" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_05.gif"></td>
											<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_03.gif" width="7" height="6"></td>
										</tr>
										<tr>
											<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_07.gif"></td>
											<td align="center" >



<br>
			<table width="90%" class="search" align="center" border="0" cellpadding="0" cellspacing="0" >
				<tr> 
					<td height="2" class="b_td01" colspan="3"> </td>
				</tr>
<%
	if (YrN.equals("Y")) {
%>

				<tr align="center">
					<td align="center" colspan="3" bgcolor="#F8F6F3">
						<font color="#9A8B7C">수강취소가 접수되었습니다.<br>
						수강취소에 따라 일정금액이 환불됩니다.<br>
						<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="5"><br>
						환불은 규정에 따라 2~3일 정도 소요될 수 있습니다.<br>
						환불시 입금 받으실 통장정보를 등록해 주시기 바랍니다.<br>
						환불금액은 강의경과일 및 진도율에 따라 결정됩니다.
					</td>
				</tr>
				<tr align="center">
					<td height="1"  class="b_td04" colspan="3" width="422"></td>
				</tr>


				<tr align="center">
					<td align="right" width="80" height="32" bgcolor="#F8F6F3" style="padding:0 5 0 0"><b><font color="#9A8B7C">은행정보</font></b></td>
					<td align="center" class="nu02" width="4" height="32">
					<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti.gif" width="3" height="21" border="0"> </td>
					<td align="left"  class="nu02" height="32" style="padding:0 0 0 5">
						<select name="pBankCode">
						<option value="01">국민은행</option>
						<option value="02">기업은행</option>
						<option value="03">대구은행</option>
						</select>
						<input type="hidden" name="pBankName">
					</td>
				</tr>
				<tr align="center">
					<td height="1"  class="b_td04" colspan="3" width="422"></td>
				</tr>
				<tr align="center">
					<td align="right" width="80" height="32" bgcolor="#F8F6F3" style="padding:0 5 0 0"><b><font color="#9A8B7C">계좌번호</font></b></td>
					<td align="center" class="nu02" width="4" height="32">
					<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti.gif" width="3" height="21" border="0"> </td>
					<td align="left"  class="nu02" height="32" style="padding:0 0 0 5">
						<input type="text" name="pAccNo" size="30" maxLength="30" notNull dispName="계좌번호">
					</td>
				</tr>
				<tr align="center">
					<td height="1"  class="b_td04" colspan="3" width="422"></td>
				</tr>
				<tr align="center">
					<td align="right" width="80" height="32" bgcolor="#F8F6F3" style="padding:0 5 0 0"><b><font color="#9A8B7C">예금주</font></b></td>
					<td align="center" class="nu02" width="4" height="32">
					<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti.gif" width="3" height="21" border="0"> </td>
					<td align="left"  class="nu02" height="32" style="padding:0 0 0 5">
						<input type="text" name="pAccName" size="30" maxLength="20" notNull dispName="예금주">
					</td>
				</tr>
				<tr align="center">
					<td height="1"  class="b_td04" colspan="3" width="422"></td>
				</tr>
<%
	}
%>
				<tr align="center">
					<td align="right" width="80" height="32" bgcolor="#F8F6F3" style="padding:0 5 0 0"><b><font color="#9A8B7C">취소사유</font></b></td>
					<td align="center" class="nu02" width="4" height="32">
					<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti.gif" width="3" height="21" border="0"> </td>
					<td align="left"  class="nu02" height="32" style="padding:0 0 0 5">
					<textarea name="pOrderMemo" cols="35" rows="<%=height%>" maxLength=2000></textarea>
					</td>
				</tr>
				<tr align="center">
					<td height="1"  class="b_td04" colspan="3" width="422"></td>
				</tr>
 
 

			</table>
<br>


											</td>
											<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_08.gif"></td>
										</tr>
										<tr>
											<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_02.gif" width="7" height="6"></td>
											<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_06.gif"></td>
											<td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/search_04.gif" width="7" height="6"></td>
										</tr>
									</table>
</form>
									<!-- 라운드 박스 -->

								</td>
							</tr>
							<tr>
								<td width="100%" height="30" align="center">
<a href="javascript:goRepayment()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bttn_ok.gif" align="absmiddle" border="0"></a>
<a href="javascript:window.close();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/bttn_close.gif" align="absmiddle" border="0"></a>
<br><br>
								</td>
							</tr>
						</table>
					
					</center>


</form>

<%@include file="../common/popup_footer.jsp" %>
<%/*if(YrN.equals("Y")){%><Script>doOnLoad()</Script><%}*/%>