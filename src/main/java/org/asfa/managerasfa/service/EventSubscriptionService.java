package org.asfa.managerasfa.service;

import java.util.Optional;
import org.asfa.managerasfa.domain.EventSubscription;
import org.asfa.managerasfa.repository.EventSubscriptionRepository;
import org.asfa.managerasfa.service.dto.EventSubscriptionDTO;
import org.asfa.managerasfa.service.mapper.EventSubscriptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.asfa.managerasfa.domain.EventSubscription}.
 */
@Service
@Transactional
public class EventSubscriptionService {

    private static final Logger LOG = LoggerFactory.getLogger(EventSubscriptionService.class);

    private final EventSubscriptionRepository eventSubscriptionRepository;

    private final EventSubscriptionMapper eventSubscriptionMapper;

    public EventSubscriptionService(
        EventSubscriptionRepository eventSubscriptionRepository,
        EventSubscriptionMapper eventSubscriptionMapper
    ) {
        this.eventSubscriptionRepository = eventSubscriptionRepository;
        this.eventSubscriptionMapper = eventSubscriptionMapper;
    }

    /**
     * Save a eventSubscription.
     *
     * @param eventSubscriptionDTO the entity to save.
     * @return the persisted entity.
     */
    public EventSubscriptionDTO save(EventSubscriptionDTO eventSubscriptionDTO) {
        LOG.debug("Request to save EventSubscription : {}", eventSubscriptionDTO);
        EventSubscription eventSubscription = eventSubscriptionMapper.toEntity(eventSubscriptionDTO);
        eventSubscription = eventSubscriptionRepository.save(eventSubscription);
        return eventSubscriptionMapper.toDto(eventSubscription);
    }

    /**
     * Update a eventSubscription.
     *
     * @param eventSubscriptionDTO the entity to save.
     * @return the persisted entity.
     */
    public EventSubscriptionDTO update(EventSubscriptionDTO eventSubscriptionDTO) {
        LOG.debug("Request to update EventSubscription : {}", eventSubscriptionDTO);
        EventSubscription eventSubscription = eventSubscriptionMapper.toEntity(eventSubscriptionDTO);
        eventSubscription = eventSubscriptionRepository.save(eventSubscription);
        return eventSubscriptionMapper.toDto(eventSubscription);
    }

    /**
     * Partially update a eventSubscription.
     *
     * @param eventSubscriptionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EventSubscriptionDTO> partialUpdate(EventSubscriptionDTO eventSubscriptionDTO) {
        LOG.debug("Request to partially update EventSubscription : {}", eventSubscriptionDTO);

        return eventSubscriptionRepository
            .findById(eventSubscriptionDTO.getId())
            .map(existingEventSubscription -> {
                eventSubscriptionMapper.partialUpdate(existingEventSubscription, eventSubscriptionDTO);

                return existingEventSubscription;
            })
            .map(eventSubscriptionRepository::save)
            .map(eventSubscriptionMapper::toDto);
    }

    /**
     * Get all the eventSubscriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EventSubscriptionDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all EventSubscriptions");
        return eventSubscriptionRepository.findAll(pageable).map(eventSubscriptionMapper::toDto);
    }

    /**
     * Get one eventSubscription by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EventSubscriptionDTO> findOne(Long id) {
        LOG.debug("Request to get EventSubscription : {}", id);
        return eventSubscriptionRepository.findById(id).map(eventSubscriptionMapper::toDto);
    }

    /**
     * Delete the eventSubscription by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete EventSubscription : {}", id);
        eventSubscriptionRepository.deleteById(id);
    }
}
