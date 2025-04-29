package com.edutrack.framework.persist;

import java.util.Hashtable;

import com.edutrack.framework.Constants;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.logging.Log;
import com.edutrack.framework.logging.LogFactory;


/**
 * 이 클래스는 IPersistBroker 인스턴스를 얻기 위한 팩토리클래스이다.
 * 실제 구현 클래스는 Configuration파일에 명시하고 런타임에서 
 * 동적으로 로딩한다.
 * 
 * <pre>
 * 
 * # key=alias,class name
 * #
 * framework.persist.persistBroker=oracle,framework.persist.OracleBroker
 * #framework.persist.persistBroker=db2,framework.persist.DB2Broker
 * # 디폴드 DAO alias
 * framework.persist.defaultPersistBroker=oracle
 * </pre>
 * <p>
 * 사용방법
 * <pre>
 * IPersistBroker broker = PersistBroker.getInstance().getFactory();
 * SqlManager sqls = SqlManager.getInstance();
 * ISequence seq = new OracleSequence();
 * int result;
 * try
 * {
 * 	ISqlStatement stmt = sqls.getStatement("product.insert");
 * 	List param = new Vector();
 * 	String prodId = seq.getIdAsString("prod_seq");
 * 
 * 	param.add(prodId);
 * 	param.add("테스트");
 * 	......
 * 	result = broker.executeUpdate(stmt);
 * } catch(SQLException se)
 * {
 * ...
 * }
 * 
 * </pre>
 *  
 * @author $Author: cvs $<p><p>$Id: PersistBrokerFactory.java,v 1.1.1.1 2007/10/11 05:33:57 cvs Exp $
 *
 */
public class PersistBrokerFactory implements Constants
{

	// singleton instance
	private static PersistBrokerFactory instance;
	private static Configuration config = ConfigurationFactory.getInstance().getConfiguration();
	private Hashtable daos;
	private Log log = LogFactory.getLog(PersistBrokerFactory.class);
	public static String defaultBroker = config.getString(KEY_DEFAULT_PERSIST_BROKER);
	
	/*
	 * 생성자 
	 */
	private PersistBrokerFactory()
	{
		daos = new Hashtable();

		// set default dao for getDAO()
		load();
	}
	
	/*
	 * PersistBrokerFactory 인스턴스를 돌려준다.
	 */
	public static PersistBrokerFactory getInstance()
	{
		if (instance == null)
		{
			synchronized(PersistBrokerFactory.class)
			{
				instance = new PersistBrokerFactory();
				
			}
		}
		return instance;
		
	}
	
	/*
	 * defaultBroker의 인스턴스를 돌려준다. 
	 */
	public IPersistBroker getBroker()
	{
		return (IPersistBroker)daos.get(defaultBroker);
	}
	/**
	 *  DBMS에 종속적인 Broker의 인스턴스를 돌려준다.
	 * @param name dao alias name
	 */
	public IPersistBroker getBroker(String daoName)
	{
		IPersistBroker dao = (IPersistBroker)daos.get(daoName);
		if (dao == null)
		{
			// 해당 broker가  없을 경우?
		}
		return dao;
		
	}
	/**
	 * Configuraiton에 명시 DAO를 구현한 클래스 인스턴스와 앨리어스를
	 * 로딩한다.
	 */
	private void load()
	{
		
		String[] arr = config.getStringArray(KEY_PERSIST_BROKER);
		
		for (int i=0; i < arr.length;i=i+2)
		{
			String alias = arr[i];
			String className = arr[i+1];
			Object obj = newDaoInstnace(className);
			if (obj != null) daos.put(alias,obj);
		}
	}
	
	/**
	 * 해당클래스명의 인스턴스를 반환
	 * @param className
	 * @return
	 */
	private Object newDaoInstnace(String className)
	{
		Object obj = null;
		try
		{
			obj = this.getClass().getClassLoader().loadClass(className).newInstance();

		} catch(Exception e)
		{
			log.error( className +" 인스턴스를 생성할 수 없습니다.");
		}
		return obj;	
	}
	
}
