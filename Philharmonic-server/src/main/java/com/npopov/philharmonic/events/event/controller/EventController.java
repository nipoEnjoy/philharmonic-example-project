package com.npopov.philharmonic.events.event.controller;

import com.npopov.philharmonic.events.event.dto.*;
import com.npopov.philharmonic.events.event.domain.Event;
import com.npopov.philharmonic.events.event.service.EventService;
import com.npopov.philharmonic.shared.annotation.RequirePermission;
import com.npopov.philharmonic.shared.controller.GenericRestController;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final GenericRestController<Event, Long,
                EventResponse, EventCreateRequest, EventUpdateRequest> genericController;
    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.genericController = new GenericRestController<>(
                eventService,
                eventMapper::toResponse,
                eventMapper::toEventFromCreate,
                (id, req) -> {
                    Event event = eventMapper.toEventFromUpdate(req);
                    event.setId(id);
                    return event;
                }
        );
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAll(
            @RequestParam(required = false) LocalDateTime start, @RequestParam(required = false) LocalDateTime end,
            @RequestParam(required = false) Long organizerId, @RequestParam(required = false) Long venueId) {
        List<Event> events;
        if (start != null && end != null) {
            events = eventService.findByPeriod(start, end);
        } else if (organizerId != null) {
            events = eventService.findByOrganizer(organizerId);
        } else if (venueId != null) {
            events = eventService.findByVenue(venueId);
        } else {
            events = eventService.getAll();
        }
        return ResponseEntity.ok(events.stream().map(eventService::toResponse).collect(Collectors.toList()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getById(@PathVariable Long id) {
        return genericController.getById(id);
    }

    @RequirePermission("CREATE")
    @PostMapping
    public ResponseEntity<EventResponse> create(@RequestBody EventCreateRequest request) {
        Event event = eventService.createFromRequest(request);
        return ResponseEntity.ok(eventService.toResponse(event));
    }

    @RequirePermission("UPDATE")
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventUpdateRequest request) {
        return genericController.update(id, request);
    }

    @RequirePermission("DELETE")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return genericController.delete(id);
    }
}