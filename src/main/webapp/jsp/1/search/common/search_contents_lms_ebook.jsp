<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="javaclientapi.*" %>
<%@ page import="java.net.*" %>
<%@ page import="woorin.dasen.util.BeanUtil" %>
<%@ page errorPage="/jsp/1/search/error/error_jsp.jsp" %>


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

	String AC;	//-- 색인번호-판매번호
	String F1; 	//-- 이북명
	String F2;	//-- 이북금액
	String F3;	//-- 표지이미지
	String F4;	//-- 저자
	String F5;	//-- 출판사
	String F6;	//-- 발행일
	String FC;	//-- 분류코드
	String FD;	//-- 연결이북수
	String FE;	//-- 연결이북번호
	
	int PresentCnt = 0;
	int DocOrder = 1;
	int DocWeight = 0;

	/* 현재 결과셋에 속한 맨 처음 문서로 이동 */
	ret = japi.DS_SetDocPos(hptr, japi.DS_FIRST);
	PresentCnt = japi.DS_GetResultPresentCnt(hptr);

	i = 0;
	int k = 5;
	String tmpLink="";
%>
				<table width="690" border="0" cellspacing="0" cellpadding="0">
					
<%	
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
		AC = F1 = F2 = F3 = F4 = F5 = F6 = FC = FD = FE = "";
		tmpLink="";
		
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
			else if(fieldInfo.getFieldid().startsWith("F5"))
				F5 = fieldInfo.getFieldcontent();
			else if(fieldInfo.getFieldid().startsWith("F6"))
				F6 = fieldInfo.getFieldcontent();
			else if(fieldInfo.getFieldid().startsWith("FC"))
				FC = fieldInfo.getFieldcontent();
			else if(fieldInfo.getFieldid().startsWith("FD"))
				FD = fieldInfo.getFieldcontent();
			else if(fieldInfo.getFieldid().startsWith("FE"))
				FE = fieldInfo.getFieldcontent();
			
			//### 다음 필드로 이동 ###//
			ret = japi.DS_SetFieldPos(hptr, japi.DS_NEXT);
		} // Field While
		
		if((i+1)%5 == 1)
			out.println("<tr align=center valign=middle valign=top>");
		
		if(Integer.parseInt(FD) > 1)
			tmpLink = "<a href='/BookInfo.cmd?cmd=bookDetailShow&pMode=Ebook&pSaleIdx="+AC+"&pInfoIdx=&pCateCode="+FC+"'>";
		else
			tmpLink = "<a href='/BookInfo.cmd?cmd=bookDetailShow&pMode=Ebook&pSaleIdx="+AC+"&pInfoIdx="+FE+"&pCateCode="+FC+"'>";
		System.out.println("tmpLink = "+tmpLink);
		//tmpLink = "<a href='/BookInfo.cmd?cmd=bookDetailShow&pMode=Ebook&pSaleIdx="+AC+"&pInfoIdx=&pCateCode=1'>";
%>
			
				       <td class=td2 valign=top width=138>	
						<table border=0>
				         <tr align=center valign=middle>
				           <td class=td2 align=center><%=tmpLink%><img src="/data/<%=F3%>" border="0"></a></td>
				         </tr>
				         <tr align=center valign=middle>
				           <td class=td2 align=center><%=F1%></td>
				         </tr>
				         </table>
				       </td>
<%
		

		
		//### 다음 문서로 이동 ###//
		ret = japi.DS_SetDocPos(hptr, japi.DS_NEXT);
		i++;
		k--;
		if(i%5 == 0){
			out.println("</tr >");
			k = 5;
		}
    }
    if(i%5 != 0){
		for(int j=1;j<=k;j++)
			out.println("<td width=138>&nbsp;</td>");
	}
    
	if((i+1)%5 != 0)
			out.println("</tr >");
%>

         		</table>
