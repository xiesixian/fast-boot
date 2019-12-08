package com.xiesx.gotv.support.datebase.mpbatis.cfg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

@Configuration
@MapperScan(basePackages = { "com.xiesx.gotv.services*", "com.xiesx.gotv.support*" })
public class MPlusCfg {

	/**
	 * mybatis-plus SQL执行效率插件【生产环境可以关闭】
	 */
//	@Bean
//	public PerformanceInterceptor performanceInterceptor() {
//		return new PerformanceInterceptor();
//	}

	/**
	 * mybatis-plus分页插件<br>
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		//paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
		return paginationInterceptor;
	}
}