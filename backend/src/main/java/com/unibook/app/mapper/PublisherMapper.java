package com.unibook.app.mapper;

import com.unibook.app.dto.request.publisher.PartialUpdatePublisherRequest;
import com.unibook.app.dto.request.publisher.UpdatePublisherRequest;
import com.unibook.app.dto.response.PublisherResponse;
import com.unibook.app.model.Publisher;

public class PublisherMapper {
    
    /**
     * Convert Publisher instance to PublisherResponse
     * @param publisher
     * @return PublisherResponse
     */ 
    public static PublisherResponse toResponse(Publisher publisher) {
        PublisherResponse response = new PublisherResponse();
        response.setId(publisher.getId());
        response.setTitle(publisher.getTitle());
        response.setDescription(publisher.getDescription());
        return response;
    }

    public static PartialUpdatePublisherRequest toPartialUpdate(UpdatePublisherRequest request){
        PartialUpdatePublisherRequest partial = new PartialUpdatePublisherRequest();
        partial.setTitle(request.getTitle());
        partial.setDescription(request.getDescription());
        return partial;
    }

}
