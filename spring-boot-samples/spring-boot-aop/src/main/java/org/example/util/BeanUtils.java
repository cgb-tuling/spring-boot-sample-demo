package org.example.util;

import org.example.annotation.NeedSetValue;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yct
 */
@Component
public class BeanUtils implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //1拿注解-->method-> java Bean -->Spring -->applicationContext
    public void setFeildValue(Collection collection) throws Throwable {
        Class<?> aClass = collection.iterator().next().getClass();
        Field[] fields = aClass.getDeclaredFields();
        Map<String, Object> cacheMap = new HashMap<>();
        for (Field needField : fields) {
            NeedSetValue annotation = needField.getAnnotation(NeedSetValue.class);
            if (annotation == null) {
                continue;
            }
            needField.setAccessible(true);
            Object bean = this.applicationContext.getBean(annotation.beanClass());
            Method method = annotation.beanClass().getMethod(annotation.method(), aClass.getDeclaredField(annotation.param()).getType());
            //customerId
            Field paramField = aClass.getDeclaredField(annotation.param());
            paramField.setAccessible(true);
            Field targetField = null;
            Boolean needInnerField = StringUtils.isEmpty(annotation.targetFiled());
            String keyPrefix = annotation.beanClass() + "-" + annotation.method() + "-" + annotation.targetFiled() + "-";
            for (Object obj : collection) {
                Object paramValue = paramField.get(obj);
                if (paramValue == null) {
                    continue;
                }
                Object value = null;
                //先从缓存拿
                String key = keyPrefix + paramValue;
                if (cacheMap.containsKey(key)) {
                    value = cacheMap.get(key);
                } else {
                    value = method.invoke(bean, paramValue);
                    if (needInnerField) {
                        if (value != null) {
                            if (targetField == null) {
                                targetField = value.getClass().getDeclaredField(annotation.targetFiled());
                            }
                            value = targetField.get(value);
                        }
                    }
                    cacheMap.put(key, value);
                }
                needField.set(obj, value);
            }
        }
    }
}