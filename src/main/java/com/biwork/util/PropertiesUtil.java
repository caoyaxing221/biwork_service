package com.biwork.util;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	
	private static String[] configs={"/config.properties"};
	private static PropertiesUtil instance;
	private Resource resource = null;
	private Properties props = null;

	private PropertiesUtil(){
		refresh();
	}
	
	  private synchronized void refresh()
	  {
	    if (null != this.props) {
	      this.props.clear();
	    }
	    for (String path : configs)
	      try {
	        this.resource = new ClassPathResource(path);
	        if (null == this.props)
	          this.props = PropertiesLoaderUtils.loadProperties(this.resource);
	        else
	          this.props.putAll(PropertiesLoaderUtils.loadProperties(this.resource));
	      }
	      catch (IOException e) {
	        logger.error(e.getMessage(), e);
	      }
	  }
	  private static synchronized void checkInstance()
	  {
	    if (instance == null)
	      synchronized (configs) {
	        if (instance == null)
	          instance = new PropertiesUtil();
	      }
	  }
	  public static String getProperty(String key)
	  {
	    checkInstance();
	    return instance.props.getProperty(key);
	  }

	  public static String getProperty(String key, String defStr)
	  {
	    checkInstance();
	    return instance.props.getProperty(key, defStr);
	  }

	public static boolean containsKey(String key) {
		checkInstance();
	    return instance.props.containsKey(key);
	}
}
