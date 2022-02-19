package com.springboot.telegym.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SearchObject {

    private String str1;
    private String str2;
    private String str3;
    private Set<String> stringSet1;
    private boolean bl1;
}
