package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.ResponseObject;
import com.springboot.telegym.dto.PackageDto;
import com.springboot.telegym.service.packageFolder.PackageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(Constant.CROSS_ORIGIN)
@RestController
@RequestMapping(Constant.API_URL + "package")
public class PackageController extends BaseController {

    protected final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @PostMapping("/modify")
    public ResponseEntity<ResponseObject> modifyPackage(@Valid @RequestBody PackageDto packageDto) {
        PackageDto modifiedPackage = packageService.createOrUpdate(packageDto);
        return modifiedPackage != null ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", "Thông tin gói tập", modifiedPackage)) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                        "Failed", MessageResponse.message, ""));
    }

    @GetMapping("/getall")
    public ResponseEntity<ResponseObject> getAll(@RequestParam(value = "typeName", required = false) String typeName) {
        List<PackageDto> packageDtoList = packageService.getAll(typeName);
        return packageDtoList.size() != 0 ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", "Thông tin gói tập", packageDtoList)) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                        "Failed", "Không tìm thấy kết quả phù hợp", ""));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deletePackage(@PathVariable String id) {
        boolean checkExisted = packageService.existedId(id);
        if (checkExisted) {
            packageService.deletePackage(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Ok", "Xoá thành công", ""));
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                    "Failed", "Xoá không thành công", ""));
        }
    }
}
