package com.example.repository;

import com.example.entity.Note;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {
    List<Note> findAll();

    Optional<Note> findById(Long id);

    Note save (Note note);

    boolean existsById(Long id);

    void deleteById(Long id);
}
