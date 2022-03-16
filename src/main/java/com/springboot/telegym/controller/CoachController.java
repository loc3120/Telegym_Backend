package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.ResponseObject;
import com.springboot.telegym.common.SearchObject;
import com.springboot.telegym.dto.CoachDto;
import com.springboot.telegym.request.StructurePageRequest;
import com.springboot.telegym.service.coach.CoachService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<ResponseObject> getAllCoach(StructurePageRequest structurePageRequest, String search) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Tìm kiếm thành công",
                            coachService.getAllCoach(structurePageRequest, search)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Failed", "Danh sách HLV trống", ""));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @PostMapping("/modify")
    public ResponseEntity<ResponseObject> modifyCoach(@Valid @RequestBody CoachDto coachDto) {
        CoachDto modifiedCoach = coachService.createOrUpdate(coachDto);
        return modifiedCoach != null ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", MessageResponse.message, modifiedCoach)) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                        "Failed", MessageResponse.message, ""));
    }

    @PostMapping("/rating")
    public ResponseEntity<ResponseObject> ratingCoach(@Valid @RequestBody SearchObject searchObject) {
        int lineSuccess = coachService.ratingCoach(searchObject);
        return lineSuccess != 0 ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", MessageResponse.message, "")) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                        "Failed", MessageResponse.message, ""));
    }

    @GetMapping("/gettop6")
    public ResponseEntity<ResponseObject> getTop6Coach() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", MessageResponse.message, coachService.top6RatingCoach()));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @GetMapping("/detail")
    public ResponseEntity<ResponseObject> detailCoach(String id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                "Ok", MessageResponse.message, coachService.coachDetail(id)));
    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseObject> deleteCoach(@Valid @RequestBody SearchObject searchObject) {
        int lineSuccess = coachService.deleteCoach(searchObject.getStr1());
        return lineSuccess != 0 ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", MessageResponse.message, "")) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                        "Failed", MessageResponse.message, ""));
    }
}
