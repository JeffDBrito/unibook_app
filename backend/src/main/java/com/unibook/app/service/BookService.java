package com.unibook.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.response.BookResponse;
import com.unibook.app.model.Book;
import com.unibook.app.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
    
    private final BookRepository bookRepository;

    public BookResponse createBook(String title, String isbn, Integer publicationYear) {
        var book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPublicationYear(publicationYear);
        var savedBook = bookRepository.save(book);
        return toResponse(savedBook);
    }

    public List<BookResponse> findAll() {
        var books = bookRepository.findAll();
        return books.stream().map(this::toResponse).toList();
    }

    public BookResponse findById(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return toResponse(book);
    }

    public BookResponse findByIsbn(String isbn) {
        var book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found with ISBN: " + isbn));
        return toResponse(book);
    }

    public BookResponse findByTitle(String title) {
        var book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Book not found with title: " + title));
        return toResponse(book);
    }

    private BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setIsbn(book.getIsbn());
        response.setPublicationYear(book.getPublicationYear());
        return response;
    }


}
