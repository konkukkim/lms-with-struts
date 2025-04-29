package com.edutrack.framework.persist;


import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import com.edutrack.framework.util.ObjectCloner;
import com.edutrack.framework.util.StringUtil;

/**
 * 이 클래스는 IsqlStatement를 구현한 클래스이다.
 *  
 * 
 * @author $Author: cvs $<p><p>$Id: QueryStatement.java,v 1.1.1.1 2007/10/11 05:33:57 cvs Exp $
 *
 */
public class QueryStatement implements ISqlStatement, Serializable
{
	protected String sql;
	protected String key;
	protected String description;
	
	protected List bindVars = null;
	
	protected List batch = null;
	protected boolean batchMode = false;
	
	long lastModifiedCheckTime;
	
	String source;
	
	
	/**
	 * 생성자
	 */
	public QueryStatement() 
	{
		bindVars = new Vector();
		batch = new Vector();
		lastModifiedCheckTime = System.currentTimeMillis();
	}
	
	/**
	 *  Parameter 초기화
	 */
	public void clearParam(){
		if(this.bindVars != null ) this.bindVars.clear();
	}
	/**
	 * 쿼리문을 돌려준다. 
	 */
	public String getSql() 
	{
		return sql;
	}
	
	/**
	 * 쿼리문을 셋팅한다. 
	 */
	public void setSql(String sql)
	{
//		this.sql = sql.toUpperCase();//-- mssql 만 사용
		this.sql = sql;
	}

	/**
	 * 키값을 돌려준다. 
	 */
	public String getKey() 
	{
		return key;
	}
	
	/**
	 * 키값을 셋팅한다.
	 */
	public void setKey(String key)
	{
		this.key = key;	
	}
	
	/**
	 * 설명을 셋팅한다. 
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * 설명을 돌려준다.
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	/**
	 * String 형태의 파라미터를 셋팅해준다. 
	 */
	public void setString(String param){
		setString(param, "");
	}
	public void setString(String param, String mode){
	    if(mode.equals("")) param = StringUtil.fn_input_text (param);
		this.bindVars.add(param);
	}
	
	/**
	 * int 형태의 파라미터를 세팅해 준다.
	 */
	public void setInteger(int param){
		this.bindVars.add(new Integer(param));
	}
	
	/**
	 * int 형태의 파라미터를 세팅해 준다.
	 */
	public void setInteger(String param){
		this.bindVars.add(Integer.valueOf(param));
	}
	
	/**
	 *  4096 자 이상의 파라미터 처리
	 */
	public void setLongString(String param){
		this.bindVars.add(new StringBuffer(param));
	}
	
	/**
	 * long 형태의 파라미터를 세팅해 준다.
	 */
	public void setLong(long param){
		this.bindVars.add(new Long(param));
	}
	
	/**
	 * long 형태의 파라미터를 세팅해 준다.
	 */
	public void setLong(String param){
		this.bindVars.add(Long.valueOf(param));
	}

	/**
	 * float 형태의 파라미터를 세팅해 준다.
	 */
	public void setFloat(float param){
		this.bindVars.add(new Float(param));
	}
	
	/**
	 * float 형태의 파라미터를 세팅해 준다.
	 */
	public void setFloat(String param){
		this.bindVars.add(Float.valueOf(param));
	}

	/**
	 * double 형태의 파라미터를 세팅해 준다.
	 */
	public void setDouble(double param){
		this.bindVars.add(new Double(param));
	}
	
	/**
	 * double 형태의 파라미터를 세팅해 준다.
	 */
	public void setDouble(String param){
		this.bindVars.add(Double.valueOf(param));
	}

	
	/**
	 * 파라미터를 셋팅해준다. 
	 */
	protected void setParam(List bindVars)
	{
		this.bindVars = bindVars;
	}
	
	/**
	 * 파라미터 리스트를 돌려준다. 
	 */
	public List getParam()
	{
		return this.bindVars;
	}


	/**
	 * 파라미터를 세팅한다.
	 */
	public void add(Object param){
		this.bindVars.add(param);
	}
	/**
	 *  셋팅된 쿼리문장을 반환해준다.
	 */
	public String toString()
	{
		StringBuffer s = new StringBuffer();
		
		s.append("SQL[").append(getKey()).append("]").append("\n");
		s.append(getSql());
		if(this.bindVars != null && this.bindVars.isEmpty() == false){
			s.append("\nPARAM : ");
			int len = this.bindVars.size();
			for(int i =0; i < len; i++){
				s.append(this.bindVars.get(i));
				s.append(",");
			}
			s.append("\n");
		}
		return s.toString();
	}
	
	
	/**
	 * @return
	 */
	public boolean isBatchMode()
	{
		return batchMode;
	}

	/**
	 * @param b
	 */
	public void setBatchMode(boolean b)
	{
		batchMode = b;
	}
	
	public void addBatch()
	{
		List param = (List)ObjectCloner.deepCopy(bindVars);
		batch.add(param);
		clearParam();
		 
	}

	/**
	 * @return
	 */
	public List getBatch()
	{
		return batch;
	}

	/**
	 * @param list
	 */
	public void setBatch(List list)
	{
		batch = list;
	}
	
	public void setLastModifiedCheckTime(long l)
	{
		this.lastModifiedCheckTime = l;
	}
	
	public long getLastModifiedCheckTime()
	{
		return this.lastModifiedCheckTime;
	}

	public void setSource(String string)
	{
		source = string;
	}
	
	public String getSource()
	{
		return source;
	}

}
