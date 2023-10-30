package com.example.shamanApi.factory;

import java.util.UUID;
import org.dozer.BeanFactory;
/**
 * UuidBeanFactory initializuje ID obiektu podczas mapowania
 */
public class UuidBeanFactory implements BeanFactory {

    /**
     * generuje Bean'a
     *
     * @param sourceBean
     * @param destinationType
     * @param mapId
     * @return nowy UUID
     */
    public Object createBean(Object sourceBean, Class<?> destinationType, String mapId) {
        if (sourceBean == null) {
            return null;
        }
        UUID source = (UUID) sourceBean;
        UUID destination = new UUID(source.getMostSignificantBits(), source.getLeastSignificantBits());
        return destination;
    }
}
