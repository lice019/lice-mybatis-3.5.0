package org.apache.ibatis.executor.resultset;

import org.apache.ibatis.cursor.Cursor;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * ResultSetHandler：提供了处理不同Statement的方法
 * ResultSetHandler负责处理两件事：
 * （1）处理Statement执行后产生的结果集，生成结果列表
 * （2）处理存储过程执行后的输出参数
 *
 * @author Clinton Begin
 */
public interface ResultSetHandler {

    //将一个Statement对象转换为一个List对象
    <E> List<E> handleResultSets(Statement stmt) throws SQLException;

    //将一个Statement对象转换为一个Cursor对象
    <E> Cursor<E> handleCursorResultSets(Statement stmt) throws SQLException;

    //处理存储过程的输出
    void handleOutputParameters(CallableStatement cs) throws SQLException;

}
