package com.unibook.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.request.CreateBookRequest;
import com.unibook.app.dto.response.BookResponse;
import com.unibook.app.service.BookService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/books")
public class BookController {
    
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // List books
    @GetMapping
    @Operation(summary = "List books", description = "Retrieves a list of all books and returns their details.")
    public List<BookResponse> getAllBooks() {
        return bookService.findAll();
    }

    // Create book
    @PostMapping
    @Operation(summary = "Create a new book", description = "Creates a new book with the provided details and returns the created book.")
    public BookResponse createBook(@RequestBody CreateBookRequest request) {
        return bookService.createBook(
            request.getTitle(), 
            request.getIsbn(), 
            request.getDescription(),
            request.getPublicationYear(),
            request.getPublisherId(),
            request.getAuthorIds(),
            request.getCategoryIds()
        );
    }

    // Get book by id
    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", description = "Retrieves a book by their id and returns the book details.")
    public BookResponse getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    // Get book by isbn
    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "Get book by ISBN", description = "Retrieves a book by their ISBN and returns the book details.")
    public BookResponse getBookByIsbn(@PathVariable String isbn) {
        return bookService.findByIsbn(isbn);
    }

    // Get book by title
    @GetMapping("/title/{title}")
    @Operation(summary = "Get book by title", description = "Retrieves a book by their title and returns the book details.")
    public BookResponse getBookByTitle(@PathVariable String title) {
        return bookService.findByTitle(title);
    }


}
