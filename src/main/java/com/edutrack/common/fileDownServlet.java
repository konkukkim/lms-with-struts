/*
 * Created on 2004. 9. 3.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.common;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edutrack.framework.util.FileUtil;
import com.edutrack.framework.util.StringUtil;

/**
 * @author osc72
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class fileDownServlet  extends HttpServlet {

    public void doGet(HttpServletRequest req,HttpServletResponse resp)
    throws ServletException, IOException {

    try
    {

        String rFileName = StringUtil.nvl(req.getParameter("rFileName"));
        String sFileName = StringUtil.nvl(req.getParameter("sFileName"));
        String filePath = StringUtil.nvl(req.getParameter("filePath"));
	    String fileSize =  StringUtil.nvl(req.getParameter("fileSize"),"0");
	    String viewYn   = StringUtil.nvl(req.getParameter("viewYn"));

	    //rFileName		=	new String(rFileName.getBytes("euc-kr"),"KSC5601");

        File file		=	new File(FileUtil.UPLOAD_PATH +filePath+"/"+sFileName);

        String file_name = req.getParameter( "file_name" );
        if ( file_name==null || file_name.equals("") ) {


        }
          FileInputStream in = null;

        try {
            in = new FileInputStream(file);
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        resp.setContentType( "application/x-msdownload" );
        resp.setHeader( "Content-Disposition", "attachment; filename=\""+ URLEncoder.encode(rFileName, "UTF-8") + "\"" );
        //resp.setHeader( "Content-Disposition", "attachment; filename=\""+ rFileName + "\"" );
        resp.setHeader( "Content-Transfer-Coding", "binary" );

        ServletOutputStream binaryOut = resp.getOutputStream();
        byte b[] = new byte[1024];

        try {
            int nRead;
            do {
                nRead = in.read( b );
                binaryOut.write( b, 0, nRead );
            } while ( nRead == 1024 );

        } catch ( Exception e ) {

        } finally {
            if ( in != null )
                in.close();
            if(binaryOut != null){
            	binaryOut.close();
            }
        }

    }catch(Exception e)
    {
       e.printStackTrace();
    }
}
}



