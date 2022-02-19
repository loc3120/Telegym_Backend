package com.springboot.telegym.controller;

import com.springboot.telegym.common.Constant;
import com.springboot.telegym.common.MessageResponse;
import com.springboot.telegym.common.ResponseObject;
import com.springboot.telegym.dto.TypeExerciseDto;
import com.springboot.telegym.service.typeExercise.TypeExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(Constant.CROSS_ORIGIN)
@RestController
@RequestMapping(Constant.API_URL + "te")
public class TypeExerciseController extends BaseController {

    protected final TypeExerciseService typeExerciseService;

    public TypeExerciseController(TypeExerciseService typeExerciseService) {
        this.typeExerciseService = typeExerciseService;
    }

    @PostMapping("/modify")
    public ResponseEntity<ResponseObject> modifyTE(@Valid @RequestBody TypeExerciseDto typeExerciseDto) {
        TypeExerciseDto modifiedTE = typeExerciseService.createOrUpdate(typeExerciseDto);
        return modifiedTE != null ?
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                        "Ok", "Thông tin loại gói tập", modifiedTE)) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                        "Failed", MessageResponse.message, ""));
    }
}
