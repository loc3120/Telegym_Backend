package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.ResponseObject;
import com.springboot.telegym.dto.TrackingProgressDto;
import com.springboot.telegym.request.StructurePageRequest;
import com.springboot.telegym.service.trackingProgress.TrackingProgressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(Constant.CROSS_ORIGIN)
@RestController
@RequestMapping(Constant.API_URL + "tracking")
public class TrackingProgressController extends BaseController {

    protected final TrackingProgressService trackingProgressService;

    public TrackingProgressController(TrackingProgressService trackingProgressService) {
        this.trackingProgressService = trackingProgressService;
    }

    @GetMapping("/getall/{id_private_class}")
    public ResponseEntity<ResponseObject> getAllTrackingProgress
            (StructurePageRequest structurePageRequest, @PathVariable("id_private_class") String id_private_class) {
        System.out.println(id_private_class);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "In danh sách thành công",
                            trackingProgressService.getAllTrackingProgress(structurePageRequest, id_private_class)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Failed", "In danh sách thất bại", ""));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createTrackingProgress(@Valid @RequestBody TrackingProgressDto trackingProgressDto) {
        TrackingProgressDto newTrackingProgress = trackingProgressService.createData(trackingProgressDto);
        return newTrackingProgress != null ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", MessageResponse.message, newTrackingProgress)) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                        "Failed", MessageResponse.message, ""));
    }
}
