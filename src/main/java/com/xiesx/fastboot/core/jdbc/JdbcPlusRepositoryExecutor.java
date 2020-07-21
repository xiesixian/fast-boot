package com.xiesx.fastboot.core.jdbc;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.data.jdbc.repository.support.SimpleJdbcRepository;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.NonUniqueResultException;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import com.querydsl.sql.RelationalPath;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.dml.SQLUpdateClause;

@Transactional(readOnly = true)
public class JdbcPlusRepositoryExecutor<T, ID> implements JdbcPlusRepository<T, ID> {

    private final SQLQueryFactory sqlQueryFactory;

    private final ConstructorExpression<T> constructorExpression;

    private final RelationalPath<T> path;

    private final SimpleJdbcRepository<T, ID> repository;

    public JdbcPlusRepositoryExecutor(JdbcAggregateOperations entityOperations, PersistentEntity<T, ?> entity,
            SQLQueryFactory sqlQueryFactory, ConstructorExpression<T> constructorExpression, RelationalPath<T> path) {
        this.sqlQueryFactory = sqlQueryFactory;
        this.constructorExpression = constructorExpression;
        this.path = path;
        this.repository = new SimpleJdbcRepository<T, ID>(entityOperations, entity);
    }

    @Override
    @Transactional
    public List<T> save(@SuppressWarnings("unchecked") T... iterable) {
        return Stream.of(iterable).map(this::save).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        return StreamSupport.stream(entities.spliterator(), false).map(this::save).collect(Collectors.toList());
    }

    @Override
    public Optional<T> findOne(Predicate predicate) {
        try {
            return Optional.ofNullable(sqlQueryFactory.query().select(entityProjection()).where(predicate).from(path).fetchOne());
        } catch (NonUniqueResultException ex) {
            throw new IncorrectResultSizeDataAccessException(ex.getMessage(), 1, ex);
        }
    }

    @Override
    public List<T> findAll() {
        return sqlQueryFactory.query().select(entityProjection()).from(path).fetch();
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(T instance) {
        repository.delete(instance);
    }

    @Override
    @Transactional
    public void deleteAll(Iterable<? extends T> entities) {
        repository.deleteAll(entities);
    }

    @Override
    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<T> findAll(Predicate predicate) {
        return sqlQueryFactory.query().select(entityProjection()).from(path).where(predicate).fetch();
    }

    @Override
    public <O> O query(Function<SQLQuery<?>, O> query) {
        return query.apply(sqlQueryFactory.query());
    }

    @Override
    @Transactional
    public void update(Consumer<SQLUpdateClause> update) {
        update.accept(sqlQueryFactory.update(path));
    }

    @Override
    @Transactional
    public long deleteWhere(Predicate predicate) {
        return sqlQueryFactory.delete(path).where(predicate).execute();
    }

    @Override
    public Expression<T> entityProjection() {
        return constructorExpression;
    }

    @Override
    @Transactional
    public <S extends T> S save(S instance) {
        return repository.save(instance);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }
}
