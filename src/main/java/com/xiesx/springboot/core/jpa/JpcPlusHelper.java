package com.xiesx.springboot.core.jpa;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.google.common.collect.Lists;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JpcPlusHelper {

	private Class<?> tupleClass;

	private List<Expression<?>> expressions;

	private List<EntityPath<?>> entityPaths;

	private List<Predicate> predicates;

	private List<OrderSpecifier<?>> orderSpecifiers;

	private Pageable pageable;

	public static JpcPlusHelper create() {
		return JpcPlusHelper.builder()
				.entityPaths(Lists.newArrayList())
				.expressions(Lists.newArrayList())
				.predicates(Lists.newArrayList())
				.orderSpecifiers(Lists.newArrayList())
				.build();
	}

	public void addEntityPath(EntityPath<?>... entityPath) {
		entityPaths.addAll(Arrays.asList(entityPath));
	}

	public void addExpression(Expression<?>... expression) {
		expressions.addAll(Arrays.asList(expression));
	}

	public void addPredicate(Predicate... predicate) {
		predicates.addAll(Arrays.asList(predicate));
	}

	public void addOrder(OrderSpecifier<?>... orderSpecifier) {
		orderSpecifiers.addAll(Arrays.asList(orderSpecifier));
	}

	public void addPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	public Expression<?>[] expressionToArray() {
		return expressions.toArray(new Expression[expressions.size()]);
	}

	public EntityPath<?>[] entityPathToArray() {
		return entityPaths.toArray(new EntityPath[entityPaths.size()]);
	}

	public Predicate[] predicatesToArray() {
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	public OrderSpecifier<?>[] orderSpecifiersToArray() {
		return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
	}
}
