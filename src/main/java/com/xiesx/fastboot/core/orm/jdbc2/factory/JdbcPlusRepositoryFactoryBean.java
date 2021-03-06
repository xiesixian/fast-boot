package com.xiesx.fastboot.core.orm.jdbc2.factory;

import java.io.Serializable;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jdbc.core.convert.DataAccessStrategy;
import org.springframework.data.jdbc.core.convert.DefaultDataAccessStrategy;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.convert.SqlGeneratorSource;
import org.springframework.data.jdbc.repository.QueryMappingConfiguration;
import org.springframework.data.jdbc.repository.support.JdbcRepositoryFactoryBean;
import org.springframework.data.mapping.callback.EntityCallbacks;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.util.Assert;

import com.querydsl.sql.SQLQueryFactory;

public class JdbcPlusRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable>
        extends JdbcRepositoryFactoryBean<T, S, ID> {

    private ApplicationEventPublisher publisher;

    private BeanFactory beanFactory;

    private RelationalMappingContext mappingContext;

    private JdbcConverter converter;

    private DataAccessStrategy dataAccessStrategy;

    private QueryMappingConfiguration queryMappingConfiguration = QueryMappingConfiguration.EMPTY;

    private NamedParameterJdbcOperations operations;

    private EntityCallbacks entityCallbacks;

    private Dialect dialect;

    private SQLQueryFactory sqlQueryFactory;

    protected JdbcPlusRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {

        super.setApplicationEventPublisher(publisher);
        this.publisher = publisher;
    }

    @Override
    protected RepositoryFactorySupport doCreateRepositoryFactory() {
        JdbcPlusRepositoryFactory jdbcRepositoryFactory = new JdbcPlusRepositoryFactory(dataAccessStrategy, mappingContext, converter,
                dialect, publisher, operations, sqlQueryFactory);
        jdbcRepositoryFactory.setQueryMappingConfiguration(queryMappingConfiguration);
        jdbcRepositoryFactory.setEntityCallbacks(entityCallbacks);
        return jdbcRepositoryFactory;
    }

    @Override
    @Autowired
    protected void setMappingContext(RelationalMappingContext mappingContext) {

        super.setMappingContext(mappingContext);
        this.mappingContext = mappingContext;
    }

    @Override
    public void setDataAccessStrategy(DataAccessStrategy dataAccessStrategy) {
        super.setDataAccessStrategy(dataAccessStrategy);
        this.dataAccessStrategy = dataAccessStrategy;
    }

    @Override
    @Autowired(required = false)
    public void setQueryMappingConfiguration(QueryMappingConfiguration queryMappingConfiguration) {
        super.setQueryMappingConfiguration(queryMappingConfiguration);
        this.queryMappingConfiguration = queryMappingConfiguration;
    }

    @Autowired
    public void setSQLQueryFactory(SQLQueryFactory sqlQueryFactory) {
        this.sqlQueryFactory = sqlQueryFactory;
    }

    @Override
    public void setJdbcOperations(NamedParameterJdbcOperations operations) {
        this.operations = operations;
    }

    @Override
    @Autowired
    public void setConverter(JdbcConverter converter) {
        super.setConverter(converter);
        this.converter = converter;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() {

        Assert.state(this.mappingContext != null, "MappingContext is required and must not be null!");
        Assert.state(this.converter != null, "RelationalConverter is required and must not be null!");

        if (this.operations == null) {

            Assert.state(beanFactory != null, "If no JdbcOperations are set a BeanFactory must be available.");

            this.operations = beanFactory.getBean(NamedParameterJdbcOperations.class);
        }

        if (this.dataAccessStrategy == null) {

            Assert.state(beanFactory != null, "If no DataAccessStrategy is set a BeanFactory must be available.");

            this.dataAccessStrategy = this.beanFactory.getBeanProvider(DataAccessStrategy.class) //
                    .getIfAvailable(() -> {
                        SqlGeneratorSource sqlGeneratorSource = new SqlGeneratorSource(this.mappingContext, this.converter, this.dialect);
                        return new DefaultDataAccessStrategy(sqlGeneratorSource, this.mappingContext, this.converter, this.operations);
                    });
        }

        if (this.queryMappingConfiguration == null) {
            this.queryMappingConfiguration = QueryMappingConfiguration.EMPTY;
        }

        if (beanFactory != null) {
            entityCallbacks = EntityCallbacks.create(beanFactory);
        }

        super.afterPropertiesSet();
    }
}
