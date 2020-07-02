package com.demo.cloud.system.dao;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;

/**
 * 其他数据库连接配置
 *
 * @author sheng
 * @date 2020/07/01
 */
@Repository("OtherDao")
public class OtherDao extends Dao {
    public OtherDao() {
    }

    public void initDao(DataSource dataSource) {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        try {
            factoryBean.setMapperLocations(this.getMapperLocations());
            factoryBean.setConfigLocation(this.getConfigLocation());
            SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(factoryBean.getObject());
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            this.setSqlSessionTemplate(sqlSessionTemplate);
            this.setJdbcTemplate(jdbcTemplate);
            this.setSqlReadSessionTemplate(sqlSessionTemplate);
            this.setJdbcReadTemplate(jdbcTemplate);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }
}