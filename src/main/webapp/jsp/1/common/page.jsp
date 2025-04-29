<%@page contentType="text/html;charset=euc-kr" %> 
<html><head><title>JSP 실행의 증거</title></head><body> 
<h1> JSP 실행의 증거</h1> 
<% 
  for(int i=0;i<10;i++){ 
    out.println("<font color=blue>JSP 실행의 증거</font>: "+i); 
    out.println("<br>"); 
  }  
%> 
</body></html>  