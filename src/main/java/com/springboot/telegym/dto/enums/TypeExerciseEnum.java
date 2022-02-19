package com.springboot.telegym.dto.enums;

public enum TypeExerciseEnum {

    GYM ("GYM"),
    YOGA ("YOGA");

    private String type;

    TypeExerciseEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
