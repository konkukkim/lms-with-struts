<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="javaclientapi.*" %>
<%@ page import="java.net.*" %>
<%@ page import="woorin.dasen.util.BeanUtil" %>
<%@ page errorPage="/jsp/1/search/error/error_jsp.jsp" %>

 <table width="690" border="0" cellspacing="0" cellpadding="0">
<%
	JavaExternalAPI japi = new JavaExternalAPI();
	DS_Handle hptr = (DS_Handle) request.getAttribute("HPTR");
	ResultSet_Doc docInfo = null;
	ResultSet_Field fieldInfo = null;
	int ret, i;

    int dbNo = Integer.parseInt(request.getParameter("dbNo"));
	String kwStr = request.getParameter("kwStr");
	kwStr = URLEncoder.encode(kwStr);
    String expMode = request.getParameter("expMode");

	String AC;	//-- 색인번호-search_idx
	String F1; 	//-- 과정코드
	String F2;	//-- 과정년도
	String F3;	//-- 과정기수
	String F4;	//-- 과정명
	String FD;	//-- 수강료
	String FE;	//-- 수강신청시작일
	String FF;	//-- 수강신청종료일
	String FG;	//-- 수강시작일
	String FH;	//-- 수강종료일
	
	String tmpServiceDate;  //-- 수강기간
	String tmpCurriType;	//-- 과정구분
	int PresentCnt = 0;
	int DocOrder = 1;
	int DocWeight = 0;

	/* 현재 결과셋에 속한 맨 처음 문서로 이동 */
	ret = japi.DS_SetDocPos(hptr, japi.DS_FIRST);
	PresentCnt = japi.DS_GetResultPresentCnt(hptr);

	i = 0;
	while(ret >= 0 && i <= PresentCnt)
	{
		// 현재 문서 정보 읽음
		docInfo = japi.DS_GetDocInfo(hptr);
		if(docInfo == null)
		{
			out.print("문서정보를 읽지 못하였습니다.");
			return;
		}

		/* 검색 결과 내용 출력 : 내용 */
		DocOrder = japi.DS_GetDocOrder(hptr);
		DocWeight = japi.DS_GetDocWeight(hptr);
		AC = F1 = F2 = F3 = F4 = FD = FE = FF = FG = FH = "";
		tmpServiceDate = tmpCurriType = "";
		
		//### 현재 문서에 속한 맨 처음 필드로 이동 ###//
		ret = japi.DS_SetFieldPos(hptr, japi.DS_FIRST);

		while(ret >= 0)
		{
		
			//### 현재 필드 정보 읽음 ###//
			fieldInfo = japi.DS_GetFieldInfo(hptr);

			if(fieldInfo.getFieldid().startsWith("AC"))
				AC = fieldInfo.getFieldcontent();
			else if(fieldInfo.getFieldid().startsWith("F1"))
				F1 = fieldInfo.getFieldcontent();
			else if(fieldInfo.getFieldid().startsWith("F2"))
				F2 = fieldInfo.getFieldcontent();
			else if(fieldInfo.getFieldid().startsWith("F3"))
				F3 = fieldInfo.getFieldcontent();
			else if(fieldInfo.getFieldid().startsWith("F4"))
				F4 = fieldInfo.getFieldcontent();
			else if(fieldInfo.getFieldid().startsWith("FD"))
				FD = fieldInfo.getFieldcontent();
			else if(fieldInfo.getFieldid().startsWith("FE"))
				FE = fieldInfo.getFieldcontent();
			else if(fieldInfo.getFieldid().startsWith("FF"))
				FF = fieldInfo.getFieldcontent();
			else if(fieldInfo.getFieldid().startsWith("FG"))
				FG = fieldInfo.getFieldcontent();
			else if(fieldInfo.getFieldid().startsWith("FH"))
				FH = fieldInfo.getFieldcontent();
			
			//### 다음 필드로 이동 ###//
			ret = japi.DS_SetFieldPos(hptr, japi.DS_NEXT);
		} // Field While

		if(FG.length() > 8 ){
			tmpCurriType = "정규과정";
			tmpServiceDate = FG.substring(0,4)+". ";
			tmpServiceDate += FG.substring(4,6)+".";
			tmpServiceDate += FG.substring(6,8)+" ~ ";
			tmpServiceDate += FH.substring(0,4)+".";
			tmpServiceDate += FH.substring(4,6)+".";
			tmpServiceDate += FH.substring(6,8);
		}else{
			tmpCurriType = "상시과정";
			tmpServiceDate = "결제일로부터 "+FG+" 일간";
		}
%>


				         <tr align=center valign=middle  height="24">
				           <!--<td width=40 class=td2><%=AC%></td>-->
				           <td width=430 class=td2 align=left><img src="img/1/common/blank.gif" width="10" height="1"><a href="/CurriSub.cmd?cmd=enrollInfo&pProperty1=Cyber&pCurriCode=<%=F1%>&pCurriYear=<%=F2%>&pCurriTerm=<%=F3%>"><%=F4%></a></td>
				           <td width=100 class=td2><%=tmpCurriType%></td>
				           <td width=140 class=td2><%=tmpServiceDate%></td>
				         </tr>
				         <tr> 
                            <td colspan="7" height="1" bgcolor="#C2B29D"><img src="/img/1/common/blank.gif" width="1" height="1"></td>
                          </tr>

<%
		//### 다음 문서로 이동 ###//
		ret = japi.DS_SetDocPos(hptr, japi.DS_NEXT);
		i++;
    }
%>
							<tr> 
                            <td colspan="7" height="1" bgcolor="#D5E4CF"><img src="/img/1/common/blank.gif" width="1" height="1"></td>
                          </tr>
						</table>
