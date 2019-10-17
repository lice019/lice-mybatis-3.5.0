package org.apache.ibatis.mapping;

import java.sql.ResultSet;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

/**
 * ParameterMapping：参数映射处理
 *
 * @author Clinton Begin
 */
public class ParameterMapping {

    //配置类
    private Configuration configuration;

    //属性字段
    private String property;

    //参数模式
    private ParameterMode mode;
    //javaType--java数据类型
    private Class<?> javaType = Object.class;
    //数据库字段类型
    private JdbcType jdbcType;

    private Integer numericScale;
    //类型处理器
    private TypeHandler<?> typeHandler;
    //resultMap标签的属性Id
    private String resultMapId;
    //数据库字段的类型名称
    private String jdbcTypeName;
    //表达式
    private String expression;

    private ParameterMapping() {
    }

    //构建类
    public static class Builder {
        private ParameterMapping parameterMapping = new ParameterMapping();

        //Builder构造器
        public Builder(Configuration configuration, String property, TypeHandler<?> typeHandler) {
            parameterMapping.configuration = configuration;
            parameterMapping.property = property;
            parameterMapping.typeHandler = typeHandler;
            parameterMapping.mode = ParameterMode.IN;
        }

        public Builder(Configuration configuration, String property, Class<?> javaType) {
            parameterMapping.configuration = configuration;
            parameterMapping.property = property;
            parameterMapping.javaType = javaType;
            parameterMapping.mode = ParameterMode.IN;
        }

      /**
       * 以下方法将传入的参数，一一对应ParameterMapping类中成员字段变量
       */
        public Builder mode(ParameterMode mode) {
            parameterMapping.mode = mode;
            return this;
        }

        public Builder javaType(Class<?> javaType) {
            parameterMapping.javaType = javaType;
            return this;
        }

        public Builder jdbcType(JdbcType jdbcType) {
            parameterMapping.jdbcType = jdbcType;
            return this;
        }

        public Builder numericScale(Integer numericScale) {
            parameterMapping.numericScale = numericScale;
            return this;
        }

        public Builder resultMapId(String resultMapId) {
            parameterMapping.resultMapId = resultMapId;
            return this;
        }

        public Builder typeHandler(TypeHandler<?> typeHandler) {
            parameterMapping.typeHandler = typeHandler;
            return this;
        }

        public Builder jdbcTypeName(String jdbcTypeName) {
            parameterMapping.jdbcTypeName = jdbcTypeName;
            return this;
        }

        public Builder expression(String expression) {
            parameterMapping.expression = expression;
            return this;
        }

        public ParameterMapping build() {
            resolveTypeHandler();
            validate();
            return parameterMapping;
        }


        //检验数据库返回的结果集中的成员类型是否匹配JAVA的数据类型
        private void validate() {
            if (ResultSet.class.equals(parameterMapping.javaType)) {
                if (parameterMapping.resultMapId == null) {
                    throw new IllegalStateException("Missing resultmap in property '"
                            + parameterMapping.property + "'.  "
                            + "Parameters of type java.sql.ResultSet require a resultmap.");
                }
            } else {
                if (parameterMapping.typeHandler == null) {
                    throw new IllegalStateException("Type handler was null on parameter mapping for property '"
                            + parameterMapping.property + "'. It was either not specified and/or could not be found for the javaType ("
                            + parameterMapping.javaType.getName() + ") : jdbcType (" + parameterMapping.jdbcType + ") combination.");
                }
            }
        }

        private void resolveTypeHandler() {
            if (parameterMapping.typeHandler == null && parameterMapping.javaType != null) {
                Configuration configuration = parameterMapping.configuration;
                TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
                parameterMapping.typeHandler = typeHandlerRegistry.getTypeHandler(parameterMapping.javaType, parameterMapping.jdbcType);
            }
        }

    }

    public String getProperty() {
        return property;
    }

    /**
     * Used for handling output of callable statements
     *
     * @return
     */
    public ParameterMode getMode() {
        return mode;
    }

    /**
     * Used for handling output of callable statements
     *
     * @return
     */
    public Class<?> getJavaType() {
        return javaType;
    }

    /**
     * Used in the UnknownTypeHandler in case there is no handler for the property type
     *
     * @return
     */
    public JdbcType getJdbcType() {
        return jdbcType;
    }

    /**
     * Used for handling output of callable statements
     *
     * @return
     */
    public Integer getNumericScale() {
        return numericScale;
    }

    /**
     * Used when setting parameters to the PreparedStatement
     *
     * @return
     */
    public TypeHandler<?> getTypeHandler() {
        return typeHandler;
    }

    /**
     * Used for handling output of callable statements
     *
     * @return
     */
    public String getResultMapId() {
        return resultMapId;
    }

    /**
     * Used for handling output of callable statements
     *
     * @return
     */
    public String getJdbcTypeName() {
        return jdbcTypeName;
    }

    /**
     * Not used
     *
     * @return
     */
    public String getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ParameterMapping{");
        //sb.append("configuration=").append(configuration); // configuration doesn't have a useful .toString()
        sb.append("property='").append(property).append('\'');
        sb.append(", mode=").append(mode);
        sb.append(", javaType=").append(javaType);
        sb.append(", jdbcType=").append(jdbcType);
        sb.append(", numericScale=").append(numericScale);
        //sb.append(", typeHandler=").append(typeHandler); // typeHandler also doesn't have a useful .toString()
        sb.append(", resultMapId='").append(resultMapId).append('\'');
        sb.append(", jdbcTypeName='").append(jdbcTypeName).append('\'');
        sb.append(", expression='").append(expression).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
