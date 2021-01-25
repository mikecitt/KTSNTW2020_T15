package com.example.culturecontentapp.api;

import com.example.culturecontentapp.payload.response.SubscriptionResponse;
import com.example.culturecontentapp.service.SubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService service;

    @Autowired
    public SubscriptionController(SubscriptionService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<Void> add(@RequestParam Long id) {
        return this.service.add(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("{id}")
    public ResponseEntity<Boolean> isSubscribed(@PathVariable Long id) {
        return this.service.isSubscribed(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        return this.service.delete(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<Page<SubscriptionResponse>> get(Pageable pageable) {
        return this.service.get(pageable);
    }
}
