package com.edutrack.framework.persist;

import java.util.List;

/**
 * 이 인터페이스는 DAO를 이용해서 데이터 모델을 추출하기위한
 * SQL 정보를 담고 있다.
 *
 * @author $Author: cvs $<p><p>$Id: ISqlStatement.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 *
 */
public interface ISqlStatement
{
	public static final String SELECT				= "select ";
	public static final String FROM					= " from ";
	public static final String WHERE					= " where ";
	public static final String GROUP_BY			= " group by ";
	public static final String HAVING				= " having ";
	public static final String ORDER_BY			= " order by ";

	public static final String INSERT_INTO				= "insert into ";
	public static final String INTO_COLUMN_S		= " ( ";
	public static final String INTO_COLUMN_E		= " ) ";
	public static final String VALUES_S					= " values( ";
	public static final String VALUES_E					= " ) ";

	public static final String UPDATE				= "update ";
	public static final String SET						= " set ";

	public static final String DELETE_FROM	= "delete from ";

        public static final String PART_MIDDLE = " ,rnum from (select ";
        public static final String PART_FROM = " ,limit rnum from ";

    /*
     * 쿼리문을 셋팅해 준다.
     */
	public void setSql(String sql);

    /*
     * 쿼리문을 돌려 준다.
     */
	public String getSql();

    /*
     * 키값을 셋팅해 준다.
     */
	public void setKey(String key);

    /*
     * 키값을 돌려 준다.
     */
	public String getKey();

    /*
     * Description을 셋팅해 준다.
     */
	public void setDescription(String desc);

    /*
     * Description을 돌려 준다.
     */
	public String getDescription();

    /*
     * 파라미터 리스트를 돌려 준다.
     */
	public List getParam();

	/**
	 * 파라미터를 세팅한다.
	 */
	public void add(Object param);


	/**
	 * String 형태의 파라미터를 셋팅해준다.
	 */
	public void setString(String param);

	/**
	 * int 형태의 파라미터를 세팅해 준다.
	 */
	public void setInteger(int param);

	/**
	 * int 형태의 파라미터를 세팅해 준다.
	 */
	public void setInteger(String param);

	/**
	 *  4096 자 이상의 파라미터 처리
	 */
	public void setLongString(String param);

	/**
	 * long 형태의 파라미터를 세팅해 준다.
	 */
	public void setLong(long param);

	/**
	 * long 형태의 파라미터를 세팅해 준다.
	 */
	public void setLong(String param);

	/**
	 * float 형태의 파라미터를 세팅해 준다.
	 */
	public void setFloat(float param);

	/**
	 * float 형태의 파라미터를 세팅해 준다.
	 */
	public void setFloat(String param);

	/**
	 * double 형태의 파라미터를 세팅해 준다.
	 */
	public void setDouble(double param);

	/**
	 * double 형태의 파라미터를 세팅해 준다.
	 */
	public void setDouble(String param);
	
	/**
	 * 배치모드여부를 반환한다.
	 * 
	 * @return
	 */
	public boolean isBatchMode();

	/**
	 * 배치모드를 셋팅한다.
	 * 
	 * @param b
	 */
	public void setBatchMode(boolean b);

	/**
	 * 배치를 추가한다.
	 *
	 */
	public void addBatch();
	
	/**
	 * 배치 파라메터 리스트를 반환한다.
	 * 
	 * @return
	 */
	public List getBatch();
	
	/**
	 * 배치 파라메트를 셋팅한다.
	 * @param list
	 */
	public void setBatch(List list);
	
	/**
	 * 파일변경여부에 대한 최종 체크 일시를 반환
	 * 
	 * @return
	 */
	public long getLastModifiedCheckTime();
	
	/**
	 *  파일변경여부에 대한 최종 체크 일시를 반환
	 * 
	 * @return
	 */
	public void setLastModifiedCheckTime(long l);
	
	/**
	 * XML 파일명을 반환
	 * 
	 * @return
	 */
	public String getSource();
	
	/**
	 * XML 파일명을 셋팅 
	 *
	 */
	public void setSource(String source);
	

}
