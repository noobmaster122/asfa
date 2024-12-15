package org.asfa.managerasfa.web.rest;

import static org.asfa.managerasfa.domain.SubscriptionTypeAsserts.*;
import static org.asfa.managerasfa.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.asfa.managerasfa.IntegrationTest;
import org.asfa.managerasfa.domain.SubscriptionType;
import org.asfa.managerasfa.repository.SubscriptionTypeRepository;
import org.asfa.managerasfa.service.dto.SubscriptionTypeDTO;
import org.asfa.managerasfa.service.mapper.SubscriptionTypeMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SubscriptionTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubscriptionTypeResourceIT {

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_SUMMARY = "AAAAAAAAAA";
    private static final String UPDATED_SUMMARY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/subscription-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SubscriptionTypeRepository subscriptionTypeRepository;

    @Autowired
    private SubscriptionTypeMapper subscriptionTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubscriptionTypeMockMvc;

    private SubscriptionType subscriptionType;

    private SubscriptionType insertedSubscriptionType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubscriptionType createEntity() {
        return new SubscriptionType().label(DEFAULT_LABEL).summary(DEFAULT_SUMMARY);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubscriptionType createUpdatedEntity() {
        return new SubscriptionType().label(UPDATED_LABEL).summary(UPDATED_SUMMARY);
    }

    @BeforeEach
    public void initTest() {
        subscriptionType = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSubscriptionType != null) {
            subscriptionTypeRepository.delete(insertedSubscriptionType);
            insertedSubscriptionType = null;
        }
    }

    @Test
    @Transactional
    void createSubscriptionType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SubscriptionType
        SubscriptionTypeDTO subscriptionTypeDTO = subscriptionTypeMapper.toDto(subscriptionType);
        var returnedSubscriptionTypeDTO = om.readValue(
            restSubscriptionTypeMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(subscriptionTypeDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SubscriptionTypeDTO.class
        );

        // Validate the SubscriptionType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSubscriptionType = subscriptionTypeMapper.toEntity(returnedSubscriptionTypeDTO);
        assertSubscriptionTypeUpdatableFieldsEquals(returnedSubscriptionType, getPersistedSubscriptionType(returnedSubscriptionType));

        insertedSubscriptionType = returnedSubscriptionType;
    }

    @Test
    @Transactional
    void createSubscriptionTypeWithExistingId() throws Exception {
        // Create the SubscriptionType with an existing ID
        subscriptionType.setId(1L);
        SubscriptionTypeDTO subscriptionTypeDTO = subscriptionTypeMapper.toDto(subscriptionType);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubscriptionTypeMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subscriptionTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLabelIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        subscriptionType.setLabel(null);

        // Create the SubscriptionType, which fails.
        SubscriptionTypeDTO subscriptionTypeDTO = subscriptionTypeMapper.toDto(subscriptionType);

        restSubscriptionTypeMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subscriptionTypeDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSubscriptionTypes() throws Exception {
        // Initialize the database
        insertedSubscriptionType = subscriptionTypeRepository.saveAndFlush(subscriptionType);

        // Get all the subscriptionTypeList
        restSubscriptionTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscriptionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].summary").value(hasItem(DEFAULT_SUMMARY)));
    }

    @Test
    @Transactional
    void getSubscriptionType() throws Exception {
        // Initialize the database
        insertedSubscriptionType = subscriptionTypeRepository.saveAndFlush(subscriptionType);

        // Get the subscriptionType
        restSubscriptionTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, subscriptionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subscriptionType.getId().intValue()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.summary").value(DEFAULT_SUMMARY));
    }

    @Test
    @Transactional
    void getNonExistingSubscriptionType() throws Exception {
        // Get the subscriptionType
        restSubscriptionTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubscriptionType() throws Exception {
        // Initialize the database
        insertedSubscriptionType = subscriptionTypeRepository.saveAndFlush(subscriptionType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subscriptionType
        SubscriptionType updatedSubscriptionType = subscriptionTypeRepository.findById(subscriptionType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSubscriptionType are not directly saved in db
        em.detach(updatedSubscriptionType);
        updatedSubscriptionType.label(UPDATED_LABEL).summary(UPDATED_SUMMARY);
        SubscriptionTypeDTO subscriptionTypeDTO = subscriptionTypeMapper.toDto(updatedSubscriptionType);

        restSubscriptionTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subscriptionTypeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subscriptionTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the SubscriptionType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSubscriptionTypeToMatchAllProperties(updatedSubscriptionType);
    }

    @Test
    @Transactional
    void putNonExistingSubscriptionType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subscriptionType.setId(longCount.incrementAndGet());

        // Create the SubscriptionType
        SubscriptionTypeDTO subscriptionTypeDTO = subscriptionTypeMapper.toDto(subscriptionType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubscriptionTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subscriptionTypeDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subscriptionTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubscriptionType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subscriptionType.setId(longCount.incrementAndGet());

        // Create the SubscriptionType
        SubscriptionTypeDTO subscriptionTypeDTO = subscriptionTypeMapper.toDto(subscriptionType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubscriptionTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(subscriptionTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubscriptionType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subscriptionType.setId(longCount.incrementAndGet());

        // Create the SubscriptionType
        SubscriptionTypeDTO subscriptionTypeDTO = subscriptionTypeMapper.toDto(subscriptionType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubscriptionTypeMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(subscriptionTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubscriptionType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubscriptionTypeWithPatch() throws Exception {
        // Initialize the database
        insertedSubscriptionType = subscriptionTypeRepository.saveAndFlush(subscriptionType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subscriptionType using partial update
        SubscriptionType partialUpdatedSubscriptionType = new SubscriptionType();
        partialUpdatedSubscriptionType.setId(subscriptionType.getId());

        restSubscriptionTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubscriptionType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubscriptionType))
            )
            .andExpect(status().isOk());

        // Validate the SubscriptionType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubscriptionTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSubscriptionType, subscriptionType),
            getPersistedSubscriptionType(subscriptionType)
        );
    }

    @Test
    @Transactional
    void fullUpdateSubscriptionTypeWithPatch() throws Exception {
        // Initialize the database
        insertedSubscriptionType = subscriptionTypeRepository.saveAndFlush(subscriptionType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the subscriptionType using partial update
        SubscriptionType partialUpdatedSubscriptionType = new SubscriptionType();
        partialUpdatedSubscriptionType.setId(subscriptionType.getId());

        partialUpdatedSubscriptionType.label(UPDATED_LABEL).summary(UPDATED_SUMMARY);

        restSubscriptionTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubscriptionType.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubscriptionType))
            )
            .andExpect(status().isOk());

        // Validate the SubscriptionType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubscriptionTypeUpdatableFieldsEquals(
            partialUpdatedSubscriptionType,
            getPersistedSubscriptionType(partialUpdatedSubscriptionType)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSubscriptionType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subscriptionType.setId(longCount.incrementAndGet());

        // Create the SubscriptionType
        SubscriptionTypeDTO subscriptionTypeDTO = subscriptionTypeMapper.toDto(subscriptionType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubscriptionTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subscriptionTypeDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subscriptionTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubscriptionType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subscriptionType.setId(longCount.incrementAndGet());

        // Create the SubscriptionType
        SubscriptionTypeDTO subscriptionTypeDTO = subscriptionTypeMapper.toDto(subscriptionType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubscriptionTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subscriptionTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubscriptionType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        subscriptionType.setId(longCount.incrementAndGet());

        // Create the SubscriptionType
        SubscriptionTypeDTO subscriptionTypeDTO = subscriptionTypeMapper.toDto(subscriptionType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubscriptionTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(subscriptionTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubscriptionType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubscriptionType() throws Exception {
        // Initialize the database
        insertedSubscriptionType = subscriptionTypeRepository.saveAndFlush(subscriptionType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the subscriptionType
        restSubscriptionTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, subscriptionType.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return subscriptionTypeRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected SubscriptionType getPersistedSubscriptionType(SubscriptionType subscriptionType) {
        return subscriptionTypeRepository.findById(subscriptionType.getId()).orElseThrow();
    }

    protected void assertPersistedSubscriptionTypeToMatchAllProperties(SubscriptionType expectedSubscriptionType) {
        assertSubscriptionTypeAllPropertiesEquals(expectedSubscriptionType, getPersistedSubscriptionType(expectedSubscriptionType));
    }

    protected void assertPersistedSubscriptionTypeToMatchUpdatableProperties(SubscriptionType expectedSubscriptionType) {
        assertSubscriptionTypeAllUpdatablePropertiesEquals(
            expectedSubscriptionType,
            getPersistedSubscriptionType(expectedSubscriptionType)
        );
    }
}
