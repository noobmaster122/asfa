package org.asfa.managerasfa.service.mapper;

import org.asfa.managerasfa.domain.Payment;
import org.asfa.managerasfa.domain.PaymentMethod;
import org.asfa.managerasfa.service.dto.PaymentDTO;
import org.asfa.managerasfa.service.dto.PaymentMethodDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payment} and its DTO {@link PaymentDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentMapper extends EntityMapper<PaymentDTO, Payment> {
    @Mapping(target = "paymentmethods", source = "paymentmethods", qualifiedByName = "paymentMethodId")
    PaymentDTO toDto(Payment s);

    @Named("paymentMethodId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentMethodDTO toDtoPaymentMethodId(PaymentMethod paymentMethod);
}
