package com.springboot.telegym.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StructurePageRequest {
    private int pageSize;
    private int page;
    private String sortProperty;
    private String sortOrder;
}
