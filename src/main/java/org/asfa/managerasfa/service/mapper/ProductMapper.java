package org.asfa.managerasfa.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.asfa.managerasfa.domain.Category;
import org.asfa.managerasfa.domain.EventSubscription;
import org.asfa.managerasfa.domain.Product;
import org.asfa.managerasfa.service.dto.CategoryDTO;
import org.asfa.managerasfa.service.dto.EventSubscriptionDTO;
import org.asfa.managerasfa.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "eventsubscriptions", source = "eventsubscriptions", qualifiedByName = "eventSubscriptionIdSet")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "categoryId")
    ProductDTO toDto(Product s);

    @Mapping(target = "removeEventsubscription", ignore = true)
    Product toEntity(ProductDTO productDTO);

    @Named("eventSubscriptionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventSubscriptionDTO toDtoEventSubscriptionId(EventSubscription eventSubscription);

    @Named("eventSubscriptionIdSet")
    default Set<EventSubscriptionDTO> toDtoEventSubscriptionIdSet(Set<EventSubscription> eventSubscription) {
        return eventSubscription.stream().map(this::toDtoEventSubscriptionId).collect(Collectors.toSet());
    }

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);
}
