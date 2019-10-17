package org.apache.ibatis.session;

import java.io.Closeable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;

/**
 * SqlSession：是mybatis提供给使用者的一个与数据库操作的会话，
 * 每个一个线程应该具有自己的SqlSession实例，SqlSession实例不应该被共享，也不是线程安全的。
 * 因此，最佳范围是请求或方法范围。永远不要在静态字段或类的实例字段中保留对SqlSession实例的引用。
 * 切勿在任何托管范围内保留对SqlSession的引用，例如Servlet框架的HttpSession。
 * 如果您使用任何类型的Web框架，请考虑SqlSession遵循与HTTP请求相似的范围。
 * 换句话说，收到HTTP请求后，您可以打开SqlSession，然后在返回响应时就可以将其关闭。
 * 结束会话非常重要。
 * <p>
 * 注意：spring框架是可以创建线程安全的
 * try (SqlSession session = sqlSessionFactory.openSession()) {
 * // do work
 * }
 */
public interface SqlSession extends Closeable {


    //sqlSession.selectOne("com.lice.mapper.EmployeeMapper.getEmployeeById");
    //通过返回一个Object类型实例，也可以转成相应的实体实例
    <T> T selectOne(String statement);


    //sqlSession.selectOne("com.lice.mapper.EmployeeMapper.getEmployeeById",1);
    //parameter是接口中的参数，与XML中的parameterType="java.lang.Integer"类型对应
    <T> T selectOne(String statement, Object parameter);


    //查询多条记录
    <E> List<E> selectList(String statement);

    //根据parameter参数查询多条记录
    <E> List<E> selectList(String statement, Object parameter);


    <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds);


    <K, V> Map<K, V> selectMap(String statement, String mapKey);


    <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey);


    <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds);


    <T> Cursor<T> selectCursor(String statement);


    <T> Cursor<T> selectCursor(String statement, Object parameter);


    <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds);


    void select(String statement, Object parameter, ResultHandler handler);


    void select(String statement, ResultHandler handler);


    void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler);


    //添加记录，返回成功row数
    int insert(String statement);


    //根据查询添加记录，返回成功的row数
    int insert(String statement, Object parameter);


    //更新记录
    int update(String statement);


    int update(String statement, Object parameter);


    //删除记录
    int delete(String statement);


    int delete(String statement, Object parameter);


    //事务提交
    void commit();


    void commit(boolean force);


    //事务回滚
    void rollback();


    void rollback(boolean force);


    List<BatchResult> flushStatements();

    //关闭session
    @Override
    void close();

    //清除session缓存
    void clearCache();

    //获取一个当前线程的Configuration对象
    Configuration getConfiguration();

    //根据mapper的字节码获取一个相应的Mapper接口实例
    <T> T getMapper(Class<T> type);

    //获取一个数据库连接对象Connection
    Connection getConnection();
}
