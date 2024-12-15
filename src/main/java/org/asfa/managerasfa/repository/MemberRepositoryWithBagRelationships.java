package org.asfa.managerasfa.repository;

import java.util.List;
import java.util.Optional;
import org.asfa.managerasfa.domain.Member;
import org.springframework.data.domain.Page;

public interface MemberRepositoryWithBagRelationships {
    Optional<Member> fetchBagRelationships(Optional<Member> member);

    List<Member> fetchBagRelationships(List<Member> members);

    Page<Member> fetchBagRelationships(Page<Member> members);
}
