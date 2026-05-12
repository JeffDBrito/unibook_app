package com.unibook.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.response.PublisherResponse;
import com.unibook.app.model.Publisher;
import com.unibook.app.repository.PublisherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherService {
    
    private final PublisherRepository publisherRepository;

    public PublisherResponse createPublisher(String title, String description) {
        Publisher publisher = new Publisher();
        publisher.setTitle(title);
        publisher.setDescription(description);
        Publisher savedPublisher = publisherRepository.save(publisher);
        return toResponse(savedPublisher);
    }

    public List<PublisherResponse> findAll() {
        List<Publisher> publishers = publisherRepository.findAll();
        return publishers.stream().map(this::toResponse).toList();
    }

    public PublisherResponse findById(Long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + id));
        return toResponse(publisher);
    }

    public PublisherResponse findByTitle(String title) {
        Publisher publisher = publisherRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Publisher not found with title: " + title));
        return toResponse(publisher);
    }

    public void deleteById(Long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + id));
        
        publisherRepository.delete(publisher);
    }

    private PublisherResponse toResponse(Publisher publisher) {
        PublisherResponse response = new PublisherResponse();
        response.setId(publisher.getId());
        response.setTitle(publisher.getTitle());
        response.setDescription(publisher.getDescription());
        return response;
    }

}
