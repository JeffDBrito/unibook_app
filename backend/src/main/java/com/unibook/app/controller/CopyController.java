package com.unibook.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.unibook.app.dto.request.copy.CreateCopyRequest;
import com.unibook.app.dto.request.copy.UpdateCopyRequest;
import com.unibook.app.dto.response.CopyResponse;
import com.unibook.app.service.CopyService;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/copy")
public class CopyController {
    private final CopyService copyService;

    public CopyController(CopyService copyService){
        this.copyService = copyService;
    }

    // List copies
    @GetMapping
    @Operation(summary = "List copies", description = "Retrieves a list of all copies and returns their details.", tags = {"Copy Endpoints"})
    public List<CopyResponse> getAll() {
        return copyService.findAll();
    }

    // Create copy
    @PostMapping
    @Operation(summary = "Create a new copy", description = "Creates a new copy with the provided details and returns the created copy.", tags = {"Copy Endpoints"})
    public CopyResponse create(@RequestBody CreateCopyRequest request) {
        System.out.println(request);
        return copyService.createCopy(request);
    }

    // Partial update
    @PatchMapping("/{id}")
    @Operation(summary = "Partial update copy", description = "Partially updates an existing copy with the provided details and returns the updated copy.", tags = {"Copy Endpoints"})
    public CopyResponse partialUpdate( @PathVariable Long id, @RequestBody UpdateCopyRequest request ) {
        return copyService.update(id, request, true);
    }

    // Full update
    @PutMapping("/{id}")
    @Operation(summary = "Update copy", description = "Updates an existing copy with the provided details and returns the updated copy.", tags = {"Copy Endpoints"})
    public CopyResponse fullUpdate( @PathVariable Long id, @RequestBody UpdateCopyRequest request) {
        return copyService.update(id, request, false);
    }

    // Get copy by id
    @GetMapping("/{id}")
    @Operation(summary = "Get copy by id", description = "Retrieves a copy by their id and returns the copy details.", tags = {"Copy Endpoints"})
    public CopyResponse getById(@PathVariable Long id) {
        return copyService.findById(id);
    }

    // Delete copy by id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete copy by id", description = "Deletes a copy by their id and returns no content.", tags = {"Copy Endpoints"})
    public void deleteById(@PathVariable Long id) {
        copyService.deleteById(id);
    }

    // Restore copy by id
    @PostMapping("/{id}/restore")
    @Operation(summary = "Restore copy by id", description = "Restores a previously deleted copy by their id and returns the restored copy details.", tags = {"Copy Endpoints"})
    public CopyResponse restoreById(@PathVariable Long id) {
        return copyService.restoreById(id);
    }
}
