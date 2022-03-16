package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.PageData;
import com.springboot.telegym.common.ResponseObject;
import com.springboot.telegym.dto.AccessManagementDto;
import com.springboot.telegym.dto.CustomerDto;
import com.springboot.telegym.request.StructurePageRequest;
import com.springboot.telegym.service.accessManagement.AccessManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(Constant.CROSS_ORIGIN)
@RestController
@RequestMapping(Constant.API_URL + "accessmanagement")
public class AccessManagementController extends BaseController {

    protected final AccessManagementService accessManagementService;

    public AccessManagementController(AccessManagementService accessManagementService) {
        this.accessManagementService = accessManagementService;
    }

    @PostMapping("/access")
    public ResponseEntity<ResponseObject> checkinOrCheckout(@Valid @RequestBody AccessManagementDto accessManagementDto) {
        int lineSuccess = accessManagementService.CheckInOrCheckOut(accessManagementDto);
        return lineSuccess > 0 ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", MessageResponse.message, "")) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                        "Failed", MessageResponse.message, ""));
    }

    @GetMapping("/historycustomer")
    public ResponseEntity<ResponseObject> getHistoryCustomer(StructurePageRequest structurePageRequest, String id_customer) {
        PageData<AccessManagementDto> accessManagementDtoPage =
                accessManagementService.selectEntryAndExitHistoryCustomer(structurePageRequest, id_customer);
        return accessManagementDtoPage.getData() != null ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", "Truy vấn thành công", accessManagementDtoPage)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                        "Failed", "Truy vấn thất bại", ""));
    }

    @GetMapping("/historyclass")
    public ResponseEntity<ResponseObject> getHistoryGeneralClass(StructurePageRequest structurePageRequest, String id_generalClass) {
        PageData<AccessManagementDto> accessManagementDtoPage =
                accessManagementService.selectEntryAndExitHistoryGeneralClass(structurePageRequest, id_generalClass);
        return accessManagementDtoPage.getData() != null ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", "Truy vấn thành công", accessManagementDtoPage)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                        "Failed", "Truy vấn thất bại", ""));
    }

    @GetMapping("/listcustomernow")
    public ResponseEntity<ResponseObject> getListCustomerNow(StructurePageRequest structurePageRequest, String id_generalClass) {
        PageData<CustomerDto> customerDtoPage =
                accessManagementService.listCustomerInClass(structurePageRequest, id_generalClass);
        return customerDtoPage.getData() != null ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", "Truy vấn thành công", customerDtoPage)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                        "Failed", "Truy vấn thất bại", ""));
    }

//    @GetMapping("/numbercustomernow")
//    public ResponseEntity<ResponseObject> getNumberCustomerNow(String id_generalClass) {
//        int lineSuccess = accessManagementService.numberCustomerInClass(id_generalClass);
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Ok", "Truy vấn thành công", lineSuccess));
//    }
}
