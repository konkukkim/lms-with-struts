/*
 * Created on 2004. 7. 6.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.framework.persist.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.StringTokenizer;

import ssari.db.Column;
import ssari.db.DBConnection;
import ssari.db.Table;

public class DtoMaker {
    private DtoLoader dtoLoader = null;
    public static void main(String[] args){
        DtoMaker dm = new DtoMaker();
       try{
        	dm.start();
       }catch(Exception e){
       	   e.printStackTrace();
       }
    }
    /**
	 * 
	 */
	private void start() throws ClassNotFoundException, SQLException, IOException{
		// TODO Auto-generated method stub
       dtoLoader = new DtoLoader();
		
        makeInfoClass();	
	}

	public void makeInfoClass()
        throws ClassNotFoundException, SQLException, IOException
    {

		DbInfo dbInfo = dtoLoader.getDbInfo(); 
		HashMap tableList = dtoLoader.getTableList();
		TableInfo tableInfo = null;
		// get parameter
        String root = dbInfo.getRoot();
        String info_package_name = "";
        String database = "";
        String column = "%";
        String[] types = {"TABLE"};
        String catalog = "";
        String database_table = "%";
        String directory = ""; 
		
        // 데이터베이스 정보
        String jdbc_driver = dbInfo.getDriver();
        String database_url = dbInfo.getUrl();
        String database_user = dbInfo.getUser();
        String database_password = dbInfo.getPasswd();
        String database_schema = dbInfo.getSchema();


        // 테이블 리스트 얻기
        Table[] tables = DBConnection.getTables(jdbc_driver, database_url, database_user, database_password,
            catalog, database_schema, database_table, types);


        for (int i = 0; i < tables.length; i++)
        {
            // 클래스 이름 얻기
            String class_name = "";
            
            System.out.println(tables[i].name);
             
            if (tableList.containsKey(tables[i].name))
            {
                tableInfo = (TableInfo)tableList.get(tables[i].name);
                class_name = tableInfo.getTableMap(); 
                class_name +=  tableInfo.getSuffix();
            }else continue;

            directory = root + "/" +tableInfo.getDtoPackage().replace('.', '/');

            makeDirectory(directory);            
                        
            // 컬럼 정보 얻기
            Column[] columns = DBConnection.getColumns(jdbc_driver, database_url, database_user, database_password,
                catalog, database_schema, tables[i].name, column);

            PrintWriter out = new PrintWriter(new BufferedWriter(
                new FileWriter(directory + "/" + class_name + ".java")));

            java.util.Date now = new java.util.Date();
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);

            String[] javaType = new String[columns.length];
            String[] columnName = new String[columns.length];

            for (int j = 0; j < columns.length; j++)
            {
                javaType[j] = DBConnection.getJavaType(columns[j]);
                StringBuffer sb = new StringBuffer();

                StringTokenizer st = new StringTokenizer(columns[j].column_name, "_");

                if (st.hasMoreTokens()) {
                    sb.append(st.nextToken().toString().toLowerCase());
                }

                while (st.hasMoreTokens()) {
                    String tmp = st.nextToken().toString();
                    sb.append(tmp.substring(0, 1).toUpperCase() + tmp.substring(1).toLowerCase());
                }

                columnName[j] = sb.toString();
            }


            out.println("/**");
            //out.println(" * @(#)" + class_name + ".java    1.0    " + df.format(now));
            out.println(" * @(#)" + class_name);
            out.println(" *");
            out.println(" * Copyright 2004 mediopia. All Rights Reserved.");
            out.println(" */");
            out.println();


            out.println("package " + tableInfo.getDtoPackage() + ";");
            out.println();

            out.println("import java.sql.Timestamp;");
            //out.println("import lz.common.info.*;");
            out.println();
            out.println("/**");
            out.println(" * " + tables[i].name + " 테이블 Dto 클래스.");
            out.println(" *");
            out.println(" ************************************************");
            out.println(" * DATE         AUTHOR      DESCRIPTION");
            out.println(" * ----------------------------------------------");
            out.println(" * " + df.format(now) + "  mediopia       Initial Release");
            out.println(" ************************************************");
            out.println(" *");
            out.println(" * @author      mediopia");
            out.println(" * @version     1.0");
            out.println(" * @since       " + df.format(now));
            out.println(" */");
            //out.println("public class " + class_name + " extends Info {");
            out.println("public class " + class_name + " {");
            out.println();


            // 필드
            for (int j = 0; j < columns.length; j++)
            {

                out.println("    protected " + javaType[j] + " " + columnName[j] + ";");
            }

            out.println();

            String allColumn = ""; 
            // get, set 메소드
            for (int j = 0; j < columns.length; j++)
            {
                String methodName = columnName[j].substring(0, 1).toUpperCase() + columnName[j].substring(1);

                out.println("    /**");
                out.println("     * get " + columnName[j] + ".");
                out.println("     *");
                out.println("     * @return      " + columnName[j]);
                out.println("     */");
                out.println("    public " + javaType[j] + " get" + methodName + "() {");
                out.println("        return  this." + columnName[j] + ";");
                out.println("    }");
                out.println();
                out.println("    /**");
                out.println("     * set " + columnName[j] + ".");
                out.println("     *");
                out.println("     * @param       " + columnName[j]);
                out.println("     */");
                out.println("    public void set" + methodName + "(" + javaType[j] + " " + columnName[j] + ") {");
                out.println("        this." + columnName[j] + " = " + columnName[j] + ";");
                out.println("    }");
                out.println();
                
                if(j > 0) allColumn += "+\",";
                else allColumn += "\"";
                allColumn += columnName[j]+"=\"+"+columnName[j];
                if(((j+1)%5 == 0) && (j+1 != columns.length)) allColumn += "\n               ";
            }

            out.println("    /**");
            out.println("     * toString ");
            out.println("     *");
            out.println("     * @return  String ");
            out.println("     */");
            out.println("    public String toString() {");
            out.println("        return  " + allColumn + ";");
            out.println("    }");
            out.println();
            
            out.println("    }");
            
            out.close();
            
        }

    }

    public void makeDirectory(String directory) {

        File file = new File(directory);

        if (file.exists() && file.isDirectory()) {
            return;
        }

        file.mkdirs();
    }
}