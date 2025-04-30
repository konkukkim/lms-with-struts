/*
 * Created on 2003-04-04
 *
 */
package com.edutrack.framework.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import com.edutrack.framework.Constants;

/**
 * 이 클래스는 시스템 Configuration 객체를 얻기 위한 팩토리 클래스이다.
 * 설정파일위치는 "${framework.home}/config/framework.properties" 로 지정된다.
 * ${framework.home}의 위치는 JVM 실행 환경변수에
 * <pre>
 * java -Dframework.home=[프레임워크 홈 디렉토리]
 * </pre>
 * 로 등록한다.
 * <p>
 * 사용예)
 *
 * <pre>
 * Configuration config = ConfigurationFactory.getInstance().getConfiguraiton();
 * String appDir = config.getString("framework.home");
 *
 *
 * </pre>
 *
 * @author $Author: ssunja $<p><p>$Id: ConfigurationFactory.java,v 1.2 2007/10/11 06:59:19 ssunja Exp $
 *
 */
public class ConfigurationFactory implements Constants
{

	private static ConfigurationFactory instance = null;

	private static Configuration config = null;

	private ConfigurationFactory()
	{
		try
		{
			Properties jvmEnv = System.getProperties();
			String fs = jvmEnv.getProperty("file.separator");

			// String homeDir = "/var/www/html/junnodae/WEB-INF";//
			String homeDir = jvmEnv.getProperty(KEY_HOME);
			//String homeDir = "E:\\0-pjt-20250423-junnodae\\src\\main\\webapp\\WEB-INF";
			System.out.println("ConfigurationFactory.java ----------- homeDir:" + homeDir);
			if (homeDir == null) {
				System.out.println("ERROR ConfigurationFactory.java ----------- homeDir is null");
				System.out.println("ERROR ConfigurationFactory.java ----------- {docBase}/WEB-INF/config/framework.properties is missing");
				throw new ConfigLoadingException(MSG_HOME_NOT_FOUND);
			}

			String configFile = homeDir + fs + "config" + fs +DEFAULT_CONFIG_FILENAME;
			load(configFile);

			config.addProperty(KEY_HOME, homeDir);
			String platform = jvmEnv.getProperty(KEY_PROVIDER);
			if(platform != null && "".equals(platform) == false)
				config.addProperty(KEY_PROVIDER, platform);

			String scheduler = jvmEnv.getProperty(KEY_SCHEDULER);
			if("true".equalsIgnoreCase(scheduler)){
				config.addProperty(KEY_SCHEDULER, "true");
			}else{
				config.addProperty(KEY_SCHEDULER, "false");
			}

		} catch(ConfigLoadingException cle)
		{
			System.err.println(cle.getMessage());
			System.err.println("시스템을 종료합니다.");
			System.exit(0);
		}
	}
	/**
	 * Get ConfigurationFactory singleton instance.
	 *
	 * @return ConfigurationsFactory instance
	 *
	 */
	public static ConfigurationFactory getInstance()
	{
		if (instance == null)
		{
			synchronized(ConfigurationFactory.class)
			{
				instance = new ConfigurationFactory();
			}
		}
		return instance;
	}


	/**
	 * 로딩된 Configurastion을 얻어옮.
	 *
	 *
	 * @return Configuration configuration
	 */
	public Configuration getConfiguration()
	{
		return config;
	}


	/**
	 *
	 * 파일을 Configruation 객체로 로딩한다.
	 *
	 * @param configFilePath 로딩하고자 하는 설정파일의 절대경로
	 *
	 * @throws Exception
	 */
	protected void load(String configFilePath)
			throws ConfigLoadingException
	{
		try
		{
			File configFile = new File(configFilePath);
			if (!configFile.exists()) throw new ConfigLoadingException(
					configFilePath + MSG_FILE_NOT_FOUND);
			config = new PropertiesConfiguration(configFilePath);
		} catch(IOException ie)
		{
			throw new ConfigLoadingException();
		}

	}
}
