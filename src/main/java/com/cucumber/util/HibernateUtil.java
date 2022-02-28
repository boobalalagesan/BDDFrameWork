package com.cucumber.util;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
//import org.apache.commons.configuration2.FileBasedConfiguration;
//import org.apache.commons.configuration2.PropertiesConfiguration;
//import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
//import org.apache.commons.configuration2.builder.fluent.Parameters;
//import org.apache.commons.configuration2.ex.ConfigurationException;
//import org.apache.commons.conf
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	static Properties envProps;	
	private static final SessionFactory sessionFactory;

    static {
        try {
        	envProps = loadPropertiesFile();
            Configuration cfg = new Configuration();
            cfg.setProperty("hibernate.connection.url", envProps.getProperty("connection.url"));
            cfg.setProperty("hibernate.connection.username", envProps.getProperty("connection.username"));
            cfg.setProperty("hibernate.connection.password", envProps.getProperty("connection.password"));
            cfg.setProperty("hibernate.dialect", envProps.getProperty("hibernate.dialect"));
            cfg.setProperty("hibernate.connection.driver_class", envProps.getProperty("hibernate.connection.driver_class"));
            cfg.setProperty("show_sql", envProps.getProperty("show_sql"));
            cfg.configure();
            sessionFactory = cfg.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("SessionFactory creation failed" + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }
      
    public HibernateUtil(){
        
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Properties loadPropertiesFile() throws Exception {
		/*
		 * String env = System.getProperty("env"); if(StringUtils.isEmpty(env)){ env =
		 * "qa"; }
		 */
    	Properties props = new Properties();
    	String fileName = "application-qa.properties";
    	System.out.println("Loading fileName - "+fileName);
    	props.load(HibernateUtil.class.getClassLoader().getResourceAsStream(fileName));
    	return props;    	
    }
}
