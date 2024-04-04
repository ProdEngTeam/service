package ro.unibuc.hello.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.LoanEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoanControllerUnitTest {
    @Autowired
    LoanController loanController;
    
    @Test
    void getLoanByIdSuccessfully() {
        List<LoanEntity> loanList = loanController.getAllLoans();
        LoanEntity loan = loanList.get(0);

        //Act
        LoanEntity readLoan = loanController.getLoanById(loan.getId());

        //Assert
        Assertions.assertDoesNotThrow(() -> {
            loanController.getLoanById(loan.getId());
        });
    }
}