package com.example.util;

import java.util.concurrent.atomic.AtomicLong;

// відповідає тільки за генерацію унікальних ID IdGenerator
public final class IdGenerator {
    private static final AtomicLong counter = new AtomicLong(0);

    public static Long generateUniqueId() {
        return counter.incrementAndGet();
    }
}
