<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="java.io.*,com.edutrack.framework.util.FileUtil,com.edutrack.framework.util.StringUtil" %>
<% String CONTEXTPATH = request.getContextPath(); %>
<%
    String rFileName = StringUtil.nvl(request.getParameter("rFileName"));
    String sFileName = StringUtil.nvl(request.getParameter("sFileName"));
    String filePath = StringUtil.nvl(request.getParameter("filePath"));
    String fileSize =  StringUtil.nvl(request.getParameter("fileSize"),"0");
    String pWhere =  StringUtil.nvl(request.getParameter("pWhere"),"K");
    
////System.out.println("file name = "+rFileName);
////System.out.println("file name = asc2ksc = "+StringUtil.asc2ksc(rFileName));
////System.out.println("file name = ksc2asc = "+StringUtil.ksc2asc(rFileName));
    
    //----------- 여기 부터
    //rFileName		=	StringUtil.asc2ksc(rFileName);
    File file		=	new File(FileUtil.UPLOAD_PATH +filePath+"/"+sFileName);
    response.setContentType("application/octet-stream"); 
    String Agent=request.getHeader("USER-AGENT");
    if(Agent.indexOf("MSIE")>=0){
			int i=Agent.indexOf('M',2);//두번째 'M'자가 있는 위치
			String IEV=Agent.substring(i+5,i+8);
			if(IEV.equalsIgnoreCase("5.5")){
				response.setHeader("Content-Disposition", "attachment;filename="+new String(rFileName.getBytes("euc-kr"),"8859_1"));
			}else{
				response.setHeader("Content-Disposition", "attachment;filename="+new String(rFileName.getBytes("euc-kr"),"8859_1"));
			}
	}else{
		response.setHeader("Content-Disposition", "attachment;filename="+new String(rFileName.getBytes("euc-kr"),"8859_1"));
	}
	//System.out.println("down.....");
	byte b[] = new byte[1024];  
	if (file.isFile()){  
		try { 
			BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));  
			BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());  
			int read = 0;  
			while ((read = fin.read(b)) != -1){  
				outs.write(b,0,read);
			} 
			outs.flush();
			outs.close();  
			fin.close(); 
		}catch(Exception e){}
	}
    
    //-- 여기까지
    
	//<IFRAME ID="fileDownFrame" NAME="fileDownFrame" SRC="CONTEXTPATH/fileDownServlet?rFileName=rFileName&sFileName=sFileName&filePath=filePath&fileSize=fileSize" WIDTH="500" HEIGHT="500" frameborder="0" scrolling="NO" noresize></IFRAME>
    
%>
