package org.example.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

//@Configuration
public class CustomizeDataSourceInitializer {

    @Value("classpath:sql/schema-mysql.sql")
    private Resource sqlScriptSchema;
    @Value("classpath:sql/data.sql")
    private Resource sqlScriptData;
    @Value("classpath:sql/procedure.sql")
    private Resource sqlScriptProcedure;
//    @Value("classpath:sql/function.sql")
//    private Resource sqlScriptFunction;
	
    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource){
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(databasePopulator());
        return dataSourceInitializer;
    }

    private DatabasePopulator databasePopulator(){
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(sqlScriptSchema);
        populator.addScript(sqlScriptProcedure);
        populator.addScript(sqlScriptData);
//        populator.addScript(sqlScriptFunction);
        populator.setSeparator("$$$"); // 分隔符，默认为;
        return populator;
    }
}