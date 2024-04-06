package ro.unibuc.hello.dto;

public class CreateClientDto {
    public String fullName;
    public String favouriteBook;

    public CreateClientDto() {}

    public CreateClientDto(String fullName, String favouriteBook) {
        this.fullName = fullName;
        this.favouriteBook = favouriteBook;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFavouriteBook() {
        return favouriteBook;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setFavouriteBook(String favouriteBook) {
        this.favouriteBook = favouriteBook;
    }
}
