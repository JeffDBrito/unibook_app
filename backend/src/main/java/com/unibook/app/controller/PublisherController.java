package com.unibook.app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.request.publisher.CreatePublisherRequest;
import com.unibook.app.dto.request.publisher.UpdatePublisherRequest;
import com.unibook.app.dto.response.PublisherResponse;
import com.unibook.app.service.PublisherService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    
    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    // List publishers
    @GetMapping
    @Operation(summary = "List publishers", description = "Retrieves a list of all publishers and returns their details.", tags = {"Publisher Endpoints"})
    public List<PublisherResponse> getAllPublishers() {
        return publisherService.findAll();
    }

    // Create publisher
    @PostMapping
    @Operation(summary = "Create a new publisher", description = "Creates a new publisher with the provided details and returns the created publisher.", tags = {"Publisher Endpoints"})
    public PublisherResponse createPublisher(@RequestBody CreatePublisherRequest request) {
        return publisherService.createPublisher(
            request.getTitle(),
            request.getDescription()
        );
    }

    // Get publisher by id
    @GetMapping("/{id}")
    @Operation(summary = "Get publisher by ID", description = "Retrieves a publisher by their unique ID and returns the publisher details.", tags = {"Publisher Endpoints"})
    public PublisherResponse getPublisherById(@PathVariable Long id) {
        return publisherService.findById(id);
    }

    // Delete by id
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete publisher by ID", description = "Deletes a publisher by their unique ID and returns a confirmation message.", tags = {"Publisher Endpoints"})
    public void deletePublisher(@PathVariable Long id) {
        publisherService.deleteById(id);        
    }

    // Get publisher by title
    @GetMapping("/title/{title}")
    @Operation(summary = "Get publisher by title", description = "Retrieves a publisher by their title and returns the publisher details.", tags = {"Publisher Endpoints"})
    public PublisherResponse getPublisherByTitle(@PathVariable String title) {
        return publisherService.findByTitle(title);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update Publisher", description = "Partially updates an existing Publisher with the provided details and returns the updated Publisher.", tags = {"Publisher Endpoints"})
    public PublisherResponse partialUpdate(@PathVariable Long id, @RequestBody UpdatePublisherRequest request){
        return publisherService.update(id, request, true);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Publisher", description = "Updates an existing Publisher with the provided details and returns the updated Publisher.", tags = {"Publisher Endpoints"})
    public PublisherResponse fullUpdate(@PathVariable Long id, @RequestBody UpdatePublisherRequest request){
        return publisherService.update(id, request, false);
    }
}
