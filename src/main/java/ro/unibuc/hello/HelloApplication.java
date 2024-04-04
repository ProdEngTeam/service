package ro.unibuc.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import ro.unibuc.hello.data.ClientEntity;
import ro.unibuc.hello.data.ClientRepository;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.LoanEntity;
import ro.unibuc.hello.data.LoanRepository;
import ro.unibuc.hello.data.BookEntity;
import ro.unibuc.hello.data.BookRepository;

import javax.annotation.PostConstruct;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {InformationRepository.class, ClientRepository.class, BookRepository.class})
public class HelloApplication {

	@Autowired
	private InformationRepository informationRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private LoanRepository loanRepository;

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

	@PostConstruct
	public void runAfterObjectCreated() {
		informationRepository.deleteAll();
		informationRepository.save(new InformationEntity("Overview",
				"This is an example of using a data storage engine running separately from our applications server"));

		clientRepository.deleteAll();
		ClientEntity client1 = new ClientEntity("Johnny Bravo", "1984");
		ClientEntity client2 = new ClientEntity("Spongebob", "Baltagul");
		clientRepository.saveAll(Arrays.asList(client1, client2));

		bookRepository.deleteAll();
		BookEntity book1 = new BookEntity("1984", "George Orwell");
		BookEntity book2 = new BookEntity("Brave New World", "Aldous Huxley");
		BookEntity book3 = new BookEntity("The Catcher in the Rye", "J.D. Salinger");
		BookEntity book4 = new BookEntity("To Kill a Mockingbird", "Harper Lee");
		bookRepository.saveAll(List.of(book1, book2, book3, book4));
		Logger logger = LoggerFactory.getLogger(getClass());

		loanRepository.deleteAll();
		LoanEntity loan1 = new LoanEntity(book1, client1, LocalDate.of(2024, 04, 04), LocalDate.of(2024, 04, 12));
		loanRepository.save(loan1);
		


		logger.info("Am construit clasele.");
	}
}
