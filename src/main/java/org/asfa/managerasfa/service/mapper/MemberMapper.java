package org.asfa.managerasfa.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.asfa.managerasfa.domain.EventSubscription;
import org.asfa.managerasfa.domain.Member;
import org.asfa.managerasfa.service.dto.EventSubscriptionDTO;
import org.asfa.managerasfa.service.dto.MemberDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Member} and its DTO {@link MemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface MemberMapper extends EntityMapper<MemberDTO, Member> {
    @Mapping(target = "eventsubscriptions", source = "eventsubscriptions", qualifiedByName = "eventSubscriptionIdSet")
    @Mapping(target = "member", source = "member", qualifiedByName = "memberId")
    MemberDTO toDto(Member s);

    @Mapping(target = "removeEventsubscription", ignore = true)
    Member toEntity(MemberDTO memberDTO);

    @Named("memberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MemberDTO toDtoMemberId(Member member);

    @Named("eventSubscriptionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventSubscriptionDTO toDtoEventSubscriptionId(EventSubscription eventSubscription);

    @Named("eventSubscriptionIdSet")
    default Set<EventSubscriptionDTO> toDtoEventSubscriptionIdSet(Set<EventSubscription> eventSubscription) {
        return eventSubscription.stream().map(this::toDtoEventSubscriptionId).collect(Collectors.toSet());
    }
}
