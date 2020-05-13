package com.xiesx.springboot.core.logger.pkaq;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class P6SpyLogger implements MessageFormattingStrategy {

	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared,
			String sql, String url) {
		return "| took " + elapsed + "ms | " + "\n " + sql;
	}
}