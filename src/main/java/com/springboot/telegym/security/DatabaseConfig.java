package com.springboot.telegym.security;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

//@Configuration
public class DatabaseConfig {

    public static String loginName;

    public static String password;

    @Bean
    public DataSource getDataSource()
    {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSourceBuilder.url("jdbc:sqlserver://BUBBLENGUYENPC;databaseName=Telegym");
        dataSourceBuilder.username(loginName);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}
