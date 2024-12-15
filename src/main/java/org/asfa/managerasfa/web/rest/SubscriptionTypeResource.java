package org.asfa.managerasfa.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.asfa.managerasfa.repository.SubscriptionTypeRepository;
import org.asfa.managerasfa.service.SubscriptionTypeService;
import org.asfa.managerasfa.service.dto.SubscriptionTypeDTO;
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
 * REST controller for managing {@link org.asfa.managerasfa.domain.SubscriptionType}.
 */
@RestController
@RequestMapping("/api/subscription-types")
public class SubscriptionTypeResource {

    private static final Logger LOG = LoggerFactory.getLogger(SubscriptionTypeResource.class);

    private static final String ENTITY_NAME = "subscriptionType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubscriptionTypeService subscriptionTypeService;

    private final SubscriptionTypeRepository subscriptionTypeRepository;

    public SubscriptionTypeResource(
        SubscriptionTypeService subscriptionTypeService,
        SubscriptionTypeRepository subscriptionTypeRepository
    ) {
        this.subscriptionTypeService = subscriptionTypeService;
        this.subscriptionTypeRepository = subscriptionTypeRepository;
    }

    /**
     * {@code POST  /subscription-types} : Create a new subscriptionType.
     *
     * @param subscriptionTypeDTO the subscriptionTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subscriptionTypeDTO, or with status {@code 400 (Bad Request)} if the subscriptionType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SubscriptionTypeDTO> createSubscriptionType(@Valid @RequestBody SubscriptionTypeDTO subscriptionTypeDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SubscriptionType : {}", subscriptionTypeDTO);
        if (subscriptionTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new subscriptionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        subscriptionTypeDTO = subscriptionTypeService.save(subscriptionTypeDTO);
        return ResponseEntity.created(new URI("/api/subscription-types/" + subscriptionTypeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, subscriptionTypeDTO.getId().toString()))
            .body(subscriptionTypeDTO);
    }

    /**
     * {@code PUT  /subscription-types/:id} : Updates an existing subscriptionType.
     *
     * @param id the id of the subscriptionTypeDTO to save.
     * @param subscriptionTypeDTO the subscriptionTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subscriptionTypeDTO,
     * or with status {@code 400 (Bad Request)} if the subscriptionTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subscriptionTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionTypeDTO> updateSubscriptionType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SubscriptionTypeDTO subscriptionTypeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SubscriptionType : {}, {}", id, subscriptionTypeDTO);
        if (subscriptionTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subscriptionTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subscriptionTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        subscriptionTypeDTO = subscriptionTypeService.update(subscriptionTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subscriptionTypeDTO.getId().toString()))
            .body(subscriptionTypeDTO);
    }

    /**
     * {@code PATCH  /subscription-types/:id} : Partial updates given fields of an existing subscriptionType, field will ignore if it is null
     *
     * @param id the id of the subscriptionTypeDTO to save.
     * @param subscriptionTypeDTO the subscriptionTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subscriptionTypeDTO,
     * or with status {@code 400 (Bad Request)} if the subscriptionTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subscriptionTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subscriptionTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubscriptionTypeDTO> partialUpdateSubscriptionType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SubscriptionTypeDTO subscriptionTypeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SubscriptionType partially : {}, {}", id, subscriptionTypeDTO);
        if (subscriptionTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subscriptionTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subscriptionTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubscriptionTypeDTO> result = subscriptionTypeService.partialUpdate(subscriptionTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, subscriptionTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /subscription-types} : get all the subscriptionTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subscriptionTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SubscriptionTypeDTO>> getAllSubscriptionTypes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of SubscriptionTypes");
        Page<SubscriptionTypeDTO> page = subscriptionTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /subscription-types/:id} : get the "id" subscriptionType.
     *
     * @param id the id of the subscriptionTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subscriptionTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionTypeDTO> getSubscriptionType(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SubscriptionType : {}", id);
        Optional<SubscriptionTypeDTO> subscriptionTypeDTO = subscriptionTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subscriptionTypeDTO);
    }

    /**
     * {@code DELETE  /subscription-types/:id} : delete the "id" subscriptionType.
     *
     * @param id the id of the subscriptionTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscriptionType(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SubscriptionType : {}", id);
        subscriptionTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
