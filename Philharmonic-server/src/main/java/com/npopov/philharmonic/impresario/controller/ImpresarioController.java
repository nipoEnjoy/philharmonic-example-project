package com.npopov.philharmonic.impresario.controller;

import com.npopov.philharmonic.impresario.domain.Impresario;
import com.npopov.philharmonic.impresario.dto.ImpresarioCreateRequest;
import com.npopov.philharmonic.impresario.dto.ImpresarioUpdateRequest;
import com.npopov.philharmonic.impresario.dto.ImpresarioMapper;
import com.npopov.philharmonic.impresario.dto.ImpresarioResponse;
import com.npopov.philharmonic.impresario.service.ImpresarioService;
import com.npopov.philharmonic.shared.annotation.RequirePermission;
import com.npopov.philharmonic.shared.controller.GenericRestController;
import com.npopov.philharmonic.shared.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/impresarios")
public class ImpresarioController {

    private final GenericRestController<Impresario, Long,
            ImpresarioResponse, ImpresarioCreateRequest, ImpresarioUpdateRequest> genericController;
    private final ImpresarioService impresarioService;
    private final ImpresarioMapper impresarioMapper;

    public ImpresarioController(ImpresarioService impresarioService, ImpresarioMapper impresarioMapper) {
        this.impresarioService = impresarioService;
        this.impresarioMapper = impresarioMapper;
        this.genericController = new GenericRestController<>(
                impresarioService,
                impresarioMapper::toResponse,
                impresarioMapper::toImpresarioFromCreate,
                (id, req) -> {
                    Impresario impresario = impresarioMapper.toImpresarioFromUpdate(req);
                    impresario.setId(id);
                    return impresario;
                }
        );
    }

    @GetMapping
    public ResponseEntity<List<ImpresarioResponse>> getAll(@RequestParam(required = false) Long artistId, @RequestParam(required = false) String genre) {
        List<ImpresarioResponse> responseList = new ArrayList<>();
        if (artistId != null) {
            responseList.addAll(
                    impresarioService.findByArtist(artistId)
                            .stream()
                            .map(impresarioMapper::toResponse)
                            .toList()
            );
        }
        if (genre != null) {
            responseList.addAll(
                    impresarioService.findByGenre(genre)
                    .stream()
                    .map(impresarioMapper::toResponse)
                    .toList()
            );
        }
        if (artistId == null && genre == null) {
            responseList.addAll(
                    impresarioService.getAll()
                            .stream()
                            .map(impresarioMapper::toResponse)
                            .toList()
            );
        }
        return ResponseEntity.ok(responseList);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<ImpresarioResponse> getById(@PathVariable Long id) {
        return genericController.getById(id);
    }

    @RequirePermission("CREATE")
    @PostMapping
    public ResponseEntity<ImpresarioResponse> create(@Valid @RequestBody ImpresarioCreateRequest request) {
        return genericController.create(request);
    }

    @RequirePermission("UPDATE")
    @PutMapping("/{id}")
    public ResponseEntity<ImpresarioResponse> update(@PathVariable Long id, @RequestBody ImpresarioUpdateRequest impresario) {
        return genericController.update(id, impresario);
    }

    @RequirePermission("DELETE")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImpresario(@PathVariable Long id) {
        if (impresarioService.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Impresario not found");
        }
        impresarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}