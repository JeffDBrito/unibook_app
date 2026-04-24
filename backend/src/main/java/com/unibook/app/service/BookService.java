package com.unibook.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.response.BookResponse;
import com.unibook.app.model.Author;
import com.unibook.app.model.Book;
import com.unibook.app.model.Category;
import com.unibook.app.model.Publisher;
import com.unibook.app.repository.AuthorRepository;
import com.unibook.app.repository.BookRepository;
import com.unibook.app.repository.CategoryRepository;
import com.unibook.app.repository.PublisherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
    
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public BookResponse createBook(String title, String isbn, String description, Integer publicationYear, Long publisherId, List<Long> authorIds, List<Long> categoryIds) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setDescription(description);
        book.setPublicationYear(publicationYear);

        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + publisherId));
        book.setPublisher(publisher);

        List<Author> authors = authorRepository.findAllById(authorIds);
        if (authors.size() != authorIds.size()) {
            throw new RuntimeException("One or more authors not found");
        }
        book.setAuthors(authors);
        
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        if (categories.size() != categoryIds.size()) {
            throw new RuntimeException("One or more categories not found");
        }
        book.setCategories(categories);

        Book savedBook = bookRepository.save(book);
        return toResponse(savedBook);
    }

    public List<BookResponse> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::toResponse).toList();
    }

    public BookResponse findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return toResponse(book);
    }

    public BookResponse findByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found with ISBN: " + isbn));
        return toResponse(book);
    }

    public BookResponse findByTitle(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Book not found with title: " + title));
        return toResponse(book);
    }

    private BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setDescription(book.getDescription());
        response.setIsbn(book.getIsbn());
        response.setPublicationYear(book.getPublicationYear());
        response.setPublisher(
            book.getPublisher() != null ? book.getPublisher().getTitle() : null
        );

        String authors = book.getAuthors().stream()
            .map(author -> author.getPerson().getName())
            .collect(Collectors.joining(", "));

        response.setAuthors(authors);        

        String categories = book.getCategories().stream()
            .map(Category::getTitle)
            .collect(Collectors.joining(", "));

        response.setCategories(categories);

        return response;
    }

}
