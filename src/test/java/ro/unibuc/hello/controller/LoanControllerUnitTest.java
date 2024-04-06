package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.ClientEntity;
import ro.unibuc.hello.data.LoanEntity;
import ro.unibuc.hello.data.LoanRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class LoanControllerUnitTest {
    @Mock
    LoanRepository loanRepository;
    @InjectMocks
    LoanController loanController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
        objectMapper = new ObjectMapper();
    }
    @Test
    void getLoanByIdSuccessfully() throws Exception {
        var id = 1;
        LoanEntity loan = new LoanEntity();
        //Arrange
        when(loanRepository.findById(any())).thenReturn(Optional.of(loan));
        //Act
        var result = mockMvc.perform(get("/loans/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        Assertions.assertEquals(objectMapper.writeValueAsString(loan),
                result.getResponse().getContentAsString());
    }
}