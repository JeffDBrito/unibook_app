package com.unibook.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.request.book.CreateBookRequest;
import com.unibook.app.dto.request.book.PartialUpdateBookRequest;
import com.unibook.app.dto.request.book.UpdateBookRequest;
import com.unibook.app.dto.response.BookResponse;
import com.unibook.app.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
    
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // List books
    @PreAuthorize("hasAuthority('BOOK_LIST')")
    @GetMapping
    @Operation(summary = "List books", description = "Retrieves a list of all books and returns their details.", tags = {"Book Endpoints"})
    public List<BookResponse> getAll() {
        return bookService.findAll();
    }

    // Create book
    @PreAuthorize("hasAuthority('BOOK_CREATE')")
    @PostMapping
    @Operation(summary = "Create a new book", description = "Creates a new book with the provided details and returns the created book.", tags = {"Book Endpoints"})
    public BookResponse create(@Valid @RequestBody CreateBookRequest request) {
        System.out.println("CreateBookRequest: " + request);
        return bookService.createBook(request);
    }

    // Partial update
    @PreAuthorize("hasAuthority('BOOK_UPDATE')")
    @PatchMapping("/{id}")
    @Operation(summary = "Partial update book", description = "Partially updates an existing book with the provided details and returns the updated book.", tags = {"Book Endpoints"})
    public BookResponse partialUpdate( @PathVariable Long id, @Valid @RequestBody PartialUpdateBookRequest request ) {
        return bookService.update(id, request, true);
    }

    // Full update
    @PreAuthorize("hasAuthority('BOOK_UPDATE')")
    @PutMapping("/{id}")
    @Operation(summary = "Update book", description = "Updates an existing book with the provided details and returns the updated book.", tags = {"Book Endpoints"})
    public BookResponse fullUpdate( @PathVariable Long id, @Valid @RequestBody UpdateBookRequest request) {
        return bookService.update(id, request);
    }

    // Get book by id
    @PreAuthorize("hasAuthority('BOOK_READ')")
    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", description = "Retrieves a book by their id and returns the book details.", tags = {"Book Endpoints"})
    public BookResponse getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    // Get book by isbn
    @PreAuthorize("hasAuthority('BOOK_READ')")
    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "Get book by ISBN", description = "Retrieves a book by their ISBN and returns the book details.", tags = {"Book Endpoints"})
    public BookResponse getByIsbn(@PathVariable String isbn) {
        return bookService.findByIsbn(isbn);
    }

    // Get book by title
    @PreAuthorize("hasAuthority('BOOK_READ')")
    @GetMapping("/title/{title}")
    @Operation(summary = "Get book by title", description = "Retrieves a book by their title and returns the book details.", tags = {"Book Endpoints"})
    public BookResponse getByTitle(@PathVariable String title) {
        return bookService.findByTitle(title);
    }

    // Delete book by id
    @PreAuthorize("hasAuthority('BOOK_DELETE')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete book by id", description = "Deletes a book by their id and returns no content.", tags = {"Book Endpoints"})
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    // Restore book by id
    @PreAuthorize("hasAuthority('BOOK_RESTORE')")
    @PostMapping("/{id}/restore")
    @Operation(summary = "Restore book by id", description = "Restores a previously deleted book by their id and returns the restored book details.", tags = {"Book Endpoints"})
    public BookResponse restoreById(@PathVariable Long id) {
        return bookService.restoreById(id);
    }

}
