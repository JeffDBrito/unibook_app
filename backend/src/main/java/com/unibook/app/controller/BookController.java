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

@RestController
@RequestMapping("/books")
public class BookController {
    
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // List books
    @GetMapping
    public List<BookResponse> getAllBooks() {
        return bookService.findAll();
    }

    // Create book
    @PostMapping
    public BookResponse createBook(@RequestBody CreateBookRequest request) {
        return bookService.createBook(
            request.getTitle(), 
            request.getIsbn(), 
            request.getDescription(),
            request.getPublicationYear(),
            request.getPublisherId(),
            request.getAuthorIds()
        );
    }

    // Get book by id
    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    // Get book by isbn
    @GetMapping("/isbn/{isbn}")
    public BookResponse getBookByIsbn(@PathVariable String isbn) {
        return bookService.findByIsbn(isbn);
    }

    // Get book by title
    @GetMapping("/title/{title}")
    public BookResponse getBookByTitle(@PathVariable String title) {
        return bookService.findByTitle(title);
    }


}
