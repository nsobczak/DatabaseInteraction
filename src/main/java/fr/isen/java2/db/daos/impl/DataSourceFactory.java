package fr.isen.java2.db.daos.impl;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataSourceFactory {

	private static MysqlDataSource dataSource;

	public static DataSource getDataSource() throws IOException {
		if (dataSource == null) {
			createDataSource();
		}
		return dataSource;
	}


	private static void createDataSource() throws IOException {
		Properties dbProperties = new Properties();
        try(InputStream dbPropertiesStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")){
            dbProperties.load(dbPropertiesStream);
            dataSource = new MysqlDataSource();
            dataSource.setServerName(dbProperties.getProperty("db.server"));
            dataSource.setPort(Integer.valueOf(dbProperties.getProperty("db.port")));
            dataSource.setDatabaseName(dbProperties.getProperty("db.schema"));
            dataSource.setUser(dbProperties.getProperty("db.user"));
            dataSource.setPassword(dbProperties.getProperty("db.password"));
        }

	}
}
