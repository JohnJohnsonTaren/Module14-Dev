package com.example.service;

import com.example.entity.Note;
import com.example.exception.NoteNotFoundExeption;
import com.example.repository.NoteRepository; // ← Змінено import
import com.example.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) { // ← Змінено параметри
        this.noteRepository = noteRepository;
    }

    @Override
    public List<Note> listAll() {
        return noteRepository.findAll();
    }

    @Override
    public Note add(Note note) {
        Long id = IdGenerator.generateUniqueId();
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
                .orElseThrow(() -> new NoteNotFoundExeption(id));

        existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent());
        noteRepository.save(existingNote);
    }

    @Override
    public Note getById(long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundExeption(id));
    }
}