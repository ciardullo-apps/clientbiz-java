package org.ciardullo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@MapperScan("org.ciardullo.data.mapper")
@PropertySource("classpath:config/db.properties")
public class DbConfig {
    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource =
                new DriverManagerDataSource(env.getProperty("db.url"),
                        env.getProperty("db.username"),
                        env.getProperty("db.password"));
        Properties connProps = new Properties();

        // MySQL 8 default character set is UTF-8
        connProps.setProperty("useUnicode", env.getProperty("db.useUnicode"));
        connProps.setProperty("characterEncoding", env.getProperty("db.characterEncoding"));
        dataSource.setConnectionProperties(connProps);

        dataSource.setDriverClassName(env.getProperty("db.driverClassName"));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionalManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean ss = new SqlSessionFactoryBean();
        ss.setDataSource(dataSource());
        return ss.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSession() throws Exception {
        SqlSessionTemplate sst = new SqlSessionTemplate(sqlSessionFactory());
        return sst;
    }

}
