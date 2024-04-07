package ro.unibuc.hello.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.CreateBookDto;
import ro.unibuc.hello.dto.CreateClientDto;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class ClientControllerUnitTest {
    @Mock
    ClientRepository clientRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    LoanRepository loanRepository;
    @InjectMocks
    ClientController clientController;
    @InjectMocks
    BookController bookController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createClientSuccess() throws Exception {
        ///Arrange
        var createClientDto = new CreateClientDto("Aurel Aurescu", "1994");
        ClientEntity client = new ClientEntity("Aurel Aurescu", "1994");
        when(clientRepository.save(any())).thenReturn(client);

        //Act
        var result = mockMvc.perform(post("/clients")
                        .content(objectMapper.writeValueAsString(createClientDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        Assertions.assertEquals(objectMapper.writeValueAsString(client),
                result.getResponse().getContentAsString());
    }

    @Test
    void getClientById() throws Exception {
        var id = 1;
        ClientEntity client = new ClientEntity("TestName", "TestBook");
        //Arrange
        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        //Act
        var result = mockMvc.perform(get("/clients/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        Assertions.assertEquals(objectMapper.writeValueAsString(client),
                result.getResponse().getContentAsString());
    }

    @Test
    void deleteClientSuccessfully() throws Exception {
        // Arrange
        ClientEntity client = new ClientEntity("TestNme", "Testbook");
        var id = "1";
        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        doNothing().when(clientRepository).deleteById(anyString());

        // ACT
        var result = mockMvc.perform(delete("/clients/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        // ASSERT
        Assertions.assertEquals(200,
                result.getResponse().getStatus());
    }

    @Test
    void addBookToClientAndCreateLoanSuccessfully() throws Exception {
        //Arrange
        // Arrange
        var clientId = 1337;
        ClientEntity client = new ClientEntity("Arnold Einstein", "Cartea abecedar");
        var createClientDto = new CreateClientDto("Arnold Einstein", "Cartea abecedar");

        var bookId = 420;
        BookEntity book = new BookEntity("Om bogat Om sarac", "Irwin Shaw");
        var createBookDto = new CreateBookDto("Om bogat Om sarac", "Irwin Shaw");
        LoanEntity loan = new LoanEntity();
        when(clientRepository.findById(any())).thenReturn(Optional.of(client));
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        when(loanRepository.save(any())).thenReturn(loan);

        //Act
        var result = mockMvc.perform(post("/clients/{clientId}/books/{bookId}", clientId, bookId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(objectMapper.writeValueAsString(loan),
                result.getResponse().getContentAsString());
    }

    @Test
    void editClientSuccessfully() throws Exception {
        // Arrange
        String id = "1337";
        ClientEntity client = new ClientEntity("Arnold Einstein", "Cartea abecedar");
        
        // Assuming CreateClientDto has a similar structure to ClientEntity for this example
        CreateClientDto createClientDto = new CreateClientDto("Arnold Einstein Updated", "Cartea abecedar Updated");
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(client);

        // Act + assert
        mockMvc.perform(put("/clients/{id}", id)
                .content(objectMapper.writeValueAsString(createClientDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(client)));
    }
}