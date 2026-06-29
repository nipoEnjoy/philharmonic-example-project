package com.npopov.philharmonic.events.concert.controller;

import com.npopov.philharmonic.events.concert.domain.Concert;
import com.npopov.philharmonic.events.concert.dto.ConcertCreateRequest;
import com.npopov.philharmonic.events.concert.dto.ConcertMapper;
import com.npopov.philharmonic.events.concert.dto.ConcertResponse;
import com.npopov.philharmonic.events.concert.dto.ConcertUpdateRequest;
import com.npopov.philharmonic.events.concert.service.ConcertService;
import com.npopov.philharmonic.shared.annotation.RequirePermission;
import com.npopov.philharmonic.shared.controller.GenericRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events/concert")
public class ConcertController {

    private final GenericRestController<Concert, Long,
            ConcertResponse, ConcertCreateRequest, ConcertUpdateRequest> genericController;
    private final ConcertService concertService;
    private final ConcertMapper concertMapper;

    public ConcertController(ConcertService concertService, ConcertMapper concertMapper) {
        this.concertService = concertService;
        this.concertMapper = concertMapper;
        this.genericController = new GenericRestController<>(
                concertService,
                concertMapper::toResponse,
                concertMapper::toConcertFromCreate,
                (id, req) -> {
                    Concert concert = concertMapper.toConcertFromUpdate(req);
                    concert.setId(id);
                    return concert;
                }
        );
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<ConcertResponse> getAll() {
        return genericController.getAll();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ConcertResponse> getById(@PathVariable Long id) {
        return genericController.getById(id);
    }

    @RequirePermission("CREATE")
    @PostMapping
    public ResponseEntity<ConcertResponse> create(@RequestBody ConcertCreateRequest request) {
        return genericController.create(request);
    }

    @RequirePermission("UPDATE")
    @PutMapping("/{id}")
    public ResponseEntity<ConcertResponse> update(
            @PathVariable Long id,
            @RequestBody ConcertUpdateRequest request) {
        return genericController.update(id, request);
    }

    @RequirePermission("DELETE")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return genericController.delete(id);
    }
}