package com.example.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;


// відповідає тільки за генерацію унікальних ID IdGenerator
@Component
public class IdGenerator implements IIdGenerator {
    private final AtomicLong counter = new AtomicLong(0);

    public Long generateUniqueId() {
        return counter.incrementAndGet();
    }
}
