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

    public PublisherResponse createPublisher(String title) {
        var publisher = new Publisher();
        publisher.setTitle(title);
        var savedPublisher = publisherRepository.save(publisher);
        return toResponse(savedPublisher);
    }

    public List<PublisherResponse> findAll() {
        var publishers = publisherRepository.findAll();
        return publishers.stream().map(this::toResponse).toList();
    }

    public PublisherResponse findById(Long id) {
        var publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + id));
        return toResponse(publisher);
    }

    public PublisherResponse findByTitle(String title) {
        var publisher = publisherRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Publisher not found with title: " + title));
        return toResponse(publisher);
    }

    private PublisherResponse toResponse(Publisher publisher) {
        PublisherResponse response = new PublisherResponse();
        response.setId(publisher.getId());
        response.setTitle(publisher.getTitle());
        return response;
    }

}
