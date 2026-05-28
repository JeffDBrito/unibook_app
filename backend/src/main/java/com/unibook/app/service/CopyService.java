package com.unibook.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unibook.app.dto.request.copy.CreateCopyRequest;
import com.unibook.app.dto.request.copy.PartialUpdateCopyRequest;
import com.unibook.app.dto.request.copy.UpdateCopyRequest;
import com.unibook.app.dto.response.CopyResponse;
import com.unibook.app.enums.CopyStatus;
import com.unibook.app.exceptions.BadRequestException;
import com.unibook.app.exceptions.ResourceNotFoundException;
import com.unibook.app.mapper.CopyMapper;
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

        String code = request.getCode();
        CopyStatus status = request.getStatus();
        Long bookId = request.getBookId();

        if(copyRepository.existsByCode(code)){
            throw new BadRequestException("code already exists");
        }

        Copy copy = new Copy();
        copy.setCode(code);
        copy.setStatus(status);

        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        copy.setBook(book);

        return CopyMapper.toResponse(copyRepository.save(copy));
    }

    /**
     * Soft Delete a copy
     * @param id
     */
    public void deleteById(Long id){
        Copy copy = copyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Copy not found"));

        copy.softDelete();
        copyRepository.save(copy);
    }

    /**
     * Restore a copy
     * @param id
     */
    public CopyResponse restoreById(Long id){
        Copy copy = copyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Copy not found"));

        copy.restore();
        return CopyMapper.toResponse(copyRepository.save(copy));
    }


    /**
     * Update Copy
     * @param id
     * @param request
     * @param partial
     * @return CopyResponse
     */
    public CopyResponse update(Long id, PartialUpdateCopyRequest request, boolean partial){
        
        String code = request.getCode();
        CopyStatus status = request.getStatus();

        if(copyRepository.existsByCode(code)){
            throw new BadRequestException("code already exists");
        }

        Copy copy = copyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Copy not found"));

        if(!partial || code != null){
            copy.setCode(code);
        }

        if(!partial || status != null){
            copy.setStatus(status);
        }
        
        if(!partial || request.getInventoryId() != null){
            Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

            copy.setInventory(inventory);
        }
        
        return CopyMapper.toResponse(copyRepository.save(copy));

    }

    public CopyResponse update(Long id, UpdateCopyRequest request){
        return update(id, CopyMapper.toPartialRequest(request), false);
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
            .orElseThrow(() -> new ResourceNotFoundException("Copy not found"));
        
        return CopyMapper.toResponse(copy);
    }

    /**
     * Fetch all Copies
     * @return List<CopyResponse>
     */
    public List<CopyResponse> findAll(){
        List<Copy> copies = copyRepository.findAll();
        return copies.stream().map(CopyMapper::toResponse).toList();
    }

}
