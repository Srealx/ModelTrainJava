package com.binninfo.model_train.service.cache;

import java.util.function.Supplier;

/**
 * @Author chengang
 * @Date 2025/5/29
 */
public class CommonThreadLocal<T> extends ThreadLocal<T>{

    /**
     * 获取当前线程的值（不移除）
     */
    @Override
    public T get() {
        return super.get();
    }

    /**
     * 获取值并可选地移除
     * @param remove 是否在获取后从 ThreadLocal 中移除该值
     * @return 当前线程存储的值
     */
    public T get(boolean remove) {
        T value = super.get();
        if (remove) {
            super.remove();
        }
        return value;
    }

    /**
     * 安全获取值（如果不存在则初始化）
     * @param remove 是否在获取后移除
     * @param initializer 初始化函数（当值为空时使用）
     * @return 当前线程的值
     */
    public T getOrInitialize(boolean remove, Supplier<T> initializer) {
        T value = super.get();
        if (value == null && initializer != null) {
            value = initializer.get();
            super.set(value);
        }

        if (remove) {
            super.remove();
        }
        return value;
    }

    /**
     * 获取值，如果不存在则返回默认值
     * @param remove 是否在获取后移除
     * @param defaultValue 默认值
     * @return 当前线程的值或默认值
     */
    public T getOrDefault(boolean remove, T defaultValue) {
        T value = super.get();
        if (value == null) {
            value = defaultValue;
        }

        if (remove) {
            super.remove();
        }
        return value;
    }

    /**
     * 检查值是否存在
     */
    public boolean isPresent() {
        return super.get() != null;
    }

    /**
     * 移除当前线程的值
     * @return 被移除的值（可能为null）
     */
    public T removeAndGet() {
        T value = super.get();
        super.remove();
        return value;
    }
}
