package com.xiesx.springboot.core.jdbc.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.xiesx.springboot.core.jdbc.JdbcPlusTemplate;

@Configuration
@Import({JdbcPlusTemplate.class})
public class JdbcPlusCfg {
}
