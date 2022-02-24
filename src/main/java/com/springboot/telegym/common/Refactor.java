package com.springboot.telegym.common;

import java.util.List;

public class Refactor {

    public static String RefactorList(List<String> sourceList) {
        return sourceList.toString().substring(1, sourceList.toString().length() - 1).
                replaceAll(" ", "");
    }
}
