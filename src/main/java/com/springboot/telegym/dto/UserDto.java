package com.springboot.telegym.dto;

import com.springboot.telegym.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseDto{

    private String id;

    private String username;

    private String pass;

    private String name;

    private String tokenValue;

    private boolean isDelete;

    private String role;

    public UserDto(User user) {
        super(user);
        this.id = user.getId();
        this.username = user.getUsername();
        this.pass = user.getPass();
        this.name = user.getName();
        this.tokenValue = user.getToken_value();
        this.isDelete = user.is_deleted();
        this.role = user.getRole().getRolename();
    }
}