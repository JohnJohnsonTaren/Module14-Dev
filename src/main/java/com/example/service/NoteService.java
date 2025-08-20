//Завдання №3 - створити CRUD сервіс NoteService
//Створи новий клас з назвою NoteService. Це клас для управління нотатками.
// В майбутньому нотатки будуть зберігатись в БД, наразі використай
// колецію (List<com.example.entity.Note> або Map<Long, com.example.entity.Note>) для зберігання цих нотаток.
//
//В сервісі мають бути наступні методи:
//
//List<com.example.entity.Note> listAll() - повертає список всіх нотаток
//com.example.entity.Note add(com.example.entity.Note note) - додає нову нотатку, генеруючи для цієї нотатки унікальний
// (випадковий) числовий ідентифікатор, повертає цю ж нотатку з згенерованим ідентифікатором.
//void deleteById(long id) - видаляє нотатку з вказаним ідентифікатором.
// Якщо нотатки з ідентифікатором немає - викидає виключення.
//void update(com.example.entity.Note note) - шукає нотатку по note.id.
// Якщо нотатка є - оновлює для неї title та content.
// Якщо нотатки немає - викидає виключення.
//com.example.entity.Note getById(long id) - повертає нотатку по її ідентифікатору.
// Якщо нотатки немає - викидає виключення.
//Використай анотацію @Service, щоб зробити цей клас Spring Bean.
//
//Запусти програму, і переконайсь, що вона успішно запускається (без помилок в консолі).

package com.example.service;

import com.example.entity.Note;
import com.example.repository.NoteRepository;
import com.example.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// містить бізнес-логіку `NoteService`
@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final IdGenerator idGenerator;

    @Autowired
    public NoteService(NoteRepository noteRepository, IdGenerator idGenerator) {
        this.noteRepository = noteRepository;
        this.idGenerator = idGenerator;
    }

    public List<Note> listAll() {
        return noteRepository.findAll();
    }

    public Note add(Note note) {
        Long id = idGenerator.generateUniqueId(noteRepository::existsById);
        Note newNote = new Note(id, note.getTitle(), note.getContent());
        return noteRepository.save(newNote);
    }

    public void deleteById(long id) {
        if (!noteRepository.existsById(id)) {
            throw new IllegalArgumentException("Нотатку " + id + " не знайдено");
        }
        noteRepository.deleteById(id);
    }

    public void update(Note note) {
        Long id = note.getId();
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Нотатку " + id + " не знайдено"));

        existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent());
        noteRepository.save(existingNote);
    }

    public Note getById(long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Нотатку" + id + " не знайдено"));
    }
}
