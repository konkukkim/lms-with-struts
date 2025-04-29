/*
 * Created on 2003-04-07
 *
 */
package com.edutrack.framework;

/**
 * 서버에서 사용하는 변수를 정의한 클래스
 * <p>
 * @author $Author: ssunja $<p><p>$Id: Constants.java,v 1.2 2007/10/24 00:21:44 ssunja Exp $ 
 */
public interface Constants
{
	public static final String KEY_HOME =
		"framework.home";
	
	public static final String DEFAULT_CONFIG_FILENAME = 
		"framework.properties";

	public static final String BUSINESS_CONFIG_FILENAME = 
		"mall.properties";

	public static final String DEFAULT_LOG4J_CONFIG_FILENAME = 
		"log4j.properties";
	/**
	 *  환경 설정파일의 위치를 지정하는 시스템 컨피그 설정 이름 
	 */
	public static final String KEY_CONFIG_FILE = 
		"config.file";
	/**
	 *  사용자가 정의한 쿼리 관련 xml파일을 모아 놓은 위치  
	 */		
	public static final String KEY_SQL_DIR	= 
		"framework.persist.sqldir";
	/**
	 *  Default Persist Broker의 클래스 명  
	 */		
	public static final String KEY_DEFAULT_PERSIST_BROKER = 
		"framework.persist.defaultPersistBroker";
	/**
	 *  Persist Broker의 클래스 명  
	 */
	public static final String KEY_PERSIST_BROKER = 
		"framework.persist.persistBroker";
	/**
	 *  DB Pool의 클래스 명 
	 */
	public static final String KEY_DBPOOL =
		"framework.persist.dbpoolManager";
	
	/**
	 * initialContextFactory의 클래스 명 
	 */
	public static final String KEY_INITIAL_CONTEXT_FACTORY = 
		"framework.ejbutil.initialContextFactory";
	public static final String KEY_PROVIDER_URL =
		"framework.ejbutil.providerUrl";	
	/**
	 *  환경 파일을 찾을 수 없을 때 보여주는 메시지 정의
	 */	
	public static final String MSG_FILE_NOT_FOUND = 
		"\n파일을 찾을 수 없습니다.";
		
	public static final String MSG_HOME_NOT_FOUND =
		"\n시스템 환경변수에서 " + KEY_HOME + " 을 찾을 수 없습니다."
	+	"\n자바 VM 실행 옵션에 -D" + KEY_HOME + "=[프레임워크 홈 디렉토리]를 명시하세요.";
	
	/**
	 *   로깅관련 팩토리 클래스 지정
	 */
	public static final String KEY_LOG_FACTORY = "framework.logging.factory";
	
	/**
	 *   Log4j관련 팩토리 클래스 지정
	 */
	public static final String DEFAULT_LOG_FACTORY  = "framework.logging.Log4jFactory";
	
	/**
	 *   메일서버 호스트 주소
	 */
    public static final String KEY_MAIL_HOST = "framework.mail.host";
	/**
	 *   메일 전송 프로토콜 정의
	 */
	public static final String KEY_MAIL_TRANS = "framework.mail.transport.protocol";
	/**
	 *   메일서버 사용자 아이디
	 */
	public static final String KEY_MAIL_USER = "framework.mail.user";
	/**
	 *   메일 전송 사용자 비번
	 */
	public static final String KEY_MAIL_PASSWORD = "framework.mail.password";

	/**
	 *   메일 전송 시 디버그 모드 설정
	 */
	public static final String KEY_MAIL_DEBUG = "framework.mail.debug";
	
	/**
	 * TemplateManager 관련 상수
	 */
	public static final String KEY_TEMPLATE_DIR = "framework.template.templatedir";
	public static final String KEY_TEMPLATE_UPDATE_DELAY = "framework.template.update_delay";
	public static final String KEY_TEMPLATE_ENCODING = "framework.template.encoding";
	
	/**
	 * Platform 지정 관련 상수
	 * 
	 */
	public static final String KEY_PROVIDER = "framework.provider";
	
	
	public static final String KEY_SQL_UPDATE_DELAY = "framework.presist.sql.update_delay";


	public static final String KEY_SCHEDULER = "framework.schedule.enable";
	
	// 세션이나 쿠키에서 사용할 변수명 정의 
    public static final String LOGIN_SYSTEMCODE = "SYSTEMCODE";
	public static final String LOGIN_USERID = "USERID";
	public static final String LOGIN_USERNAME = "USERNAME";
	public static final String LOGIN_USERTYPE = "USERTYPE";
	public static final String LOGIN_SCHOOLYEAR = "SCHOOLYEAR";
}
