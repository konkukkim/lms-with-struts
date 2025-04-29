/*
 * Created on 2003-04-03
 *
 */
package com.edutrack.framework.persist;

/**
 * Data Transfer Object의 데이터 처리 기능을 정의한 인터페이스 
 *  
 * 
 * @author $Author: cvs $<p><p>$Id: IDAO.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 *
 */
public interface IDAO
{
    /*
     *  Insert 쿼리로 작업을 처리한다.
     */
	public void create() throws DAOException;
	
    /*
     *  Update 쿼리로 작업을 처리한다.
     */
	public void update() throws DAOException;
	
    /*
     *  Delete 쿼리로 작업을 처리한다.
     */
	public void delete() throws DAOException;
	
    /*
     *  Select 쿼리로 작업을 처리한다.
     */
	public void retrieve() throws DAOException; 
	
}
