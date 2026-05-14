package com.unibook.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.copy.CreateCopyRequest;
import com.unibook.app.dto.request.copy.UpdateCopyRequest;
import com.unibook.app.dto.response.BookResponse;
import com.unibook.app.dto.response.CopyResponse;
import com.unibook.app.model.Book;
import com.unibook.app.model.Copy;
import com.unibook.app.model.Inventory;
import com.unibook.app.repository.BookRepository;
import com.unibook.app.repository.CopyRepository;
import com.unibook.app.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CopyService {
 
    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final InventoryRepository inventoryRepository;

    // --------------------- //
    // Management Operations //
    // --------------------- //

    /**
     * Create a Copy
     * @param request
     * @return CopyResponse
     */
    public CopyResponse createCopy(CreateCopyRequest request){

        System.out.println("\nCODE: "+request.getCode());
        System.out.println("\nSTATUS: "+request.getStatus());
        System.out.println("\nBOOK ID: "+request.getBookId()+"\n");

        Copy copy = new Copy();
        copy.setCode(request.getCode());
        copy.setStatus(request.getStatus());

        Book book = bookRepository.findById(request.getBookId())
            .orElseThrow(() -> new RuntimeException("Book not found"));

        copy.setBook(book);

        return toResponse(copyRepository.save(copy));
    }

    /**
     * Soft Delete a copy
     * @param id
     */
    public void deleteById(Long id){
        Copy copy = copyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Copy not found"));

        copy.softDelete();
        copyRepository.save(copy);
    }

    /**
     * Restore a copy
     * @param id
     */
    public CopyResponse restoreById(Long id){
        Copy copy = copyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Copy not found"));

        copy.restore();
        return toResponse(copyRepository.save(copy));
    }


    /**
     * Update Copy
     * @param id
     * @param request
     * @param partial
     * @return CopyResponse
     */
    public CopyResponse update(Long id, UpdateCopyRequest request, boolean partial){

        Copy copy = copyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Copy not found"));

        if(!partial || request.getCode() != null){
            copy.setCode(request.getCode());
        }

        if(!partial || request.getStatus() != null){
            copy.setStatus(request.getStatus());
        }
        
        if(!partial || request.getInventoryId() != null){
            Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));

            copy.setInventory(inventory);
        }
        
        return toResponse(copyRepository.save(copy));

    }

    // ----------------- //
    // Search Operations //
    // ----------------- //

    /**
     * Fetch Copy by id
     * @param id
     * @return CopyResponse
     */
    public CopyResponse findById(Long id){
        Copy copy = copyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Copy not found"));
        
        return toResponse(copy);
    }

    /**
     * Fetch all Copies
     * @return List<CopyResponse>
     */
    public List<CopyResponse> findAll(){
        List<Copy> copies = copyRepository.findAll();
        return copies.stream().map(this::toResponse).toList();
    }

    // -------------- //
    // Helper Methods //
    // -------------- //

    /**
     * Convert a Copy instance to CopyResponse
     * @param copy
     * @return
     */
    // TODO: Create a Mapper
    public CopyResponse toResponse(Copy copy) {

        CopyResponse response = new CopyResponse();

        response.setId(copy.getId());
        response.setCode(copy.getCode());
        response.setStatus(copy.getStatus().name());

        if (copy.getInventory() != null) {

            Inventory inventory = copy.getInventory();

            response.setInventoryAddress(
                "section_" + inventory.getSector() +
                ", shelf_" + inventory.getShelf() +
                ", row_" + inventory.getRow() +
                ", slot_" + inventory.getSlot()
            );
        }

        if (copy.getBook() != null) {
            BookResponse bookResponse = bookService.findById(copy.getBook().getId());
            
            response.setBookTitle(bookResponse.getTitle());
            response.setAuthors(bookResponse.getAuthors());
            response.setBookId(bookResponse.getId());


        }

        return response;
    }

}
