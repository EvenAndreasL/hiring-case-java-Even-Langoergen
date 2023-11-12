/**
 * 
 */
package no.hnikt.patgen.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.Environment;

/**
 * @author Wesley Snipes
 *
 */
@Configuration
public class DirectoryVerifier implements BeanFactoryPostProcessor, PriorityOrdered {

	private static final Logger LOG = LoggerFactory.getLogger(DirectoryVerifier.class);
	
	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Environment environment = beanFactory.getBean(Environment.class);
		
		String dirname = environment.getProperty("dirname");
		if ("".equals(dirname)) {
			LOG.info("Reading default files from classpath");
		} else {
			File directory = new File(dirname);
			if (!directory.exists() || !directory.isDirectory()) {
				throw new ApplicationContextException("Directory " + dirname + " does not exist ");
			} else {
				LOG.info("Reading files from external directory " + dirname);
			}
		}
	}
}
