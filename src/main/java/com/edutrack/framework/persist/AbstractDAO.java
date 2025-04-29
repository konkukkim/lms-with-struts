/*
 * Created on 2003-04-09
 *
 */
package com.edutrack.framework.persist;

import com.edutrack.framework.logging.Log;
import com.edutrack.framework.logging.LogFactory;


/**
 * IPersistBroker 와 SqlManager 을 멤버변수로 가지고 있는
 * 추상화 클래스
 *  
 * @author $Author: cvs $<p><p>$Id: AbstractDAO.java,v 1.1.1.1 2007/10/11 05:33:56 cvs Exp $
 *
 */
public abstract class AbstractDAO implements IDAO
{
	public IPersistBroker broker = new SqlBroker();
	public Log log = LogFactory.getLog(getClass());

	/*
	 *  Insert 쿼리로 작업을 처리한다.
	 */
	public void create() throws DAOException{}
	
	/*
	 *  Update 쿼리로 작업을 처리한다.
	 */
	public void update() throws DAOException{}
	
	/*
	 *  Delete 쿼리로 작업을 처리한다.
	 */
	public void delete() throws DAOException{}
	
	/*
	 *  Select 쿼리로 작업을 처리한다.
	 */
	public void retrieve() throws DAOException{} 
	
}
