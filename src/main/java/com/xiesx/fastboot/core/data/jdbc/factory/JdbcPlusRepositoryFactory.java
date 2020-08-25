package com.xiesx.fastboot.core.data.jdbc.factory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.ResolvableType;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.jdbc.core.convert.DataAccessStrategy;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.repository.support.JdbcRepositoryFactory;
import org.springframework.data.mapping.PreferredConstructor;
import org.springframework.data.mapping.callback.EntityCallbacks;
import org.springframework.data.mapping.model.PreferredConstructorDiscoverer;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.util.ReflectionUtils;

import com.google.common.base.CaseFormat;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.sql.RelationalPath;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.SQLQueryFactory;
import com.xiesx.fastboot.core.data.jdbc.JdbcPlusRepository;
import com.xiesx.fastboot.core.data.jdbc.JdbcPlusRepositoryExecutor;

public class JdbcPlusRepositoryFactory extends JdbcRepositoryFactory {

    private final RelationalMappingContext context;

    private final JdbcConverter converter;

    private final ApplicationEventPublisher publisher;

    private final DataAccessStrategy accessStrategy;

    private final SQLQueryFactory sqlQueryFactory;

    private EntityCallbacks entityCallbacks;

    public JdbcPlusRepositoryFactory(DataAccessStrategy dataAccessStrategy, RelationalMappingContext context, JdbcConverter converter,
            Dialect dialect, ApplicationEventPublisher publisher, NamedParameterJdbcOperations operations,
            SQLQueryFactory sqlQueryFactory) {
        super(dataAccessStrategy, context, converter, dialect, publisher, operations);
        this.publisher = publisher;
        this.context = context;
        this.converter = converter;
        this.accessStrategy = dataAccessStrategy;
        this.sqlQueryFactory = sqlQueryFactory;
    }

    @Override
    public void setEntityCallbacks(EntityCallbacks entityCallbacks) {
        super.setEntityCallbacks(entityCallbacks);
        this.entityCallbacks = entityCallbacks;
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata repositoryMetadata) {
        return JdbcPlusRepositoryExecutor.class;
    }

    @Override
    protected Object getTargetRepository(RepositoryInformation repositoryInformation) {
        JdbcAggregateTemplate template = new JdbcAggregateTemplate(publisher, context, converter, accessStrategy);
        Class<?> type = repositoryInformation.getDomainType();
        RelationalPath<?> relationalPathBase = getRelationalPathBase(repositoryInformation);
        ConstructorExpression<?> constructor = getConstructorExpression(type, relationalPathBase);
        @SuppressWarnings({"rawtypes", "unchecked"})
        JdbcPlusRepositoryExecutor<?, ?> repository = new JdbcPlusRepositoryExecutor(template, context.getRequiredPersistentEntity(type),
                sqlQueryFactory, constructor, relationalPathBase);
        if (entityCallbacks != null) {
            template.setEntityCallbacks(entityCallbacks);
        }
        return repository;
    }

    private ConstructorExpression<?> getConstructorExpression(Class<?> type, RelationalPath<?> pathBase) {
        PreferredConstructor<?, ?> constructor = PreferredConstructorDiscoverer.discover(type);

        if (constructor == null) {
            throw new IllegalArgumentException("Could not discover preferred constructor for " + type);
        }

        Map<String, Path<?>> columnNameToColumn =
                pathBase.getColumns().stream().collect(Collectors.toMap(column -> column.getMetadata().getName(), Function.identity()));

        Path<?>[] paths = constructor.getParameters()
                .stream()
                .map(PreferredConstructor.Parameter::getName)
                .map(columnNameToColumn::get)
                .toArray(Path[]::new);

        return Projections.constructor(type, paths);
    }

    private RelationalPathBase<?> getRelationalPathBase(RepositoryInformation repositoryInformation) {

        ResolvableType entityType =
                ResolvableType.forClass(repositoryInformation.getRepositoryInterface()).as(JdbcPlusRepository.class).getGeneric(0);
        if (entityType.getRawClass() == null) {
            throw new IllegalArgumentException("Could not resolve query class for " + repositoryInformation);
        }

        return getRelationalPathBase(getQueryClass(entityType.getRawClass()));
    }

    private Class<?> getQueryClass(Class<?> entityType) {
        String fullName = entityType.getPackage().getName() + ".Q" + entityType.getSimpleName();
        try {
            return entityType.getClassLoader().loadClass(fullName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to load class " + fullName);
        }
    }

    private RelationalPathBase<?> getRelationalPathBase(Class<?> queryClass) {
        String fieldName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, queryClass.getSimpleName().substring(1));
        Field field = ReflectionUtils.findField(queryClass, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("Did not find a static field of the same type in " + queryClass);
        }

        return (RelationalPathBase<?>) ReflectionUtils.getField(field, null);
    }
}
