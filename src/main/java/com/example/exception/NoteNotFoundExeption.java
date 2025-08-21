package com.example.exception;

public class NoteNotFoundExeption extends RuntimeException{
    public NoteNotFoundExeption(String message) {
        super(message);
    }

    public NoteNotFoundExeption(Long id) {
        super("Нотатку за таким ID: " + id + " не знайдено");
    }
}
