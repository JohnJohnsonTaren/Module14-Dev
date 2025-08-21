package com.example.service;

import com.example.entity.Note;
import com.example.exception.NoteNotFoundExeption;
import com.example.repository.NoteRepository;
import com.example.util.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceImplTest {

    @Mock
    private NoteRepository noteRepository;
    
    @Mock
    private IdGenerator idGenerator;
    
    private NoteServiceImpl noteServiceImpl;
    
    @BeforeEach
    void setUp() {
        noteServiceImpl = new NoteServiceImpl(noteRepository);
    }
    
    @Test
    void testListAll() {
        // Given
        Note note1 = new Note(1L, "Заголовок 1", "Контент 1");
        Note note2 = new Note(2L, "Заголовок 2", "Контент 2");
        List<Note> expectedNotes = Arrays.asList(note1, note2);
        when(noteRepository.findAll()).thenReturn(expectedNotes);
        
        // When
        List<Note> actualNotes = noteServiceImpl.listAll();
        
        // Then
        assertEquals(2, actualNotes.size());
        assertEquals(expectedNotes, actualNotes);
        verify(noteRepository, times(1)).findAll();
    }
    
    @Test
    void testAdd_CreatesNote() {
        // Given
        Note inputNote = new Note(null, "Новий заголовок", "Новий контент");
        Long generatedId = 5L;
        Note expectedNote = new Note(generatedId, "Новий заголовок", "Новий контент");
        
        try (MockedStatic<IdGenerator> mockedIdGenerator = Mockito.mockStatic(IdGenerator.class)) {
            mockedIdGenerator.when(IdGenerator::generateUniqueId).thenReturn(generatedId);
            when(noteRepository.save(any(Note.class))).thenReturn(expectedNote);
        
            // When
            Note result = noteServiceImpl.add(inputNote);
        
            // Then
            assertNotNull(result);
            assertEquals(generatedId, result.getId());
            assertEquals("Новий заголовок", result.getTitle());
            assertEquals("Новий контент", result.getContent());
        
            verify(noteRepository, times(1)).save(any(Note.class));
        }
    }
    
    @Test
    void testGetById_ReturnsExisting() {
        // Given
        Long noteId = 1L;
        Note expectedNote = new Note(noteId, "Заголовок", "Контент");
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(expectedNote));
        
        // When
        Note result = noteServiceImpl.getById(noteId);
        
        // Then
        assertNotNull(result);
        assertEquals(expectedNote, result);
        verify(noteRepository, times(1)).findById(noteId);
    }
    
    @Test
    void testGetById_ThrowsException() {
        // Given
        Long noteId = 999L;
        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());
        
        // When & Then
        NoteNotFoundExeption exception = assertThrows(
            NoteNotFoundExeption.class, 
            () -> noteServiceImpl.getById(noteId)
        );
        
        assertTrue(exception.getMessage().contains("999"));
        verify(noteRepository, times(1)).findById(noteId);
    }
    
    @Test
    void testDeleteById_Deletes() {
        // Given
        Long noteId = 1L;
        when(noteRepository.existsById(noteId)).thenReturn(true);
        
        // When
        noteServiceImpl.deleteById(noteId);
        
        // Then
        verify(noteRepository, times(1)).existsById(noteId);
        verify(noteRepository, times(1)).deleteById(noteId);
    }
    
    @Test
    void testDeleteById_ThrowsException() {
        // Given
        Long noteId = 999L;
        when(noteRepository.existsById(noteId)).thenReturn(false);
        
        // When & Then
        NoteNotFoundExeption exception = assertThrows(
            NoteNotFoundExeption.class,
            () -> noteServiceImpl.deleteById(noteId)
        );
        
        assertTrue(exception.getMessage().contains("999"));
        verify(noteRepository, times(1)).existsById(noteId);
        verify(noteRepository, never()).deleteById(noteId); // Змінено з times(1) на never()
    }
    
    @Test
    void testUpdate_Updates() {
        // Given
        Long noteId = 1L;
        Note existingNote = new Note(noteId, "Старий заголовок", "Старий контент");
        Note updatedNote = new Note(noteId, "Новий заголовок", "Новий контент");
        
        when(noteRepository.findById(noteId)).thenReturn(Optional.of(existingNote));
        
        // When
        noteServiceImpl.update(updatedNote);
        
        // Then
        assertEquals("Новий заголовок", existingNote.getTitle());
        assertEquals("Новий контент", existingNote.getContent());
        
        verify(noteRepository, times(1)).findById(noteId);
        verify(noteRepository, times(1)).save(existingNote);
    }
    
    @Test
    void testUpdate_ThrowsException() {
        // Given
        Long noteId = 999L;
        Note updatedNote = new Note(noteId, "Новий заголовок", "Новий контент");
        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());
        
        // When & Then
        NoteNotFoundExeption exception = assertThrows(
            NoteNotFoundExeption.class,
            () -> noteServiceImpl.update(updatedNote)
        );
        
        assertTrue(exception.getMessage().contains("999"));
        verify(noteRepository, times(1)).findById(noteId);
        verify(noteRepository, never()).save(any(Note.class));
    }
}