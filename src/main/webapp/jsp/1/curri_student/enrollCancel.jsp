<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.currisub.dto.CurriSubInfoDTO"%>
<%@ page import ="java.math.BigInteger"%>

<%@include file="../common/header.jsp" %>
<%
	RowSet list = (RowSet) model.get("enrollCancelInfo");
	int totBookMarkCnt = Integer.parseInt(StringUtil.nvl((String) model.get("totBookMarkCnt"),"0"));
	int totCurriCnt    = Integer.parseInt(StringUtil.nvl((String) model.get("totCurriCnt"   ),"0"));

	String pFeeType   = "";
	String pFeeTypeNm = "";
	String pCurriName = "" ;
	String pServiceStartDate    = "" ;
	String pCancelEndDate       = "0" ;
	String pEnrollStatus   = "" ;
	int    pCancelConfigStudy   = 0 ;
	int dReturnPay = 0;
	int pPrice     = 0;
	int ifSelect   = 0;


	while (list.next()) {
		pFeeType            = StringUtil.nvl(list.getString("fee_type")) ;
		pCurriName          = StringUtil.nvl(list.getString("curri_name")) ;
		pServiceStartDate   = DateTimeUtil.getDateType(1,StringUtil.nvl(list.getString("servicestart_date"))) ;
		pCancelEndDate      = StringUtil.nvl(list.getString("cancel_end_date"),"0")  ;
		pCancelConfigStudy  = Integer.parseInt(StringUtil.nvl(list.getString("cancel_config_study"),"0")) ;
		pPrice              = Integer.parseInt(StringUtil.nvl(list.getString("price"),"0"))  ;
		pEnrollStatus       = StringUtil.nvl(list.getString("enroll_status")) ;

		if(("card").equals(pFeeType)) pFeeTypeNm = "신용카드";
		else if(("bank").equals(pFeeType)) pFeeTypeNm = "계좌이체 ";
		else if(("off").equals(pFeeType)) pFeeTypeNm = "무통장입금";
//		else if(("sms"  ).equals(pFeeType)) pFeeTypeNm = "핸드폰";
		
		
		ifSelect			= Integer.parseInt(StringUtil.nvl(list.getString("status1"),"0")); //추가 했따...젠장....
	}
	list.close();

	if (totCurriCnt==0 || totBookMarkCnt==0) 
		dReturnPay = pPrice;
	else
		dReturnPay = pPrice - pPrice * ( ( totBookMarkCnt * 100 )/ totCurriCnt );


	BigInteger biCancelEndDate = new BigInteger( pCancelEndDate );
	BigInteger biCurrentDate = new BigInteger( CommonUtil.getCurrentDate() );
		
	//out.println(ifSelect);
%>
<Script Language="javascript">

/** 환불을 받기 위한 계좌정보를 입력하는 팝업을 오픈한다.
 */
function openEnrollCancel(YrN)
{
	//alert(YrN);
	
	Page = "<%=CONTEXTPATH%>/Student.cmd?cmd=popEnrollCancel&pMode=MYPAGE&pCurriCode=<%=request.getParameter("pCurriCode") %>&pCurriYear=<%=request.getParameter("pCurriYear") %>&pCurriTerm=<%=request.getParameter("pCurriTerm") %>&pRetPrice=<%=dReturnPay %>&pPaymentIdx=<%=request.getParameter("pPaymentIdx") %>&pEnrollStatus=<%=request.getParameter("pEnrollStatus") %>&YrN="+YrN;

	var winOption = "left=100,top=100,toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=yes,resizable=no,width=450,height=450";
	
	ShowInfo = window.open(Page,"StudyInfo", winOption);
    
    
}

/** 이전목록으로 이동시킨다.
 */
function goPrev()
{
	
	location.href="<%=CONTEXTPATH%>/Payment.cmd?cmd=myPaymentList&MENUNO=0";
    
}
</Script>

                 </tr>
                <!-- main 시작 -->
                <tr> 
					<td height="410" align="left" valign="top" style="padding:0 10 0 15">




<table width="100%" height="35" border="0" cellpadding="0" cellspacing="0" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/03_cen_titlebg.gif">
  <tr> 
	<td align="left" valign="middle" style="padding:0 0 0 8"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/03_centitle02.gif" align="absmiddle" border="0"></td>
  </tr>
</table>


<br>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td align="center" valign="top">


<!-- 강좌결제내역 시작 -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="28">
		<tr> 
		  <td align="left" valign="top"> 
<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/01_title013.gif">
		  </td>
		</tr>
		</table>

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr> 
		  <td height="2" class="b_td01"> </td>
		</tr>
		<tr> 
		  <td height="30" class="b_td02">
		  <table width="100%" height="30" border="0" cellpadding="0" cellspacing="0">
			  <tr align="center" valign="middle"> 
				<td width="39%" class="b_td02" align="center">과정명</td>
				<td width="1%" class="b_td02"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti.gif" width="3" height="21"></td>
				<td width="19%" class="b_td02" align="center">구매일</td>
				<td width="1%" class="b_td02"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti.gif" width="3" height="21"></td>
				<td width="19%" class="b_td02" align="center">구매가</td>
				<td width="1%" class="b_td02"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/bbs_parti.gif" width="3" height="21"></td>
				<td width="19%" class="b_td02" align="center">결제수단</td>
			  </tr>
			</table>
			</td>
		</tr>
		<tr> 
		  <td height="1" class="b_td03"> </td>
		</tr>
		<tr> 
		  <td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr onMouseOver="this.className='tdcolor2';" onMouseOut="this.className='tdcolor1'"> 
						<td width="40%" height="25">
						<%=pCurriName %>
                        </td>
						<td width="20%" height="25" align="center">
						<%=pServiceStartDate %>
						</td>
						<td width="20%" height="25" align="center">
						<%=StringUtil.getMoneyType(pPrice) %> 원
						</td>
						<td width="20%" height="25" align="center">
						<%=pFeeTypeNm %>
						</td>
                    </tr>
                    <tr> 
						<td colspan="4" height="1" class="b_td03"></td>
                    </tr>
				</table>

		  </td>
		</tr>
		</table>

<!-- 강좌결제내역 끝 -->

<br>

      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="center"> <table width="90%" border="0" cellspacing="0" cellpadding="5">
              <tr> 
                <td><strong><font color="#66945C">[<%=pCurriName%>]</font></strong>과정의 
                  수강취소는 수강강좌가 <%=pCancelConfigStudy%>강 미만인 경우로
				  <%=DateTimeUtil.getDateType(1, pCancelEndDate)%>일까지만 가능합니다.<br>
                  <%=DateTimeUtil.getDateType(1, CommonUtil.getCurrentDate() )%>일 
				  현재 <%=totBookMarkCnt%>강을 수강하셨습니다.<br>
<%
// 수강취소 종료일이 오늘보다 이후가 아니고,
// 수강취소 강좌가 초과되지 않을경우에
// 수강취소를 할 수 있는 버튼을 보여준다.
// 제어 추가 했따...젠장...
	if( ifSelect != 2 && (biCancelEndDate.max(biCurrentDate).equals(biCancelEndDate) || totBookMarkCnt >= pCancelConfigStudy)) {
%> 
                  수강을 취소하시게 되면 강의료 <font color="#FF6600"><strong>[<%=StringUtil.getMoneyType(pPrice)%>원]</strong></font>중 
                  <font color="#FF6600"> <strong>[<%=StringUtil.getMoneyType( dReturnPay )%>원]</strong></font>을 환불 
                  받으실 수 있습니다.<br>
                  (단, 무통장 입금 결제하신 경우 환불금액에서 은행수수료가 공제됩니다.)
				  <br><br>
				  </td>
              </tr>

            </table></td>
        </tr>
        <tr> 
          <td height="1" align="center" bgcolor="#C6C5BF"> </td>
        </tr>
        <tr> 
          <td align="center"><table width="90%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td align="center">
				<br>
				<font color="#666666" size="2"><strong>[<%=pCurriName%>]강좌의
				수강을 취소하시겠습니까?</strong></font><br><br>
				<a href="javascript:openEnrollCancel('Y')"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/03_cen_30.gif" align="absmiddle" border="0"></a>
				<a href="javascript:goPrev()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/03_cen_31.gif"  align="absmiddle" border="0"></a> 
                </td>
              </tr>
            </table></td>
        </tr>
      </table>


<%
	}
	else if ( ifSelect == 2 && (biCancelEndDate.max(biCurrentDate).equals(biCancelEndDate) || totBookMarkCnt >= pCancelConfigStudy)) {
%>
                  현재 수강료가 미결제 상태 이므로 환불 받으실 금액은 없습니다.<br>
				  수강 취소 사유 입력만으로 수강 취소 가능 합니다.
				  <br><br>
				  </td>
              </tr>
            </table></td>
        </tr>
        <tr> 
          <td height="1" align="center" bgcolor="#C6C5BF"> </td>
        </tr>
        <tr> 
          <td align="center"><table width="450" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td align="center">
				<br><font color="#666666" size="2"><strong>[<%=pCurriName%>]강좌의
				수강을 취소하시겠습니까?</strong></font><br><br>
				<a href="javascript:openEnrollCancel('N')"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/03_cen_30.gif" align="absmiddle" border="0"></a>
				<a href="javascript:goPrev()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/myeda/03_cen_31.gif"  align="absmiddle" border="0"></a> 
                </td>
              </tr>
            </table></td>
        </tr>
      </table>


<%
	}
	else {
%>
                  <b>[<%=pCurriName %>]</b>강좌의 수강을 취소하실수 없습니다.
				  <br><br>
				  </td>
              </tr>
            </table></td>
        </tr>
      </table>
<%
	} // end if
%>





	  </td>
  </tr>
</table>




					</td>
				 </tr>
				 <!-- main 끝 -->
	
<%@include file="../common/footer.jsp" %>
