package com.ariari.ariari.commons.pkgenerator;

import com.github.f4b6a3.tsid.TsidCreator;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.util.Properties;

/**
 * Primary key generator
 *  -> Tsid 사용
 */
public class CustomPkGenerator implements IdentifierGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        return TsidCreator.getTsid().toLong();
    }

}

/**
 * problem (create additional table(_seq))
 */
//public class CustomPkGenerator extends SequenceStyleGenerator {
//
//    @Override
//    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
//        return TsidCreator.getTsid().toLong();
//    }
//
//    @Override
//    public void configure(Type type, Properties parameters, ServiceRegistry serviceRegistry) throws MappingException {
//        super.configure(type, parameters, serviceRegistry);
//    }
//
//}
