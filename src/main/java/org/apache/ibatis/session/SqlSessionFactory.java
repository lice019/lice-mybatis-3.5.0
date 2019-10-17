package org.apache.ibatis.session;

import java.sql.Connection;

/**
 * SqlSessionFactory：SqlSessionFactory是一个SQLSession工厂的接口，定义了一些SqlSessionFactory相应操作接口。
 * SqlSessionFactory被DefaultSQLSessionFactory所实现，也是mybatis定义的默认SqlSession工厂类
 * SqlSessionFactory的实例最好是单例的使用，这个在spring整合中最好的体现。因为如果使用多个SqlSessionFactory会存在
 * 事务安全的问题和资源开销浪费的问题。
 */
public interface SqlSessionFactory {

    //开启Session对话
    SqlSession openSession();

    /**
     * 开启相应功能的对话对象
     *
     * @param autoCommit
     * @return
     */
    SqlSession openSession(boolean autoCommit);

    SqlSession openSession(Connection connection);

    SqlSession openSession(TransactionIsolationLevel level);

    //开启执行器相应的session对象
    SqlSession openSession(ExecutorType execType);

    SqlSession openSession(ExecutorType execType, boolean autoCommit);

    SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level);

    SqlSession openSession(ExecutorType execType, Connection connection);

    //获取Configuration配置对象
    Configuration getConfiguration();

}
