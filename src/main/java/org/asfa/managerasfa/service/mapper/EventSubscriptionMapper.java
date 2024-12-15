package org.asfa.managerasfa.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.asfa.managerasfa.domain.EventSubscription;
import org.asfa.managerasfa.domain.Member;
import org.asfa.managerasfa.domain.Payment;
import org.asfa.managerasfa.domain.Product;
import org.asfa.managerasfa.domain.SubscriptionType;
import org.asfa.managerasfa.service.dto.EventSubscriptionDTO;
import org.asfa.managerasfa.service.dto.MemberDTO;
import org.asfa.managerasfa.service.dto.PaymentDTO;
import org.asfa.managerasfa.service.dto.ProductDTO;
import org.asfa.managerasfa.service.dto.SubscriptionTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventSubscription} and its DTO {@link EventSubscriptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventSubscriptionMapper extends EntityMapper<EventSubscriptionDTO, EventSubscription> {
    @Mapping(target = "types", source = "types", qualifiedByName = "subscriptionTypeId")
    @Mapping(target = "payment", source = "payment", qualifiedByName = "paymentId")
    @Mapping(target = "members", source = "members", qualifiedByName = "memberIdSet")
    @Mapping(target = "products", source = "products", qualifiedByName = "productIdSet")
    EventSubscriptionDTO toDto(EventSubscription s);

    @Mapping(target = "members", ignore = true)
    @Mapping(target = "removeMember", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "removeProduct", ignore = true)
    EventSubscription toEntity(EventSubscriptionDTO eventSubscriptionDTO);

    @Named("subscriptionTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubscriptionTypeDTO toDtoSubscriptionTypeId(SubscriptionType subscriptionType);

    @Named("paymentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentDTO toDtoPaymentId(Payment payment);

    @Named("memberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MemberDTO toDtoMemberId(Member member);

    @Named("memberIdSet")
    default Set<MemberDTO> toDtoMemberIdSet(Set<Member> member) {
        return member.stream().map(this::toDtoMemberId).collect(Collectors.toSet());
    }

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);

    @Named("productIdSet")
    default Set<ProductDTO> toDtoProductIdSet(Set<Product> product) {
        return product.stream().map(this::toDtoProductId).collect(Collectors.toSet());
    }
}
