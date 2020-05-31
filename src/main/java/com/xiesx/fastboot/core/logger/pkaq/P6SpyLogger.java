package com.xiesx.fastboot.core.logger.pkaq;

import org.apache.commons.lang3.StringUtils;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

public class P6SpyLogger implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        if (StringUtils.isNotEmpty(sql)) {
            return "| took " + elapsed + "ms | " + " | " + category + " | " + sql;
        }
        return "| took " + elapsed + "ms| " + " |  " + category;
    }
}
