/**
 *    Copyright ${license.git.copyrightYears} the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.session;

import java.sql.Connection;

/**
 * Creates an {@link SqlSession} out of a connection or a DataSource
 *
 * @author Clinton Begin
 */

/**
 * SqlSessionFactory：SqlSessionFactory是一个SQLSession工厂的接口，定义了一些SqlSessionFactory相应操作接口。
 * SqlSessionFactory被DefaultSQLSessionFactory所实现，也是mybatis定义的默认SqlSession工厂类
 */
public interface SqlSessionFactory {

  //开启Session对话
  SqlSession openSession();

  /**
   * 开启相应功能的对话对象
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
