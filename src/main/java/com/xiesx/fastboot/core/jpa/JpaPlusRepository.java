package com.xiesx.fastboot.core.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;

@SuppressWarnings("unchecked")
@NoRepositoryBean
public interface JpaPlusRepository<T, ID> extends JpaRepositoryImplementation<T, ID>, QuerydslPredicateExecutor<T> {

    <O> Page<O> findAll(JPAQuery<O> query, Pageable pageable);

    <O> Page<O> findAll(JPAQuery<O> query, Pageable pageable, OrderSpecifier<?>... orders);

    <S extends T> List<S> insert(S... entities);

    int insertOrUpdate(T... entities);

    int delete(ID... ids);

    int delete(JPADeleteClause delete, Predicate... predicate);

    int update(JPAUpdateClause update, Predicate... predicate);
}
