package com.example.util;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.function.Predicate;


// відповідає тільки за генерацію унікальних ID IdGenerator
@Component
public class IdGenerator {
    private final Random random = new Random();

    public Long generateUniqueId(Predicate<Long> existsChacker) {
        Long id;
        do {
            id = Math.abs(random.nextLong());
        } while (existsChacker.test(id) || id == 0);
        return id;
    }
}
