package ro.unibuc.hello.data;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

import org.springframework.data.annotation.Id;

@Document
public class BookEntity {
    @Id
    private String id;

    private String title;
    private String author;

    public BookEntity() {}

    public BookEntity(String title, String author){
        this.title = title;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author);
    }
}