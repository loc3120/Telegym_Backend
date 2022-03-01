package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.common.ResponseObject;
import com.springboot.telegym.dto.CustomerDto;
import com.springboot.telegym.request.StructurePageRequest;
import com.springboot.telegym.service.customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(Constant.CROSS_ORIGIN)
@RestController
@RequestMapping(Constant.API_URL + "customer")
public class CustomerController extends BaseController {

    protected final CustomerService customerService;;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @PostMapping("/modify")
    public void modifyCustomer(@Valid @RequestBody CustomerDto customerDto) {
        customerService.createOrUpdate(customerDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @GetMapping("/getall")
    public ResponseEntity<ResponseObject> selectCustomer(StructurePageRequest structurePageRequest, String search) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Ok", "In danh sách thành công", customerService.getAllCustomer(structurePageRequest, search)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Failed", "Lỗi trong quá trình in danh sách", ""));
    }
}
