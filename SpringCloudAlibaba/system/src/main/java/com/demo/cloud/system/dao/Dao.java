package com.demo.cloud.system.dao;

import com.demo.cloud.system.util.PageData;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

/**
 * 数据库连接配置
 *
 * @author sheng
 * @date 2020/07/01
 */
@Repository("Dao")
public class Dao {
    @Resource(name = "writeDataSource")
    private DataSource writeDataSource;
    private SqlSessionTemplate sqlSessionTemplate;
    private JdbcTemplate jdbcTemplate;
    @Resource(name = "readDataSourceOne")
    private DataSource readDataSourceOne;
    private SqlSessionTemplate sqlReadSessionTemplate;
    private JdbcTemplate jdbcReadTemplate;
    @Value("${spring.read.type:null}")
    private String readType;
    @Value("${mybatis.config.location}")
    private org.springframework.core.io.Resource configLocation;
    @Value("${mybatis.mapper.locations}")
    private org.springframework.core.io.Resource[] mapperLocations;

    public Dao() {
    }

    private void initWriteDataSource() {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(this.writeDataSource);

        try {
            factoryBean.setMapperLocations(this.mapperLocations);
            factoryBean.setConfigLocation(this.configLocation);
            this.sqlSessionTemplate = new SqlSessionTemplate(factoryBean.getObject());
            this.jdbcTemplate = new JdbcTemplate(this.writeDataSource);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    private void initReadDataSource() {
        if (this.readDataSourceOne != null) {
            SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
            factoryBean.setDataSource(this.readDataSourceOne);

            try {
                factoryBean.setMapperLocations(this.mapperLocations);
                factoryBean.setConfigLocation(this.configLocation);
                this.sqlReadSessionTemplate = new SqlSessionTemplate(factoryBean.getObject());
                this.jdbcReadTemplate = new JdbcTemplate(this.readDataSourceOne);
            } catch (Exception var3) {
                var3.printStackTrace();
            }

        }
    }

    @PostConstruct
    public void init() {
        this.initWriteDataSource();
        this.initReadDataSource();
        if (this.readType == null || this.readType.length() == 0 || "null".equals(this.readType)) {
            this.sqlReadSessionTemplate = this.sqlSessionTemplate;
            this.jdbcReadTemplate = this.jdbcTemplate;
        }

    }

    public Object save(String str, Object obj) throws Exception {
        return this.sqlSessionTemplate.insert(str, obj);
    }

    public Object update(String str, Object obj) throws Exception {
        return this.sqlSessionTemplate.update(str, obj);
    }

    public Object delete(String str, Object obj) throws Exception {
        return this.sqlSessionTemplate.delete(str, obj);
    }

    public Object findForObject(String str, Object obj) throws Exception {
        return this.sqlReadSessionTemplate.selectOne(str, obj);
    }

    public Object findForList(String str, Object obj) throws Exception {
        return this.sqlReadSessionTemplate.selectList(str, obj);
    }

    public Object findForList(String str, Object obj, Boolean bl) throws Exception {
        return bl ? this.sqlSessionTemplate.selectList(str, obj) : this.sqlReadSessionTemplate.selectList(str, obj);
    }

    public Object findForMap(String str, Object obj, String key, String value) throws Exception {
        return this.sqlReadSessionTemplate.selectMap(str, obj, key);
    }

    public Boolean findExist(String sql) {
        SqlRowSet rs = this.jdbcReadTemplate.queryForRowSet(sql);
        return rs.next();
    }

    public Object findSinge(String sql) {
        SqlRowSet rs = this.jdbcReadTemplate.queryForRowSet(sql);
        if (rs.next()) {
            return rs.getObject(1);
        }
        return null;
    }

    public Integer ExecuteSql(String sql) {
        Integer rs = 0;
        rs = this.jdbcTemplate.update(sql);
        return rs;
    }

    public List<PageData> Query(String sql) {
        List<Map<String, Object>> list = this.jdbcReadTemplate.queryForList(sql);
        List<PageData> listPd = new ArrayList();
        if (list != null) {
            for(int i = 0; i < list.size(); ++i) {
                PageData pd = new PageData();
                pd.putAll((Map)list.get(i));
                listPd.add(pd);
            }
        }

        return listPd;
    }

    public Connection getConnection() {
        return this.sqlSessionTemplate.getSqlSessionFactory().openSession().getConnection();
    }

    public SqlSessionTemplate getSqlSessionTemplate() {
        return this.sqlSessionTemplate;
    }

    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setSqlReadSessionTemplate(SqlSessionTemplate sqlReadSessionTemplate) {
        this.sqlReadSessionTemplate = sqlReadSessionTemplate;
    }

    public void setJdbcReadTemplate(JdbcTemplate jdbcReadTemplate) {
        this.jdbcReadTemplate = jdbcReadTemplate;
    }

    public org.springframework.core.io.Resource getConfigLocation() {
        return this.configLocation;
    }

    public org.springframework.core.io.Resource[] getMapperLocations() {
        return this.mapperLocations;
    }
}
