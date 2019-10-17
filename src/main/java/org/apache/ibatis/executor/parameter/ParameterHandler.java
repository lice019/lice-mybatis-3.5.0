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
package org.apache.ibatis.executor.parameter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ParameterHandler：在Mybatis四大对象中负责将sql中的占位符替换为真正的参数，它是一个接口.
 * 有且只有一个实现类DefaultParameterHandler
 *
 * @author Clinton Begin
 */
public interface ParameterHandler {

    Object getParameterObject();

    //setParameters是处理参数最核心的方法。
    void setParameters(PreparedStatement ps)
            throws SQLException;

}
