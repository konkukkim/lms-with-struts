<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.user.dto.UsersDTO"   %>
<%@ page import ="javax.sql.RowSet, com.edutrack.currisub.dto.CurriSubInfoDTO, java.util.ArrayList, com.edutrack.user.action.ParseParam"%> 
<%@ page import ="com.edutrack.sample.dto.UserInfo" %>
<%@include file="../common/header.jsp" %>
<%
	UsersDTO       userInfo    = (UsersDTO)model.get("userInfo");
	CurriSubInfoDTO	curriSubInfo	= new CurriSubInfoDTO();
	curriSubInfo = (CurriSubInfoDTO)model.get("curriSubInfo");

	String curriType = StringUtil.nvl(curriSubInfo.getCurriProperty2(),"R");

	String tmpPrice = "";
	if(curriSubInfo.getPrice() <= 0)
		tmpPrice = "무료";
		
	int failCnt = Integer.parseInt((String)model.get("errorCnt"));
	int succCnt = Integer.parseInt((String)model.get("succCnt"));
	int jobCnt = Integer.parseInt((String)model.get("jobCnt"));
	ArrayList succList = (ArrayList)model.get("succList");	
	ArrayList failList = (ArrayList)model.get("failList");

	int saleFee = 0;
	String EnrollLink = CONTEXTPATH+"/Student.cmd?cmd=enrollDeptRegist";
	// 주문번호
	String MallOrderId = "KIDP_CURRI_"+CommonUtil.getCurrentDate();
	saleFee = Integer.parseInt((String)model.get("saleFee"));

%>
<script language="JavaScript">
function makeWin() {
	var termid		=	"T00000";			// KCP로부터 부여받은 영업점코드(터미날 ID)를 정확히 입력하세요(6자리)
	var midbykcp	=	"MT31";				// KCP로부터 부여받은 가맹점코드(가맹점 ID)를 정확히 입력하세요(4자리)	

	var frm	=	document.payfrm;
	var amt				=	frm.amt.value;				// 구매금액 
	var orderName		=	frm.orderName.value;		// 주문내역     
	var orderId			=	frm.orderId.value;			// 주문번호     
	var customerName	=	frm.customerName.value;		// 구매자명     
	var customerTel		=	frm.customerTel.value;		// 구매자전화
	var customerMobile	=	frm.customerMobile.value;	// 구매자휴대폰   
	var userkey			=	frm.userkey.value;			// 주문요구
	var customerEmail	=	frm.customerEmail.value;	// 구매자E-mail 


	var cgiurl = "/KCP/order.jsp?amt=" + amt + "&orderName=" + orderName + "&orderId=" + orderId + "&customerName=" + customerName + "&customerTel=" + customerTel + "&customerMobile=" + customerMobile + "&userkey=" + userkey + "&customerEmail=" + customerEmail;

	if (frm.paymethod[0].checked==true) {
		cgiurl = "/KCP/order.jsp?amt=" + amt + "&orderName=" + orderName + "&orderId=" + orderId + "&customerName=" + customerName + "&customerTel=" + customerTel + "&customerMobile=" + customerMobile + "&userkey=" + userkey + "&customerEmail=" + customerEmail;
		open_win(cgiurl);
	}
	else if (frm.paymethod[1].checked==true) {
		cgiurl = "";
	}
	else if (frm.paymethod[2].checked==true) {
		frm.submit();
	}
	else if (frm.paymethod[3].checked==true) {
		cgiurl = "";
	}

}

function open_win(url) {
	if(navigator.appName == "Netscape") {
<%//		newWin = window.showModalDialog(cgiurl, window, "dialogLeft:50px; dialogWidth:530px; dialogHeight:530px; edge:none; center:no; resizable:no; status:no; help:no; scroll:no;");%>
		newWin = window.open(url, "_new", "width=530, height=530, scrollbars=0, scroll=0, resizable=0, status=1");
	}
	else {
<%//		newWin = window.showModalDialog(cgiurl, window, "dialogLeft:50px; dialogWidth:530px; dialogHeight:530px; edge:none; center:no; resizable:no; status:no; help:no; scroll:no;");%>
		newWin = window.open(url, "_new", "width=530, height=530, scrollbars=0, scroll=0, resizable=0, status=1");
	}
}

function changepay(paymethod) {
	var frm = document.payfrm;
	if (paymethod=='card') {
		frm.paymethodname.value='신용카드';
		frm.orderName.value = '<%=curriSubInfo.getCurriName()%> 과정신청(신용카드)';
	}
	else if (paymethod=='bank') {
		frm.paymethodname.value='계좌이체';
		frm.orderName.value = '<%=curriSubInfo.getCurriName()%> 과정신청(계좌이체)';
	}
	else if (paymethod=='off') {
		frm.paymethodname.value='무통장입금';
		frm.orderName.value = '<%=curriSubInfo.getCurriName()%> 과정신청(무통장입금)';
	}
	else if (paymethod=='sms') {
		frm.paymethodname.value='핸드폰결제';
		frm.orderName.value = '<%=curriSubInfo.getCurriName()%> 과정신청(핸드폰결제)';
	}
}
</script>

                 </tr>
                <!-- main 시작 -->
                <tr> 
					<td height="410" align="left" valign="top" style="padding:0 10 0 15">


            <table width="100%" height="35" border="0" cellpadding="0" cellspacing="0" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/02_cen_titlebg.gif">
              <tr> 
                <td align="left" valign="middle" style="padding:0 0 0 8"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/02_centitle06.gif" align="absmiddle" border="0"></td>
              </tr>
            </table>

<form name="payfrm" method="post" action="<%=EnrollLink%>">
<input type="hidden" name="amt" value="<%=(saleFee*succCnt)%>">
<input type="hidden" name="orderName" value="<%=curriSubInfo.getCurriName()%> 과정신청(신용카드)">
<input type="hidden" name="orderId" value="<%=MallOrderId%>">
<input type="hidden" name="customerName" value="<%//=userInfo.getUserName()%>">
<input type="hidden" name="customerTel" value="<%//=userInfo.getPhoneHome()%>">
<input type="hidden" name="customerMobile" value="<%//=userInfo.getPhoneMobile()%>">
<input type="hidden" name="userkey" value="">
<input type="hidden" name="customerEmail" value="<%//=userInfo.getEmail()%>">

<input type="hidden" name="paychk" value="">
<input type="hidden" name="pCurriCode" value="<%=model.get("pCurriCode")%>">
<input type="hidden" name="pCurriYear" value="<%=model.get("pCurriYear")%>">
<input type="hidden" name="pCurriTerm" value="<%=model.get("pCurriTerm")%>">
<!-- 과정유형 ==> 정규과정 : R 상시과정 : O -->
<input type="hidden" name="pCurriType" value="<%=curriType%>">
<!-- 등록 대상 수강자 수 -->
<input type="hidden" name="pRegCnt" value="<%=succCnt%>">
<%
	if (curriSubInfo.getCurriProperty2().equals("O")) {
%>
<!-- 상시과정 수강일자 -->
<input type="hidden" name="pServiceDay" value="<%=StringUtil.nvl(curriSubInfo.getServiceStart())%>"> 
<%
	}
%>

            <table width="100%" height="45" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="center" valign="top" class="padding:20 0 0 0"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="45" align="left" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/02_cen_stitle07.gif" width="132" height="18" vspace="2"> 
                      </td>
                    </tr>
                  </table>

                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="center" valign="top" style="padding:10 0 25 15"> 
                        <table width="100%" border="0" cellpadding="0" cellspacing="2" bgcolor="#96ABCA">
                          <tr> 
                            <td bgcolor="#FFFFFF">
<%
	if (failCnt <= 0 ) {
%>                              
								<table width="100%" border="0" cellspacing="0" cellpadding="0">

<%
		ParseParam param = null;
		int i =0;
		for(i=0; i < succList.size();i++){
			param = (ParseParam)succList.get(i);
			if (((i+1)%3) == 1)
				out.println("<tr align=\"center\" valign=\"middle\">");
%>	
									<input type="hidden" name="pUserId[<%=i%>]" value="<%=param.getUserId()%>">
									<td width="33%" height="25" bgcolor="#EEF4F9" style="padding:1 0 1 0" align="center">
                                    <%=StringUtil.substring(param.getJuminNo(),0,6)%>-******* 
                                    [<%=param.getUserId()%>]
									</td>
<%
			if (((i+1)%3) == 0)
				out.println("</tr>");
		}

		if ((i%3) > 0) {

			for (int j=(i%3)+1; j<=3; j++)
				out.println("<td width=\"33%\" height=\"25\" bgcolor=\"#EEF4F9\" style=\"padding:1 0 1 0\" align=\"center\"></td>");
			out.println("</tr>");
		}
%>
                                </table>
<%
	}
	else {
%>
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
								  <tr align="center" valign="middle">
									<td width="33%" height="25" bgcolor="#EEF4F9" style="padding:1 0 1 0" align="center" class="b_title02">
									라인번호
									</td>
									<td width="33%" height="25" bgcolor="#EEF4F9" style="padding:1 0 1 0" align="center" class="b_title02">
									사용자ID
									</td>
									<td width="33%" height="25" bgcolor="#EEF4F9" style="padding:1 0 1 0" align="center" class="b_title02">
									오류내용
									</td>
								  </tr>
								  <tr align="center" valign="top">
									<td colspan="3" height="1" bgcolor="#96ABCA"></td>
								  </tr>
<%   
		ParseParam param = null;
		for (int i =0; i < failList.size();i++) {
			param = (ParseParam)failList.get(i); 
%>
								  <tr align="center" valign="middle">
									<td width="33%" height="25" bgcolor="white" style="padding:1 0 1 0" align="center">
									<%=param.getLineNo()%>
									</td>
									<td width="33%" height="25" bgcolor="white" style="padding:1 0 1 0" align="center">
									<%=param.getUserId()%>
									</td>
									<td width="33%" height="25" bgcolor="white" style="padding:1 0 1 0" align="center">
									<%=param.getErrorType()%>
									</td>
								  </tr>
								  <tr align="center" valign="top">
									<td colspan="3" height="1" bgcolor="#96ABCA"></td>
								  </tr>
<%
		}
%>
                                </table>

<%
	}
%>

								<table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr align="center" bgcolor="#FFFFFF"> 
                                  <td height="25" colspan="3" style="padding:15 0 15 0">총 
                                    <font color="#FF1000"> <strong><%=jobCnt%>명</strong></font>을 
                                    단체수강 신청하셨으며 <font color="#FF1000"><b><%=failCnt%></b>건</font>의 오류가 있습니다.<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="8" height="20" align="absmiddle">
								  </td>
                                </tr>
                              </table></td>
                          </tr>
                        </table> 
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="30" align="right" valign="bottom">
							<a href="<%=CONTEXTPATH%>/Student.cmd?cmd=enrollDeptProcess1&pProperty1=<%=model.get("pProperty1")%>&pCurriCode=<%=model.get("pCurriCode")%>&pCurriYear=<%=model.get("pCurriYear")%>&pCurriTerm=<%=model.get("pCurriTerm")%>"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/02_cen34.gif" align="absmiddle" border="0"></a>
							</td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>












<%
	if (failCnt <= 0 ) {

		//saleFee = Integer.parseInt((String)model.get("saleFee"));
		if (saleFee > 0) {
		//-- 유료강좌 시작
			tmpPrice = "<b>"+StringUtil.getMoneyType(saleFee*succCnt)+"원</b><br>("+StringUtil.getMoneyType(saleFee)+"원 x "+succCnt+"명)";
%>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="80" height="25" align="left" valign="top" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/02_cen_stitle05.gif" width="104" height="19" vspace="2"> 
                      </td>
                    </tr>
                    <tr> 
                      <td height="15" align="left" valign="top" style="padding:0 0 0 15" ><font color="#0066CC"> 
                        결제방법을 선택하세요.</font></td>
                    </tr>
                    <tr> 
                      <td height="20" align="left" valign="top"  style="padding:0 0 0 15">
<input type="radio" name="paymethod" value="card" class="chek1" checked onClick="javascript:changepay('card');">
신용카드<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="30" height="20"> 
<input type="radio" name="paymethod" value="bank" class="chek1" onClick="javascript:changepay('bank');">
계좌이체<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="30" height="20"> 
<input type="radio" name="paymethod" value="off" class="chek1" onClick="javascript:changepay('off');">
무통장 입금<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="30" height="20"> 
<!--<input type="radio" name="paymethod" value="sms" class="chek1" onClick="javascript:changepay('sms');">
핸드폰결제-->
					  </td>
                    </tr>
                    <tr> 
                      <td align="left" valign="top"  style="padding:5 0 0 15"><table width="50%" border="0" cellpadding="0" cellspacing="2" bgcolor="#C9CDD3">
                          <tr> 
                            <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#C9CDD3">
                                <tr align="center"> 
                                  <td width="50%" height="25" bgcolor="#E2ECF4" class="b_title02">결제방법</td>
                                  <td width="50%" height="25" bgcolor="#E2ECF4" class="b_title02">결제금액</td>
                                </tr>
                                <tr align="center"> 
                                  <td width="50%" height="35" bgcolor="#FFFFFF" >
<input type="text" name="paymethodname" value="신용카드" style="font-weight:bold; color:red; text-align:center; border-width:1px; border-color:white; border-style:solid;" readonly>
								  </td>
                                  <td width="50%" height="35" bgcolor="#FFFFFF" ><font color="#FF100%0"><strong><%=tmpPrice%></strong></font></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td align="left" valign="top" style="padding:15 0 20 15"><table width="100%" border="0" cellpadding="15" cellspacing="1" bgcolor="#C9CDD3">
                          <tr> 
                            <td bgcolor="#FFFFFF"><strong><font color="#333333">[ 
                              국민, BC, 조흥, 우리 카드 ISP 결제]</font></strong><br>
                              1. 반드시 인터넷안전결제(ISP)로 하셔야 합니다.<br>
                              2. 10만원이상 결제하실 경우, 공인인증서를 통해 본인여부를 확인하고 인터넷안전결제(ISP)로 
                              하셔야 합니다.<br> <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="15" height="15" align="absmiddle">자세한 
                              내용은 해당 카드사를 참조하시기 바랍니다.<br> <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="15" height="15" align="absmiddle"><a href="#" class="b_title02">국민카드</a><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="15" height="15" align="absmiddle"> 
                              <a href="#" class="b_title02">BC카드</a><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="15" height="15" align="absmiddle"> 
                              <a href="#" class="b_title02">조흥카드</a><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="15" height="15" align="absmiddle"> 
                              <a href="#" class="b_title02">우리카드<br>
                              <font color="#333333"><br>
                              </font></a><font color="#333333"><strong>[ 비자 안심클릭 
                              결제 ]</strong></font><br>
                              1. <font color="#FF100%0">삼성/LG/외환/롯데/현대/신한/한미/하나/전북·수협·제주은행</font> 
                              카드는 안심클릭으로 결제하셔야 합니다.<br>
                              2. 자세한 내용은 비자 안심 클릭 페이지를 참고하세요.<br></td>
                          </tr>
                        </table>
                        <table width="100%" border="0" cellpadding="15" cellspacing="1" bgcolor="#C9CDD3">
                          <tr> 
                            <td bgcolor="#FFFFFF"><strong><font color="#333333">[ 
                              이동통신사별 휴대폰결제 월 이용 한도액 안내 ]</font></strong><br> 
                              <br> <font color="#0066CC">SK텔레콤</font> : 10만원(High고객), 
                              6만원(Medium고객), 3만원(Low고객)<br>
                              가. 결제 건당 한도금액 : 10만원<br>
                              나. 단, 신규가입 고객은 Low고객으로 3만원 한도<br> <br> <font color="#0066CC">KTF</font> 
                              : 6만원 (모든 고객)<br> <br> <font color="#0066CC">LG텔레콤</font> 
                              : 10만원 (모든 고객) </td>
                          </tr>
                        </table>
                        <table width="100%" border="0" cellpadding="15" cellspacing="1" bgcolor="#C9CDD3">
                          <tr> 
                            <td bgcolor="#FFFFFF"><strong><font color="#333333">[ 
                              실시간 계좌이체시 주의사항 ]</font></strong><br>
                              1. 인터넷으로 상품구매와 동시에 본인 통장에서 Cinema Academy로 바로 입금처리가 
                              됩니다.<br>
                              2. 실시간 계좌이체시 은행별 서비스 가능시간 을 확인바랍니다.<br>
                              3. 고객님의 실시간 계좌이체 내역 을 확인할 수 있습니다.<br>
                              4. 실시간 계좌이체로 주문처리 중 병행 결제한 카드의 장애시 발생할 경우는 이체 처리 
                              후 취소처리되어<br> <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="15" height="15" align="absmiddle">바로 
                              고객님의 통장에 재입금 됩니다.<br>
                              5. 실시간 계좌이체는 10분 이내로 입금확인을 하실 수 있습니다.<br>
                              6. 송금 수수료는 Cinema Academy에서 부담합니다.</td>
                          </tr>
                        </table>
                        <table width="100%" border="0" cellpadding="15" cellspacing="1" bgcolor="#C9CDD3">
                          <tr> 
                            <td align="center" bgcolor="#FFFFFF"> <table width="80%" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="15%" height="22"><strong>입금계좌</strong><br></td>
                                  <td height="22">: 
                                    <select name="select2">
                                      <option>국민은행 [000-00-0000-000]</option>
                                    </select></td>
                                </tr>
                                <tr> 
                                  <td width="15%" height="22"><strong>입금자명</strong></td>
                                  <td height="22">: 
                                    <input type="text" name="textfield2" style="width:90%"></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>


<%
		}	//	if(curriSubInfo.getPrice() > 0) {
		else {
%>
<!-- 결제 방법 ==> 무료 : Free 신용카드 : Card 실시간계좌이체 :DirectBank 무통장입금 : VBank 휴대폰결제 MPhone -->
<input type="hidden" name="paymethod" value="Free">
					<table width="100%" border="0" cellpadding="0" cellspacing="2" bgcolor="#96ABCA">
					  <tr> 
						<td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr> 
							  <td height="25" align="center"><br>
							  ※ 무료 강의 입니다. 확인 버튼 클릭으로 수강 가능 하십니다.
							  <br><br>
							  </td>
							</tr>
						</table>
						</td>
					  </tr>
					</table>
					<br>
<%
		}
%>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="35" align="center" valign="top">
<a href="Javascript:makeWin();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/02_cen29.gif" align="absmiddle" border="0" class="solid0"></a>
<a href="javascript:history.back();"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/courses/02_cen30.gif" align="absmiddle" border="0"></a>
					  </td>
                    </tr>
                  </table>
                  <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table>
<%
	}
%>



				</td>
              </tr>
            </table> 

</form>


					</td>
				 </tr>
				 <!-- main 끝 -->
<%@include file="../common/footer.jsp" %>
