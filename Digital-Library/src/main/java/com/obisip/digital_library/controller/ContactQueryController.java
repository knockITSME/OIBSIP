package com.obisip.digital_library.controller;

import com.obisip.digital_library.entity.ContactQuery;
import com.obisip.digital_library.service.ContactQueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
public class ContactQueryController {

    private final ContactQueryService contactQueryService;

    public ContactQueryController(ContactQueryService contactQueryService) {
        this.contactQueryService = contactQueryService;
    }

    @PostMapping
    public ContactQuery submitQuery(@RequestBody ContactQuery query) {
        return contactQueryService.submitQuery(query);
    }

    @GetMapping
    public List<ContactQuery> getAllQueries() {
        return contactQueryService.getAllQueries();
    }

    @GetMapping("/{id}")
    public ContactQuery getQueryById(@PathVariable Long id) {
        return contactQueryService.getQueryById(id);
    }
}