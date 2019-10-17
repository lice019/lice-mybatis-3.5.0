package org.apache.ibatis.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;

/**
 * BoundSql：意为绑定SQL语句。通俗来说，就是讲动态的SQL转成数据库认识的语句。将#或$解析为？占位符。
 * BoundSql实际上没做什么操作，像一个中转站
 * <p>
 * 处理完任何动态内容后，从{@link SqlSource}获得的实际SQL字符串。
 * SQL可能有SQL占位符“?”和一个参数映射列表(有序)，其中包含每个参数的附加信息(至少是要从中读取值的输入对象的属性名)。
 * <p>
 * 还可以有动态语言(for loops, bind…)创建的其他参数。
 *
 * @author Clinton Begin
 */
public class BoundSql {

    // SQL语句
    //进行 #{ } 和 ${ } 替换完毕之后的结果sql, 注意每个 #{ }替换完之后就是一个 ?
    private final String sql;
    // SQL的参数集合
    private final List<ParameterMapping> parameterMappings;
    // 用户传入的数据
    private final Object parameterObject;
    private final Map<String, Object> additionalParameters;
    private final MetaObject metaParameters;

    //根据mapper.xml和mapper接口的参数来构建sql执行语句
    public BoundSql(Configuration configuration, String sql, List<ParameterMapping> parameterMappings, Object parameterObject) {
        //select
        //	emp_id as empId,
        //	emp_name as empName,
        //	emp_no as empNO,
        //	emp_birthday as empBirthday
        //	from tb_employee where emp_id = ?
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.parameterObject = parameterObject;
        this.additionalParameters = new HashMap<>();
        this.metaParameters = configuration.newMetaObject(additionalParameters);
    }

    public String getSql() {
        return sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public Object getParameterObject() {
        return parameterObject;
    }

    //获取额外参数
    public boolean hasAdditionalParameter(String name) {
        String paramName = new PropertyTokenizer(name).getName();
        return additionalParameters.containsKey(paramName);
    }

    //设置参数的key和value
    public void setAdditionalParameter(String name, Object value) {
        metaParameters.setValue(name, value);
    }

    //根据key获取额外参数
    public Object getAdditionalParameter(String name) {
        return metaParameters.getValue(name);
    }
}
