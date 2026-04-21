package com.unibook.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.request.CreatePublisherRequest;
import com.unibook.app.dto.response.PublisherResponse;
import com.unibook.app.service.PublisherService;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    // List publishers
    @GetMapping
    public List<PublisherResponse> getAllPublishers() {
        return publisherService.findAll();
    }

    // Create publisher
    @PostMapping
    public PublisherResponse createPublisher(@RequestBody CreatePublisherRequest request) {
        return publisherService.createPublisher(
            request.getTitle(),
            request.getDescription()
        );
    }

    // Get publisher by id
    @GetMapping("/{id}")
    public PublisherResponse getPublisherById(@PathVariable Long id) {
        return publisherService.findById(id);
    }

    // Get publisher by title
    @GetMapping("/title/{title}")
    public PublisherResponse getPublisherByTitle(@PathVariable String title) {
        return publisherService.findByTitle(title);
    }
}
