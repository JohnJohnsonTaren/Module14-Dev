package com.example.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorTest {
    
    private IdGenerator idGenerator;
    
    @BeforeEach
    void setUp() {
        idGenerator = new IdGenerator();
    }
    
    @Test
    void testGenerateUniqueId_IncreasingNumbers() {
        // Given & When
        Long firstId = idGenerator.generateUniqueId();
        Long secondId = idGenerator.generateUniqueId();
        Long thirdId = idGenerator.generateUniqueId();
        
        // Then
        assertNotNull(firstId);
        assertNotNull(secondId);
        assertNotNull(thirdId);
        assertEquals(1L, firstId);
        assertEquals(2L, secondId);
        assertEquals(3L, thirdId);
    }
    
    @Test
    void testGenerateUniqueId_AllIdsAreUnique() {
        // Given
        int numberOfIds = 100;
        java.util.Set<Long> generatedIds = new java.util.HashSet<>();
        
        // When
        for (int i = 0; i < numberOfIds; i++) {
            Long id = idGenerator.generateUniqueId();
            generatedIds.add(id);
        }
        
        // Then
        assertEquals(numberOfIds, generatedIds.size(), "Всі ID повинні бути унікальними");
    }
}