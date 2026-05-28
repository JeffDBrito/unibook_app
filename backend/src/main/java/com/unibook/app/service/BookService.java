package com.unibook.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.book.CreateBookRequest;
import com.unibook.app.dto.request.book.PartialUpdateBookRequest;
import com.unibook.app.dto.request.book.UpdateBookRequest;
import com.unibook.app.dto.response.BookResponse;
import com.unibook.app.exceptions.BadRequestException;
import com.unibook.app.exceptions.ResourceNotFoundException;
import com.unibook.app.mapper.BookMapper;
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
     * @param request
     * @return BookResponse
     * @throws ResourceNotFoundException
     */
    public BookResponse createBook(CreateBookRequest request) {

        String title = request.getTitle();
        String isbn = request.getIsbn();
        String description = request.getDescription();
        Integer publicationYear = request.getPublicationYear();
        Long publisherId = request.getPublisherId();
        Set<Long> authorIds = request.getAuthorIds();
        Set<Long> categoryIds = request.getCategoryIds();

        if(bookRepository.existsByIsbn(isbn)){
            throw new BadRequestException("Isbn already exists");
        }

        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setDescription(description);
        book.setPublicationYear(publicationYear);

        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found"));
        book.setPublisher(publisher);

        Set<Author> authors = new HashSet<>(authorRepository.findAllById(authorIds));
        if (authors.size() != authorIds.size()) {
            throw new ResourceNotFoundException("One or more authors not found");
        }
        book.setAuthors(authors);
        
        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));
        if (categories.size() != categoryIds.size()) {
            throw new ResourceNotFoundException("One or more categories not found");
        }
        book.setCategories(categories);

        Book savedBook = bookRepository.save(book);
        return BookMapper.toResponse(savedBook);
    }

    /**
     * Update Book
     * @param id
     * @param request
     * @param partial
     * @return BookResponse
     * @throws ResourceNotFoundException
     */
    public BookResponse update(Long id, PartialUpdateBookRequest request, boolean partial) {
        
        Book book = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        
        if (!partial || request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        
        String isbn = request.getIsbn();
        if (!partial || isbn != null) {            
            if(bookRepository.existsByIsbn(isbn)){
                throw new BadRequestException("Isbn already exists");
            }
            book.setIsbn(isbn);
        }

        if (!partial || request.getDescription() != null) {
            book.setDescription(request.getDescription());
        }

        if (!partial || request.getPublicationYear() != null) {
            book.setPublicationYear(request.getPublicationYear());
        }

        if (!partial || request.getPublisherId() != null) {

            Publisher publisher = publisherRepository.findById(request.getPublisherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Publisher not found"));

            book.setPublisher(publisher);
        }

        if (!partial || request.getAuthorIds() != null) {

            Set<Author> authors = new HashSet<>(authorRepository.findAllById(request.getAuthorIds()));

            book.setAuthors(authors);
        }

        if (!partial || request.getCategoryIds() != null) {

            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.getCategoryIds()));

            book.setCategories(categories);
        }

        return BookMapper.toResponse(bookRepository.save(book));
    }

    /**
     * Full Update
     * Convert FullUpdateRequest to PartialUpdateRequest
     * @param id
     * @param request
     * @return
     */
    public BookResponse update(Long id, UpdateBookRequest request){
        return this.update(id,BookMapper.toPartialUpdate(request), false);
    }

    /**
     * Soft Delete a Book by id
     * @param id
     */
    public void deleteById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        book.softDelete();
        bookRepository.save(book);
    }

    /**
     * Restore Book by id
     * @param id
     * @return BookResponse
     */
    public BookResponse restoreById(Long id){
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        book.restore();

        bookRepository.save(book);

        return BookMapper.toResponse(book);
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
        return books.stream().map(BookMapper::toResponse).toList();
    }

    /**
     * Find Book by id
     * @param id
     * @return BookResponse
     * @throws ResourceNotFoundException
     */
    public BookResponse findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return BookMapper.toResponse(book);
    }

    /**
     * Find Book by isbn
     * @param isbn
     * @return BookResponse
     * @throws ResourceNotFoundException
     */
    public BookResponse findByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
        return BookMapper.toResponse(book);
    }

    /**
     * Find Book by title
     * @param title
     * @return BookResponse
     * @throws ResourceNotFoundException
     */
    public BookResponse findByTitle(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with title: " + title));
        return BookMapper.toResponse(book);
    }

}
