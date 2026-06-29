package com.npopov.philharmonic.venues.culturalcentre.controller;

import com.npopov.philharmonic.shared.annotation.RequirePermission;
import com.npopov.philharmonic.shared.controller.GenericRestController;
import com.npopov.philharmonic.venues.culturalcentre.domain.CulturalCentre;
import com.npopov.philharmonic.venues.culturalcentre.dto.CulturalCentreCreateRequest;
import com.npopov.philharmonic.venues.culturalcentre.dto.CulturalCentreMapper;
import com.npopov.philharmonic.venues.culturalcentre.dto.CulturalCentreResponse;
import com.npopov.philharmonic.venues.culturalcentre.dto.CulturalCentreUpdateRequest;
import com.npopov.philharmonic.venues.culturalcentre.service.CulturalCentreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues/cultural-centre")
public class CulturalCentreController {

    private final GenericRestController<CulturalCentre, Long,
            CulturalCentreResponse, CulturalCentreCreateRequest, CulturalCentreUpdateRequest> genericController;
    private final CulturalCentreService culturalCentreService;
    private CulturalCentreMapper culturalCentreMapper;

    public CulturalCentreController(CulturalCentreService culturalCentreService, CulturalCentreMapper culturalCentreMapper) {
        this.culturalCentreService = culturalCentreService;
        this.culturalCentreMapper = culturalCentreMapper;
        this.genericController = new GenericRestController<>(
                culturalCentreService,
                culturalCentreMapper::toResponse,
                culturalCentreMapper::toCulturalCentreFromCreate,
                (id, req) -> {
                    CulturalCentre culturalCentre = culturalCentreMapper.toCulturalCentreFromUpdate(req);
                    culturalCentre.setId(id);
                    return culturalCentre;
                }
        );
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<CulturalCentreResponse> getAll() {
        return genericController.getAll();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<CulturalCentreResponse> getById(@Valid @PathVariable Long id) {
        return genericController.getById(id);
    }


    @RequirePermission("DELETE")
    @PostMapping
    public ResponseEntity<CulturalCentreResponse> create(@Valid @RequestBody CulturalCentreCreateRequest request) {
        return genericController.create(request);
    }

    @RequirePermission("DELETE")
    @PutMapping("/{id}")
    public  ResponseEntity<CulturalCentreResponse> update(
            @Valid @PathVariable Long id, @Valid @RequestBody CulturalCentreUpdateRequest request) {
        return genericController.update(id, request);
    }

    @RequirePermission("DELETE")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return genericController.delete(id);
    }
}