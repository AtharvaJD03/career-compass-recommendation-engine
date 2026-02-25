package com.careercompass.careercompassbackendv2.controller;

import com.careercompass.careercompassbackendv2.model.UserProfileRequest;
import com.careercompass.careercompassbackendv2.service.VectorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//public class RecommendationController {
//
//    private final VectorService vectorService;
//
//    public RecommendationController(VectorService vectorService) {
//        this.vectorService = vectorService;
//    }
//
//    //  Testing on Browser
//    @GetMapping("/")
//    public List<String> defaultRecommendation() {
//
//        UserProfileRequest demoUser = new UserProfileRequest();
//        demoUser.setSkills("Python, Machine Learning");
//        demoUser.setEducation("BTech CSE");
//        demoUser.setInterests("AI Research");
//        demoUser.setExperience(2);
//
//        return vectorService.recommendFromProfile(demoUser, 3);
//    }
//}

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RecommendationController {

    private final VectorService vectorService;

    public RecommendationController(VectorService vectorService) {
        this.vectorService = vectorService;
    }

    @PostMapping("/recommend")
    public List<String> recommend(@RequestBody UserProfileRequest request) {
        return vectorService.recommendFromProfile(request, 3);
    }
}