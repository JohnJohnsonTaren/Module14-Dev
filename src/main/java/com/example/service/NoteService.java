package com.example.service;

import com.example.entity.Note;
import com.example.exception.NoteNotFoundExeption;
import com.example.repository.INoteRepository; // ← Змінено import
import com.example.util.IIdGenerator;          // ← Змінено import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService implements INoteService {
    private final INoteRepository noteRepository;  // ← Змінено тип
    private final IIdGenerator idGenerator;        // ← Змінено тип

    @Autowired
    public NoteService(INoteRepository noteRepository, IIdGenerator idGenerator) { // ← Змінено параметри
        this.noteRepository = noteRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public List<Note> listAll() {
        return noteRepository.findAll();
    }

    @Override
    public Note add(Note note) {
        Long id = idGenerator.generateUniqueId();
        Note newNote = new Note(id, note.getTitle(), note.getContent());
        return noteRepository.save(newNote);
    }

    @Override
    public void deleteById(long id) {
        if (!noteRepository.existsById(id)) {
            throw new NoteNotFoundExeption(id);
        }
        noteRepository.deleteById(id);
    }

    @Override
    public void update(Note note) {
        Long id = note.getId();
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Нотатку " + id + " не знайдено"));

        existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent());
        noteRepository.save(existingNote);
    }

    @Override
    public Note getById(long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Нотатку " + id + " не знайдено"));
    }
}