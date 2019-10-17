package org.apache.ibatis.datasource.jndi;

import java.util.Map.Entry;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceException;
import org.apache.ibatis.datasource.DataSourceFactory;

/**
 * JndiDataSourceFactory：Jndi类型的连接类型的工厂。
 *
 * @author Clinton Begin
 */
public class JndiDataSourceFactory implements DataSourceFactory {

    //最初的内容
    public static final String INITIAL_CONTEXT = "initial_context";

    //数据库资源的内容
    public static final String DATA_SOURCE = "data_source";

    public static final String ENV_PREFIX = "env.";

    //数据库资源
    private DataSource dataSource;

    @Override
    public void setProperties(Properties properties) {
        try {
            /**
             * 这个类是执行命名操作的起始上下文。
             * 所有命名操作都与上下文相关。 初始上下文实现了Context接口，并提供了解析名称的起点。
             * 当构建初始上下文时，将使用传递给构造函数的环境参数中定义的属性和任何application resource files对其环境进行初始化
             */
            InitialContext initCtx;
            Properties env = getEnvProperties(properties);
            if (env == null) {
                initCtx = new InitialContext();
            } else {
                initCtx = new InitialContext(env);
            }

            if (properties.containsKey(INITIAL_CONTEXT)
                    && properties.containsKey(DATA_SOURCE)) {
                Context ctx = (Context) initCtx.lookup(properties.getProperty(INITIAL_CONTEXT));
                dataSource = (DataSource) ctx.lookup(properties.getProperty(DATA_SOURCE));
            } else if (properties.containsKey(DATA_SOURCE)) {
                dataSource = (DataSource) initCtx.lookup(properties.getProperty(DATA_SOURCE));
            }

        } catch (NamingException e) {
            throw new DataSourceException("There was an error configuring JndiDataSourceTransactionPool. Cause: " + e, e);
        }
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    private static Properties getEnvProperties(Properties allProps) {
        final String PREFIX = ENV_PREFIX;
        Properties contextProperties = null;
        for (Entry<Object, Object> entry : allProps.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key.startsWith(PREFIX)) {
                if (contextProperties == null) {
                    contextProperties = new Properties();
                }
                contextProperties.put(key.substring(PREFIX.length()), value);
            }
        }
        return contextProperties;
    }

}
