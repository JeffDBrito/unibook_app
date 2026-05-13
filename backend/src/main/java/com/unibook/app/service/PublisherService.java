package com.unibook.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.publisher.UpdatePublisherRequest;
import com.unibook.app.dto.response.PublisherResponse;
import com.unibook.app.model.Publisher;
import com.unibook.app.repository.PublisherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherService {
    
    private final PublisherRepository publisherRepository;

    // --------------------- //
    // Management Operations //
    // --------------------- //

    /**
     * Create Publisher
     * @param title
     * @param description
     * @return PublisherResponse
     */ //TODO: User CreateRequest dto
    public PublisherResponse createPublisher(String title, String description) {
        Publisher publisher = new Publisher();
        publisher.setTitle(title);
        publisher.setDescription(description);
        Publisher savedPublisher = publisherRepository.save(publisher);
        return toResponse(savedPublisher);
    }

    /**
     * Soft Delete Publisher by id
     * @param id
     */
    public void deleteById(Long id) {
        Publisher publisher = publisherRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + id));
        publisher.softDelete();
        publisherRepository.save(publisher);
    }

    /**
     * Restore Publisher by id
     * @param id
     * @return PublisherResponse
     */
    public PublisherResponse restoreById(Long id){
        Publisher publisher = publisherRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + id));
        publisher.restore();
        return toResponse(publisherRepository.save(publisher));
    }

    /**
     * Update Publisher
     * @param id
     * @param request
     * @param partial
     * @return PublisherResponse
     */
    public PublisherResponse update(Long id, UpdatePublisherRequest request, boolean partial){

        Publisher publisher = publisherRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Publisher not found"));

        if(!partial || request.getTitle() != null){
            publisher.setTitle(request.getTitle());
        }

        if(!partial || request.getDescription() != null){
            publisher.setDescription(request.getDescription());
        }

        return toResponse(publisherRepository.save(publisher));
    }

    // ----------------- //
    // Search Operations //
    // ----------------- //

    /**
     * Fetch all Publishers
     * @return List<PublisherResponse>
     */
    public List<PublisherResponse> findAll() {
        List<Publisher> publishers = publisherRepository.findAll();
        return publishers.stream().map(this::toResponse).toList();
    }

    /**
     * Find Publisher by id
     * @param id
     * @return PublisherResponse
     */
    public PublisherResponse findById(Long id) {
        Publisher publisher = publisherRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + id));
        return toResponse(publisher);
    }

    /**
     * Find Publisher by title
     * @param title
     * @return PublisherResponse
     */
    public PublisherResponse findByTitle(String title) {
        Publisher publisher = publisherRepository.findByTitle(title)
            .orElseThrow(() -> new RuntimeException("Publisher not found with title: " + title));
        return toResponse(publisher);
    }

    // -------------- //
    // Helper Methods //
    // -------------- //

    /**
     * Convert Publisher instance to PublisherResponse
     * @param publisher
     * @return PublisherResponse
     */ // TODO: Create a Mapper
    private PublisherResponse toResponse(Publisher publisher) {
        PublisherResponse response = new PublisherResponse();
        response.setId(publisher.getId());
        response.setTitle(publisher.getTitle());
        response.setDescription(publisher.getDescription());
        return response;
    }

}
