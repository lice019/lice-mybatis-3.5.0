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
package org.apache.ibatis.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * A class to simplify access to resources through the classloader.
 *
 * @author Clinton Begin
 */

/**
 * Resources:通过与ClassLoader类加载器，进行对系统配置文件的加载
 */
public class Resources {

    //定义一个ClassLoaderWrapper的类加载器的包装类(里面包含了多个ClassLoader类加载器)，会根据调整多个类加载器的使用顺序，ClassLoaderWrapper 可以确保返回给系统使用的是正确的类加载器
    private static ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();

    /**
     * Charset to use when calling getResourceAsReader.
     * null means use the system default.
     */
    //定义字符集，如果不传，默认使用系统字符集
    private static Charset charset;

    //Resources空构造器
    Resources() {
    }

    /**
     * Returns the default classloader (may be null).
     *
     * @return The default classloader
     */
    //获取一个默认的类加载器defaultClassLoader
    public static ClassLoader getDefaultClassLoader() {
        return classLoaderWrapper.defaultClassLoader;
    }

    /**
     * Sets the default classloader
     *
     * @param defaultClassLoader - the new default ClassLoader
     */
    //设置一个类加载器
    public static void setDefaultClassLoader(ClassLoader defaultClassLoader) {
        classLoaderWrapper.defaultClassLoader = defaultClassLoader;
    }

    /**
     * Returns the URL of the resource on the classpath
     *
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    //根据类路径下，返回一个URL对象
    public static URL getResourceURL(String resource) throws IOException {
        // issue #625
        return getResourceURL(null, resource);
    }

    /**
     * Returns the URL of the resource on the classpath
     *
     * @param loader   The classloader used to fetch the resource
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    //只读的通用资源，加载为 URL
    public static URL getResourceURL(ClassLoader loader, String resource) throws IOException {
        //根据配置文件路径和类加载器，返回一个URL对象
        URL url = classLoaderWrapper.getResourceAsURL(resource, loader);
        if (url == null) {
            throw new IOException("Could not find resource " + resource);
        }
        return url;
    }

    /**
     * Returns a resource on the classpath as a Stream object
     *
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    //通过输入流读取解析mybatis全局配置文件mybatis.cfg.xml
    public static InputStream getResourceAsStream(String resource) throws IOException {
        //返回一个读取到配置文件的mybatis.cfg.xml的输入流对象
        return getResourceAsStream(null, resource);
    }

    /**
     * Returns a resource on the classpath as a Stream object
     *
     * @param loader   The classloader used to fetch the resource
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    //使用ClassLoader类加载器,加载流类，将mybatis全局配置文件mybatis.cfg.xml读取到输入流中
    public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
        //将配置文件加载到InputStream流中
        InputStream in = classLoaderWrapper.getResourceAsStream(resource, loader);
        //如果InputStream为null，抛出IO异常
        if (in == null) {
            throw new IOException("Could not find resource " + resource);
        }
        //如果InputStream不为null,返回in流对象
        return in;
    }

    /**
     * Returns a resource on the classpath as a Properties object
     *
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static Properties getResourceAsProperties(String resource) throws IOException {
        Properties props = new Properties();
        try (InputStream in = getResourceAsStream(resource)) {
            props.load(in);
        }
        return props;
    }

    /**
     * Returns a resource on the classpath as a Properties object
     *
     * @param loader   The classloader used to fetch the resource
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static Properties getResourceAsProperties(ClassLoader loader, String resource) throws IOException {
        Properties props = new Properties();
        try (InputStream in = getResourceAsStream(loader, resource)) {
            props.load(in);
        }
        return props;
    }

    /**
     * Returns a resource on the classpath as a Reader object
     *
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    //简单的只读文本数据，加载为 Reader
    public static Reader getResourceAsReader(String resource) throws IOException {
        Reader reader;
        if (charset == null) {
            reader = new InputStreamReader(getResourceAsStream(resource));
        } else {
            reader = new InputStreamReader(getResourceAsStream(resource), charset);
        }
        return reader;
    }

    /**
     * Returns a resource on the classpath as a Reader object
     *
     * @param loader   The classloader used to fetch the resource
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static Reader getResourceAsReader(ClassLoader loader, String resource) throws IOException {
        Reader reader;
        if (charset == null) {
            reader = new InputStreamReader(getResourceAsStream(loader, resource));
        } else {
            reader = new InputStreamReader(getResourceAsStream(loader, resource), charset);
        }
        return reader;
    }

    /**
     * Returns a resource on the classpath as a File object
     *
     * @param resource The resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    //返回一个文件对象
    public static File getResourceAsFile(String resource) throws IOException {
        return new File(getResourceURL(resource).getFile());
    }

    /**
     * Returns a resource on the classpath as a File object
     *
     * @param loader   - the classloader used to fetch the resource
     * @param resource - the resource to find
     * @return The resource
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static File getResourceAsFile(ClassLoader loader, String resource) throws IOException {
        return new File(getResourceURL(loader, resource).getFile());
    }

    /**
     * Gets a URL as an input stream
     *
     * @param urlString - the URL to get
     * @return An input stream with the data from the URL
     * @throws java.io.IOException If the resource cannot be found or read
     */
    //简单的只读二进制或文本数据，加载为 Stream
    public static InputStream getUrlAsStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        return conn.getInputStream();
    }

    /**
     * Gets a URL as a Reader
     *
     * @param urlString - the URL to get
     * @return A Reader with the data from the URL
     * @throws java.io.IOException If the resource cannot be found or read
     */
    public static Reader getUrlAsReader(String urlString) throws IOException {
        Reader reader;
        if (charset == null) {
            reader = new InputStreamReader(getUrlAsStream(urlString));
        } else {
            reader = new InputStreamReader(getUrlAsStream(urlString), charset);
        }
        return reader;
    }

    /**
     * Gets a URL as a Properties object
     *
     * @param urlString - the URL to get
     * @return A Properties object with the data from the URL
     * @throws java.io.IOException If the resource cannot be found or read
     */
    //只读的配置属性文件，加载为 Properties
    public static Properties getUrlAsProperties(String urlString) throws IOException {
        Properties props = new Properties();
        try (InputStream in = getUrlAsStream(urlString)) {
            props.load(in);
        }
        return props;
    }

    /**
     * Loads a class
     *
     * @param className - the class to fetch
     * @return The loaded class
     * @throws ClassNotFoundException If the class cannot be found (duh!)
     */
    //根据全类名，返回一个Class对象
    public static Class<?> classForName(String className) throws ClassNotFoundException {
        return classLoaderWrapper.classForName(className);
    }

    public static Charset getCharset() {
        return charset;
    }

    public static void setCharset(Charset charset) {
        Resources.charset = charset;
    }

}
