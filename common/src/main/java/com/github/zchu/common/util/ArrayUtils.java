package com.github.zchu.common.util;

public class ArrayUtils {


    private static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 显示数组信息
     *
     * @param <T>   数组对象类型
     * @param array 目标数组
     * @return 数组信息
     */
    private static <T> String print(T[] array) {
        if (array == null) {
            return "null";
        }

        if (array.length == 0) {
            return "{}";
        }

        StringBuilder buffer = new StringBuilder(128);
        buffer.append("{");
        for (T item : array) {
            buffer.append(item == null ? "null" : ("\"" + item.toString() + "\"")).append(", ");
        }
        buffer.append("}");
        return buffer.toString().replace(", }", "}");
    }

    ////////////////////////////////////////////////////////////////////////
    // 1. 验证集合中是否包含目标元素

    /**
     * 验证一个集合数组中是否包含目标对象
     *
     * @param array  目标数组
     * @param target 目标对象
     * @return 数组不为空且至少包含一个元素, 且包含目标对象时, 返回{@code true}; 否则, 返回{@code false}
     */
    public static <T> boolean contains(T[] array, T target) {
        if (!isEmpty(array)) {
            if (target == null) {
                for (T item : array) {
                    if (item == null) {
                        return true;
                    }
                }
            } else {
                for (T item : array) {
                    if (target.equals(item)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////
    // 2. 验证集合A是否不含集合B中的任意元素

    /**
     * 验证集合A是否不含集合B中的任意元素
     *
     * @param a 集合A
     * @param b 集合B
     * @return 集合A或集合B为空或不含任何元素, 或集合A不含集合B中的任意元素, 返回{@code true}; 否则, 返回{@code false}
     */
    public static <T> boolean containsNon(T[] a, T[] b) {
        // 集合A或集合B为空或不含任何元素
        if (isEmpty(a) || isEmpty(b)) {
            return true;
        }

        // 集合A或集合B不为空且至少包含一个元素
        for (T item : b) {
            if (contains(a, item)) {
                return false;
            }
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////////////
    // 3. 验证集合A是否包含集合B中的至少一个元素

    /**
     * 验证集合A是否包含集合B中的至少一个元素
     *
     * @param a 集合A
     * @param b 集合B
     * @return 集合A和集合B不为空且至少包含一个元素, 且集合A包含集合B中的至少一个元素, 返回{@code true}; 否则, 返回{@code false}
     */
    public static <T> boolean containsAny(T[] a, T[] b) {
        // 集合A或集合B为空或不含任何元素
        if (isEmpty(a) || isEmpty(b)) {
            return false;
        }

        // 集合A或集合B不为空且至少包含一个元素
        for (T item : b) {
            if (contains(a, item)) {
                return true;
            }
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////
    // 4. 验证集合A是否包含集合B中的所有元素

    /**
     * 验证集合A是否包含集合B中的所有元素
     *
     * @param a 集合A
     * @param b 集合B
     * @return 集合A和集合B不为空且至少包含一个元素(集合A中包含的元素个数不少于集合B), 且集合A包含集合B中所有元素, 返回{@code true}; 否则, 返回{@code false}
     */
    public static <T> boolean containsAll(T[] a, T[] b) {
        if (a == b)
            return true;
        if (a == null)
            return false;
        if (b == null) {
            return true;
        }
        if (b.length > a.length)
            return false;
        for (T item : b) {
            if (!contains(a, item)) {
                return false;
            }
        }
        return true;
    }

}
