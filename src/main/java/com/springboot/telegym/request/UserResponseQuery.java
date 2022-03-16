package com.springboot.telegym.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseQuery {

    String id;

    String username;

    String name;

    String rolename;

    Date created_time;
}
