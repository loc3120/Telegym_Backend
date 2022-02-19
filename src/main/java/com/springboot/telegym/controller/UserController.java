package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.ResponseObject;
import com.springboot.telegym.dto.UserDto;
import com.springboot.telegym.request.StructurePageRequest;
import com.springboot.telegym.request.UserFilterRequest;
import com.springboot.telegym.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(Constant.CROSS_ORIGIN)
@RestController
@RequestMapping(Constant.API_URL + "user")
public class UserController extends BaseController {

    protected final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable("id") String id) {
        UserDto userDto = userService.getById(id);
        return userDto != null ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", "Truy vấn thành công", userDto)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                        "Failed", MessageResponse.message, ""));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<ResponseObject> update(@Valid @RequestBody UserDto userDto) {
        UserDto modifiedUser = userService.createOrUpdate(userDto);
        return modifiedUser != null ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", "Thay đổi thông tin thành công", modifiedUser)) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                        "Failed", MessageResponse.message, ""));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable String id) {
        boolean existed = userService.existById(id);
        if (existed) {
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Ok", "Xoá tài khoản thành công", ""));
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                    "Failed", "Không tìm thấy tài khoản", ""));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<ResponseObject> filterUser(StructurePageRequest structurePageRequest, UserFilterRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Ok", "Tìm kiếm thành công", userService.filterUser(structurePageRequest, request)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Failed", "Không tìm thấy tài khoản phù hợp", ""));
    }
}
