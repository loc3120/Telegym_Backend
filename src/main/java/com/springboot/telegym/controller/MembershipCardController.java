package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.common.ResponseObject;
import com.springboot.telegym.dto.MembershipCardDto;
import com.springboot.telegym.request.StructurePageRequest;
import com.springboot.telegym.service.membershipCard.MembershipCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(Constant.CROSS_ORIGIN)
@RestController
@RequestMapping(Constant.API_URL + "membershipcard")
public class MembershipCardController extends BaseController {

    protected final MembershipCardService membershipCardService;

    public MembershipCardController(MembershipCardService membershipCardService) {
        this.membershipCardService = membershipCardService;
    }

    @GetMapping("/getall")
    public ResponseEntity<ResponseObject> getAllMembershipCard(StructurePageRequest structurePageRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "In danh sách thành công",
                            membershipCardService.getAllMembershipCard(structurePageRequest)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                "Failed", "In danh sách thất bại", ""));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    @PostMapping("/modify")
    public void modifyMembershipCard(@Valid @RequestBody MembershipCardDto membershipCardDto) {
        membershipCardService.createOrUpdate(membershipCardDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteMembershipCard(@PathVariable("id") String id) {
        int lineSuccess = membershipCardService.deleteMembershipCard(id);
        return lineSuccess > 0 ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("Ok", "Xoá thành công", "")) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Ok", "Xoá thất bại", ""));
    }
}
