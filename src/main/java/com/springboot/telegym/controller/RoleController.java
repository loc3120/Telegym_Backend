package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.common.ResponseObject;
import com.springboot.telegym.common.SearchObject;
import com.springboot.telegym.dto.RoleDto;
import com.springboot.telegym.service.role.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/permission/modify")
    public ResponseEntity<ResponseObject> modifyPermUser(@Valid @RequestBody SearchObject searchObject) {;
        int lineSuccess = roleService.modifyPermission(searchObject);
        return lineSuccess > 0 ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", "Update quyền thành công", "")) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                        "Failed", "Update quyền thất bại", ""));
    }

}
