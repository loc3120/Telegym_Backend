package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.dto.RoleDto;
import com.springboot.telegym.service.role.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(Constant.CROSS_ORIGIN)
@RestController
@RequestMapping(Constant.API_URL + "role")
public class RoleController extends BaseController {

    protected final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getall")
    public ResponseEntity<List<RoleDto>> getAll() {;
        return new ResponseEntity<>(roleService.getAll(), HttpStatus.OK);
    }
}
