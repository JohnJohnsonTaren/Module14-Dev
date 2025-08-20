package com.example.repository;

import com.example.entity.Note;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// NoteRepository відповідає тільки за збереження/отримання даних
@Repository
public class NoteRepository implements INoteRepository {
    private final Map<Long, Note> notes = new ConcurrentHashMap<>();

    public List<Note> findAll() {
        return new ArrayList<>(notes.values());
    }

    public Optional<Note> findById(Long id) {
        return Optional.ofNullable(notes.get(id));
    }

    public Note save(Note note) {
        notes.put(note.getId(), note);
        return note;
    }

    public boolean existsById(Long id) {
        return notes.containsKey(id);
    }

    public void deleteById(Long id) {
        notes.remove(id);
    }
}
