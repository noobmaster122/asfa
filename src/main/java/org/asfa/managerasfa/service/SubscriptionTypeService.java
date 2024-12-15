package org.asfa.managerasfa.service;

import java.util.Optional;
import org.asfa.managerasfa.domain.SubscriptionType;
import org.asfa.managerasfa.repository.SubscriptionTypeRepository;
import org.asfa.managerasfa.service.dto.SubscriptionTypeDTO;
import org.asfa.managerasfa.service.mapper.SubscriptionTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link org.asfa.managerasfa.domain.SubscriptionType}.
 */
@Service
@Transactional
public class SubscriptionTypeService {

    private static final Logger LOG = LoggerFactory.getLogger(SubscriptionTypeService.class);

    private final SubscriptionTypeRepository subscriptionTypeRepository;

    private final SubscriptionTypeMapper subscriptionTypeMapper;

    public SubscriptionTypeService(SubscriptionTypeRepository subscriptionTypeRepository, SubscriptionTypeMapper subscriptionTypeMapper) {
        this.subscriptionTypeRepository = subscriptionTypeRepository;
        this.subscriptionTypeMapper = subscriptionTypeMapper;
    }

    /**
     * Save a subscriptionType.
     *
     * @param subscriptionTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public SubscriptionTypeDTO save(SubscriptionTypeDTO subscriptionTypeDTO) {
        LOG.debug("Request to save SubscriptionType : {}", subscriptionTypeDTO);
        SubscriptionType subscriptionType = subscriptionTypeMapper.toEntity(subscriptionTypeDTO);
        subscriptionType = subscriptionTypeRepository.save(subscriptionType);
        return subscriptionTypeMapper.toDto(subscriptionType);
    }

    /**
     * Update a subscriptionType.
     *
     * @param subscriptionTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public SubscriptionTypeDTO update(SubscriptionTypeDTO subscriptionTypeDTO) {
        LOG.debug("Request to update SubscriptionType : {}", subscriptionTypeDTO);
        SubscriptionType subscriptionType = subscriptionTypeMapper.toEntity(subscriptionTypeDTO);
        subscriptionType = subscriptionTypeRepository.save(subscriptionType);
        return subscriptionTypeMapper.toDto(subscriptionType);
    }

    /**
     * Partially update a subscriptionType.
     *
     * @param subscriptionTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubscriptionTypeDTO> partialUpdate(SubscriptionTypeDTO subscriptionTypeDTO) {
        LOG.debug("Request to partially update SubscriptionType : {}", subscriptionTypeDTO);

        return subscriptionTypeRepository
            .findById(subscriptionTypeDTO.getId())
            .map(existingSubscriptionType -> {
                subscriptionTypeMapper.partialUpdate(existingSubscriptionType, subscriptionTypeDTO);

                return existingSubscriptionType;
            })
            .map(subscriptionTypeRepository::save)
            .map(subscriptionTypeMapper::toDto);
    }

    /**
     * Get all the subscriptionTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SubscriptionTypeDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all SubscriptionTypes");
        return subscriptionTypeRepository.findAll(pageable).map(subscriptionTypeMapper::toDto);
    }

    /**
     * Get one subscriptionType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubscriptionTypeDTO> findOne(Long id) {
        LOG.debug("Request to get SubscriptionType : {}", id);
        return subscriptionTypeRepository.findById(id).map(subscriptionTypeMapper::toDto);
    }

    /**
     * Delete the subscriptionType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SubscriptionType : {}", id);
        subscriptionTypeRepository.deleteById(id);
    }
}
