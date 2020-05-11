package com.xiesx.springboot.core.logger.pkaq;

import java.time.LocalDateTime;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class P6SpyLogger implements MessageFormattingStrategy {

	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
			String sql, String url) {
		// TODO Auto-generated method stub
		return !"".equals(sql.trim()) ? "[ " + LocalDateTime.now() + " ] --- | took " + elapsed + "ms | " + category
				+ " | connection " + connectionId + "\n " + sql + ";" : "";
	}
}