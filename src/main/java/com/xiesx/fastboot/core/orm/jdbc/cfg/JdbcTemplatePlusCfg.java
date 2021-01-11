package com.xiesx.fastboot.core.orm.jdbc.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiesx.fastboot.core.orm.jdbc.JdbcTemplatePlus;

@Configuration
@Import({JdbcTemplatePlus.class})
public class JdbcTemplatePlusCfg {

}
