package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.ResponseObject;
import com.springboot.telegym.dto.GeneralClassDto;
import com.springboot.telegym.service.generalClass.GeneralClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(Constant.CROSS_ORIGIN)
@RestController
@RequestMapping(Constant.API_URL + "gc")
public class GeneralClassController extends BaseController {

    protected final GeneralClassService generalClassService;

    public GeneralClassController(GeneralClassService generalClassService) {
        this.generalClassService = generalClassService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @PostMapping("/modify")
    public ResponseEntity<ResponseObject> modifyGeneralClass(@Valid @RequestBody  GeneralClassDto generalClassDto) {
        generalClassService.createOrUpdate(generalClassDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Ok", MessageResponse.message, ""));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @GetMapping("/getall")
    public ResponseEntity<ResponseObject> selectGeneralClass() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Ok", "In danh sách thành công", generalClassService.selectGC()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Failed", "Lỗi trong quá trình in danh sách", ""));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @GetMapping("/getname")
    public ResponseEntity<ResponseObject> selectListNameGeneralClass(String type) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Ok", "In danh sách thành công", generalClassService.listNameGeneralClass(type)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Failed", "Lỗi trong quá trình in danh sách", ""));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @GetMapping("/detail")
    public ResponseEntity<ResponseObject> selectDetailGeneralClass(String id) {
        GeneralClassDto generalClassDto = generalClassService.detailGeneralClass(id);
        return generalClassDto != null ? ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Ok", MessageResponse.message, generalClassDto)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                        "Failed", MessageResponse.message, ""));
    }
}
