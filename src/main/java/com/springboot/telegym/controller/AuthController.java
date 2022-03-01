package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.ResponseObject;
import com.springboot.telegym.common.SearchObject;
import com.springboot.telegym.dto.UserDto;
import com.springboot.telegym.repository.UserRepository;
import com.springboot.telegym.security.JwtUtils;
import com.springboot.telegym.service.manageAccessDB.LoginDBService;
import com.springboot.telegym.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(Constant.CROSS_ORIGIN)
@RestController
@RequestMapping(Constant.API_URL + "auth")
public class AuthController extends BaseController {

    protected final UserService userService;

    protected final LoginDBService loginDBService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    public AuthController(UserService userService, LoginDBService loginDBService) {
        this.userService = userService;
        this.loginDBService = loginDBService;
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseObject> login(@Valid @RequestBody SearchObject searchObject) {
        UserDto userDto = userService.login(searchObject);
        return userDto != null ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", "Thông tin tài khoản", userDto)) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                        "Failed", MessageResponse.message, ""));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto userDto) {

        UserDto newUserDto = userService.createOrUpdate(userDto);

        return newUserDto != null ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", MessageResponse.message, newUserDto)) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                        "Failed", MessageResponse.message, ""));
    }

    @PostMapping("/logindb")
    public void loginDataBase(@Valid @RequestBody SearchObject searchObject) {
        loginDBService.loginDataBase(searchObject.getStr1(), searchObject.getStr2());
    }
}
