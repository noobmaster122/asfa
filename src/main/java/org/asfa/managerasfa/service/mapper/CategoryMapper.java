package org.asfa.managerasfa.service.mapper;

import org.asfa.managerasfa.domain.Category;
import org.asfa.managerasfa.service.dto.CategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {}
