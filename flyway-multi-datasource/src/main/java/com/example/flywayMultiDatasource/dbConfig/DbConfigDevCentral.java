/**
 * 
 */
package com.example.flywayMultiDatasource.dbConfig;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import lombok.extern.slf4j.Slf4j;

/**
 * @author VedantRaj
 *
 */
@Slf4j
@Configuration
public class DbConfigDevCentral {
	private String driverClassName = "com.mysql.cj.jdbc.Driver";

	@Value("${dev_central.datasource.url}")
	private String url;

	@Value("${dev_central.datasource.username}")
	private String userName;

	@Value("${dev_central.datasource.password}")
	private String password;

	@Bean
	public DataSource dataSourceDevCentral() {
		log.info("DataSource creation from dataSource(), with url{}, username{}, password{}", url, userName, password);

		@SuppressWarnings("rawtypes")
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(driverClassName);
		dataSourceBuilder.url(url);
		dataSourceBuilder.username(userName);
		dataSourceBuilder.password(password);
		return dataSourceBuilder.build();
	}
	
	  @Bean
	  public Flyway devFlywayCentral() throws Exception {
	    ClassicConfiguration configuration = new ClassicConfiguration();
	    configuration.setBaselineOnMigrate(true);
//	    configuration.setTable("new_table");
	    configuration.setLocationsAsStrings("db/migration/devCentral");
	    configuration.setDataSource(dataSourceDevCentral());
	    Flyway flyway = new Flyway(configuration);
//	    flyway.repair();
	    flyway.migrate();
	    return flyway;
	  }
}

