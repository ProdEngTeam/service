package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import ro.unibuc.hello.data.LoanEntity;
import ro.unibuc.hello.data.LoanRepository;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;

    // @Autowired
    // private BookRepository bookRepository;

    // @Autowired
    // private ClientRepository clientRepository;


    /// nu stiu daca ne trebuie :/
    // @PostMapping
    // public LoanEntity createLoan(@RequestBody LoanEntity loan) {

    //     return loanRepository.save(loan);
    // }

    @GetMapping("/get-loans")
    @ResponseBody
    @Timed(value = "hello.loan.time", description = "Time taken to return a loan")
    @Counted(value = "hello.loan.count", description = "Times loan was returned")
    public List<LoanEntity> getAllLoans() {
        return loanRepository.findAll();
    }

    @GetMapping("/{id}")
    public LoanEntity getLoanById(@PathVariable String id) {
        return loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found with id: " + id));
    }

    @PutMapping("/{id}/return")
    public LoanEntity returnBook(@PathVariable String id) {
        LoanEntity loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found with id: " + id));
        loan.setReturnDate(LocalDate.now());
        loan.setIsReturned(true);
        return loanRepository.save(loan);
    }

}
