package org.apache.ibatis.cursor;

import java.io.Closeable;

/**
 * 游标契约，使用迭代器惰性地处理获取项。
 * 游标非常适合处理数百万项查询，而这些查询通常不适合存储在内存中。
 * 如果在resultmap中使用集合，则必须对游标SQL查询进行排序(resultorder ="true")
 * *使用resultMap的id列。
 * @author Guillaume Darmont / guillaume@dropinocean.com
 */
public interface Cursor<T> extends Closeable, Iterable<T> {

    /**
     * @return true if the cursor has started to fetch items from database.
     */
    boolean isOpen();

    /**
     *
     * @return true if the cursor is fully consumed and has returned all elements matching the query.
     */
    boolean isConsumed();

    /**
     * Get the current item index. The first item has the index 0.
     * @return -1 if the first cursor item has not been retrieved. The index of the current item retrieved.
     */
    int getCurrentIndex();
}
