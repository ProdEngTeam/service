package ro.unibuc.hello.dto;

public class CreateBookDto {
    private String title;
    private String author;

    public CreateBookDto() {}

    public CreateBookDto(String title, String author){
        this.title = title;
        this.author = author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
