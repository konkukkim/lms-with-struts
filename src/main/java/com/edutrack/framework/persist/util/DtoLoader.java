
package com.edutrack.framework.persist.util;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;
/**
 * 
 * 설문지에 대한 리스트를 xml파일에서 가져온다. 
 */
public class DtoLoader
{
  private DbInfo dbInfo = null;
  private HashMap tableList = null;
   /**
	* 생성자
	*/
   public DtoLoader()
   {
	  init();
   }

   /**
	* 서버정보와 테이블 정보를 저장할 공간 생성
	*/
   public void init()
   {
	 dbInfo = new DbInfo();
	 tableList = new HashMap();
	 load();
   }

   /**
	* XML파일 정보를 읽어와서 파싱을 한다.
	*/
   private void load()
   {
	 String configFile = "C:/eclipse/workspace/EdutrackCommon/webapps/WEB-INF/dto/dto.xml";
	 File file = new File(configFile);
	 try{
			runDigester(this,file);
		} catch (IOException ie)
		{
		  ie.printStackTrace();
		} catch (SAXException se)
		{
		  se.printStackTrace();
		}
   }

   private String getFileName(String file)
   {
		   String filename = "";

		   if (file != null)
		   {
				   int sepIndex = file.lastIndexOf(".");
				   if (sepIndex > 0 )
						   filename = file.substring(0,sepIndex);
		   }
		   return filename;
   }

   public void addTableInfo(TableInfo table){
		tableList.put(table.getName(), table); 	
   }

   public void addDbInfo(DbInfo db){
	    dbInfo = db; 	 
   }
   
   protected HashMap getTableList(){
	 return tableList;
   }

   public DbInfo getDbInfo(){
     return dbInfo;
   }
   
   private void runDigester(Object topObj, File xmlFile) throws IOException, SAXException
   {
	 Digester digester = new Digester();

	 digester.push(this);
	 
	 digester.addObjectCreate("DATABASE/SERVER", DbInfo.class);
	 digester.addCallMethod("DATABASE/SERVER/ROOT","setRoot",0);
	 digester.addCallMethod("DATABASE/SERVER/DRIVER", "setDriver", 0);
	 digester.addCallMethod("DATABASE/SERVER/URL", "setUrl", 0);
	 digester.addCallMethod("DATABASE/SERVER/USER", "setUser",0);
	 digester.addCallMethod("DATABASE/SERVER/PASSWD", "setPasswd",0);
	 digester.addCallMethod("DATABASE/SERVER/SCHEMA", "setSchema",0);
	 digester.addSetNext("DATABASE/SERVER","addDbInfo");
	 
	 Class param[] = {String.class};
	 digester.addObjectCreate("DATABASE/TABLES/TABLE", TableInfo.class);	 
	 digester.addSetNext("DATABASE/TABLES/TABLE","addTableInfo");
	 digester.addCallMethod("DATABASE/TABLES/TABLE", "setName", 1, param);
     digester.addCallParam("DATABASE/TABLES/TABLE", 0, "NAME");
     digester.addCallMethod("DATABASE/TABLES/TABLE", "setTableMap", 1, param);
     digester.addCallParam("DATABASE/TABLES/TABLE", 0, "MAP");
     digester.addCallMethod("DATABASE/TABLES/TABLE", "setDtoPackage", 1, param);
     digester.addCallParam("DATABASE/TABLES/TABLE", 0, "PACKAGE");
     digester.addCallMethod("DATABASE/TABLES/TABLE", "setSuffix", 1, param);
     digester.addCallParam("DATABASE/TABLES/TABLE", 0, "SUFFIX");
	 
	
	 digester.parse(xmlFile);
   }
   
   /**
	* 메모리 해제 
	*/
   public void dispose()
   {
	  tableList.clear();
	  tableList = null;
   }
   
   public static void main(String[] args){
   	DtoLoader ql = new DtoLoader();

   	HashMap list = ql.getTableList();
    TableInfo ti = null;  
    
    ti = (TableInfo)list.get("TB_USERS");
    System.out.println(ti.getTableMap());
    
    ti = (TableInfo)list.get("TB_USERS1");
    System.out.println(ti.getTableMap());
   }

}
