package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.ResponseObject;
import com.springboot.telegym.dto.CoachDto;
import com.springboot.telegym.request.StructurePageRequest;
import com.springboot.telegym.service.coach.CoachService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(Constant.CROSS_ORIGIN)
@RestController
@RequestMapping(Constant.API_URL + "coach")
public class CoachController extends BaseController {

    protected final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping("/getall")
    public ResponseEntity<ResponseObject> getAllCoach(StructurePageRequest structurePageRequest,
                                                      @RequestParam("typecoach") String typeCoach) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    "Ok", "Tìm kiếm thành công",
                    coachService.getAllCoach(structurePageRequest, typeCoach)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Failed", MessageResponse.message, ""));
    }

    @PostMapping("/modify")
    public ResponseEntity<ResponseObject> modifyCoach(@Valid @RequestBody CoachDto coachDto) {
        CoachDto modifiedCoach = coachService.createOrUpdate(coachDto);
        return modifiedCoach != null ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", "Thông tin tài khoản", modifiedCoach)) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                        "Failed", MessageResponse.message, ""));
    }
}
