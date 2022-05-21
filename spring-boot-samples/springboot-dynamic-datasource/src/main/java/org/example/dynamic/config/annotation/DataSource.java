package org.example.dynamic.config.annotation;


import org.example.dynamic.config.DataSourceName;

import java.lang.annotation.*;

/**
 * 多数据源注解
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
	DataSourceName name() default DataSourceName.MASTER;
}
