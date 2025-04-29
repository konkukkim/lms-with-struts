<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.CommonUtil"%>
<%@ page import ="com.edutrack.framework.util.DateTimeUtil"%> 
<%@ page import ="com.edutrack.payment.dto.PaymentDTO"%> 
<%@include file="../common/header.jsp" %>
<%------------------------------------------------------------------------------
 FILE NAME : INIsecurepay.jsp
 AUTHOR : jwkim@inicis.com
 DATE : 2004/07
 USE WITH : INIsecurepay.html
 
 이니페이 플러그인을 통해 요청된 지불을 처리한다.
                                                          http://www.inicis.com
                                                      http://support.inicis.com
                            Copyright 2004 Inicis Co., Ltd. All rights reserved.
------------------------------------------------------------------------------%>


<%@ page
	language = "java"
	contentType = "text/html; charset=euc-kr"
	import = "com.inicis.inipay.*"
%>
<% 
	/**************************************
	 * 1. INIpay41 클래스의 인스턴스 생성 *
	 **************************************/
	INIpay41 inipay = (INIpay41)model.get("inipay");
	String   pResultCode = (String) model.get("pResultCode");
	String   addResultJsp = StringUtil.nvl((String) model.get("addResultJsp"));

	/*--------------------------------------------------------------*
	 * 무이자 할부 거래일 경우 할부개월 뒤에 무이자 여부를 표시한다.*
	 *--------------------------------------------------------------*/
	String interest = "";
	String quotainterest = request.getParameter("quotainterest");
	if(quotainterest.equals("1")){
		interest = "(무이자 할부)";
	}
	/*--------------------------------------------------------------*/
	
	/****************
	 * 4. 지불 결과 *
	 ****************/
	 
	 String tid = inipay.getTid(); // 거래번호
	 String resultCode = inipay.getResultCode(); // 결과코드 ("00"이면 지불 성공)
	 String resultMsg = inipay.getResultMsg(); // 결과내용 (지불결과에 대한 설명)
	 String payMethod = inipay.getPayMethod(); // 지불방법 (매뉴얼 참조)
	 String price1 = inipay.getPrice1(); // OK Cashbag 복합결제시 신용카드 지불금액
	 String price2 = inipay.getPrice2(); // OK Cashbag 복합결제시 포인트 지불금액
	 String authCode = inipay.getAuthCode(); // 신용카드 승인번호
	 String cardQuota = inipay.getCardQuota(); // 할부기간
	 String quotaInterest = inipay.getQuotaInterest(); // 무이자할부 여부 ("1"이면 무이자할부)
	 String cardCode = inipay.getCardCode(); // 신용카드사 코드 (매뉴얼 참조)
	 String cardIssuerCode = inipay.getCardIssuerCode(); // 카드발급사 코드 (매뉴얼 참조)
	 String authCertain = inipay.getAuthCertain(); // 본인인증 수행여부 ("00"이면 수행)
	 String pgAuthDate = inipay.getPgAuthDate(); // 이니시스 승인날짜
	 String pgAuthTime = inipay.getPgAuthTime(); // 이니시스 승인시각
	 String ocbSaveAuthCode = inipay.getOcbSaveAuthCode(); // OK Cashbag 적립 승인번호
	 String ocbUseAuthCode = inipay.getOcbUseAuthCode(); // OK Cashbag 사용 승인번호
	 String ocbAuthDate = inipay.getOcbAuthDate(); // OK Cashbag 승인일시
	 String eventFlag = inipay.getEventFlag(); // 각종 이벤트 적용 여부
	 String nohpp = inipay.getNoHpp(); // 휴대폰 결제시 사용된 휴대폰 번호
	 String noars = inipay.getNoArs(); // 전화결제 시 사용된 전화번호  
	 String perno = inipay.getPerno(); // 송금자 주민번호
	 String vacct = inipay.getVacct(); // 가상계좌번호
	 String vcdbank = inipay.getVcdbank(); // 입금할 은행코드
	 String dtinput = inipay.getDtinput(); // 입금예정일
	 String nminput = inipay.getNminput(); // 송금자 명
	 String nmvacct = inipay.getNmvacct(); // 예금주 명
	 String moid = inipay.getmoid(); // 상점 주문번호
	 String codegw = inipay.getcodegw(); // 전화결제 사업자 코드
	 String ocbcardnumber = inipay.getocbcardnumber(); // OK CASH BAG 결제 , 적립인 경우 OK CASH BAG 카드 번호
	 String cultureid = inipay.getcultureid(); // 컬쳐 랜드 ID
	 String cardNumber = inipay.getCardNumber(); // 신용카드번호  	 
	 String mid = request.getParameter("mid");
	 String goodname = request.getParameter("goodname");
	 String price = request.getParameter("price");
	 String buyername = request.getParameter("buyername");
	 String buyertel = request.getParameter("buyertel");
	 String buyeremail = request.getParameter("buyeremail");
	 
	// 틴캐쉬 결제수단을 이용시에만  결제 결과 내용	
	String RemainPrice = inipay.getRemainPrice();		//틴캐쉬결제후 잔액

	 
	/*-------------------------------------------------------------------------*
	 * 에러발생시 결과 메세지에서 에러코드를 추출하는 부분으로 절대 수정 금지  *
	 *-------------------------------------------------------------------------*/
	 String tmp_ErrCode[] = resultMsg.split("]");
	 String resulterrCode = resultMsg.substring(1,tmp_ErrCode[0].length()); 
	/*-------------------------------------------------------------------------*/

%>
<Script>
	var openwin=window.open("<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/payment/childwin.html","childwin","width=300,height=160");
	openwin.close();
	
	/*------------------------------------------------------------------------------------------------------*
         * 1. $inipay->m_resultCode 										*
         *       가. 결 과 코 드: "00" 인 경우 결제 성공[무통장입금인 경우 - 고객님의 무통장입금 요청이 완료]	*
         *       나. 결 과 코 드: "00"외의 값인 경우 결제 실패  						*
         *------------------------------------------------------------------------------------------------------*/
	
	function show_receipt() // 영수증 출력
	{
		if("<%=resultCode%>" == "00")
		{
			var receiptUrl = "https://iniweb.inicis.com/DefaultWebApp/mall/cr/cm/mCmReceipt_head.jsp?noTid=" + "<%=tid%>" + "&noMethod=1";
			window.open(receiptUrl,"receipt","width=430,height=700");
		}
		else
		{
			alert("해당하는 결제내역이 없습니다");
		}
	}
		
	function errhelp() // 상세 에러내역 출력
	{
		var errhelpUrl = "http://www.inicis.com/ErrCode/Error.jsp?result_err_code=" + "<%=resulterrCode%>" + "&mid=" + "<%=mid%>" + "&tid=<%=tid%>" + "&goodname=" + "<%=goodname%>" + "&price=" + "<%=price%>" + "&paymethod=" + "<%=payMethod%>" + "&buyername=" + "<%=buyername%>" + "&buyertel=" + "<%=buyertel%>" + "&buyeremail=" + "<%=buyeremail%>" + "&codegw=" + "<%=codegw%>";
		window.open(errhelpUrl,"errhelp","width=520,height=150, scrollbars=yes,resizable=yes");
	}

</Script>

                <td colspan="2" valign="top" align="left"><img src="img/1/title/process02.gif" width="721" height="51" border="0"></td>
              </tr>
              <!-- main 시작 -->
              <tr> 
                <td width="900" valign="top"  height="510"  bgcolor="#FEFEF6""> 
                  <table width="627" border="0" cellpadding="0" cellspacing="0">
                    <tr> 
                      <td rowspan="10" valing="top" width="22"><img src="img/1/common/blank.gif" width="44" height="1"></td>
                      <td height="43" valign="top"><img src="img/1/common/blank.gif" width="1" height="42"></td>
                    </tr>
                    <!-- 수강신청 시작 -->
                    <tr> 
                      <td align="left" valign="top"> 
                        <!--강좌 주문확인 시작 -->
                        <table width="660" border="0" cellpadding="0" cellspacing="0">
                          <tr> 
                            <td colspan="3"><img src="img/1/00/00_order12.gif" width="44" height="12" vspace="5"></td>
                          </tr>
                          <tr> 
                            <td height="2" bgcolor="DECDBA" width="44"><img src="img/1/common/blank.gif" width="1" height="2"></td>
                            <td height="2" colspan="2"><img src="img/1/common/blank.gif" width="1" height="2"></td>
                          </tr>
                          <tr> 
                            <td colspan="3" height="1" bgcolor="DECDBA"><img src="img/1/common/blank.gif" width="1" height="1"></td>
                          </tr>
                          <tr> 
                            <td width="44">&nbsp;</td>
                            <td width="40">&nbsp;</td>
                            <td>&nbsp;</td>
                          </tr>
                          <tr align="center"> 
                            <td valign="top" colspan="3"> 
                              <table width="660"" border="0" cellpadding="0" cellspacing="0">
                                <tr> 
                                  <td bgcolor="D6D2E0" align="center" width="20" height="24"><img src="img/1/common/blank.gif" width="20" height="1"></td>
                                  <td bgcolor="D6D2E0" align="center" width="98" height="24"><img width="49" height="11" src="img/1/02/02_img18.gif"></td>
                                  <td width="1" bgcolor="ffffff"><img src="img/1/common/blank.gif" width="1" height="1"></td>
                                  <td bgcolor="D6D2E0" width="233" align="center"><img width="31" height="11" src="img/1/02/02_img02.gif"></td>
                                  <td width="1" bgcolor="ffffff" align="center"><img src="img/1/common/blank.gif" width="1" height="1"></td>
                                  <td bgcolor="D6D2E0" width="131" align="center"><img width="31" height="11" src="img/1/02/02_img03.gif"></td>
                                  <td width="1" bgcolor="ffffff" align="center"><img src="img/1/common/blank.gif" width="1" height="1"></td>
                                  <td bgcolor="D6D2E0" width="163" align="center"><img width="43" height="11" src="img/1/02/02_img05.gif"></td>
                                  <td bgcolor="D6D2E0" width="20" align="center"><img src="img/1/common/blank.gif" width="20" height="1"></td>
                                </tr>
                                <%
                                ArrayList curriBuyLog = (ArrayList)model.get("curriBuyLog");
                                PaymentDTO paymentDto = new PaymentDTO();
                                int totPrice = 0;
                                String regDate = "";
                                String sProcess = "";
                                String sFeeType   = "";
                                String sFeeTypeNm = "";
								
								String curriTypeName = "";
								String serviceDate = "";
                                for(int i=0; i<curriBuyLog.size() ;i++)
                                {
                                    paymentDto = (PaymentDTO)curriBuyLog.get(i);
                                    curriTypeName = CommonUtil.getSoName(SYSTEMCODE,"Cyber",paymentDto.getCurriType());
                                    serviceDate = DateTimeUtil.getDateType( 1, paymentDto.getServiceStartDate () )+" ~ "+DateTimeUtil.getDateType( 1, paymentDto.getServiceEndDate ());
                                    if(paymentDto.getCurriType().equals("O") && payMethod.equals("VBank"))
                                    	serviceDate = "결제일로부터 "+paymentDto.getServiceDay()+" 일간";
                                %>
                                <tr> 
                                  <td align="center" height="22">&nbsp;</td>
                                  <td align="center" height="22"><%=curriTypeName %></td>
                                  <td width="1" bgcolor="ffffff"><img src="img/1/common/blank.gif" width="1" height="1"></td>
                                  <td align="center"><%=paymentDto.getCurriName () %></td>
                                  <td width="1" bgcolor="ffffff" align="center"><img src="img/1/common/blank.gif" width="1" height="1"></td>
                                  <td align="center"><%=StringUtil.getMoneyType( Integer.parseInt( StringUtil.nvl(paymentDto.getTotalFee (),"0")) ) %> 원</td>
                                  <td width="1" bgcolor="ffffff" align="center"><img src="img/1/common/blank.gif" width="1" height="1"></td>
                                  <td align="center"><%=serviceDate %></td>
                                  <td align="center">&nbsp;</td>
                                </tr>
                                <tr> 
                                  <td colspan="9" height="1" bgcolor="#9A93B9"><img src="img/1/common/blank.gif" width="1" height="1"></td>
                                </tr>
                                <%
                                } // end for
                                
                                if(curriBuyLog.size() > 0)
                                {
                                    paymentDto = (PaymentDTO)curriBuyLog.get(0);
                                    totPrice = Integer.parseInt(paymentDto.getTotalFee());
                                    regDate  = DateTimeUtil.getDateType( 1, paymentDto.getRegDate() );
                                    sProcess   = paymentDto.getProcess() ;
                                    sFeeType   = paymentDto.getFeeType() ;
                                    sFeeTypeNm = (sFeeType.equals("HPP")? "핸드폰으로" : (sFeeType.equals("Card")? "신용카드로"  :  (sFeeType.equals("DirectBank")? "실시간계좌이체로" :  "무통장입금으로")  ) )  ;
                                }

                                %>
                                <tr> 
                                  <td colspan="9" height="1" bgcolor="#D0CAE2"><img src="img/1/common/blank.gif" width="1" height="1"></td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr> 
                            <td align="left" valign="top" colspan="3">&nbsp; </td>
                          </tr>
                          <!--tr> 
                            <td height="50" width="550"><b>2003년 11월 10일 한국영화역사 
                              강좌 [<font color="CB622E">50,000원</font>]을 <font color="CB622E">신용카드</font>로 
                              결제완료하셨습니다.</b></td>
                          </tr>
                          <tr> 
                            <td valign="top">&nbsp;</td>
                            <td valign="top">&nbsp;</td>
                            <td valign="top"><a href="03결제현황.htm"><img src="img/1/button/buy_info.gif" width="103" height="21" vspace="2" hspace="5" border="0"></a></td>
                          </tr-->
<%
// db fail 이 아닐경우
if( !("01").equals(pResultCode) )
{
%>                          
                          <tr> 
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td height="50" width="550"><b><%=regDate %>
                              [<font color="CB622E"><%=StringUtil.getMoneyType(totPrice) %>원</font>]을 <font color="CB622E"><%=sFeeTypeNm %></font> <%=sProcess %>하셨습니다.</b></td>
                          </tr>
                          <tr> 
                            <td valign="top" colspan=3 align=center><%=addResultJsp%></td>
                          </tr>
                          <tr> 
                            <td valign="top">&nbsp;</td>
                            <td valign="top">&nbsp;</td>
                            <td valign="top"><!--a href="03결제현황.htm"><img src="img/1/button/buy_info.gif" width="103" height="21" vspace="2" hspace="5" border="0"></a-->
                            <!-- 실패결과 상세 내역 버튼 출력 -->
                            <%
                            if(resultCode.equals("00"))
                            {
                                out.println("<a href='javascript:show_receipt();'><img src='"+ CONTEXTPATH +"/img/"+ SYSTEMCODE +"/button/button_02.gif' width='94' height='24' border='0'></a>");
                            }
                            else{
                                out.println("<a href='javascript:errhelp();'><img src='"+ CONTEXTPATH +"/img/"+ SYSTEMCODE +"/button/button_01.gif' width='142' height='24' border='0'></a>");
                            }
                            
                            %>
                            </td>
                          </tr>
<%
}
else
{
%>                          
                          <tr> 
                            <td>&nbsp;</td>
                            <td>&nbsp;</td>
                            <td height="50" width="550"><b><%=regDate %> 결제에 실패하였읍니다.[DB FAIL]<br>지속적인 오류 발생시 관리자에게 문의 바랍니다.</b></td>
                          </tr>
<%
} // end if
%>                          
                          <tr> 
                            <td valign="top">&nbsp;</td>
                            <td valign="top">&nbsp;</td>
                            <td valign="top">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td valign="top">&nbsp;</td>
                            <td valign="top">&nbsp;</td>
                            <td valign="top">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td valign="top">&nbsp;</td>
                            <td valign="top">&nbsp;</td>
                            <td valign="top" bgcolor="F6F3E8">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td valign="top">&nbsp;</td>
                            <td valign="top">&nbsp;</td>
                            <td valign="top" bgcolor="F6F3E8"><font color="CB622E">[주의]</font> 
                              <img src="img/1/common/blank.gif" width="20" height="1">무통장
                              입금을 선택하신 경우 10일 이내에 입금이 이루어지지 않으면 자동 주문취소 됩니다. </td>
                          </tr>
                          <tr> 
                            <td valign="top">&nbsp;</td>
                            <td valign="top">&nbsp;</td>
                            <td valign="top" bgcolor="F6F3E8">&nbsp;</td>
                          </tr>
                          <!-- 무통장 입금으로 주문한 경우 끝  -->
                        </table>
                        <!--강좌 주문확인 끝 -->
                      </td>
                    </tr>
                    <tr> 
                      <td align="left" valign="top" height="20">&nbsp;</td>
                    </tr>
                  </table>
                </td>
                <td valign="top" bgcolor="#DFDFDF" ><img src="img/1/common/white.gif" width="1" height="25"></td>
              </tr>
                <!-- main 끝 -->

         
<%@include file="../common/footer.jsp" %>