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
/**
 * Hyper-simple Datasource.
 */
package org.apache.ibatis.datasource.unpooled;


/**
 * unpooled：mybatis会为每一个数据库创建一个连接，并关闭它，适用于小规模并发用户的简单应用。
 */

/**
 * UnpooledDataSource实现了DriverManager获取Connection对象，使mybatis具有了连接数据库和操作数据的能力
 */
