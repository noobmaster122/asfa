package org.asfa.managerasfa.repository;

import org.asfa.managerasfa.domain.EventSubscription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventSubscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventSubscriptionRepository extends JpaRepository<EventSubscription, Long> {}
