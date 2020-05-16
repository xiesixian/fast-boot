package com.xiesx.springboot.core.jpa;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;

@SuppressWarnings("unchecked")
@NoRepositoryBean
public interface JpaPlusRepository<T, ID> extends JpaRepositoryImplementation<T, ID>, QuerydslPredicateExecutor<T> {

    int delete(ID... ids);

    <O> O findAll(Function<JPAQuery<?>, O> query);

    Page<Tuple> findAll(JpcPlusHelper helper);

    <O> Page<O> findAll(JpcPlusHelper helper, Class<O> cla);

    <S extends T> List<S> save(S... entities);

    int saveOrUpdate(T... entities);

    void update(Consumer<JPAUpdateClause> update, Predicate... predicate);
}
