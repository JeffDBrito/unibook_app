package com.unibook.app.mapper;

import java.util.stream.Collectors;

import com.unibook.app.dto.request.book.PartialUpdateBookRequest;
import com.unibook.app.dto.request.book.UpdateBookRequest;
import com.unibook.app.dto.response.BookResponse;
import com.unibook.app.model.Book;
import com.unibook.app.model.Category;

public class BookMapper {
    
    private BookMapper(){}

    /**
     * Convert Book Instance to BookResponse dto
     * @param book
     * @return BookResponse
     */
    public static BookResponse toResponse(Book book) {
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

    /**
     * Convert UpdateBookRequest to PartialUpdateBookRequest
     * @param request
     * @return PartialUpdateBookRequest
     */
    public static PartialUpdateBookRequest toPartialUpdate(UpdateBookRequest request){
        PartialUpdateBookRequest partial = new PartialUpdateBookRequest();
        partial.setTitle(request.getTitle());
        partial.setDescription(request.getDescription());
        partial.setIsbn(request.getIsbn());
        partial.setPublicationYear(request.getPublicationYear());
        partial.setPublisherId(request.getPublisherId());
        partial.setAuthorIds(request.getAuthorIds());
        partial.setCategoryIds(request.getCategoryIds());

        return partial;
    }

}
