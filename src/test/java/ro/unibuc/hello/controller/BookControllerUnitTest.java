package ro.unibuc.hello.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.BookRepository;
import ro.unibuc.hello.dto.CreateBookDto;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class BookControllerUnitTest {
    @Mock
    BookRepository bookRepository;
    @InjectMocks
    BookController bookController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        objectMapper = new ObjectMapper();
    }


    @Test
    void newBookCreatedSuccesfully() throws Exception {
        //Arrange
        var createBookDto = new CreateBookDto("Carte de bucate", "Alin Bucataru");
        BookEntity book = new BookEntity("Carte de bucate", "Alin Bucataru");
        when(bookRepository.save(any())).thenReturn(book);

        //Act
        var result = mockMvc.perform(post("/books")
                        .content(objectMapper.writeValueAsString(createBookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        //Assert
        Assertions.assertEquals(objectMapper.writeValueAsString(book),
                result.getResponse().getContentAsString());
    }
    @Test
    void getAllBooksSuccesfully() throws Exception {
        BookEntity book = new BookEntity("Carte de bucate", "Alin Bucataru");
        var bookList = List.of(book);
        when(bookRepository.findAll()).thenReturn(bookList);

        // ACT
        var result = mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andReturn();

        // ASSERT
        Assertions.assertEquals(objectMapper.writeValueAsString(bookList),
                result.getResponse().getContentAsString());
    }
    @Test
    void bookReadByIdSuccessfully() throws Exception {
        var id = 1;
        BookEntity book = new BookEntity("Carte de bucate", "Alin Bucataru");
        //Arrange
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        //Act
        var result = mockMvc.perform(get("/books/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        Assertions.assertEquals(objectMapper.writeValueAsString(book),
                result.getResponse().getContentAsString());
    }

    @Test
    void deleteBookSuccesfully() throws Exception {
        // Arrange
        BookEntity book = new BookEntity("Test Book", "Test Author");
        var id = "1";
        doNothing().when(bookRepository).deleteById(anyString());

        // ACT
        var result = mockMvc.perform(delete("/books/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        // ASSERT
        Assertions.assertEquals(200,
                result.getResponse().getStatus());
    }

    /*@Test
    void editBookSuccessfully() throws Exception {
        // Arrange
        var id = 420;
        BookEntity book = new BookEntity("Om bogat Om sarac", "Irwin Shaw");
        var createBookDto = new CreateBookDto("Om bogat Om sarac", "Irwin Shaw");
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        when(bookRepository.save(any())).thenReturn(Optional.of(book));

        //Act
        var result = mockMvc.perform(put("/books/{id}", id)
                        .content(objectMapper.writeValueAsString(createBookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(objectMapper.writeValueAsString(book),
                result.getResponse().getContentAsString());
    }*/
}