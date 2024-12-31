/*
 * Copyright (c) 2011-2023, baomidou (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *//*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/
本软件受适用的国家软件著作权法（包括国际条约）和开源协议 双重保护许可。

开源协议中文释意如下：
1.JeeLowCode开源版本无任何限制，在遵循本开源协议（Apache2.0）条款下，【允许商用】使用，不会造成侵权行为。
2.允许基于本平台软件开展业务系统开发。
3.在任何情况下，您不得使用本软件开发可能被认为与【本软件竞争】的软件。

最终解释权归：http://www.jeelowcode.com
*/
package com.jeelowcode.framework.plus.core.toolkit;

import java.io.*;


/**
 * <p> copy from spring-core#org.springframework.util.SerializationUtils version 5.2.8 </p>
 *
 * @since 1.0
 */
public class SerializationUtils {

    /**
     * Deep clone an {@code Object} using serialization.
     * <p>This is many times slower than writing clone methods by hand
     * on all objects in your object graph. However, for complex object
     * graphs, or for those that don't support deep cloning this can
     * be a simple alternative implementation. Of course all the objects
     * must be {@code Serializable}.</p>
     *
     * @param <T>    the type of the object involved
     * @param object the {@code Serializable} object to clone
     * @return the cloned object
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(final T object) {
        if (object == null) {
            return null;
        }
        final byte[] objectData = serialize(object);
        return (T) deserialize(objectData);
    }

    /**
     * Serialize the given object to a byte array.
     *
     * @param object the object to serialize
     * @return an array of bytes representing the object in a portable fashion
     */
    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            oos.flush();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), ex);
        }
        return baos.toByteArray();
    }

    /**
     * Deserialize the byte array into an object.
     *
     * @param bytes a serialized object
     * @return the result of deserializing the bytes
     */
    public static Object deserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return ois.readObject();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to deserialize object", ex);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Failed to deserialize object type", ex);
        }
    }
}
