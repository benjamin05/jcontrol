package mx.com.lux.control.trabajos.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceLocator {

	private static final Logger log = LoggerFactory.getLogger( ServiceLocator.class );

	private static ApplicationContext context;

	static {
		context = new ClassPathXmlApplicationContext( "classpath:spring-config.xml" );
	}

	public static Object getBean( String beanName ) {
		log.debug( "Getting bean with name: " + beanName );
		return context.getBean( beanName );
	}

	public static <T> T getBean( String beanName, Class<T> clazz ) {
		log.debug( "Getting bean with name: " + beanName + " of Class: " + clazz.getSimpleName() );
		return context.getBean( beanName, clazz );
	}

	public static <T> T getBean( Class<T> clazz ) {
		log.debug( "Getting bean of Class: " + clazz.getSimpleName() );
		return context.getBean( clazz );
	}
}
