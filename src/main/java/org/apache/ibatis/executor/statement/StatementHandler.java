package org.apache.ibatis.executor.statement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.ResultHandler;

/**
 * StatementHandler：Statement的处理器，专门负责处理JDBC中的Statement相应的操作。
 * 大致可以分三大类：
 * 1、SimpleStatementHandler，这个很简单了，就是对应我们JDBC中常用的Statement接口，用于简单SQL的处理；
 * 2、PreparedStatementHandler，这个对应JDBC中的PreparedStatement，预编译SQL的接口；
 * 3、CallableStatementHandler，这个对应JDBC中CallableStatement，用于执行存储过程相关的接口；
 * 4、RoutingStatementHandler，这个接口是以上三个接口的路由，没有实际操作，只是负责上面三个StatementHandler的创建及调用。
 *
 * @author Clinton Begin
 */
public interface StatementHandler {

    //通过Connection对象，获取JDBC的Statement对象。Statement sts = conn.createStatement();
    Statement prepare(Connection connection, Integer transactionTimeout)
            throws SQLException;

    void parameterize(Statement statement)
            throws SQLException;

    //批量处理SQL语句
    void batch(Statement statement)
            throws SQLException;

    //处理更新SQL语句
    int update(Statement statement)
            throws SQLException;

    //查询
    <E> List<E> query(Statement statement, ResultHandler resultHandler)
            throws SQLException;

    //游标查询
    <E> Cursor<E> queryCursor(Statement statement)
            throws SQLException;

    //获取处理后的SQL语句
    BoundSql getBoundSql();

    //获取参数处理器
    ParameterHandler getParameterHandler();

}
