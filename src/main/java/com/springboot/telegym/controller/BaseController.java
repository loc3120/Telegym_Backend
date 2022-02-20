package com.springboot.telegym.controller;

import com.springboot.telegym.service.candidate.CandidateService;
import com.springboot.telegym.service.coach.CoachService;
import com.springboot.telegym.service.role.RoleService;
import com.springboot.telegym.service.tryingPractice.TryingPracticeService;
import com.springboot.telegym.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class BaseController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    CandidateService candidateService;

    @Autowired
    TryingPracticeService tryingPracticeService;

    @Autowired
    CoachService coachService;
}
