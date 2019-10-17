package org.apache.ibatis.builder;

import java.util.List;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

/**
 * StaticSqlSource：静态SQL，将解析到的静态SQL语句封装到StaticSqlSource中。
 * 静态SQL：就是不用根据传参来决定的SQL语句，使用了${}和#{}、(比如，if，trim，foreach，choose，bind节点等)就是动态SQL
 * 静态：
 * <select id="selectAllAuthors" resultType="org.apache.ibatis.domain.blog.Author">
 * select * from author
 * </select>
 *
 * @author Clinton Begin
 */
public class StaticSqlSource implements SqlSource {

    //从Mapper.xml解析后的得到的SQL语句
    private final String sql;

    private final List<ParameterMapping> parameterMappings;
    //配置类
    private final Configuration configuration;


    public StaticSqlSource(Configuration configuration, String sql) {
        this(configuration, sql, null);
    }

    public StaticSqlSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.configuration = configuration;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return new BoundSql(configuration, sql, parameterMappings, parameterObject);
    }

}
