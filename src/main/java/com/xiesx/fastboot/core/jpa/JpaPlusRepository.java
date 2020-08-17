package com.xiesx.fastboot.core.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;

@SuppressWarnings("all")
@NoRepositoryBean
public interface JpaPlusRepository<T, ID> extends JpaRepositoryImplementation<T, ID>, QuerydslPredicateExecutor<T> {

    T findOne(ID id);

    @Override
    List<T> findAll(Predicate predicate);

    @Override
    List<T> findAll(Predicate predicate, Sort sort);

    <S> Page<S> findAll(JPAQuery<S> query, Pageable pageable);

    <O extends T> List<O> insert(O... entities);

    int insertOrUpdate(T... entities);

    int delete(ID... ids);

    int delete(JPADeleteClause delete);

    int delete(JPADeleteClause delete, Predicate... predicate);

    int update(JPAUpdateClause update);

    int update(JPAUpdateClause update, Predicate... predicate);
}
