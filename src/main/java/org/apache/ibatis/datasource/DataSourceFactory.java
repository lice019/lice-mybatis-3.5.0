package org.apache.ibatis.datasource;

import java.util.Properties;
import javax.sql.DataSource;

/**
 * DataSourceFactory：数据库资源工厂，将由jndi、pooled、unpooled三种数据连接工厂实现
 *
 * @author Clinton Begin
 */
public interface DataSourceFactory {

    //设置属性
    void setProperties(Properties props);

    //获取数据库资源，JDK中的接口规范。可以获取数据库连接对象Connection
    DataSource getDataSource();

}
