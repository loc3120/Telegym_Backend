package com.springboot.telegym.controller;

import com.springboot.telegym.common.*;
import com.springboot.telegym.dto.CandidateDto;
import com.springboot.telegym.request.StructurePageRequest;
import com.springboot.telegym.service.candidate.CandidateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(Constant.CROSS_ORIGIN)
@RestController
@RequestMapping(Constant.API_URL + "candidate")
public class CandidateController extends BaseController {

    protected final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/getall")
    public ResponseEntity<ResponseObject> getAllCandidate(StructurePageRequest structurePageRequest, String search) {
        PageData<CandidateDto> pageAllCandidates =
                candidateService.getAllCandidate(structurePageRequest, search);
        return pageAllCandidates.getData() != null ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", "Truy vấn thành công", pageAllCandidates)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                        "Failed", MessageResponse.message, ""));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createCandidate(@Valid @RequestBody CandidateDto candidateDto) {
        int lineSuccess = candidateService.createCandidate(candidateDto);
        return lineSuccess > 0 ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", "Gửi ứng tuyển thành công", "")) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                        "Failed", "Gửi ứng tuyển thất bại", ""));
    }

    @PostMapping(value = "/review/{id}")
    public String candidateReview(@PathVariable("id") String id, @RequestBody SearchObject searchObject) {
        return candidateService.candidateReview(id, searchObject.isBl1());
    }
}
