package com.xiesx.springboot.core.jpa.identifier;

import java.io.Serializable;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class IdWorkerGenerator implements Configurable, IdentifierGenerator {

    public String pre;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        this.pre = params.getProperty("prefix");
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        if (StringUtils.isNoneEmpty(this.pre)) {
            return this.pre + IdWorker.getID();
        }
        return IdWorker.getID();
    }
}
