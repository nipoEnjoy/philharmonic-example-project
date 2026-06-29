package com.npopov.philharmonic.events.festival.controller;

import com.npopov.philharmonic.events.concert.dto.ConcertCreateRequest;
import com.npopov.philharmonic.events.concert.dto.ConcertResponse;
import com.npopov.philharmonic.events.concert.dto.ConcertUpdateRequest;
import com.npopov.philharmonic.events.festival.domain.Festival;
import com.npopov.philharmonic.events.festival.dto.FestivalCreateRequest;
import com.npopov.philharmonic.events.festival.dto.FestivalMapper;
import com.npopov.philharmonic.events.festival.dto.FestivalResponse;
import com.npopov.philharmonic.events.festival.dto.FestivalUpdateRequest;
import com.npopov.philharmonic.events.festival.service.FestivalService;
import com.npopov.philharmonic.shared.annotation.RequirePermission;
import com.npopov.philharmonic.shared.controller.GenericRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events/festival")
public class FestivalController {

    private final GenericRestController<Festival, Long,
            FestivalResponse, FestivalCreateRequest, FestivalUpdateRequest> genericController;
    private final FestivalService festivalService;
    private final FestivalMapper festivalMapper;

    public FestivalController(FestivalService festivalService, FestivalMapper festivalMapper) {
        this.festivalService = festivalService;
        this.festivalMapper = festivalMapper;
        this.genericController = new GenericRestController<>(
                festivalService,
                festivalMapper::toResponse,
                festivalMapper::toFestivalFromCreate,
                (id, req) -> {
                    Festival festival = festivalMapper.toFestivalFromUpdate(req);
                    festival.setId(id);
                    return festival;
                }
        );
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<FestivalResponse> getAll() {
        return genericController.getAll();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<FestivalResponse> getById(@PathVariable Long id) {
        return genericController.getById(id);
    }

    @RequirePermission("CREATE")
    @PostMapping
    public ResponseEntity<FestivalResponse> create(@RequestBody FestivalCreateRequest request) {
        return genericController.create(request);
    }

    @RequirePermission("UPDATE")
    @PutMapping("/{id}")
    public ResponseEntity<FestivalResponse> update(
            @PathVariable Long id,
            @RequestBody FestivalUpdateRequest request) {
        return genericController.update(id, request);
    }

    @RequirePermission("DELETE")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return genericController.delete(id);
    }
}
