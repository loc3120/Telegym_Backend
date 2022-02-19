package com.springboot.telegym.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserFilterRequest {
    String search;
    String roleName;
}
