package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.common.PageData;
import com.springboot.telegym.common.ResponseObject;
import com.springboot.telegym.common.SearchObject;
import com.springboot.telegym.dto.TryingPracticeDto;
import com.springboot.telegym.request.StructurePageRequest;
import com.springboot.telegym.service.tryingPractice.TryingPracticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(Constant.CROSS_ORIGIN)
@RestController
@RequestMapping(Constant.API_URL + "tp")
public class TryingPracticeController extends BaseController {

    protected final TryingPracticeService tryingPracticeService;

    public TryingPracticeController(TryingPracticeService tryingPracticeService) {
        this.tryingPracticeService = tryingPracticeService;
    }

    @GetMapping("/getall")
    public ResponseEntity<ResponseObject> getAllTP(StructurePageRequest structurePageRequest, String search) {
        PageData<TryingPracticeDto> pageAllTPs =
                tryingPracticeService.getAllTP(structurePageRequest, search);
        return pageAllTPs != null ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", "Truy vấn thành công", pageAllTPs)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                        "Failed", "Dữ liệu đưa vào không hợp lệ ", ""));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createTP(@Valid @RequestBody TryingPracticeDto tryingPracticeDto) {
        int lineSuccess = tryingPracticeService.createTP(tryingPracticeDto);
        return lineSuccess > 0 ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", "Đăng ký tập thử thành công", "")) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                        "Failed", "Đăng ký tập thử thất bại", ""));
    }

    @PostMapping("/contact/{id}")
    public String contactCustomer(@PathVariable("id") String id, @RequestBody SearchObject searchObject) {
        return tryingPracticeService.contactCustomer(id, searchObject.isBl1());
    }
}
