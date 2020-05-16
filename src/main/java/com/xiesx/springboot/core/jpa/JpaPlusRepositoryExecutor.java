package com.xiesx.springboot.core.jpa;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

@SuppressWarnings("unchecked")
@Transactional(readOnly = true)
public class JpaPlusRepositoryExecutor<T, ID> extends SimpleJpaRepository<T, ID> implements JpaPlusRepository<T, ID> {

    protected final EntityManager entityManager;

    protected final JPAQueryFactory jpaQueryFactory;

    private final EntityPath<T> path;

    protected final Querydsl querydsl;

    protected final QuerydslJpaPredicateExecutor<T> jpaPredicateExecutor;

    public JpaPlusRepositoryExecutor(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
        this.path = SimpleEntityPathResolver.INSTANCE.createPath(domainClass);
        this.querydsl = new Querydsl(entityManager, new PathBuilder<T>(path.getType(), path.getMetadata()));
        this.jpaPredicateExecutor = new QuerydslJpaPredicateExecutor<>(
                JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager), entityManager,
                SimpleEntityPathResolver.INSTANCE, getRepositoryMethodMetadata());
    }

    // ==========================

    @Override
    public long count(Predicate predicate) {
        return jpaPredicateExecutor.count(predicate);
    }

    @Deprecated
    @Transactional
    @Override
    public int delete(ID... ids) {
        Assert.notNull(ids, "Ids must not be null!");
        int rows = 0;
        for (ID id : ids) {
            try {
                deleteById(id);
                rows++;
            } catch (Exception e) {

            }
        }
        return rows;
    }

    @Override
    public boolean exists(Predicate predicate) {
        return jpaPredicateExecutor.exists(predicate);
    }

    @Deprecated
    @Override
    public <O> O findAll(Function<JPAQuery<?>, O> query) {
        return query.apply(jpaQueryFactory.query());
    }

    @Override
    @Deprecated
    public Page<Tuple> findAll(JpcPlusHelper helper) {
        // 数量
        JPAQuery<Tuple> countQuery = jpaQueryFactory.select(helper.expressionToArray())
                .from(helper.entityPathToArray())
                .where(helper.predicatesToArray());
        // 分页查询
        JPQLQuery<Tuple> query =
                querydsl.applyPagination(helper.getPageable(), countQuery).orderBy(helper.orderSpecifiersToArray());
        // 构造分页
        return PageableExecutionUtils.getPage(query.fetch(), helper.getPageable(), countQuery::fetchCount);
    }

    @Override
    @Deprecated
    public <O> Page<O> findAll(JpcPlusHelper helper, Class<O> cla) {
        // 数据结果
        List<O> content = Lists.newArrayList();
        // 分页结果
        Page<Tuple> page = findAll(helper);
        for (Tuple row : page) {
            // 取出每一条
            Map<String, Object> map = Maps.newConcurrentMap();
            // 获取投影结果
            for (Expression<?> expression : helper.getExpressions()) {
                if (ObjectUtils.isNotEmpty(row.get(expression))) {
                    if (expression.toString().contains("as")) {
                        map.put(expression.toString().split(" as ")[1], row.get(expression));
                    } else {
                        map.put(expression.toString(), row.get(expression));
                    }
                }
            }
            // 转换Class
            content.add(JSON.toJavaObject(new JSONObject(map), cla));
        }
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    @Override
    public Iterable<T> findAll(OrderSpecifier<?>... orders) {
        return jpaPredicateExecutor.findAll(orders);
    }

    @Override
    public Iterable<T> findAll(Predicate predicate) {
        return jpaPredicateExecutor.findAll(predicate);
    }

    @Override
    public Iterable<T> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        return jpaPredicateExecutor.findAll(predicate, orders);
    }

    @Override
    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        return jpaPredicateExecutor.findAll(predicate, pageable);
    }

    @Override
    public Iterable<T> findAll(Predicate predicate, Sort sort) {
        return jpaPredicateExecutor.findAll(predicate, sort);
    }

    @Override
    public Optional<T> findOne(Predicate predicate) {
        return jpaPredicateExecutor.findOne(predicate);
    }

    // ==========================

    @Deprecated
    @Transactional
    @Override
    public <S extends T> List<S> save(S... entities) {
        List<S> list = saveAll(Arrays.asList(entities));
        entityManager.flush();
        return list;
    }

    @Deprecated
    @Transactional
    @Override
    public int saveOrUpdate(T... entities) {
        return save(entities).size();
    }

    @Deprecated
    @Transactional
    @Override
    public void update(Consumer<JPAUpdateClause> update, Predicate... predicate) {
        update.accept(jpaQueryFactory.update(path).where(predicate));
    }
}
