package com.example.repository;

import com.example.entity.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class NoteRepositoryImplTest {
    
    private NoteRepositoryImpl noteRepositoryImpl;
    
    @BeforeEach
    void setUp() {
        noteRepositoryImpl = new NoteRepositoryImpl();
    }
    
    @Test
    void testSave_SavesAndReturnsNote() {
        // Given
        Note note = new Note(1L, "Тестовий заголовок", "Тестовий контент");
        
        // When
        Note savedNote = noteRepositoryImpl.save(note);
        
        // Then
        assertNotNull(savedNote);
        assertEquals(note.getId(), savedNote.getId());
        assertEquals(note.getTitle(), savedNote.getTitle());
        assertEquals(note.getContent(), savedNote.getContent());
    }
    
    @Test
    void testFindById_Note() {
        // Given
        Note note = new Note(1L, "Заголовок", "Контент");
        noteRepositoryImpl.save(note);
        
        // When
        Optional<Note> found = noteRepositoryImpl.findById(1L);
        
        // Then
        assertTrue(found.isPresent());
        assertEquals(note.getId(), found.get().getId());
        assertEquals(note.getTitle(), found.get().getTitle());
    }
    
    @Test
    void testFindById_Empty() {
        // When
        Optional<Note> found = noteRepositoryImpl.findById(999L);
        
        // Then
        assertFalse(found.isPresent());
    }
    
    @Test
    void testFindAll_AllNotes() {
        // Given
        Note note1 = new Note(1L, "Заголовок 1", "Контент 1");
        Note note2 = new Note(2L, "Заголовок 2", "Контент 2");
        noteRepositoryImpl.save(note1);
        noteRepositoryImpl.save(note2);
        
        // When
        List<Note> allNotes = noteRepositoryImpl.findAll();
        
        // Then
        assertEquals(2, allNotes.size());
        assertTrue(allNotes.contains(note1));
        assertTrue(allNotes.contains(note2));
    }
    
    @Test
    void testExistsById_True() {
        // Given
        Note note = new Note(1L, "Заголовок", "Контент");
        noteRepositoryImpl.save(note);
        
        // When
        boolean exists = noteRepositoryImpl.existsById(1L);
        
        // Then
        assertTrue(exists);
    }
    
    @Test
    void testExistsById_False() {
        // When
        boolean exists = noteRepositoryImpl.existsById(999L);
        
        // Then
        assertFalse(exists);
    }
    
    @Test
    void testDeleteById() {
        // Given
        Note note = new Note(1L, "Заголовок", "Контент");
        noteRepositoryImpl.save(note);
        assertTrue(noteRepositoryImpl.existsById(1L));
        
        // When
        noteRepositoryImpl.deleteById(1L);
        
        // Then
        assertFalse(noteRepositoryImpl.existsById(1L));
        assertTrue(noteRepositoryImpl.findById(1L).isEmpty());
    }
}