package com.example.entity;//  Створи сутність com.example.entity.Note - нотатка.
//      В цій сутності мають бути наступні поля:
//          id - унікальний ідентифікатор, ціле автогенероване число
//          title - назва нотатки. Рядок (String).
//          content - контент нотатки. Рядок (String).
//  Оскільки в проєкті поки немає підключеного модулю Spring Data, не додавай до сутності анотацію @Entity.

public class Note {
    private Long id;
    private String title;
    private String content;

    public Note() {
    }

    public Note(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "com.example.entity.Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
