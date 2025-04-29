/*
 * Created on 2003-04-09
 *
 */
package com.edutrack.framework.persist;

/**
 *  DAT에서 일어나는 예외처리 클래스
 * @author $Author: cvs $<p><p>$Id: DAOException.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 *
 */
public class DAOException extends PersistentException
{

	/**
	 * 생성자
	 */
	public DAOException()
	{
		super();

	}

	/**
	 * 메시지를 셋팅한다.
	 * @param s
	 */
	public DAOException(String s)
	{
		super(s);
	}

}
