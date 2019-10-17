/**
 * Copyright ${license.git.copyrightYears} the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.mapping;

/**
 * SqlSource：
 * 表示从XML文件或注释中读取的映射语句的内容。
 *它创建将从用户接收的输入参数传递到数据库的SQL。
 *
 * SqlSource实现类（实现类是重点，存储着XML解析出来的SQL语句）：StaticSqlSource（静态SQL），RawSqlSource和DynamicSqlSource（动态SQL）
 *
 * DynamicSqlSource（动态SQL）：org.apache.ibatis.scripting.xmltags.DynamicSqlSource
 * 动态SQL表示这个SQL节点中含有${}或是其他动态的标签（比如，if，trim，foreach，choose，bind节点等），需要在运行时根据传入的条件才能确定SQL，因此对于动态SQL的MappedStatement的解析过程应该是在运行时。
 *
 * StaticSqlSource（静态SQL）：org.apache.ibatis.builder.StaticSqlSource
 * 而静态SQL是不含以上这个节点的SQL，能直接解析得到含有占位符形式的SQL语句，而不需要根据传入的条件确定SQL，因此可以在加载时就完成解析。所在在执行效率上要高于动态SQL。
 *<select id="selectAllAuthors" resultType="org.apache.ibatis.domain.blog.Author">
 *         select * from author
 * </select>
 * @author Clinton Begin
 */
public interface SqlSource {

    BoundSql getBoundSql(Object parameterObject);

}
