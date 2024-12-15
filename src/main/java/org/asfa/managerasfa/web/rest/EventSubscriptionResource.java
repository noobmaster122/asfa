package org.asfa.managerasfa.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.asfa.managerasfa.repository.EventSubscriptionRepository;
import org.asfa.managerasfa.service.EventSubscriptionService;
import org.asfa.managerasfa.service.dto.EventSubscriptionDTO;
import org.asfa.managerasfa.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.asfa.managerasfa.domain.EventSubscription}.
 */
@RestController
@RequestMapping("/api/event-subscriptions")
public class EventSubscriptionResource {

    private static final Logger LOG = LoggerFactory.getLogger(EventSubscriptionResource.class);

    private static final String ENTITY_NAME = "eventSubscription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventSubscriptionService eventSubscriptionService;

    private final EventSubscriptionRepository eventSubscriptionRepository;

    public EventSubscriptionResource(
        EventSubscriptionService eventSubscriptionService,
        EventSubscriptionRepository eventSubscriptionRepository
    ) {
        this.eventSubscriptionService = eventSubscriptionService;
        this.eventSubscriptionRepository = eventSubscriptionRepository;
    }

    /**
     * {@code POST  /event-subscriptions} : Create a new eventSubscription.
     *
     * @param eventSubscriptionDTO the eventSubscriptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventSubscriptionDTO, or with status {@code 400 (Bad Request)} if the eventSubscription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EventSubscriptionDTO> createEventSubscription(@Valid @RequestBody EventSubscriptionDTO eventSubscriptionDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save EventSubscription : {}", eventSubscriptionDTO);
        if (eventSubscriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventSubscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        eventSubscriptionDTO = eventSubscriptionService.save(eventSubscriptionDTO);
        return ResponseEntity.created(new URI("/api/event-subscriptions/" + eventSubscriptionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, eventSubscriptionDTO.getId().toString()))
            .body(eventSubscriptionDTO);
    }

    /**
     * {@code PUT  /event-subscriptions/:id} : Updates an existing eventSubscription.
     *
     * @param id the id of the eventSubscriptionDTO to save.
     * @param eventSubscriptionDTO the eventSubscriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventSubscriptionDTO,
     * or with status {@code 400 (Bad Request)} if the eventSubscriptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventSubscriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EventSubscriptionDTO> updateEventSubscription(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EventSubscriptionDTO eventSubscriptionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update EventSubscription : {}, {}", id, eventSubscriptionDTO);
        if (eventSubscriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventSubscriptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventSubscriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        eventSubscriptionDTO = eventSubscriptionService.update(eventSubscriptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventSubscriptionDTO.getId().toString()))
            .body(eventSubscriptionDTO);
    }

    /**
     * {@code PATCH  /event-subscriptions/:id} : Partial updates given fields of an existing eventSubscription, field will ignore if it is null
     *
     * @param id the id of the eventSubscriptionDTO to save.
     * @param eventSubscriptionDTO the eventSubscriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventSubscriptionDTO,
     * or with status {@code 400 (Bad Request)} if the eventSubscriptionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eventSubscriptionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventSubscriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventSubscriptionDTO> partialUpdateEventSubscription(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EventSubscriptionDTO eventSubscriptionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update EventSubscription partially : {}, {}", id, eventSubscriptionDTO);
        if (eventSubscriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventSubscriptionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventSubscriptionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventSubscriptionDTO> result = eventSubscriptionService.partialUpdate(eventSubscriptionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventSubscriptionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /event-subscriptions} : get all the eventSubscriptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventSubscriptions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EventSubscriptionDTO>> getAllEventSubscriptions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of EventSubscriptions");
        Page<EventSubscriptionDTO> page = eventSubscriptionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-subscriptions/:id} : get the "id" eventSubscription.
     *
     * @param id the id of the eventSubscriptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventSubscriptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EventSubscriptionDTO> getEventSubscription(@PathVariable("id") Long id) {
        LOG.debug("REST request to get EventSubscription : {}", id);
        Optional<EventSubscriptionDTO> eventSubscriptionDTO = eventSubscriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventSubscriptionDTO);
    }

    /**
     * {@code DELETE  /event-subscriptions/:id} : delete the "id" eventSubscription.
     *
     * @param id the id of the eventSubscriptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventSubscription(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete EventSubscription : {}", id);
        eventSubscriptionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
