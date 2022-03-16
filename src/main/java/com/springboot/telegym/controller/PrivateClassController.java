package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.ResponseObject;
import com.springboot.telegym.dto.PrivateClassDto;
import com.springboot.telegym.request.StructurePageRequest;
import com.springboot.telegym.service.privateClass.PrivateClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(Constant.CROSS_ORIGIN)
@RestController
@RequestMapping(Constant.API_URL + "pc")
public class PrivateClassController extends BaseController{

    protected final PrivateClassService privateClassService;

    public PrivateClassController(PrivateClassService privateClassService) {
        this.privateClassService = privateClassService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @GetMapping("/getall")
    public ResponseEntity<ResponseObject> getAllPrivateClass(StructurePageRequest structurePageRequest, String search) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "In danh sách thành công",
                            privateClassService.getAllPrivateClass(structurePageRequest, search)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Failed", "In danh sách thất bại", ""));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @PostMapping("/modify")
    public ResponseEntity<ResponseObject> modifyPrivateClass(@Valid @RequestBody PrivateClassDto privateClassDto) {
        privateClassService.createOrUpdate(privateClassDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Ok", MessageResponse.message, ""));
    }
}
