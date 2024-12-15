package org.asfa.managerasfa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.asfa.managerasfa.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class MemberRepositoryWithBagRelationshipsImpl implements MemberRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String MEMBERS_PARAMETER = "members";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Member> fetchBagRelationships(Optional<Member> member) {
        return member.map(this::fetchEventsubscriptions);
    }

    @Override
    public Page<Member> fetchBagRelationships(Page<Member> members) {
        return new PageImpl<>(fetchBagRelationships(members.getContent()), members.getPageable(), members.getTotalElements());
    }

    @Override
    public List<Member> fetchBagRelationships(List<Member> members) {
        return Optional.of(members).map(this::fetchEventsubscriptions).orElse(Collections.emptyList());
    }

    Member fetchEventsubscriptions(Member result) {
        return entityManager
            .createQuery("select member from Member member left join fetch member.eventsubscriptions where member.id = :id", Member.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Member> fetchEventsubscriptions(List<Member> members) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, members.size()).forEach(index -> order.put(members.get(index).getId(), index));
        List<Member> result = entityManager
            .createQuery(
                "select member from Member member left join fetch member.eventsubscriptions where member in :members",
                Member.class
            )
            .setParameter(MEMBERS_PARAMETER, members)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
