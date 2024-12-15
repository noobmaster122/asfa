package org.asfa.managerasfa.repository;

import org.asfa.managerasfa.domain.SubscriptionType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SubscriptionType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubscriptionTypeRepository extends JpaRepository<SubscriptionType, Long> {}
