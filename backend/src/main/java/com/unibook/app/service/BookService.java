package com.unibook.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.book.UpdateBookRequest;
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

    // --------------------- //
    // Management Operations //
    // --------------------- //

    /**
     * Create Book
     * @param title
     * @param isbn
     * @param description
     * @param publicationYear
     * @param publisherId
     * @param authorIds
     * @param categoryIds
     * @return BookResponse
     * @throws RuntimeException
     */
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

    /**
     * Update Book
     * @param id
     * @param request
     * @param partial
     * @return BookResponse
     * @throws RuntimeException
     */
    public BookResponse update(Long id, UpdateBookRequest request, boolean partial) {

        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!partial || request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }

        if (!partial || request.getIsbn() != null) {
            book.setIsbn(request.getIsbn());
        }

        if (!partial || request.getDescription() != null) {
            book.setDescription(request.getDescription());
        }

        if (!partial || request.getPublicationYear() != null) {
            book.setPublicationYear(request.getPublicationYear());
        }

        if (!partial || request.getPublisherId() != null) {

            Publisher publisher = publisherRepository.findById(request.getPublisherId())
                    .orElseThrow(() -> new RuntimeException("Publisher not found"));

            book.setPublisher(publisher);
        }

        if (!partial || request.getAuthorIds() != null) {

            List<Author> authors = authorRepository.findAllById(request.getAuthorIds());

            book.setAuthors(authors);
        }

        if (!partial || request.getCategoryIds() != null) {

            List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());

            book.setCategories(categories);
        }

        return toResponse(bookRepository.save(book));
    }

    /**
     * Delete Book by id
     * @param id
     */
    public void deleteById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        
        bookRepository.delete(book);
    }

    /**
     * Restore Book by id
     * @param id
     * @return BookResponse
     */
    public BookResponse restoreById(Long id){
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        book.setDeletedAt(null);

        bookRepository.save(book);

        return toResponse(book);
    }

    // ----------------- //
    // Search Operations //
    // ----------------- //

    /**
     * List all Books
     * @return List<BookResponse>
     */
    public List<BookResponse> findAll() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::toResponse).toList();
    }

    /**
     * Find Book by id
     * @param id
     * @return BookResponse
     * @throws RuntimeException
     */
    public BookResponse findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return toResponse(book);
    }

    /**
     * Find Book by isbn
     * @param isbn
     * @return BookResponse
     * @throws RuntimeException
     */
    public BookResponse findByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found with ISBN: " + isbn));
        return toResponse(book);
    }

    /**
     * Find Book by title
     * @param title
     * @return BookResponse
     * @throws RuntimeException
     */
    public BookResponse findByTitle(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Book not found with title: " + title));
        return toResponse(book);
    }

    // -------------- //
    // Helper Methods //
    // -------------- //

    /**
     * Convert Book instance to BookResponse dto
     * @param book
     * @return BookResponse
     */
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
