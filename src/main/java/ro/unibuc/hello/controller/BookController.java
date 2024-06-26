package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Optional;

import ro.unibuc.hello.data.BookRepository;
import ro.unibuc.hello.data.BookEntity;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public BookEntity createBook(@RequestBody BookEntity book) {
        return bookRepository.save(book);
    }

    @GetMapping
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public BookEntity getBookById(@PathVariable String id) {
        return bookRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable String id) {
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookEntity> editBooks(@PathVariable String id, @RequestBody BookEntity bookEntity) {
        Optional<BookEntity> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            BookEntity book = optionalBook.get();
            BeanUtils.copyProperties(bookEntity, book, "id");
            bookRepository.save(book);
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}