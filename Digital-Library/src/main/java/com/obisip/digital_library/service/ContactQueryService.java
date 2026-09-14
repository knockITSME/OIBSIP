package com.obisip.digital_library.service;

import com.obisip.digital_library.entity.ContactQuery;
import com.obisip.digital_library.repository.ContactQueryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactQueryService {

    private final ContactQueryRepository contactQueryRepository;

    public ContactQueryService(ContactQueryRepository contactQueryRepository) {
        this.contactQueryRepository = contactQueryRepository;
    }

    public ContactQuery submitQuery(ContactQuery query) {
        query.setSubmittedAt(LocalDateTime.now());
        return contactQueryRepository.save(query);
    }

    public List<ContactQuery> getAllQueries() {
        return contactQueryRepository.findAll();
    }

    public ContactQuery getQueryById(Long id) {
        return contactQueryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Query not found"));
    }
}