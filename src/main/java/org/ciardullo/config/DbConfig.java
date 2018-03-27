package org.ciardullo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.ciardullo.database.ClientMapper;
import org.ciardullo.model.Clientele;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
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
    public MapperFactoryBean<ClientMapper> clientMapper() throws Exception {
        MapperFactoryBean<ClientMapper> mfb = new MapperFactoryBean<>(ClientMapper.class);
        mfb.setSqlSessionFactory(sqlSessionFactory());
        return mfb;
    }
}
