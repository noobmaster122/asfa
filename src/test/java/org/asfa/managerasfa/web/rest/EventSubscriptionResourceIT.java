package org.asfa.managerasfa.web.rest;

import static org.asfa.managerasfa.domain.EventSubscriptionAsserts.*;
import static org.asfa.managerasfa.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.asfa.managerasfa.IntegrationTest;
import org.asfa.managerasfa.domain.EventSubscription;
import org.asfa.managerasfa.repository.EventSubscriptionRepository;
import org.asfa.managerasfa.service.dto.EventSubscriptionDTO;
import org.asfa.managerasfa.service.mapper.EventSubscriptionMapper;
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
 * Integration tests for the {@link EventSubscriptionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventSubscriptionResourceIT {

    private static final LocalDate DEFAULT_SUBSCRIPTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SUBSCRIPTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/event-subscriptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EventSubscriptionRepository eventSubscriptionRepository;

    @Autowired
    private EventSubscriptionMapper eventSubscriptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventSubscriptionMockMvc;

    private EventSubscription eventSubscription;

    private EventSubscription insertedEventSubscription;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventSubscription createEntity() {
        return new EventSubscription().subscriptionDate(DEFAULT_SUBSCRIPTION_DATE).isActive(DEFAULT_IS_ACTIVE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventSubscription createUpdatedEntity() {
        return new EventSubscription().subscriptionDate(UPDATED_SUBSCRIPTION_DATE).isActive(UPDATED_IS_ACTIVE);
    }

    @BeforeEach
    public void initTest() {
        eventSubscription = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedEventSubscription != null) {
            eventSubscriptionRepository.delete(insertedEventSubscription);
            insertedEventSubscription = null;
        }
    }

    @Test
    @Transactional
    void createEventSubscription() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EventSubscription
        EventSubscriptionDTO eventSubscriptionDTO = eventSubscriptionMapper.toDto(eventSubscription);
        var returnedEventSubscriptionDTO = om.readValue(
            restEventSubscriptionMockMvc
                .perform(
                    post(ENTITY_API_URL)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(eventSubscriptionDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EventSubscriptionDTO.class
        );

        // Validate the EventSubscription in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEventSubscription = eventSubscriptionMapper.toEntity(returnedEventSubscriptionDTO);
        assertEventSubscriptionUpdatableFieldsEquals(returnedEventSubscription, getPersistedEventSubscription(returnedEventSubscription));

        insertedEventSubscription = returnedEventSubscription;
    }

    @Test
    @Transactional
    void createEventSubscriptionWithExistingId() throws Exception {
        // Create the EventSubscription with an existing ID
        eventSubscription.setId(1L);
        EventSubscriptionDTO eventSubscriptionDTO = eventSubscriptionMapper.toDto(eventSubscription);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventSubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventSubscription in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSubscriptionDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        eventSubscription.setSubscriptionDate(null);

        // Create the EventSubscription, which fails.
        EventSubscriptionDTO eventSubscriptionDTO = eventSubscriptionMapper.toDto(eventSubscription);

        restEventSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventSubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActiveIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        eventSubscription.setIsActive(null);

        // Create the EventSubscription, which fails.
        EventSubscriptionDTO eventSubscriptionDTO = eventSubscriptionMapper.toDto(eventSubscription);

        restEventSubscriptionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventSubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEventSubscriptions() throws Exception {
        // Initialize the database
        insertedEventSubscription = eventSubscriptionRepository.saveAndFlush(eventSubscription);

        // Get all the eventSubscriptionList
        restEventSubscriptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventSubscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].subscriptionDate").value(hasItem(DEFAULT_SUBSCRIPTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getEventSubscription() throws Exception {
        // Initialize the database
        insertedEventSubscription = eventSubscriptionRepository.saveAndFlush(eventSubscription);

        // Get the eventSubscription
        restEventSubscriptionMockMvc
            .perform(get(ENTITY_API_URL_ID, eventSubscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventSubscription.getId().intValue()))
            .andExpect(jsonPath("$.subscriptionDate").value(DEFAULT_SUBSCRIPTION_DATE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEventSubscription() throws Exception {
        // Get the eventSubscription
        restEventSubscriptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEventSubscription() throws Exception {
        // Initialize the database
        insertedEventSubscription = eventSubscriptionRepository.saveAndFlush(eventSubscription);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eventSubscription
        EventSubscription updatedEventSubscription = eventSubscriptionRepository.findById(eventSubscription.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEventSubscription are not directly saved in db
        em.detach(updatedEventSubscription);
        updatedEventSubscription.subscriptionDate(UPDATED_SUBSCRIPTION_DATE).isActive(UPDATED_IS_ACTIVE);
        EventSubscriptionDTO eventSubscriptionDTO = eventSubscriptionMapper.toDto(updatedEventSubscription);

        restEventSubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventSubscriptionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventSubscriptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventSubscription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEventSubscriptionToMatchAllProperties(updatedEventSubscription);
    }

    @Test
    @Transactional
    void putNonExistingEventSubscription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventSubscription.setId(longCount.incrementAndGet());

        // Create the EventSubscription
        EventSubscriptionDTO eventSubscriptionDTO = eventSubscriptionMapper.toDto(eventSubscription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventSubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventSubscriptionDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventSubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventSubscription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventSubscription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventSubscription.setId(longCount.incrementAndGet());

        // Create the EventSubscription
        EventSubscriptionDTO eventSubscriptionDTO = eventSubscriptionMapper.toDto(eventSubscription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventSubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(eventSubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventSubscription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventSubscription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventSubscription.setId(longCount.incrementAndGet());

        // Create the EventSubscription
        EventSubscriptionDTO eventSubscriptionDTO = eventSubscriptionMapper.toDto(eventSubscription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventSubscriptionMockMvc
            .perform(
                put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(eventSubscriptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventSubscription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventSubscriptionWithPatch() throws Exception {
        // Initialize the database
        insertedEventSubscription = eventSubscriptionRepository.saveAndFlush(eventSubscription);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eventSubscription using partial update
        EventSubscription partialUpdatedEventSubscription = new EventSubscription();
        partialUpdatedEventSubscription.setId(eventSubscription.getId());

        partialUpdatedEventSubscription.subscriptionDate(UPDATED_SUBSCRIPTION_DATE);

        restEventSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventSubscription.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEventSubscription))
            )
            .andExpect(status().isOk());

        // Validate the EventSubscription in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEventSubscriptionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEventSubscription, eventSubscription),
            getPersistedEventSubscription(eventSubscription)
        );
    }

    @Test
    @Transactional
    void fullUpdateEventSubscriptionWithPatch() throws Exception {
        // Initialize the database
        insertedEventSubscription = eventSubscriptionRepository.saveAndFlush(eventSubscription);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the eventSubscription using partial update
        EventSubscription partialUpdatedEventSubscription = new EventSubscription();
        partialUpdatedEventSubscription.setId(eventSubscription.getId());

        partialUpdatedEventSubscription.subscriptionDate(UPDATED_SUBSCRIPTION_DATE).isActive(UPDATED_IS_ACTIVE);

        restEventSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventSubscription.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEventSubscription))
            )
            .andExpect(status().isOk());

        // Validate the EventSubscription in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEventSubscriptionUpdatableFieldsEquals(
            partialUpdatedEventSubscription,
            getPersistedEventSubscription(partialUpdatedEventSubscription)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEventSubscription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventSubscription.setId(longCount.incrementAndGet());

        // Create the EventSubscription
        EventSubscriptionDTO eventSubscriptionDTO = eventSubscriptionMapper.toDto(eventSubscription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventSubscriptionDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eventSubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventSubscription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventSubscription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventSubscription.setId(longCount.incrementAndGet());

        // Create the EventSubscription
        EventSubscriptionDTO eventSubscriptionDTO = eventSubscriptionMapper.toDto(eventSubscription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eventSubscriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventSubscription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventSubscription() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        eventSubscription.setId(longCount.incrementAndGet());

        // Create the EventSubscription
        EventSubscriptionDTO eventSubscriptionDTO = eventSubscriptionMapper.toDto(eventSubscription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventSubscriptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(eventSubscriptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventSubscription in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventSubscription() throws Exception {
        // Initialize the database
        insertedEventSubscription = eventSubscriptionRepository.saveAndFlush(eventSubscription);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the eventSubscription
        restEventSubscriptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventSubscription.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return eventSubscriptionRepository.count();
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

    protected EventSubscription getPersistedEventSubscription(EventSubscription eventSubscription) {
        return eventSubscriptionRepository.findById(eventSubscription.getId()).orElseThrow();
    }

    protected void assertPersistedEventSubscriptionToMatchAllProperties(EventSubscription expectedEventSubscription) {
        assertEventSubscriptionAllPropertiesEquals(expectedEventSubscription, getPersistedEventSubscription(expectedEventSubscription));
    }

    protected void assertPersistedEventSubscriptionToMatchUpdatableProperties(EventSubscription expectedEventSubscription) {
        assertEventSubscriptionAllUpdatablePropertiesEquals(
            expectedEventSubscription,
            getPersistedEventSubscription(expectedEventSubscription)
        );
    }
}
