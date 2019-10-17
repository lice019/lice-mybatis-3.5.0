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
package org.apache.ibatis.session;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;

/**
 * Builds {@link SqlSession} instances.
 *
 * @author Clinton Begin
 */

/**
 * SqlSessionFactoryBuilder：由类名可知SqlSessionFactoryBuilder是SqlSessionFactory的构造类，而SqlSessionFactory又是SqlSession的工厂接口，SqlSession从字面意思可知是sql会话接口.
 * SqlSessionFactoryBuilder作用：创建SqlSessionFactory工厂，由于mybatis不直接在SqlSessionFactory中解析mybatis的配置，因此使用SqlSessionFactoryBuilder来解析XML文件，并赋给SqlSessionFactory
 * 总结：SqlSessionFactoryBuilder根据mybatis的配置文件流创建Configuration对象，然后新建一个SqlSessionFactory的默认实现类DefaultSqlSessionFactory的对象
 */
public class SqlSessionFactoryBuilder {

    //根据Resource对象返回的Reader对象来构建一个SqlSessionFactory
    public SqlSessionFactory build(Reader reader) {
        return build(reader, null, null);
    }

    public SqlSessionFactory build(Reader reader, String environment) {
        return build(reader, environment, null);
    }

    public SqlSessionFactory build(Reader reader, Properties properties) {
        return build(reader, null, properties);
    }

    public SqlSessionFactory build(Reader reader, String environment, Properties properties) {
        try {
            //根据XMLConfiguration对象的parse方法创建了一个Configuration对象
            XMLConfigBuilder parser = new XMLConfigBuilder(reader, environment, properties);
            //解析Configuration对象的配置参数值
            return build(parser.parse());
        } catch (Exception e) {
            throw ExceptionFactory.wrapException("Error building SqlSession.", e);
        } finally {
            ErrorContext.instance().reset();
            try {
                reader.close();
            } catch (IOException e) {
                // Intentionally ignore. Prefer previous error.
            }
        }
    }

    //根据Resource对象返回的InputStream对象构造SQLSessionFactory对象
    public SqlSessionFactory build(InputStream inputStream) {
        return build(inputStream, null, null);
    }

    public SqlSessionFactory build(InputStream inputStream, String environment) {
        return build(inputStream, environment, null);
    }

    public SqlSessionFactory build(InputStream inputStream, Properties properties) {
        return build(inputStream, null, properties);
    }

    //解析InputStream
    public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties) {
        try {
            /**
             *  XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);
             *  执行的类有：XMLConfigBuilder-->BaseBuilder-->Configuration-->DefaultReflectorFactory
             */
            //构建一个XMLConfigBuilder对象，XMLConfigBuilder使用了构建者模式，用于解析XML到Configuration对象。
            //通过XMLConfigBuilder构造器，将inputStream, environment, properties传入给XMLConfigBuilder对象。用于XMLConfigBuilder对象调用parse()方法解析
            XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);
            //解析XML文件
            return build(parser.parse());
        } catch (Exception e) {
            throw ExceptionFactory.wrapException("Error building SqlSession.", e);
        } finally {
            ErrorContext.instance().reset();
            try {
                inputStream.close();
            } catch (IOException e) {
                // Intentionally ignore. Prefer previous error.
            }
        }
    }

    /**
     * 创建一个DefaultSqlSessionFactory工厂，默认的工厂
     * @param config 流的配置对象
     * @return
     */
    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }

}
