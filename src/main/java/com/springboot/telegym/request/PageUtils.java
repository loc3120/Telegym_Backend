package com.springboot.telegym.request;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtils {

    public static Pageable getPageable(StructurePageRequest structurePageRequest) {

        int size = structurePageRequest.getPageSize() != 0 ? structurePageRequest.getPageSize() : 10;
        int page = structurePageRequest.getPage() != 0 ? structurePageRequest.getPage() - 1 : 0;
        String sortProperty = (structurePageRequest.getSortProperty() == null || structurePageRequest.getSortProperty().isBlank()) ?
                "created_time" : structurePageRequest.getSortProperty();
        String sortOrder = (structurePageRequest.getSortProperty() == null || structurePageRequest.getSortProperty().isBlank()) ?
                "desc" : structurePageRequest.getSortOrder();

        return org.springframework.data.domain.PageRequest.of(page, size, Sort.Direction.fromString(sortOrder), sortProperty);
    }
}
