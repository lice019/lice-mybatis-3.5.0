package org.apache.ibatis.executor;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

/**
 * Executor：Executor接口共有两个实现类，分别是BaseExecutor和CachingExecutor，CachingExecutor是缓存执行器
 * sql的具体执行是通过调用SqlSession接口的对应的方法去执行的，而SqlSession最终都是通过调用了自己的Executor对象的query和update去执行的。
 * Executor：是mybatis的一个核心接口。mybatis中的执行器，会根据mybatis解析Mapper.xml文件来选择相应的执行类型
 * 所有的Mapper语句的执行都是通过Executor进行的。
 * <p>
 * Executor定义了各种处理方法。
 *
 * Executor的主要作用：sql的具体执行是通过调用SqlSession接口的对应的方法去执行的，而SqlSession最终都是通过调用了自己的Executor对象的query和update去执行的。
 *
 * Executor的实现类时在Configuration类中
 *
 * @author Clinton Begin
 */
public interface Executor {

    ResultHandler NO_RESULT_HANDLER = null;

    //次方法包含的insert、update、delete的作用
    int update(MappedStatement ms, Object parameter) throws SQLException;

    //查询，先查缓存，再查数据库
    <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;

    // 查询信息
    <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException;

    <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException;

    // 刷新statements
    List<BatchResult> flushStatements() throws SQLException;

    // 提交数据
    void commit(boolean required) throws SQLException;

    //回滚
    void rollback(boolean required) throws SQLException;

    //创建缓存key
    CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql);

    //是否被缓存
    boolean isCached(MappedStatement ms, CacheKey key);

    // 清空本地缓存
    void clearLocalCache();

    // 延迟加载
    void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType);

    // 获取Transaction 对象
    Transaction getTransaction();

    // 连接关闭
    void close(boolean forceRollback);

    // 连接是否关闭
    boolean isClosed();

    // 设置执行器增强器
    void setExecutorWrapper(Executor executor);

}
