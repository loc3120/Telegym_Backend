package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.common.PageData;
import com.springboot.telegym.common.ResponseObject;
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
    public void createTP(@Valid @RequestBody TryingPracticeDto tryingPracticeDto) {
        tryingPracticeService.createTP(tryingPracticeDto);
    }

    @PostMapping("/contact/{id}")
    public void contactCustomer(@PathVariable("id") String id) {
        tryingPracticeService.contactCustomer(id);
    }
}
