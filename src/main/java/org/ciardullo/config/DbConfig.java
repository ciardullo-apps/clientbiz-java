package org.ciardullo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan("org.ciardullo.data.mapper")
public class DbConfig {
    @Bean
    public DataSource dataSource() {
        // TODO Parameterize credentials
        DriverManagerDataSource dataSource =
                new DriverManagerDataSource("jdbc:mysql://localhost:3306/clientbiz", "john", "test");
        // TODO Parameterize driver name
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
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
