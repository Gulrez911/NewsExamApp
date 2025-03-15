package com.ctet;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySources({ @PropertySource("classpath:mail.properties"), @PropertySource("classpath:database.properties") })
@ComponentScan({ "com.ctet" })
@EnableJpaRepositories(basePackages = "com.ctet.repositories")
public class PersistenceJPAConfig {

	@Autowired
	private Environment env;

	private final Logger logger = LoggerFactory.getLogger(PersistenceJPAConfig.class);

	public PersistenceJPAConfig() {
		super();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan(new String[] { "com.ctet.data", "com.ctet.entity" });

//        entityManagerFactoryBean.setPackagesToScan(new String[] { "net.javaguides.springmvc.entity" });

		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactoryBean.setJpaProperties(additionalProperties());

		return entityManagerFactoryBean;
	}

	final Properties additionalProperties() {
		final Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		hibernateProperties.setProperty("hibernate.cache.use_second_level_cache",
				env.getProperty("hibernate.cache.use_second_level_cache"));
		hibernateProperties.setProperty("hibernate.cache.use_query_cache",
				env.getProperty("hibernate.cache.use_query_cache"));

		hibernateProperties.setProperty("hibernate.dialect.storage_engine",
				env.getProperty("hibernate.dialect.storage_engine"));

		hibernateProperties.setProperty("hibernate.connection.CharSet", "utf8");
		hibernateProperties.setProperty("hibernate.connection.characterEncoding", "utf8");
		hibernateProperties.setProperty("hibernate.connection.useUnicode", "true");
		hibernateProperties.setProperty("hibernate.c3p0.max_size", env.getProperty("hibernate.c3p0.max_size"));
		hibernateProperties.setProperty("hibernate.c3p0.min_size", env.getProperty("hibernate.c3p0.min_size"));
		hibernateProperties.setProperty("hibernate.c3p0.acquire_increment",
				env.getProperty("hibernate.c3p0.acquire_increment"));

		hibernateProperties.setProperty("hibernate.c3p0.idle_test_period",
				env.getProperty("hibernate.c3p0.idle_test_period"));
		hibernateProperties.setProperty("hibernate.c3p0.max_statements",
				env.getProperty("hibernate.c3p0.max_statements"));
		hibernateProperties.setProperty("hibernate.c3p0.timeout", env.getProperty("hibernate.c3p0.timeout"));
		// hibernateProperties.setProperty("hibernate.globally_quoted_identifiers",
		// "true");
		hibernateProperties.setProperty("hibernate.c3p0.initialPoolSize",
				env.getProperty("hibernate.c3p0.initialPoolSize"));
		hibernateProperties.setProperty("hibernate.c3p0.minPoolSize", env.getProperty("hibernate.c3p0.minPoolSize"));
		hibernateProperties.setProperty("hibernate.c3p0.maxPoolSize", env.getProperty("hibernate.c3p0.maxPoolSize"));
		hibernateProperties.setProperty("hibernate.c3p0.acquireIncrement",
				env.getProperty("hibernate.c3p0.acquireIncrement"));
		hibernateProperties.setProperty("hibernate.c3p0.maxStatements",
				env.getProperty("hibernate.c3p0.maxStatements"));

//		Retries 
		hibernateProperties.setProperty("hibernate.c3p0.maxStatementsPerConnection",
				env.getProperty("hibernate.c3p0.maxStatementsPerConnection"));
		hibernateProperties.setProperty("hibernate.c3p0.acquireRetryAttempts",
				env.getProperty("hibernate.c3p0.acquireRetryAttempts"));
		hibernateProperties.setProperty("hibernate.c3p0.acquireRetryDelay",
				env.getProperty("hibernate.c3p0.acquireRetryDelay"));
		hibernateProperties.setProperty("hibernate.c3p0.breakAfterAcquireFailure",
				env.getProperty("hibernate.c3p0.breakAfterAcquireFailure"));

//		Refreshing Connections
		hibernateProperties.setProperty("hibernate.c3p0.maxIdleTime", env.getProperty("hibernate.c3p0.maxIdleTime"));
		hibernateProperties.setProperty("hibernate.c3p0.maxConnectionAge",
				env.getProperty("hibernate.c3p0.maxConnectionAge"));

//		Timeouts And Testing 
		hibernateProperties.setProperty("hibernate.c3p0.checkoutTimeout",
				env.getProperty("hibernate.c3p0.checkoutTimeout"));
		hibernateProperties.setProperty("hibernate.c3p0.idleConnectionTestPeriod",
				env.getProperty("hibernate.c3p0.idleConnectionTestPeriod"));
		hibernateProperties.setProperty("hibernate.c3p0.testConnectionOnCheckout",
				env.getProperty("hibernate.c3p0.testConnectionOnCheckout"));
		hibernateProperties.setProperty("hibernate.c3p0.preferredTestQuery",
				env.getProperty("hibernate.c3p0.preferredTestQuery"));
		hibernateProperties.setProperty("hibernate.c3p0.testConnectionOnCheckin",
				env.getProperty("hibernate.c3p0.testConnectionOnCheckin"));
		return hibernateProperties;
	}

	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.pass"));
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

//	@Bean
//	public TaskScheduler  taskScheduler() {
//	    ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
//	    threadPoolTaskScheduler.setPoolSize(5);
//	    threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
//	    return threadPoolTaskScheduler;
//	}

	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(5);
		threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
		logger.info("ThreadPoolTaskScheduler is configured with pool size: " + 5);
		return threadPoolTaskScheduler;
	}

}