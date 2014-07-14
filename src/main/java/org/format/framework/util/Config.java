package org.format.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;

public class Config {

    public static final String EXTENSION_KEY = "config.Extension";
	
	private static Properties properties;
	static{
        properties = new Properties();
        String property = "config.properties";
        boolean loaded = loadProperties(properties, "." + File.separatorChar + property) || loadProperties(properties, Config.class.getResourceAsStream("/WEB-INF/" + property)) || loadProperties(properties,Config.class.getResourceAsStream("/" + property));
        if(!loaded)
            System.err.println("init failure....");
	}
	/**
	 * 初始化配置文件
	 * @param props
	 * @param path
	 * @return
	 */
    public static boolean loadProperties(Properties props,String path) {
        try {
            File file = new File(path);
            if(file.exists() && file.isFile()){
                props.load(new FileInputStream(file));
                return true;
            }
        } catch (Exception ignore){}
        return false;
    }
    /**
     * 初始化配置文件
     * @param props
     * @param is
     * @return
     */
    public static boolean loadProperties(Properties props, InputStream is) {
        try{
            props.load(is);
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }

    public static boolean getBoolean(String name) {
        String value = getProperty(name);
        return Boolean.valueOf(value);
    }

    public static int getIntProperty(String name) {
        String value = getProperty(name);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return -1;
        }
    }

    public static int getIntProperty(String name, int defaultVal) {
        String value = getProperty(name);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return defaultVal;
        }
    }

    public static long getLongProperty(String name) {
        String value = getProperty(name);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException nfe) {
            return -1;
        }
    }
    public static long getLongProperty(String name,long defaultVal) {
        String value = getProperty(name);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException nfe) {
            return defaultVal;
        }
    }
    public static String getProperty(String name) {
        return getProperty(name, null);
    }

    public static String getProperty(String name, String defaultValue) {
        String value = properties.getProperty(name);
        if(value==null)
            value=defaultValue;
        return value;
    }
    
}
