package com.springboot.telegym.entity.idclass;

import java.io.Serializable;

public class CoachPackageId implements Serializable {

    private String id_coach;

    private String id_package;

    public CoachPackageId(String id_coach, String id_package) {
        this.id_coach = id_coach;
        this.id_package = id_package;
    }
}
