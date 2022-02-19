package com.springboot.telegym.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.telegym.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public abstract class BaseDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date created_time;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String created_by;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updated_time;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String updated_by;

    public BaseDto(BaseEntity baseEntity) {
        this.created_time = baseEntity.getCreated_time();
        this.created_by = baseEntity.getCreated_by();
        this.updated_time = baseEntity.getUpdated_time();
        this.updated_by = baseEntity.getUpdated_by();
    }
}