package com.unibook.app.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.unibook.app.dto.request.loan.CreateLoanRequest;
import com.unibook.app.dto.response.LoanResponse;
import com.unibook.app.model.Book;
import com.unibook.app.model.Copy;
import com.unibook.app.model.User;
import com.unibook.app.repository.BookRepository;
import com.unibook.app.repository.CopyRepository;
import com.unibook.app.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
class LoanServiceTest {

    @Autowired
    private LoanService loanService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CopyRepository copyRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldCreateLoanSuccessfully() {

        User user = new User();
        user.setLogin("john");
        user.setPassword("123");

        user = userRepository.save(user);

        Book book = new Book();
        book.setTitle("1984");

        book = bookRepository.save(book);

        Copy copy = new Copy();
        copy.setCode("COPY-001");
        copy.setBook(book);

        copy = copyRepository.save(copy);

        CreateLoanRequest request = new CreateLoanRequest();

        request.setUserId(user.getId());
        request.setCopyId(copy.getId());
        request.setDueDate(LocalDate.now().plusDays(7));

        LoanResponse response = loanService.createLoan(request);

        assertNotNull(response);
        assertEquals(user.getId(), response.getUser().getId());
        assertEquals(copy.getId(), response.getCopy().getId());
        assertEquals("ACTIVE", response.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenCopyIsUnavailable() {

        User user = new User();
        user.setLogin("john");
        user.setPassword("123");

        user = userRepository.save(user);

        Book book = new Book();
        book.setTitle("1984");

        book = bookRepository.save(book);

        Copy copy = new Copy();
        copy.setCode("COPY-001");
        copy.setBook(book);

        copy = copyRepository.save(copy);

        CreateLoanRequest request = new CreateLoanRequest();

        request.setUserId(user.getId());
        request.setCopyId(copy.getId());
        request.setDueDate(LocalDate.now().plusDays(7));

        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> loanService.createLoan(request)
        );

        assertEquals(
            "Copy is not available",
            exception.getMessage()
        );
    }

    @Test
    void shouldReturnLoanSuccessfully() {

        User user = new User();
        user.setLogin("john");
        user.setPassword("123");

        user = userRepository.save(user);

        Book book = new Book();
        book.setTitle("1984");

        book = bookRepository.save(book);

        Copy copy = new Copy();
        copy.setCode("COPY-001");
        copy.setBook(book);

        copy = copyRepository.save(copy);

        CreateLoanRequest request = new CreateLoanRequest();

        request.setUserId(user.getId());
        request.setCopyId(copy.getId());
        request.setDueDate(LocalDate.now().plusDays(7));

        LoanResponse createdLoan = loanService.createLoan(request);

        LoanResponse returnedLoan = loanService.returnLoan(createdLoan.getId());

        assertNotNull(returnedLoan);

        assertEquals("RETURNED", returnedLoan.getStatus());

        assertNotNull(returnedLoan.getReturnDate());

        Copy updatedCopy = copyRepository.findById(copy.getId())
            .orElseThrow();

        assertEquals("AVAILABLE",updatedCopy.getStatus());
    }

}