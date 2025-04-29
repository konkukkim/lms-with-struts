/*
 * Created on 2004. 12. 17.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.util.StringUtil;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EbizFileDownServlet extends HttpServlet {
	
    protected static String ebizFilePath = (ConfigurationFactory.getInstance().getConfiguration()).getString("framework.ebizedu.filedown.path"); 
    public void doGet(HttpServletRequest req,HttpServletResponse resp)
    throws ServletException, IOException {

    try
    {
             
        String fileName = StringUtil.nvl(req.getParameter("fileName"));
	    String filePath = StringUtil.nvl(req.getParameter("filePath"));
        File file		=	new File(ebizFilePath +filePath+"/"+fileName);
        
        FileInputStream in = null;
        
        try {
            in = new FileInputStream(file);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
         
        resp.setContentType( "application/x-msdownload" );
        //resp.setHeader( "Content-Disposition", "attachment; filename=\""+ URLEncoder.encode(rFileName, "8859_1") + "\"" );
        resp.setHeader( "Content-Disposition", "attachment; filename=\""+ fileName + "\"" );
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
