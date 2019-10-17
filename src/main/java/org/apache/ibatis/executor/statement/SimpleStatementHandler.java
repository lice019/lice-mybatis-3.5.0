package org.apache.ibatis.executor.statement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * SimpleStatementHandler：JDBC中常用的Statement接口，用于简单SQL的处理。
 *
 * @author Clinton Begin
 */
public class SimpleStatementHandler extends BaseStatementHandler {


    //构造器，使用父类的BaseStatementHandler的构造器
    public SimpleStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        super(executor, mappedStatement, parameter, rowBounds, resultHandler, boundSql);
    }

    //
    @Override
    public int update(Statement statement) throws SQLException {
        //使用BoundSQL来处理mapper.xml中的SQL语句
        String sql = boundSql.getSql();
        //获取参数数据类型
        Object parameterObject = boundSql.getParameterObject();
        //获取主键
        KeyGenerator keyGenerator = mappedStatement.getKeyGenerator();
        //返回处理的行数
        int rows;
        /**
         * instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例。instanceof通过返回一个布尔值来指出，这个对象是否是这个特定类或者是它的子类的一个实例。
         *
         * 判断keyGenerator运行时的对象，是否是Jdbc3KeyGenerator的实例
         */
        if (keyGenerator instanceof Jdbc3KeyGenerator) {
            //statement执行SQL语句，并返回生成的主键
            statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
            //获取数据受影响的行数
            rows = statement.getUpdateCount();
            keyGenerator.processAfter(executor, mappedStatement, statement, parameterObject);
        } else if (keyGenerator instanceof SelectKeyGenerator) {
            /**
             *如果keyGenerator是SelectKeyGenerator的实例
             */
            statement.execute(sql);
            rows = statement.getUpdateCount();
            keyGenerator.processAfter(executor, mappedStatement, statement, parameterObject);
        } else {
            statement.execute(sql);
            rows = statement.getUpdateCount();
        }
        //返回数据库受影响的行数
        return rows;
    }

    //批量执行SQL语句
    @Override
    public void batch(Statement statement) throws SQLException {
        String sql = boundSql.getSql();
        statement.addBatch(sql);
    }


    //查询，并返回多条记录的结果集
    @Override
    public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
        String sql = boundSql.getSql();
        statement.execute(sql);
        return resultSetHandler.handleResultSets(statement);
    }

    @Override
    public <E> Cursor<E> queryCursor(Statement statement) throws SQLException {
        String sql = boundSql.getSql();
        statement.execute(sql);
        return resultSetHandler.handleCursorResultSets(statement);
    }

    //SimpleStatementHandler实现实例化Statement对象，可以通过该接口指定statement对象
    @Override
    protected Statement instantiateStatement(Connection connection) throws SQLException {
        //获取ResultSetType类型
        if (mappedStatement.getResultSetType() == ResultSetType.DEFAULT) {
            //返回一个Statement对象
            return connection.createStatement();
        } else {
            return connection.createStatement(mappedStatement.getResultSetType().getValue(), ResultSet.CONCUR_READ_ONLY);
        }
    }

    @Override
    public void parameterize(Statement statement) {
        // N/A
    }

}
