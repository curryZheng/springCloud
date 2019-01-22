package com.demo.framework.config;

import java.util.Properties;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import com.github.pagehelper.PageHelper;

@Configuration
@MapperScan("com.demo.*.dao")
public class DataSourceConfig {



	@Bean(name = "dataSource")
	@ConfigurationProperties("spring.dataSource")
	public DataSource dataSource() {
		DataSource dataSource = new DataSource();
		return dataSource;

	}

	@Bean
	public DataSourceTransactionManager transactionManager(DataSource dynamicDataSource) {
		DataSourceTransactionManager my = new DataSourceTransactionManager();
		my.setDataSource(dynamicDataSource);
		return my;
	}

	@ConfigurationProperties(prefix="mybatis")
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactoryBean sqlSessionFactory(DataSource dynamicDataSource) throws Exception {

		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dynamicDataSource);
		//分页插件
		sessionFactory.setPlugins(new Interceptor[] {createPageHelper()});

		return sessionFactory;
	}

	/**
	 * 分页控件
	 * 
	 * @return
	 */
	@Bean
	public PageHelper createPageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties p = new Properties();
		p.setProperty("offsetAsPageNum", "true");
		p.setProperty("rowBoundsWithCount", "true");
		p.setProperty("reasonable", "true");
		pageHelper.setProperties(p);
		return pageHelper;
	}

}
