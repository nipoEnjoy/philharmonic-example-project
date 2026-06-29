package com.npopov.philharmonic.venues.theatre.controller;

import com.npopov.philharmonic.shared.annotation.RequirePermission;
import com.npopov.philharmonic.shared.controller.GenericRestController;
import com.npopov.philharmonic.venues.theatre.domain.Theatre;
import com.npopov.philharmonic.venues.theatre.dto.TheatreCreateRequest;
import com.npopov.philharmonic.venues.theatre.dto.TheatreMapper;
import com.npopov.philharmonic.venues.theatre.dto.TheatreResponse;
import com.npopov.philharmonic.venues.theatre.dto.TheatreUpdateRequest;
import com.npopov.philharmonic.venues.theatre.service.TheatreService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues/theatre")
public class TheatreController {

    private final GenericRestController<Theatre, Long,
            TheatreResponse, TheatreCreateRequest, TheatreUpdateRequest> genericController;
    private final TheatreService theatreService;
    private final TheatreMapper theatreMapper;

    public TheatreController(TheatreService theatreService, TheatreMapper theatreMapper) {
        this.theatreService = theatreService;
        this.theatreMapper = theatreMapper;
        this.genericController = new GenericRestController<>(
                theatreService,
                theatreMapper::toResponse,
                theatreMapper::toTheatreFromCreate,
                (id, req) -> {
                    Theatre theatre = theatreMapper.toTheatreFromUpdate(req);
                    theatre.setId(id);
                    return theatre;
                }
        );
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<TheatreResponse> getAll() {
        return genericController.getAll();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<TheatreResponse> getById(@Valid @PathVariable Long id) {
        return genericController.getById(id);
    }

    @RequirePermission("CREATE")
    @PostMapping
    public ResponseEntity<TheatreResponse> create(@Valid @RequestBody TheatreCreateRequest request) {
        System.out.println("Received JSON: " + request.toString());
        return genericController.create(request);
    }

    @RequirePermission("UPDATE")
    @PutMapping("/{id}")
    public ResponseEntity<TheatreResponse> update(
            @Valid @PathVariable Long id,
            @Valid @RequestBody TheatreUpdateRequest request) {
        return genericController.update(id, request);
    }

    @RequirePermission("DELETE")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return genericController.delete(id);
    }
}
