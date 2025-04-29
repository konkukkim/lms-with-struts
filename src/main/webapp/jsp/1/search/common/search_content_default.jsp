<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="javaclientapi.*" %>
<%@ page import="java.net.*" %>
<%@ page import="woorin.dasen.util.BeanUtil" %>
<%@ page errorPage="/jsp/1/error/error_jsp.jsp" %>


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

	String AC, TI;
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
		AC = TI = "";

		//### 현재 문서에 속한 맨 처음 필드로 이동 ###//
		ret = japi.DS_SetFieldPos(hptr, japi.DS_FIRST);
		while(ret >= 0)
		{
			//### 현재 필드 정보 읽음 ###//
			fieldInfo = japi.DS_GetFieldInfo(hptr);

			if(fieldInfo.getFieldid().startsWith("AC"))
				AC = fieldInfo.getFieldcontent();
			else if(fieldInfo.getFieldid().startsWith("F1"))
				TI = fieldInfo.getFieldcontent();
			
			//### 다음 필드로 이동 ###//
			ret = japi.DS_SetFieldPos(hptr, japi.DS_NEXT);
		} // Field While
%>

         <tr align=center valign=middle>
		   <!-- 번호 -->
           <td class=td2><%=DocOrder%></td>

		   <!-- 제목 -->
           <td class=td2 align=left>&nbsp;<a href=./SearchServlet?command=search.full&dbNo=<%=dbNo%>&docNo=<%=AC%>&kwStr=<%=kwStr%>&expMode=<%=expMode%>><%=TI%></a></td>
         </tr>

<%
		//### 다음 문서로 이동 ###//
		ret = japi.DS_SetDocPos(hptr, japi.DS_NEXT);
		i++;
    }
%>
