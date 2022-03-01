package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.common.ResponseObject;
import com.springboot.telegym.dto.GeneralClassDto;
import com.springboot.telegym.request.StructurePageRequest;
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
    public void modifyGeneralClass(@Valid @RequestBody  GeneralClassDto generalClassDto) {
        generalClassService.createOrUpdate(generalClassDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @GetMapping("/getall")
    public ResponseEntity<ResponseObject> selectGeneralClass(StructurePageRequest structurePageRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Ok", "In danh sách thành công", generalClassService.selectGC(structurePageRequest)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Failed", "Lỗi trong quá trình in danh sách", ""));
    }
}
