package com.xiesx.fastboot.core.jdbc.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiesx.fastboot.core.jdbc.JdbcPlusTemplate;

@Configuration
@Import({JdbcPlusTemplate.class})
public class JdbcPlusCfg {

}
