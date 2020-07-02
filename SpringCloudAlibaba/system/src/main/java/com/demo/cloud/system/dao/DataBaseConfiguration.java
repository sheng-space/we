package com.demo.cloud.system.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 多数据源配置
 *
 * @author sheng
 * @date 2020/07/01
 */
@Configuration
public class DataBaseConfiguration {
    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    public DataBaseConfiguration() {
    }

    public Class<? extends DataSource> getDataSourceType() {
        return this.dataSourceType;
    }

    @Bean(
            name = {"writeDataSource"}
    )
    @Primary
    @ConfigurationProperties(
            prefix = "spring.datasource"
    )
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().type(this.dataSourceType).build();
    }

    @Bean(
            name = {"readDataSourceOne"}
    )
    @ConfigurationProperties(
            prefix = "spring.read"
    )
    public DataSource readDataSourceOne() {
        return DataSourceBuilder.create().type(this.dataSourceType).build();
    }
}

